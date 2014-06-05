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
import com.google.gwt.user.client.ui.Label;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragEvent;


/**
 * A button light grey with left handed text orientation.<br>
 * Can be used a substitute for an {@link Label}.
 * 
 */
public class LabelButton extends Button {

	/**
	 * Default constructor.
	 */
	public LabelButton() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().buttonLightLeftText());
	}



	/**
	 * Constructor.
	 * 
	 * @param caption
	 *            the caption.
	 * @param handler
	 *            click handler.
	 */
	public LabelButton(String caption, ClickHandler handler) {
		this();
		setHTML(caption);
		this.addClickHandler(handler);
	}

	/**
	 * Überschreibt die Button-Methode und Propagiert das Event weiter (wichtig für Scrollbare liste aus LabelButtons.
	 * Wird das Event nicht weiterpropagiert, ist die Liste nicht merh Scrollbar.
	 *
	 * @param e .
	 */
	@Override
	public void onDragMove(DragEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDragEnd(DragEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDragStart(DragEvent e) {
	}
}
