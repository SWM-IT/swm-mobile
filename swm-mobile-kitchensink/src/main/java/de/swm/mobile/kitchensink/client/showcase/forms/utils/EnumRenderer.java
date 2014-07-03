package de.swm.mobile.kitchensink.client.showcase.forms.utils;

import com.google.gwt.text.shared.Renderer;
import de.swm.commons.mobile.client.widgets.GenericRadioButtonGroup;
import de.swm.commons.mobile.client.widgets.RadioButton;
import de.swm.commons.mobile.client.widgets.itf.IProvidesKeyAndValue;

import java.io.IOException;

/**
 * Helperclass to showcase i18n enum rendering.
 */
public class EnumRenderer implements IProvidesKeyAndValue<EnumRenderer.ExampleEnum>,
		Renderer<EnumRenderer.ExampleEnum> {

	public enum ExampleEnum {

		GENERIC_OPTION_1,
		GENERIC_OPTION_2,
		GENERIC_OPTION_3

	}

	@Override
	public String getKey(ExampleEnum item) {
		return item.name();
	}


	@Override
	public ExampleEnum getValue(String key) {
		return ExampleEnum.valueOf(key);
	}


	@Override
	public String render(ExampleEnum object) {
		switch (object) {
			case GENERIC_OPTION_1:
				return "Option 1";
			case GENERIC_OPTION_2:
				return "Option 2";
			case GENERIC_OPTION_3:
				return "Option 3";
			default:
				return "n/a";
		}
	}


	@Override
	public void render(ExampleEnum object, Appendable appendable) throws IOException {
		appendable.append(render(object));

	}

	/**
	 * Set the possible values in the rendering widget.
	 *
	 * @param field the underlying widget
	 */
	public static void setValues(GenericRadioButtonGroup<ExampleEnum> field) {
		for (ExampleEnum entry : ExampleEnum.values()) {
			RadioButton radio = new RadioButton();
			radio.setFormValue(entry.name());
			field.add(radio);
		}

	}

}
