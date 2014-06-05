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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;


/**
 * A ListItem with an Image.
 * <p/>
 * <pre>
 * new DecoratedListItem(resources.gear3(), &quot;Command Panel&quot;, &quot;Sub Label &quot;);
 * </pre>
 */
public class DecoratedListItem extends ListItem {

	protected Image img;
	protected Label titleLabel;
	protected Label subtitleLabel;
	protected HorizontalPanel mainPanel;
	protected VerticalPanel vPanel;
	protected Object userObject;


	/**
	 * Default constructor.
	 */
	public DecoratedListItem() {
		this.mainPanel = new HorizontalPanel();
		this.img = new Image();
		this.mainPanel.add(this.img);
		this.vPanel = new VerticalPanel();
		this.vPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss().decoratedListItemVPanel());
		this.titleLabel = new Label();
		this.titleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemTitle());
		this.vPanel.add(this.titleLabel);
		this.subtitleLabel = new Label();
		this.subtitleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemSubtitle());
		this.vPanel.add(this.subtitleLabel);
		this.mainPanel.add(this.vPanel);
		add(this.mainPanel);
	}


	/**
	 * Default constructor.
	 *
	 * @param image    image od the text box
	 * @param title    title
	 * @param subtitle subtitle
	 */
	@UiConstructor
	public DecoratedListItem(ImageResource image, String title, String subtitle) {
		this();
		this.img.setUrlAndVisibleRect(image.getSafeUri(), image.getLeft(), image.getTop(), image.getWidth(),
				image.getHeight());
		this.titleLabel.setText(title);
		this.subtitleLabel.setText(subtitle);
	}

	/**
	 * Default constructor.
	 *
	 * @param image    image od the text box
	 * @param title    title
	 * @param subtitle subtitle
	 */
	public DecoratedListItem(ImageResource image, int width, int height, String title, String subtitle) {
		this();
		this.img.setUrlAndVisibleRect(image.getSafeUri(), image.getLeft(), image.getTop(), width,
				height);
		this.titleLabel.setText(title);
		this.subtitleLabel.setText(subtitle);
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	/**
	 * Disabled/enables the widget.
	 *
	 * @param disabled true if disabled.
	 */
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		if (myDisabled) {
			this.titleLabel.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
					.decoratedListItemTitle());
			this.titleLabel.removeStyleName("gwt-Label");
			this.titleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().disabled());
		} else {
			this.titleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
					.decoratedListItemTitle());
			this.titleLabel.addStyleName("gwt-Label");
			titleLabel.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().disabled());
		}
	}

	/**
	 * Sets an image.
	 *
	 * @param image the image
	 */
	public void setImage(ImageResource image) {
		this.img.setResource(image);
	}

	/**
	 * Sets an image.
	 *
	 * @param image the image
	 */
	public void addImage(ImageResource image) {
		Image toAdd = new Image();
		toAdd.setResource(image);
		this.mainPanel.add(toAdd);
	}


	/**
	 * Sets an title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.titleLabel.setText(title);
	}


	@Override
	public String getTitle() {
		return this.titleLabel.getText();
	}


	/**
	 * Sets the subtitle.
	 *
	 * @param subtitle subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitleLabel.setText(subtitle);
	}


	public String getSubtitle() {
		return this.subtitleLabel.getText();
	}


	public Label getTitleLabel() {
		return titleLabel;
	}

	public Label getSubtitleLabel() {
		return subtitleLabel;
	}

	public Image getImage() {
		return img;
	}

	public HorizontalPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Register a handler which processes clicks on the embedded image.
	 *
	 * @param handler the handler
	 * @return the handle registration
	 */
	public HandlerRegistration addImageClickHandler(ClickHandler handler) {
		return this.img.addClickHandler(handler);
	}

	/**
	 * Adds a new line to the list item in the style of the subtitle.
	 *
	 * @param newLine .
	 */
	public void addLine(String newLine) {
		Label newLineLabel = new Label(newLine);
		newLineLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemSubtitle());
		this.vPanel.add(newLineLabel);
	}

	/**
	 * Adds a new line to the list item in the style of the subtitle and a given color.
	 *
	 * @param newLine .
	 * @param color   .
	 */
	public void addLine(String newLine, String color) {
		Label newLineLabel = new Label(newLine);
		newLineLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemSubtitle());
		newLineLabel.getElement().getStyle().setColor(color);
		this.vPanel.add(newLineLabel);
	}

}