package de.swm.commons.mobile.client.widgets.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * TODO Dokumentieren.
 *
 * @author steuer.konstantin
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class DateStyleTest {

	@Test
	public void testFormatDatePattern() throws Exception {
		Date dateToFormat = this.getDefinedDate();
		SimpleDateFormat smd = new SimpleDateFormat(DateStyle.DATETIME.getFormatPattern());

		assertEquals("16.04.2012 10:52", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.DATE.getFormatPattern());
		assertEquals("16. April 2012", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.TIME.getFormatPattern());
		assertEquals("10:52", smd.format(dateToFormat));
	}

	@Test
	public void testParseDatePattern() throws Exception {
		Calendar expectedCalendarTime = new GregorianCalendar();
		expectedCalendarTime.setTime(this.getDefinedDate());

		SimpleDateFormat smdRead = new SimpleDateFormat(DateStyle.DATETIME.getParsePattern());
		expectedCalendarTime.set(Calendar.MILLISECOND, 0);
		expectedCalendarTime.set(Calendar.SECOND, 0);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("16.04.2012 10:52"));

		smdRead = new SimpleDateFormat(DateStyle.DATE.getParsePattern());
		expectedCalendarTime.set(Calendar.MINUTE, 0);
		expectedCalendarTime.set(Calendar.HOUR, 0);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("16. April 2012"));

		smdRead = new SimpleDateFormat(DateStyle.TIME.getParsePattern());
		expectedCalendarTime.setTime(this.getDefinedDate());
		expectedCalendarTime.set(Calendar.YEAR, 1970);
		expectedCalendarTime.set(Calendar.MONTH, 0);
		expectedCalendarTime.set(Calendar.DAY_OF_YEAR, 1);
		expectedCalendarTime.set(Calendar.MILLISECOND, 0);
		expectedCalendarTime.set(Calendar.SECOND, 0);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("10:52"));
	}


	@Test
	public void testFormatDatePatternRfc3339() throws Exception {
		Date dateToFormat = this.getDefinedDate();
		SimpleDateFormat smd = new SimpleDateFormat(DateStyle.DATETIME.getFormatPatternRfc3339());

		assertEquals("2012-04-16T10:52:05.65Z", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.DATE.getFormatPatternRfc3339());
		assertEquals("2012-04-16", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.TIME.getFormatPatternRfc3339());
		assertEquals("10:52:05.65Z", smd.format(dateToFormat));
	}

	@Test
	public void testFormatDatePatternRfc3339Local() throws Exception {
		Date dateToFormat = this.getDefinedDate();
		SimpleDateFormat smd = new SimpleDateFormat(DateStyle.DATETIME.getFormatPatternRfc3339Local());

		assertEquals("2012-04-16T10:52:05.65", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.DATE.getFormatPatternRfc3339Local());
		assertEquals("2012-04-16", smd.format(dateToFormat));

		smd = new SimpleDateFormat(DateStyle.TIME.getFormatPatternRfc3339Local());
		assertEquals("10:52:05.65", smd.format(dateToFormat));
	}


	@Test
	public void testParseDatePatternRfc3339() throws Exception {
		Calendar expectedCalendarTime = new GregorianCalendar();
		expectedCalendarTime.setTime(this.getDefinedDate());

		SimpleDateFormat smdRead = new SimpleDateFormat(DateStyle.DATETIME.getParsePatternRfc3339());
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("2012-04-16T10:52:05.65Z"));

		smdRead = new SimpleDateFormat(DateStyle.DATE.getParsePatternRfc3339());
		expectedCalendarTime.set(Calendar.MILLISECOND, 0);
		expectedCalendarTime.set(Calendar.SECOND, 0);
		expectedCalendarTime.set(Calendar.MINUTE, 0);
		expectedCalendarTime.set(Calendar.HOUR, 0);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("2012-04-16T10:52:05.65Z"));

		smdRead = new SimpleDateFormat(DateStyle.TIME.getParsePatternRfc3339());
		expectedCalendarTime.setTime(this.getDefinedDate());
		expectedCalendarTime.set(Calendar.YEAR, 1970);
		expectedCalendarTime.set(Calendar.MONTH, 0);
		expectedCalendarTime.set(Calendar.DAY_OF_YEAR, 1);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("10:52:05.65Z"));
	}

	@Test
	public void testParseDatePatternRfc3339Local() throws Exception {
		Calendar expectedCalendarTime = new GregorianCalendar();
		expectedCalendarTime.setTime(this.getDefinedDate());

		SimpleDateFormat smdRead = new SimpleDateFormat(DateStyle.DATETIME.getParsePatternRfc3339Local());
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("2012-04-16T10:52:05.65"));

		smdRead = new SimpleDateFormat(DateStyle.DATE.getParsePatternRfc3339Local());
		expectedCalendarTime.set(Calendar.MILLISECOND, 0);
		expectedCalendarTime.set(Calendar.SECOND, 0);
		expectedCalendarTime.set(Calendar.MINUTE, 0);
		expectedCalendarTime.set(Calendar.HOUR, 0);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("2012-04-16T10:52:05.65"));

		smdRead = new SimpleDateFormat(DateStyle.TIME.getParsePatternRfc3339Local());
		expectedCalendarTime.setTime(this.getDefinedDate());
		expectedCalendarTime.set(Calendar.YEAR, 1970);
		expectedCalendarTime.set(Calendar.MONTH, 0);
		expectedCalendarTime.set(Calendar.DAY_OF_YEAR, 1);
		assertEquals(expectedCalendarTime.getTime(), smdRead.parse("10:52:05.65"));
	}


	/**
	 * Returns defined Date 2012-04-16 10:52:05.065.
	 *
	 * @return defined day.
	 */
	private Date getDefinedDate() {
		GregorianCalendar calendar = new GregorianCalendar(2012, 03, 16, 10, 52, 05);
		calendar.set(Calendar.MILLISECOND, 65);
		return calendar.getTime();
	}
}
