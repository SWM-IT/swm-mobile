package de.swm.gwt.client.generator.ui;


import com.google.gwt.user.client.ui.Composite;

/**
 * Steuerung Klassen fuer die UI-Templates der SWM Anwendungsgenerators >=4.0.
 * Diese Klassen werden als Meta-Informationen in der gwt ui.xml Vorlage verwendet
 * und rendern sich ohne siteneffekte so als ob sie nicht exitent sind.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class Snippet extends Composite {

	private String clazz;

	/**
	 * True wenn das Attribut provided ist.
	 */
	private boolean provided;


	/**
	 * * Map as <code>java.sql.Date</code>
	 * <p/>
	 * DATE,
	 * <p/>
	 * /**
	 * Map as <code>java.sql.Time</code>
	 * <p/>
	 * TIME,
	 * <p/>
	 * /**
	 * Map as <code>java.sql.Timestamp</code>
	 * <p/>
	 * TIMESTAMP
	 */
	private String dateFormat;
	private boolean generic;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public boolean isGeneric() {
		return generic;
	}

	public void setGeneric(boolean generic) {
		this.generic = generic;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}
}
