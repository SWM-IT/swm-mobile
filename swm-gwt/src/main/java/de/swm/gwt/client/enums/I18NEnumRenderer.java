package de.swm.gwt.client.enums;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.text.shared.Renderer;
import de.swm.gwt.client.I18NEnum;

import java.io.IOException;


/**
 * Renderer fuer GWT, der alle Enums, die I18NEnum implementieren, rendered.
 *
 * @param <T> der Typ des Enums.
 *            <br> copyright (C) 2013-2014, SWM Services GmbH
 * @author Steiner.Christian
 */
public class I18NEnumRenderer<T extends I18NEnum> implements Renderer<T> {

	private final ConstantsWithLookup constants;
	private String emptyValueConstant = "";


	/**
	 * Erzeugt das Binding.
	 *
	 * @param constants die passenden Konstanten fuer das I18NEnum.
	 */
	public I18NEnumRenderer(ConstantsWithLookup constants) {
		this(constants, "emptyValue");
	}


	/**
	 * Erzeugt das Binding.
	 *
	 * @param constants          die passenden Konstanten fuer das I18NEnum.
	 * @param emptyValueConstant die Konstante die angezeigt werden soll, wenn nichts gewaehlt wurde.
	 */
	public I18NEnumRenderer(ConstantsWithLookup constants, String emptyValueConstant) {
		this.constants = constants;
		this.emptyValueConstant = emptyValueConstant;
	}


	@Override
	public String render(T object) {
		return object == null ? "" : constants.getString(object.getI18NKey());
	}


	@Override
	public void render(T object, Appendable appendable) throws IOException {
		appendable.append(render(object));
	}


	/**
	 * Liefert den internationalisierten Text fuer die nicht ausgewaehlte Box.
	 *
	 * @return internationalisierten Text fuer die nicht ausgewaehlte Box.
	 */
	public String getEmptyText() {
		return constants.getString(emptyValueConstant);
	}


}