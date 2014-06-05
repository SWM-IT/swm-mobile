package de.swm.commons.mobile.client.validation;

/**
 * Interface for widgets that contain validators.
 *
 * @author kutschke.christian <br/>
 *         copyright (C) 2012, SWM Services GmbH
 */
public interface IHasValidator {

	/**
	 * Validates the current content of the widget with the underlying validators.
	 *
	 * @return true if valid, false otherwise
	 */
	boolean validate();

	/**
	 * Clears the component of all validation results.
	 */
	void clearValidation();

}
