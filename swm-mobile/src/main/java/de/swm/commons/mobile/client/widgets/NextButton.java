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

import com.google.gwt.event.dom.client.ClickHandler;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.FastClickHelper;


/**
 * The next button typically placed inside a {@link IHeaderPanel}.
 * 
 */
public class NextButton extends Button {

	/**
	 * Default constructor with an caption.
	 */
	public NextButton() {
		setHTML("Next");
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().nextButton());
	}



	/**
	 * Default constructor.
	 *
	 * @param caption the caption
	 * @param handler the handler.
	 */
	public NextButton(String caption, ClickHandler handler) {
		setHTML(caption);
		this.addClickHandler(handler);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().nextButton());
	}

	/**
	 * Alternative constructor.
	 *
	 * @param caption the caption
	 * @param handler the fast click handler.
	 */
	public NextButton(String caption, FastClickHelper.FastClickHandler handler) {
		setHTML(caption);
		FastClickHelper.addClickHandler(this, handler);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().nextButton());
	}



	@Override
	public void setHTML(String html) {
		super.setHTML("<span class=\"" + SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().button() + "\">" + html
				+ "</span>" + "<span class=\"" + SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pointer()
				+ "\"></span>");
	}

}
