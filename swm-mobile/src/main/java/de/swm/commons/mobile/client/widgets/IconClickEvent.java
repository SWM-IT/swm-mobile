package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import de.swm.commons.mobile.client.event.IconClickHandler;

public class IconClickEvent extends GwtEvent<IconClickHandler> {
	 
	
	
	  private static final Type<IconClickHandler> TYPE = new Type<IconClickHandler>();

	  private ClickEvent clickEvent;
	  
	  public IconClickEvent(ClickEvent clickEvent) {
		  this.clickEvent = clickEvent;
	  }
	  
	  public ClickEvent getClickEvent() {
		return clickEvent;
	}

	/**
	   * Gets the event type associated with click events.
	   * 
	   * @return the handler type
	   */
	  public static Type<IconClickHandler> getType() {
	    return TYPE;
	  }

	  @Override
	  public final Type<IconClickHandler> getAssociatedType() {
	    return TYPE;
	  }

	  
	  
	  @Override
	  protected void dispatch(IconClickHandler handler) {
	    handler.onIconClick(this);
	  }
}
