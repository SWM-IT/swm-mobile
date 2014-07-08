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
package de.swm.commons.mobile.client.utils;

import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.IsSWMMobileWidget;



/**
 * Helper class for SWM Mobile widgets.
 * 
 */
public class IsSWMMobileWidgetHelper {

	private boolean isInitialLoad = true;



	/**
	 * Checks if this widget was already initialized.
	 * 
	 * @param widget
	 *            the widget.
	 */
	public void checkInitialLoad(IsSWMMobileWidget widget) {
		if (isInitialLoad) {
			isInitialLoad = false;
			widget.onInitialLoad();
		}
	}



	/**
	 * Adds a secondary style to a widget.
	 * 
	 * @param widget
	 *            the widget to add the style to
	 * @param style
	 *            style name.
	 */
	public void setSecondaryStyle(Widget widget, String style) {
		widget.addStyleName(style);
	}
}
