package com.gdxsoft.web.message.email;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class OutlookTest {

	private static String invokeEscapeIcsText(String s) throws Exception {
		Method m = Outlook.class.getDeclaredMethod("escapeIcsText", String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, s);
	}

	private static String invokeEscapeIcsParam(String s) throws Exception {
		Method m = Outlook.class.getDeclaredMethod("escapeIcsParam", String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, s);
	}

	// ---- escapeIcsText ----

	@Test
	void escapeIcsText_preservesPlainText() throws Exception {
		assertEquals("Hello World", invokeEscapeIcsText("Hello World"));
	}

	@Test
	void escapeIcsText_escapesBackslash() throws Exception {
		assertEquals("C:\\\\Users\\\\test", invokeEscapeIcsText("C:\\Users\\test"));
	}

	@Test
	void escapeIcsText_escapesNewline() throws Exception {
		assertEquals("line1\\nline2", invokeEscapeIcsText("line1\nline2"));
	}

	@Test
	void escapeIcsText_escapesCrLf() throws Exception {
		assertEquals("line1\\nline2", invokeEscapeIcsText("line1\r\nline2"));
	}

	@Test
	void escapeIcsText_nullReturnsEmpty() throws Exception {
		assertEquals("", invokeEscapeIcsText(null));
	}

	// ---- escapeIcsParam ----

	@Test
	void escapeIcsParam_escapesQuote() throws Exception {
		assertEquals("He's there", invokeEscapeIcsParam("He\"s there"));
	}

	@Test
	void escapeIcsParam_nullReturnsEmpty() throws Exception {
		assertEquals("", invokeEscapeIcsParam(null));
	}

	// ---- attachBinaryAttachment ----

	@Test
	void attachBinaryAttachment_producesValidIcs() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2026, 0, 15, 9, 0, 0);
		Date from = cal.getTime();
		cal.set(2026, 0, 15, 10, 0, 0);
		Date to = cal.getTime();

		byte[] ics = Outlook.attachBinaryAttachment(
				"项目评审", "讨论第一季度计划", "会议室 301",
				from, to, "user@test.com;admin@test.com");

		assertNotNull(ics);
		String content = new String(ics, StandardCharsets.UTF_8);

		assertTrue(content.contains("BEGIN:VCALENDAR"));
		assertTrue(content.contains("END:VCALENDAR"));
		assertTrue(content.contains("VERSION:2.0"));
		assertTrue(content.contains("METHOD:REQUEST"));
		assertTrue(content.contains("BEGIN:VEVENT"));
		assertTrue(content.contains("END:VEVENT"));
		assertTrue(content.contains("DTSTART;TZID=Asia/Shanghai:20260115T090000"));
		assertTrue(content.contains("DTEND;TZID=Asia/Shanghai:20260115T100000"));
		assertTrue(content.contains("LOCATION:会议室 301"));
		assertTrue(content.contains("ORGANIZER;CN=郭磊:mailto:lei.guo@gyap.org"));
		assertTrue(content.contains("ATTENDEE;ROLE=OPT-PARTICIPANT:mailto:user@test.com"));
		assertTrue(content.contains("ATTENDEE;ROLE=OPT-PARTICIPANT:mailto:admin@test.com"));
		assertTrue(content.contains("BEGIN:VALARM"));
		assertTrue(content.contains("TRIGGER:-PT15M"));
		assertTrue(content.contains("ACTION:DISPLAY"));
		assertTrue(content.contains("BEGIN:VTIMEZONE"));
	}

	@Test
	void attachBinaryAttachment_hasUid() throws Exception {
		Date now = new Date();
		byte[] ics1 = Outlook.attachBinaryAttachment("S", "C", "L", now, now, "a@b.com");
		byte[] ics2 = Outlook.attachBinaryAttachment("S", "C", "L", now, now, "a@b.com");

		String c1 = new String(ics1, StandardCharsets.UTF_8);
		String c2 = new String(ics2, StandardCharsets.UTF_8);

		// 两次应生成不同 UID
		String uid1 = extractLine(c1, "UID:");
		String uid2 = extractLine(c2, "UID:");
		assertFalse(uid1.equals(uid2));
	}

	@Test
	void attachBinaryAttachment_handlesSpecialChars() throws Exception {
		Date now = new Date();
		byte[] ics = Outlook.attachBinaryAttachment(
				"主\\题", "内\n容", "地;点",
				now, now, "a@b.com");

		String content = new String(ics, StandardCharsets.UTF_8);
		// 反斜杠被转义
		assertTrue(content.contains("主\\\\题"));
		// 换行被转义
		assertTrue(content.contains("内\\n容"));
	}

	@Test
	void attachBinaryAttachment_emptyTo_skipped() throws Exception {
		Date now = new Date();
		byte[] ics = Outlook.attachBinaryAttachment("S", "C", "L", now, now, "a@b.com;;c@d.com");

		String content = new String(ics, StandardCharsets.UTF_8);
		// 空收件人不生成 ATTENDEE
		assertTrue(content.contains("mailto:a@b.com"));
		assertTrue(content.contains("mailto:c@d.com"));
		long attendeeCount = content.lines().filter(l -> l.startsWith("ATTENDEE")).count();
		assertTrue(attendeeCount == 2, "should have exactly 2 attendees");
	}

	private static String extractLine(String content, String prefix) {
		return content.lines()
				.filter(l -> l.startsWith(prefix))
				.findFirst()
				.orElse("");
	}

	static void assertEquals(String expected, String actual) {
		org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
	}
}
