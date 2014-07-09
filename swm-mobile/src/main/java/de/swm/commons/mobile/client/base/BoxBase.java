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
package de.swm.commons.mobile.client.base;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.validation.IErrorOutputElement;
import de.swm.commons.mobile.client.validation.IValidator;
import de.swm.commons.mobile.client.validation.impl.ValidationHelper;
import de.swm.commons.mobile.client.widgets.itf.IsSWMMobileWidget;

import java.io.IOException;



/**
 * Creates an HTML5 Element and uses the right GWT renderer and parser to support data binding. Has to inherit from
 * {@link ValueBoxBase} gwt's {@link ValueBox} has an assertion to insert only text content (not valid using e.g.
 * numbers).
 * 
 * @param <T>
 *            the value type
 * 
 * 
 * 
 * @param <T>
 */
public class BoxBase<T> extends com.google.gwt.user.client.ui.ValueBoxBase<T> implements FocusHandler, BlurHandler,
		IsSWMMobileWidget {

	private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();
	private final ValidationHelper<T> validationHelper = new ValidationHelper<T>();

	public static class ToStringRenderer<V> implements Renderer<V>{

		@Override
		public String render(Object obj) {
			if(obj == null){
				return "";
			}
			return obj.toString();
		}

		@Override
		public void render(Object obj, Appendable app) throws IOException {
			app.append(render(obj));
		}
		
	}

	/**
	 * Default constructor.
	 * 
	 * @param html5Type
	 *            the html5 element type
	 * @param renderer
	 *            the renderer
	 * @param parser
	 *            the parser
	 */
	public BoxBase(String html5Type, Renderer<T> renderer, Parser<T> parser) {

		super(createNumberInputElement(html5Type), renderer, parser);
		// BiDi input is not expected - disable direction estimation.
		setDirectionEstimator(false);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			setDirection(Direction.LTR);
		}
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().textBox());
		addFocusHandler(this);
		addBlurHandler(this);
	}



	/**
	 * Adds a validator to this Ui-Widget
	 * 
	 * @param validatorToAdd
	 *            the validtors to add
	 */
	public void addValidator(IValidator<T> validatorToAdd) {
		this.validationHelper.addValidator(validatorToAdd);
	}



	/**
	 * Add an error output element where error messages are displayed
	 * 
	 * @param errorOutputToAdd
	 *            the error output element to add
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
	protected void onLoad() {
		super.onLoad();
		myWidgetHelper.checkInitialLoad(this);

	}



	@Override
	public void onFocus(FocusEvent event) {
		this.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().focus());
	}



	@Override
	public void onBlur(BlurEvent event) {
		this.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTextBoxCss().focus());
		this.validationHelper.validate(this);
	}



	/**
	 * Creates the right HTML input element.
	 * 
	 * @param type
	 *            the type
	 * @return the input element.
	 */
	protected static native InputElement createNumberInputElement(String type) /*-{
																				var e = $doc.createElement("INPUT");
																				e.type = type;
																				return e;
																				}-*/;



	/**
	 * Will capitalize the first letter.
	 * 
	 * @param input
	 *            the input
	 * @return the complete string containing the capitalized first letter
	 */
	private String capitalize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

	}



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
	 * Gets the maximum allowable length.
	 * 
	 * @return the maximum length, in characters
	 */
	public int getMaxLength() {
		return getInputElement().getMaxLength();
	}



	/**
	 * Gets the number of visible characters.
	 * 
	 * @return the number of visible characters
	 */
	public int getVisibleLength() {
		return getInputElement().getSize();
	}



	/**
	 * Sets the maximum allowable length.
	 * 
	 * @param length
	 *            the maximum length, in characters
	 */
	public void setMaxLength(int length) {
		getInputElement().setMaxLength(length);
	}



	/**
	 * Sets the number of visible characters.
	 * 
	 * @param length
	 *            the number of visible characters
	 */
	public void setVisibleLength(int length) {
		getInputElement().setSize(length);
	}



	private InputElement getInputElement() {
		return getElement().cast();
	}

}
