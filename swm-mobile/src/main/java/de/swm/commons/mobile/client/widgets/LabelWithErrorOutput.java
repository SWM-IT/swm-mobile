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

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

import de.swm.commons.mobile.client.validation.IErrorOutputElement;



/**
 * Defines a label with an included Error outut option.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public class LabelWithErrorOutput extends VerticalPanel implements IErrorOutputElement {

	private final Label title;
	private final ErrorLabel errorLabel;



	/**
	 * Default constructor.
	 */
	public LabelWithErrorOutput() {
		this("");
	}



	/**
	 * Default constructor.
	 * 
	 * @param labelText
	 *            the title text of the Label
	 */
	public LabelWithErrorOutput(String labelText) {
		this.title = new Label(labelText);
		this.errorLabel = new ErrorLabel();
		this.add(title);
		this.add(errorLabel);
	}



	/**
	 * Sets the label's content to the given text.
	 * <p>
	 * Doesn't change the widget's direction or horizontal alignment if {@code directionEstimator} is null. Otherwise,
	 * the widget's direction is set using the estimator, and its alignment may therefore change as described in
	 * {@link #setText(String, com.google.gwt.i18n.client.HasDirection.Direction) setText(String, Direction)}.
	 * 
	 * @param text
	 *            the widget's new text
	 */
	public void setText(String text) {
		this.title.setText(text);
	}



	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public Label getLabel() {
		return title;
	}



	/**
	 * Returns the errorLabel.
	 * 
	 * @return the errorLabel
	 */
	public ErrorLabel getErrorLabel() {
		return errorLabel;
	}



	@Override
	public void appendErrorMessage(HasValue<?> producer, String errorMessageToAppend) {
		this.errorLabel.appendErrorMessage(producer, errorMessageToAppend);

	}



	@Override
	public void clearErrorMessage() {
		this.errorLabel.clearErrorMessage();

	}

}
