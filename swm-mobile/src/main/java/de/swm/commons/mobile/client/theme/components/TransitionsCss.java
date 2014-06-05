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
package de.swm.commons.mobile.client.theme.components;

import com.google.gwt.resources.client.CssResource;



/**
 * Definines css ressources for transitions.
 * 
 */
public interface TransitionsCss extends CssResource {
	/** CSS Style name. @return style name. **/
	String slide();



	/** CSS Style name. @return style name. **/
	String slideup();



	/** CSS Style name. @return style name. **/
	String slidedown();



	/** CSS Style name. @return style name. **/
	String fade();



	/** CSS Style name. @return style name. **/
	String pop();



	/** CSS Style name. @return style name. **/
	String flip0();



	/** CSS Style name. @return style name. **/
	String flip1();



	/** CSS Style name. @return style name. **/
	String swap0();



	/** CSS Style name. @return style name. **/
	String swap1();



	/** CSS Style name. @return style name. **/
	String in();



	/** CSS Style name. @return style name. **/
	String out();



	/** CSS Style name. @return style name. **/
	String reverse();



	/** CSS Style name. @return style name. **/
	String start();
}
