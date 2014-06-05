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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;



/**
 * Defines the visible header of an {@link AccordionStack}.
 * 
 */
public class AccordionHeader extends PanelBase implements ClickHandler, DragEventsHandler {

	private final AccordionArrow arrow;
	private final AccordionArrowInvisible arrowInvisible;



	/**
	 * Default constructor.
	 */
	public AccordionHeader() {
		this.addDomHandler(this, ClickEvent.getType());
		arrow = new AccordionArrow();
		arrowInvisible = new AccordionArrowInvisible();
		arrowInvisible.setVisible(false);
		this.add(arrow);
		this.add(arrowInvisible);
	}



	/**
	 * Hides/Shows the accordion arrow.
	 * 
	 * @param show
	 *            true if the arrow will be shown
	 */
	public void showArrow(boolean show) {
		arrow.setVisible(show);
		arrowInvisible.setVisible(!show);
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



	@Override
	public void onClick(ClickEvent event) {
		AccordionStack parent = (AccordionStack) this.getParent().getParent();
		parent.toggle();
	}



	@Override
	public void onDragEnd(DragEvent e) {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().pressed());
	}



	@Override
	public void onDragMove(DragEvent e) {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().pressed());
	}



	@Override
	public void onDragStart(DragEvent e) {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().pressed());
	}

	/**
	 * The arrow to close and open an accordion.
	 */
	public static class AccordionArrow extends HTML {
		/**
		 * Default constructor.
		 */
		public AccordionArrow() {
			setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().accordionArrow());
		}

	}

	/**
	 * An invisible accorion arrow (with spacing)
	 */
	public static class AccordionArrowInvisible extends HTML {
		/**
		 * Default constructor.
		 */
		public AccordionArrowInvisible() {
			setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().accordionArrowInvisible());
		}

	}

}
