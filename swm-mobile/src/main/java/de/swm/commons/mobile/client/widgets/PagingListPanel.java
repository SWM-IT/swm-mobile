/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.Utils;



/**
 * List panel which enables paging. This is realized be placing a loading {@link DecoratedListItem}. If this item is
 * pressed, more items are fetched from the server.
 */
public class PagingListPanel extends ListPanel {

	private static final Logger LOGGER = Logger.getLogger(PagingListPanel.class.getName());
	private static final int DEFAULT_PAGE_SIZE = 10;


	/**
	 * Data provider.
	 * 
	 * 
	 *         
	 * 
	 */
	public interface ListItemProvider {

		/**
		 * Will provide more items from the server.
		 * 
		 * @param startIndex
		 *            the start index
		 * @param numEntries
		 *            number of entries
		 * @param callback
		 *            callback
		 */
		void provideItems(int startIndex, int numEntries, AsyncCallback<List<ListItem>> callback);

	}

	private ListItemProvider provider;
	private int currentPage = 0;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private DecoratedListItem moreItem;
	private ImageResource moreIcon = SWMMobile.getTheme().getMGWTImageBundle().down();
	private String moreText = "More...";
	private boolean showLoadingIndicator = false;
	private LoadingIndicatorPopup loadingIndicator = null;



	public void setProvider(ListItemProvider provider) {
		this.provider = provider;
	}



	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public void setMoreIcon(ImageResource moreIcon) {
		this.moreIcon = moreIcon;
	}



	public void setMoreText(String moreText) {
		this.moreText = moreText;
	}



	public void setShowLoadingIndicator(boolean showLoadingIndicator) {
		this.showLoadingIndicator = showLoadingIndicator;
	}



	@Override
	public void onLoad() {
		super.onLoad();
		assert provider != null : "ListItemProvider may not be null";
		provider.provideItems((currentPage * pageSize), pageSize, new AsyncCallback<List<ListItem>>() {

			@Override
			public void onFailure(Throwable caught) {
				LOGGER.info("error: " + caught);
			}



			@Override
			public void onSuccess(List<ListItem> result) {
				for (ListItem item : result) {
					add(item);
				}
				moreItem = new DecoratedListItem(moreIcon, moreText, "");
				add(moreItem);
			}
		});
	}



	@Override
	public void onClick(ClickEvent e) {
		if (mySelected >= 0) {
			ListItem item = (ListItem) getWidget(mySelected);
			if (item.equals(moreItem)) {
				if (showLoadingIndicator) {
					if (loadingIndicator == null) {
						loadingIndicator = new LoadingIndicatorPopup();
					}
					loadingIndicator.showCentered(true);
				}
				moreItem.setImage(SWMMobile.getTheme().getMGWTImageBundle().loading());
				currentPage++;
				provider.provideItems((currentPage * pageSize), pageSize, new AsyncCallback<List<ListItem>>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.info("error: " + caught);
						if (showLoadingIndicator) {
							loadingIndicator.hide();
						}
					}



					@Override
					public void onSuccess(List<ListItem> result) {
						moreItem.setImage(SWMMobile.getTheme().getMGWTImageBundle().down());
						remove(moreItem);
						for (ListItem i : result) {
							add(i);
						}
						add(moreItem);
						if (showLoadingIndicator) {
							loadingIndicator.hide();
						}
					}
				});
			}
		}
		super.onClick(e);
	}

}
