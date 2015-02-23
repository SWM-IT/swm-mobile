package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.theme.components.NotificationBoxCss;
import de.swm.commons.mobile.client.theme.components.TransitionsCss;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;

import java.util.Iterator;

/**
 * A notification box wich displays a message at the botton of the Page. Will showup for 5 seconds and fadeout
 * automatically after this time period.
 *
 * Useag
 * <code>NotificationBox.showPopupDialog(container, "Message1", "Message2");</code>
 *
 * @author Daniel Wiese
 */
public class NotificationBox extends Composite implements HasWidgets, IsSWMMobileWidget {

    public static NotificationBox HIGH_LANDER;

	private static final int DEFAULT_DELAY = 10;
	private static final int FADE_IN_AFTER_MS = 1000;
	private static final int FADE_OUT_AFTER_MS = 5000;
	private static final int FADE_OUT_DURATION = 1000;

	protected final FlowPanel slideUpPanel;
	private final FlowPanel container;
	protected HasWidgets panelToOverlay;
	protected final NotificationBoxCss css;
	protected final Anchor closeButton;

	private boolean isVisible = false;
	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();

	/**
	 * Construct a popin dialog
	 */
	public NotificationBox() {
		this.css = SWMMobile.getTheme().getMGWTCssBundle().getNotificationBox();
		slideUpPanel = new FlowPanel();
		slideUpPanel.addStyleName(css.animationContainerCenter());

		container = new FlowPanel();
		container.addStyleName(css.getBottomPanel());

		closeButton = new Anchor();
		closeButton.setStyleName(css.getCloseLink());
		container.add(closeButton);

		slideUpPanel.add(container);

		initWidget(slideUpPanel);
	}

	/**
	 * Wil show the notificationf or 5 secs.
	 *
	 * @param toAdd
	 */
	public void addText(String toAdd) {
		this.add(new Label(toAdd));
	}

	/**
	 * Will perform a smmoth fadeout.
	 *
	 * @param containerForThisWidget
	 */
	private void fadeOut(final HasWidgets containerForThisWidget) {
		final TransitionsCss transitionsCss = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss();
		//Opacity 0 --> unvisible
		new Timer() {

			@Override
			public void run() {
				container.addStyleName(transitionsCss.in());
				container.addStyleName(transitionsCss.fade());
				new Timer() {

					@Override
					public void run() {
						container.removeStyleName(transitionsCss.in());
						container.removeStyleName(transitionsCss.fade());
						containerForThisWidget.remove(NotificationBox.this);
						isVisible = false;
					}
				}.schedule(FADE_OUT_DURATION);
			}
		}.schedule(DEFAULT_DELAY);
	}


	/*
		 * (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
		 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Widget w) {
		container.add(w);

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		container.clear();

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Widget> iterator() {
		return container.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Widget w) {
		return container.remove(w);
	}

	/**
	 * Set the area to cover
	 *
	 * @param widgetToCover the widet to cover
	 */
	public void setPanelToOverlay(HasWidgets widgetToCover) {
		setPanelToOverlay(widgetToCover);

	}


	/**
	 * get the panel that the dialog overlays
	 *
	 * @return the panel that is overlayed by this dialog
	 */
	public HasWidgets getPanelToOverlay() {
		if (panelToOverlay == null) {
			panelToOverlay = RootPanel.get();
		}
		return panelToOverlay;
	}

	/**
	 * Returns true if this notification is currently visible.
	 *
	 * @return true if visible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * When the widget is loaded first time.
	 */
	@Override
	public void onInitialLoad() {
	}

	/**
	 * When an transition containing this widget ends.
	 */
	@Override
	public void onTransitionEnd() {
	}

	/**
	 * Will set the secondary style.
	 *
	 * @param style the style to set.
	 */
	@Override
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}

	/**
	 * Defines if the notificationbox should have a close button or not.
	 * @param show .
	 * @param containerForThisWidget .
	 */
	public void showCloseButton(boolean show, final HasWidgets containerForThisWidget) {
		this.closeButton.setVisible(show);
		if (show) {
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					fadeOut(containerForThisWidget);
				}
			});
		}
	}


	/**
	 * Will dispay a notification for 5 seconds and hide it again.
	 * @param containerForThisWidget the container (may outer HTML pannel of a Page)
	 * @param messages messages to display
	 */
	public static void showPopupDialog(final HasWidgets containerForThisWidget, final String... messages) {
		showPopupDialog(containerForThisWidget, FADE_IN_AFTER_MS, FADE_OUT_AFTER_MS, false, messages);
	}

	/**
	 * Will display a notification box, which will fade in and out after a specified time.
	 * @param containerForThisWidget -
	 * @param fadeInAfter .
	 * @param fadeOutAfter .
	 * @param messages .
	 */
	public static void showPopupDialog(final HasWidgets containerForThisWidget, final int fadeInAfter,
			final int fadeOutAfter, final String... messages) {
		showPopupDialog(containerForThisWidget, fadeInAfter, fadeOutAfter, false, messages);
	}

	/**
	 * Will display a notification box, which will fade in and out after a specified time and defines if it should
	 * have a close button or not.
	 * @param containerForThisWidget .
	 * @param fadeInAfter .
	 * @param fadeOutAfter .
	 * @param showCloseButton .
	 * @param messages .
	 */
	public static void showPopupDialog(final HasWidgets containerForThisWidget, final int fadeInAfter,
			final int fadeOutAfter, final boolean showCloseButton, final String... messages) {
		if (HIGH_LANDER != null) {
            HIGH_LANDER.fadeOut(containerForThisWidget);
        }

        new Timer() {
			@Override
			public void run() {
				final NotificationBox popinDialog = new NotificationBox();
                HIGH_LANDER = popinDialog;
				popinDialog.showCloseButton(showCloseButton, containerForThisWidget);
				for (String message : messages) {
					popinDialog.add(new Label(message));
				}
				containerForThisWidget.add(popinDialog);
				new Timer() {


					@Override
					public void run() {
						popinDialog.fadeOut(containerForThisWidget);
						//goTo(getView(), Transition.SLIDEDOWN, Direction.LEFT, null);
					}
				}.schedule(fadeOutAfter);
			}
		}.schedule(fadeInAfter);
	}
}
