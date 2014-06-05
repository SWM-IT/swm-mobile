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
package de.swm.commons.mobile.client.event;

import com.google.gwt.core.client.JavaScriptObject;



/**
 * Java script wrapper for browsers touch events.
 * 
 */
public class Touch extends JavaScriptObject {

	/**
	 * Default constructor.
	 */
	protected Touch() {

	}



	/**
	 * Client x position.
	 * 
	 * @return x position.
	 */
	public final native int getClientX() /*-{
											return this.clientX;
											}-*/;


	/**
	 * Client y position.
	 * 
	 * @return y position.
	 */
	public final native int getClientY() /*-{
											return this.clientY;
											}-*/;

	/**
	 * Client page x position.
	 * 
	 * @return page x position.
	 */
	public final native int pageX() /*-{
									return this.pageX;
									}-*/;


	/**
	 * Client page y position.
	 * 
	 * @return page y position.
	 */
	public final native int pageY() /*-{
									return this.pageY;
									}-*/;


	/**
	 * Client screen x position.
	 * 
	 * @return x position.
	 */
	public final native int screenX() /*-{
										return this.screenX;
										}-*/;


	/**
	 * Client screen y position.
	 * 
	 * @return screen y position.
	 */
	public final native int screenY() /*-{
										return this.screenY;
										}-*/;
}
