package de.swm.commons.mobile.client.event;

import com.google.gwt.event.dom.client.*;
import de.swm.commons.mobile.client.SWMMobile;

/**
 * Will add a click event as TochStart event to widgets on mobile system. On desktop click hanlder will be added.
 * On mobile systems touch start handler will be used.
 *
 * @author Daniel Wiese
 */
public class FastClickHelper {

	public interface FastClickHandler {
		void onFastClick(FastClickEvent event);
	}

	public interface FastClickEvent {

		void preventDefault();

		void stopPropagation();
	}


	/**
	 * Will add a click or touch hander (on mobile systems) to the application.
	 *
	 * @param toWidget     the widget to add
	 * @param handlerToAdd the fast click handler to add
	 */

	public static <T extends HasTouchStartHandlers & HasClickHandlers> void addClickHandler(T toWidget, final FastClickHandler handlerToAdd) {
		if (SWMMobile.getOsDetection().isDesktop()) {
			if (toWidget instanceof HasClickHandlers) {
				final HasClickHandlers cast = (HasClickHandlers) toWidget;
				cast.addClickHandler(wrapAsClickHandler(handlerToAdd));
			}
		} else {
			if (toWidget instanceof HasTouchStartHandlers) {
				HasTouchStartHandlers cast = (HasTouchStartHandlers) toWidget;
				cast.addTouchStartHandler(wrapAsTouchHandler(handlerToAdd));
			} else {
				//fallback
				final HasClickHandlers cast = (HasClickHandlers) toWidget;
				cast.addClickHandler(wrapAsClickHandler(handlerToAdd));
			}
		}
	}

	/**
	 * Wraps a fast click Handler to a click Hanlder
	 * @param handlerToWrap   the ahndler to wrap
	 * @return the fast click hanlder
	 */
	public static FastClickHandler wrapAsFastClickHandler(final ClickHandler handlerToWrap) {
		return new FastClickHandler() {
			@Override
			public void onFastClick(FastClickEvent event) {
				handlerToWrap.onClick(null);
			}
		};
	}

	private static ClickHandler wrapAsClickHandler(final FastClickHandler handlerToWrap) {
		return new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				event.stopPropagation();
				event.preventDefault();
				final FastClickEvent eventWrapped = new FastClickEvent() {
					@Override
					public void preventDefault() {
						event.preventDefault();
					}

					@Override
					public void stopPropagation() {
						event.stopPropagation();
					}
				};
				handlerToWrap.onFastClick(eventWrapped);
			}
		};
	}

	private static TouchStartHandler wrapAsTouchHandler(final FastClickHandler handlerToWrap) {
		return new TouchStartHandler() {
			@Override
			public void onTouchStart(final TouchStartEvent event) {
				event.stopPropagation();
				event.preventDefault();
				final FastClickEvent eventWrapped = new FastClickEvent() {
					@Override
					public void preventDefault() {
						event.preventDefault();
					}

					@Override
					public void stopPropagation() {
						event.stopPropagation();
					}
				};
				handlerToWrap.onFastClick(eventWrapped);
			}


		};
	}


}
