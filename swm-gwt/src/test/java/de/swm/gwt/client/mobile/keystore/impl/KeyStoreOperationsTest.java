package de.swm.gwt.client.mobile.keystore.impl;

import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.KeySet;
import de.swm.gwt.client.testhelper.SwmJsonWriterReader;
import de.swm.gwt.client.testhelper.SwmTestStorage;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Unit Test
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class KeyStoreOperationsTest {

	/**
	 * Json Reader for {@link TestDto}. @author wiese.daniel <br>
	 * copyright (C) 2012, SWM Services GmbH
	 */
	public interface TestDtoJsonReader extends JsonReader<TestDto> {
	}

	/**
	 * Json Writer for {@link TestDto}. @author wiese.daniel <br>
	 * copyright (C) 2012, SWM Services GmbH
	 */
	public interface TestDtoJsonWriter extends JsonWriter<TestDto> {
	}

	/**
	 * Json Reader for {@link KeySet}. @author wiese.daniel <br>
	 * copyright (C) 2012, SWM Services GmbH
	 */
	public interface KeySetJsonReader extends JsonReader<KeySet> {
	}

	/**
	 * JsonWriter for {@link KeySet}. @author wiese.daniel <br>
	 * copyright (C) 2012, SWM Services GmbH
	 */
	public interface KeySetJsonWriter extends JsonWriter<KeySet> {
	}

	private IStorage storage;
	private KeySetJsonReader keySetJsonReader;
	private KeySetJsonWriter keySetJsonWriter;
	private TestDtoJsonWriter testDtoJsonWriter;
	private TestDtoJsonReader testDtoJsonReader;

	@Before
	public void setUp() {
		storage = new SwmTestStorage();
		keySetJsonReader = SwmJsonWriterReader.createReader(KeySetJsonReader.class);
		keySetJsonWriter = SwmJsonWriterReader.createWriter(KeySetJsonWriter.class);
		testDtoJsonWriter = SwmJsonWriterReader.createWriter(TestDtoJsonWriter.class);
		testDtoJsonReader = SwmJsonWriterReader.createReader(TestDtoJsonReader.class);
	}


	@Test
	public void testGetDTOList_usingKeyset() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");

		ops.createList(list, keySet, testDtoJsonWriter);
		final List<TestDto> dtoListFromStore = ops.getDTOList(keySet, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));

	}

	@Test
	public void testGetDTOList_withoutKeyset() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");

		ops.createList(list, keySet, testDtoJsonWriter);
		final List<TestDto> dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));

	}

	@Test
	public void testRemoveDTOList() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		ops.createList(list, keySet, testDtoJsonWriter);
		assertEquals(4, storage.getLength());
		ops.removeDTOList(keySet);
		assertEquals(0, storage.getLength());
	}

	@Test
	public void testGetDTOListAndDeleteIt() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		ops.createList(list, keySet, testDtoJsonWriter);
		assertEquals(4, storage.getLength());
		final List<TestDto> dtoListFromStore = ops.getDTOListAndDeleteIt(keySet, testDtoJsonReader);
		//ist empty?
		assertEquals(0, storage.getLength());
		//is result ok?
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));

	}

	@Test
	public void testUpdateDTO() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		ops.createList(list, keySet, testDtoJsonWriter);
		assertEquals(4, storage.getLength());
		ops.updateDTO(new TestDto(2, "Test2X"), keySet, "2", testDtoJsonWriter);
		//ist size same?
		assertEquals(4, storage.getLength());

		//is result ok?
		final List<TestDto> dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));

		assertFalse(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2X")));

		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));


	}

	@Test
	public void testDeleteDTO() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		ops.createList(list, keySet, testDtoJsonWriter);
		assertEquals(4, storage.getLength());
		ops.deleteDTO(keySet, "2", testDtoJsonWriter);
		assertEquals(3, storage.getLength());

		//is result ok?
		final List<TestDto> dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(2, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertFalse(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));
	}

	@Test
	public void testUpdateOrCreateDTOList() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		//1x Schreiben , noch nicht existent
		ops.updateOrCreateDTOList(list, keySet, testDtoJsonWriter, Arrays.asList(1L, 2L, 3L));
		assertEquals(4, storage.getLength());

		//is result ok?
		List<TestDto> dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));

		//2x Schreiben , bereits existent
		list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2XX"));
		list.add(new TestDto(3, "Test3"));
		ops.updateOrCreateDTOList(list, keySet, testDtoJsonWriter, Arrays.asList(1L, 2L, 3L));
		assertEquals(4, storage.getLength());

		//is result ok?
		dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2XX")));
		assertFalse(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));


	}

	@Test
	public void testLoadKeysetFromStoreOrCreateEmptyOne() throws Exception {

		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		KeySet keySet = ops.loadKeysetFormStoreOrCreateEmptyOne(TestDto.class, "user", "UC_LSIT");
		assertEquals(0, keySet.getKeys().size());

		//elemente hinzufuegen
		keySet.createKey("Key1");
		keySet.createKey("Key2");
		keySet.createKey("Key3");

		storage.setItem(keySet.createKeyCollectionKey(), keySetJsonWriter.toJson(keySet));

		//noch einmal lesen
		keySet = ops.loadKeysetFormStoreOrCreateEmptyOne(TestDto.class, "user", "UC_LSIT");
		assertEquals(3, keySet.getKeys().size());
		assertTrue(keySet.getKeys().contains("user_de.swm.gwt.client.mobile.keystore.impl.TestDto_UC_LSIT_Key1"));
		assertTrue(keySet.getKeys().contains("user_de.swm.gwt.client.mobile.keystore.impl.TestDto_UC_LSIT_Key2"));
		assertTrue(keySet.getKeys().contains("user_de.swm.gwt.client.mobile.keystore.impl.TestDto_UC_LSIT_Key3"));
	}

	@Test
	public void testCreateList() throws Exception {
		KeyStoreOperations ops = new KeyStoreOperations(storage, keySetJsonReader, keySetJsonWriter);
		List<TestDto> list = new ArrayList<TestDto>();
		list.add(new TestDto(1, "Test1"));
		list.add(new TestDto(2, "Test2"));
		list.add(new TestDto(3, "Test3"));
		KeySet keySet = new KeySet(TestDto.class, "user", "UC_LIST");
		ops.createList(list, keySet, testDtoJsonWriter);
		assertEquals(4, storage.getLength());
		//is result ok?
		final List<TestDto> dtoListFromStore = ops.getDTOList("user", "UC_LIST", TestDto.class, testDtoJsonReader);
		assertEquals(3, dtoListFromStore.size());
		assertTrue(dtoListFromStore.contains(new TestDto(1, "Test1")));
		assertTrue(dtoListFromStore.contains(new TestDto(2, "Test2")));
		assertTrue(dtoListFromStore.contains(new TestDto(3, "Test3")));
	}
}
