package de.swm.gwt.client.interfaces;

import java.util.Collection;
import java.util.Map;



/**
 * Models support a form of "introspection" as property names and values can be retrieved at runtime.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IModelData {

	/**
	 * Returns the named property from this model instance.
	 * 
	 * @param property
	 *            the property name
	 * @param <X>
	 *            der typ der property
	 * @return the value
	 */
	<X> X get(String property);



	/**
	 * Returns the model's properties and values as a map.
	 * 
	 * <p/>
	 * Changes to the returned collection should not mutate this model instance.
	 * 
	 * @return the properties and values
	 */
	Map<String, Object> getProperties();



	/**
	 * Returns an collection of the model's property names.
	 * 
	 * <p/>
	 * The collection should be a snapshot of the property names that the model represents.
	 * 
	 * <p/>
	 * Changes to the returned collection should not mutate this model instance.
	 * @return die liste der Properties.
	 */
	Collection<String> getPropertyNames();



	/**
	 * Removes the named property from this model instance.
	 * 
	 * @param property
	 *            the property name
	 * @return the old value for the property
	 * @param <X>
	 *            der typ der property
	 */
	<X> X remove(String property);



	/**
	 * Sets a property.
	 * 
	 * @param property
	 *            the property name
	 * @param value
	 *            property value
	 * @return the old value for the property
	 * @param <X>
	 *            der typ der property
	 */
	<X> X set(String property, X value);

}
