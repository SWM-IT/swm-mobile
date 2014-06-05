package de.swm.gwt.client.widget;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ValueLabel;

/**
 * Ein Label, das abhaengig von einem booleschen Wert Ja oder Nein anzeigt.
 *
 * @copyright SWM Services GmbH, 2013
 */
public class BooleanJaNeinLabel extends ValueLabel<Boolean> implements TakesValue<Boolean> {

	/**
	 * Standard-Constuctor.
	 */
	public BooleanJaNeinLabel() {
		super(new BooleanJaNeinRenderer());
	}

}
