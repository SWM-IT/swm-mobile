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
package de.swm.commons.mobile.client.widgets.itf;

/**
 * Interface for widgets that have keys and values assigned to
 * items belonging to the widget. Used e.g. by {@see DropDownList}.
 * 
 * 
 *         
 * 
 * @param <T> der typ
 */
public interface IProvidesKeyAndValue<T> {

	/**
	 * Get the key for a list item.
	 * 
	 * @param item
	 *            the list item
	 * @return the key that represents the item
	 */
	String getKey(T item);



	/**
	 * Gets the value for the specified Key
	 * 
	 * @param key
	 *            the key
	 * @return the value
	 */
	T getValue(String key);

}
