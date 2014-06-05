package de.swm.gwt.client.widget;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ValueLabel;
import de.swm.gwt.client.I18NEnum;
import de.swm.gwt.client.enums.I18NEnumRenderer;

/**
 * Ein Label, das den internationalisierten Wert von einem 18N Enum anzeigt.
 *
 * @copyright SWM Services GmbH, 2013
 */
public class EnumLabel<T extends I18NEnum> extends ValueLabel<T> implements TakesValue<T> {

	/**
	 * Standard-Constructor.
	 *
	 * @param constants die fuer JaNeinDTO verwendeten Konstanten.
	 */
	public EnumLabel(ConstantsWithLookup constants) {
		super(new I18NEnumRenderer<T>(constants));
	}

}
