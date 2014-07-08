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
package de.swm.commons.mobile.client.setup;

import org.junit.Test;

import static org.junit.Assert.assertTrue;



public class OsDetectionImplTest {

	@Test
	public void testGetOs_android() throws Exception {
		OsDetection detection = new OsDetectionImpl(
				"Mozilla/5.0 (Linux; U; Android 2.3.3; zh-tw; HTC_Pyramid Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		assertTrue("Android expected", detection.isAndroid());
	}
	
	public void testGetOs_iPhone() throws Exception {
		OsDetection detection = new OsDetectionImpl("AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A538a Safari/419. ");
		assertTrue("Android expected", detection.isAndroid());
	}
	

}

