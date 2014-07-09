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
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;
import de.swm.commons.mobile.client.widgets.itf.IProvidesKeyAndValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Listbox that supports data binding and internationalization
 *
 * @param <T> the list box type
 */
public class DropDownList<T> extends PanelBase implements FocusHandler, BlurHandler, ChangeHandler,
		HasValueChangeHandlers<T>, IsEditor<TakesValueEditor<T>>, HasConstrainedValue<T>, HasFocusHandlers,
		HasBlurHandlers, HasEnabled {

	private final ListBox listBox = new ListBox();

	private final Map<Integer, String> indexKeyMap = new HashMap<Integer, String>();

	private final Map<String, Integer> keyIndexMap = new HashMap<String, Integer>();

	private TakesValueEditor<T> editor;

	private IProvidesKeyAndValue<T> keyValueProvider;

	private Renderer<T> renderer;

	private int lastIndex = 0;

	private T currentValue;

	/**
	 * Default constructor.
	 */
	public DropDownList() {
		super();
		myFlowPanel.add(listBox);
		if (!(SWMMobile.getOsDetection().isIOs())) {
			myFlowPanel.add(new HTMLPanel(""));
			myFlowPanel.add(new HTMLPanel(""));
			myFlowPanel.add(new HTMLPanel(""));
		}
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDropDownCss().dropDownList());
		listBox.addFocusHandler(this);
		listBox.addBlurHandler(this);
		listBox.addChangeHandler(this);
	}

	/**
	 * Clears the drop down list.
	 */
	public void clear() {
		indexKeyMap.clear();
		keyIndexMap.clear();

		lastIndex = 0;
		listBox.clear();
	}

	@Override
	protected void onUnload() {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDropDownCss().focus());
		super.onUnload();
	}

	@Override
	public void onFocus(FocusEvent event) {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDropDownCss().focus());
	}

	@Override
	public void onBlur(BlurEvent event) {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDropDownCss().focus());
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return listBox.addBlurHandler(handler);
	}

	public void clearSelection() {
		lastIndex = 0;
		new Timer() {
			@Override
			public void run() {
				listBox.setSelectedIndex(0);
			}
		}.schedule(100);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return listBox.addFocusHandler(handler);
	}

	@Override
	public void onChange(ChangeEvent event) {
		ValueChangeEvent.fire(this, getValue());
	}

	/**
	 * Sets the size of the Drop Down List
	 */
	public void setSize(String width, String height) {
		listBox.setSize(width, height);
	}

	/**
	 * keyValueProvider the keyValueProvider to set.
	 *
	 * @param keyValueProvider the keyValueProvider to set
	 */
	public void setKeyValueProvider(IProvidesKeyAndValue<T> keyValueProvider) {
		this.keyValueProvider = keyValueProvider;
	}

	/**
	 * renderer the renderer to set.
	 *
	 * @param renderer the renderer to set
	 */
	public void setRenderer(Renderer<T> renderer) {
		this.renderer = renderer;
	}

	@Override
	public void add(Widget w) {
		assert w instanceof DropDownItem : "Can only contain DropDownItem in DropDownList.";
		DropDownItem item = (DropDownItem) w;
		if (keyValueProvider != null) {
			// die idee ist, dass nur key's hinzgefuegt werden > das sind die auswahlbaren enum werte
			if (this.keyValueProvider.getValue(item.getKey()) != null) {
				T value = this.keyValueProvider.getValue(item.getKey());
				this.keyIndexMap.put(item.getKey(), lastIndex);
				this.indexKeyMap.put(lastIndex, item.getKey());
				listBox.addItem(this.renderer != null ? this.renderer.render(value) : String.valueOf(value), item.getKey());
				lastIndex++;
			}
		} else {
			// support String as Type T (shortcut)
			listBox.addItem(item.getText(), item.getKey());
		}
	}

	public ListBox getListBox() {
		return listBox;
	}

	/**
	 * Returns a {@link TakesValueEditor} backed by the ValueListBox.
	 *
	 * @return the value
	 */
	public TakesValueEditor<T> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue() {
		int index = listBox.getSelectedIndex();
		if (index >= 0) {
			if (keyValueProvider != null) {
				return this.keyValueProvider.getValue(this.indexKeyMap.get(index));
			} else {
				// support String as Type T (shortcut)
				return (T) listBox.getValue(index);
			}
		}
		return null;
	}

	@Override
	public void setValue(T value) {
		Integer indexToSelect = 0;
		if (keyValueProvider != null) {
			final String key = this.keyValueProvider.getKey(value);
			if (key != null) {
				indexToSelect = this.keyIndexMap.get(key);
			}
		} else {
			// support String as Type T (shortcut)
			String stringValue = (String) value;
			for (int i = 0; i < listBox.getItemCount(); i++) {
				if (listBox.getValue(i).equals(stringValue)) {
					indexToSelect = i;
					break;
				}
			}
		}
		listBox.setSelectedIndex(indexToSelect);
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

	/**
	 * Will register a value change handler
	 *
	 * @param handler the handler to add.
	 * @return the registration handle.
	 */
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setAcceptableValues(Collection<T> values) {
		// intentionally left empty

	}

	@Override
	public boolean isEnabled() {
		return !DOM.getElementPropertyBoolean(listBox.getElement(), "disabled");
	}

	@Override
	public void setEnabled(boolean enabled) {
		DOM.setElementPropertyBoolean(listBox.getElement(), "disabled", !enabled);
	}
}
