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
package de.swm.commons.mobile.client.widgets.tree;

import com.google.gwt.resources.client.ImageResource;

public class DefaultTreeNodeImpl implements ITreeNode {
	
	String header;
	String styleName;
	ImageResource icon;
	ImageResource selectedIcon;
	Object userData;
	ITreeNodeDisplay display;
	
	public DefaultTreeNodeImpl(String header) {
		this(header, null, null, null, null);
	}
	
	public DefaultTreeNodeImpl(String header, String styleName) {
		this(header, styleName, null, null, null);
	}
		
	public DefaultTreeNodeImpl(String header, String styleName, ImageResource icon) {
		this(header, styleName, icon, null, null);
	}
	
	public DefaultTreeNodeImpl(String header, String styleName, ImageResource icon, ImageResource selectedIcon) {
		this(header, styleName, icon, selectedIcon, null);
	}
	
	public DefaultTreeNodeImpl(String header, String styleName, ImageResource icon, ImageResource selectedIcon, Object userData) {
		this.header = header;
		this.styleName = styleName;
		this.icon = icon;
		this.selectedIcon = selectedIcon;
		this.userData = userData;
	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public ImageResource getIcon() {
		return icon;
	}
	
	@Override
	public ImageResource getSelectedIcon() {
		return selectedIcon;
	}
	
	@Override
	public String getStyleName() {
		return styleName;
	}

	@Override
	public ITreeNodeDisplay getDisplay() {
		return display;
	}
	
	public void setDisplay(ITreeNodeDisplay display) {
		this.display = display;
	}

	@Override
	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}
}
