package de.swm.commons.mobile.client.widgets;

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;

/**
 * Displays Arrival and Departure times.
 * 
 */
public class FlipTimePanel extends HorizontalPanel {

	private static final int HOURS_PER_HALF_DAY = 12;
	private static final double WIDTH = 0.9;

	/**
	 * Zeit eine numme an (als Abfahrtzeit).
	 * 
	 * @author wiese.daniel <br>
	 *         copyright (C) 2011, SWM Services GmbH
	 * 
	 */
	public class FlipNumber extends FlowPanel {

		/**
		 * Default constructor.
		 * 
		 * @param text
		 *            der text
		 */
		public FlipNumber(String text) {
			getElement().addClassName(
					SWMMobile.getTheme().getMGWTCssBundle()
							.getFlipTimePanelCss().flipNumber());
			Element cover = DOM.createDiv();
			cover.addClassName(SWMMobile.getTheme().getMGWTCssBundle()
					.getFlipTimePanelCss().flipNumberCover());
			DOM.appendChild(getElement(), cover);
			Element content = DOM.createElement("p");
			content.addClassName(SWMMobile.getTheme().getMGWTCssBundle()
					.getFlipTimePanelCss().flipNumberContent());
			content.setInnerText(text);
			DOM.appendChild(getElement(), content);
		}

	}

	/**
	 * Default constructor.
	 * 
	 * @param d
	 *            das datum.
	 */
	public FlipTimePanel(Date d) {
		this(d, true);
	}

	/**
	 * Default constructor.
	 * 
	 * @param d
	 *            the date.
	 * @param is24hourFormat
	 *            true if date format is 24 hours
	 */
	@SuppressWarnings("deprecation")
	public FlipTimePanel(Date d, boolean is24hourFormat) {
		int hoursAsInt = d.getHours();
		if (!is24hourFormat) {
			if (hoursAsInt > HOURS_PER_HALF_DAY) {
				hoursAsInt = hoursAsInt - HOURS_PER_HALF_DAY;
			}
		}
		NumberFormat nf = NumberFormat.getFormat("00");
		FlipNumber hours = new FlipNumber(nf.format(hoursAsInt));
		hours.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getFlipTimePanelCss().flipHours());
		add(hours);
		FlipNumber colon = new FlipNumber(":");
		colon.getElement().getStyle().setWidth(WIDTH, Unit.EM);
		add(colon);
		FlipNumber minutes = new FlipNumber(nf.format(d.getMinutes()));
		minutes.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getFlipTimePanelCss().flipMinutes());
		add(minutes);
	}

}
