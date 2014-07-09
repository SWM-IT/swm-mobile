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

import com.google.gwt.user.client.ui.Composite;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;


/**
 * Base class for all SWM-Mobile widgets.
 * 
 */
public class SWMMobileWidgetBase extends Composite implements IsSWMMobileWidget {

	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();



	@Override
	public void onLoad() {
		super.onLoad();
		myWidgetHelper.checkInitialLoad(this);
	}



	/**
	 * When the widget is initially loaded.
	 */
	public void onInitialLoad() {
	}



	/**
	 * When a transition ends.
	 */
	public void onTransitionEnd() {
	}



	/**
	 * Adds a secondary or dependent style name to this object. A secondary style name is an additional style name that
	 * is, in HTML/CSS terms, included as a space-separated token in the value of the CSS <code>class</code> attribute
	 * for this object's root element.
	 * 
	 * @param style
	 *            the style name.
	 */
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}
}
