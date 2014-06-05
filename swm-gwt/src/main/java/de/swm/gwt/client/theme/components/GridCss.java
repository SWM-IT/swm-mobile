package de.swm.gwt.client.theme.components;

import com.google.gwt.resources.client.CssResource;

/**
 * Styling for data grids (tables).
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public interface GridCss extends CssResource {

	/**
	 * Liefert den den verschleierten (obfuscated) CSS-Klassen-Namen fuer die CSS-Klasse '.defaultDataGrid'.
	 * Kann auch mit @ClassName("defaultDataGrid") explizit gemacht werden, z.B.
	 * wenn es ueberschneidungen mit @def-Werten gibt.
	 *
	 * @return der obfucscated CSS Klassenname.
	 */
	@ClassName("defaultDataGrid")
	String defaultDataGrid();

	/**
	 * CSS-Klasse.
	 *
	 * @return .
	 */
	@ClassName("defaultSimplePager")
	String defaultSimplePager();

}
