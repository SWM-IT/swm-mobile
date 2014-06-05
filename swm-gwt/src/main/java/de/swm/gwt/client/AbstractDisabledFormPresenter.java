package de.swm.gwt.client;

import com.google.gwt.user.client.Window;
import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.exception.MessagingUncaughtExceptionHandler;
import de.swm.gwt.client.interfaces.ILocation;


/**
 * Abstrahierung des Form Presenters.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-13, SWM Services GmbH
 *
 * @param <T>
 *            der Typ des DTO's
 *  @param <Y> der Typ des Formulars
 */
public abstract class AbstractDisabledFormPresenter<T, Y extends IDisabledForm<T>>
		implements IDisabledFormPresenter<T> {

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
	public AbstractDisabledFormPresenter(ILocation contentPanel, Y formToControl) {
		this.contentPanel = contentPanel;
		this.formToControl = formToControl;
		formToControl.setPresenter(this);
	}



	/**
	 * Liefert das Formular, das der Presenter steuert.
	 * @return das Formular
	 */
	protected Y getForm() {
		return formToControl;
	}

	/**
	 * Erzeugt einen event handler der ein show event behandelt - der konkrte Present muss
	 * dann die Methode <code>handleShow</code> ueberschreiben.
	 * @return der event handler
	 */
	protected IEventHandler createShowEventHandler() {
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
	 * @return der event handler
	 */
	protected IEventHandler createHideEventHandler() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleHide(eventType, location, customData);
			}

		};
	}

	/**
	 * Erzeugt einen event handler der ein model update event behandelt - der konkrte Present muss
	 * dann die Methode <code>handleModelUpdate</code> ueberschreiben.
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
	 * Erzeugt einen event handler der automatisch (mit relead der anwendung) einen fehgeschllagenen
	 * server call behanlelt. Eine use-case spezifische Fahlerbehandlung kann in  <code>handleServerCallFailed</code>
	 * festgelegt werden.
	 * @return der event handler
	 */
	protected IEventHandler createServerCallFailedHandler() {
		return new IEventHandler() {
			@Override
			public void handleEvent(IEvent eventType, ILocation location, ICustomData customData) {
				handleServerCallFailed(eventType, location, customData);
			}
		};
	}

	/**
	 * Wird bei einem on Show-Event aufgerufen.
	 * @param eventType der Event-Typ
	 * @param location die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleShow(IEvent eventType, ILocation location, ICustomData customData) {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}

	/**
	 * Wird bei einem on Hide-Event aufgerufen.
	 * @param eventType der Event-Typ
	 * @param location die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleHide(IEvent eventType, ILocation location, ICustomData customData) {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}

	/**
	 * Wird bei einem on Model-Update-Event aufgerufen.
	 * @param eventType der Event-Typ
	 * @param location die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleModelUpdate(IEvent eventType, ILocation location, ICustomData customData) {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}


	/**
	 * Wird bei einem on Fehler aufgerufen.
	 * @param eventType der Event-Typ
	 * @param location die ziel Location zum Rendern
	 * @param customData use case spezifischen Daten
	 */
	protected void handleServerCallFailed(IEvent eventType, ILocation location, ICustomData customData) {
		new MessagingUncaughtExceptionHandler(true).onUncaughtException(new RuntimeException("Beim Zugriff auf " +
				"den Server ist eine Exception aufgetreten"));
	}

	@Override
	public void handleCancel() {
		//Leere Implementierung, damit nicht jeder Presenter die Methode implementieren muss.
	}


	@Override
	public void displayDTO(T toDisplay) {
		getForm().displayDTO(toDisplay);
	}



	@Override
	public T getDisplayedDTO() {
		return getForm().getDisplayedDTO();
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
