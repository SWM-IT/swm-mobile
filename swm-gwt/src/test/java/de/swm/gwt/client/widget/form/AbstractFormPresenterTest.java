package de.swm.gwt.client.widget.form;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Button;


/**
 * Unit-Test Prototy> leider laesst sich GXT nicht testen.
 * @author Wiese.Daniel
 * <br>copyright (C) 2010, SWM Services GmbH
 *
 */
public class AbstractFormPresenterTest {

	@Before
	public void setUp() throws Exception {
		GWTMockUtilities.disarm();
	}



	@After
	public void tearDown() {
		GWTMockUtilities.restore();
	}



	@Test
	public void testSomething() {
		Button mock = EasyMock.createMock(Button.class);
		mock.setText("expected text");
		replay(mock);
		// test
		mock.setText("expected text");
		verify(mock);
	}

}
