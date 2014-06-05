package de.swm.gwt.server;

import static org.junit.Assert.*;

import org.junit.Test;



public class MobileRemoteServiceServletTest {

	@Test
	public void testExtractModuleName() throws Exception {
		final String andoidPhoneGapURL = "file:///android_asset/www/mvgfahrplan/";
		assertEquals("mvgfahrplan", MobileRemoteServiceServlet.extractModuleName(andoidPhoneGapURL));
	}

}
