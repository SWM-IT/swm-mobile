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
package de.swm.commons.mobile.client.validation.impl;

import com.google.gwt.user.client.ui.HasValue;
import de.swm.commons.mobile.client.validation.IErrorOutputElement;
import de.swm.commons.mobile.client.validation.IValidator;

import java.util.ArrayList;
import java.util.List;


/**
 * Helper class for validation.
 * @author wiese.daniel
 * <br>copyright (C) 2012, SWM Services GmbH
 *
 * @param <T> the type of the field
 */
public class ValidationHelper<T> {

	private List<IValidator<T>> validators = new ArrayList<IValidator<T>>();
	private List<IErrorOutputElement> validatorErrorOutputs = new ArrayList<IErrorOutputElement>();



	/**
	 * Adds a validator to this Ui-Widget
	 * 
	 * @param validatorToAdd
	 *            the validtors to add
	 */
	public void addValidator(IValidator<T> validatorToAdd) {
		this.validators.add(validatorToAdd);
	}

	public void clearValidation() {
		for (IErrorOutputElement error : validatorErrorOutputs) {
			error.clearErrorMessage();
		}
	}



	/**
	 * Add an error output element where error messages are displayed
	 * 
	 * @param errorOutputToAdd
	 *            the error output element to add
	 */
	public void addErrorOuptut(IErrorOutputElement errorOutputToAdd) {
		this.validatorErrorOutputs.add(errorOutputToAdd);
	}



	/**
	 * Will remove all registered validators.
	 */
	public void removeAllValidators() {
		this.validators.clear();
	}



	/**
	 * Executes the validator.
	 * @param owner the owning field
	 * @return true if the owning filed contains errors.
	 */
	public boolean validate(HasValue<T> owner) {
		boolean hasErrors = false;
		if (!validators.isEmpty()) {
			for (IValidator<T> validator : validators) {
				final String errorMessage = validator.validate(owner);
				if (errorMessage != null) {
					hasErrors = true;
					for (IErrorOutputElement validatorErrorOutput : this.validatorErrorOutputs) {
						validatorErrorOutput.appendErrorMessage(owner, errorMessage);
					}
				} else {
					for (IErrorOutputElement validatorErrorOutput : this.validatorErrorOutputs) {
						validatorErrorOutput.clearErrorMessage();
					}
				}
			}
		}
		return hasErrors;
	}

}
