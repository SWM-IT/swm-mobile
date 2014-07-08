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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.itf.IProvidesKeyAndValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Container for a group of {@link de.swm.commons.mobile.client.widgets.RadioButton}s.
 * Supports i18n-Enums as source for the radio button options.
 */
public class GenericRadioButtonGroup<T> extends CheckBoxGroup implements HasConstrainedValue<T>,
		IsEditor<TakesValueEditor<T>>, HasEnabled {

	private static final Logger LOGGER = Logger.getLogger(GenericRadioButtonGroup.class.getName());


	/**
	 * Name for the button group.
	 */
	private String myName = null;

	private final Map<Integer, String> indexKeyMap = new HashMap<Integer, String>();

	private final Map<String, Integer> keyIndexMap = new HashMap<String, Integer>();

	private IProvidesKeyAndValue<T> keyValueProvider;

	private Renderer<T> renderer;

	private TakesValueEditor<T> editor;

	private int lastIndex = 0;

	private T currentValue;

	private boolean enabled = true;

	/**
	 * Default constructor.
	 */
	public GenericRadioButtonGroup() {
		super();
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().radioButtonGroup());
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().vertical());
	}

	@Override
	public void onInitialLoad() {
		super.onInitialLoad();
		assert myName != null : "Attribute 'name' must be set on RadioButtonGroup";
	}

	@Override
	public void onClick(ClickEvent e) {
		if (!enabled) {
			return;
		}
		EventTarget target = e.getNativeEvent().getEventTarget();
		String targetTagName = ((Element) target.cast()).getTagName().toUpperCase();
		if (targetTagName.equals("SPAN")) {
			int before = getCheckedIndex();
			super.onClick(e);
			int after = getCheckedIndex();
			LOGGER.info("before " + before + " after " + after);
			if (after == -1) {
				RadioButton radio = (RadioButton) getWidget(before);
				if (!radio.getValue()) {
					// cannot un-select a radio button without selecting another one.
					radio.setValue(true);
				}
			} else if (before > -1) {
				RadioButton radio = (RadioButton) getWidget(before);
				radio.setValue(false);
			}
		} else if (targetTagName.equals("INPUT")) {
			super.onClick(e);
			for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
				RadioButton radio = (RadioButton) getWidget(i);
				radio.setValue(radio.getValue());
			}
		}
	}

	/**
	 * The widget index of first checked radio button.
	 *
	 * @return index
	 */
	public int getCheckedIndex() {
		for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
			RadioButton radio = (RadioButton) myFlowPanel.getWidget(i);
			if (radio.getValue()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Will return the first checked radio button or null if no {@link de.swm.commons.mobile.client.widgets.RadioButton} was checked.
	 *
	 * @return the checked widget
	 */
	public RadioButton getCheckedWidget() {
		int i = getCheckedIndex();
		if (i > -1) {
			return (RadioButton) getWidget(i);
		}
		return null;
	}

	@Override
	public void add(Widget w) {
		assert w instanceof RadioButton : "Can only contain RadioButton widgets in RadioButtonGroup";
		RadioButton radio = (RadioButton) w;

		if (keyValueProvider != null) {
			if (this.keyValueProvider.getValue(radio.getFormValue()) != null) {
				T enumValue = this.keyValueProvider.getValue(radio.getFormValue());
				this.keyIndexMap.put(radio.getFormValue(), lastIndex);
				this.indexKeyMap.put(lastIndex, radio.getFormValue());
				radio.setText(renderer.render(enumValue));
				myFlowPanel.add(radio);
				lastIndex++;
			}
		} else {
			myFlowPanel.add(radio);
		}
		if (myName != null) {
			radio.setName(myName);
		}
		radio.addValueChangeHandler(this);
	}

	/**
	 * Sets the name of the slideUpPanel.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		myName = name;
		for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
			RadioButton radio = (RadioButton) myFlowPanel.getWidget(i);
			radio.setName(myName);
		}
	}

	/**
	 * Return the name of this button group.
	 *
	 * @return the name
	 */
	public String getName() {
		return myName;
	}

	@Override
	public void setAcceptableValues(Collection<T> values) {
		// intentionally left empty
	}

	@Override
	public T getValue() {
		int index = getCheckedIndex();
		if (index >= 0) {
			if (keyValueProvider != null) {
				return this.keyValueProvider.getValue(this.indexKeyMap.get(index));
			} else {
				return (T) getCheckedWidget().getFormValue();
			}
		}
		return null;
	}

	@Override
	public void setValue(T value) {
		Integer indexToSelect;
		if (keyValueProvider != null) {
			indexToSelect = this.keyIndexMap.get(this.keyValueProvider.getKey(value));
			if (indexToSelect == null) {
				// index not found - reset all
				indexToSelect = -1;
			} else {
				// set selected radio button
				((RadioButton) myFlowPanel.getWidget(indexToSelect)).setValue(true);
			}
			for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
				// reset the rest
				if (i != indexToSelect) {
					((RadioButton) myFlowPanel.getWidget(i)).setValue(false);
				}
			}
		} else {
			// support String as Type T (shortcut)
			String stringValue = (String) value;
			for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
				RadioButton radio = (RadioButton) myFlowPanel.getWidget(i);
				if (radio.getFormValue().equals(stringValue)) {
					radio.setValue(true);
					break;
				}
			}
		}
		this.currentValue = value;
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		final T oldValue = currentValue;
		setValue(value);
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	/**
	 * KyValueProvider: the keyValueProvider to set.
	 *
	 * @param keyValueProvider the keyValueProvider to set
	 */
	public void setKeyValueProvider(IProvidesKeyAndValue<T> keyValueProvider) {
		this.keyValueProvider = keyValueProvider;
	}

	/**
	 * Renderer: the renderer to set.
	 *
	 * @param renderer the renderer to set
	 */
	public void setRenderer(Renderer<T> renderer) {
		this.renderer = renderer;
	}

	@Override
	public TakesValueEditor<T> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
			((RadioButton) myFlowPanel.getWidget(i)).setEnabled(enabled);
		}
	}
}
