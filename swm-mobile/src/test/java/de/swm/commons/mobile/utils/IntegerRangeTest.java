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
package de.swm.commons.mobile.utils;

import de.swm.commons.mobile.client.utils.IntegerRange;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class IntegerRangeTest {

	@Test
	public void testSpan1() {
		IntegerRange d = new IntegerRange();
		assertEquals(20, d.getSpan());
		
		d.setRange(2000, 2010);
		assertEquals(10, d.getSpan());
		
		d.setRange(1990, 2020);
		assertEquals(30, d.getSpan());
				
		d = new IntegerRange();
		d.setSpan(10);
		assertEquals(10, d.getSpan());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSpan2() {
		int current = (new Date().getYear()) + 1900;
		IntegerRange d = new IntegerRange(current);
		assertEquals(current - (d.getSpan() / 2), d.getStartValue());
		assertEquals(d.getStartValue() + d.getSpan(), d.getEndValue());

		d = new IntegerRange(current);
		d.setSpan(0);
		assertEquals(current, d.getStartValue());
		assertEquals(current, d.getEndValue());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testWithValue() {
		IntegerRange d = new IntegerRange();
		Date value = new Date(70, 1, 1);
		d.setValue(value.getYear() + 1900);
		assertEquals(1970 - (d.getSpan() / 2), d.getStartValue());
		assertEquals(d.getStartValue() + d.getSpan(), d.getEndValue());
		
		d.setSpan(30);
		assertEquals(1955, d.getStartValue());
		assertEquals(1985, d.getEndValue());
	}
	
	@Test
	public void testWithAdaptedSpan() {
		IntegerRange d = new IntegerRange(50);
		d.setRange(1, 60);
		assertEquals(1, d.getStartValue());
		assertEquals(60, d.getEndValue());
		assertEquals(59, d.getSpan());
		
		d.setRange(1, 30);
		assertEquals(1, d.getStartValue());
		assertEquals(50, d.getEndValue());
		assertEquals(49, d.getSpan());

		d.setRange(60, 90);
		assertEquals(50, d.getStartValue());
		assertEquals(90, d.getEndValue());
		assertEquals(40, d.getSpan());
		
		d.setValue(-1);
		assertEquals(50, d.getStartValue());
		assertEquals(90, d.getEndValue());
		assertEquals(40, d.getSpan());
		
		d.setSpan(20);
		assertEquals(1, d.getStartValue());
		assertEquals(21, d.getEndValue());
		assertEquals(20, d.getSpan());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testArguments1() {
		@SuppressWarnings("unused")
		IntegerRange d = new IntegerRange(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testArguments2() {
		@SuppressWarnings("unused")
		IntegerRange d = new IntegerRange(1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testArguments3() {
		@SuppressWarnings("unused")
		IntegerRange d = new IntegerRange(-1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testArguments4() {
		@SuppressWarnings("unused")
		IntegerRange d = new IntegerRange(3, 2, 1);
	}

}
