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
package de.swm.gwt.client.theme.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import de.swm.gwt.client.theme.GWTCssBundle;
import de.swm.gwt.client.theme.GWTImageBundle;
import de.swm.gwt.client.theme.GWTTheme;


/**
 * Defines all resources belonging to this base style. Details see {@link de.swm.gwt.client.theme.GWTTheme}.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-14, SWM Services GmbH
 */
public class BasePlainGwtTheme implements GWTTheme {

	private final BaseGWTCssBundle cssBundle;
	private final GWTImageBundle imageBundle;
	private final CssResource applicationCss;
	private final ClientBundle applicationImages;
	private final BaseJSBundle jsBundle;


	/**
	 * Default constructor.
	 */
	public BasePlainGwtTheme() {
		this(null, null);
	}

	/**
	 * Default constructor.
	 *
	 * @param applicationCss    application specific css definitions
	 * @param applicationImages application specific client bundle (app images, etc.)
	 */
	public BasePlainGwtTheme(CssResource applicationCss, ClientBundle applicationImages) {
		imageBundle = GWT.create(BaseGWTImageRessources.class);
		cssBundle = GWT.create(BaseGWTCssBundle.class);
		cssBundle.getTextCss().ensureInjected();
		cssBundle.getPageCss().ensureInjected();
		cssBundle.getButtonCss().ensureInjected();
		cssBundle.getErrorCss().ensureInjected();
		cssBundle.getGridCss().ensureInjected();
		cssBundle.getDatePickerCss().ensureInjected();
		this.jsBundle = GWT.create(BaseJSBundle.class);
		this.applicationCss = applicationCss;
		this.applicationImages = applicationImages;
		StyleInjector.inject(cssBundle.pureFramework().getText());
		//Vorbereitung um JS zu injizieren
		//ScriptInjector.fromString(jsBundle.myAwesomeJavaScript().getText()).inject();
	}


	@Override
	public GWTCssBundle getCssBundle() {
		return cssBundle;
	}


	@Override
	public GWTImageBundle getImageBundle() {
		return imageBundle;
	}

	@Override
	public <T extends CssResource> T getApplicationCss() {
		return (T) applicationCss;
	}

	@Override
	public <T extends ClientBundle> T getApplicationImages() {
		return (T) applicationImages;
	}
}
