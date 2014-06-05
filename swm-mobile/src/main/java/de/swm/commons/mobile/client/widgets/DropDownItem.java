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

import com.google.gwt.user.client.ui.Label;


/**
 * Defines an entry inside a drop down box. The entry contains an internationalization key which is translated inside
 * the drop down box.
 */
public class DropDownItem extends Label {

	private String key;


	/**
	 * Default constructor.
	 */
	public DropDownItem() {
		key = "Undefined";
	}


	/**
	 * Default constructor.
	 *
	 * @param key an unique key to identify this item.
	 */
	public DropDownItem(String key) {
		this.key = key;
	}

	/**
	 * Constructor for simple value/text drop down item.
	 *
	 * @param key item key
	 * @param text  item text, will be displayed in the dropdown list
	 */
	public DropDownItem(String key, String text) {
		this(key);
		this.setText(text);
	}


	public String getKey() {
		return key;

	}


	public void setKey(String i18NKey) {
		this.key = i18NKey;
	}
}
