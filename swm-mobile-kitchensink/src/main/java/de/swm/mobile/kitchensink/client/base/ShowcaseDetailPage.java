package de.swm.mobile.kitchensink.client.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.Button;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.ShowcaseConstants;

/**
 * SWM S-IP-AN
 * User: wiesed
 * Date: 12.06.14
 * Time: 14:05
 */
public abstract class ShowcaseDetailPage extends SimplePage {

	private final Class<? extends ShowcaseDetailPage> clazz;
	boolean isInitialized = false;


	public abstract SimpleHeaderPanel getHeaderPanel();

	protected ShowcaseDetailPage(Class<? extends ShowcaseDetailPage> clazz) {
		this.clazz = clazz;
	}

	public void init() {
		if (!isInitialized) {
			isInitialized = true;
			final String shortClassName = clazz.getSimpleName();

			HorizontalPanel menu = new HorizontalPanel();
			menu.add(new Button("Source", new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					String path = GWT.getModuleBaseURL() + ShowcaseConstants.DST_SOURCE_EXAMPLE + shortClassName + ".html";
					Window.open(path, "_blank", null);
				}
			}));
			menu.add(new Button("UI.xml", new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					String path = GWT.getModuleBaseURL() + ShowcaseConstants.DST_SOURCE_UIXML + shortClassName + ".ui.xml.html";
					Window.open(path, "_blank", null);
				}
			}));
			getHeaderPanel().setRightWidget(menu);
		}
	}


	public void setParentPage(final ShowcaseOverviewPage overviewPage) {
		if (!isInitialized) {
			init();
		}
		this.setParent(overviewPage.getParentWidget());
		getHeaderPanel().setLeftButtonClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				goTo(overviewPage, Direction.LEFT);
			}
		});
	}
}
