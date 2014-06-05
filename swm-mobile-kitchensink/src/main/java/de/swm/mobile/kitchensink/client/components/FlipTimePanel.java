package de.swm.mobile.kitchensink.client.components;

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

import de.swm.commons.mobile.client.widgets.HorizontalPanel;

public class FlipTimePanel extends HorizontalPanel {

	class FlipNumber extends FlowPanel {
				
		public FlipNumber(String text) {
			getElement().addClassName("flip-number");
			Element cover = DOM.createDiv();
			cover.addClassName("flip-number-cover");
			DOM.appendChild(getElement(), cover);
			Element content = DOM.createElement("p");
			content.addClassName("flip-number-content");
			content.setInnerText(text);
			DOM.appendChild(getElement(), content);
		}
		
	}
	
	private NumberFormat nf = NumberFormat.getFormat("00");

	@SuppressWarnings("deprecation")
	public FlipTimePanel(Date d) {
		FlipNumber hours = new FlipNumber(nf.format(d.getHours()));
		hours.addStyleName("flip-hours");
		add(hours);
		FlipNumber colon = new FlipNumber(":");
		colon.getElement().getStyle().setWidth(0.9, Unit.EM);
		add(colon);
		FlipNumber minutes = new FlipNumber(nf.format(d.getMinutes()));
		minutes.addStyleName("flip-minutes");
		add(minutes);
	}

}
