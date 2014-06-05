/*
 * Copyright 2012 SWM Services GmbH.
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
package de.swm.gwt.client.theme.components;

import com.google.gwt.resources.client.CssResource;


/**
 * Represents a css resource for different kinds of error messages.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-14, SWM Services GmbH
 */
public interface DatePickerCss extends CssResource {


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("gwt-DatePicker")
	public String gwtDatePicker();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDay")
	public String datePickerDay();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerWeekendLabel")
	public String datePickerWeekendLabel();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerMonthSelector")
	public String datePickerMonthSelector();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("gwt-DateBox")
	public String gwtDateBox();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDays")
	public String datePickerDays();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDayIsWeekend")
	public String datePickerDayIsWeekend();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDayIsFiller")
	public String datePickerDayIsFiller();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDayIsSelected")
	public String datePickerDayIsSelected();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDayIsDisabled")
	public String datePickerDayIsDisabled();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerDayIsHighlighted")
	public String datePickerDayIsHighlighted();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerMonth")
	public String datePickerMonth();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerPreviousButton")
	public String datePickerPreviousButton();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("datePickerNextButton")
	public String datePickerNextButton();

}
