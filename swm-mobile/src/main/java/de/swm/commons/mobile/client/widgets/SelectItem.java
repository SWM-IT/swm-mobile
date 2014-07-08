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

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;


/**
 * Defines a select item which can be placed inside a {@link SelectPanel}. A select item can have a checkmark.
 */
public class SelectItem extends HorizontalPanel {

	protected Label textLabel;
	protected Image checkmarkImage;
	protected String key;
	protected boolean disabled = false;
	protected boolean selected = false;
	
	public SelectItem(String text, String key, boolean selected) {
		assert text != null : "Text must be set for SelectItem";
		textLabel = new Label(text);
		add(textLabel);
		this.key = key;
		this.selected = selected;
		if (selected) {
			checkmarkImage = new Image(SWMMobile.getTheme().getMGWTImageBundle().checkmark());
		} else {
			checkmarkImage = new Image(SWMMobile.getTheme().getMGWTImageBundle().checkmark_gray());
		}
		add(checkmarkImage);
	}
	
	public void setText(String text) {
		if (textLabel == null) {
			textLabel = new Label();
			add(textLabel);
		}
		textLabel.setText(text);
	}

	/**
	 * Disabled/enables the widget.
	 *
	 * @param disabled true if disabled.
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (disabled) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().disabled());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().disabled());
		}
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			checkmarkImage.setResource(SWMMobile.getTheme().getMGWTImageBundle().checkmark());
		} else {
			checkmarkImage.setResource(SWMMobile.getTheme().getMGWTImageBundle().checkmark_gray());			
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public String getKey() {
		return key;
	}
	
	@Override
	protected void onUnload() {
		super.onUnload();
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
	}
}
