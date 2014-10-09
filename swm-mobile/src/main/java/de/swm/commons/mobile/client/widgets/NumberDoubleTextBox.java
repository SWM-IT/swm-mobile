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
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.text.shared.Parser;
import de.swm.commons.mobile.client.base.BoxBase;

import java.text.ParseException;



/**
 * Defines a box for double values.
 * 
 */
public class NumberDoubleTextBox extends BoxBase<Double> {
	
	private static class DoubleParser implements Parser<Double>{
		
		private static final DoubleParser INST = new DoubleParser();

		@Override
		public Double parse(CharSequence text) throws ParseException {
			if(text == null) {
				return null;
			}
			String str = text.toString();
			try{
				return Double.parseDouble(str.replace(",", "."));
			}catch(NumberFormatException e) {
				return null;
			}
		}
		
		public static DoubleParser instance()  {
			return INST;
		}
	}

	/**
	 * Default constructor.
	 */
	public NumberDoubleTextBox() {
		super("number", new ToStringRenderer<Double>(), DoubleParser.instance());
	}
}
