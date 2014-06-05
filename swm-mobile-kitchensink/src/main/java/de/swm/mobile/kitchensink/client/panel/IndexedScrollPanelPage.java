package de.swm.mobile.kitchensink.client.panel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.IndexedScrollPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;

public class IndexedScrollPanelPage extends SimplePage {

	private static String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

	private static IndexedScrollPanelPageUiBinder uiBinder = GWT.create(IndexedScrollPanelPageUiBinder.class);

	interface IndexedScrollPanelPageUiBinder extends UiBinder<Widget, IndexedScrollPanelPage> {}

	@UiField HTMLPanel content;
	@UiField SimpleHeaderPanel header;
	@UiField IndexedScrollPanel list;
	
	public IndexedScrollPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		for (int i = 0; i < 26; i++) {
			List<ListItem> items = new ArrayList<ListItem>();
			for (int j = 0; j < 5; j++) {
				ListItem item = new ListItem();
				item.add(new Label(letters[i] + " List Item " + j));
				items.add(item);
			}
			list.setIndexedItems(i, items);
		}
		list.updateIndex();
	}

	@Override
	public String getName() {
		return "IndexPanel";
	}

}
