package de.swm.gwt.client.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;



/**
 * Implementierung einen einachen Dispachers.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2009-2011, SWM Services GmbH
 * 
 */
public class Dispatcher implements IDispatcher {

	private final Map<IEvent, List<IEventHandler>> eventHandlerMap = new HashMap<IEvent, List<IEventHandler>>();
	private final Map<IEvent, List<IMobileEventHandler>> eventHandlerMobileMap = new HashMap<IEvent, List<IMobileEventHandler>>();

	private final Set<IEvent> disabledEvents = new HashSet<IEvent>();



	/**
	 * {@inheritDoc}
	 * 
	 */
	public void registerEventTypes(IEvent event, IEventHandler handler) {
		List<IEventHandler> list = eventHandlerMap.get(event);
		if (list == null) {
			list = new ArrayList<IEventHandler>();
			eventHandlerMap.put(event, list);
		}
		list.add(handler);
	}



	@Override
	public void registerEventTypes(IEvent event, IMobileEventHandler handler) {
		List<IMobileEventHandler> list = eventHandlerMobileMap.get(event);
		if (list == null) {
			list = new ArrayList<IMobileEventHandler>();
			eventHandlerMobileMap.put(event, list);
		}
		list.add(handler);

	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see .IDispatcher{@link #fireEvent(Enum, ILocation, ICustomData)}
	 */
	public void fireEvent(IEvent event, ILocation location, ICustomData customData) {
		if (!disabledEvents.contains(event)) {
			List<IEventHandler> list = eventHandlerMap.get(event);
			if (list != null) {
				for (IEventHandler eventHandler : list) {
					eventHandler.handleEvent(event, location, customData);
				}
			}
		}
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see .IDispatcher{@link #fireEvent(Enum)}
	 */
	@Override
	public void fireEvent(IEvent event) {
		fireEvent(event, (ILocation) null, (ICustomData) null);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(IEvent event, ICustomData userObject) {
		fireEvent(event, (ILocation) null, userObject);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see .IDispatcher{@link #fireEvent(Enum, ILocation)}
	 */
	@Override
	public void fireEvent(IEvent event, ILocation location) {
		fireEvent(event, location, (ICustomData) null);

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(IEvent event, IForwardEvent eventToForward) {
		final CustomData wrapper = new CustomData(null, null, null);
		wrapper.setForwardEvent(eventToForward);
		this.fireEvent(event, null, wrapper);

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(IEvent event, IForwardEvent eventToForward, ILocation location) {
		final CustomData wrapper = new CustomData(null, null, null);
		wrapper.setForwardEvent(eventToForward);
		this.fireEvent(event, location, wrapper);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(IEvent event, IForwardEvent eventToForward, ILocation location, ICustomData customData) {
		final CustomData wrapper = new CustomData(customData.title(), customData.subtitle(), customData.footer());
		wrapper.setForwardEvent(eventToForward);
		this.fireEvent(event, location, wrapper);

	}



	// mobiler teil
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireMobileEvent(IEvent event) {
		fireMobileEvent(event, null, null);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireMobileEvent(IEvent event, IPage originatorPage) {
		fireMobileEvent(event, originatorPage, null);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireMobileEvent(IEvent event, IPage originatorPage, IMobileCustomData customData) {
		if (!disabledEvents.contains(event)) {
			List<IMobileEventHandler> list = eventHandlerMobileMap.get(event);
			if (list != null) {
				for (IMobileEventHandler eventHandler : list) {
					eventHandler.handleEvent(event, originatorPage, customData);
				}
			}
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disableEvents(IEvent... eventToDisable) {
		if (eventToDisable != null) {
			for (IEvent iEvent : eventToDisable) {
				disabledEvents.add(iEvent);
			}
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IEvent> disableAllEvents() {
		this.disabledEvents.addAll(this.eventHandlerMap.keySet());
		return this.getAllDisabledEvents();
	}



	@Override
	public Set<IEvent> getAllDisabledEvents() {
		return new HashSet<IEvent>(disabledEvents);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enableAllEvents() {
		this.disabledEvents.clear();

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enableEvents(IEvent... eventToEnable) {
		if (eventToEnable != null) {
			for (IEvent iEvent : eventToEnable) {
				disabledEvents.remove(iEvent);
			}
		}
	}

}
