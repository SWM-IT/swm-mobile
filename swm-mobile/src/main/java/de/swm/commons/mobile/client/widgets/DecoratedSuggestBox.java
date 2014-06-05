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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;



/**
 * Suggest box with icon decoration.
 * 
 */
public class DecoratedSuggestBox extends HorizontalPanel implements DragEventsHandler {

	private SuggestBox suggestBox;
	private Image img;
	private final ImageResource icon1, icon2;



	/**
	 * Default constructor.
	 * 
	 * @param oracle
	 *            multi word oracle
	 * @param iconNormal
	 *            normal icon
	 * @param iconSelected
	 *            pressed icon
	 * @param handler
	 *            if icon was selected (not the suggestion)
	 */
	public DecoratedSuggestBox(MultiWordSuggestOracle oracle, ImageResource iconNormal, ImageResource iconSelected,
			ClickHandler handler) {
		setSecondaryStyle(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedSuggestBox());
		suggestBox = new SuggestBox(oracle);
		suggestBox.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().textBox());
		suggestBox.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedSuggestBoxInput());
		add(suggestBox);
		this.icon1 = iconNormal;
		this.icon2 = iconSelected;
		img = new Image(iconNormal);
		img.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxIcon(), true);
		img.addClickHandler(handler);
		add(img);
	}



	/**
	 * Max limit of suggestions.
	 * 
	 * @param limit
	 *            the limit.
	 */
	public void setLimit(int limit) {
		suggestBox.setLimit(limit);
	}



	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
	}



	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
	}



	@Override
	public void onDragStart(DragEvent e) {
		if (icon2 != null) {
			img.setResource(icon2);
		}
		e.stopPropagation();
	}



	@Override
	public void onDragMove(DragEvent e) {
		if (icon2 != null) {
			img.setResource(icon2);
		}
		DragController.get().suppressNextClick();
		e.stopPropagation();
	}



	@Override
	public void onDragEnd(DragEvent e) {
		img.setResource(icon1);
		e.stopPropagation();
	}

}
