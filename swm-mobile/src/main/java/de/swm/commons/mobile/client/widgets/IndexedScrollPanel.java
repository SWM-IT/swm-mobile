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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.scroll.ScrollPanel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * Indes scroll panel is a address book like widget that orders his entries alphabetically including a selection index
 * displayed on the right.
 * 
 */
public class IndexedScrollPanel extends HorizontalPanel {

	private static final double INNER_HEIGHT = 90.0;
	private static final double OUTER_HEIGHT = 100.0;
	private static final Set<Integer> usedIndexes = new HashSet<Integer>();

	private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
		"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private final ScrollPanel scroller;
	private final ListPanel list;



	/**
	 * Default constructor.
	 */
	public IndexedScrollPanel() {
		getElement().getStyle().setHeight(INNER_HEIGHT, Unit.PCT); // compensation for height of header panel
		scroller = new ScrollPanel();
		scroller.getElement().getStyle().setHeight(OUTER_HEIGHT, Unit.PCT);
		list = new ListPanel();
		createAlphabeticalIndex();
		scroller.add(list);
		add(scroller);
	}



	/**
	 * Create the basic index structure,.
	 */
	private void createAlphabeticalIndex() {
		for (int i = 0; i < letters.length; i++) {
			ListItem item = new ListItem();
			item.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexHeaderListItem());
			item.add(new Label(letters[i]));
			item.setDisabled(true);
			list.add(item);
		}
	}



	/**
	 * Adds the items to the pannel.
	 * 
	 * @param sectionIndex
	 *            the selection index
	 * @param items
	 *            the items
	 */
	public void setIndexedItems(int sectionIndex, List<ListItem> items) {
		if (!items.isEmpty()) {
			usedIndexes.add(sectionIndex);
		}
		int pos = findIndexedSectionPosition(sectionIndex);
		if (pos >= 0) {
			for (int j = items.size(); j > 0; j--) {
				list.insert(items.get(j - 1), (pos + 1));
			}
		}
	}



	/**
	 * Reomoves the header of empty indexes.
	 */
	public void removeEmptyIndexes() {
		final List<ListItem> listItemsToRemove = new ArrayList<ListItem>();

		ListPanel list = (ListPanel) scroller.getWidget();
		int sectionItem = 0;
		for (int i = 0; i < list.getWidgetCount(); i++) {
			ListItem item = list.getItem(i);
			if (item.getElement().getClassName()
					.contains(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexHeaderListItem())) {
				if (!usedIndexes.contains(sectionItem)) {
					listItemsToRemove.add(item);
				}
				sectionItem++;
			}
		}

		for (ListItem listItem : listItemsToRemove) {
			list.remove(listItem);
		}
	}



	/**
	 * Clears all entries.
	 * 
	 * @see de.swm.commons.mobile.client.base.PanelBase#clear()
	 */
	@Override
	public void clear() {
		list.clear();
		usedIndexes.clear();
		createAlphabeticalIndex();
	}



	/**
	 * Finds the screen position of an concrete index
	 * 
	 * @param sectionIndex
	 *            the index
	 * @return the screen position.
	 */
	private int findIndexedSectionPosition(int sectionIndex) {
		ListPanel list = (ListPanel) scroller.getWidget();
		int s = 0;
		for (int i = 0; i < list.getWidgetCount(); i++) {
			ListItem item = list.getItem(i);
			if (item.getElement().getClassName()
					.contains(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexHeaderListItem())) {
				if (s == sectionIndex) {
					return i;
				}
				s++;
			}
		}
		return -1;
	}
}
