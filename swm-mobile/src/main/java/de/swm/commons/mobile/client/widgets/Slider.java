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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;



/**
 * Integer value slider with data binding support.
 *  
 */
public class Slider extends SWMMobileWidgetBase implements DragEventsHandler, HasValueChangeHandlers<Integer>,
		IsEditor<LeafValueEditor<Integer>>, HasValue<Integer>, HasFocusHandlers, HasBlurHandlers {

	private static final int SCALE_FACTOR = 100;
	protected int myValue = 0;
	protected FlowPanel contentPanel = new FlowPanel();
	protected HTML myLabel = new HTML(myValue + "");
	protected HTML mySlider = new HTML();
	private LeafValueEditor<Integer> editor;



	/**
	 * Default constructor.
	 */
	public Slider() {
		contentPanel.add(myLabel);
		mySlider.setHTML("<div></div><div></div><div></div>");
		contentPanel.add(mySlider);
		initWidget(contentPanel);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSliderCss().slider());
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
	public void onDragStart(DragEvent e) {
		DragController.get().captureDragEvents(this);
		int value = computeNewValue(e);
		setValue(value);
	}



	@Override
	public void onDragMove(DragEvent e) {
		e.stopPropagation();
		int value = computeNewValue(e);
		setValue(value);
	}



	@Override
	public void onDragEnd(DragEvent e) {
		DragController.get().releaseDragCapture(this);
	}



	@Override
	public Integer getValue() {
		return myValue;
	}



	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}



	/**
	 * If the current value should be displayed.
	 * 
	 * @param display
	 *            true if the value should be displayed
	 */
	public void setDisplayValue(boolean display) {
		if (display) {
			myLabel.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSliderCss().hide());
		} else {
			myLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSliderCss().hide());
		}
	}



	/**
	 * Computes the value after a Drag event (after the slider was moved)
	 * 
	 * @param event
	 *            the event
	 * @return the new value
	 */
	private int computeNewValue(DragEvent event) {
		Element ele = getElement();
		int offset = (int) event.getX() - ele.getAbsoluteLeft();
		int width = ele.getClientWidth();
		int value = offset * SCALE_FACTOR / width;
		if (value > SCALE_FACTOR) {
			value = SCALE_FACTOR;
		} else if (value < 0) {
			value = 0;
		}
		return value;
	}



	/**
	 * Enforce the slider position update.
	 */
	private void updateSliderPosition() {
		myLabel.setHTML(myValue + "");
		Element slider = getSliderElement();
		slider.getStyle().setWidth(myValue, Unit.PCT);
	}



	private Element getSliderElement() {
		return (Element) mySlider.getElement().getChild(1);
	}



	@Override
	public LeafValueEditor<Integer> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}



	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the value
	 */
	public void setValue(Integer value) {
		if (value != null && myValue != value) {
			myValue = value;
			updateSliderPosition();
			ValueChangeEvent.fire(this, myValue);
		}
	}



	@Override
	public void setValue(Integer value, boolean fireEvents) {
		setValue(value);
	}
	
	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return mySlider.addDomHandler(handler, BlurEvent.getType());
	}



	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return mySlider.addDomHandler(handler, FocusEvent.getType());
	}
}
