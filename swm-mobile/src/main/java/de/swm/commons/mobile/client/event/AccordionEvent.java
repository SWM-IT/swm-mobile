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

import de.swm.commons.mobile.client.widgets.AccordionStack;

/**
 * TODO Dokumentieren.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class AccordionEvent {

	/**
	 * States of an Accordeon.
	 */
	public enum Type {
		/** Opens an Accordion stack. **/
		Open,
		/** Will close an accordion stack. **/
		Close;
	};

	private Type myType;
	private final AccordionStack accordionStack;

	/**
	 * Constructor
	 * @param myType  event type.
	 * @param accordionStack the closed or expanded stack
	 */
	public AccordionEvent(Type myType, AccordionStack accordionStack){

		this.myType = myType;
		this.accordionStack = accordionStack;
	}


	public AccordionStack getAccordionStack() {
		return accordionStack;
	}

	public Type getMyType() {
		return myType;
	}

	/**
	 * Will dispatch this event.
	 *
	 * @param handler
	 *            handler
	 */
	public void dispatch(AccordionEventsHandler handler) {
		switch (myType) {
			case Open:
				handler.onExpand(this);
				break;
			case Close:
				handler.onClose(this);
				break;
		}
	}
}
