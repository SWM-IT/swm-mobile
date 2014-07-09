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

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.validation.IErrorOutputElement;
import de.swm.commons.mobile.client.validation.IHasValidator;
import de.swm.commons.mobile.client.validation.IValidator;
import de.swm.commons.mobile.client.validation.impl.ValidationHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;


/**
 * Base class for all Textboxes.
 */
class TextBoxBase extends com.google.gwt.user.client.ui.TextBoxBase implements FocusHandler, BlurHandler,
		IsSWMMobileWidget, IHasValidator {

	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();
	private final ValidationHelper<String> validationHelper = new ValidationHelper<String>();
	private String placeholder = null;


	/**
	 * Default constructor.
	 *
	 * @param type the HTML5 input type
	 */
	TextBoxBase(String type) {
		super(createNumberInputElement(type));
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().textBox());
		addFocusHandler(this);
		addBlurHandler(this);
	}


	/**
	 * Sets a Placehoder text
	 *
	 * @param placeholder Placeholder
	 */
	public void setPlaceholder(final String placeholder) {
		if (placeholder != null && !placeholder.trim().isEmpty()) {
			this.placeholder = placeholder;
		}
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

	@Override
	public void clearValidation() {
		this.validationHelper.clearValidation();
		setValidStyle(true);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		myWidgetHelper.checkInitialLoad(this);

	}


	@Override
	public void onFocus(FocusEvent event) {
		this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().focus());
		if (getText().equalsIgnoreCase(placeholder)) {
			setText("");
		}
	}


	@Override
	public void onBlur(BlurEvent event) {
		validate();
	}


	/**
	 * Create the HTML input element.
	 *
	 * @param type the type
	 * @return the input element.
	 */
	private static native InputElement createNumberInputElement(String type) /*-{
        var e = $doc.createElement("INPUT");
        e.type = type;
        return e;
    }-*/;


	@Override
	public void onInitialLoad() {
	}


	@Override
	public void onTransitionEnd() {
	}


	@Override
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}

	/**
	 * Sets the maximum allowable length of the text box.
	 *
	 * @param length the maximum length, in characters
	 */
	public void setMaxLength(int length) {
		this.getInputElement().setMaxLength(length);
	}

	/**
	 * Returns and Casts the Element as InputElement.
	 *
	 * @return Element as InputElement
	 */
	private InputElement getInputElement() {
		return getElement().cast();
	}
}
