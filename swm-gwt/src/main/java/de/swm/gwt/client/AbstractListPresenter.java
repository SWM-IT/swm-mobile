package de.swm.gwt.client;

import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.exception.MessagingUncaughtExceptionHandler;
import de.swm.gwt.client.interfaces.ILocation;


/**
 * Abstrahierung des List Presenters.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-13, SWM Services GmbH
 *
 * @param <T>
 *            der Typ des DTO's
 *  @param <Y> der Typ des Formulars
 */
public abstract class AbstractListPresenter<T, Y extends IList<T>> implements IListPresenter<T> {

	private final ILocation contentPanel;
	private ILocation lastRenderInLocation;

	private Y formToControl;



	/**
	 *
	 * Default constructor.
	 *
	 * @param contentPanel
	 *            das Contentpanel
	 * @param formToControl das zu steuernde Formular
	 */
	public AbstractListPresenter(ILocation contentPanel, Y formToControl) {
		this.contentPanel = contentPanel;
		this.formToControl = formToControl;
		formToControl.setPresenter(this);
	}

	/**
	 * Erzeugt einen event handler der ein show event behandelt - der konkrte Present muss
	 * dann die Methode <code>handleShow</code> ueberschreiben.
	 *
	 * @return der event handler
	 */
	protected IEventHandler createShowHandler() {
		return new IEventHandler() {

			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleShow(eventType, location, customData);
			}
		};
	}

	/**
	 * Erzeugt einen event handler der ein hide event behandelt - der konkrte Present muss
	 * dann die Methode <code>handleHide</code> ueberschreiben.
	 *
	 * @return der event handler
	 */
	protected IEventHandler createHideHandler() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleHide(eventType, location, customData);
			}

		};
	}

	/**
	 * Erzeugt einen event handler der ein update event behandelt - der konkrte Present muss
	 * dann die Methode <code>handleModelUpdate</code> ueberschreiben.
	 *
	 * @return der event handler
	 */
	protected IEventHandler createModelUpdateHandler() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleModelUpdate(eventType, location, customData);
			}
		};
	}

	/**
	 * Erzeugt einen event handler der ein hide event behandelt - der konkrete Present muss
	 * dann die Methode <code>handleHide</code> ueberschreiben.
	 *
	 * @return der event handler
	 */
	protected IEventHandler createModelDeletedHandler() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleModelDelete(eventType, location, customData);
			}
		};
	}

	/**
	 * Erzeugt einen event handler der ein fehler event behandelt - der konkrete Present kann
	 * dann die Methode <code>handleServerCallFailed</code> ueberschreiben.
	 *
	 * @return der event handler
	 */
	protected IEventHandler createServerCallFailedHandler(){
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleServerCallFailed(eventType, location, customData);
			}
		};
	}

	/**
	 * Wird bei einem on Fehler aufgerufen.
	 *
	 * @param eventType  der Event-Typ
	 * @param location   die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleServerCallFailed(IEvent eventType, ILocation location, ICustomData customData) {
		new MessagingUncaughtExceptionHandler(true).onUncaughtException(new RuntimeException("Beim Zugriff auf " +
				"den Server ist eine Exception aufgetreten"));
	}

	/**
	 * Wird bei einem on Show-Event aufgerufen (bereits implementiert) - kann bei bedarf ueberschrieben werden.
	 *
	 * @param eventType  der Event-Typ
	 * @param location   die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleShow(IEvent eventType, ILocation location, ICustomData customData) {
		getForm().render(getCurrentLocation(location));

	}
	/**
	 * Wird bei einem on Hide-Event aufgerufen (bereits implementiert) - kann bei bedarf ueberschrieben werden.
	 *
	 * @param eventType  der Event-Typ
	 * @param location   die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleHide(IEvent eventType, ILocation location, ICustomData customData) {
		getForm().remove(getCurrentLocation(location));
	}



	protected void handleModelUpdate(IEvent eventType, ILocation location, ICustomData customData) {
		getForm().onLoad();
		if (customData != null && customData.userObject() != null) {
			getForm().selectRow((T) customData.userObject());
		}
	}

	/**
	 * Wird bei einem on Delete-Event aufgerufen (bereits implementiert) - kann bei bedarf ueberschrieben werden.
	 *
	 * @param eventType  der Event-Typ
	 * @param location   die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleModelDelete(IEvent eventType, ILocation location, ICustomData customData) {
		getForm().onLoad();
	}



	/**
	 * Liefert das Formular, das der Presenter steuert.
	 * @return das Formular
	 */
	protected Y getForm() {
		return formToControl;
	}



	/**
	 * {@inheritDoc}
	 * Hook-Methode, die ggf. ueberschrieben werden kann.
	 */
	@Override
	public void onAddClicked() {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}



	/**
	 * {@inheritDoc}
	 * Hook-Methode, die ggf. ueberschrieben werden kann.
	 */
	@Override
	public void onRowSelected(T dto) {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}



	/**
	 * Liefert den Content Pannel.
	 *
	 * @return den Content pannel.
	 */
	public ILocation getContentPanel() {
		return contentPanel;
	}



	/**
	 * Setzt die letzte Location, in die dieses Formular hineingerendert wurde.
	 *
	 * @param lastRenderInLocation
	 *            die letzte location in die dieses Formular hineingerendert wurde.
	 */
	public void setLastRenderInLocation(ILocation lastRenderInLocation) {
		this.lastRenderInLocation = lastRenderInLocation;
	}



	/**
	 * Liefert die richtige derzeitige location des zugehoerigen Views.
	 *
	 * @param custom
	 *            null oder aktuelle alternative location
	 * @return die location
	 */
	protected ILocation getCurrentLocation(ILocation custom) {
		if (custom != null) {
			return custom;
		}
		if (lastRenderInLocation != null) {
			return lastRenderInLocation;
		}
		return this.contentPanel;
	}


}
