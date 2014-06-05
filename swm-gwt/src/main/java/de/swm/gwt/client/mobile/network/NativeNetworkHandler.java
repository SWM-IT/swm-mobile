package de.swm.gwt.client.mobile.network;

/**
 * Will detect network availability using a native implementation instead of polling.
 *
 */
public interface NativeNetworkHandler {

	/**
	 * Trie is Network is available.
	 * @return true if network such as 3g is present
	 */
	boolean hasNetwork();
}
