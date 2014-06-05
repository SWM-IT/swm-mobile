package de.swm.commons.mobile.client.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;

public class SimplePopup extends SimplePanel implements HasCloseHandlers<SimplePopup> {

	public class Position {
		private int left = -1;
		private int top = -1;
		private double width = -1; // use given width
		private double height = -1; // use given height

		/**
		 * Specify position of the popup.
		 * 
		 * @param left x-coordinate of position
		 * @param top y-coordinate of position
		 */
		public Position(int left, int top) {
			this.left = left;
			this.top = top;
		}

		/**
		 * Specify position and dimension of the popup.
		 * 
		 * @param left x-coordinate of position
		 * @param top y-coordinate of position
		 * @param width width of popup in percentage (0.0 < width < 1.0) or -1 for calculated width
		 * @param height height of popup in percentage (0.0 < width < 1.0) or -1 for calculated height
		 */
		public Position(int left, int top, double width, double height) {
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
		}

		public int getLeft() {
			return left;
		}

		public int getTop() {
			return top;
		}
		
		public double getWidth() {
			return width;
		}
		
		public double getHeight() {
			return height;
		}
	}

	/**
	 * A callback that is used to set the position of a {@link SimplePopup} right before it is shown.
	 */
	public interface PositionCallback {

		/**
		 * Provides the opportunity to set the position of the SimplePopup right before the SimplePopup is shown. The
		 * offsetWidth and offsetHeight values of the SimplePopup are made available to allow for positioning based on
		 * its size.
		 * 
		 * @param offsetWidth
		 *            the offsetWidth of the SimplePopup
		 * @param offsetHeight
		 *            the offsetHeight of the SimplePopup
		 * @see SimplePopup#show(PositionCallback)
		 */
		Position getPosition(int offsetWidth, int offsetHeight);

	}

	protected VerticalPanel panel;
	private boolean autoHide = false;
	private boolean displayed = false;
	private boolean glassEnabled = false;
	private boolean glassShowing = false;
	private Element glass;
	private HandlerRegistration nativePreviewHandlerRegistration;
    private HandlerRegistration resizeRegistration;
	/**
	 * Window resize handler used to keep the glass the proper size.
	 */
	private ResizeHandler glassResizer = new ResizeHandler() {
		public void onResize(ResizeEvent event) {
			Style style = glass.getStyle();

			int winWidth = Window.getClientWidth();
			int winHeight = Window.getClientHeight();

			// Hide the glass while checking the document size. Otherwise it would
			// interfere with the measurement.
			style.setDisplay(Display.NONE);
			style.setWidth(0, Unit.PX);
			style.setHeight(0, Unit.PX);

			int width = Document.get().getScrollWidth();
			int height = Document.get().getScrollHeight();

			// Set the glass size to the larger of the window's client size or the
			// document's scroll size.
			style.setWidth(Math.max(width, winWidth), Unit.PX);
			style.setHeight(Math.max(height, winHeight), Unit.PX);

			// The size is set. Show the glass again.
			style.setDisplay(Display.BLOCK);
		}
	};

