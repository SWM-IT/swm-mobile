package de.swm.gwt.client.asyncjs;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.eventbus.IForwardEvent;
import de.swm.gwt.client.eventbus.IMobileCustomData;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;
import de.swm.gwt.client.progressbar.IProgressBarNoCancelWaitDialog;



/**
 * Hilfklasse um Navigations-Menue Action so umzuformen, dass diese erst nach einem "Click" den zugehoerigen JS-Code
 * downloaden.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class AsyncWrapper implements IAsynchWrapper {

	private final IProgressBarNoCancelWaitDialog progressNoCancelWaitDialog;
	private final IDispatcher dispatcher;



	/**
	 * Default constructor.
	 * 
	 * @param progressNoCancelWaitDialog
	 *            die Progress bar
	 * @param dispatcher
	 *            der dispatcher.
	 */
	@Inject
	public AsyncWrapper(IProgressBarNoCancelWaitDialog progressNoCancelWaitDialog, IDispatcher dispatcher) {
		this.progressNoCancelWaitDialog = progressNoCancelWaitDialog;
		this.dispatcher = dispatcher;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.issng.gkpk.client.asyncjs.IAsynchWrapper#wrap(AsyncProvider, ModuleActivation)
	 */
	@Override
	public <T extends ClickHandler> ClickHandler wrap(final AsyncProvider<T> asycAction, final IModuleActivation module) {
		final IForwardEventWithClickEvent forward = wrapTargetActionToForwardEvent(asycAction);
		return forwardToClickHandler(module, forward);
	}


	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void loadModuleAndFireMobileEvent(final IModuleActivation module, final IEvent toFire, final IPage originator) {

		final IForwardEvent forwardEventWrapper = new IForwardEvent() {

			@Override
			public void execute() {
				dispatcher.fireMobileEvent(toFire, originator);

			}
		};
		dispatcher.fireEvent(module, forwardEventWrapper);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void loadModuleAndFireMobileEvent(final IModuleActivation module, final IEvent toFire, final IPage originator, final IMobileCustomData customData) {

		final IForwardEvent forwardEventWrapper = new IForwardEvent() {

			@Override
			public void execute() {
				dispatcher.fireMobileEvent(toFire, originator, customData);

			}
		};
		dispatcher.fireEvent(module, forwardEventWrapper);
	}



	/**
	 * FIXME: Wird diese Methode noch benoetigt? {@inheritDoc}
	 */
	@Override
	public <T extends IEventHandler> IEventHandler wrapEvent(final AsyncProvider<T> asyncAction,
		final IModuleActivation module) {
		return new IEventHandler() {
			// wenn das synchrone event ausgelost wird, startet die kette
			@Override
			public void handleEvent(final IEvent eventType, final ILocation location, final ICustomData customData) {
				// wird ausgeloest wenn das Modul geladen ist.
				final IForwardEvent forward = new IForwardEvent() {

					@Override
					public void execute() {
						// 1.) Modul ist geladen > Event Handler laden
						asyncAction.get(progressNoCancelWaitDialog.start(new AsyncCallback<T>() {

							@Override
							public void onSuccess(T action) {
								// 2.) nach dem erfolgreichen laden des Ziel Event Handlers, des event weiterleiten
								action.handleEvent(eventType, location, customData);
							}



							@Override
							public void onFailure(Throwable error) {
								Window.alert("Kann Java-Script-Code nicht downloaden, "
										+ "bitte wenden sie sich an die Hotline");
							}
						}));
					}

				};
				dispatcher.fireEvent(module, forward);

			}

		};
	}



	/**
	 * Wrapped die Action in einen Formward event - diese wird ausgefuehrt wenn der module-js-code vorliegt und
	 * aktiviert wurde.
	 * 
	 * @param <T>
	 *            der typ des events
	 * @param asycAction
	 *            die asynchrone action
	 * @return der gwarappte click handler
	 */
	private <T extends ClickHandler> IForwardEventWithClickEvent wrapTargetActionToForwardEvent(
		final AsyncProvider<T> asycAction) {
		return new IForwardEventWithClickEvent() {

			@Override
			public void execute(final ClickEvent clickEvent) {
				asycAction.get(progressNoCancelWaitDialog.start(new AsyncCallback<T>() {

					@Override
					public void onSuccess(T action) {
						action.onClick(clickEvent);

					}



					@Override
					public void onFailure(Throwable error) {
						Window.alert("Kann Java-Script-Code nicht downloaden, bitte wenden sie sich an die Hotline");
					}
				}));

			}
		};
	}



	/**
	 * Erzeugt einen click handler, der bein clicken einen Event auf den Event-Bus sendet, der dann Asnychron den
	 * JS-Code des Moduls laedt.
	 * 
	 * @param module
	 *            das zu aktivierene Moduel (event)
	 * @param forwardEwent
	 *            der forward event der nach der aktivierung ausgefuehrt werden soll.
	 * @return des ganze als clik handler.s
	 */
	private ClickHandler forwardToClickHandler(final IModuleActivation module,
		final IForwardEventWithClickEvent forwardEwent) {
		return new ClickHandler() {

			@Override
			public void onClick(final ClickEvent click) {
				final IForwardEvent forwardEventWrapper = new IForwardEvent() {

					@Override
					public void execute() {
						forwardEwent.execute(click);

					}
				};
				dispatcher.fireEvent(module, forwardEventWrapper);
			}
		};
	}

}
