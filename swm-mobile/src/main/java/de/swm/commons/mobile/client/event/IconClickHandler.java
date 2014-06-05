package de.swm.commons.mobile.client.event;

import com.google.gwt.event.shared.EventHandler;
import de.swm.commons.mobile.client.widgets.IconClickEvent;

public interface IconClickHandler extends EventHandler {
	  void onIconClick(IconClickEvent event);
}
