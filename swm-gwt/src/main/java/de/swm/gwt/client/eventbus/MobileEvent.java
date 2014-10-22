package de.swm.gwt.client.eventbus;

import com.google.web.bindery.event.shared.binder.GenericEvent;
import de.swm.gwt.client.mobile.IPage;

/**
 * Beschreibung:.
 * 22.10.14, Time: 13:27.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2014, SWM Services GmbH
 */
public abstract class MobileEvent extends GenericEvent implements IEvent {

	private static final String MOBILE_GENERIC_EVENT = "MobileGenericEvent";

	private IPage originatorPage;

	@Override
	public String eventName() {
		return MOBILE_GENERIC_EVENT;
	}

	public IPage getOriginatorPage() {
		return originatorPage;
	}

	public void setOriginatorPage(IPage originatorPage) {
		this.originatorPage = originatorPage;
	}
}
