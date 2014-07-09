/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets.date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.shared.Parser;

import java.text.ParseException;
import java.util.Date;


/**
 * Implements regarding to googles parser system for a date parser.
 */
public class DateTimeParser implements Parser<Date> {

	final DateTimeFormat format;


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
