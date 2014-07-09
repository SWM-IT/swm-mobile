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
package de.swm.commons.mobile.client.widgets.itf;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.Button;


/**
 * HeaderPanel interface
 * 
 */
public interface IHeaderPanel {

	/**
	 * Adds a widget to the header panel (back/forward button)
	 * 
	 * @param w
	 *            the widget
	 */
	void add(Widget w);



	/**
	 * Sets the caption.
	 * 
	 * @param caption
	 *            .
	 */
	void setCaption(String caption);



	/**
	 * Returns the current caption.
	 * 
	 * @return current caption.
	 */
	String getCaption();



	/**
	 * Sets the left button with the given caption.
	 * 
	 * @param buttonName
	 *            the name of the button (caption)
	 */
	void setLeftButton(String buttonName);


	/**
	 * Sets the right button .
	 * 
	 * @param buttonInstance
	 *            the button instance
	 */
	void setRightButton(Button buttonInstance);

	/**
	 * Sets the right button with the given caption.
	 * 
	 * @param buttonName
	 *            the name of the button (caption)
	 */
	void setRightButton(String buttonName);



	/**
	 * Returns the left button
	 * 
	 * @return the left button
	 */
	Button getLeftButton();



	/**
	 * Returns the right button
	 * 
	 * @return the right button
	 */
	Button getRightButton();



	/**
	 * Click handler for the left button.
	 * 
	 * @param handler
	 *            the handler
	 */
	void setLeftButtonClickHandler(ClickHandler handler);



	/**
	 * Click handler for the right button.
	 * 
	 * @param handler
	 *            the handler
	 */
	void setRightButtonClickHandler(ClickHandler handler);



	/**
	 * Sets the header panel visible or invisible.
	 * 
	 * @param visible
	 *            true if visible
	 */
	void setVisible(boolean visible);

}