/*
 * Copyright (c) 2011 Alexander Vilbig
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.theme.SWMMobileImageBundle;

import java.util.Date;
import java.util.logging.Logger;

/**
 * ListItem wich represents a specific connection.
 * 
 */
public class ConnectionListItem extends ListItem {

	private static final Logger LOGGER = Logger.getLogger(ConnectionListItem.class.getName());


	private static final long MILLIS_PER_MINUTE = (1000L * 60);
	private static final long MILLIS_PER_HOUR = 1000L * 60 * 60;
	private static final int PADDING_RIGHT = 4;
	private static final double PADDING_TOP = 0.4;
	private static final double PADDING = 0.6;

	/**
	 * Die verschiedenen Verkehrsmittel.
	 */
	public enum TransportMeans {
		/** Transportmittel. **/
		WALK,
		/** Transportmittel. **/
		BUS,
		/** Transportmittel. **/
		TRAM,
		/** Transportmittel. **/
		UBAHN,
		/** Transportmittel. **/
		SBAHN,
		/** Transportmittel. **/
		BOAT,
		/** Transportmittel. **/
		CABLE,
		/** Transportmittel unbekannt. **/
		UNKNOWN
	}

	private final Label timeLabel;
	private final Label durationAndInfoLabel;
	private final SWMMobileImageBundle icons;
	private String infoText;
	private final Date fromDate;
	private final Date toDate;

