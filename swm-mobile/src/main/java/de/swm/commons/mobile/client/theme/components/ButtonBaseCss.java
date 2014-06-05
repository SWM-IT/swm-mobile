package de.swm.commons.mobile.client.theme.components;

import com.google.gwt.resources.client.CssResource;

/**
 * Überklasse um Shared Styles zu haben.
 * @author wiese.daniel
 * 			karsunke.franziskus
 * 		copyright (C) 2012, SWM Services GmbH
 */
@CssResource.Shared
public interface ButtonBaseCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("pressed")
	public String pressed();

	/** CSS Style name. @return style name. **/
	@ClassName("button")
	public String button();

	/** CSS Style name. @return style name. **/
	@ClassName("backButton")
	public String backButton();

	/** CSS Style name. @return style name. **/
	@ClassName("pointer")
	public String pointer();

	/** CSS Style name. @return style name. **/
	@ClassName("nextButton")
	public String nextButton();
}
