package de.swm.commons.mobile.client.setup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewPortTest {

	private static String EXPECTED = "initial-scale=1,maximum-scale=1,width=device-width,user-scalable=no";

	@Test
	public void testGetContent() throws Exception {
		ViewPort toTest = new ViewPort();
		toTest.setWidthToDeviceWidth();
		toTest.setInitialScale(1);
		toTest.setMaximumScale(1);
		toTest.setUserScaleAble(false);

		assertEquals(EXPECTED, toTest.getContent());
	}
}