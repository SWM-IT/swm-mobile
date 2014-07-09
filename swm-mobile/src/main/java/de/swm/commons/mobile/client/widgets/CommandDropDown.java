package de.swm.commons.mobile.client.widgets;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.popup.SelectPopup;
import de.swm.commons.mobile.client.widgets.popup.SimplePopup;
import de.swm.gwt.client.utils.ShimClickHandler;

/**
 * Defines a command bar containing command widgets with drop down selection (DropDownItems).
 * <p/>
 * <pre>
 * <ui:with field='icon' type='de.swm....SomeIconResources'/>
 *
 * <m:CommandPanel>
 * 		<m:CommandItem ui:field='command1' headerText='Home' normalIcon='{icon.home}' highlightIcon='{icon.home_selected}' />
 * 		<m:CommandDropDown ui:field='dropDown' headerText='Options' normalIcon='{icon.option}' highlightIcon='{icon.option_selected}' >
 * 			<m:DropDownItem value="1" text="Option 1"/>
 * 			<m:DropDownItem value="2" text="Option 2"/>
 * 			<m:DropDownItem value="3" text="Option 3"/>
 * 		</m:CommandDropDown>
 * </m:CommandPanel>
 * </pre>
 */
public class CommandDropDown extends CommandItem implements HasValueChangeHandlers<String> {

	private DropDownList<String> list;
	private SelectPopup popup;
	private final ShimClickHandler clickHandlerWithDisableOption = new ShimClickHandler();

	@UiConstructor
	public CommandDropDown(String headerText, ImageResource normalIcon, ImageResource highlightIcon) {
		this(null, headerText, normalIcon, highlightIcon, null);
	}

	public CommandDropDown(final DropDownItem[] items, String headerText, ImageResource normalIcon, ImageResource highlightIcon, ValueChangeHandler<String> handler) {
		assert normalIcon != null : "Normal icon must be set for CommandDropDown";
		this.normalIcon = normalIcon;
		this.highlightIcon = highlightIcon;

		if (SWMMobile.getOsDetection().isIOs()) {
			// use list (= native HTML select element) for iOS platform
			list = new DropDownList<String>();
			list.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCommandPanelCss().commandDropDown());
		} else {
			// fallback to popup implementation
			popup = new SelectPopup();
			popup.setAutoHide(true);
			popup.addCloseHandler(new CloseHandler<SimplePopup>() {

				@Override
				public void onClose(CloseEvent<SimplePopup> event) {
					if (!event.isAutoClosed()) {
						fireChangeEvent(popup.getSelectedKey());
					}
				}
			});
		}

		setIcon(normalIcon);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				add(items[i]);
			}
		}

		if (handler != null) {
			addValueChangeHandler(handler);
		}

		if (SWMMobile.getOsDetection().isIOs()) {
			// simply hide the text on iOS devices because native component (wheel, popup etc.) will be used to display items
			list.getListBox().addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCommandPanelCss().hidden());
		}

		if (list != null) {
			add(list);
		}

		if (headerText != null) {
			this.headerText = new Label(headerText);
			add(this.headerText);
		}

		ClickHandler clickHandler = null;
		if (list != null) {
			// open selection of HTML select element on iOS devices
			clickHandler = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					list.getListBox().getElement().focus();
				}
			};
		} else {
			// use normal popup on other platforms
			final CommandDropDown instance = this;
			clickHandler = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					popup.showRelativeTo(instance);
				}
			};
		}

		clickHandlerWithDisableOption.addClickHandler(clickHandler);
		addDomHandler(clickHandlerWithDisableOption, ClickEvent.getType());
	}


	/**
	 * Removes the current selection.
	 */
	public void clearSelection() {
		if (list != null) {
			list.clearSelection();
		}
		if (popup != null) {
			popup.clearSelection();
		}
	}

	public void add(DropDownItem item) {
		add(item, false);
	}

	/**
	 * Disables / Enables this item.
	 *
	 * @param disabled true if disabled
	 */
	@Override
	public void setDisabled(boolean disabled) {
		this.clickHandlerWithDisableOption.setEnabled(!disabled);
		super.setDisabled(disabled);
	}

	public void add(DropDownItem item, boolean selected) {
		if (list != null) {
			list.add(item);
			if (selected) {
				list.setValue(item.getKey());
			}
		} else if (popup != null) {
			popup.addItem(item.getText(), item.getKey(), selected);
		}
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		if (list != null) {
			return list.addValueChangeHandler(handler);
		} else if (popup != null) {
			return addHandler(handler, ValueChangeEvent.getType());
		}
		return null;
	}

	@Override
	public void clear() {
		if (list != null) {
			list.clear();
		} else if (popup != null) {
			popup.clear();
		}
	}

	@Override
	protected void highlightIcon() {
		setIcon(highlightIcon);

	}

	@Override
	protected void unhighlightIcon() {
		setIcon(normalIcon);
	}

	@Override
	public void setImageOnIcon(ImageResource resource) {
		normalIcon = resource;
		setIcon(normalIcon);
	}

	private void fireChangeEvent(Object value) {
		ValueChangeEvent.fire(this, (String) value);
	}

	private void setIcon(ImageResource iconResource) {
		if (iconResource != null) {
			if (list != null) {
				list.getListBox().getElement().getStyle().setBackgroundImage("url(" + iconResource.getSafeUri().asString() + ")");
				list.getListBox().getElement().getStyle().setWidth(iconResource.getWidth(), Unit.PX);
				list.getListBox().getElement().getStyle().setHeight(iconResource.getHeight(), Unit.PX);
				list.getListBox().getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			} else if (popup != null) {
				if (icon == null) {
					icon = new Image(iconResource);
					add(icon);
				} else {
					icon.setResource(iconResource);
				}
			}
		}
	}

}
