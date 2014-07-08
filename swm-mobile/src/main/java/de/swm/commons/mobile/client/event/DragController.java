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
package de.swm.commons.mobile.client.event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.utils.Point;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;



/**
 * Is handling HTML5 drag events. The sublassed {@link DragControllerMobile} and {@link DragControllerDesktop} are
 * plantfom specific.
 */
public abstract class DragController implements EventListener {

	private static final Logger LOGGER = Logger.getLogger(DragController.class.getName());


	private List<DragEventsHandler> dragEventHandlers = new ArrayList<DragEventsHandler>();
	private List<SwipeEventsHandler> swipeEventHandlers = new ArrayList<SwipeEventsHandler>();
	private DragEventsHandler capturingDragEventsHandler = null;
	private SwipeEventsHandler capturingSwipeEventsHandler = null;
	private Widget source;
	private boolean isDown = false;
	private boolean suppressNextClick = false;
	private long lastDragTimeStamp = 0;
	private Point lastDragPos = new Point(0, 0);
	private long currDragTimeStamp = 0;
	private Point currDragPos = new Point(0, 0);
	private JavaScriptObject clickListener;
	private Point dragStartPos = new Point(0, 0);

	private JavaScriptObject dragStartListener;
	private JavaScriptObject dragMoveListener;
	private JavaScriptObject dragEndListener;

	private final DragControllerOptions options = new DragControllerOptions();
	private static final DragController INSTANCE = GWT.create(DragController.class);



	/**
	 * Default constructor.
	 */
	DragController() {
		LOGGER.info("New DragController instance created");
		init();
	}



	/**
	 * Returns the options.
	 * 
	 * @return the options
	 */
	public DragControllerOptions getOptions() {
		return options;
	}



	/**
	 * Returns the singleton instance of this controller.
	 * 
	 * @return the singleton instance.
	 */
	public static DragController get() {
		return INSTANCE;
	}



	/**
	 * Initializes the controller.
	 */
	protected void init() {
		source = RootLayoutPanel.get();
		registerEvents();
	}



	/**
	 * Allow components to register drag events.
	 * 
	 * @param dragHandler
	 *            the handler.
	 */
	public void addDragEventsHandler(DragEventsHandler dragHandler) {
		dragEventHandlers.add(dragHandler);
	}



	/**
	 * Allow components to register swipe events.
	 * 
	 * @param swipeHandler
	 *            the handler.
	 */
	public void addSwipeEventsHandler(SwipeEventsHandler swipeHandler) {
		swipeEventHandlers.add(swipeHandler);
	}



	/**
	 * Allow components to remove registered drag events.
	 * 
	 * @param dragHandler
	 *            the handler.
	 */
	public void removeDragEventsHandler(DragEventsHandler dragHandler) {
		dragEventHandlers.remove(dragHandler);
	}



	/**
	 * Allow components to remove registered swipe events.
	 * 
	 * @param swipeHandler
	 *            the handler.
	 */
	public void removeSwipeEventHandler(SwipeEventsHandler swipeHandler) {
		swipeEventHandlers.remove(swipeHandler);
	}



	@Override
	public void onBrowserEvent(Event e) {
		String type = e.getType();
		if (type.equals("click")) {
			onClick(e);
		}
	}



	/**
	 * Will be invoked after a click event.
	 * 
	 * @param e
	 *            event.
	 */
	private void onClick(Event e) {
		if (suppressNextClick) {
			if (!options.isEnableNativeEventPropagation()) {
				e.stopPropagation();
			}
			suppressNextClick = false;
		}
	}



	/**
	 * May need an onPreStart event to indicate that mouse is down, but no movement yet, // so onStart event can
	 * actually mean drag is indeed started.
	 * 
	 * @param e
	 *            event
	 * @param p
	 *            point
	 */
	protected void onStart(Event e, Point p) {
		isDown = true;
		suppressNextClick = false;
		Date currentDateTime = new Date();
		lastDragTimeStamp = currentDateTime.getTime();
		currDragTimeStamp = lastDragTimeStamp;
		lastDragPos.clone(p);
		currDragPos.clone(p);
		DragEvent dragEvent = new DragEvent(e, DragEvent.Type.Start, p.xPos(), p.yPos(), p.xPos() - currDragPos.xPos(), p.yPos()
				- currDragPos.yPos());
		fireDragEvent(dragEvent);
		
		dragStartPos.clone(p);
	}



