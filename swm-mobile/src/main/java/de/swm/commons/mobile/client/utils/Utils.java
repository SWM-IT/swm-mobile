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
package de.swm.commons.mobile.client.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.setup.OsDetection;
import de.swm.gwt.client.exception.ApplicationRedirecter;

import java.util.logging.Logger;


/**
 * Utility, to e.g. write to the browser's console.
 */
public class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

	private static final double HVGA_PIXEL_RATIO = 1.5;
	private static final String FORM_CONTROLLS = "BUTTON INPUT SELECT TEXTAREA";


	/**
	 * Writes a debug message to the browser's console.
	 *
	 * @param msg message to be written
	 */
	public static void console(String msg) {
		LOGGER.info(msg);
	}

	/**
	 * Fuhrt ein Redirect der url durch.
	 *
	 * @param url die Zielurl
	 */
	public static void redirect(final String url) {
		ApplicationRedirecter.redirect(url);
	}


	/**
	 * Removes the Title bar on Adroid
	 */
	public static native void removeTitleBar() /*-{
        $wnd.scrollTo(0, 1);
    }-*/;

	/**
	 * A utility method to hide the soft keyboard
	 */
	public static void hideKeyBoard() {
		final Anchor anchor = new Anchor(" ");

		anchor.getElement().getStyle().setWidth(1, Style.Unit.PX);
		anchor.getElement().getStyle().setHeight(1, Style.Unit.PX);
		anchor.getElement().getStyle().setDisplay(Style.Display.INLINE);
		anchor.getElement().getStyle().setFloat(Style.Float.LEFT);

		RootPanel.get().add(anchor);
		anchor.setFocus(true);

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				anchor.removeFromParent();

			}
		});

	}

	/**
	 * Will re-redner a screen on android.
	 */
	public static void forceFullRepaint() {
		OsDetection d = SWMMobile.getOsDetection();
		if (d.isAndroid()) {
			final String zIndex = Document.get().getBody().getStyle().getZIndex();
			Document.get().getBody().getStyle().setZIndex(-1);

			new Timer() {

				@Override
				public void run() {
					Document.get().getBody().getStyle().setProperty("zIndex", zIndex);

				}

			}.schedule(50);

		}
	}


	/**
	 * Wtirts to the native log window.
	 *
	 * @param msg the message to write.
	 */
	private static native void consoleLogNative(String msg) /*-{
        var log = $doc.getElementById('log');
        if (log) {
            log.innerHTML = msg + '<br/>' + log.innerHTML;
        } else {
            if ($wnd.console) {
                $wnd.console.log(msg);
            }
        }
    }-*/;


	/**
	 * Adds an event listener to an HTML element.
	 *
	 * @param ele      the element
	 * @param event    the event
	 * @param capture  .
	 * @param listener the listener to add
	 * @return the java script object
	 */
	public static native JavaScriptObject addEventListener(Element ele, String event, boolean capture,
														   EventListener listener) /*-{
        var callBack = function (e) {
            listener.@com.google.gwt.user.client.EventListener::onBrowserEvent(Lcom/google/gwt/user/client/Event;)(e);
        };
        ele.addEventListener(event, callBack, capture);
        return callBack;
    }-*/;


	/**
	 * Removes (if exiting) and adds an event listener to an HTML element.
	 *
	 * @param ele      the element
	 * @param event    the event
	 * @param capture  .
	 * @param listener the listener to add
	 */
	public static native void addEventListenerOnce(Element ele, String event, boolean capture, EventListener listener) /*-{
        var callBack = function (e) {
            ele.removeEventListener(event, callBack, capture);
            listener.@com.google.gwt.user.client.EventListener::onBrowserEvent(Lcom/google/gwt/user/client/Event;)(e);
        };
        ele.addEventListener(event, callBack, capture);
    }-*/;


	/**
	 * Removes an event listener form an HTML element.
	 *
	 * @param ele      the element
	 * @param event    the event
	 * @param capture  .
	 * @param callBack call back if removed
	 */
	public static native void removeEventListener(Element ele, String event, boolean capture, JavaScriptObject callBack) /*-{
        ele.removeEventListener(event, callBack, capture);
    }-*/;

	private static Element htmlNode = DOM.createElement("DIV");


	/**
	 * Returns true if the passed HTML element is a Form element.
	 *
	 * @param ele the html element
	 * @return true if form control
	 */
	public static boolean isHtmlFormControl(Element ele) {
		if (ele == null) {
			return false;
		}
		String nodeName = ele.getNodeName().toUpperCase();
		String roleName = ele.getAttribute("role").toUpperCase();
		return FORM_CONTROLLS.contains(nodeName) || roleName.length() > 0 && FORM_CONTROLLS.contains(roleName)
				|| isHtmlFormControl(ele.getParentElement());
	}


	/**
	 * Retunns the current active HTML Element.
	 *
	 * @return the html element
	 */
	public static native Element getActiveElement() /*-{
        return $doc.activeElement;
    }-*/;


	/**
	 * Bestimmt ob es sich um eine WVGA ausloesung handelt.
	 *
	 * @return true wenn WVGA ausloesung
	 */
	public static boolean isWVGA() {
		int ratio = getDevicePixelRatio();
		if (ratio == HVGA_PIXEL_RATIO) {
			return true;
		}
		return false;
	}


	/**
	 * Returns the device pixel ratio.
	 *
	 * @return the device pixel ratio.
	 */
	public static native int getDevicePixelRatio() /*-{
        if (typeof $wnd.devicePixelRatio != 'undefined') {
            return $wnd.devicePixelRatio;
        } else {
            return 1;
        }
    }-*/;


	/**
	 * Moves an element on x-axis.
	 *
	 * @param ele   the element.
	 * @param value the value in px
	 */
	public static native void setTranslateX(Element ele, double value) /*-{
        if (typeof ele.style.webkitTransform != 'undefined') {
            ele.style.webkitTransform = "translate3d(" + value + "px, 0px, 0px)";
        } else {
            if (typeof ele.style.transform != 'undefined') {
                ele.style.transform = "translate3d(" + value + "px, 0px, 0px)";
            }
        }
    }-*/;


	/**
	 * Returns the x position of an element.
	 *
	 * @param ele the element
	 * @return the position
	 */
	public static native int getTranslateX(Element ele) /*-{
        var transform;
        var translateX = 0;
        if (typeof ele.style.webkitTransform != 'undefined') {
            transform = ele.style.webkitTransform;
        } else {
            if (typeof ele.style.transform != 'undefined') {
                transform = ele.style.transform;
            }
        }

        if (transform && transform !== "") {
            translateX = parseInt((/translate3d\((\-?.*)px, 0px, 0px\)/).exec(transform)[1]);
        }
        return translateX;
    }-*/;


	/**
	 * Moves an element on y-axis.
	 *
	 * @param ele   the element.
	 * @param value the value in px
	 */
	public static native void setTranslateY(Element ele, double value) /*-{
        if (typeof ele.style.webkitTransform != 'undefined') {
            ele.style.webkitTransform = "translate3d(0px, " + value + "px ,0px)";
        } else {
            if (typeof ele.style.transform != 'undefined') {
                ele.style.transform = "translate3d(0px, " + value + "px ,0px)";
            }
        }
    }-*/;


	/**
	 * Returns the y position of an element.
	 *
	 * @param ele the element
	 * @return the position
	 */
	public static native int getTranslateY(Element ele) /*-{
        var transform;
        var translateY = 0;
        if (typeof ele.style.webkitTransform != 'undefined') {
            transform = ele.style.webkitTransform;
        } else {
            if (typeof ele.style.transform != 'undefined') {
                transform = ele.style.transform;
            }
        }

        if (transform && transform !== "") {
            translateY = parseInt((/translate3d\(0px, (\-?.*)px, 0px\)/).exec(transform)[1]);
        }
        return translateY;
    }-*/;


	/**
	 * Returns the sixth 2D vector value. WebKitCSSMatrix objects represent a 4x4 homogeneous matrix for 3D transforms
	 * or a vector for 2D transforms. You can use these objects to manipulate matrices in JavaScript. For example, you
	 * can multiply, translate, and scale matrices.
	 *
	 * @param ele the element.
	 * @return the value
	 */
	public static native int getMatrixY(Element ele) /*-{
        var matrix;
        if (typeof window.getComputedStyle(ele).webkitTransform != 'undefined') {
            matrix = new WebKitCSSMatrix(window.getComputedStyle(ele).webkitTransform);
        } else {
            if (typeof window.getComputedStyle(ele).transform != 'undefined') {
                matrix = new MSCSSMatrix(window.getComputedStyle(ele).transform);
            }
        }
        return matrix.f;
    }-*/;


	/**
	 * Sets a duration of an Transition.
	 *
	 * @param ele   the element.
	 * @param value the value
	 */
	public static native void setTransitionDuration(Element ele, double value) /*-{
        if (typeof ele.style.webkitTransitionDuration != 'undefined') {
            ele.style.webkitTransitionDuration = "" + value + "ms";
        } else {
            if (typeof ele.style.transitionDuration != 'undefined') {
                ele.style.transitionDuration = "" + value + "ms";
            }
        }
    }-*/;

	public static native String getTransitionEventName(Element ele) /*-{
        if (typeof ele.style.webkitTransition != 'undefined') {
            return "webkitTransitionEnd";
        } else {
            if (typeof ele.style.transition != 'undefined') {
                return "transitionend";
            }
        }
        return "undefined";
    }-*/;

	/**
	 * Computes the height of passed HTML element.
	 *
	 * @param ele the height.
	 * @return the height in px
	 */
	public static native int getHeight(Element ele) /*-{
        return parseInt($doc.defaultView.getComputedStyle(ele, "").getPropertyValue("height"));
    }-*/;


	/**
	 * Computes the width of passed HTML element.
	 *
	 * @param ele the width.
	 * @return the width in px
	 */
	public static native int getWidth(Element ele) /*-{
        return parseInt($doc.defaultView.getComputedStyle(ele, "").getPropertyValue("width"));
    }-*/;


	/**
	 * Retuns the index of the target relative to his parent.
	 *
	 * @param parent the paent.
	 * @param target the target element.
	 * @return the child index.
	 */
	public static int getTargetItemIndex(Element parent, EventTarget target) {
		Element div = Element.as(target);
		if (div == parent) {
			return -1;
		}
		while (div.getParentElement() != parent) {
			div = div.getParentElement();
			if (div == null) {
				return -1;
			}
		}
		return DOM.getChildIndex((com.google.gwt.user.client.Element) parent,
				(com.google.gwt.user.client.Element) div);
	}


	/**
	 * Returns true if class1 ist sublass of class2
	 *
	 * @param class1 .
	 * @param class2 .
	 * @return ture if subclass.
	 */
	public static boolean isSubclassOf(Class<?> class1, Class<?> class2) {
		while (class1 != null) {
			if (class1 == class2) {
				return true;
			}
			class1 = class1.getSuperclass();
		}
		return false;
	}

}
