/**
 * SWM Services GmbH 2012.
 */
package de.swm.commons.mobile.client.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Testsuite for {@link DateCalculation}. Frame of reference in date calculations is joda datetime API.
 *
 * @author Ch. Kutschke<br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public class DateCalculationTest {

	/**
	 * The testee DateCalculation.
	 */
	private DateCalculation testee;

	/**
	 * Used for example date parsing.
	 */
	private static final DateFormat ddMMyyyyHHmm = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.GERMANY);


	@Test
	public void testConstructorAndGetter() throws Exception {

		String day = "14";
		String month = "3";
		String year = "2012";
		int hour = 12;
		int minute = 34;

		Date testDate = ddMMyyyyHHmm.parse(day + "." + month + "." + year + " - " + hour + ":" + minute);
		testee = new DateCalculation(testDate);

		assertEquals("Derived date matches given date.", testDate, testee.getDate());
		assertEquals("Strings are formatted correctly.", day, testee.getDay());
		assertEquals("Strings are formatted correctly.", month, testee.getMonth());
		assertEquals("Strings are formatted correctly.", year, testee.getYear());
		assertEquals("Strings are formatted correctly.", hour, testee.getHour());
		assertEquals("Strings are formatted correctly.", minute, testee.getMinute());

	}


	@Test
	public void testIncrementDayRegularAndOverflow() throws Exception {

		Date testDate = ddMMyyyyHHmm.parse("27.01.2012 - 12:00");
		testee = new DateCalculation(testDate);

		DateTime dt = new DateTime(testDate.getTime());

		// regular inc
		testee.incrementDay();
		dt = dt.plusDays(1);
		assertEquals("Increased to 28. Jan.", dt.toDate(), testee.getDate());

		// inc till eom
		testee.incrementDay();
		testee.incrementDay();
		testee.incrementDay();
		dt = dt.plusDays(3);
		assertEquals("Increased to 31. Jan.", dt.toDate(), testee.getDate());

		// inc with overflow
		testee.incrementDay();
		dt = dt.plusDays(1);
		assertEquals("Increased to 1. Feb.", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementDayFebruaryRegularAndLeapYear() throws Exception {

		Date testDate = ddMMyyyyHHmm.parse("28.02.2011 - 12:00");
		testee = new DateCalculation(testDate);

		DateTime dt = new DateTime(testDate.getTime());

		// non leap year recognized
		testee.incrementDay();
		dt = dt.plusDays(1);
		assertEquals("Increased to 1. Mar.", dt.toDate(), testee.getDate());

		testDate = ddMMyyyyHHmm.parse("28.02.2012 - 12:00");
		testee = new DateCalculation(testDate);
		dt = new DateTime(testDate.getTime());

		// leap year recognized
		testee.incrementDay();
		dt = dt.plusDays(1);
		assertEquals("Increased to 29. Feb.", dt.toDate(), testee.getDate());

		// overflow
		testee.incrementDay();
		dt = dt.plusDays(1);
		assertEquals("Increased to 1. Mar.", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementMonthRegular() throws Exception {

		Date testDate = ddMMyyyyHHmm.parse("15.01.2011 - 12:00");
		testee = new DateCalculation(testDate);

		DateTime dt = new DateTime(testDate.getTime());

		// regular inc
		testee.incrementMonth();
		dt = dt.plusMonths(1);
		assertEquals("Increased to 15. Jan.", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementMonthOverflow() throws Exception {

		// year overflow
		Date testDate = ddMMyyyyHHmm.parse("15.12.2011 - 12:00");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMonth();
		dt = dt.plusMonths(1);
		assertEquals("Increased to 15. Jan. 2012", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementMonthEom() throws Exception {

		// eom
		Date testDate = ddMMyyyyHHmm.parse("31.03.2011 - 12:00");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMonth();
		dt = dt.plusMonths(1);
		assertEquals("Increased to 30. Apr.", dt.toDate(), testee.getDate());
		assertEquals("Increased to 30. Apr.", "30.04.2011 - 12:00", ddMMyyyyHHmm.format(testee.getDate()));

	}


	@Test
	public void testIncrementMonthFebLeapYear() throws Exception {

		// eom - February
		Date testDate = ddMMyyyyHHmm.parse("31.01.2011 - 12:00");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMonth();
		dt = dt.plusMonths(1);
		assertEquals("Increased to 28. Feb.", dt.toDate(), testee.getDate());
		assertEquals("Increased to 28. Feb.", "28.02.2011 - 12:00", ddMMyyyyHHmm.format(testee.getDate()));

	}


	@Test
	public void testIncrementMinute() throws Exception {

		// regular inc
		Date testDate = ddMMyyyyHHmm.parse("31.01.2011 - 12:34");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMinute();
		dt = dt.plusMinutes(1);
		assertEquals("Increased to 12:35.", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementMinuteOverflow() throws Exception {

		// overflow inc
		Date testDate = ddMMyyyyHHmm.parse("12.01.2011 - 12:59");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMinute();
		dt = dt.plusMinutes(1);
		assertEquals("Increased to 13:00.", dt.toDate(), testee.getDate());

	}


	@Test
	public void testIncrementMinuteEod() throws Exception {

		// eod inc
		Date testDate = ddMMyyyyHHmm.parse("12.01.2011 - 23:59");
		testee = new DateCalculation(testDate);
		DateTime dt = new DateTime(testDate.getTime());
		testee.incrementMinute();
		dt = dt.plusMinutes(1);
		assertEquals("Increased to 13.01.", dt.toDate(), testee.getDate());
		assertEquals("Increased to 13.01.", "13.01.2011 - 00:00", ddMMyyyyHHmm.format(testee.getDate()));

	}


}
