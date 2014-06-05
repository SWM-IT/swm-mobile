package de.swm.gwt.client.navigation;

import com.google.gwt.event.dom.client.ClickHandler;



/**
 * Der Navigation Content einer Anwendung.
 * 
 * @author Wiese.Daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface INavigationContent {

	/**
	 * Der click handler > wird ausgefuehrt wenn auf den Teil des Navigationsmenues gedrueckt wird.
	 * 
	 * @return der Click Handler
	 */
	ClickHandler getHandler();



	/**
	 * Den Text der im Menu angezeigt wird.
	 * 
	 * @return den Text.
	 */
	String getLinkText();



	/**
	 * Definiert den Bereich in dem der Content eingefuegt werden soll.
	 * 
	 * @return der Navigationsbereich
	 */
	INavigationArea getTargetArea();



	/**
	 * Definiert eine numerische Priritaet dar, in der ein Menuepunkt geordnet werden kann. 1=Hoeschste Proiritaet, eine
	 * grosse Zahl stellt eine sehr keline Prioritaet dar.
	 * 
	 * @return die prioritaet
	 */
	int priority();
	
	/**
	 * Setzt die Prioritaet (Reihenfolge) im Menue.
	 * @param priority - die prioritaet
	 */
	void setPriority(int priority);
	
	/**
	 * Liefert die Liste der erlaubten Rollen.
	 * @return die liste der erlaubten rollen
	 */
	String[] allowedRoles();

}
