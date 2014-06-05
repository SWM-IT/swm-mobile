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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.gwt.client.utils.ShimClickHandler;


/**
 * Defines a list item which can be placed inside a {@link ListPanel} A list item can have a left navigation arrow.
 */
public class ListItem extends FlowPanel {

	private ShimClickHandler shimClickHandler = new ShimClickHandler();

	/**
	 * Arrow visibility.
	 */
	public enum ShowArrow {
		/**
		 * Arrow visibility. *
		 */
		Default,
		/**
		 * Arrow visibility. *
		 */
		True,
		/**
		 * Arrow visibility. *
		 */
		False
	}

	protected ShowArrow myShowArrow = ShowArrow.Default;
	protected boolean myDisabled = false;


	/**
	 * Default constructor.
	 */
	public ListItem() {
		super();
		this.addDomHandler(shimClickHandler, ClickEvent.getType());
	}


	/**
	 * Adds a click Handler to the list item.
	 *
	 * @param handlerToAdd the click handler to add.
	 * @return the click handler
	 */
	public HandlerRegistration addClickHanlder(ClickHandler handlerToAdd) {
		return shimClickHandler.addClickHandler(handlerToAdd);
	}


	/**
	 * Shows/hides the right arrow icon
	 *
	 * @param show .
	 */
	public void setShowArrow(boolean show) {
		myShowArrow = show ? ShowArrow.True : ShowArrow.False;
		int last = getWidgetCount() - 1;
		if (last >= 0) {
			Widget widget = getWidget(last);
			if (widget != null && widget instanceof ListPanel.Chevron) {
				if (!show) {
					remove(last);
				}
			} else {
				if (show) {
					add(new ListPanel.Chevron());
				}
			}
		}
	}


	/**
	 * Disabled/enables the widget.
	 *
	 * @param disabled true if disabled.
	 */
	public void setDisabled(boolean disabled) {
		myDisabled = disabled;
		shimClickHandler.setEnabled(!disabled);
		if (myDisabled) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().disabled());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().disabled());
		}
	}


	public boolean getDisabled() {
		return myDisabled;
	}


	/**
	 * Defines the global arrow options
	 *
	 * @param show the global arraow visibility options
	 */
	void setShowArrowFromParent(boolean show) {
		// Parent can only override if it has not been set.
		if (myShowArrow == ShowArrow.Default) {
			setShowArrow(show);
		}
	}

}
