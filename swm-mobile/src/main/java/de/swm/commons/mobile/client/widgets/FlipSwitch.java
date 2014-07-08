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
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



/**
 * Flip-Switch (boolean widget) with data binding.
 * 
 */
public class FlipSwitch extends SWMMobileWidgetBase implements DragEventsHandler, ClickHandler, HasValueChangeHandlers<Boolean>,
		IsEditor<LeafValueEditor<Boolean>>, HasValue<Boolean>, HasClickHandlers, HasFocusHandlers, HasBlurHandlers {

	private static final Logger LOGGER = Logger.getLogger(FlipSwitch.class.getName());


	private static final int DURATION = 200;
	private static final double MIDDLE = 0.5;
	protected boolean myEnabled = true;
	protected boolean myValue = true;
	protected HTML myHtml = new HTML();
	private LeafValueEditor<Boolean> editor;
	private final List<DragEventsHandler> dragHandlers = new ArrayList<DragEventsHandler>();
	private String option1 = "On";
	private String option2 = "Off";


	/**
	 * Default constructor.
	 */
	public FlipSwitch() {
		initWidget(myHtml);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getFlipSwitchCss().flipSwitch());
		myHtml.addClickHandler(this);
		updateUI();
	}

	private void updateUI() {
		myHtml.setHTML(
				"<div></div><div></div><div><div><div>"
				+ this.option1
				+ "</div><div></div><div>"
				+ this.option2
				+ "</div></div></div>");
	}

	@Override
	public void onInitialLoad() {
		super.onInitialLoad();
		if (!myValue) {
			updateFlipPosition(0);
		}
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
		if (!myEnabled) {
			return;
		}
		DragController.get().captureDragEvents(this);
		Utils.setTransitionDuration(getFilpElement(), 0);
		for (DragEventsHandler toNotify : this.dragHandlers) {
			toNotify.onDragStart(e);
		}
	}



	@Override
	public void onDragMove(DragEvent e) {
		if (!myEnabled) {
			return;
		}
		e.stopPropagation();
		Element flip = getFilpElement();
		int x = Utils.getTranslateX(flip);
		int newX = (int) (x + e.getOffsetX());
		int onPosition = getOnPosition();
		int offPosition = getOffPosition();
		if (newX > onPosition) {
			newX = onPosition;
		} else if (newX < offPosition) {
			newX = offPosition;
		}
		if (newX != x) {
			Utils.setTranslateX(flip, newX);
		}

		for (DragEventsHandler toNotify : this.dragHandlers) {
			toNotify.onDragMove(e);
		}
	}



	@Override
	public void onDragEnd(DragEvent e) {
		if (!myEnabled) {
			return;
		}
		DragController.get().releaseDragCapture(this);
		Element flip = getFilpElement();
		int x = Utils.getTranslateX(flip);
		int onPosition = getOnPosition();
		int offPosition = getOffPosition();
		if (x == onPosition) {
			setValue(true, false, 0, true);
		} else if (x == offPosition) {
			setValue(false, false, 0, true);
		} else {
			float ratio = (float) x / (float) (offPosition - onPosition);
			boolean newValue = ratio < MIDDLE;
			int duration = (int) ((MIDDLE - Math.abs(ratio - MIDDLE)) * DURATION);
			LOGGER.info("ratio " + ratio + " duration " + duration);
			setValue(newValue, true, duration, true);
		}

		for (DragEventsHandler toNotify : this.dragHandlers) {
			toNotify.onDragEnd(e);
		}
	}



	/**
	 * Manually sets the value.
	 * 
	 * @param value
	 *            the new value
	 * @param forceUpdateFlipPosition
	 *            tue updated the position
	 * @param duration
	 *            the duration af the animation in ms
	 */
	private void setValue(boolean value, boolean forceUpdateFlipPosition, int duration, boolean fireEvents) {
		if (myValue != value) {
			myValue = value;
			updateFlipPosition(duration);
			if (fireEvents) {
				ValueChangeEvent.fire(this, myValue);
			}
		} else if (forceUpdateFlipPosition) {
			updateFlipPosition(duration);
		}
	}



	public boolean isEnabled() {
		return myEnabled;
	}



	/**
	 * Enables/disables the widget.
	 * 
	 * @param enabled
	 *            true if enabled.
	 */
	public void setEnabled(boolean enabled) {
		if (enabled == myEnabled) {
			return;
		}
		if (enabled) {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getFlipSwitchCss().disabled());
		} else {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getFlipSwitchCss().disabled());
		}
		myEnabled = enabled;
	}



	@Override
	public void onClick(ClickEvent event) {
		if (myEnabled) {
			setValue(!myValue);
		}
	}



	/**
	 * Updates the flip position.
	 * 
	 * @param duration
	 *            the duration of the animation
	 */
	private void updateFlipPosition(int duration) {
		Utils.setTransitionDuration(getFilpElement(), duration);
		Element flip = getFilpElement();
		if (myValue) {
			Utils.setTranslateX(flip, getOnPosition());
		} else {
			Utils.setTranslateX(flip, getOffPosition());
		}
	}



	private Element getFilpElement() {
		return (Element) getElement().getChild(2).getChild(0);
	}



	private int getOnPosition() {
		return 0;
	}



	/**
	 * Returns the absolute off potion
	 * 
	 * @return the sreen postion
	 */
	private int getOffPosition() {
		Element flip = getFilpElement();
		Element parent = flip.getParentElement();
		int flipWidth = flip.getScrollWidth();
		int parentWidth = parent.getClientWidth();
		return parentWidth - flipWidth;
	}



	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}



	@Override
	public LeafValueEditor<Boolean> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}



	@Override
	public void setValue(Boolean value) {
		if (value != null) {
			setValue(value, false, DURATION, true);
		}

	}



	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		this.setValue(value, true, DURATION, fireEvents);
	}



	public Boolean getValue() {
		return myValue;
	}



	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return myHtml.addClickHandler(handler);
	}



	/**
	 * Add a drag handler to the widget.
	 * 
	 * @param hanlder
	 *            a drag handler.
	 * @return the drag handler.
	 */
	public HandlerRegistration addDragHandler(final DragEventsHandler hanlder) {
		HandlerRegistration registration = new HandlerRegistration() {

			@Override
			public void removeHandler() {
				dragHandlers.remove(hanlder);
			}
		};

		dragHandlers.add(hanlder);
		return registration;
	}



	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return myHtml.addDomHandler(handler, BlurEvent.getType());
	}



	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return myHtml.addDomHandler(handler, FocusEvent.getType());
	}

	/**
	 * Gets the Name of the second option.
	 * @return the String of the second option.
	 */
	public String getOption2() {
		return option2;
	}

	/**
	 * Sets the Name of the second option.
	 * @param option2 the String of the second option.
	 */
	public void setOption2(String option2) {
		this.option2 = option2;
		this.updateUI();
	}

	/**
	 * Gets the Name of the first option.
	 * @return the String of the first option.
	 */
	public String getOption1() {
		return option1;
	}

	/**
	 * Gets the Name of the first option.
	 * @param option1 the String of the first option.
	 */
	public void setOption1(String option1) {
		this.option1 = option1;
		this.updateUI();
	}
}
