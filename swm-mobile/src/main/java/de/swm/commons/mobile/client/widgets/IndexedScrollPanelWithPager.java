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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;
import de.swm.commons.mobile.client.widgets.scroll.ScrollPanel;

import java.util.List;


/**
 * Indes scroll panel is a address book like widget that orders his entries alphabetically including a selection index
 * displayed on the right.
 */
public class IndexedScrollPanelWithPager extends HorizontalPanel {

	private static final double INNER_HEIGHT = 90.0;
	private static final double OUTER_HEIGHT = 100.0;
	private static final String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * Inner class that represents the alphabetical selection index.
	 */
	public class IndexPanel extends VerticalPanel implements DragEventsHandler {

		private static final int PADDING = 4;

		private static final int NO_OF_LETTERS = 26;

		private final IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();

		private final int[] sectionHeight = new int[NO_OF_LETTERS];
		private int absY, letterHeight;
		private final ScrollPanel scroller;


		/**
		 * Default constructor.
		 *
		 * @param scroller the indexed scroller.
		 */
		public IndexPanel(final ScrollPanel scroller) {
			this.scroller = scroller;
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexPanel());
			for (int i = 0; i < NO_OF_LETTERS; i++) {
				add(new Label(letters[i]));
				sectionHeight[i] = -1;
			}

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					absY = getAbsoluteTop() + PADDING; // include padding
					letterHeight = getWidget(0).getElement().getClientHeight();
				}
			});
		}


		/**
		 * Updates the positions.
		 */
		void updateSectionPositions() {
			for (int i = 0; i < NO_OF_LETTERS; i++) {
				sectionHeight[i] = -1;
			}
			ListPanel list = (ListPanel) scroller.getWidget();
			int s = 0;
			for (int i = 0; i < list.getWidgetCount(); i++) {
				ListItem item = list.getItem(i);
				if (item.getElement().getClassName()
						.contains(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexHeaderListItem())) {
					sectionHeight[s++] = item.getElement().getOffsetTop();
				}
			}
		}


		@Override
		public void onLoad() {
			super.onLoad();
			DragController.get().addDragEventsHandler(this);
			myWidgetHelper.checkInitialLoad(this);
		}


		@Override
		public void onUnload() {
			DragController.get().removeDragEventsHandler(this);
		}


		@Override
		public void onDragStart(DragEvent e) {
			// do nothing
		}


		@Override
		public void onDragMove(DragEvent e) {
			int index = (((int) e.getY()) - absY) / letterHeight;
			index = Math.max(0, index);
			index = Math.min(NO_OF_LETTERS - 1, index);
			if (sectionHeight[index] >= 0) {
				scroller.setScrollPosition(-sectionHeight[index]);
			}
		}


		@Override
		public void onDragEnd(DragEvent e) {
			int index = (((int) e.getY()) - absY) / letterHeight;
			index = Math.max(0, index);
			index = Math.min(NO_OF_LETTERS - 1, index);
			if (sectionHeight[index] >= 0) {
				scroller.setScrollPosition(-sectionHeight[index]);
			}
		}
	}

	private final IndexPanel indexPanel;
	private final ScrollPanel scroller;
	private final ListPanel list;


	/**
	 * Default constructor.
	 */
	public IndexedScrollPanelWithPager() {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getIndexPanelCss().indexedScrollPanel());
		getElement().getStyle().setHeight(INNER_HEIGHT, Unit.PCT); // compensation for height of header panel
		scroller = new ScrollPanel();
		scroller.getElement().getStyle().setHeight(OUTER_HEIGHT, Unit.PCT);
		list = new ListPanel();
		createAlphabeticalIndex();
		scroller.add(list);
		add(scroller);
		indexPanel = new IndexPanel(scroller);
		add(indexPanel);
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
	 * @param sectionIndex the selection index
	 * @param items        the items
	 */
	public void setIndexedItems(int sectionIndex, List<ListItem> items) {
		int pos = findIndexedSectionPosition(sectionIndex);
		if (pos >= 0) {
			for (int j = items.size(); j > 0; j--) {
				list.insert(items.get(j - 1), (pos + 1));
			}
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
		createAlphabeticalIndex();
	}


	/**
	 * Updated the index.
	 */
	public void updateIndex() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				indexPanel.updateSectionPositions();
			}
		});
	}


	/**
	 * Finds the screen position of an concrete index
	 *
	 * @param sectionIndex the index
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
