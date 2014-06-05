package de.swm.commons.mobile.client.widgets;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.Utils;

public class SlidePopup extends SimplePopup {
	
    private JavaScriptObject transitionRegistration;
    private String transitionEndEvent;
    private boolean hiding = false;

	public SlidePopup() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().slidePopup());
		transitionEndEvent = Utils.getTransitionEventName(getElement());
		setGlassEnabled(true);
	}
	
	@Override
	protected void onShow() {
		hiding = false;
		Layer layer = (Layer) getLayoutData();
		if (transitionRegistration == null) {
			transitionRegistration = Utils.addEventListener(layer.getContainerElement(), transitionEndEvent, true, new EventListener() {
				
				@Override
				public void onBrowserEvent(Event event) {
					if (hiding) {
						hide(false);
					}
				}
			});
		}
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				Layer layer = (Layer) getLayoutData();
				layer.getContainerElement().addClassName(SWMMobile.getTheme().getMGWTCssBundle().getAnimationCss().transformEaseOut());			
				Utils.setTranslateY(layer.getContainerElement(), -getOffsetHeight());
			}
		});
	}
	
	@Override
	protected void onHide() {
		if (transitionRegistration != null) {
			Layer layer = (Layer) getLayoutData();
			Utils.removeEventListener(layer.getContainerElement(), transitionEndEvent, true, transitionRegistration);
			transitionRegistration = null;
		}
	}
	
	public void show() {
		show(new PositionCallback() {
			
			@Override
			public Position getPosition(int offsetWidth, int offsetHeight) {
				// show below current window height with a width of 100% and given height in pixel
				return new Position(0, Window.getClientHeight(), 1.0, -1.0);
			}
		});		
	}
	
	@Override
	public void hide() {
		hiding = true;
		Layer layer = (Layer) getLayoutData();
		Utils.setTranslateY(layer.getContainerElement(), getOffsetHeight());
		super.hide();
	}
}
