/*
 * Copyright 2011 SWM Services GmbH.
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
package de.swm.commons.mobile.client.widgets.publictransport;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.VerticalPanel;

import java.util.Date;

/**
 * ListItem which represents an individual component of a trip.
 */
public class TripListItem extends ListItem {

	private final Image progressImage;
	private Image categoryImage;
	private final Label titleLabel;
	private final Label subtitleLabel;
	private final Label subSubtitleLabel;
	private final Label timeLabel;
	private Date departureTime;
	private Date arrivalTime;


	/**
	 * Default constructor.
	 *
	 * @param progress .
	 * @param category .
	 * @param title    .
	 * @param subtitle .
	 * @param dalay    .
	 */
	public TripListItem(ImageResource progress, ImageResource category,
						String title, String subtitle, String dalay) {
		this(progress, category, title, subtitle, "", dalay, "", "");

	}

	/**
	 * Default constructor.
	 *
	 * @param progress .
	 * @param category .
	 * @param title    .
	 * @param subtitle .
	 */
	public TripListItem(ImageResource progress, ImageResource category,
						String title, String subtitle, String subSubTitle, String dalay) {
		this(progress, category, title, subtitle, subSubTitle, dalay, "", "");

	}

	/**
	 * Default constructor.
	 *
	 * @param progress          .
	 * @param category          .
	 * @param title             .
	 * @param subtitle          .
	 * @param subSubTitle       .
	 * @param dalay             .
	 * @param departurePlatform .
	 * @param arrivalPlatform   .
	 */
	public TripListItem(ImageResource progress, ImageResource category,
						String title, String subtitle, String subSubTitle, String dalay,
						String departurePlatform, String arrivalPlatform) {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListMainPanel());
		HorizontalPanel panel = new HorizontalPanel();
		panel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripHPanel());

		progressImage = new Image(progress);
		panel.add(progressImage);
		panel.getElement().getStyle().setHeight(progress.getHeight(), Unit.PX);

		categoryImage = new Image(category);
		categoryImage.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripTransportImage());
		panel.add(categoryImage);

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripVPanel());

		//title
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListTitlePanel());

		titleLabel = new Label(title);
		titleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListItemTitle());
		titlePanel.add(titleLabel);

		HorizontalPanel timeAndPlatformPanel = new HorizontalPanel();
		timeAndPlatformPanel.addStyleName(SWMMobile.getTheme()
				.getMGWTCssBundle().getTripListItemCss().tripListTimePanel());

		final Label departurePlatformLabel = new Label(departurePlatform);
		departurePlatformLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListTimeLabel());
		departurePlatformLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripPunctual());
		timeAndPlatformPanel.add(departurePlatformLabel);

		timeLabel = new Label(dalay);
		timeLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListTimeLabel());
		timeAndPlatformPanel.add(timeLabel);

		titlePanel.add(timeAndPlatformPanel);
		vPanel.add(titlePanel);

		//subtitle
		subtitleLabel = new Label(subtitle);
		subtitleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListItemSubtitle());
		vPanel.add(subtitleLabel);

		//subsub title
		HorizontalPanel subsubTitlePanel = new HorizontalPanel();
		subSubtitleLabel = new Label(subSubTitle);
		subSubtitleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListItemSubSubtitle());
		subsubTitlePanel.add(subSubtitleLabel);

		HorizontalPanel arrivalPlatformPanel = new HorizontalPanel();
		arrivalPlatformPanel.addStyleName(SWMMobile.getTheme()
				.getMGWTCssBundle().getTripListItemCss().tripListTimePanel());

		final Label arrivalPlatformLabel = new Label(arrivalPlatform);
		arrivalPlatformLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripListTimeLabel());
		arrivalPlatformLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
				.getTripListItemCss().tripPunctual());

		arrivalPlatformPanel.add(arrivalPlatformLabel);
		subsubTitlePanel.add(arrivalPlatformPanel);
		vPanel.add(subsubTitlePanel);

		panel.add(vPanel);
		add(panel);
	}

	/**
	 * Image for the progress bar.
	 *
	 * @param image the image
	 */
	public void setProgressImage(ImageResource image) {
		progressImage.setResource(image);
	}

	/**
	 * The image for the trip category.
	 *
	 * @param categoryImage the category image
	 */
	public void setCategoryImage(Image categoryImage) {
		this.categoryImage = categoryImage;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	/**
	 * Sets the subtitle.
	 *
	 * @param subtitle the subtitle.
	 */
	public void setSubtitle(String subtitle) {
		subtitleLabel.setText(subtitle);
	}

	/**
	 * Sets the departure time.
	 *
	 * @param time the time
	 */
	public void setTime(String time) {
		timeLabel.setText(time);
	}


	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * The delay in minutes (if to late or to early).
	 *
	 * @param delay the delay
	 */
	public void setDelay(long delay) {
		if (delay == 0) {
			timeLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getTripListItemCss().tripPunctual());
		} else if (delay < 0) {
			timeLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getTripListItemCss().tripEarly());
		} else {
			timeLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getTripListItemCss().tripLate());
		}
	}
}
