package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;

public class SimpleImageButton extends FlowPanel implements HasClickHandlers, DragEventsHandler, IsSWMMobileWidget {

	private final IsSWMMobileWidgetHelper widgetHelper = new IsSWMMobileWidgetHelper();
	protected Image icon;
	protected ImageResource normalIcon;
	protected ImageResource highlightIcon;
	protected Label headerText;

	@UiConstructor
	public SimpleImageButton(ImageResource normalIcon) {
		this(null, normalIcon, null, null);
	}

	public SimpleImageButton(ImageResource normalIcon, ClickHandler handler) {
		this(null, normalIcon, null, handler);
	}

	public SimpleImageButton(String headerText, ImageResource normalIcon, ImageResource highlightIcon, ClickHandler handler) {
		assert normalIcon != null : "Normal icon must be set for SimpleImageButton";
		this.normalIcon = normalIcon;
		this.highlightIcon = highlightIcon;
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().imageButton());
		setIcon(normalIcon);
		if (headerText != null) {
			this.headerText = new Label(headerText);
			add(this.headerText);
		}
		if (handler != null) {
			addDomHandler(handler, ClickEvent.getType());
		}
	}

	public void setHeaderText(String headerText) {
		if (this.headerText == null) {
			this.headerText = new Label();
			add(this.headerText);
		}
		this.headerText.setText(headerText);
	}

	public void setNormalIcon(ImageResource normalIcon) {
		this.normalIcon = normalIcon;
	}

	public void setHighlightIcon(ImageResource highlightIcon) {
		this.highlightIcon = highlightIcon;
	}
	
	protected void highlightIcon() {
		if ((highlightIcon != null) && (icon != null)) {
			icon.setResource(highlightIcon);
		}		
	}

	protected void unhighlightIcon() {
		if ((highlightIcon != null) && (icon != null)) {
			icon.setResource(normalIcon);
		}
	}

	public void setIcon(ImageResource iconResource) {
		if (icon == null) {
			icon = new Image(iconResource);
			add(icon);
		} else {
			icon.setResource(iconResource);
		}
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
		widgetHelper.checkInitialLoad(this);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		DragController.get().removeDragEventsHandler(this);
	}

	@Override
	public void onDragStart(DragEvent e) {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		highlightIcon();
		e.stopPropagation();
	}

	@Override
	public void onDragMove(DragEvent e) {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		unhighlightIcon();
		e.stopPropagation();
	}

	@Override
	public void onDragEnd(DragEvent e) {
		removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getButtonCss().pressed());
		unhighlightIcon();
		e.stopPropagation();
	}

	@Override
	public void onInitialLoad() {
	}

	@Override
	public void onTransitionEnd() {
	}

	@Override
	public void setSecondaryStyle(String style) {
		widgetHelper.setSecondaryStyle(this, style);
	}
}
