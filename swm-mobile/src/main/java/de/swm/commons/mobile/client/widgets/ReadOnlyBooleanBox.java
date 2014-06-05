package de.swm.commons.mobile.client.widgets;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;



/**
 * Defines a Label with yes no values bound to a boolean editor. Enables to display read only boolen values by editor
 * binding.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public class ReadOnlyBooleanBox extends SWMMobileWidgetBase implements IsEditor<LeafValueEditor<Boolean>>,
		HasValue<Boolean>, HasText {

	// FIXME: Internationalization
	private static final String YES_VALUE = "Ja";
	private static final String NO_VALUE = "Nein";

	private final Label view;
	private boolean booleanValue;
	private LeafValueEditor<Boolean> editor;



	/**
	 * Default constructor.
	 */
	public ReadOnlyBooleanBox() {
		view = new Label(NO_VALUE);
		booleanValue = false;
		initWidget(view);
	}



	@Override
	public Boolean getValue() {
		return booleanValue;
	}



	@Override
	public void setValue(Boolean value) {
		view.setText((value) ? YES_VALUE : NO_VALUE);
		booleanValue = value;

	}



	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		setValue(value);

	}



	@Override
	public LeafValueEditor<Boolean> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}



	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return view.addHandler(handler, ValueChangeEvent.getType());
	}



	@Override
	public String getText() {
		return view.getText();
	}



	@Override
	public void setText(String text) {
		if (text.equals(NO_VALUE) || text.equals(YES_VALUE)) {
			view.setText(text);
		}

	}

}