	/**
	 * Default constructor.
	 * 
	 * @param is24hourFormat
	 *            true if date format is 24 hours
	 * @param fromDate
	 *            .
	 * @param toDate
	 *            .
	 * @param numTransfer
	 *            .
	 * @param transfer
	 *            .
	 * @param infoText
	 *            .
	 */
	@UiConstructor
	public ConnectionListItem(boolean is24hourFormat, Date fromDate,
			Date toDate, int numTransfer, TransportMeans[] transfer,
			String infoText) {
		this.icons = SWMMobile.getTheme().getMGWTImageBundle();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.infoText = infoText;
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getDecoratedListItemCss().decoratedListItemHPanel());
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getDecoratedListItemCss().decoratedListItemVPanel());

		HorizontalPanel dPanel = new HorizontalPanel();
		dPanel.add(new FlipTimePanel(fromDate, is24hourFormat));
		HTML dash = new HTML("-");
		dash.getElement().getStyle().setPaddingLeft(PADDING, Unit.EM);
		dash.getElement().getStyle().setPaddingRight(PADDING, Unit.EM);
		dPanel.add(dash);
		dPanel.add(new FlipTimePanel(toDate, is24hourFormat));
		HorizontalPanel timePanel = new HorizontalPanel();
		timePanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getConnectionListItemCss().connectionListTimePanel());
		timeLabel = new Label();
		timeLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getConnectionListItemCss().connectionListItemText());
		timePanel.add(timeLabel);
		dPanel.add(timePanel);
		vPanel.add(dPanel);

		durationAndInfoLabel = new Label(formatDurationAndInfoText());
		durationAndInfoLabel.addStyleName(SWMMobile.getTheme()
				.getMGWTCssBundle().getConnectionListItemCss()
				.connectionListItemText());
		durationAndInfoLabel.getElement().getStyle()
				.setPaddingTop(PADDING, Unit.EM);
		vPanel.add(durationAndInfoLabel);

		HorizontalPanel tPanel = new HorizontalPanel();
		Label transferLabel = new Label(SWMMobile.getI18N().interchanges() + " "
				+ numTransfer);
		transferLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getConnectionListItemCss().connectionListItemText());
		transferLabel.getElement().getStyle().setPaddingRight(PADDING, Unit.EM);
		tPanel.add(transferLabel);
		buildTransfer(tPanel, transfer);
		tPanel.getElement().getStyle().setPaddingTop(PADDING_TOP, Unit.EM);
		vPanel.add(tPanel);
		hPanel.add(vPanel);
		add(hPanel);
	}

	/**
	 * Aktualisiert die Anzeige der lokalen Abfahrtszeit.
	 * 
	 * @param currentTime
	 *            Aktuelle Zeit
	 * @return is true the connection is in the past.
	 */
	public boolean updateTime(Date currentTime) {
		timeLabel.setText(formatDepartureTime(currentTime));
		boolean isOldConnection = false;
		if (fromDate.before(currentTime)) {
			isOldConnection = true;
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getConnectionListItemCss().connectionListItemPast());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getConnectionListItemCss().connectionListItemPast());
		}

		return isOldConnection;
	}

	/**
	 * Marks the connection as the next recommended connection.
	 * 
	 * @param isRecomended
	 *            true if recommended
	 */
	public void setRecomended(boolean isRecomended) {
		if (isRecomended) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getConnectionListItemCss().connectionListItemRecomended());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getConnectionListItemCss().connectionListItemRecomended());
		}
	}

	/**
	 * Setzt den Infotext in der Zeitdauer anzeige.
	 * 
	 * @param text
	 *            der text
	 */
	public void setInfotext(String text) {
		this.infoText = text;
		durationAndInfoLabel.setText(formatDurationAndInfoText());

	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		if (disabled) { // add cover
			setShowArrow(false);
			getElement().getStyle().setPosition(Position.RELATIVE);
			Element cover = DOM.createDiv();
			cover.setClassName(SWMMobile.getTheme().getMGWTCssBundle()
					.getConnectionListItemCss().connectionListItemCover());
			DOM.appendChild(getElement(), cover);
		} else { // remove cover
			Element cover = null;
			for (int i = 0; i < DOM.getChildCount(getElement()); i++) {
				Element el = DOM.getChild(getElement(), i);
				if (SWMMobile.getTheme().getMGWTCssBundle()
						.getConnectionListItemCss().connectionListItemCover()
						.equals(el.getClassName())) {
					cover = el;
					break;
				}
			}
			if (cover != null) {
				DOM.removeChild(getElement(), cover);
			}
			setShowArrow(true);
		}
	}

	/**
	 * Zeigt die verschiedenen Verkehrsmittel an.
	 * 
	 * @param panel
	 *            das panel
	 * @param transfer
	 *            die verscheidenen Verkehrsmittel
	 */
	private void buildTransfer(HorizontalPanel panel, TransportMeans[] transfer) {
		for (TransportMeans tm : transfer) {
			Image img = null;
			switch (tm) {
			case WALK:
				img = new Image(icons.walk_small());
				break;
			case BUS:
				img = new Image(icons.bus_small());
				break;
			case TRAM:
				img = new Image(icons.tram_small());
				break;
			case UBAHN:
				img = new Image(icons.ubahn_small());
				break;
			case SBAHN:
				img = new Image(icons.sbahn_small());
				break;
			case BOAT:
				img = new Image(icons.boat_small());
				break;
			case CABLE:
				img = new Image(icons.cable_small());
				break;
			default:
				LOGGER.info("Unbekanntes Verkehrsmittel (" + tm + ")");
				img = new Image(icons.unknownSmall());
				break;
			}
			img.getElement().getStyle().setPaddingRight(PADDING_RIGHT, Unit.PX);
			panel.add(img);
		}
	}

	/**
	 * Formatiert die Zeit und den Text.
	 * 
	 * @return die zeitliche Formatierung.
	 */
	private String formatDurationAndInfoText() {
		long millis = this.toDate.getTime() - this.fromDate.getTime();
		long hours = millis / MILLIS_PER_HOUR;
		long minutes = (millis % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;
		StringBuilder sb = new StringBuilder();
		sb.append(SWMMobile.getI18N().journeyTime() + " ");
		if (hours > 0) {
			sb.append(hours + " " + SWMMobile.getI18N().journeyTimeHours()
					+ ", ");
		}
		sb.append(minutes + " " + SWMMobile.getI18N().journeyTimeMinutes());
		if (this.infoText != null) {
			sb.append(" - ").append(this.infoText);
		}
		return sb.toString();
	}

	/**
	 * Formats the time.
	 * 
	 * @param currentTime
	 *            current time
	 * @return the time
	 */
	private String formatDepartureTime(Date currentTime) {
		long millis = fromDate.getTime() - currentTime.getTime();
		boolean isFuture = true;
		if (millis < 0) {
			isFuture = false;
			millis = -millis;
		}
		long hours = millis / MILLIS_PER_HOUR;
		long minutes = (millis % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;
		StringBuilder sb = new StringBuilder();
		if (isFuture) {
			sb.append(SWMMobile.getI18N().journeyWillDepartureIn() + " ");
		} else {
			sb.append(SWMMobile.getI18N().journeyDeparturedBefore() + " ");
		}
		if (hours > 0) {
			sb.append(hours + " " + SWMMobile.getI18N().journeyTimeHours()
					+ ", ");
		}
		sb.append(minutes + " " + SWMMobile.getI18N().journeyTimeMinutes());

		if (!isFuture) {
			sb.append(" " + SWMMobile.getI18N().journeyDeparturedBeforeSuffix());
		}
		return sb.toString();
	}
}
