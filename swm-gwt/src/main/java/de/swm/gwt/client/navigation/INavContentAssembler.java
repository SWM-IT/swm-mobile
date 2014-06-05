package de.swm.gwt.client.navigation;

/**
 * Setzt den Teil des Navigationsmenues fuer dieses Modul zusammen.
 * @author Wiese.Daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public interface INavContentAssembler {
	
	/**
	 * Assembler fuer das Navigationsmenue.
	 * @param navMenuePresenter der Presenter fuer das Navigations-Meneue 
	 */
	void assemble(INavigationPresenter navMenuePresenter);

}
