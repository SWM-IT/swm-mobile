package de.swm.gwt.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.text.client.DateTimeFormatRenderer;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.elementparsers.DateLabelParser;
import com.google.gwt.user.client.ui.ValueBox;

import java.util.Date;
import java.util.List;

/**
 * Eine Date-Box der Attribute uebergeben werden koennen.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class DateBoxWithAttributes extends ValueBox<Date> implements HasEditorErrors<Date> {

	private final SwmErrorStyleHelper swmErrorStyleHelper;


	/**
	 * Erzeugt eine Date box mit dem entsprechendne Pattern.
	 * @param pattern das pattern
	 */
	@UiConstructor
	public DateBoxWithAttributes(String pattern) {
		super(Document.get().createTextInputElement(), new DateTimeFormatRenderer(DateTimeFormat.getFormat(pattern)),
				new DateTimeParser(pattern));
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
