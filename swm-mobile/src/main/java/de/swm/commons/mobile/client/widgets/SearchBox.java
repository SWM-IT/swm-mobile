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
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.TextBox;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.theme.components.SearchBoxCss;
import de.swm.commons.mobile.client.widgets.itf.IHasPlaceHolder;

/**
 * A search widget
 */
public class SearchBox extends Composite implements HasChangeHandlers, HasText,
		HasName, HasValue<String>, IHasPlaceHolder, HasAllKeyHandlers, HasEnabled,
		HasAllFocusHandlers {

	/**
	 * The radius that a finger can move before the touch is not considered a
	 * simple touch anymore
	 */
	public static final int RADIUS = 10;

	private final TextBox box;
	private ClearButton clearButton;
	private ClearButtonTouchHandler clearButtonHandler;
	private HandlerRegistration boxHandler;
	private final FlowPanel roundDiv;
	protected final SearchBoxCss css;

	/**
	 * Construct a search box
	 */
	public SearchBox() {
		this(SWMMobile.getTheme().getMGWTCssBundle().getSearchBoxCss());
	}

	/**
	 * Construct a search box with a given css
	 *
	 * @param css the css to use
	 */
	public SearchBox(SearchBoxCss css) {
		this.css = css;
		this.css.ensureInjected();
		FlowPanel main = new FlowPanel();

		main.addStyleName(css.searchBox());

		initWidget(main);

		roundDiv = new FlowPanel();
		roundDiv.addStyleName(css.round());
		main.add(roundDiv);

		box = new TextBox();
		box.addStyleName(css.input());

		box.getElement().setAttribute("autocapitalize", "off");
		box.getElement().setAttribute("autocorrect", "off");
		if (!SWMMobile.getOsDetection().isDesktop()) {
			box.getElement().setAttribute("type", "search");
		}

		roundDiv.add(box);

		if (!SWMMobile.getOsDetection().isDesktop()) {
			clearButton = new ClearButton();
			clearButton.addStyleName(css.clear());
		}

		setPlaceHolder("Search");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onAttach() {
		super.onAttach();
		if (!SWMMobile.getOsDetection().isDesktop()) {
			clearButtonHandler = new ClearButtonTouchHandler(clearButton);
			DragController.get().addDragEventsHandler(clearButtonHandler);
		}
		boxHandler = box.addKeyUpHandler(new SearchBoxChangeHandler());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		if (!SWMMobile.getOsDetection().isDesktop()) {
			DragController.get().removeDragEventsHandler(clearButtonHandler);
		}
		boxHandler.removeHandler();
	}

	/**
	 * Returns true if the widget is enabled, false if not.
	 */
	@Override
	public boolean isEnabled() {
		return box.isEnabled();
	}

    /**
     * Will set the focus of the search box.
     * @param focused true if focused
     */
    public void setFocus(boolean focused){
        box.setFocus(focused);
    }

	/**
	 * Sets whether this widget is enabled.
	 *
	 * @param enabled <code>true</code> to enable the widget, <code>false</code>
	 *                to disable it
	 */
	public void setEnabled(boolean enabled) {
		this.box.setEnabled(enabled);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPlaceHolder(String text) {
		box.getElement().setAttribute("placeholder", text);
	}

	/**
	 * Clear handler for the clear button.
	 *
	 * @param cl the clear button
	 */
	public void addClearButtonHandler(ClickHandler cl) {
		if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
			this.clearButton.addClickHandler(cl);
		}
	}

	private void clearSearchField() {
		box.setValue("");
		if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
			roundDiv.remove(clearButton);
		}
	}


	/**
	 * <p>
	 * getPlaceHolder
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPlaceHolder() {
		return box.getElement().getAttribute("placeholder");
	}

	/**
	 * <p>
	 * getText
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getText() {
		return box.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setText(String text) {
		box.setText(text);
	}

	/**
	 * {@inheritDoc}
	 */
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> stringValueChangeHandler) {
		return box.addValueChangeHandler(stringValueChangeHandler);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setName(String name) {
		box.setName(name);
	}

	/**
	 * <p>
	 * getName
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return box.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public com.google.gwt.event.shared.HandlerRegistration addChangeHandler(
			ChangeHandler handler) {
		return box.addChangeHandler(handler);
	}

	/**
	 * <p>
	 * getKey
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return box.getValue();
	}

	/**
	 * <p>
	 * setKey
	 * </p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		box.setValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setValue(String value, boolean fireEvents) {
		box.setValue(value, fireEvents);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return box.addKeyUpHandler(handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return box.addKeyDownHandler(handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return box.addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return box.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return box.addBlurHandler(handler);
	}

	private class SearchBoxChangeHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (!SWMMobile.getOsDetection().isDesktop()) {
				if (box.getValue().length() > 0) {
					if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
						roundDiv.add(clearButton);
					}
				} else {
					if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
						roundDiv.remove(clearButton);
					}
				}
			}

		}

	}

	private class ClearButtonTouchHandler implements DragEventsHandler {

		private boolean moved;
		private double x;
		private double y;
		private final ClearButton clearButton;

		public ClearButtonTouchHandler(ClearButton clearButton) {
			this.clearButton = clearButton;
		}

		@Override
		public void onDragStart(DragEvent event) {
			if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
				clearButton.addStyleName(css.clearActive());
			}
			moved = false;
			x = event.getOffsetX();
			y = event.getOffsetY();

			event.stopPropagation();

		}

		@Override
		public void onDragMove(DragEvent event) {

			if (Math.abs(event.getOffsetX() - x) > RADIUS
					|| Math.abs(event.getOffsetY() - y) > RADIUS) {
				moved = true;
				if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
					clearButton.removeStyleName(css.clearActive());
				}
			}
			event.stopPropagation();

		}

		@Override
		public void onDragEnd(DragEvent event) {
			if (clearButton != null && !SWMMobile.getOsDetection().isDesktop()) {
				clearButton.removeStyleName(css.clearActive());
			}

			if (!moved) {
				clearSearchField();
			}
			event.stopPropagation();
		}

		@Override
		public Element getElement() {
			return clearButton.getElement();
		}
	}

	public static class ClearButton extends HTML {

		public ClearButton() {
			super("<div></div>");
		}
	}

}
