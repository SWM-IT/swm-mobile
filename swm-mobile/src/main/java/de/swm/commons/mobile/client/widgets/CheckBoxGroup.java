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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.event.SelectionChangedHandler;
import de.swm.commons.mobile.client.utils.Utils;



/**
 * Defines a group of check boxes.
 * 
 */
public class CheckBoxGroup extends PanelBase implements HasWidgets, ClickHandler, DragEventsHandler,
		ValueChangeHandler<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(CheckBoxGroup.class.getName());


	private int pressed = -1;



	/**
	 * Default constructor.
	 */
	public CheckBoxGroup() {
		super();
		addDomHandler(this, ClickEvent.getType());
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().checkBoxGroup());
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().vertical());
	}



	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
	}



	@Override
	protected void onUnload() {
		super.onUnload();
		DragController.get().removeDragEventsHandler(this);
	}



	/**
	 * Adds a selection changes handler to the checkboxgroup.
	 * 
	 * @param handler
	 *            the handler.
	 * @return handler registration to remove this handler.
	 */
	public HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler handler) {
		return this.addHandler(handler, SelectionChangedEvent.TYPE);
	}



	@Override
	public void onClick(ClickEvent e) {
		final EventTarget target = e.getNativeEvent().getEventTarget();
		String targetTagName = ((Element) target.cast()).getTagName().toUpperCase();
		LOGGER.info("onClick target " + targetTagName);
		if (targetTagName.equals("LABEL") || targetTagName.equals("IMG")) {
			return; // if check box label or image is clicked, another (simulated) click event with
					// check box INPUT as target will fire after this one. So this click event
					// must be ignored.
		}
		Element div = Element.as(target);
		while (!div.getNodeName().toUpperCase().equals("SPAN") || div.getParentElement() != this.getElement()) {
			div = div.getParentElement();
			if (div == null) {
				LOGGER.info("CheckBoxGroup onClick: span not found");
				return;
			}
		}
		final int index = DOM.getChildIndex(this.getElement(), (com.google.gwt.user.client.Element) div);
		com.google.gwt.user.client.ui.CheckBox checkbox = (com.google.gwt.user.client.ui.CheckBox) myFlowPanel
				.getWidget(index);
		LOGGER.info("onClick " + checkbox.getValue());
		if (targetTagName.equals("INPUT")) {
			LOGGER.info("onClick value changed");
			checkbox.setValue(checkbox.getValue()); // if target is check box INPUT, check box value is
													// already changed when click event is fired.
													// just need to set its current value back to the check box
													// to update style.
		} else {
			checkbox.setValue(!checkbox.getValue());
		}

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(index, target);
				fireEvent(selectionChangedEvent);
			}
		});
	}



	/**
	 * Returns the checked check box indices.
	 * 
	 * @return the checked check box indices.
	 */
	public List<Integer> getCheckedIndices() {
		ArrayList<Integer> checkedList = new ArrayList<Integer>(myFlowPanel.getWidgetCount());
		for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
			com.google.gwt.user.client.ui.CheckBox radio = (com.google.gwt.user.client.ui.CheckBox) myFlowPanel.getWidget(i);
			if (radio.getValue()) {
				checkedList.add(i);
			}
		}
		return checkedList;
	}



	/**
	 * Returns the checked check box widgets. All widgets are instances of
	 * {@link com.google.gwt.user.client.ui.CheckBox}
	 * 
	 * @return the checked check box widgets.
	 */
	public List<Widget> getCheckedWidgets() {
		ArrayList<Widget> checkedList = new ArrayList<Widget>(myFlowPanel.getWidgetCount());
		for (int i = 0; i < myFlowPanel.getWidgetCount(); i++) {
			com.google.gwt.user.client.ui.CheckBox radio = (com.google.gwt.user.client.ui.CheckBox) myFlowPanel.getWidget(i);
			if (radio.getValue()) {
				checkedList.add(radio);
			}
		}
		return checkedList;
	}



	@Override
	public void add(Widget w) {
		assert w instanceof CheckBox : "Can only contain CheckBox widgets in CheckBoxGroup";
		CheckBox checkbox = (CheckBox) w;
		myFlowPanel.add(checkbox);
		checkbox.addValueChangeHandler(this);
	}



	/**
	 * Will display the check box group vertical or horizontal.
	 * 
	 * @param vertical true if vertical
	 */
	public void setVertical(boolean vertical) {
		if (vertical) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().vertical());
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().horizontal());
		} else {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().horizontal());
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().vertical());
		}
	}



	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
		LOGGER.info("onValueChange " + event.getValue() + " " + event.getSource().getClass());
	}



	@Override
	public void onDragStart(DragEvent e) {
		pressed = Utils.getTargetItemIndex(getElement(), e.getNativeEvent().getEventTarget());
		if (pressed >= 0) {
			Widget item = getWidget(pressed);
			item.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().pressed());
		}
	}



	@Override
	public void onDragMove(DragEvent e) {
		if (pressed >= 0) {
			Widget item = getWidget(pressed);
			item.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().pressed());
			pressed = -1;
		}
	}



	@Override
	public void onDragEnd(DragEvent e) {
		onDragMove(e);
	}

}
