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
package de.swm.commons.mobile.client.widgets.experimental;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.FastClickHelper;
import de.swm.commons.mobile.client.widgets.*;


/**
 * Header-Panel which is very fast on Click events because an touch event will be forwarded as click event.
 */
public class FastHeaderPanel extends SWMMobileWidgetBase implements IHeaderPanel {

	private static String next_caption = SWMMobile.getI18N().headerPanelNextButton();
	private static String back_caption = SWMMobile.getI18N().headerPanelBackButton();

	FastClickHelper.FastClickHandler myLeftButtonClickHandler;
	FastClickHelper.FastClickHandler myRightButtonClickHandler;


	/**
	 * Default constructor.
	 */
	public FastHeaderPanel() {
		FlowPanel container = new FlowPanel();

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
	public void add(Widget w) {
		FlowPanel contents = ((FlowPanel) ((FlowPanel) getWidget()).getWidget(1));
		contents.add(w);
	}


	/**
	 * {@inheritDoc}
	 */
	public void setCaption(String caption) {
		FlowPanel contents = ((FlowPanel) ((FlowPanel) getWidget()).getWidget(1));
		contents.clear();
		contents.add(new HTML(caption));
	}


	/**
	 * {@inheritDoc}
	 */
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
	public void setLeftButton(String buttonName) {
		SimplePanel leftButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(0));
		FastClickHelper.FastClickHandler clickHandler = new FastClickHelper.FastClickHandler() {

			@Override
			public void onFastClick(FastClickHelper.FastClickEvent event) {
				onLeftButtonClick(event);
			}
		};
		if (back_caption.equalsIgnoreCase(buttonName)) {
			leftButton.setWidget(new SimpleBackButton(buttonName, clickHandler));
		} else {
			leftButton.setWidget(new Button(buttonName, clickHandler));
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setRightButton(String buttonName) {
		SimplePanel rightButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		FastClickHelper.FastClickHandler clickHandler = new FastClickHelper.FastClickHandler() {

			@Override
			public void onFastClick(FastClickHelper.FastClickEvent event) {
				onRightButtonClick(event);
			}
		};
		if (buttonName.equalsIgnoreCase(next_caption)) {
			rightButton.setWidget(new NextButton(buttonName, clickHandler));
		} else {
			rightButton.setWidget(new Button(buttonName, clickHandler));
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
	public Button getLeftButton() {
		SimplePanel leftButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(0));
		return (Button) leftButton.getWidget();
	}


	/**
	 * {@inheritDoc}
	 */
	public Button getRightButton() {
		SimplePanel rightButton = ((SimplePanel) ((FlowPanel) getWidget()).getWidget(2));
		return (Button) rightButton.getWidget();
	}



	/**
	 * When the left button was clicked
	 *
	 * @param event the click event
	 */
	public void onLeftButtonClick(FastClickHelper.FastClickEvent event) {
		if (myLeftButtonClickHandler != null) {
			myLeftButtonClickHandler.onFastClick(event);
		}
	}


	/**
	 * When the right button was clicked
	 *
	 * @param event the click event
	 */
	void onRightButtonClick(FastClickHelper.FastClickEvent event) {
		if (myRightButtonClickHandler != null) {
			myRightButtonClickHandler.onFastClick(event);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setLeftButtonClickHandler(FastClickHelper.FastClickHandler handler) {
		myLeftButtonClickHandler = handler;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setRightButtonClickHandler(FastClickHelper.FastClickHandler handler) {
		myRightButtonClickHandler = handler;
	}

	@Override
	public void setLeftButtonClickHandler(ClickHandler handler) {
		setLeftButtonClickHandler(FastClickHelper.wrapAsFastClickHandler(handler));
	}

	@Override
	public void setRightButtonClickHandler(ClickHandler handler) {
		setRightButtonClickHandler(FastClickHelper.wrapAsFastClickHandler(handler));
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

}
