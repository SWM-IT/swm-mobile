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

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.utils.Utils;



/**
 * Radio button input widget.
 * 
 */
public class RadioButton extends com.google.gwt.user.client.ui.RadioButton implements IsSWMMobileWidget {

	private final IsSWMMobileWidgetHelper widgetHelper = new IsSWMMobileWidgetHelper();



	/**
	 * Default constructor.
	 */
	public RadioButton() {
		super(null);
		if (SWMMobile.getOsDetection().isAndroid() && Utils.isWVGA()) {
			setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().radioButton());
			DivElement div = Document.get().createDivElement();
			this.getElement().insertFirst(div);
		}
	}



	@Override
	protected void onLoad() {
		super.onLoad();
		widgetHelper.checkInitialLoad(this);
	}



	@Override
	public void setValue(Boolean value) {
		setValue(value, true);
	}



	@Override
	public void setValue(Boolean checked, boolean fireEvents) {
		super.setValue(checked, fireEvents);
		updateCheckedStyle(checked);
	}



	/**
	 * Updates the style if checked.
	 * 
	 * @param checked
	 *            ture if checked
	 */
	protected void updateCheckedStyle(Boolean checked) {
		if (checked) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().selected());
		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCheckRadioBoxCss().selected());
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

}
