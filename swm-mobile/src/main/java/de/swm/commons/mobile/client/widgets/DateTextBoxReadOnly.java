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

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

import java.util.Date;


/**
 * Defines a read only Date box.
 * <p/>
 * <p/>
 */
public class DateTextBoxReadOnly extends PanelBase implements HasValue<Date>, IsEditor<TakesValueEditor<Date>>, HasValueChangeHandlers<Date> {

	private final DateTimeFormat dateFormatter;
	private final Label internLabelField;
	private Date value;

	@UiConstructor
	public DateTextBoxReadOnly(DateStyle dateStyle) {
		this.dateFormatter = DateTimeFormat.getFormat(dateStyle.getFormatPattern());
		this.internLabelField = new Label();
		myFlowPanel.add(this.internLabelField);
	}

	@Override
	public Date getValue() {
		return this.value;
	}

	@Override
	public void setValue(Date value) {
		this.value = value;
		this.internLabelField.setText(value != null ? this.dateFormatter.format(value) : null);
	}

	@Override
	public void setValue(Date newValue, boolean fireEvents) {
		Date oldValue = this.value == null ? null : (Date) this.value.clone();
		this.setValue(newValue);
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> dateValueChangeHandler) {
		return this.addHandler(dateValueChangeHandler, ValueChangeEvent.getType());
	}

	@Override
	public TakesValueEditor<Date> asEditor() {
		return TakesValueEditor.of(this);
	}
}
