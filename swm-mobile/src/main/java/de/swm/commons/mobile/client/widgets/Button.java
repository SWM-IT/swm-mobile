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

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.event.FastClickHelper;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;


/**
 * A button.
 */
public class Button extends HTML implements DragEventsHandler, IsSWMMobileWidget, HasTouchStartHandlers, HasClickHandlers {

	private boolean isDisabled = false;
	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();
	private HandlerRegistration touchStartHandler;
	private HandlerRegistration touchEndHandler;


	/**
	 * Default constructor.
	 */
	public Button() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().button());
	}


	/**
	 * Constructor.
	 *
	 * @param caption the caption.
	 * @param handler click handler.
	 */
	public Button(String caption, ClickHandler handler) {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().button());
		setHTML(caption);
		this.addClickHandler(handler);
	}

	/**
	 * Alternative constructor.
	 *
	 * @param caption the caption
	 * @param handler the fast click handler.
	 * @deprecated not working in iOS
	 */
	@Deprecated
	public Button(String caption, FastClickHelper.FastClickHandler handler) {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().button());
		setHTML(caption);
		FastClickHelper.addClickHandler(this, handler);
	}


	/**
	 * Constructor.
	 *
	 * @param caption the caption.
	 */
	public Button(String caption) {
		this();
		setHTML(caption);
	}

	/**
	 * Sets the cation of the button
	 *
	 * @param caption the caption
	 */
	public void setCaption(String caption) {
		setHTML(caption);
	}


	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
		myWidgetHelper.checkInitialLoad(this);
		//If the button was contructed with an Fast click Hanlder, no other hanlders will be added.
		touchStartHandler = this.addTouchStartHandler(new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
				if (!isDisabled) {
					addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
				}
				event.stopPropagation();
			}
		});
		touchEndHandler = this.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				if (!isDisabled) {
					removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
				}
				event.stopPropagation();
			}
		});

	}


	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
		touchStartHandler.removeHandler();
		touchEndHandler.removeHandler();
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
	}


	@Override
	public void onDragStart(DragEvent e) {
		if (!isDisabled) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		}
		e.stopPropagation();
	}


	@Override
	public void onDragMove(DragEvent e) {
		if (!isDisabled) {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		}
		e.stopPropagation();
	}


	@Override
	public void onDragEnd(DragEvent e) {
		if (!isDisabled) {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		} else {
			DragController.get().suppressNextClick();
		}
		e.stopPropagation();
	}


	/**
	 * Disables/Enables the button.
	 *
	 * @param disabled true if disabled
	 */
	public void setDisabled(boolean disabled) {
		isDisabled = disabled;
		if (disabled) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().disabled());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().disabled());
		}
	}


	public boolean isDisabled() {
		return isDisabled;
	}


	@Override
	public void onInitialLoad() {
	}


	@Override
	public void onTransitionEnd() {
	}


	@Override
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}
}
