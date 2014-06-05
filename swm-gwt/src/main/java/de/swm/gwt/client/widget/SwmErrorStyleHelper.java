package de.swm.gwt.client.widget;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 * Hilfklasse um Vaidation Fehler zu behandeln.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class SwmErrorStyleHelper implements HasEditorErrors<String> {

	private final Widget forWidget;
	private String errorStyleName;

	public SwmErrorStyleHelper(Widget forWidget) {
		this.forWidget = forWidget;
	}

	/**
	 * Setzt den Style-Name fuer einen Fehler.
	 * @param errorStyleName der Style-Name
	 */
	public void setErrorStyleName(String errorStyleName) {
		this.errorStyleName = errorStyleName;
	}

	/**
	 * Called by the EditorDriver to propagate errors. May be called with a
	 * zero-length list to indicate that any existing error condition should be
	 * cleared.
	 * <p/>
	 * An Editor may consume any errors reported by its sub-Editors by calling
	 * {@link com.google.gwt.editor.client.EditorError#setConsumed(boolean)}. Any unconsumed editors will be
	 * reported up the Editor hierarchy and will eventually be emitted by the
	 * EditorDriver.
	 *
	 * @param errors an unmodifiable list of EditorErrors
	 */
	@Override
	public void showErrors(List<EditorError> errors) {
		if (errors == null || errors.isEmpty()) {
			if (errorStyleName != null) {
				forWidget.removeStyleName(errorStyleName);
			}
		} else {
			if (errorStyleName != null) {
				forWidget.addStyleName(errorStyleName);
			}
		}
	}


}
