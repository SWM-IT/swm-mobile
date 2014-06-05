package de.swm.gwt.client.eventbus;

import de.swm.gwt.client.mobile.IPage;



/**
 * Definiert zusatzinformationen, die zu einem Event mitgesendet werden koennen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class MobileCustomData implements IMobileCustomData {

	private Object userData = null;
	private IPage originator;



	/**
	 * Default constructor.
	 */
	public MobileCustomData() {
		this(null, null);
	}



	/**
	 * Default constructor.
	 * 
	 * @param originatorPage
	 *            die Seite die dieses Event inititert hat.
	 */
	public MobileCustomData(IPage originatorPage) {
		this(originatorPage, null);
	}



	/**
	 * Default constructor.
	 * 
	 * @param originatorPage
	 *            die Seite die dieses Event inititert hat.
	 * @param userObject
	 *            das benutzerobjekt welches mit dem Event uebermittelt wird.
	 */
	public MobileCustomData(IPage originatorPage, Object userObject) {
		this.originator = originatorPage;
		this.userData = userObject;
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
	 * 
	 * @see de.swm.gwt.client.eventbus.IMobileCustomData#originatorPage()
	 */
	@Override
	public IPage originatorPage() {
		return this.originator;
	}



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



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String title() {
		return null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String subtitle() {
		return null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String footer() {
		return null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public IForwardEvent forwardEvent() {
		return null;
	}

}
