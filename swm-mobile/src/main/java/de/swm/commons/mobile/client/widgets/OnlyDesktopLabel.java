package de.swm.commons.mobile.client.widgets;

import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;

/**
 * This label is only visible on desktop devices.
 * SWM S-IP-AN
 * User: wiesed
 * Date: 07.07.14
 * Time: 15:14
 */
public class OnlyDesktopLabel extends Label {

	public OnlyDesktopLabel() {
	}

	public OnlyDesktopLabel(String text) {
		super(getText(text));
	}


	public OnlyDesktopLabel(String text, Direction dir) {
		super(getText(text), dir);
	}

	public OnlyDesktopLabel(String text, DirectionEstimator directionEstimator) {
		super(getText(text), directionEstimator);
	}

	public OnlyDesktopLabel(String text, boolean wordWrap) {
		super(getText(text), wordWrap);
	}


	private static boolean isTextVisible() {
		return SWMMobile.getOsDetection().isDesktop();
	}

	@Override
	public String getText() {
		return getText(super.getText());
	}

	@Override
	public void setText(String text) {
		super.setText(getText(text));
	}

	private static String getText(String text) {
		if (isTextVisible()) {
			return text;
		}
		return "";
	}

}
