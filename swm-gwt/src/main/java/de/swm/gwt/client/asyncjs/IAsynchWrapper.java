package de.swm.gwt.client.asyncjs;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.inject.client.AsyncProvider;

import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.eventbus.IMobileCustomData;
import de.swm.gwt.client.mobile.IPage;



/**
 * Hilfklasse um Navigations-Menue Action so umzuformen, dass diese erst nach einem "Click" den zugehoerigen JS-Code
 * downloaden.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IAsynchWrapper {

	/**
	 * Warpped eine Action so dass diese Asynchron - min Nachladen von JS Code ausgeführt werden kann.
	 * 
	 * @param <T>
	 *            der Type der zu warappenden Action.
	 * @param asycAction
	 *            die Asynchrone Action.
	 * @param module
	 *            das Modul, welches vor dem Ausfuehren aktiviert werden soll.
	 * @return die Action als Click handler
	 */
	<T extends ClickHandler> ClickHandler wrap(final AsyncProvider<T> asycAction, final IModuleActivation module);



	/**
	 * Laedt das Modul und feuert anschliessend das uebergebene Event (als Mobiles Event).
	 * 
	 * @param module .
	 * @param toFire .
	 * @param originator .
	 */
	void loadModuleAndFireMobileEvent(final IModuleActivation module, final IEvent toFire, final IPage originator);


	/**
	 * Laedt das Modul und feuert anschliessend das uebergebene Event (als Mobiles Event).
	 * @param module .
	 * @param toFire .
	 * @param originator .
	 * @param customData .
	 */
	void loadModuleAndFireMobileEvent(final IModuleActivation module, final IEvent toFire, final IPage originator,
		final IMobileCustomData customData);



	/**
	 * Warpped ein Event des Event-Busses so, dass dieses vorher Asynchron den JS Code nachlaedt und aktiviert bevor das
	 * eigentliche event ausgefuehrt wird.
	 * 
	 * @param <T>
	 *            der Type der zu wrappenden Action.
	 * @param asycAction
	 *            die Asynchrone Action.
	 * @param module
	 *            das Modul, welches vor dem Ausfuehren aktiviert werden soll.
	 * @return die Action als Click handler
	 */
	<T extends IEventHandler> IEventHandler wrapEvent(final AsyncProvider<T> asycAction, final IModuleActivation module);

}