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
package de.swm.commons.mobile.client.orientation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.logging.Logger;


/**
 * Controller that supports orientation changes.
 * 
 */
public class OrientationController {

	private static final Logger LOGGER = Logger.getLogger(OrientationChangeHandler.class.getName());

	private static final int DEGREES_90 = 90;
	private static final int DEGREES_180 = 180;
	private static Orientation currentOrientation;
	private static JavaScriptObject nativeJsFunction;
	private static final EventBus MANAGER = new SimpleEventBus();

	static {
		if (GWT.isClient()) {
			setupOrientation();
		}

	}



	/**
	 * Add a orientation handler to detect the device orientation
	 * 
	 * @param handler
	 *            the handler to add {@link  OrientationChangeHandler} .
	 * @return a {@link com.google.gwt.event.shared.HandlerRegistration} object.
	 */
	public static HandlerRegistration addOrientationChangeHandler(OrientationChangeHandler handler) {
		return MANAGER.addHandler(OrientationChangeEvent.getType(), handler);
	}



	/**
	 * Get the current orientation of the device
	 * 
	 * @return the current orientation of the device
	 */
	public static Orientation getOrientation() {

		if (!orientationSupport()) {
			int height = Window.getClientHeight();
			int width = Window.getClientWidth();

			if (width > height) {
				return Orientation.LANDSCAPE;
			} else {
				return Orientation.PORTRAIT;
			}

		} else {
			int orientation = testBrowserOrientationSupport();

			Orientation o;
			switch (orientation) {
				case 0:
				case DEGREES_180:
					o = Orientation.PORTRAIT;
					break;

				case DEGREES_90:
				case -DEGREES_90:
					o = Orientation.LANDSCAPE;
					break;

				default:
					throw new IllegalStateException("this should not happen!?");
			}

			return o;
		}

	}



	/**
	 * Fires an orientation change event to the event bus.
	 * 
	 * @param orientation
	 *            the orientation
	 */
	private static void fireOrientationChangedEvent(Orientation orientation) {
		MANAGER.fireEvent(new OrientationChangeEvent(orientation));
	}



	/**
	 * Will test if the browser supports orientation events natively.
	 * 
	 * @return true is supported.
	 */
	private static native int testBrowserOrientationSupport()/*-{
																if (typeof ($wnd.orientation) == 'undefined') {
																return 0;
																}

																return $wnd.orientation;
																}-*/;



	/**
	 * Will be called when the orientation will change.
	 * 
	 * @param orientation
	 *            orientation value in degrees
	 */
	private static void onorientationChange(int orientation) {

		Orientation o;
		switch (orientation) {
			case 0:
			case DEGREES_180:
				o = Orientation.PORTRAIT;
				break;

			case DEGREES_90:
			case -DEGREES_90:
				o = Orientation.LANDSCAPE;
				break;

			default:
				LOGGER.info("on orientation changed called with invalid value: '" + orientation
						+ "' - defaulting to Portrait");
				o = Orientation.PORTRAIT;
				break;
		}
		currentOrientation = o;
		fireOrientationChangedEvent(o);

	}



	/**
	 * Determine if the device provides Orientation support
	 * 
	 * @return true if provided.
	 */
	private static boolean orientationSupport() {
		return SWMMobile.getOsDetection().isIPad() || SWMMobile.getOsDetection().isIPhone();
	}



	/**
	 * Set up orientation support.
	 */
	private static void setupOrientation() {

		if (!orientationSupport()) {
			Window.addResizeHandler(new ResizeHandler() {

				@Override
				public void onResize(ResizeEvent event) {
					Orientation orientation = getOrientation();
					if (orientation != currentOrientation) {
						currentOrientation = orientation;
						fireOrientationChangedEvent(orientation);
					}
				}
			});
		} else {
			nativeJsFunction = setupBrowserNativeOrientationSupport();
			Window.addCloseHandler(new CloseHandler<Window>() {

				@Override
				public void onClose(CloseEvent<Window> event) {
					destroyOrientation(nativeJsFunction);

				}
			});
		}

	}



	/**
	 * Will register an orientation change handler to support native browser orientation cahnge event.
	 * 
	 * @return true if supported
	 */
	private static native JavaScriptObject setupBrowserNativeOrientationSupport()/*-{

																					var func = $entry(function() {
																					@de.swm.commons.mobile.client.orientation.OrientationController::onorientationChange(I)($wnd.orientation);
																					});
																					$doc.body.onorientationchange = func;
																					$doc.addEventListener("orientationChanged", func);
																					return func;
																					}-*/;



	/**
	 * Unregister all orientation change events.
	 * 
	 * @param o
	 *            the js-event handler.
	 */
	private static native void destroyOrientation(JavaScriptObject o)/*-{
																		$doc.body.onorientationchange = null;
																		$doc.removeEventListener("orientationChanged", o);
																		}-*/;

}
