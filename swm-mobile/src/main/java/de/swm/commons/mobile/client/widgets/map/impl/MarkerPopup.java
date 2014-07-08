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
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.Pixel;
import org.gwtopenmaps.openlayers.client.event.MapZoomListener;



/**
 * Popup Map Widget.
 * 
 * @author wiese.daniel
 * 
 */
public class MarkerPopup extends VerticalPanel {

	/**
	 * Anchor position.
	 * 
	 * @author wiese.daniel
	 */
	public enum Anchor {
		/** Pos. **/
		LEFT_TOP,
		/** Pos. **/
		LEFT_BOTTOM,
		/** Pos. **/
		RIGHT_TOP,
		/** Pos. **/
		RIGHT_BOTTOM,
		/** Pos. **/
		CENTER,
		/** Pos. **/
		CENTER_TOP,
		/** Pos. **/
		CENTER_BOTTOM
	}

	private static final int ELEMENT_Z_INDEX = 500;
	private static final String OPEN_LAYER_MAP_WIDGET_ID = "ol-map-widget";
	private static final String OL_CONTAINER_SUFFIX = "OpenLayers_Container";

	private Element mapContainer;
	private Map map;
	private LonLat position;
	private int offsetX, offsetY;
	private Anchor anchor;
	private HorizontalPanel contentPanel;
	private boolean isVisible = false;
	private MapZoomListener zoomListener;
	private boolean initialized = false;
	private boolean zoomPending = false;



	/**
	 * Default constructor.
	 * 
	 * @param m
	 *            the map
	 * @param pos
	 *            the pos
	 * @param offX
	 *            the X offset
	 * @param offY
	 *            the Y offset
	 * @param anchor
	 *            the anchor
	 */
	public MarkerPopup(Map m, LonLat pos, int offX, int offY, Anchor anchor) {
		this.map = m;
		this.position = pos;
		this.offsetX = offX;
		this.offsetY = offY;
		if (anchor == null) {
			this.anchor = Anchor.LEFT_TOP;
		} else {
			this.anchor = anchor;
		}
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().markerPopupPanelParent());
		contentPanel = new HorizontalPanel();
		contentPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().markerPopupPanelContent());
		HorizontalPanel iPanel;
		HTML indicator;
		switch (this.anchor) {
			case CENTER_BOTTOM:
				contentPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().shadowTop());
				add(contentPanel);
				iPanel = new HorizontalPanel();
				indicator = new HTML();
				indicator.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().markerPopupPanelIndicatorDown());
				iPanel.add(indicator);
				add(iPanel);
				break;
			case CENTER_TOP:
				iPanel = new HorizontalPanel();
				indicator = new HTML();
				indicator.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().markerPopupPanelIndicatorUp());
				iPanel.add(indicator);
				add(iPanel);
				contentPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().shadowBottom());
				add(contentPanel);
				break;
			case CENTER:
			case LEFT_BOTTOM:
			case RIGHT_BOTTOM:
				contentPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().shadowTop());
				add(contentPanel);
				break;
			case LEFT_TOP:
			case RIGHT_TOP:
				contentPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getMapCss().shadowBottom());
				add(contentPanel);
				break;
			default:
				break;
		}
		getElement().getStyle().setDisplay(Display.NONE);
		zoomListener = new MapZoomListener() {

			public void onMapZoom(MapZoomEvent eventObject) {
				if (isVisible) {
					Pixel relPos = map.getPixelFromLonLat(position);
					hide();
					setPosition(relPos.x(), relPos.y());
					show();
				} else {
					zoomPending = true;
				}
			}
		};
		map.addMapZoomListener(zoomListener);
	}



	public HasWidgets getContentArea() {
		return contentPanel;
	}



	/**
	 * Destroys the popup.
	 */
	public void destroy() {
		map.removeListener(zoomListener);
		clear();
		if (initialized) {
			onDetach();
			DOM.removeChild(mapContainer, getElement());
		}
		mapContainer = null;
		map = null;
		position = null;
		isVisible = false;
		zoomListener = null;
		initialized = false;
		zoomPending = false;
	}



	/**
	 * Shows th popup.
	 */
	public void show() {
		if (!initialized || zoomPending) {
			Pixel relPos = map.getPixelFromLonLat(position);
			setPosition(relPos.x(), relPos.y());
			getElement().getStyle().clearDisplay();
			zoomPending = false;
		} else {
			getElement().getStyle().clearDisplay();
		}
		isVisible = true;
	}



	/**
	 * Shows the popup tht the rigt offset
	 * 
	 * @param x
	 *            x offset
	 * @param y
	 *            y offset
	 */
	public void showAt(int x, int y) {
		setPosition(x, y);
		getElement().getStyle().clearDisplay();
		isVisible = true;
	}



	/**
	 * Hides the popup.
	 */
	public void hide() {
		getElement().getStyle().setDisplay(Display.NONE);
		isVisible = false;
	}



	/**
	 * Toggels the visibility.
	 */
	public void toggleVisibility() {
		if (isVisible) {
			hide();
		} else {
			show();
		}
	}



	/**
	 * Separate method to compensate race conditions with DOM structure of OL map widget
	 */
	private void attach() {
		Element mapWidget = (Element) Document.get().getElementById(OPEN_LAYER_MAP_WIDGET_ID); // assume only one map!
		if (mapWidget != null) {
			Element child = DOM.getFirstChild(mapWidget);
			while (child != null) {
				String id = child.getPropertyString("id");
				if (id != null && id.endsWith(OL_CONTAINER_SUFFIX)) {
					mapContainer = child;
					DOM.appendChild(mapContainer, getElement());
					onAttach();
					break;
				} else {
					child = DOM.getFirstChild(child);
				}
			}
			assert mapContainer != null : "Map Container für MarkerPopup nicht gesetzt";
			initialized = true;
		}
	}



	/**
	 * Sts the potion of the popup.
	 * 
	 * @param x
	 *            the x offset
	 * @param y
	 *            the y offset
	 */
	private void setPosition(final int x, final int y) {
		if (!initialized) {
			attach();
			// display popup behind map to enforce correct calculation of size and bounds
			getElement().getStyle().setZIndex(-1);
			getElement().getStyle().clearDisplay();
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			public void execute() {
				int posX = x - mapContainer.getOffsetLeft() + offsetX;
				int posY = y - mapContainer.getOffsetTop() + offsetY;
				switch (anchor) {
					case LEFT_TOP:
						// nothing to do
						break;
					case LEFT_BOTTOM:
						posY -= getElement().getOffsetHeight();
						break;
					case RIGHT_TOP:
						posX -= getElement().getOffsetWidth();
						break;
					case RIGHT_BOTTOM:
						posX -= getElement().getOffsetWidth();
						posY -= getElement().getOffsetHeight();
						break;
					case CENTER:
						posX -= getElement().getOffsetWidth() / 2;
						posY -= getElement().getOffsetHeight() / 2;
						break;
					case CENTER_TOP:
						posX -= getElement().getOffsetWidth() / 2;
						break;
					case CENTER_BOTTOM:
						posX -= getElement().getOffsetWidth() / 2;
						posY -= getElement().getOffsetHeight();
						break;
					default:
						break;
				}
				getElement().getStyle().setLeft(posX, Unit.PX);
				getElement().getStyle().setTop(posY, Unit.PX);
				getElement().getStyle().setZIndex(ELEMENT_Z_INDEX); // put popup in front of map
			}
		});
	}

}
