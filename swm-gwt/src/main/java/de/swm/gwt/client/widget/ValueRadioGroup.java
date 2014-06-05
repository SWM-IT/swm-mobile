package de.swm.gwt.client.widget;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SimpleKeyProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modifizierter Code der ValueListBox von GWT. Gleiche Funktionalitaet, wird aber als Radio-Group dargestellt.
 * @author Steiner.Christian<br>
 * copyright 2013 SWM Service GmbH
 * @param <T> der Typ der dargestellten Objekte.
 */
public class ValueRadioGroup<T> extends Composite implements
	HasConstrainedValue<T>, IsEditor<TakesValueEditor<T>> {

	private final List<T> values = new ArrayList<T>();
	private final Map<Object, Integer> valueKeyToIndex = new HashMap<Object, Integer>();
	private final Renderer<T> renderer;
	private final ProvidesKey<T> keyProvider;
	private final List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	private final FlowPanel panel = new FlowPanel();
	private final String groupName;

	private TakesValueEditor<T> editor;
	private T value;

	/**
	 * Der ValueChangeHandler aktualisiert den vom User gewaehlten Wert, wenn ein RadioButton gewaehlt wird.
	 */
	private final ValueChangeHandler<Boolean> handler = new ValueChangeHandler() {
		@Override
		public void onValueChange(ValueChangeEvent event) {
			int selectedIndex = getSelectedIndex();

			if (selectedIndex < 0) {
				return; // Not sure why this happens during addValue
			}
			T newValue = values.get(selectedIndex);
			setValue(newValue, true);
		}
	};



	/**
	 * Default Constructor.
	 * @param renderer  Renderer um die Objekte vom Typ T zu Rendern.
	 * @param groupName Der Name der RadioGroup
	 */
	public ValueRadioGroup(Renderer<T> renderer, String groupName) {
		this(renderer, new SimpleKeyProvider<T>(), groupName);
	}



	/**
	 * Default Constructor.
	 * @param renderer  Renderer um die Objekte vom Typ T zu Rendern.
	 * @param keyProvider Liefert den Key der einzelnen Listenelemente, falls vorhanden.
	 * @param groupName Der Name der RadioGroup
	 */
	public ValueRadioGroup(Renderer<T> renderer, ProvidesKey<T> keyProvider, String groupName) {
		this.keyProvider = keyProvider;
		this.renderer = renderer;
		initWidget(panel);
		this.groupName = groupName;

	}



	/**
	 * Liefert den Index des Gewaehlten RadioButtons aus der Liste.
	 * @return der Index oder -1 wenn nichts gewaehlt.
	 */
	private int getSelectedIndex() {
		for (int i = 0; i < getRadioButtons().size(); i++) {
			if (getRadioButtons().get(i).getValue()) {
				return i;
			}
		}
		return -1;
	}



	/**
	 * {@inheritDoc}
	 */
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}



	/**
	 * Returns a {@link TakesValueEditor} backed by the ValueListBox.
	 * @return the TakesValueEditor
	 */
	public TakesValueEditor<T> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}



	/**
	 * {@inheritDoc}
	 */
	public T getValue() {
		return value;
	}



	/**
	 * {@inheritDoc}
	 */
	public void setAcceptableValues(Collection<T> newValues) {
		values.clear();
		valueKeyToIndex.clear();
		List<RadioButton> buttons = getRadioButtons();
		buttons.clear();

		for (T nextNewValue : newValues) {
			addValue(nextNewValue);
		}

		updateRadios();
	}



	/**
	 * Set the value and display it in the select element. Add the value to the
	 * acceptable set if it is not already there.
	 * @param value the value
	 */
	public void setValue(T value) {
		setValue(value, false);
	}



	/**
	 * {@inheritDoc}
	 */
	public void setValue(T value, boolean fireEvents) {
		if (value == this.value || (this.value != null && this.value.equals(value))) {
			return;
		}

		T before = this.value;
		this.value = value;
		updateRadios();

		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, before, value);
		}
	}



	/**
	 * Fuegt einen Wert in die RadioGroup ein. Erstellt dazu einen neuen RadioButton.
	 * @param value der Wert, der mit dem RadioButton gewaehlt werden kann.
	 */
	private void addValue(T value) {
		Object key = keyProvider.getKey(value);
		if (valueKeyToIndex.containsKey(key)) {
			throw new IllegalArgumentException("Duplicate value: " + value);
		}

		valueKeyToIndex.put(key, values.size());
		values.add(value);
		final RadioButton radioButton = new RadioButton(groupName, renderer.render(value));
		radioButton.addValueChangeHandler(handler);
		getRadioButtons().add(radioButton);
		panel.add(radioButton);
		assert values.size() == getRadioButtons().size();
	}



	/**
	 * Liefert eine Liste mit allen RadioButtons, die zu der Gruppe gehoeren.
	 * @return die Liste.
	 */
	private List<RadioButton> getRadioButtons() {
		return radioButtons;
	}



	/**
	 * Aktualisiert die Anzeige der RadioButtons.
	 */
	private void updateRadios() {
		Object key = keyProvider.getKey(value);
		Integer index = valueKeyToIndex.get(key);
		if (index == null) {
			addValue(value);
		}

		index = valueKeyToIndex.get(key);
		getRadioButtons().get(index).setValue(true);
	}
}
