package de.swm.mobile.kitchensink.client.components;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import de.swm.mobile.kitchensink.client.icon.IconResources;



/**
 * ListItem wich represents a specific connection.
 * 
 * @author vilbig.alexander <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class ConnectionListItem extends ListItem {

	public enum TransportMeans {
		WALK, BUS, TRAM, UBAHN, SBAHN
	}

	private Label durationLabel;
	private Label transferLabel;
	private IconResources icons = GWT.create(IconResources.class);



	public @UiConstructor
	ConnectionListItem(Date fromDate, Date toDate, int numTransfer, TransportMeans[] transfer) {
		HorizontalPanel panel = new HorizontalPanel();
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss().decoratedListItemVPanel());

		HorizontalPanel dPanel = new HorizontalPanel();
		dPanel.add(new FlipTimePanel(fromDate));
		HTML dash = new HTML("-");
		dash.getElement().getStyle().setPaddingLeft(0.6, Unit.EM);
		dash.getElement().getStyle().setPaddingRight(0.6, Unit.EM);
		dPanel.add(dash);
		dPanel.add(new FlipTimePanel(toDate));
		vPanel.add(dPanel);

		durationLabel = new Label(formatDuration(fromDate, toDate));
		durationLabel.addStyleName("connection-list-item-text");
		durationLabel.getElement().getStyle().setPaddingTop(0.6, Unit.EM);
		vPanel.add(durationLabel);

		HorizontalPanel tPanel = new HorizontalPanel();
		transferLabel = new Label("Umsteigen: " + numTransfer);
		transferLabel.addStyleName("connection-list-item-text");
		transferLabel.getElement().getStyle().setPaddingRight(0.6, Unit.EM);
		tPanel.add(transferLabel);
		buildTransfer(tPanel, transfer);
		tPanel.getElement().getStyle().setPaddingTop(0.4, Unit.EM);
		vPanel.add(tPanel);
		panel.add(vPanel);
		add(panel);
	}



	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		if (disabled) { // add cover
			setShowArrow(false);
			getElement().getStyle().setPosition(Position.RELATIVE);
			Element cover = DOM.createDiv();
			cover.setClassName("connection-list-item-cover");
			DOM.appendChild(getElement(), cover);
		} else { // remove cover
			Element cover = null;
			for (int i = 0; i < DOM.getChildCount(getElement()); i++) {
				Element el = DOM.getChild(getElement(), i);
				if ("connection-list-item-cover".equals(el.getClassName())) {
					cover = el;
					break;
				}
			}
			if (cover != null) {
				DOM.removeChild(getElement(), cover);
			}
			setShowArrow(true);
		}
	}



	private void buildTransfer(HorizontalPanel panel, TransportMeans[] transfer) {
		for (TransportMeans tm : transfer) {
			Image img = null;
			switch (tm) {
				case WALK:
					img = new Image(icons.walk_small());
					break;
				case BUS:
					img = new Image(icons.bus_small());
					break;
				case TRAM:
					img = new Image(icons.tram_small());
					break;
				case UBAHN:
					img = new Image(icons.ubahn_small());
					break;
				case SBAHN:
					img = new Image(icons.sbahn_small());
					break;
				default:
					break;
			}
			img.getElement().getStyle().setPaddingRight(4, Unit.PX);
			panel.add(img);
		}
	}



	private String formatDuration(Date from, Date to) {
		long millis = to.getTime() - from.getTime();
		long hours = millis / (1000L * 60 * 60);
		long minutes = (millis % (1000L * 60 * 60)) / (1000L * 60);
		StringBuilder sb = new StringBuilder();
		sb.append("Dauer: ");
		if (hours > 0) {
			sb.append(hours + " Stunden, ");
		}
		sb.append(minutes + " Minuten");
		return sb.toString();
	}
}
