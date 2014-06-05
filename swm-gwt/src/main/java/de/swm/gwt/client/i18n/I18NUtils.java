package de.swm.gwt.client.i18n;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;



/**
 * Hilfklasse im I18N Texte mit Variablen zu versehen.
 * 
 * @author Wiese.Daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public final class I18NUtils {

	private static final String DATE_FORMAT = "dd.MM.yyyy";

	private final DateTimeFormat dtf = DateTimeFormat.getFormat(DATE_FORMAT);

	private final String toCustomize;
	private final Map<String, String> variableReplacementMap = new HashMap<String, String>();



	/**
	 * Utility constructor.
	 * 
	 * @param toCustomize
	 *            der zu customizende I18N String.
	 */
	private I18NUtils(String toCustomize) {
		this.toCustomize = toCustomize;

	}



	/**
	 * Fuegt eine Ersetzung der Variablen hinzu.
	 * 
	 * @param name
	 *            der Variablen Name
	 * @param value
	 *            der Wert.
	 * @return leifert die Intance fuer das Inlining.
	 */
	public I18NUtils replaceVariable(String name, String value) {
		variableReplacementMap.put(name, value);
		return this;
	}



	/**
	 * Fuegt eine Ersetzung der Variablen hinzu.
	 * 
	 * @param name
	 *            der Variablen Name
	 * @param value
	 *            der Wert.
	 * @return leifert die Intance fuer das Inlining.
	 */
	public I18NUtils replaceVariable(String name, Date value) {
		variableReplacementMap.put(name, dtf.format(value));
		return this;
	}



	/**
	 * Fuegt eine Ersetzung der Variablen hinzu.
	 * 
	 * @param name
	 *            der Variablen Name
	 * @param values
	 *            werte als Liste.
	 * @return leifert die Intance fuer das Inlining.
	 */
	public I18NUtils replaceVariable(String name, List<String> values) {
		String result = "";
		for (int i = 0; i < values.size(); i++) {
			final String value = values.get(i);
			result += value;
			if ((i + 1) < values.size()) {
				result += ", ";
			}
		}
		variableReplacementMap.put(name, result);
		return this;
	}



	/**
	 * Fuegt eine Ersetzung der Variablen hinzu.
	 * 
	 * @param name
	 *            der Variablen Name
	 * @param value
	 *            der Wert.
	 * @return leifert die Intance fuer das Inlining.
	 */
	public I18NUtils replaceVariable(String name, long value) {
		return replaceVariable(name, String.valueOf(value));
	}



	/**
	 * Fuegt eine Ersetzung der Variablen hinzu.
	 * 
	 * @param name
	 *            der Variablen Name
	 * @param value
	 *            der Wert.
	 * @return leifert die Intance fuer das Inlining.
	 */
	public I18NUtils replaceVariable(String name, Integer value) {
		if (value != null) {
			return replaceVariable(name, String.valueOf(value));
		}
		return this;
	}



	/**
	 * Satrtet das Cutomizen eine I18N Strings.
	 * 
	 * @param toCustomize
	 *            der zu customizende I18N String.
	 * @return die instance fuer das Inlining.
	 */
	public static I18NUtils customize(String toCustomize) {
		return new I18NUtils(toCustomize);
	}



	@Override
	public String toString() {
		String result = this.toCustomize;
		for (String variable : this.variableReplacementMap.keySet()) {
			if (this.variableReplacementMap.get(variable) != null) {
				result = result.replace("$" + variable + "$", this.variableReplacementMap.get(variable));
			}
		}
		return result;
	}

}
