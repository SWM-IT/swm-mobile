package de.swm.gwt.client.widget;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.text.shared.testing.PassthroughRenderer;
import com.google.gwt.user.client.ui.ValueLabel;

/**
 * Ein Label, das einen String anzeigt.
 *
 * @copyright SWM Services GmbH, 2013
 */
public class StringLabel extends ValueLabel<String> {

	/**
	 * Standard-Constructor.
	 */
	public StringLabel() {
		super(PassthroughRenderer.instance());
	}
}
