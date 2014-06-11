package de.swm.mobile.kitchensink.client.components;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.VerticalPanel;

import java.util.Date;


/**
 * ListItem wich represents an individual component of a trip.
 * 
 * @author vilbig.alexander <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class TripListItem extends ListItem {

	private Image progressImage;
	private Image categoryImage;
	private Label titleLabel;
	private Label subtitleLabel;
	private Date departureTime;
	private Date arrivalTime;




	public @UiConstructor
	TripListItem(ImageResource progress, ImageResource category, String title, String subtitle) {
		getElement().getStyle().setPadding(0.0, Unit.PX);
		HorizontalPanel panel = new HorizontalPanel();
		progressImage = new Image(progress);
		panel.add(progressImage);
		categoryImage = new Image(category);
		categoryImage.getElement().getStyle().setPadding(4.0, Unit.PX);
		categoryImage.getElement().getStyle().setMarginLeft(8.0, Unit.PX);
		panel.add(categoryImage);
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss().decoratedListItemVPanel());
		titleLabel = new Label(title);
		titleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemTitle());
		vPanel.add(titleLabel);
		subtitleLabel = new Label(subtitle);
		subtitleLabel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getDecoratedListItemCss()
				.decoratedListItemSubtitle());
		vPanel.add(subtitleLabel);
		panel.add(vPanel);
		add(panel);
	}



	public void setProgressImage(ImageResource image) {
		progressImage.setResource(image);
	}



	public void setCategoryImage(Image categoryImage) {
		this.categoryImage = categoryImage;
	}



	public void setTitle(String title) {
		titleLabel.setText(title);
	}



	public void setSubtitle(String subtitle) {
		subtitleLabel.setText(subtitle);
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}
