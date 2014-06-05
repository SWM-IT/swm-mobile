/*
 * Copyright 2012 SWM Services GmbH.
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
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.*;
import de.swm.commons.mobile.client.SWMMobile;

import java.util.Iterator;


/**
 * Header-Panel which is not maintaining any history.
 */
public class SimpleHeaderPanel extends SWMMobileWidgetBase implements HasWidgets, IHeaderPanel {

	private static String next_caption = SWMMobile.getI18N().headerPanelNextButton();
	private static String back_caption = SWMMobile.getI18N().headerPanelBackButton();
	private static boolean isHideBackUttonsOnAndroid;

	ClickHandler myLeftButtonClickHandler;
	ClickHandler myRightButtonClickHandler;
	TouchStartHandler myLeftButtonTouchHandler;
	TouchStartHandler myRightButtonTouchHandler;
	FlowPanel container;

	/**
	 * Default constructor.
	 */
	public SimpleHeaderPanel() {
		container = new FlowPanel();

		final SimplePanel leftButtonContainer = new SimplePanel();
		container.add(leftButtonContainer); // left button placeholder

		container.add(new FlowPanel()); // contents

		final SimplePanel rightButtonContainer = new SimplePanel();
		container.add(rightButtonContainer); // right button placeholder

		initWidget(container);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getHeaderCss().headerPanel());

	}


	/**
	 * Sets the widget displayed on the left side.
	 *
	 * @param w widget to be displayed
	 */
	public void setLeftWidget(Widget w) {
		SimplePanel leftPanel = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(0));
		leftPanel.setWidget(w);
	}


	/**
	 * Sets the widget displayed on the right side.
	 *
	 * @param w widget to be displayed
	 */
	public void setRightWidget(Widget w) {
		SimplePanel rightPanel = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		rightPanel.setWidget(w);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Widget w) {
		FlowPanel contents = ((FlowPanel) ((FlowPanel) getWidget()).getWidget(1));
		contents.add(w);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCaption(String caption) {
		FlowPanel contents = ((FlowPanel) ((FlowPanel) getWidget()).getWidget(1));
		contents.clear();
		contents.add(new HTML(caption));
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCaption() {
		FlowPanel contents = ((FlowPanel) ((FlowPanel) getWidget()).getWidget(1));
		if (contents.getWidgetCount() > 0) {
			HTML w = (HTML) contents.getWidget(0);
			return w.getHTML();
		}
		return "";
	}


	/**
	 * Will display the back button.
	 *
	 * @param hasButton true to display
	 */
	public void setHasBackButton(boolean hasButton) {
		if (hasButton) {
			setLeftButton(SWMMobile.getI18N().headerPanelBackButton());
		}
	}


	/**
	 * Will display the next button.
	 *
	 * @param hasButton true to display
	 */
	public void setHasNextButton(boolean hasButton) {
		if (hasButton) {
			setLeftButton(SWMMobile.getI18N().headerPanelNextButton());
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLeftButton(String buttonName) {
		if (!hideBackButtonsOnAndroid()) {
			SimplePanel leftButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(0));
			final ClickHandler clickHandler = new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					onLeftButtonClick(event);
				}
			};
			final TouchStartHandler touchStartHandler = new TouchStartHandler() {
				@Override
				public void onTouchStart(TouchStartEvent event) {
					onLeftButtonTouchStart(event);
				}
			};
			if (back_caption.equalsIgnoreCase(buttonName)) {
				SimpleBackButton button = new SimpleBackButton(buttonName, clickHandler);
				button.addTouchStartHandler(touchStartHandler);
				leftButton.setWidget(button);
			} else {
				Button button = new Button(buttonName, clickHandler);
				button.addTouchStartHandler(touchStartHandler);
				leftButton.setWidget(button);
			}
		}
	}

	private boolean hideBackButtonsOnAndroid() {
		if (SWMMobile.getOsDetection().isAndroid() && isHideBackUttonsOnAndroid){
			return true;
		}
		//dafaultmassig ist der back button aktiv
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRightButton(String buttonName) {
		SimplePanel rightButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		final ClickHandler clickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onRightButtonClick(event);
			}
		};
		final TouchStartHandler touchStartHandler = new TouchStartHandler() {
			@Override
			public void onTouchStart(TouchStartEvent event) {
				onRightButtonTouchStart(event);
			}
		};
		if (buttonName.equalsIgnoreCase(next_caption)) {
			NextButton button = new NextButton(buttonName, clickHandler);
			button.addTouchStartHandler(touchStartHandler);
			rightButton.setWidget(button);
		} else {
			Button button = new Button(buttonName, clickHandler);
			button.addTouchStartHandler(touchStartHandler);
			rightButton.setWidget(button);
		}
	}


	/**
	 * Will set a pre-constructed button as right button.
	 *
	 * @param button the preconstructed button.
	 */
	public void setRightButton(Button button) {
		SimplePanel rightButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		rightButton.setWidget(button);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button getLeftButton() {
		SimplePanel leftButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(0));
		return (Button) leftButton.getWidget();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button getRightButton() {
		SimplePanel rightButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		return (Button) rightButton.getWidget();
	}


	private void onLeftButtonClick(ClickEvent event) {
		if (myLeftButtonClickHandler != null) {
			myLeftButtonClickHandler.onClick(event);
		}
	}


	private void onRightButtonClick(ClickEvent event) {
		if (myRightButtonClickHandler != null) {
			myRightButtonClickHandler.onClick(event);
		}
	}

	private void onLeftButtonTouchStart(TouchStartEvent event) {
		if (myLeftButtonTouchHandler != null) {
			myLeftButtonTouchHandler.onTouchStart(event);
		}
	}

	private void onRightButtonTouchStart(TouchStartEvent event) {
		if (myRightButtonTouchHandler != null) {
			myRightButtonTouchHandler.onTouchStart(event);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLeftButtonClickHandler(ClickHandler handler) {
		myLeftButtonClickHandler = handler;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRightButtonClickHandler(ClickHandler handler) {
		myRightButtonClickHandler = handler;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLeftButtonTouchHandler(TouchStartHandler handler) {
		myLeftButtonTouchHandler = handler;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setRightButtonTouchHandler(TouchStartHandler handler) {
		myRightButtonTouchHandler = handler;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Widget> iterator() {
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Widget w) {
		return false;
	}


	@Override
	public void clear() {
		container.clear();

	}


	/**
	 * Sets the default text literals for next and back buttons. All buttons with this name will be rendered as back or
	 * next buttons. This method should be called on app start.
	 *
	 * @param backCaptionText .
	 * @param nextCaptionText .
	 */
	public static void internationalize(String backCaptionText, String nextCaptionText) {
		back_caption = backCaptionText;
		next_caption = nextCaptionText;
	}

	/**
	 * If true - back button will not be visible on android.
	 * @param hideBackUttonsOnAndroid if true back button will not be visible on android
	 */
	public static void setHideBackBttonsOnAndroid(boolean hideBackUttonsOnAndroid) {
		isHideBackUttonsOnAndroid = hideBackUttonsOnAndroid;
	}
}
