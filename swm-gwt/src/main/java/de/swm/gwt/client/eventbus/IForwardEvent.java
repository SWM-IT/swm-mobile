package de.swm.gwt.client.eventbus;

/**
 * Wrapper interface um eine nachfolgende folge von Actions an ein formular /liste zu senden. Diese koennen dann vom
 * jeweiligen Presenter zu einem gegebnen Zeitpunkt ausgefuehrt werden.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public interface IForwardEvent {

	/**
	 * Fuehrt das forward event aus.
	 */
	void execute();

}
