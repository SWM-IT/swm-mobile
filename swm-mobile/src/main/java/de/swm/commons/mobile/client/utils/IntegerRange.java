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
package de.swm.commons.mobile.client.utils;

/**
 * Utility class to represent a mutable range of integer values with an optional distinguished value within the
 * given range. The range may be specified with start and end values or with a given distinguished value and a span
 * which will cover the value symmetrically.
 * 
 * This class is suited to represent date intervals for {@link DateTextBox} Widget.
 * 

 *         
 * 
 */
public class IntegerRange {

	private static final int DEFAULT_SPAN = 20;

	private int span = -1;
	private int start = -1;
	private int end = -1;
	private int value = -1;



	/**
	 * Create an empty range with a default span of values.
	 */
	public IntegerRange() {
		this.span = DEFAULT_SPAN;
	}



	/**
	 * Create an integer range around the given value with a default span.
	 * 
	 * @param value
	 *            Value wich is used to center the range
	 */
	public IntegerRange(int value) {
		this(value, DEFAULT_SPAN);
	}



	/**
	 * Create an integer range around the given value with a given span.
	 * 
	 * @param value
	 *            Value wich is used to center the range
	 * @param span
	 *            Span which will cover the given value symmetrically
	 */
	public IntegerRange(int value, int span) {
		if (value < 0 || span < 0) {
			throw new IllegalArgumentException("Positive values expected");
		}
		this.value = value;
		this.span = span;
		update(true);
	}



	/**
	 * Create an integer range with the given start and end values. An optional distinguished value may be specified.
	 * 
	 * @param start
	 *            Lower bound of the range
	 * @param end
	 *            Upper bound of the range
	 * @param value
	 *            Optional distinguished value within the specified range (<code>-1</code> for unspecified value)
	 */
	public IntegerRange(int start, int end, int value) {
		if (start < 0 || end < 0) {
			throw new IllegalArgumentException("Positive values expected");
		}
		if (start > end) {
			throw new IllegalArgumentException("Start value must be lower than end value");
		}
		if (isSet(value) && !((start <= value) && (start <= end))) {
			throw new IllegalArgumentException("Given value must be between start and end value");
		}
		this.value = value;
		this.start = start;
		this.end = end;
		update(false);
	}



	/**
	 * Sets the distinguished value of the integer range. The range will be adjusted to include this value.
	 * 
	 * @param value
	 *            Distinguished value within the range (<code>-1</code> to reset the value)
	 */
	public void setValue(int value) {
		this.value = value;
		update(false);
	}



	/**
	 * Sets the lower and upper boundary of the range. The range will be adjusted to include the distinguished value if
	 * necessary.
	 * 
	 * @param start
	 *            Lower positive bound of the range
	 * @param end
	 *            Upper positive bound of the range
	 */
	public void setRange(int start, int end) {
		if (start < 0 || end < 0) {
			throw new IllegalArgumentException("Positive value expected");
		}
		if (start > end) {
			throw new IllegalArgumentException("Start value must be lower than end value");
		}
		this.start = start;
		this.end = end;
		update(false);
	}



	public int getStartValue() {
		return start;
	}



	public int getEndValue() {
		return end;
	}



	public int getSpan() {
		return span;
	}



	/**
	 * Sets the span of the range around the given distinguished value. This will adjust the lower and upper bound of
	 * the range to result in the given span.
	 * 
	 * @param span
	 *            Span of values which defines the range (<code>0</code> for a single element range)
	 */
	public void setSpan(int span) {
		if (span < 0) {
			throw new IllegalArgumentException("Positive value expected for span");
		}
		this.span = span;
		update(true);
	}



	/**
	 * Internal method to compute upper and lower boundary of the range as well as the span of values.
	 * 
	 * @param forceSpan
	 *            <code>true</code> if the span will dominate the calculation, <code>false</code> if start and end value
	 *            will remain fixed
	 */
	private void update(boolean forceSpan) {

		if (!isSet(start) || !isSet(end)) {
			// dates unset -> center around given value
			start = value - (span / 2);
			end = start + span;
		} else {
			// both dates set -> try to accomodate current value (if set)
			if (isSet(value)) {
				if (value < start) {
					// extend interval to the left
					start = value;
				} else if (end < value) {
					// extend interval to the right
					end = value;
				}
				if (!forceSpan) {
					span = end - start;
				} else {
					start = value - (span / 2);
					end = start + span;
				}
			} else {
				if (!forceSpan) {
					// update span
					span = end - start;
				} else {
					// update start and end values
					int temp = (span / 2) + 1;
					start = temp - (span / 2);
					end = start + span;
				}
			}
		}
	}



	/**
	 * Trie if the value is set.
	 * 
	 * @param value
	 *            the value.
	 * @return true if set.
	 */
	private boolean isSet(int value) {
		return value >= 0;
	}

}
