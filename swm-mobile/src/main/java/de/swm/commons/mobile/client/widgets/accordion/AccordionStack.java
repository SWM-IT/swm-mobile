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
package de.swm.commons.mobile.client.widgets.accordion;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.AccordionEvent;
import de.swm.commons.mobile.client.event.AccordionEventsHandler;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.PanelBase;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * An AccordionPanel is an widget, that can be expanded or collapsed. The stack is the content of such a panel.
 * <p/>
 * <pre>
 * <m:AccordionPanel ui:field="accordion">
 * 		<m:AccordionStack>
 * 			<m:AccordionHeader>
 * 				<g:Label>Accordion Stack 1</g:Label>
 * 			</m:AccordionHeader>
 * 			<m:AccordionContent>
 * 				<g:Label>Accordion Stack 1 Content</g:Label>
 * 				<g:Label>Accordion Stack 4 Content</g:Label>
 * 			</m:AccordionContent>
 * 		</m:AccordionStack>
 * 	</m:AccordionPanel>
 * </pre>
 */
public class AccordionStack extends PanelBase {

	private static final Logger LOGGER = Logger.getLogger(AccordionStack.class.getName());


	private AccordionHeader myHeader;
	private AccordionContent myContent;
	private final List<AccordionEventsHandler> accordionEventHandlers = new ArrayList<AccordionEventsHandler>();


	@Override
	public void onInitialLoad() {
		if (!this.getStyleName().contains(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().expand())) {
			close();
		}
	}


	@Override
	public void add(Widget w) {
		if (myHeader == null) {
			myHeader = (AccordionHeader) w;
		} else if (myContent != null) {
			assert false : "Can only contain a header and a content.";
		} else {
			myContent = (AccordionContent) w;
		}
		myFlowPanel.add(w);
	}

	/**
	 * Allow this component to register accordion events.
	 *
	 * @param accordionEventsHanlder the handler.
	 */
	public void addSwipeEventsHandler(AccordionEventsHandler accordionEventsHanlder) {
		accordionEventHandlers.add(accordionEventsHanlder);
	}


	/**
	 * Will close this stack.
	 */
	public void close() {
		for (AccordionEventsHandler handler : accordionEventHandlers) {
			final AccordionEvent event = new AccordionEvent(AccordionEvent.Type.Close, this);
			event.dispatch(handler);
		}
		this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().collapse());
		this.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().expand());
		Element focus = Utils.getActiveElement();
		focus.blur();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				myContent.setHeight("0px");
			}
		});
	}


	/**
	 * Will expand this stack.
	 */
	public void expand() {
		if (isExpended()) {
			return;
		}
		for (AccordionEventsHandler handler : accordionEventHandlers) {
			final AccordionEvent event = new AccordionEvent(AccordionEvent.Type.Open, this);
			event.dispatch(handler);
		}

		this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().expand());
		this.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().collapse());
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				int newHeight = myContent.getElement().getScrollHeight() - myContent.getElement().getOffsetHeight();
				LOGGER.info(newHeight + "px");
				if (newHeight < 0) {
					newHeight = 0;
				}
				myContent.setHeight(newHeight + "px");
			}
		});
	}


	/**
	 * Will invert the stack (close if opened, close if open).
	 */
	public void toggle() {
		if (!isExpended()) {
			expand();
		} else {
			close();
		}
	}


	public boolean isExpended() {
		return this.getStyleName().contains(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().expand());
	}


	public AccordionHeader getHeader() {
		return myHeader;
	}


	public AccordionContent getContent() {
		return myContent;
	}
}
