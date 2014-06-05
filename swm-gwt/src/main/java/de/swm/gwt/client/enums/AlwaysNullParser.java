package de.swm.gwt.client.enums;

import com.google.gwt.text.shared.Parser;
import de.swm.gwt.client.I18NEnum;

import java.text.ParseException;

/**
 * Hilfklasse fuer die Read-Only darstellung von Enums.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */


public class AlwaysNullParser<T extends I18NEnum> implements Parser<T> {


	@Override

	public T parse(CharSequence text) throws ParseException {
		return null;
	}
}
