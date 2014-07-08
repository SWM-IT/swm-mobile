/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.swm.gwt.client.utils.ShimClickHandler;

/**
 * Defines a command bar containing command widgets (CommandItems).
 * <p/>
 * <pre>
 * <ui:with field='icon' type='de.swm....SomeIconResources'/>
 *
 * <m:CommandPanel>
 * 		<m:CommandItem ui:field='command1' headerText='Home' normalIcon='{icon.home}' highlightIcon='{icon.home_selected}' />
 * 		<m:CommandItem ui:field='command2' headerText='Download' normalIcon='{icon.download}' highlightIcon='{icon.download_selected}' />
 * 		<m:CommandItem ui:field='command3' headerText='Info' normalIcon='{icon.information}' highlightIcon='{icon.information_selected}' />
 * 	</m:CommandPanel>
 * </pre>
 */
public class CommandItem extends FlowPanel implements DragEventsHandler,
		IsSWMMobileWidget {

	private boolean isDisabled = false;
	private boolean isSelected = false;
	private final IsSWMMobileWidgetHelper widgetHelper = new IsSWMMobileWidgetHelper();
	protected Image icon;
	protected ImageResource normalIcon;
	protected ImageResource highlightIcon;
	protected Label headerText;
	private final ShimClickHandler clickHandler = new ShimClickHandler();

	/**
	 * Allow subclasses to use different constructors
	 */
	protected CommandItem() {
	}

	/**
	 * Constructor.
	 *
	 * @param headerText    .
	 * @param normalIcon    .
	 * @param highlightIcon .
	 */
	@UiConstructor
	public CommandItem(String headerText, ImageResource normalIcon,
					   ImageResource highlightIcon) {
		this(headerText, normalIcon, highlightIcon, null);
	}

	/**
	 * Constructor.
	 *
	 * @param headerText .
	 * @param normalIcon .
	 * @param handler    .
	 */
	public CommandItem(String headerText, ImageResource normalIcon,
					   ClickHandler handler) {
		this(headerText, normalIcon, null, handler);
	}

	/**
	 * Constructor.
	 *
	 * @param normalIcon .
	 * @param handler    .
	 */
	public CommandItem(ImageResource normalIcon, ClickHandler handler) {
		this(null, normalIcon, null, handler);
	}

	/**
	 * Constructor.
	 *
	 * @param normalIcon    .
	 * @param highlightIcon .
	 * @param handler       .
	 */
	public CommandItem(ImageResource normalIcon, ImageResource highlightIcon,
					   ClickHandler handler) {
		this(null, normalIcon, highlightIcon, handler);
	}

	/**
	 * Constructor.
	 *
	 * @param headerText    .
	 * @param normalIcon    .
	 * @param highlightIcon .
	 * @param handler       .
	 */
	public CommandItem(String headerText, ImageResource normalIcon,
					   final ImageResource highlightIcon, final ClickHandler handler) {
		assert normalIcon != null : "Normal icon must be set for CommandItem";
		this.normalIcon = normalIcon;
		this.icon = new Image(normalIcon);
		add(this.icon);
		this.highlightIcon = highlightIcon;
		if (headerText != null) {
			this.headerText = new Label(headerText);
			add(this.headerText);
		}
		if (handler != null) {
			clickHandler.addClickHandler(handler);
		}
		addDomHandler(clickHandler, ClickEvent.getType());
	}

	/**
	 * Add a click handler to the command panel.
	 *
	 * @param handler the click handler.
	 */
	public void addClickHandler(ClickHandler handler) {
		clickHandler.addClickHandler(handler);
	}

	public void setNormalIcon(ImageResource normalIcon) {
		this.normalIcon = normalIcon;
	}

	public void setHighlightIcon(ImageResource highlightIcon) {
		this.highlightIcon = highlightIcon;
	}

	/**
	 * Sets the header text.
	 *
	 * @param headerText the header text
	 */
	public void setHeaderText(String headerText) {
		if (this.headerText == null) {
			this.headerText = new Label();
			add(this.headerText);
		}
		this.headerText.setText(headerText);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
		widgetHelper.checkInitialLoad(this);
	}

	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
	}

	@Override
	public void onDragStart(DragEvent e) {
		if (!isDisabled) {
			if (!isSelected) {
				addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
						.getCommandPanelCss().pressed());
			}
			highlightIcon();
		}
		e.stopPropagation();
	}

	protected void highlightIcon() {
		if ((highlightIcon != null) && (icon != null)) {
			icon.setResource(highlightIcon);
		}
	}

	protected void unhighlightIcon() {
		if (icon != null) {
			icon.setResource(normalIcon);
		}
	}

	/**
	 * Selects permanetly the item.
	 *
	 * @param selected true is command item schould be selected
	 */
	public void setSelected(boolean selected) {
		if (!this.isSelected && selected) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getCommandPanelCss().pressed());
		} else if (this.isSelected && !selected) {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getCommandPanelCss().pressed());
		}
		this.isSelected = selected;
	}

	/**
	 * Returns the isSelected.
	 *
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void onDragMove(DragEvent e) {
		if (!isDisabled) {
			if (!isSelected) {
				removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
						.getCommandPanelCss().pressed());
			}
			unhighlightIcon();
		}
		e.stopPropagation();
	}

	@Override
	public void onDragEnd(DragEvent e) {
		if (!isDisabled) {
			if (!isSelected) {
				removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
						.getCommandPanelCss().pressed());
			}
			unhighlightIcon();
		} else {
			DragController.get().suppressNextClick();
		}
		e.stopPropagation();
	}

	/**
	 * Disables / Enables this item.
	 *
	 * @param disabled true if disabled
	 */
	public void setDisabled(boolean disabled) {
		isDisabled = disabled;
		this.clickHandler.setEnabled(!disabled);
		if (disabled) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getCommandPanelCss().disabled());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle()
					.getCommandPanelCss().disabled());
		}
	}

	/**
	 * Retunrs true if disabled.
	 * @return true if disabled
	 */
	public boolean isDisabled() {
		return isDisabled;
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

	/**
	 * Tauscht während der Laufzeit das Icon aus (wenn sich ein Item beispielsweise deaktiviert).
	 *
	 * @param resource .
	 */
	public void setImageOnIcon(ImageResource resource) {
		if (icon != null) {
			this.icon.setResource(resource);
		}
	}

}
