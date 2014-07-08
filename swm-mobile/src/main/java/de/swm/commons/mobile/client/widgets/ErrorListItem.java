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
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.validation.IErrorOutputElement;



/**
 * Simple implementation of an error Output element as List Item Representation. Supports form structures where form
 * elements are ListItem's. The {@link ErrorListItem} should be used if one form field will display exactly one message
 * at time.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public class ErrorListItem extends ListItem implements IErrorOutputElement {

	private Label errorTitle;
	private Label errorMessage;



	/**
	 * Default constructor.
	 */
	public ErrorListItem() {
		errorTitle = new Label();
		// FIXME: Internalization?
		errorTitle.setText("Fehler: ");

		errorMessage = new Label();
		errorMessage.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getErrorCss().errorText());
		add(errorTitle);
		add(errorMessage);
		this.setVisible(false);

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendErrorMessage(HasValue<?> producer, String errorMessageToAppend) {
		errorMessage.setText(errorMessageToAppend);
		this.setVisible(true);

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearErrorMessage() {
		errorMessage.setText("");
		this.setVisible(false);

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		errorMessage.setVisible(visible);
		errorTitle.setVisible(visible);
	}

}
