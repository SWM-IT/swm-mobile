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

import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;
import de.swm.gwt.client.utils.ShimClickHandler;


/**
 * CheckBox widget.
 */
public class CheckBox extends com.google.gwt.user.client.ui.CheckBox implements IsSWMMobileWidget {

	private final IsSWMMobileWidgetHelper widgetHelper = new IsSWMMobileWidgetHelper();
	private Node imageNode = null;
	private final ShimClickHandler checkBoxClickHanlders = new ShimClickHandler();
	private boolean isEnabled = true;
	private boolean currentValue = false;

	/**
	 * Default constructor.
	 */
	public CheckBox() {
		super();
		if (SWMMobile.getOsDetection().isAndroid() && Utils.isWVGA()) {
			CheckBoxIndicator indicator = new CheckBoxIndicator();
			this.getElement().insertFirst(indicator.getElement());
		}
		addDomHandler(checkBoxClickHanlders, ClickEvent.getType());
		super.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event != null && event.getValue() != null) {
					ensureValueWillNotChangeWhenDisabled(event.getValue());
					if (isEnabled) {
						currentValue = event.getValue();
					}
				}
			}
		});
		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ensureValueWillNotChangeWhenDisabled(getValue());
			}
		}, ClickEvent.getType());
	}

	private void ensureValueWillNotChangeWhenDisabled(boolean newValue) {
		if (!isEnabled && newValue != currentValue) {
			new Timer() {

				@Override
				public void run() {
					setValue(currentValue, false);
					if (!currentValue) {
						removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().selected());
					}
				}
			}.schedule(50);
		}
	}

	/**
	 * Sets an image to be displayed as a label for the checkbox.
	 *
	 * @param imgRes ImageResource for the image to be displayed
	 */
	public void setImage(ImageResource imgRes) {
		Node labelElem = findLabelElement();
		if (labelElem == null) {
			return;
		}

		if (imageNode != null) {
			labelElem.removeChild(imageNode);
		}

		Image img = new Image(imgRes);
		imageNode = img.getElement();
		labelElem.appendChild(imageNode);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
		checkBoxClickHanlders.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return checkBoxClickHanlders.addClickHandler(handler);
	}


	@Override
	protected void onLoad() {
		super.onLoad();
		widgetHelper.checkInitialLoad(this);
	}


	@Override
	public void setValue(Boolean value) {
		setValue(value, false);
	}


	@Override
	public void setValue(Boolean checked, boolean fireEvents) {
		if (this.isEnabled) {
			this.currentValue = checked;
			super.setValue(checked, fireEvents);
			if (checked) {
				addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().selected());
			} else {
				removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().selected());
			}
		}
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


	private Node findLabelElement() {
		for (Node child = getElement().getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child.getNodeName().equalsIgnoreCase("label")) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Indicator class for Android devices.
	 */
	public static class CheckBoxIndicator extends HTML {

		/**
		 * Default constructor.
		 */
		public CheckBoxIndicator() {
			super("<div><div></div></div><div></div><div></div><div></div>");
			setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().checkBoxIndicator());
		}
	}
}
