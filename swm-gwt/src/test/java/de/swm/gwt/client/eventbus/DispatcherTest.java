package de.swm.gwt.client.eventbus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.swm.gwt.client.eventbus.Dispatcher;
import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.eventbus.IMobileCustomData;
import de.swm.gwt.client.eventbus.IMobileEventHandler;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;


public class DispatcherTest {
	
	private IDispatcher dispatcher;
	
	@Before
	public void setup() {
		dispatcher = new Dispatcher();

	}
	
	@Test
	public void testFireAndRecieveEvent() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION, th.getEventType());
	}
	
	@Test
	public void testFireAndRecieveMobileEvent() throws Exception {
		final MobileTestHandlder th = new MobileTestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		dispatcher.fireMobileEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireMobileEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION, th.getEventType());
	}
	
	@Test
	public void testFireMultipleEventsAndRecieveEvents() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		final TestHandlder th2 = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2);
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION, th.getEventType());
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2.getEventType());
	}
	
	@Test
	public void testFireMultipleEventsAndRecieveEvents_COMPLETE_REGISTRATIONeventDisabled() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		final TestHandlder th2 = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2);
		
		dispatcher.disableEvents(PlatformEvents.COMPLETE_REGISTRATION);
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertNull("Dieses Event war disabled", th.getEventType());
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2.getEventType());
	}
	
	@Test
	public void testFireMultipleEventsAndRecieveEvents_DisableAll() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		final TestHandlder th2 = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2);
		
		Set<IEvent> all= dispatcher.disableAllEvents();
		assertEquals(2, all.size());
		assertTrue("Expected", all.contains(PlatformEvents.COMPLETE_REGISTRATION));
		assertTrue("Expectd", all.contains(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY));
		
		Set<IEvent> allDisabled= dispatcher.getAllDisabledEvents();
		assertEquals(2, allDisabled.size());
		assertTrue("Expected", allDisabled.contains(PlatformEvents.COMPLETE_REGISTRATION));
		assertTrue("Expectd", allDisabled.contains(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY));
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertNull("Dieses Event war disabled", th.getEventType());
		assertNull("Dieses Event war disabled", th2.getEventType());
	}
	
	@Test
	public void testFireMultipleEventsAndRecieveEvents_DisableAllEnableAll() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		final TestHandlder th2 = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2);
		
		dispatcher.disableAllEvents();
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertNull("Dieses Event war disabled", th.getEventType());
		assertNull("Dieses Event war disabled", th2.getEventType());
		
		dispatcher.enableAllEvents();
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION, th.getEventType());
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2.getEventType());
		
	}
	
	@Test
	public void testFireMultipleEventsAndRecieveEvents_DisableAllEnableOne() throws Exception {
		final TestHandlder th = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION, th);
		
		final TestHandlder th2 = new TestHandlder();
		dispatcher.registerEventTypes(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2);
		
		dispatcher.disableAllEvents();
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertNull("Dieses Event war disabled", th.getEventType());
		assertNull("Dieses Event war disabled", th2.getEventType());
		
		dispatcher.enableEvents(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION);
		dispatcher.fireEvent(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY);
		dispatcher.fireEvent(PlatformEvents.DISPLAY_START_SCREEN);
		
		assertNull("Ist Disabled", th.getEventType());
		assertEquals(PlatformEvents.COMPLETE_REGISTRATION_SUMMARY, th2.getEventType());
		
		
	}
	
	public static final class TestHandlder implements IEventHandler {

		private IEvent eventType;

		@Override
		public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
			this.eventType = eventType;
		}

		/**
		 * Returns the eventType.
		 * @return the eventType
		 */
		public IEvent getEventType() {
			return eventType;
		}
		
		
		
	}
	
	public static final class MobileTestHandlder implements IMobileEventHandler {

		private IEvent eventType;

		/**
		 * Returns the eventType.
		 * @return the eventType
		 */
		public IEvent getEventType() {
			return eventType;
		}

		@Override
		public void handleEvent(IEvent eventType, IPage originator, IMobileCustomData customData) {
			this.eventType = eventType;
			
		}
		
		
		
	}
	
	

}
