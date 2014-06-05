package de.swm.gwt.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.ValueBox;
import de.swm.gwt.client.I18NEnum;
import de.swm.gwt.client.enums.AlwaysNullParser;
import de.swm.gwt.client.enums.I18NEnumRenderer;

import java.util.Date;
import java.util.List;

/**
 * Redert ein Enum DISPLAY_ONLY (nicht fuer read verwenden - de ein NullParser verwendet wird)!
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class EnumBoxWithAttributes<T extends I18NEnum> extends ValueBox<T>  implements HasEditorErrors<T> {

	private final SwmErrorStyleHelper swmErrorStyleHelper;


	/**
	 * Default constructor.
	 * @param constants die i18n konstanten
	 */
	public EnumBoxWithAttributes(ConstantsWithLookup constants) {
		super(Document.get().createTextInputElement(), new I18NEnumRenderer<T>(constants),
				new AlwaysNullParser<T>());
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