	/**
	 * Will be invoked after an element will be moved.
	 * 
	 * @param e
	 *            the event
	 * @param p
	 *            the new position
	 */
	protected void onMove(Event e, Point p) {
		if (isDown) {
			if (p.equals(currDragPos)) {
				return;
			}
			// suppress next click only of move distance larger than specified radius
			// otherwise e.g. buttons do not receive click events when finger/mouse is moved on the button
			if (	(Math.abs(p.xPos() - dragStartPos.xPos()) > options.getSuppressClickRadius()) ||
					(Math.abs(p.yPos() - dragStartPos.yPos()) > options.getSuppressClickRadius()) ) {
				suppressNextClick = true;
			} 
			
			DragEvent dragEvent = new DragEvent(e, DragEvent.Type.Move, p.xPos(), p.yPos(), p.xPos() - currDragPos.xPos(), p.yPos()
					- currDragPos.yPos());
			fireDragEvent(dragEvent);
			lastDragPos.clone(currDragPos);
			lastDragTimeStamp = currDragTimeStamp;
			currDragPos.clone(p);
			Date currentDateTime = new Date();
			currDragTimeStamp = currentDateTime.getTime();
		}
	}



	/**
	 * Will be invoked after the movement of an element is completed.
	 * 
	 * @param e
	 *            the event
	 * @param p
	 *            the new position
	 */
	protected void onEnd(Event e, Point p) {
		if (isDown) {
			isDown = false;
			DragEvent dragEvent = new DragEvent(e, DragEvent.Type.End, p.xPos(), p.yPos(), p.xPos() - currDragPos.xPos(), p.yPos()
					- currDragPos.yPos());
			fireDragEvent(dragEvent);
			double distanceX = p.xPos() - lastDragPos.xPos();
			double distanceY = p.yPos() - lastDragPos.yPos();
			double distance;
			SwipeEvent.Type swipeType;
			if (Math.abs(distanceX) > Math.abs(distanceY)) {
				distance = distanceX;
				swipeType = SwipeEvent.Type.Horizontal;
			} else {
				distance = distanceY;
				swipeType = SwipeEvent.Type.Vertical;
			}
			
			Date currentDateTime = new Date();
			long time = currentDateTime.getTime() - lastDragTimeStamp;
			double speed = distance / time;

			if (speed > options.getMaxSpeed()) {
				speed = options.getMaxSpeed();
			} else if (speed < options.getMinSpeed()) {
				speed = options.getMinSpeed();
			}
			if (Math.abs(speed) > options.getMinSwipeSpeed()) {
				SwipeEvent swipeEvent = new SwipeEvent(e, swipeType, speed);
				fireSwipeEvent(swipeEvent);
			}
		}
	}



	/**
	 * Fires a drag event after a drag event was recognized.
	 * 
	 * @param e
	 *            event
	 */
	protected void fireDragEvent(DragEvent e) {
		if (capturingDragEventsHandler != null) {
			e.dispatch(capturingDragEventsHandler);
			return;
		}
		EventTarget target = e.getNativeEvent().getEventTarget();
		Node node = Node.as(target);
		if (!Element.is(node)) {
			node = node.getParentNode(); // Text node
		}
		if (Element.is(node)) {
			Element ele = Element.as(target);
			int count = 0;
			while (ele != null) {
				for (DragEventsHandler handler : dragEventHandlers) {
					if (ele.equals(handler.getElement())) {
						e.dispatch(handler);
						count++;
						if (e.getStopPropagation() || count == dragEventHandlers.size()) {
							return;
						}
					}
				}
				ele = ele.getParentElement();
			}
		}
	}



	/**
	 * Fires a drag swipe event after a drag event was recognized.
	 * 
	 * @param e
	 *            event
	 */
	protected void fireSwipeEvent(SwipeEvent e) {
		if (capturingSwipeEventsHandler != null) {
			e.dispatch(capturingSwipeEventsHandler);
			return;
		}
		if (capturingDragEventsHandler != null) {
			return;
		}
		EventTarget target = e.getNativeEvent().getEventTarget();
		Node node = Node.as(target);
		if (!Element.is(node)) {
			node = node.getParentNode(); // Text node
		}
		if (Element.is(node)) {
			Element ele = Element.as(target);
			int count = 0;
			while (ele != null) {
				for (SwipeEventsHandler handler : swipeEventHandlers) {
					if (ele.equals(handler.getElement())) {
						e.dispatch(handler);
						count++;
						if (e.getStopPropagation() || count == swipeEventHandlers.size()) {
							return;
						}
					}
				}
				ele = ele.getParentElement();
			}
		}
	}



