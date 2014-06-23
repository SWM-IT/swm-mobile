package de.swm.gwt.client.responsive;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author Ed Bras
 */
class JsHandlerRegistration extends JavaScriptObject implements HandlerRegistration {

	// Required by GWT.
	protected JsHandlerRegistration() {
	}

	public final void removeHandler() {
		removeHandlerIntern();
	}

	private native JavaScriptObject getCallback() /*-{ return this.callback; }-*/;

	private native void removeHandlerIntern() /*-{ this.match.removeListener(this.callback); }-*/;

	private native JsMatchMedia getMatchMedia() /*-{ return this.match; }-*/;

}
