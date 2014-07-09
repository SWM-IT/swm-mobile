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

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HasValue;
import de.swm.commons.mobile.client.widgets.date.DatePopup.DateSelectionHandler;
import de.swm.commons.mobile.client.widgets.PanelBase;
import de.swm.commons.mobile.client.widgets.TextBox;

import java.util.Date;


/**
 * Defines a Date selection box.
 * <p/>
 * <p/>
 * {@link Deprecated} use {@link DatePopup} instead, not compatible with iOS5.
 */
public class DateTextBox extends PanelBase implements HasValue<Date>, IsEditor<TakesValueEditor<Date>>,
		HasBlurHandlers, HasFocusHandlers {

	/**
	 * inner text box to display the formated date value.
	 */
	private final TextBox dateDisplayBox;

	/**
	 * current date value.
	 */
	private Date currentValue;

	/**
	 * marks if the was chenged by user.
	 */
	private boolean changedByUser;

	/**
	 * date popup.
	 */
	private DatePopup datePopup;

	/**
	 * Current date foematter. See {@link DateStyle#getFormatPattern()}.
	 */
	private final DateTimeFormat dateFormatter;


	/**
	 * Default constructor.
	 */
	public DateTextBox() {
		this(DateStyle.DATETIME);
	}


	/**
	 * Alternative UI constructor.
	 *
	 * @param dateStyle the date style as defined in {@link DateStyle}
	 */
	@UiConstructor
	public DateTextBox(final DateStyle dateStyle) {
		changedByUser = false;
		this.dateDisplayBox = new TextBox();
		this.dateFormatter = DateTimeFormat.getFormat(dateStyle.getFormatPattern());
		this.addClickHandler(dateStyle);
		myFlowPanel.add(this.dateDisplayBox);
	}

	/**
	 * Adds the click handler to the textfield to open the {@link DatePopup}.
	 *
	 * @param dateStyle current date style
	 */
	private void addClickHandler(final DateStyle dateStyle) {

		this.dateDisplayBox.setReadOnly(true);
		this.dateDisplayBox.addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						datePopup = new DatePopup(getValue(), dateStyle, new DateSelectionHandler() {

							@Override
							public void dateSelectionCancelled() {
								// nothing to do
							}

							@Override
							public void dateSelected(Date selectedDate) {
								setValue(selectedDate, true);
								changedByUser = true;
							}
						});
						datePopup.showCentered(true);
					}
				});
	}


	@Override
	public TakesValueEditor<Date> asEditor() {
		return TakesValueEditor.of(this);
	}

	@Override
	public Date getValue() {
		return currentValue;
	}

	@Override
	public void setValue(Date value) {
		this.setValue(value, false);
	}

	@Override
	public void setValue(final Date value, boolean fireEvents) {
		this.changedByUser = false;

		Date oldValue = this.currentValue == null ? null : (Date) this.currentValue.clone();
		this.currentValue = value;
		this.setAsTextValue(value);
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, oldValue, this.currentValue);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> dateValueChangeHandler) {
		return this.addHandler(dateValueChangeHandler, ValueChangeEvent.getType());
	}

	/**
	 * puts the given date as text value into the {@link #dateDisplayBox}.
	 *
	 * @param value date
	 */
	private void setAsTextValue(Date value) {
		this.dateDisplayBox.setValue(value != null ? this.dateFormatter.format(value) : null);

	}


	/**
	 * Returns if the current date was changed by the user..
	 *
	 * @return <code>true</code> if the date was changed by the user, <code>false</code> otehrwise
	 */
	public boolean isChangedByUser() {
		return changedByUser;
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return this.dateDisplayBox.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return this.addFocusHandler(handler);
	}
}
