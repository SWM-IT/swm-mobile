package de.swm.mobile.kitchensink.client.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.theme.bootstrap.extention.TestResources;

/**
 * SWM S-IP-AN
 * User: wiesed
 * Date: 12.06.14
 * Time: 13:51
 */
public abstract class ShowcaseOverviewPage extends SimplePage {


	private final TestResources resources;

	@UiField
	HeaderPanel header;

	@UiField
	ListPanel components;


	private static ShowcaseOverviewUiBinder uiBinder = GWT.create(ShowcaseOverviewUiBinder.class);
	private HasWidgets parentWidget;

	interface ShowcaseOverviewUiBinder extends UiBinder<Widget, ShowcaseOverviewPage> {
	}


	public ShowcaseOverviewPage(TestResources resources, HasWidgets toolbarContentArea, final int toolbarIndex) {
		this.resources = resources;
		initWidget(uiBinder.createAndBindUi(this));
		this.setParent(toolbarContentArea);
		header.setCaption(this.getName());

	}

	public void addPage(final ShowcaseDetailPage page) {
		final ListItem toAdd = new ListItem();
		toAdd.add(new Label(page.getName()));
		page.setParentPage(this);
		toAdd.setShowArrow(true);
		toAdd.addClickHanlder(new ClickHandler() {
			@Override
			public void onClick(ClickEvent clickEvent) {
				goTo(page, Direction.RIGHT);
			}
		});
		components.add(toAdd);
	}

	public TestResources getResources() {
		return resources;
	}

	@Override
	public void setParent(HasWidgets parent) {
		parentWidget = parent;
		super.setParent(parent);
	}

	public HasWidgets getParentWidget() {
		return parentWidget;
	}
}
