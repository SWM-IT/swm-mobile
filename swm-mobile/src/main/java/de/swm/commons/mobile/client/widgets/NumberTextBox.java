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

import java.text.ParseException;

import com.google.gwt.text.shared.Parser;


/**
 * Defines a box for integer values.
 * <br>
 */
public class NumberTextBox extends BoxBase<Integer> {
	
	private static class IntegerParser implements Parser<Integer>{
		
		private static final IntegerParser INST = new IntegerParser();

		@Override
		public Integer parse(CharSequence text) throws ParseException {
			if(text == null) {
				return null;
			}
			String str = text.toString();
			try{
				return Integer.parseInt(str);
			}catch(NumberFormatException e) {
				return null;
			}
		}
		
		public static IntegerParser instance()  {
			return INST;
		}
	}

	/**
	 * Default constructor.
	 */
	public NumberTextBox() {
	    super("number", new ToStringRenderer<Integer>(),
	            IntegerParser.instance());
	}	
}
