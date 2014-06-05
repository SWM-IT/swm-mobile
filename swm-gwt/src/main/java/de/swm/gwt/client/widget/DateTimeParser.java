package de.swm.gwt.client.widget;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.shared.Parser;

import java.text.ParseException;
import java.util.Date;

/**
 * TODO: aus SWM Mobile raus!
 * Implements regarding to googles parser system for a date parser.
 */
public class DateTimeParser implements Parser<Date> {

	DateTimeFormat format;


	/**
	 * Default constructor.
	 *
	 * @param pattern date time pattern.
	 */
	public DateTimeParser(String pattern) {
		format = DateTimeFormat.getFormat(pattern);
	}


	@Override
	public Date parse(CharSequence text) throws ParseException {
		if (text == null || text.length() == 0) {
			return null;
		}
		return format.parse(text.toString());
	}

}