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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.utils.Utils;

/**
 * Select panel which contains select items.
 */
public class SelectPanel extends PanelBase implements ClickHandler, DragEventsHandler {

	private int mySelected = -1;

	/**
	 * Default constructor.
	 */
	public SelectPanel() {
		addDomHandler(this, ClickEvent.getType());
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().selectPanel());
	}

	/**
	 * Adds a selection handler.
	 *
	 * @param handler selection handler
	 * @return handle to remove this handler.
	 */
	public HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler handler) {
		return this.addHandler(handler, SelectionChangedEvent.TYPE);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
	}

	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
	}

	@Override
	public void add(Widget w) {
		assert w instanceof SelectItem : "Item must be an instance of SelectItem";
		super.add(w);
	}

	/**
	 * Removes the current selection.
	 */
	public void clearSelection() {
		if (mySelected >= 0 && getWidgetCount() > mySelected) {
			SelectItem item = (SelectItem) getWidget(mySelected);
			if (item != null) {
				item.setSelected(false);
				item.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
			}
		}
		mySelected = -1;
	}

	@Override
	public void onClick(ClickEvent e) {
		if (mySelected >= 0 && getWidgetCount() > mySelected) {
			SelectItem item = (SelectItem) getWidget(mySelected);
			if (!item.isDisabled()) {
				if (e != null) {
					SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(mySelected, e.getNativeEvent()
							.getEventTarget());
					fireEvent(selectionChangedEvent);
				} else {
					SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(mySelected, null);
					fireEvent(selectionChangedEvent);
				}
				item.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
			}
		}
	}

	@Override
	public void onDragStart(DragEvent e) {
		mySelected = Utils.getTargetItemIndex(getElement(), e.getNativeEvent().getEventTarget());
		if (mySelected >= 0 && getWidgetCount() > 0 && mySelected < getWidgetCount()) {
			SelectItem item = (SelectItem) getWidget(mySelected);
			if (!item.isDisabled()) {
				getWidget(mySelected).addStyleName(
						SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
				item.setSelected(true);
				for (int i = 0; i < getWidgetCount(); i++) {
					if (i != mySelected) {
						getItem(i).setSelected(false);
					}
				}
			}
		}
		e.stopPropagation();
	}

	@Override
	public void onDragMove(DragEvent e) {
		if (mySelected >= 0) {
			getWidget(mySelected)
					.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
		}
		e.stopPropagation();
	}

	@Override
	public void onDragEnd(DragEvent e) {
		if (mySelected >= 0) {
			getWidget(mySelected)
					.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSelectPanelCss().pressed());
		}
		e.stopPropagation();
	}

	public SelectItem getItem(int index) {
		return (SelectItem) getWidget(index);
	}

	@Override
	public void clear() {
		super.clear();
		mySelected = -1;
	}

}
