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
package de.swm.commons.mobile.client.widgets.map.impl;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import org.gwtopenmaps.openlayers.client.Map;

import java.util.logging.Logger;

/**
 * Controls the zoom level of the map. 
 *
 */
public class ZoomControlPanel extends VerticalPanel {

	private static final Logger LOGGER = Logger.getLogger(ZoomControlPanel.class.getName());
	private static final String OPEN_LAYER_MAP_WIDGET_ID = "ol-map-widget";

	/**
	 * Default constructor.
	 * @param map the map
	 */
	public ZoomControlPanel(final Map map) {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().zoomControlPanel());
		HTML zoomIn = new HTML("+");
		zoomIn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				map.zoomTo(map.getZoom() + 1);
			}
		});
		add(zoomIn);
		HTML zoomOut = new HTML("-");
		zoomOut.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				map.zoomTo(map.getZoom() - 1);
			}
		});
		add(zoomOut);
		
	}
	
	/**
	 * Inits the control.
	 */
	public void init() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				Element mapWidget = (Element) Document.get().getElementById(OPEN_LAYER_MAP_WIDGET_ID); // assume only one map!
				if (mapWidget != null) {
					DOM.appendChild(mapWidget, getElement());
					onAttach();
				} else {
					LOGGER.info("warning: ZoomControl.init could not find map widget in DOM");
				}
			}
		});
	}
	
	/**
	 * Destroys the control.
	 */
	public void destroy() {
		Element mapWidget = (Element) Document.get().getElementById(OPEN_LAYER_MAP_WIDGET_ID); // assume only one map!
		if (mapWidget != null) {
			onDetach();
			DOM.removeChild(mapWidget, getElement());
		}		
	}
	
}
