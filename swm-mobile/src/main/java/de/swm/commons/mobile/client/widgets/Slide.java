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

import java.util.Iterator;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;



/**
 * A slide panel reacts to finger slide gestures (sliding events). (see {@link SlidePanel}). A Slide is the content of
 * an Slide panel.
 * 
 */
public class Slide extends SWMMobileWidgetBase implements HasWidgets {

	protected FlowPanel panel = new FlowPanel();



	/**
	 * Default constructor.
	 */
	public Slide() {
		initWidget(panel);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSlidePanelCss().slide());
	}



	@Override
	public void add(Widget w) {
		panel.add(w);
	}



	@Override
	public void clear() {
		panel.clear();

	}



	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}



	@Override
	public boolean remove(Widget w) {
		return panel.remove(w);
	}

}
