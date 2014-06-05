package de.swm.gwt.client.eventbus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.google.web.bindery.event.shared.binder.GenericEvent;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;

import java.util.*;


/**
 * Implementierung einen einachen Dispachers.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2009-2011, SWM Services GmbH
 * 
 */
public class GwtEventBusDispatcher implements IDispatcher {

	interface InternalBinder extends EventBinder<GwtEventBusDispatcher> {}
	private static InternalBinder eventBinder = GWT.create(InternalBinder.class);

	private final EventBus eventBus = GWT.create(SimpleEventBus.class);

	private final Map<IEvent, List<IEventHandler>> eventHandlerMap = new HashMap<IEvent, List<IEventHandler>>();
	private final Map<IEvent, List<IMobileEventHandler>> eventHandlerMobileMap = new HashMap<IEvent, List<IMobileEventHandler>>();

	private final Set<IEvent> disabledEvents = new HashSet<IEvent>();


	public GwtEventBusDispatcher() {
		eventBinder.bindEventHandlers(this, eventBus);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see .IDispatcher{@link #fireEvent(Enum, ILocation, ICustomData)}
	 */
	public void fireEvent(IEvent event, ILocation location, ICustomData customData) {
		eventBus.fireEvent(new EnumBasedEvent(event, location, customData));
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
		eventBus.fireEvent(new EnumBasedMobileEvent(event, customData, originatorPage));
	}

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

	@EventHandler
	void onEnumBasedEventRecieved(EnumBasedEvent event) {
		if (!disabledEvents.contains(event.getEnumBasedEvent())) {
			final List<IEventHandler> listNonMobile = eventHandlerMap.get(event.getEnumBasedEvent());
			if (listNonMobile != null) {
				for (IEventHandler eventHandler : listNonMobile) {
					eventHandler.handleEvent(event.getEnumBasedEvent(), event.getLocation(),
							event.getCustomData());
				}
			}
		}
	}

	@EventHandler
	void onEnumBasedEventRecieved(EnumBasedMobileEvent event) {
		if (!disabledEvents.contains(event.getEnumBasedEvent())) {
			final List<IMobileEventHandler> listMobile = eventHandlerMobileMap.get(event.getEnumBasedEvent());
			if (listMobile != null) {
				for (IMobileEventHandler eventHandler : listMobile) {
					eventHandler.handleEvent(event.getEnumBasedEvent(), event.getOriginatorPage(),
							event.getCustomData());
				}
			}

		}
	}

	/**
	 * Erzeugt einen Warapper um die Enum basierten events.
	 */
	public static class EnumBasedEvent extends GenericEvent {
		private final IEvent enumBasedEvent;
		private final ICustomData customData;
		private ILocation location;

		public EnumBasedEvent(IEvent enumBasedEvent, ILocation location, ICustomData customData) {

			this.enumBasedEvent = enumBasedEvent;
			this.customData = customData;
			this.location = location;
		}

		public IEvent getEnumBasedEvent() {
			return enumBasedEvent;
		}

		public ICustomData getCustomData() {
			return customData;
		}

		public ILocation getLocation() {
			return location;
		}
	}

	/**
	 * Erzeugt einen Warapper um die Enum basierten events.
	 */
	public static class EnumBasedMobileEvent extends GenericEvent {
		private final IEvent enumBasedEvent;
		private final IMobileCustomData customData;
		private IPage originatorPage;

		public EnumBasedMobileEvent(IEvent enumBasedEvent, IMobileCustomData customData, IPage originatorPage) {

			this.enumBasedEvent = enumBasedEvent;
			this.customData = customData;
			this.originatorPage = originatorPage;
		}

		public IEvent getEnumBasedEvent() {
			return enumBasedEvent;
		}

		public IMobileCustomData getCustomData() {
			return customData;
		}

		public IPage getOriginatorPage() {
			return originatorPage;
		}

		public void setOriginatorPage(IPage originatorPage) {
			this.originatorPage = originatorPage;
		}
	}

}
