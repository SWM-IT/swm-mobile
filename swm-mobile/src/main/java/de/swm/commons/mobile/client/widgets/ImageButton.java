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
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;



/**
 * A button with an image inside.
 */
public class ImageButton extends Button {
	
	Image img;
	
	public ImageButton() {
		super();
		img = new Image();
		DOM.appendChild(getElement(), img.getElement());
	}
	
	/**
	 * Default constructor.
	 * 
	 * @param image
	 *            the image inside
	 * @param handler
	 *            the buttons click handler
	 */
	public ImageButton(ImageResource image, ClickHandler handler) {
		super();
		img = new Image(image);
		DOM.appendChild(getElement(), img.getElement());
		this.addClickHandler(handler);
	}

	public void setIcon(ImageResource image) {
		img.setResource(image);
		getElement().getStyle().setHeight(image.getHeight(), Unit.PX);
		getElement().getStyle().setWidth(image.getWidth(), Unit.PX);
	}
}
