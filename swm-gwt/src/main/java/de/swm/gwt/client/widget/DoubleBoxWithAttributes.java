package de.swm.gwt.client.widget;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.user.client.ui.DoubleBox;

import java.util.List;

/**
 * Eine DoubleBox an die man zusaetzliche Attribute setzten kann.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */

public class DoubleBoxWithAttributes extends DoubleBox implements HasEditorErrors<Double> {

	private final SwmErrorStyleHelper swmErrorStyleHelper;

	/**
	 * Creates an empty text box.
	 */
	public DoubleBoxWithAttributes() {
		swmErrorStyleHelper = new SwmErrorStyleHelper(this);
	}

	public void setAttribute(String attribute) {
		this.getElement().setAttribute(attribute, "");
	}

	public void setAttribute(String attribute, String value) {
		this.getElement().setAttribute(attribute, value);
	}

	/**
	 * Setzt den Style-Name fuer einen Fehler.
	 *
	 * @param errorStyleName der Style-Name
	 */
	public void setErrorStyleName(String errorStyleName) {
		swmErrorStyleHelper.setErrorStyleName(errorStyleName);
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		swmErrorStyleHelper.showErrors(errors);
	}
}
