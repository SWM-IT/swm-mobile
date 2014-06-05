package de.swm.gwt.client.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.logging.client.TextLogFormatter;
import com.google.gwt.storage.client.Storage;

/**
 * GWT logging handler that writes log messages to local storage.
 * Useful for devices where no Javascript console is available and remote logging
 * is impossible / has side effects.
 * 
 * @author wimmel.guido
 *         copyright (C) 2012, SWM Services GmbH
 *
 */
public class LocalStorageLogHandler extends Handler {

	private static final String LASTLOGMSG_KEY = "localStorageLog.lastMessage";
	private static final String ALLLOGMSG_KEY = "localStorageLog.allMessages";
	
	private final Storage localStorage;
	
	public LocalStorageLogHandler() {
		setFormatter(new TextLogFormatter(true));
		setLevel(Level.ALL);
		localStorage = Storage.getLocalStorageIfSupported();
	}

	@Override
	public void publish(LogRecord record) {
		if (!isSupported() || !isLoggable(record)) {
			return;
		}
		String msg = getFormatter().format(record);
		log(msg);
	}



	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}
	
	private boolean isSupported() {
		return localStorage != null;
	}
	
	private void log(String msg) {
		localStorage.setItem(LASTLOGMSG_KEY, msg);
		String previousLogMessages = localStorage.getItem(ALLLOGMSG_KEY);
		if (previousLogMessages == null) {
			previousLogMessages = "";
		}
		localStorage.setItem(ALLLOGMSG_KEY, previousLogMessages + msg + "\n");
	}
	
}
