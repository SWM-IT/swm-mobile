package de.swm.commons.mobile.client.widgets;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.theme.components.TransitionsCss;
import de.swm.commons.mobile.client.utils.Utils;

/**
 * Scrollbar which is used to indicate the scroll position in a scroll panel.
 *
 * @author karsunke.franziskus
 *         <br> copyright (C) 2011, SWM Services GmbH
 */
public class ScrollBar extends SimplePanel {
	private Timer fadeOutTimer;
	SimplePanel scrollbarPanel = new SimplePanel();
	FlowPanel scrollbarTable = new FlowPanel();
	SimplePanel scrollbarRail = new SimplePanel();
	FlowPanel scrollbarIndicator = new FlowPanel();
	SimplePanel filler = new SimplePanel();

	/**
	 * default constructor.
	 */
	public ScrollBar() {
		this.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbar());
		scrollbarTable.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbarTable());
		filler.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbarFiller());
		scrollbarPanel.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbarPanel());
		scrollbarRail.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbarRail());
		scrollbarIndicator.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollbarIndicator());

		scrollbarRail.add(scrollbarIndicator);
		scrollbarPanel.add(scrollbarRail);

		scrollbarTable.add(filler);
		scrollbarTable.add(scrollbarPanel);
		this.add(scrollbarTable);
	}

	/**
	 * This method renders the scrollbar according to the height of the widget.
	 *
	 * @param widgetHeight the height of the widget inside the scrollpanel (in pixel).
	 * @param screenHeight the height of the scrollpanel itself (in pixel).
	 * @param currentPosition the current position of the scroll panel (in pixel).
	 */
	public void renderScrollbar(int widgetHeight, int screenHeight, int currentPosition, int transistionDuration) {
		// First Show the Scrollbar.
		TransitionsCss transitionsCss = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss();
		scrollbarIndicator.removeStyleName(transitionsCss.fade());
		scrollbarIndicator.removeStyleName(transitionsCss.in());

		// Move the Scrollbar to the correct Position.
		renderPosition(widgetHeight, screenHeight, currentPosition, transistionDuration);

		// Animate the Scrollbar in the right height (depending on the content and the position).
		renderHeight(widgetHeight, screenHeight, currentPosition, transistionDuration);
	}

	private void renderHeight(int widgetHeight, int screenHeight, int currentPosition, int transistionDuration) {
		// Calculate (in percent) where the scrollbar should be.
		double positionInPercent = (double) currentPosition / (double) widgetHeight;

		// Calculate (in pixel) where the scrollbar should be.
		int scrollbarposition = (int) (positionInPercent * screenHeight);

		// Calculate the base height of the Scrollbar depending on the relation between screen and widget height.
		double screenRatio = (double) screenHeight / (double) widgetHeight;
		if (screenRatio > 1.0) {
			screenRatio = 1.0;
		} else if (screenRatio < 0.0) {
			screenRatio = 0.0;
		}
		int height = (int) (screenRatio * screenHeight) - 6;


		if (scrollbarposition < 0) {
			// if the scrollbar would be above the widget, then squeeze the height.
			height += scrollbarposition;
		} else if (scrollbarposition + height > screenHeight) {
			// if the scrollbar would be below the widget, then squeeze the height.
			height -= scrollbarposition + height - screenHeight;
		}

		scrollbarIndicator.getElement().getStyle().setHeight(height, Style.Unit.PX);
	}

	private void renderPosition(int widgetHeight, int screenHeight, int currentPosition, int transistionDuration) {
		// Calculate (in percent) where the scrollbar should be.
		double positionInPercent = (double) currentPosition / (double) widgetHeight;

		// Calculate (in pixel) where the scrollbar should be.
		int scrollbarposition = (int) (positionInPercent * screenHeight);

		if (scrollbarposition < 0) {
			// If the position is negative, set the position to zero.
			scrollbarposition = 0;
		}
		// Set the Transitionduration and move the scrollbar to the Position where it should be.
		Utils.setTransitionDuration(scrollbarIndicator.getElement(), transistionDuration);
		Utils.setTranslateY(scrollbarIndicator.getElement(), scrollbarposition);
	}

	/**
	 * Fades out the scrollbar after X milliseconds.
	 * @param milliseconds the number of milliseconds after which the scrollbar is faded out.
	 */
	public void fadeOut(int milliseconds) {
		final TransitionsCss transitionsCss = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss();

		if (fadeOutTimer != null) {
			fadeOutTimer.cancel();
		}

		fadeOutTimer = new Timer() {
			@Override
			public void run() {
				scrollbarIndicator.addStyleName(transitionsCss.fade());
				Utils.setTransitionDuration(scrollbarIndicator.getElement(), 500);
				scrollbarIndicator.addStyleName(transitionsCss.in());
			}
		};

		fadeOutTimer.schedule(milliseconds);
	}

	/**
	 * Hides the scrollbar immediately.
	 */
	public void hide() {
		this.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
	}
}
