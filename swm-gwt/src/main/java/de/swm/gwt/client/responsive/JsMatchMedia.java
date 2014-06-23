package de.swm.gwt.client.responsive;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author Ed Bras
 */
class JsMatchMedia extends JavaScriptObject implements IMatchMedia {

	// Required by GWT.
	protected JsMatchMedia() {
	}

	public final HandlerRegistration addMatchMediaChangeHandler(IMatchMediaChangeHandler handler) {
		return (handler == null) ? null : addHandler(handler);
	}

	// @formatter:off
	public final native boolean hasMatch() /*-{ return this.matches; }-*/;

	public final native String getMedia() /*-{ return this.media; }-*/;

	private void forward(IMatchMediaChangeHandler handler) {
		handler.onMatchMediaChange(this);
	}
	
	private native JsHandlerRegistration addHandler(IMatchMediaChangeHandler handler) /*-{
		func = function(matchMedia) {handler.@de.swm.hrtool.client.util.MatchMediaChangeHandler::onMatchMediaChange(Lde/swm/hrtool/client/util/MatchMedia;)(matchMedia);}
		this.addListener(func);
		return {callback:func, match:this}; 
	}-*/;
	// @formatter:on

}
