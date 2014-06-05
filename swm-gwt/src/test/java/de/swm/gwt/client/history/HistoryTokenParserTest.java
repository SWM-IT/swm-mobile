package de.swm.gwt.client.history;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Test;

import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.history.HistoryTokenParser;
import de.swm.gwt.client.interfaces.ILocation;



public class HistoryTokenParserTest {

	@Test
	public void testHistoryParser_withForward() throws Exception {
		IDispatcher disp = createMock(IDispatcher.class);
		HistoryTokenParser<PlatformEvents, PlatformEvents> toTest = new HistoryTokenParser<PlatformEvents, PlatformEvents>(
				disp, PlatformEvents.class, null);
		disp.fireEvent(eq(PlatformEvents.COMPLETE_REGISTRATION), isNull(ILocation.class), isA(ICustomData.class));
		disp.fireEvent(PlatformEvents.FORWARD_TO_TARIFBERATUNG, (ILocation) null, (ICustomData) null);

		replay(disp);
		ICustomData userData = toTest
				.parseTokenAndFireEvents("COMPLETE_REGISTRATION=S_y_czzZNlyDRPHQdWPiAeXvHiQHrAphRJLGSBNnup0-EOL;FORWARD_TO_TARIFBERATUNG");
		assertNotNull("User daten objekt erwartet", userData);
		assertNotNull("Forward erwartet", userData.forwardEvent());
		assertEquals("S_y_czzZNlyDRPHQdWPiAeXvHiQHrAphRJLGSBNnup0-EOL", userData.userObject());
		userData.forwardEvent().execute();
		verify(disp);
	}



	@Test
	public void testHistoryParser_withForward_WittoutParames() throws Exception {
		IDispatcher disp = createMock(IDispatcher.class);
		HistoryTokenParser<PlatformEvents, PlatformEvents> toTest = new HistoryTokenParser<PlatformEvents, PlatformEvents>(
				disp, PlatformEvents.class, null);
		disp.fireEvent(eq(PlatformEvents.COMPLETE_REGISTRATION), isNull(ILocation.class), isA(ICustomData.class));
		disp.fireEvent(PlatformEvents.FORWARD_TO_TARIFBERATUNG, (ILocation) null, (ICustomData) null);

		replay(disp);
		ICustomData userData = toTest.parseTokenAndFireEvents("COMPLETE_REGISTRATION;FORWARD_TO_TARIFBERATUNG");
		assertNotNull("User daten objekt erwartet", userData);
		assertNotNull("Forward erwartet", userData.forwardEvent());
		assertNull(userData.userObject());
		userData.forwardEvent().execute();
		verify(disp);
	}



	@Test
	public void testHistoryParser_withoutForward() throws Exception {
		IDispatcher disp = createMock(IDispatcher.class);
		HistoryTokenParser<PlatformEvents, PlatformEvents> toTest = new HistoryTokenParser<PlatformEvents, PlatformEvents>(
				disp, PlatformEvents.class, null);
		disp.fireEvent(eq(PlatformEvents.COMPLETE_REGISTRATION), isNull(ILocation.class), isA(ICustomData.class));
		replay(disp);
		ICustomData userData = toTest
				.parseTokenAndFireEvents("COMPLETE_REGISTRATION=S_y_czzZNlyDRPHQdWPiAeXvHiQHrAphRJLGSBNnup0-EOL");
		assertNotNull("User daten objekt erwartet", userData);
		assertEquals("S_y_czzZNlyDRPHQdWPiAeXvHiQHrAphRJLGSBNnup0-EOL", userData.userObject());
		assertNull("Kein Forward erwartet", userData.forwardEvent());
		verify(disp);
	}

}
