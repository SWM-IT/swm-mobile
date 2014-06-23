package de.swm.gwt.client.responsive;


import com.google.gwt.core.shared.GWT;

/**
 * Wrapper for the javascript matchMedia function.<br>
 * Based on patch/contribution at <a href="https://code.google.com/p/google-web-toolkit/issues/detail?id=8399">GWT bug tracker</a> 
 * Use a polyfill in case the browser doesn't support this function (see links below).
 * 
 * Typical application example:
 * <pre>
 * MatchMediaFacade facade = MatchMediaFacade.getInstance();
 *		
 * IMatchMedia minWidth = facade.match("(min-width: 720px)");
 * minWidth.addMatchMediaChangeHandler(new IMatchMediaChangeHandler() {
 *			
 * 		@Override
 * 		public void onMatchMediaChange(IMatchMedia matchMedia) {
 * 			if (matchMedia.hasMatch() {
 * 			 	<implement responsive design functionality>
 * 			}
 * 		}
 * });
 * </pre>
 *  
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/window.matchMedia">Js MatchMedia</a>
 * @see <a href="https://github.com/paulirish/matchMedia.js">Polyfill</a>
 * @see <a href="https://hacks.mozilla.org/2012/06/using-window-matchmedia-to-do-media-queries-in-javascript/">Example usages</a>
 * @see <a href="http://caniuse.com/#search=matchMedia">Browser support</a>
 * @see <a href="http://css-tricks.com/enquire-js-media-query-callbacks-in-javascript/">Match Media alternative JS lib.</a>
 *
 * @author Ed Bras
 */
public final class MatchMediaFacade {

	private MatchMediaFacade() {
	}

	public static MatchMediaFacade getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public native boolean isSupported() /*-{ return $wnd.matchMedia != null || $wnd.msMatchMedia != null; }-*/;

	public IMatchMedia match(final String mediaQuery) {
		return (mediaQuery == null) ? null : matchIntern(mediaQuery);
	}

	private native JsMatchMedia matchIntern(String mediaQuery) /*-{ return $wnd.matchMedia(mediaQuery); }-*/;

	private interface InstanceHolder {
		MatchMediaFacade INSTANCE = GWT.create(MatchMediaFacade.class);
	}

}
