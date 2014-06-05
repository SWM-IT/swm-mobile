package de.swm.gwt.storage;

import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.*;

/**
 * SQL storage wrapper for {@see BigLocalStoage}.
 * @author Daniel Wiese
 */
@Connection(name = "SWMLocalStorage", version = "1.0",
		description = "LocalStroge for big Data", maxsize = SQLStorageLayer.SIZE_IN_MB * 1024 * 1024)
public interface SQLStorageLayer extends DataService {

	/**
	 * Defines the size of current local storage (Database size)
	 */
	int SIZE_IN_MB = 25;

	/**
	 * Makes sure that the 'swmstorage' table exists in the Database.
	 */
	@Update("CREATE TABLE IF NOT EXISTS swmstorage ("
			+ "id STRING NOT NULL PRIMARY KEY, "
			+ "value STRING)")
	void initTable(VoidCallback callback);

	/**
	 * Inserts a value or uptates the value.
	 */
	@Update("INSERT INTO swmstorage (id, value) VALUES ({key}, {value})")
	void insertValue(String key, String value, VoidCallback callback);

	/**
	 * Updates a value.
	 */
	@Update("UPDATE swmstorage SET value = {value} WHERE id = {key}")
	void updateValue(String key, String value, VoidCallback callback);

	/**
	 * Delete a Value.
	 */
	@Update("DELETE FROM swmstorage WHERE id = {key}")
	void deleteKeyAndValue(String key, VoidCallback callback);

	/**
	 * Delete a Value.
	 */
	@Update("DELETE FROM swmstorage")
	void deleteAll(VoidCallback callback);

	/**
	 * Returns a value.
	 */
	@Select("SELECT * FROM swmstorage")
	void getAllValues(ListCallback<GenericRow> callback);

	/**
	 * Obtains the number elements in the database.
	 */
	@Select("SELECT count(*) FROM swmstorage")
	void getCount(ScalarCallback<Integer> callback);

	/**
	 * Obtains the version number of the SQLite database.
	 */
	@Select("SELECT sqlite_version()")
	void getSqliteVersion(ScalarCallback<String> callback);
}


