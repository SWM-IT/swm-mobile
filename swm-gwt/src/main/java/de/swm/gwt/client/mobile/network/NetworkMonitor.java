package de.swm.gwt.client.mobile.network;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.mobile.network.rpcservice.NetworkServiceAsync;

import java.util.logging.Logger;


/**
 * Ueberwacht die Netzwerkverbindung und sendet Events wenn die Netzwerkverbindung ausfaellt.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class NetworkMonitor implements INetworkMonitor {

	private static final int DEFAULT_NETWORK_CHECK_INTERVALL_MS = 30 * 1000;
	private static final int DEFAULT_NETWORK_CHECK_DELAY_MS = 50;
	public static final int MIN_TIMEOUT_MS = 2000;

	private static final Logger LOGGER = Logger.getLogger(NetworkMonitor.class.getName());


	/**
	 * Defines the first check intervall after the service was started. *
	 */
	private int network_check_intervall_network_available_ms;
	/**
	 * Defines the first check intervall after the service was started. *
	 */
	private int network_check_intervall_network_unavailable_ms;
	/**
	 * Defines the timeout. *
	 */
	private int timeoutIntervallMs;

	/**
	 * Defines the first check intervall after the service was started. *
	 */
	private int network_check_delay_ms;

	@Inject
	private IDispatcher dispatcher;

	@Inject
	private IVersionControlMonitor versionControlMonitor;

	private final NetworkServiceAsync networkService;

	private boolean networkAvailable = true;

	private Timer timer;

	private boolean isStarted = false;
	private NativeNetworkHandler nativeNetworkHandler;


	/**
	 * Guice constructor.
	 *
	 * @param networkService netwerk service endpoint
	 */

	@Inject
	public NetworkMonitor(NetworkServiceAsync networkService) {
		this.networkService = networkService;
		//define default check interval > equal for available / unavaiable
		setNetworkCheckInterval(DEFAULT_NETWORK_CHECK_INTERVALL_MS);
		setNetworkCheckIntervalWhenNotAvailable(DEFAULT_NETWORK_CHECK_INTERVALL_MS);
		//define default first initial delay (after the service was started)
		setNetworkCheckDelay(DEFAULT_NETWORK_CHECK_DELAY_MS);
		calculateTimeout();
	}

	@Override
	public void setNetworkCheckInterval(int pollingFrequenceInMs) {
		this.network_check_intervall_network_available_ms = pollingFrequenceInMs;
	}


	@Override
	public void setNetworkCheckIntervalWhenNotAvailable(int networkMonCheckIntervalUnavailableInMs) {
		this.network_check_intervall_network_unavailable_ms = networkMonCheckIntervalUnavailableInMs;
	}


	@Override
	public void setNetworkCheckDelay(int delayInMs) {
		this.network_check_delay_ms = delayInMs;
	}


	@Override
	public void setNativeHandler(NativeNetworkHandler nativeNetworkHandler) {
		this.nativeNetworkHandler = nativeNetworkHandler;
	}


	/**
	 * Liefert den aktuellen Network monitoring service.
	 *
	 * @return den aktuellen Network monitoring service.
	 */
	@Override
	public NetworkServiceAsync getServiceEndpoint() {
		return this.networkService;
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see INetworkMonitor#startService()
	 */
	@Override
	public void startService() {
		startService(NetworkEvents.NETWORK_AVAILABLE);
	}

	/**
	 * Starts the service with an default state.
	 *
	 * @param defaultState default state
	 */
	@Override
	public void startService(NetworkEvents defaultState) {
		if (!isStarted) {
			isStarted = true;

			// first network request
			new NetworkCheckTimer().schedule(network_check_delay_ms);

			// repeating network requests
			timer = new NetworkCheckTimer();

			int initialRepeatInterval = (defaultState.equals(NetworkEvents.NETWORK_AVAILABLE)) ?
					network_check_intervall_network_available_ms : network_check_intervall_network_unavailable_ms;

			timer.scheduleRepeating(initialRepeatInterval);


			// assume the default state
			if (defaultState.equals(NetworkEvents.NETWORK_AVAILABLE)) {
				networkAvailable = true;
			} else {
				networkAvailable = false;
			}
			dispatcher.fireMobileEvent(defaultState);
		}
	}


	/**
	 * Sendet einen mit einem Timer befirsteten Request zum Server.
	 */
	public void sendNetworkRequest() {
		final AsyncCallback<String> networkCheckResult = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				if (networkAvailable) {
					networkAvailable = false;
					LOGGER.info("Sending Network-Unavailable Event");

					if (timer != null) {
						timer.cancel();
					}
					timer = new NetworkCheckTimer();
					timer.scheduleRepeating(network_check_intervall_network_unavailable_ms);
					dispatcher.fireMobileEvent(NetworkEvents.NETWORK_UNAVAILABLE);
				}

			}


			@Override
			public void onSuccess(String result) {
				if (result != null && !result.equalsIgnoreCase("OK")) {
					// send timestamp to version monitor
					versionControlMonitor.publishServerSideVersion(result);
				}
				if (!networkAvailable) {
					networkAvailable = true;
					LOGGER.info("Sending Network-Available Event");

					if (timer != null) {
						timer.cancel();
					}
					timer = new NetworkCheckTimer();
					timer.scheduleRepeating(network_check_intervall_network_available_ms);
					dispatcher.fireMobileEvent(NetworkEvents.NETWORK_AVAILABLE);
				}

			}
		};
		if (isStarted) {
			if (this.nativeNetworkHandler != null && this.nativeNetworkHandler.hasNetwork()) {
				networkService.checkNetwork(new AsyncCallbackWithTimeout<String>(networkCheckResult, timeoutIntervallMs));
			} else if (this.nativeNetworkHandler == null) {
				networkService.checkNetwork(new AsyncCallbackWithTimeout<String>(networkCheckResult, timeoutIntervallMs));
			} else {
				networkCheckResult.onFailure(null);
			}
		}
	}


	@Override
	public void stopService() {
		if (isStarted) {
			isStarted = false;
			if (timer != null) {
				timer.cancel();
			}
			//Annahme: Fwenn der service gestoppt wird ist das netz nicht verfugbar
			dispatcher.fireMobileEvent(NetworkEvents.NETWORK_UNAVAILABLE);
		}

	}

	private void calculateTimeout() {
		this.timeoutIntervallMs = Math.max((Math.min(network_check_intervall_network_available_ms, network_check_intervall_network_unavailable_ms) / 2), MIN_TIMEOUT_MS);
	}

	private class NetworkCheckTimer extends Timer {
		@Override
		public void run() {
			sendNetworkRequest();
		}
	}

}
