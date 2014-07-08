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

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;



/**
 * A Tab is the content of a {@link TabPanel}.
 * 
 */
public class Tab extends SWMMobileWidgetBase implements HasWidgets {

	private TabHeader myHeader;
	private TabContent myContent;



	/**
	 * Default constructor.
	 */
	public Tab() {
	}



	public TabHeader getHeader() {
		return myHeader;
	}



	public TabContent getContent() {
		return myContent;
	}



	@Override
	public void add(Widget w) {
		if (this.getWidget() == null) {
			assert w instanceof TabHeader : "Expected a TabHeader widget in a Tab";
			myHeader = (TabHeader) w;
			initWidget(myHeader);
		} else {
			assert w instanceof TabContent : "Expected a TabContent widget in a Tab";
			myContent = (TabContent) w;
		}
	}



	@Override
	public void clear() {
	}



	@Override
	public Iterator<Widget> iterator() {
		return null;
	}



	@Override
	public boolean remove(Widget w) {
		return false;
	}

}
