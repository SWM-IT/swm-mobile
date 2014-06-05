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



/**
 * Base widget for all SWM-Mobile panels.
 * 
 */
public class PanelBase extends SWMMobileWidgetBase implements HasWidgets {

	protected FlowPanel myFlowPanel = new FlowPanel();



	/**
	 * Default constructor.
	 */
	public PanelBase() {
		initWidget(myFlowPanel);
	}



	@Override
	public void add(Widget w) {
		myFlowPanel.add(w);
	}



	@Override
	public void clear() {
		myFlowPanel.clear();
	}



	@Override
	public Iterator<Widget> iterator() {
		return myFlowPanel.iterator();
	}



	@Override
	public boolean remove(Widget w) {
		return myFlowPanel.remove(w);
	}



	/**
	 * Returns an inner widget on <code>index</code> position
	 * 
	 * @param index
	 *            the index
	 * @return the widget
	 */
	public Widget getWidget(int index) {
		return myFlowPanel.getWidget(index);
	}



	public int getWidgetCount() {
		return myFlowPanel.getWidgetCount();
	}



	/**
	 * Inserts a widget
	 * 
	 * @param w
	 *            the windget to insert
	 * @param beforeIndex
	 *            the index before which it will be inserted
	 */
	public void insert(Widget w, int beforeIndex) {
		myFlowPanel.insert(w, beforeIndex);
	}



	/**
	 * Removes an widget on index potion
	 * 
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		myFlowPanel.remove(index);
	}

}
