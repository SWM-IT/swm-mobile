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
package de.swm.commons.mobile.client.validation;

import com.google.gwt.user.client.ui.HasValue;
import de.swm.commons.mobile.client.widgets.ErrorLabel;

/**
 * Implementations of this interface are responsible to display the error output of registered components. In one case
 * may all elements of  one form will share one error output instance, in another case may one field has
 * exactly one corresponding error output element. One default implementation is {@link ErrorLabel}
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public interface IErrorOutputElement {

	/**
	 * Appends a new error message.
	 *
	 * @param producer             the component which is producing this message.
	 * @param errorMessageToAppend the message to append
	 */
	void appendErrorMessage(HasValue<?> producer, String errorMessageToAppend);

	/**
	 * Clears all error messages of the specified component.
	 */
	void clearErrorMessage();

}