	/**
	 * Will supressed next click.
	 */
	public void suppressNextClick() {
		suppressNextClick = true;
	}
	
	
	/**
	 * Check if next click is suppressed.
	 * 
	 * 
	 */
	public boolean isNextClickSuppressed() {
		return suppressNextClick;
	}
	


	/**
	 * Subscribes base browser events - equal for all DragController implementations..
	 */
	protected void registerEvents() {
		if (clickListener == null) {
			clickListener = Utils.addEventListener(source.getElement(), "click", true, this);
		}
	}



	/**
	 * un-Subscribes base browser events - equal for all DragController implementations..
	 */
	protected void unregisterEvents() {
		if (clickListener != null) {
			Utils.removeEventListener(source.getElement(), "click", true, clickListener);
			clickListener = null;
		}
	}



	/**
	 * Supends the drag controller.
	 */
	public void suspend() {
		unregisterEvents();
	}



	/**
	 * Enabeld the drag controller after an suspend.
	 */
	public void resume() {
		registerEvents();
	}



	/**
	 * Will capture drag events.
	 * 
	 * @param cachingHandler
	 *            the handler.
	 * @return true if already captured
	 */
	public boolean captureDragEvents(DragEventsHandler cachingHandler) {
		if (capturingDragEventsHandler != null) {
			return false;
		}
		capturingDragEventsHandler = cachingHandler;
		return true;
	}


	/**
	 * Will capture release drag events.
	 * 
	 * @param cachingHandler
	 *            the handler.
	 * @return true if already captured
	 */
	public boolean releaseDragCapture(DragEventsHandler cachingHandler) {
		if (capturingDragEventsHandler == null) {
			return true;
		}
		if (capturingDragEventsHandler != cachingHandler) {
			return false;
		}
		capturingDragEventsHandler = null;
		return true;
	}


	/**
	 * Will capture swipe events.
	 * 
	 * @param cachingHandler
	 *            the handler.
	 * @return true if already captured
	 */
	public boolean captureSwipeEvents(SwipeEventsHandler cachingHandler) {
		if (capturingSwipeEventsHandler != null) {
			return false;
		}
		capturingSwipeEventsHandler = cachingHandler;
		return true;
	}


	/**
	 * Will capture release swipe events.
	 * 
	 * @param cachingHandler
	 *            the handler.
	 * @return true if already captured
	 */
	public boolean releaseSwipeCapture(SwipeEventsHandler cachingHandler) {
		if (capturingSwipeEventsHandler == null) {
			return true;
		}
		if (capturingSwipeEventsHandler != cachingHandler) {
			return false;
		}
		capturingSwipeEventsHandler = null;
		return true;
	}



	/**
	 * Returns the dragStartListener.
	 * 
	 * @return the dragStartListener
	 */
	public JavaScriptObject getDragStartListener() {
		return dragStartListener;
	}



	/**
	 * dragStartListener the dragStartListener to set.
	 * 
	 * @param dragStartListener
	 *            the dragStartListener to set
	 */
	public void setDragStartListener(JavaScriptObject dragStartListener) {
		this.dragStartListener = dragStartListener;
	}



	/**
	 * Returns the dragMoveListener.
	 * 
	 * @return the dragMoveListener
	 */
	public JavaScriptObject getDragMoveListener() {
		return dragMoveListener;
	}



	/**
	 * dragMoveListener the dragMoveListener to set.
	 * 
	 * @param dragMoveListener
	 *            the dragMoveListener to set
	 */
	public void setDragMoveListener(JavaScriptObject dragMoveListener) {
		this.dragMoveListener = dragMoveListener;
	}



	/**
	 * Returns the dragEndListener.
	 * 
	 * @return the dragEndListener
	 */
	public JavaScriptObject getDragEndListener() {
		return dragEndListener;
	}



	/**
	 * dragEndListener the dragEndListener to set.
	 * 
	 * @param dragEndListener
	 *            the dragEndListener to set
	 */
	public void setDragEndListener(JavaScriptObject dragEndListener) {
		this.dragEndListener = dragEndListener;
	}



	/**
	 * Returns the source.
	 * 
	 * @return the source
	 */
	public Widget getSource() {
		return source;
	}



	/**
	 * source the source to set.
	 * 
	 * @param source
	 *            the source to set
	 */
	public void setSource(Widget source) {
		this.source = source;
	}

	public void setSuppressNextClick(boolean suppressNextClick) {
		this.suppressNextClick = suppressNextClick;
	}
}
