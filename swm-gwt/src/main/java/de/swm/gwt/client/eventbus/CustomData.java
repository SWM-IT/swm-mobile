package de.swm.gwt.client.eventbus;




/**
 * Definiert adaptierbare Daten fuer Listen / Formulare.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public class CustomData implements ICustomData {

	private static final String EMPTY = "";
	private final String title;
	private final String subtitle;
	private final String footer;
	private Object userData = null;
	private IForwardEvent forwardEvent = null;



	/**
	 * Default constructor.
	 */
	public CustomData() {
		this(null, null, null);
	}



	/**
	 * Default constructor.
	 * 
	 * @param userObject
	 *            das benutzerobjekt welches mit dem Event uebermittelt wird.
	 */
	public CustomData(Object userObject) {
		this(null, null, null);
		userData = userObject;
	}



	/**
	 * Default constructor.
	 * 
	 * @param title
	 *            der titel
	 * @param subtitle
	 *            der untertitel
	 * @param footer
	 *            der footer
	 */
	public CustomData(String title, String subtitle, String footer) {
		this.title = title;
		this.subtitle = subtitle;
		this.footer = footer;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String title() {
		return (this.title == null) ? EMPTY : this.title;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String subtitle() {
		return (this.subtitle == null) ? EMPTY : this.subtitle;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String footer() {
		return (this.footer == null) ? EMPTY : this.footer;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object userObject() {
		return this.userData;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserObject(Object toSet) {
		this.userData = toSet;

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public IForwardEvent forwardEvent() {
		return forwardEvent;
	}



	/**
	 * Setzt ein forward event, wenn nach dem abarbeiten ein weiteres Event gefeuert werden soll.
	 * 
	 * @param forwardEvent
	 *            das als naechstes zus feuernde event.
	 */
	public void setForwardEvent(IForwardEvent forwardEvent) {
		this.forwardEvent = forwardEvent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T nullAndTypeSaveUserObject(Class<T> forType) {
		//isAssingableFrom not in JRE Emulation Whitelist of GWT
		if (userData != null
				&& (userData.getClass().equals(forType) || userData.getClass().getSuperclass().equals(forType))) {
			return (T) userObject();
		}
		
		return null;
	}


}
