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

import com.google.gwt.user.client.ui.Widget;

/**
 * List panel which contains list items and a static element at the button of the
 * list.
 * 
 */
public class ListPanelWithStaticLastItem extends ListPanel {

	private ListItem lastItem;

	/**
	 * Retuns the most buttonm static item.
	 * 
	 * @return the last item.
	 */
	public ListItem getLastItem() {
		return lastItem;
	}

	/**
	 * Sets the last mot button item.
	 * 
	 * @param lastItem the last item of the panel
	 */
	public void setLastItem(ListItem lastItem) {
		this.lastItem = lastItem;
	}

	@Override
	public void add(Widget w) {
		if (lastItem != null) {
			this.remove(lastItem);
		}
		super.add(w);

		if (lastItem != null) {
			super.add(lastItem);
		}
	}

}
