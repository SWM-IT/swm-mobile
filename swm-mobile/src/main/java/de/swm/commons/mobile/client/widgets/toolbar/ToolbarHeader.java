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
package de.swm.commons.mobile.client.widgets.toolbar;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;


/**
 * Header of the Toolbar containing an image and a capture
 */
public class ToolbarHeader extends FlowPanel implements DragEventsHandler, IsSWMMobileWidget {

	private Image headerImage;
	private ImageResource headerImageRes;
	private ImageResource highlightImageRes;
	private Label badgeLabel;
	private ToolbarElement iAmInsideTisElement;
	private boolean selectionEnabled = true;


	/**
	 * Default constructor.
	 *
	 * @param headerText     .
	 * @param headerImage    .
	 * @param highlightImage .
	 */
	@UiConstructor
	public ToolbarHeader(String headerText, ImageResource headerImage, ImageResource highlightImage) {
		assert headerImage != null : "Header image must be set for ToolbarHeader";
		this.headerImageRes = headerImage;
		this.headerImage = new Image(headerImage);
		Image.prefetch(headerImage.getSafeUri()); // prevent flicker of images on initial display
		add(this.headerImage);
		this.highlightImageRes = highlightImage;
		if (highlightImage != null) {
			Image.prefetch(highlightImage.getSafeUri()); // prevent flicker of images on initial display
		}
		if (headerText != null) {
			Label headerText1 = new Label(headerText);
			add(headerText1);
		}
	}

	/**
	 * Sets the element were this element is places inside.
	 *
	 * @param outerElement the outer element
	 */
	public void setOuterToolebarElement(ToolbarElement outerElement) {
		this.iAmInsideTisElement = outerElement;
	}

	/**
	 * If false he header will not be viually selected (will stay static)
	 *
	 * @param setEnabled true to anable, false to diable selection (ui highligting)
	 */
	public void setSelectionEnabled(boolean setEnabled) {
		this.selectionEnabled = setEnabled;

	}

	/**
	 * Selects or unselects this toolbar header.
	 *
	 * @param selected true if selected
	 */
	public void setSelected(boolean selected) {
		if (selectionEnabled) {
			if (!selected) {
				this.iAmInsideTisElement.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getToolbarPanelCss().selected());
				this.selectImage(false);
			} else {
				this.iAmInsideTisElement.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getToolbarPanelCss()
						.selected());
				this.selectImage(true);
			}
		}
	}


	/**
	 * Selects the image (displays the selected Image icon)
	 *
	 * @param isHighlighted if highlighted
	 */
	public void selectImage(boolean isHighlighted) {
		if (isHighlighted && (highlightImageRes != null)) {
			headerImage.setResource(highlightImageRes);
		} else if (headerImageRes != null) {
			headerImage.setResource(headerImageRes);
		}
	}

	/**
	 * Sets the text to be displayed in form of a 'badge' on top of the header image.
	 *
	 * @param badgeValue text to be displayed as badge. If badgeValue is {@code null}, the badge is hidden.
	 */
	public void setBadgeValue(String badgeValue) {

		if (badgeLabel == null) {
			if (badgeValue != null) {
				badgeLabel = new Label(badgeValue);
				badgeLabel.setStyleName(SWMMobile.getTheme().getMGWTCssBundle()
						.getToolbarPanelCss().badge());
				insert(badgeLabel, 1);
				badgeLabel.setVisible(true);
			}
		} else {
			if (badgeValue != null) {
				badgeLabel.setVisible(true);
				badgeLabel.setText(badgeValue);
			} else {
				badgeLabel.setVisible(false);
			}
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
	}

	@Override
	public void onUnload() {
		super.onUnload();
		DragController.get().removeDragEventsHandler(this);
	}

	/**
	 * When the drag starts.
	 *
	 * @param e drag event
	 */
	@Override
	public void onDragStart(DragEvent e) {
		this.iAmInsideTisElement.unselectAllElements();
		this.iAmInsideTisElement.setSelected(true, true);
	}


	/**
	 * When the drag is ongoing.
	 *
	 * @param e drag event
	 */
	@Override
	public void onDragMove(DragEvent e) {

	}

	/**
	 * When the drag ends
	 *
	 * @param e drag event
	 */
	@Override
	public void onDragEnd(DragEvent e) {

	}

	/**
	 * When the widget is loaded first time.
	 */
	@Override
	public void onInitialLoad() {

	}

	/**
	 * When an transition containing this widget ends.
	 */
	@Override
	public void onTransitionEnd() {

	}

	/**
	 * Will set the secondary style.
	 *
	 * @param style the style to set.
	 */
	@Override
	public void setSecondaryStyle(String style) {
		this.setSecondaryStyle(style);
	}
}
