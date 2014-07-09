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

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Element;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.validation.IErrorOutputElement;
import de.swm.commons.mobile.client.validation.IHasValidator;
import de.swm.commons.mobile.client.validation.IValidator;
import de.swm.commons.mobile.client.validation.impl.ValidationHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;

/**
 * Input type for larger text elements.
 */
public class TextArea extends com.google.gwt.user.client.ui.TextArea implements FocusHandler, BlurHandler,
		KeyUpHandler, IsSWMMobileWidget, IHasValidator {

	private final ValidationHelper<String> validationHelper = new ValidationHelper<String>();

	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();

	/**
	 * Default constructor.
	 */
	public TextArea() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().textArea());
		addFocusHandler(this);
		addBlurHandler(this);
		addKeyUpHandler(this);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		myWidgetHelper.checkInitialLoad(this);
	}

	@Override
	public void onFocus(FocusEvent event) {
		this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().focus());
		this.resize();
		this.setCursorPos(this.getText().length());
	}

	@Override
	public void onBlur(BlurEvent event) {
		validate();
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		resize();
	}

	private void resize() {
		Element ele = getElement();
		final int extraLineHeight = 15;

		ele.getStyle().setProperty("height", "auto");

		int scrollHeight = ele.getScrollHeight() + extraLineHeight;

		if (scrollHeight < 3 * extraLineHeight) {
			scrollHeight = 3 * extraLineHeight;
		}
		
		ele.getStyle().setProperty("height", scrollHeight + "px");

	}

	@Override
	public void onInitialLoad() {
	}

	@Override
	public void onTransitionEnd() {
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		this.resize();
	}


	@Override
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}

	/**
	 * Adds a validator to this Ui-Widget
	 *
	 * @param validatorToAdd the validtors to add
	 */
	public void addValidator(IValidator<String> validatorToAdd) {
		this.validationHelper.addValidator(validatorToAdd);
	}

	/**
	 * Add an error output element where error messages are displayed
	 *
	 * @param errorOutputToAdd the error output element to add
	 */
	public void addErrorOuptut(IErrorOutputElement errorOutputToAdd) {
		this.validationHelper.addErrorOuptut(errorOutputToAdd);
	}

	/**
	 * Will remove all registered validators.
	 */
	public void removeAllValidators() {
		this.validationHelper.removeAllValidators();
	}

	@Override
	public void clearValidation() {
		validationHelper.clearValidation();
		setValidStyle(true);
	}

	@Override
	public boolean validate() {
		boolean isValid = !this.validationHelper.validate(this);
		this.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().focus());
		setValidStyle(isValid);
		return isValid;
	}

	/**
	 * Set the components style according to validity.
	 *
	 * @param isValid Flag if contents are valid
	 */
	private void setValidStyle(boolean isValid) {
		if (!isValid) {
			this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().error());
		} else {
			this.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().error());
		}
	}
}
