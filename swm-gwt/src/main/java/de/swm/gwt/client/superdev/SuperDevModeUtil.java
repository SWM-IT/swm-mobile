package de.swm.gwt.client.superdev;

import com.google.gwt.core.client.GWT;

/**
 * This class does th magic. Calling <code>showDevMode</code>  will sho the on/off buttons in the mobile app.
 */
public class SuperDevModeUtil {

	private static final SuperDevModeHelper HELPER = GWT.create(SuperDevModeHelper.class);

	public static void showDevMode() {
		HELPER.showDevMode();
	}
}
