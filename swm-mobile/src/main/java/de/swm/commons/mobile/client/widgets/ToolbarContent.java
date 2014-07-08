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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.Iterator;



/**
 * The content of an {@link ToolbarPanel}.
 * 
 */
public class ToolbarContent extends SWMMobileWidgetBase implements HasWidgets {

	protected FlowPanel panel = new FlowPanel();



	/**
	 * Default constructor.
	 */
	public ToolbarContent() {
		initWidget(panel);
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



	@Override
	public void onTransitionEnd() {
		for (int i = 0; i < panel.getWidgetCount(); i++) {
			Widget w = panel.getWidget(i);
			if (Utils.isSubclassOf(w.getClass(), SWMMobileWidgetBase.class)) {
				((SWMMobileWidgetBase) w).onTransitionEnd();
			}
		}
	}



	public Widget getWidget() {
		return panel.getWidget(0);
	}

}
