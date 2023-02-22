package com.gdxsoft.web.message.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.utils.UMail;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.Mail.SmtpCfgs;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.UidGenerator;

public class Outlook {
	private static Logger LOGGER = LoggerFactory.getLogger(Outlook.class);

	public static byte[] attachBinaryAttachment(String subject, String content, String location, Date fromDate,
			Date toDate, String mailTos)
			throws IOException, ParserException, ValidationException, ParseException, URISyntaxException {
		/**
		 * 以下两步骤的处理也是为了防止outlook或者是notes将日历当做附件使用增加的
		 */
		CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_OUTLOOK_COMPATIBILITY, true);
		CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_NOTES_COMPATIBILITY, true);

		DateTime start = new DateTime(fromDate.getTime());
		DateTime end = new DateTime(toDate.getTime());

		VEvent meeting = new VEvent(start, end, subject);
		meeting.getProperties().add(new Uid(new UidGenerator("iCal4j").generateUid().getValue()));

		Organizer orger = new Organizer(URI.create("lei.guo@gyap.org"));
		orger.getParameters().add(new Cn("郭磊"));
		meeting.getProperties().add(orger);

		// 设置时区
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("Asia/Shanghai");
		VTimeZone tz = timezone.getVTimeZone();
		meeting.getProperties().add(tz.getTimeZoneId());

		meeting.getProperties().add(new Location(location));
		// meeting.getProperties().add(new Summary(subject));
		meeting.getProperties().add(new Description(content));

		meeting.getProperties().add(new XProperty("X-MICROSOFT-CDO-ALLDAYEVENT", "TRUE"));
		meeting.getProperties().add(new XProperty("X-MICROSOFT-CDO-INTENDEDSTATUS", "FREE"));

		// 添加参加人
		String[] mails = mailTos.split(";");
		for (int i = 0; i < mails.length; i++) {
			String m = mails[i].trim();
			if (m.length() == 0) {
				continue;
			}
			Attendee attendee = new Attendee(URI.create("mailto:" + m));
			attendee.getParameters().add(Role.OPT_PARTICIPANT);
			// attendee.getParameters().add(new Cn(m));
			meeting.getProperties().add(attendee);
		}

		// 提醒
		VAlarm reminder = new VAlarm(new Dur("-PT15M"));
		// repeat reminder four (4) more times every fifteen (15) minutes..

		// reminder.getProperties().add(new Repeat(4));
		// reminder.getProperties().add(new Duration(new Dur(1000 * 60 * 15)));

		// display a message..

		reminder.getProperties().add(Action.DISPLAY);

		reminder.getProperties().add(new Description(subject));
		// alarm.
		meeting.getAlarms().add(reminder);

		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//OneWorld CC//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		calendar.getProperties().add(Method.REQUEST);

		calendar.getComponents().add(meeting);
		// 验证
		calendar.validate();
		CalendarOutputter outputter = new CalendarOutputter();
		ByteArrayOutputStream bout1 = new ByteArrayOutputStream();
		try {
			outputter.output(calendar, bout1);
			return bout1.toByteArray();
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
			return null;
		} finally {
			bout1.close();
		}
	}

	/**
	 * 发送会议
	 * 
	 * @param subject  主题
	 * @param content  内容
	 * @param location 地点
	 * @param fromDate 开始时间
	 * @param toDate   截至时间
	 * @param mailFrom 发件人
	 * @param mailTos  收件人
	 * @return
	 */
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

			iCalAttachment.setDataHandler(new DataHandler(new ByteArrayDataSource(new ByteArrayInputStream(buf),
					"text/calendar;method=REQUEST;charset=\"UTF-8\"")));
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
