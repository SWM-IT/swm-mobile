package de.swm.mobile.kitchensink.client.components;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class PopupPanel extends SimplePanel {
	
	public PopupPanel() {
	    super();
//	    Element div = DOM.createDiv();
//	    super.getContainerElement().appendChild(div);
	}
	
	public void show() {
        DOM.setStyleAttribute(getElement(), "position", "absolute");
        int left = (Window.getClientWidth() - getOffsetWidth()) >> 1;
        int top = (Window.getClientHeight() - getOffsetHeight()) >> 1;
        setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(Window.getScrollTop() + top, 0));
        RootPanel.get().add(this);
	}
	
	public void hide() {
        RootPanel.get().remove(this);
	}
	
	/**
	 * Sets the popup's position relative to the browser's client area. The
	 * popup's position may be set before calling {@link #show()}.
	 * 
	 * @param left
	 *            the left position, in pixels
	 * @param top
	 *            the top position, in pixels
	 */
	public void setPopupPosition(int left, int top) {

		// Account for the difference between absolute position and the
		// body's positioning context.
		left -= Document.get().getBodyOffsetLeft();
		top -= Document.get().getBodyOffsetTop();

		// Set the popup's position manually, allowing setPopupPosition() to be
		// called before show() is called (so a popup can be positioned without
		// it
		// 'jumping' on the screen).
		Element elem = getElement();
		elem.getStyle().setPropertyPx("left", left);
		elem.getStyle().setPropertyPx("top", top);
	}

	/**
	 * Sets whether this object is visible. This method just sets the
	 * <code>visibility</code> style attribute. You need to call {@link #show()}
	 * to actually attached/detach the {@link PopupPanel} to the page.
	 * 
	 * @param visible
	 *            <code>true</code> to show the object, <code>false</code> to
	 *            hide it
	 */
	@Override
	public void setVisible(boolean visible) {
		DOM.setStyleAttribute(getElement(), "visibility", visible ? "visible"
				: "hidden");
	}

}
