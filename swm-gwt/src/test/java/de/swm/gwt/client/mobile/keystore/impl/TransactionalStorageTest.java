package de.swm.gwt.client.mobile.keystore.impl;

import de.swm.gwt.client.mobile.keystore.IStorageOperationCompleted;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.ITransaction;
import de.swm.gwt.client.testhelper.SwmTestStorage;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Unit Test
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class TransactionalStorageTest {

	private IStorage toTest;
	private boolean commitCompletedWasCalled;

	@Before
	public void setUp() {
		toTest = new TransactionalStorage(new SwmTestStorage());
		commitCompletedWasCalled = false;
		//toTest.beginTransaction().commit();
	}

	@Test
	public void testClear_withRollback() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.clear();
		assertEquals(0, toTest.getLength());
		assertTrue(toTest.getItem("Key1") == null);
		assertTrue(toTest.getItem("Key2") == null);
		assertTrue(toTest.getItem("Key3") == null);
		phaseCommit.rollback();
		assertEquals(3, toTest.getLength());
		assertEquals("Value1", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
	}

	@Test
	public void testClear_withoutTx() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		toTest.clear();
		assertTrue(toTest.getItem("Key1") == null);
		assertTrue(toTest.getItem("Key2") == null);
		assertTrue(toTest.getItem("Key3") == null);
		assertEquals(0, toTest.getLength());
	}

	@Test
	public void testClear_withInsert_andRollback() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.clear();
		toTest.setItem("Key4", "Value4");
		toTest.setItem("Key3", "Value3-changed");
		assertEquals(2, toTest.getLength());
		assertTrue(toTest.getItem("Key1") == null);
		assertTrue(toTest.getItem("Key2") == null);
		assertEquals("Value3-changed", toTest.getItem("Key3"));
		assertEquals("Value4", toTest.getItem("Key4"));
		phaseCommit.rollback();
		assertEquals(3, toTest.getLength());
		assertEquals("Value1", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));

	}

	@Test
	public void testClear_withCommit() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.clear();
		assertEquals(0, toTest.getLength());
		assertTrue(toTest.getItem("Key1") == null);
		assertTrue(toTest.getItem("Key2") == null);
		assertTrue(toTest.getItem("Key3") == null);
		phaseCommit.commit(new IStorageOperationCompleted() {
			@Override
			public void isCompleted() {
				assertEquals(0, toTest.getLength());
				assertTrue(toTest.getItem("Key1") == null);
				assertTrue(toTest.getItem("Key2") == null);
				assertTrue(toTest.getItem("Key3") == null);
				commitCompletedWasCalled = true;
			}
		});
		assertTrue("Commit completed was not called", commitCompletedWasCalled);

	}

	@Test
	public void testClear_withInsert_withCommit() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.clear();
		toTest.setItem("Key4", "Value4");
		toTest.setItem("Key3", "Value3-changed");
		assertEquals(2, toTest.getLength());
		assertTrue(toTest.getItem("Key1") == null);
		assertTrue(toTest.getItem("Key2") == null);
		assertEquals("Value3-changed", toTest.getItem("Key3"));
		assertEquals("Value4", toTest.getItem("Key4"));
		phaseCommit.commit(new IStorageOperationCompleted() {
			@Override
			public void isCompleted() {
				assertEquals(2, toTest.getLength());
				assertTrue(toTest.getItem("Key1") == null);
				assertTrue(toTest.getItem("Key2") == null);
				assertEquals("Value3-changed", toTest.getItem("Key3"));
				assertEquals("Value4", toTest.getItem("Key4"));
				commitCompletedWasCalled = true;
			}
		});
		assertTrue("Commit completed was not called", commitCompletedWasCalled);

	}


	@Test
	public void testGetItem_withCommitAndRollback() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		assertEquals("Value1", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		phaseCommit.commit(new IStorageOperationCompleted() {
			@Override
			public void isCompleted() {
				assertEquals("Value1", toTest.getItem("Key1"));
				assertEquals("Value2", toTest.getItem("Key2"));
				assertEquals("Value3", toTest.getItem("Key3"));
				final ITransaction phaseCommit2 = toTest.beginTransaction();
				phaseCommit2.rollback();
				assertEquals("Value1", toTest.getItem("Key1"));
				assertEquals("Value2", toTest.getItem("Key2"));
				assertEquals("Value3", toTest.getItem("Key3"));
				commitCompletedWasCalled = true;
			}
		});
		assertTrue("Commit completed was not called", commitCompletedWasCalled);

	}


	@Test
	public void testRemoveItem_withCommit() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.removeItem("Key1");
		assertEquals(null, toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		phaseCommit.commit(new IStorageOperationCompleted() {
			@Override
			public void isCompleted() {
				assertEquals(null, toTest.getItem("Key1"));
				assertEquals("Value2", toTest.getItem("Key2"));
				assertEquals("Value3", toTest.getItem("Key3"));
				commitCompletedWasCalled = true;
			}
		});
		//assertTrue("Commit completed was not called", commitCompletedWasCalled);

	}

	@Test
	public void testRemoveItem_withRollback() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.removeItem("Key1");
		assertEquals(null, toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		phaseCommit.rollback();
		assertEquals("Value1", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));

	}

	@Test
	public void testSetItem_withCommit() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.setItem("Key1", "Value1-changed");
		toTest.setItem("Key5", "Value5");
		toTest.setItem("Key6", "Value6");
		assertEquals("Value1-changed", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		assertEquals("Value5", toTest.getItem("Key5"));
		assertEquals("Value6", toTest.getItem("Key6"));
		assertEquals(5, toTest.getLength());
		phaseCommit.commit(new IStorageOperationCompleted() {
			@Override
			public void isCompleted() {
				assertEquals(5, toTest.getLength());
				assertEquals("Value1-changed", toTest.getItem("Key1"));
				assertEquals("Value2", toTest.getItem("Key2"));
				assertEquals("Value3", toTest.getItem("Key3"));
				assertEquals("Value5", toTest.getItem("Key5"));
				assertEquals("Value6", toTest.getItem("Key6"));
				commitCompletedWasCalled = true;
			}
		});
		assertTrue("Commit completed was not called", commitCompletedWasCalled);

	}

	@Test
	public void testSetItem_withRollback() throws Exception {
		toTest.setItem("Key1", "Value1");
		toTest.setItem("Key2", "Value2");
		toTest.setItem("Key3", "Value3");
		assertEquals(3, toTest.getLength());
		final ITransaction phaseCommit = toTest.beginTransaction();
		toTest.setItem("Key1", "Value1-changed");
		toTest.setItem("Key5", "Value5");
		toTest.setItem("Key6", "Value6");
		assertEquals("Value1-changed", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		assertEquals("Value5", toTest.getItem("Key5"));
		assertEquals("Value6", toTest.getItem("Key6"));
		assertEquals(5, toTest.getLength());
		phaseCommit.rollback();
		assertEquals(3, toTest.getLength());
		assertEquals("Value1", toTest.getItem("Key1"));
		assertEquals("Value2", toTest.getItem("Key2"));
		assertEquals("Value3", toTest.getItem("Key3"));
		assertEquals(null, toTest.getItem("Key5"));
		assertEquals(null, toTest.getItem("Key6"));

	}
}
