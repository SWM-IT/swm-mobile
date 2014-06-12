package de.swm.mobile.kitchensink.client.base;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.Application;

/**
 * SWM S-IP-AN
 * User: wiesed
 * Date: 12.06.14
 * Time: 14:05
 */
public abstract class ShowcaseDetailPage extends SimplePage {


	public abstract SimpleHeaderPanel getHeaderPanel();


	public void setParentPage(final ShowcaseOverviewPage overviewPage){
		this.setParent(overviewPage.getParentWidget());
		getHeaderPanel().setLeftButtonClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				goTo(overviewPage, Direction.LEFT);
			}
		});
	}
}