	public SimplePopup() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().simplePopup());
		panel = new VerticalPanel();
		setWidget(panel);
	}

	public SimplePopup(Widget[] widgets) {
		this();
		for (int i = 0; i < widgets.length; i++) {
			panel.add(widgets[i]);
		}
	}
	
	@Override
	public void add(Widget w) {
		panel.add(w);
	}

	@Override
	public void clear(){
		panel.clear();
	}


	public void setAutoHide(boolean autoHide) {
		this.autoHide = autoHide;
	}

	public void show(final PositionCallback cb) {
		if (displayed) {
			return;
		}
		
		setVisible(false);
		maybeShowGlass();

		nativePreviewHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				previewNativeEvent(event);
			}
		});
		
		RootLayoutPanel.get().add(this);

		// clear "overflow:hidden" property from parent layer to allow shadow to "bleed" over the edge
		Layer layer = (Layer) getLayoutData();
		layer.getContainerElement().getStyle().clearOverflow();
		layer.getContainerElement().getStyle().setZIndex(1);

		final SimplePopup instance = this;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				// clear "bottom:0" and "right:0" properties from element => getOffsetWidth() and getOffsetHeight()
				// calculate correct values
				getElement().getStyle().clearBottom();
				getElement().getStyle().clearRight();

				Position pos = cb.getPosition(getOffsetWidth(), getOffsetHeight());
				double width = pos.getWidth() > 0 ? pos.getWidth() * 100.0 : getOffsetWidth();
				Unit widthUnit = pos.getWidth() > 0 ? Unit.PCT : Unit.PX;
				RootLayoutPanel.get().setWidgetLeftWidth(instance, pos.getLeft(), Unit.PX, width, widthUnit);
				
				double height = pos.getHeight() > 0 ? pos.getHeight() * 100.0 : getOffsetHeight();
				Unit heightUnit = pos.getHeight() > 0 ? Unit.PCT : Unit.PX;
				RootLayoutPanel.get().setWidgetTopHeight(instance, pos.getTop(), Unit.PX, height, heightUnit);
								
				onShow();
				
				displayed = true;
				setVisible(true);				
			}
		});
	}
	
	protected void onShow() {
		// template method for subclasses
	}

	protected void onHide() {
		// template method for subclasses
	}
	
	public void hide() {
		hide(false);
	}

	public void hide(boolean autoClosed) {
		if (displayed) {
			maybeShowGlass();
			RootLayoutPanel.get().remove(this);
			if (nativePreviewHandlerRegistration != null) {
				nativePreviewHandlerRegistration.removeHandler();
				nativePreviewHandlerRegistration = null;
			}
			displayed = false;
			onHide();
			CloseEvent.fire(this, this, autoClosed);
		}
	}

	public void showCentered(boolean glassEffect) {
		setGlassEnabled(glassEffect);
		show(new PositionCallback() {

			@Override
			public Position getPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				return new Position(left, top);
			}
		});
	}

	/**
	 * Normally, the popup is positioned directly below the relative target, with its left edge aligned with the left
	 * edge of the target. Depending on the width and height of the popup and the distance from the target to the bottom
	 * and right edges of the window, the popup may be displayed directly above the target, and/or its right edge may be
	 * aligned with the right edge of the target.
	 * 
	 * @param target
	 *            the target to show the popup below
	 */
	public void showRelativeTo(final UIObject target) {
		// Set the position of the popup right before it is shown.
		show(new PositionCallback() {

			@Override
			public Position getPosition(int offsetWidth, int offsetHeight) {
				return calculatePosition(target, offsetWidth, offsetHeight);
			}
		});
	}

	/**
	 * Positions the popup, called after the offset width and height of the popup are known.
	 * 
	 * @param relativeObject
	 *            the ui object to position relative to
	 * @param offsetWidth
	 *            the drop down's offset width
	 * @param offsetHeight
	 *            the drop down's offset height
	 */
	private Position calculatePosition(final UIObject relativeObject, int offsetWidth, int offsetHeight) {
		// Calculate left position for the popup. The computation for
		// the left position is bidi-sensitive.

		int textBoxOffsetWidth = relativeObject.getOffsetWidth();

		// Compute the difference between the popup's width and the
		// textbox's width
		int offsetWidthDiff = offsetWidth - textBoxOffsetWidth;

		// Left-align the popup.
		int left = relativeObject.getAbsoluteLeft();

		// If the suggestion popup is not as wide as the text box, always align to
		// the left edge of the text box. Otherwise, figure out whether to
		// left-align or right-align the popup.
		if (offsetWidthDiff > 0) {
			// Make sure scrolling is taken into account, since
			// box.getAbsoluteLeft() takes scrolling into account.
			int windowRight = Window.getClientWidth() + Window.getScrollLeft();
			int windowLeft = Window.getScrollLeft();

			// Distance from the left edge of the text box to the right edge
			// of the window
			int distanceToWindowRight = windowRight - left;

			// Distance from the left edge of the text box to the left edge of the
			// window
			int distanceFromWindowLeft = left - windowLeft;

			// If there is not enough space for the overflow of the popup's
			// width to the right of hte text box, and there IS enough space for the
			// overflow to the left of the text box, then right-align the popup.
			// However, if there is not enough space on either side, then stick with
			// left-alignment.
			if (distanceToWindowRight < offsetWidth && distanceFromWindowLeft >= offsetWidthDiff) {
				// Align with the right edge of the text box.
				left -= offsetWidthDiff;
			}

		}

		// Calculate top position for the popup

		int top = relativeObject.getAbsoluteTop();

		// Make sure scrolling is taken into account, since
		// box.getAbsoluteTop() takes scrolling into account.
		int windowTop = Window.getScrollTop();
		int windowBottom = Window.getScrollTop() + Window.getClientHeight();

		// Distance from the top edge of the window to the top edge of the
		// text box
		int distanceFromWindowTop = top - windowTop;

		// Distance from the bottom edge of the window to the bottom edge of
		// the text box
		int distanceToWindowBottom = windowBottom - (top + relativeObject.getOffsetHeight());

		// If there is not enough space for the popup's height below the text
		// box and there IS enough space for the popup's height above the text
		// box, then then position the popup above the text box. However, if there
		// is not enough space on either side, then stick with displaying the
		// popup below the text box.
		if (distanceToWindowBottom < offsetHeight && distanceFromWindowTop >= offsetHeight) {
			top -= offsetHeight;
		} else {
			// Position above the text box
			top += relativeObject.getOffsetHeight();
		}
		return new Position(left, top);
	}

	@Override
	public void setVisible(boolean visible) {
		// We use visibility here instead of UIObject's default of display
		// Because the panel is absolutely positioned, this will not create
		// "holes" in displayed contents and it allows normal layout passes
		// to occur so the size of the PopupPanel can be reliably determined.
		DOM.setStyleAttribute(getElement(), "visibility", visible ? "visible" : "hidden");
	}

	public HandlerRegistration addCloseHandler(CloseHandler<SimplePopup> handler) {
		return addHandler(handler, CloseEvent.getType());
	}

	/**
	 * When enabled, the background will be blocked with a semi-transparent pane the next time it is shown. If the
	 * PopupPanel is already visible, the glass will not be displayed until it is hidden and shown again.
	 * 
	 * @param enabled
	 *            true to enable, false to disable
	 */
	public void setGlassEnabled(boolean enabled) {
		glassEnabled = enabled;
		if (enabled && glass == null) {
			glass = Document.get().createDivElement();
			glass.setClassName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().simplePopupGlass());
			glass.getStyle().setPosition(com.google.gwt.dom.client.Style.Position.ABSOLUTE);
			glass.getStyle().setLeft(0, Unit.PX);
			glass.getStyle().setTop(0, Unit.PX);
		}
	}

    /**
     * Show or hide the glass.
     */
    private void maybeShowGlass() {
      if (!glassShowing) {
        if (glassEnabled) {
          Document.get().getBody().appendChild(glass);
          resizeRegistration = Window.addResizeHandler(glassResizer);
          glassResizer.onResize(null);
          glassShowing = true;
        }
      } else {
        Document.get().getBody().removeChild(glass);
        resizeRegistration.removeHandler();
        resizeRegistration = null;
        glassShowing = false;
      }
    }

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}

	private void previewNativeEvent(NativePreviewEvent event) {
		if (event.isCanceled() || event.isConsumed()) {
			return;
		}

		// If the event targets the popup or the partner, consume it
		Event nativeEvent = Event.as(event.getNativeEvent());
		boolean eventTargetsPopup = eventTargetsPopup(nativeEvent);
		if (eventTargetsPopup) {
			event.consume();
		}

		int type = nativeEvent.getTypeInt();
		switch (type) {
		case Event.ONMOUSEDOWN:
		case Event.ONTOUCHSTART:
			// Don't eat events if event capture is enabled, as this can
			// interfere with dialog dragging, for example.
			if (DOM.getCaptureElement() != null) {
				event.consume();
				return;
			}

			if (!eventTargetsPopup && autoHide) {
				hide(true);
				return;
			}
			break;
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONCLICK:
		case Event.ONDBLCLICK:
		case Event.ONTOUCHEND:
			// Don't eat events if event capture is enabled, as this can
			// interfere with dialog dragging, for example.
			if (DOM.getCaptureElement() != null) {
				event.consume();
				return;
			}
			break;
		}
	}
}
