package de.swm.gwt.client.asyncjs;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.interfaces.IAssembler;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.progressbar.IProgressBarNoCancelWaitDialog;



/**
 * Wrapped einen synchronen Assembler in einen Asynchronen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 * @param <T>
 *            der typ des zu Wrappenden Assemblers.
 */
public abstract class AbstractAsyncAssembler<T extends IAssembler> implements IAssembler, IEventHandler {

	@Inject
	private IProgressBarNoCancelWaitDialog progressBarWaitDialog;

	@Inject
	private IDispatcher dispatcher;

	private final IModuleActivation forModule;

	private final AsyncProvider<T> asynchAssembler;

	private boolean isAssembledAndDownloaded = false;



	/**
	 * Default constructor.
	 * 
	 * @param forModule
	 *            derfiniert mit welchem Modul-Event die Assemblierung ansynchron angestossen werden soll.
	 * @param asynchAssembler
	 *            der zu wrappende Assembler, der nach dem Event asynchron (nachladen von JS) aufgerufen werden soll.
	 */
	public AbstractAsyncAssembler(IModuleActivation forModule, AsyncProvider<T> asynchAssembler) {
		this.forModule = forModule;
		this.asynchAssembler = asynchAssembler;
	}



	/**
	 * Erzeugt die <b>{appName}</b> Anwendung.
	 */
	public void assemble() {
		dispatcher.registerEventTypes(this.forModule, this);
	}



	@Override
	public void handleEvent(IEvent eventType, ILocation location, final ICustomData customData) {
		AsyncCallback<T> callback = new AsyncCallback<T>() {

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Kann nicht asynchron den Java-Scipt-Code fuer das Modul (" + forModule.eventName()
						+ ") nachladen, prüfen Sie bitte ihre Netzwerkverbindung");

			}



			@Override
			public void onSuccess(T assembler) {
				if (!isAssembledAndDownloaded) {
					assembler.assemble();
					isAssembledAndDownloaded = true;
				}
				if (customData.forwardEvent() != null) {
					customData.forwardEvent().execute();
				}

			}
		};
		asynchAssembler.get(progressBarWaitDialog.start(callback));
	}

}
