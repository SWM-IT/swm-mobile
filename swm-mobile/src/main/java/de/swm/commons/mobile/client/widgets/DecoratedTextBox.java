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


import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.event.DragEvent;


/**
 * Test box with an image decoration (right corner)
 */
public class DecoratedTextBox extends Composite implements DragEventsHandler, HasClickHandlers, HasText,
		HasFocusHandlers, HasBlurHandlers, HasIconClickHandlers {

	private final TextBox textBox;
	private Label captionLabel;
	private final Image img;
	private final ImageResource icon1, icon2;

	/**
	 * Default constructor.
	 *
	 * @param caption      cation text
	 * @param iconNormal   right icon
	 * @param iconSelected right icon selected
	 */
	@UiConstructor
	public DecoratedTextBox(String caption, ImageResource iconNormal, ImageResource iconSelected) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSecondaryStyle(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxHPanel());
		textBox = new TextBox();
		textBox.setSecondaryStyle(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxInput());
		hPanel.add(textBox);
		this.icon1 = iconNormal;
		this.icon2 = iconSelected;
		img = new Image(iconNormal);
		img.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxIcon(), true);
		hPanel.add(img);

		img.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new IconClickEvent(event));
			}
		});

		if ((caption != null) && (!caption.equals(""))) {
			VerticalPanel vPanel = new VerticalPanel();
			captionLabel = new Label(caption);
			captionLabel.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxCaption());
			vPanel.add(captionLabel);
			vPanel.add(hPanel);
			vPanel.setSecondaryStyle(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().decoratedTextBoxVPanel());
			initWidget(vPanel);
		} else {
			initWidget(hPanel);
		}
	}


	/**
	 * Sets the focus.
	 *
	 * @param hasFocus focus
	 */
	public void setFocus(boolean hasFocus) {
		this.textBox.setFocus(hasFocus);
	}


	/**
	 * Enables / Disables the widget.
	 *
	 * @param enabled .
	 */
	public void setEnabled(boolean enabled) {
		this.textBox.setEnabled(enabled);
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
		// do not suppress click, otherwise button does not react when small movements occured
//		DragController.get().suppressNextClick();
		e.stopPropagation();
	}


	@Override
	public void onDragEnd(DragEvent e) {
		img.setResource(icon1);
		e.stopPropagation();
	}


	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return textBox.addClickHandler(handler);
	}

	public HandlerRegistration addIconClickHandler(IconClickHandler handler) {
		return addHandler(handler, IconClickEvent.getType());
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return textBox.addFocusHandler(handler);
	}


	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return textBox.addBlurHandler(handler);
	}


	@Override
	public String getText() {
		return textBox.getText();
	}


	@Override
	public void setText(String text) {
		textBox.setText(text);
	}
	
	public String getCaption() {
		return (captionLabel != null) ? captionLabel.getText() : null;
	}

	public void setCaption(String caption) {
		if (captionLabel != null) {
			captionLabel.setText(caption);
		}
	}

	public TextBox getTextBox() {
		return textBox;
	}
	
	public void setIconVisible(boolean visible) {
		img.setVisible(visible);
	}

}
