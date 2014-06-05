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
package de.swm.commons.mobile.client.widgets.tree;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Dieses event wird geworfen, sobald sich die Baumstruktur geaendert hat.
 * 
 * @author wiese.daniel, vilbig.alexander <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public class TreeChangedEvent extends GwtEvent<TreeChangedHandler> {

	/**
	 * GWT Event type system.
	 */
	public static final Type<TreeChangedHandler> TYPE = new Type<TreeChangedHandler>();

	private final ITree newRoot;
	private final EventType type;

	/**
	 * Unterevent-Typ.
	 * 
	 * @author wiese.daniel <br>
	 *         copyright (C) 2012, SWM Services GmbH
	 * 
	 */
	public enum EventType {
		/** Unter-Event typ. **/
		INIT_COMPLETE, CHILD_SELECTED, BACK_EVENT;
	}

	/**
	 * Default constructor.
	 * 
	 * @param newRoot
	 *            das neue root element des baumes (das ausgewaehlte element)
	 * @param eventType
	 *            der typ des Events
	 */
	public TreeChangedEvent(ITree newRoot, EventType eventType) {
		this.newRoot = newRoot;
		this.type = eventType;
	}

	/**
	 * Returns the newRoot.
	 * 
	 * @return the newRoot
	 */
	public ITree getNewRoot() {
		return newRoot;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TreeChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TreeChangedHandler handler) {
		handler.onTreeChanged(this);
	}

}