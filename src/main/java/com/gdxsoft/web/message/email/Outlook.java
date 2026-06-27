package com.gdxsoft.web.message.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.utils.UMail;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.Mail.SmtpCfgs;

/**
 * 生成 Outlook 日历会议邀请 (.ics) — 纯字符串拼接，零 ical4j 依赖
 */
public class Outlook {
	private static final Logger LOGGER = LoggerFactory.getLogger(Outlook.class);

	private static final DateTimeFormatter ICS_DATETIME = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
	private static final ZoneId ZONE = ZoneId.of("Asia/Shanghai");

	// Outlook/NOTES 兼容性 VTIMEZONE
	private static final String VTIMEZONE = "BEGIN:VTIMEZONE\r\n"
			+ "TZID:Asia/Shanghai\r\n"
			+ "BEGIN:STANDARD\r\n"
			+ "DTSTART:19700101T000000\r\n"
			+ "TZOFFSETFROM:+0800\r\n"
			+ "TZOFFSETTO:+0800\r\n"
			+ "TZNAME:CST\r\n"
			+ "END:STANDARD\r\n"
			+ "END:VTIMEZONE\r\n";

	public static byte[] attachBinaryAttachment(String subject, String content, String location, Date fromDate,
			Date toDate, String mailTos) throws IOException {
		ZonedDateTime start = fromDate.toInstant().atZone(ZONE);
		ZonedDateTime end = toDate.toInstant().atZone(ZONE);

		StringBuilder ics = new StringBuilder(2048);
		ics.append("BEGIN:VCALENDAR\r\n");
		ics.append("PRODID:-//GDXSoft//Outlook Calendar//EN\r\n");
		ics.append("VERSION:2.0\r\n");
		ics.append("CALSCALE:GREGORIAN\r\n");
		ics.append("METHOD:REQUEST\r\n");
		ics.append(VTIMEZONE);
		ics.append("BEGIN:VEVENT\r\n");
		ics.append("DTSTART;TZID=Asia/Shanghai:").append(start.format(ICS_DATETIME)).append("\r\n");
		ics.append("DTEND;TZID=Asia/Shanghai:").append(end.format(ICS_DATETIME)).append("\r\n");

		String escapedSubject = escapeIcsText(subject);
		String escapedContent = escapeIcsText(content);
		String escapedLocation = escapeIcsText(location);

		ics.append("SUMMARY:").append(escapedSubject).append("\r\n");
		ics.append("DESCRIPTION:").append(escapedContent).append("\r\n");
		ics.append("LOCATION:").append(escapedLocation).append("\r\n");
		ics.append("ORGANIZER;CN=").append(escapeIcsParam("郭磊"))
				.append(":mailto:lei.guo@gyap.org\r\n");

		ics.append("X-MICROSOFT-CDO-ALLDAYEVENT:TRUE\r\n");
		ics.append("X-MICROSOFT-CDO-INTENDEDSTATUS:FREE\r\n");
		ics.append("UID:").append(UUID.randomUUID().toString()).append("\r\n");

		// Attendees
		String[] mails = mailTos.split(";");
		for (String m : mails) {
			m = m.trim();
			if (m.isEmpty()) {
				continue;
			}
			ics.append("ATTENDEE;ROLE=OPT-PARTICIPANT:mailto:").append(m).append("\r\n");
		}

		// Reminder (15 min before)
		ics.append("BEGIN:VALARM\r\n");
		ics.append("TRIGGER:-PT15M\r\n");
		ics.append("ACTION:DISPLAY\r\n");
		ics.append("DESCRIPTION:").append(escapedSubject).append("\r\n");
		ics.append("END:VALARM\r\n");

		ics.append("END:VEVENT\r\n");
		ics.append("END:VCALENDAR\r\n");

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			bout.write(ics.toString().getBytes(StandardCharsets.UTF_8));
			return bout.toByteArray();
		} finally {
			bout.close();
		}
	}

	// iCalendar text field escaping: \n → \\n, \\ → \\\\
	private static String escapeIcsText(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("\\", "\\\\").replace("\r\n", "\\n").replace("\n", "\\n").replace("\r", "\\n");
	}

	// iCalendar parameter value escaping
	private static String escapeIcsParam(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("\"", "'");
	}

	public static boolean sendCal(String subject, String content, String location, Date fromDate, Date toDate,
			String mailFrom, String mailTos) {
		try {
			byte[] buf = attachBinaryAttachment(subject, content, location, fromDate, toDate, mailTos);

			String name = Utils.getGuid() + ".ics";
			String tempFile = UPath.getPATH_IMG_CACHE() + "/icalendar/" + name;
			com.gdxsoft.easyweb.utils.UFile.createBinaryFile(tempFile, buf, true);

			MimeMessage mimeMessage = new MimeMessage(SmtpCfgs.createMailSession(SmtpCfgs.getDefaultSmtpCfg()));
			mimeMessage.setSubject(subject);
			InternetAddress iaFrom = new InternetAddress(mailFrom.trim());
			mimeMessage.setFrom(iaFrom);
			String[] tos = mailTos.split(";");

			InternetAddress[] iaToList = new InternetAddress[tos.length];
			for (int i = 0; i < iaToList.length; i++) {
				iaToList[i] = new InternetAddress(tos[i].trim());
			}
			mimeMessage.addRecipients(Message.RecipientType.TO, iaToList);

			Multipart multipart = new MimeMultipart();
			MimeBodyPart iCalAttachment = new MimeBodyPart();
			iCalAttachment.setDataHandler(new DataHandler(new ByteArrayDataSource(
					new ByteArrayInputStream(buf), "text/calendar;method=REQUEST;charset=\"UTF-8\"")));
			multipart.addBodyPart(iCalAttachment);
			mimeMessage.setContent(multipart);

			String result = UMail.sendMail(mimeMessage);
			if (result == null) {
				return true;
			} else {
				LOGGER.error(result);
				return false;
			}
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
			return false;
		}
	}
}
