package de.swm.gwt.client.widget;

import com.google.gwt.text.shared.Renderer;

import java.io.IOException;

/**
 * Ein Renderer der boolean Felder formatiert (Sprache: Deutsch)
 *
 * @copyright SWM Services GmbH, 2013
 */
public class BooleanJaNeinRenderer implements Renderer<Boolean> {


	private static final String NEIN = "Nein";
	private static final String JA = "ja";

	/**
	 * Standard-Constuctor.
	 */
	public BooleanJaNeinRenderer() {
	}

	@Override
	public String render(Boolean object) {
		if (object == null) {
			return NEIN;
		} else {
			return object ? JA : NEIN;
		}

	}

	@Override
	public void render(Boolean object, Appendable appendable) throws IOException {
		render(object);
	}
}
