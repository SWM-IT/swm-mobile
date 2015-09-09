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
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;


/**
 * Display a loading indicator and blocks the UI.
 */
public class LoadingIndicatorPopup extends PopupPanel {

	private static final double PADDING = 6.0;
	private static final double PADDING_SUBTITLE = 1.0;
	private static final String loadingText = ".... Loading ....";
	private final Label loadingLabel;
	private final Label loadingSubLabel;
	private ImageResource loadingImage = SWMMobile.getTheme().getMGWTImageBundle().loading();

	/**
	 * Default constructor.
	 */
	public LoadingIndicatorPopup() {
		this(false);
		this.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, ClickEvent.getType());

		this.addDomHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, DragStartEvent.getType());

		this.addDomHandler(new DragEndHandler() {
			@Override
			public void onDragEnd(DragEndEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, DragEndEvent.getType());

	}


	/**
	 * Default constructor.
	 *
	 * @param showInfoText true the loading indicator will display info messages
	 */
	public LoadingIndicatorPopup(boolean showInfoText) {
		loadingLabel = new Label(loadingText);
		loadingSubLabel = new Label();

		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().loadingIndicatorPopup());
		setGlassStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().loadingIndicatorGlassPanel());
		VerticalPanel vPanel = new VerticalPanel();

		// create row 1
		HorizontalPanel hPanelRow1 = new HorizontalPanel();
		hPanelRow1.add(new Image(loadingImage));
		if (showInfoText) {
			loadingLabel.getElement().getStyle().setPaddingLeft(PADDING, Unit.PCT);
			loadingSubLabel.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss()
					.loadingIndicatorHeader());
			hPanelRow1.add(loadingLabel);
		}

		// create row 2
		HorizontalPanel hPanelRow2 = new HorizontalPanel();
		if (showInfoText) {
			loadingSubLabel.getElement().getStyle().setPaddingLeft(PADDING_SUBTITLE, Unit.PCT);
			loadingSubLabel.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss()
					.loadingIndicatorSubheader());
			hPanelRow2.add(loadingSubLabel);
		}
		// add rows to main panel (vPanel)
		vPanel.add(hPanelRow1);

		if (showInfoText) {
			vPanel.add(hPanelRow2);
		}

		setWidget(vPanel);
	}


	/**
	 * Shows the indicator.
	 *
	 * @param glassEffect the glass effect
	 */
	public void showCentered(boolean glassEffect) {
		setGlassEnabled(glassEffect);
		setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				setPopupPosition(left, top);
			}
		});
	}


	/**
	 * Sets the loading label
	 *
	 * @param loadingText the text to set
	 */
	public void setLoadingText(String loadingText) {
		this.loadingLabel.setText(loadingText);
	}


	/**
	 * Sets the loading sub label
	 *
	 * @param loadingSubtitleText the text to set
	 */
	public void setLoadingSubtitleText(String loadingSubtitleText) {
		this.loadingSubLabel.setText(loadingSubtitleText);
	}


	/**
	 * Sets the loading image.
	 *
	 * @param loadingImage the image to set.
	 */
	public void setLoadingImage(ImageResource loadingImage) {
		this.loadingImage = loadingImage;
		VerticalPanel vPanel = (VerticalPanel) this.getWidget();
		HorizontalPanel imagePanel = (HorizontalPanel) vPanel.getWidget(0);

		// remove current image
		imagePanel.clear();

		// set the new image
		Image image = new Image(loadingImage);
		imagePanel.add(image);
	}
}
