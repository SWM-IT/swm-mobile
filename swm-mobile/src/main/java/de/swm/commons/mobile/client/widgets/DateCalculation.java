/**
 * SWM Services GmbH 2012.
 */
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;


/**
 * Delegate for Date Calculations. E.g. handles hour, month and year overflow.
 *
 * @author Ch. Kutschke<br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public class DateCalculation {

	// Date and Time constants
	private static final int HOURS_PER_DAY_MINUS_ONE_HOUR = 23;
	private static final int MINUTES_PER_HOUR_MINUS_ONE_SEC = 59;
	private static final int LEAP_YEAR_FREQUENCE = 4;
	private static final int FEBRUARY_STANDARD_YEAR = 28;
	private static final int FEBRUARY_LEAP_YEAR = 29;
	private static final int MONTH_WITH_30_DAYS = 30;
	private static final int MONTH_WITH_31_DAYS = 31;
	private static final int NO_OF_MONTHS = 12;
	private static final int CENTURY_1900 = 1900;
	private static final int MONTH_JANUARY = 0;
	private static final int MONTH_FEBRUARY = 1;
	private static final int MONTH_MARCH = 2;
	private static final int MONTH_APRIL = 3;
	private static final int MONTH_MAY = 4;
	private static final int MONTH_JUNE = 5;
	private static final int MONTH_JULY = 6;
	private static final int MONTH_AUGUST = 7;
	private static final int MONTH_SEPTEMBER = 8;
	private static final int MONTH_OCTOBER = 9;
	private static final int MONTH_NOVEMBER = 10;
	private static final int MONTH_DECEMBER = 11;

	/**
	 * Holds all months with their number of days.
	 */
	private int[] month2day;

	/**
	 * The current day.
	 */
	private int day = 0;

	/**
	 * The current month.
	 */
	private int month = 0;

	/**
	 * The current year.
	 */
	private int year = 0;

	/**
	 * The current hour.
	 */
	private int hour = 0;

	/**
	 * The current minute.
	 */
	private int minute = 0;


	/**
	 * Constructor that sets a given date as internal state.
	 *
	 * @param givenDate the date to set
	 */
	@SuppressWarnings("deprecation")
	public DateCalculation(Date givenDate) {

		// unfortunately only way in GWT
		day = givenDate.getDate();
		month = givenDate.getMonth() + 1;
		year = givenDate.getYear() + CENTURY_1900;
		hour = givenDate.getHours();
		minute = givenDate.getMinutes();

		// initialize constants
		month2day = new int[NO_OF_MONTHS];
		month2day[MONTH_JANUARY] = MONTH_WITH_31_DAYS;
		// leap year?
		month2day[MONTH_FEBRUARY] = (year % LEAP_YEAR_FREQUENCE == 0) ? FEBRUARY_LEAP_YEAR : FEBRUARY_STANDARD_YEAR;
		month2day[MONTH_MARCH] = MONTH_WITH_31_DAYS;
		month2day[MONTH_APRIL] = MONTH_WITH_30_DAYS;
		month2day[MONTH_MAY] = MONTH_WITH_31_DAYS;
		month2day[MONTH_JUNE] = MONTH_WITH_30_DAYS;
		month2day[MONTH_JULY] = MONTH_WITH_31_DAYS;
		month2day[MONTH_AUGUST] = MONTH_WITH_31_DAYS;
		month2day[MONTH_SEPTEMBER] = MONTH_WITH_30_DAYS;
		month2day[MONTH_OCTOBER] = MONTH_WITH_31_DAYS;
		month2day[MONTH_NOVEMBER] = MONTH_WITH_30_DAYS;
		month2day[MONTH_DECEMBER] = MONTH_WITH_31_DAYS;
	}


	/**
	 * Formats a date according to Rfc3339 spezification
	 *
	 * @param date the date
	 * @return the Rfc3339 string
	 */
	public String formatToRfc3339Timestamp(Date date, boolean isLocalTime) {
		DateStyle defaultDateStyle = DateStyle.DATETIME;
		final DateTimeFormat format = (isLocalTime) ?
				DateTimeFormat.getFormat(defaultDateStyle.getFormatPatternRfc3339Local())
				: DateTimeFormat.getFormat(defaultDateStyle.getFormatPatternRfc3339());
		return format.format(date);
	}


	/**
	 * Formats a date according to Rfc3339 spezification
	 *
	 * @param date the date
	 * @return the Rfc3339 string
	 */
	public String formatToRfc3339(Date date, DateStyle dateStyle, boolean isLocalTime) {
		if (date == null) {
			return "";
		}

		final DateTimeFormat format = (isLocalTime) ? DateTimeFormat.getFormat(dateStyle.getFormatPatternRfc3339Local())
				: DateTimeFormat.getFormat(dateStyle.getFormatPatternRfc3339());
		String format1 = format.format(date);
		return format1;
	}

	/**
	 * Will pase a String in Rfc3339 spcification to an Date as timestamp.
	 *
	 * @param pattern the patternD
	 * @return the date
	 */
	public Date parseRfc3339Timestamp(final String pattern, boolean isLocalTime) {
		if (pattern == null) {
			return null;
		}
		try {
			DateStyle defaultDateStyle = DateStyle.DATETIME;

			String dateString = this.prepareDateString(pattern, defaultDateStyle);

			DateTimeFormat format = (isLocalTime) ?
					DateTimeFormat.getFormat(defaultDateStyle.getFormatPatternRfc3339Local())
					: DateTimeFormat.getFormat(defaultDateStyle.getFormatPatternRfc3339());

			return format.parse(dateString);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}


	/**
	 * Will pase a String in Rfc3339 spcification to an Date according teh DateStyle.
	 *
	 * @param pattern the pattern
	 * @return the date
	 */
	public Date parseRfc3339(String pattern, DateStyle dateStyle, boolean isLocalTime) {
		if (pattern == null) {
			return null;
		}
		try {
			String dateString = this.prepareDateString(pattern, dateStyle);

			final DateTimeFormat format = (isLocalTime) ? DateTimeFormat.getFormat(dateStyle.getParsePatternRfc3339Local())
					: DateTimeFormat.getFormat(dateStyle.getParsePatternRfc3339());
			return format.parse(dateString);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * Convert internal state to a Date.
	 *
	 * @return the state as Date
	 */
	@SuppressWarnings("deprecation")
	public Date getDate() {
		return new Date(year - CENTURY_1900, month - 1, day, hour, minute);
	}


	/**
	 * Sets manually the day.
	 *
	 * @param day the day
	 */
	public void setDay(String day) {
		if (day != null) {
			try {
				this.day = Integer.valueOf(day);
				decrementDay();
				incrementDay();
			} catch (NumberFormatException ex) {

			}
		}
	}


	/**
	 * Sets manually the day.
	 *
	 * @param month the month
	 */
	public void setMonth(String month) {
		if (month != null) {
			try {
				this.month = Integer.valueOf(month);
				decrementMonth();
				incrementMonth();
			} catch (NumberFormatException ex) {

			}
		}
	}


	/**
	 * Sets manually the day.
	 *
	 * @param year the year
	 */
	public void setYear(String year) {
		if (year != null) {
			try {
				this.year = Integer.valueOf(year);
				decrementYear();
				incrementYear();
			} catch (NumberFormatException ex) {

			}
		}
	}


	/**
	 * Sets manually the day.
	 *
	 * @param hour the hour
	 */
	public void setHour(String hour) {
		if (hour != null) {
			try {
				this.hour = Integer.valueOf(hour);
				decrementHour();
				incrementHour();
			} catch (NumberFormatException ex) {

			}
		}
	}


	/**
	 * Sets manually the day.
	 *
	 * @param minute the minute
	 */
	public void setMinute(String minute) {
		if (minute != null) {
			try {
				this.minute = Integer.valueOf(minute);
				decrementMinute();
				incrementMinute();
			} catch (NumberFormatException ex) {

			}
		}
	}


	/**
	 * Increments a day. Handles overflow.
	 */
	public void incrementDay() {
		int newDay = day + 1;
		if (newDay > month2day[month - 1]) {
			day = 1;
			incrementMonth();
		} else {
			day = newDay;
		}

	}


	/**
	 * Decrements a day. Handles overflow.
	 */
	public void decrementDay() {
		int newDay = day - 1;
		if (newDay < 1) {
			if (month > MONTH_FEBRUARY) {
				day = month2day[month - 2];
			} else {
				day = month2day[MONTH_DECEMBER];
			}
			decrementMonth();
		} else {
			day = newDay;
		}
	}


	/**
	 * Increments a month. Handles overflow.
	 */
	public void incrementMonth() {
		int newMonth = month + 1;
		if (newMonth > MONTH_DECEMBER + 1) {
			month = 1;
			incrementYear();
		} else {
			month = newMonth;
		}
		adjustDaysInMonth();
	}


	/**
	 * Decrements a month. Handles overflow.
	 */
	public void decrementMonth() {
		int newMonth = month - 1;
		if (newMonth < 1) {
			month = MONTH_DECEMBER + 1;
			decrementYear();
		} else {
			month = newMonth;
		}
		adjustDaysInMonth();
	}


	/**
	 * Adjust days in a months.
	 */
	public void adjustDaysInMonth() {
		int fixedDay = Math.min(month2day[month - 1], day);
		if (fixedDay != day) {
			day = fixedDay;
		}
	}


	/**
	 * Increments a year. Handles leap years.
	 */
	public void incrementYear() {
		year++;
		adjustLeapYear();
	}


	/**
	 * Decrements a year. Handles leap years.
	 */
	public void decrementYear() {
		year--;
		adjustLeapYear();
	}


	/**
	 * Adjusts a leap year. Handles overflow.
	 */
	private void adjustLeapYear() {
		int daysInFebruary = (year % LEAP_YEAR_FREQUENCE == 0) ? FEBRUARY_LEAP_YEAR : FEBRUARY_STANDARD_YEAR;
		month2day[1] = daysInFebruary;
		if (month == 1) {
			adjustDaysInMonth();
		}
	}


	/**
	 * Increments a minute. Handles overflow.
	 */
	public void incrementMinute() {
		int newMinute = minute + 1;
		if (newMinute > MINUTES_PER_HOUR_MINUS_ONE_SEC) {
			minute = 0;
			incrementHour();
		} else {
			minute = newMinute;
		}

	}


	/**
	 * Decrements a minute. Handles overflow.
	 */
	public void decrementMinute() {
		int newMinute = minute - 1;
		if (newMinute < 0) {
			minute = MINUTES_PER_HOUR_MINUS_ONE_SEC;
			decrementHour();
		} else {
			minute = newMinute;
		}

	}


	/**
	 * Increments a hour. Handles overflow.
	 */
	public void incrementHour() {
		int newHour = hour + 1;
		if (newHour > HOURS_PER_DAY_MINUS_ONE_HOUR) {
			hour = 0;
			incrementDay();
		} else {
			hour = newHour;
		}

	}


	/**
	 * Decrements a hour. Handles overflow.
	 */
	public void decrementHour() {
		int newHour = hour - 1;
		if (newHour < 0) {
			hour = HOURS_PER_DAY_MINUS_ONE_HOUR;
			decrementDay();
		} else {
			hour = newHour;
		}
	}


	/**
	 * Gibt day zurück.
	 *
	 * @return day
	 */
	public String getDay() {
		return String.valueOf(day);
	}


	/**
	 * Gibt month zurück.
	 *
	 * @return month
	 */
	public String getMonth() {
		return String.valueOf(month);
	}


	/**
	 * Gibt year zurück.
	 *
	 * @return year
	 */
	public String getYear() {
		return String.valueOf(year);
	}


	/**
	 * Gibt hour zurück.
	 *
	 * @return hour
	 */
	public int getHour() {
		return hour;
	}


	/**
	 * Gibt minute zurück.
	 *
	 * @return minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * Prepares the Date String for prsing.
	 *
	 * @param originDataString origin date string
	 * @return prepared date string
	 * @see DateStyle
	 */
	private String prepareDateString(final String originDataString, final DateStyle dateStyle) {
		String dateString = null;
		if (DateStyle.DATETIME.equals(dateStyle) && originDataString.indexOf('.') < 0) {
			// miss milliseconds
			dateString = originDataString + ".00";
		} else if (DateStyle.TIME.equals(dateStyle) && originDataString.indexOf(".") < 0) {
			// miss seconds and milliseconds
			dateString = originDataString + ":00.00";
		} else {
			dateString = originDataString;
		}
		return dateString;
	}
}
