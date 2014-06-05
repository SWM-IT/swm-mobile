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

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.gwt.client.mobile.IPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A Toolbar can be places on the bottom of an {@link IPage}. Typically a toolbar contains typical use cases of a mobile
 * application.
 * <p/>
 * <pre>
 * <m:ToolbarPanel indexToSelectOnInit="0" ui:field="toolbar">
 * 			<m:ToolbarElement>
 * 				<m:ToolbarHeader headerText='Item 1' headerImage='{icon.tab0}' highlightImage='{icon.tab0selected}' />
 * 				<m:ToolbarContent>
 * 					<g:HTMLPanel styleName="Toolbar-Content">
 * 						<g:Label>Tab1 Content</g:Label>
 * 					</g:HTMLPanel>
 * 				</m:ToolbarContent>
 * 			</m:ToolbarElement>
 * 			<m:ToolbarElement>
 * 				<m:ToolbarHeader headerText='Item 2' headerImage='{icon.tab1}' highlightImage='{icon.tab1selected}' />
 * 				<m:ToolbarContent>
 * 					<g:HTMLPanel styleName="Toolbar-Content">
 * 						<g:Label>Tab2 Content</g:Label>
 * 					</g:HTMLPanel>
 * 				</m:ToolbarContent>
 * 			</m:ToolbarElement>
 * 			<m:ToolbarElement>
 * 				<m:ToolbarHeader headerText='Item 3' headerImage='{icon.tab2}' highlightImage='{icon.tab2selected}' />
 * 				<m:ToolbarContent>
 * 					<g:HTMLPanel styleName="Toolbar-Content">
 * 						<g:Label>Tab3 Content</g:Label>
 * 					</g:HTMLPanel>
 * 				</m:ToolbarContent>
 * 			</m:ToolbarElement>
 * 		</m:ToolbarPanel>
 * </pre>
 */
public class ToolbarPanel extends SWMMobileWidgetBase implements HasWidgets {

	/**
	 * Tool bas selection handler.
	 */
	public interface ToolbarSelectionHandler {

		/**
		 * Callback to determine which tool was selected.
		 *
		 * @param index the index
		 * @param te    the {@link ToolbarElement}
		 */
		void toolSelected(int index, ToolbarElement te);
	}

	private FlowPanel mainPanel = new FlowPanel();
	private FlowPanel toolbarPanel = new FlowPanel();
	private FlowPanel contentPanel = new FlowPanel();
	private int selectedToolIndex = -1;
	private List<ToolbarSelectionHandler> selectionHandlers = new ArrayList<ToolbarSelectionHandler>();
	private List<ToolbarElement> myElements = new ArrayList<ToolbarElement>();
	private final int indexToSelectOnInit;

	/**
	 * Default constructor.
	 */
	public ToolbarPanel() {
		this(0);
	}


	/**
	 * Constructor using a selection index.
	 *
	 * @param indexToSelectOnInit the selected tab (tool)
	 */
	@UiConstructor
	public ToolbarPanel(int indexToSelectOnInit) {
		this.indexToSelectOnInit = indexToSelectOnInit;
		initWidget(mainPanel);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getToolbarPanelCss().toolbarPanel());
		mainPanel.add(contentPanel);
		mainPanel.add(toolbarPanel);
	}


	public void unselectAllElements() {
		for (ToolbarElement currElement : myElements) {
			currElement.setSelected(false, false);
		}
	}


	@Override
	public void add(Widget w) {
		assert w instanceof ToolbarElement : "Can only place ToolbarElement widgets inside a Toolbar Panel.";
		myElements.add((ToolbarElement) w);
		((ToolbarElement) w).setOwningPanel(this, myElements.size() - 1);
		toolbarPanel.add(w);
	}

	/**
	 * Removes all child widgets.
	 */
	@Override
	public void clear() {
		myElements.clear();
		toolbarPanel.clear();
	}


	@Override
	public void onInitialLoad() {
		if (toolbarPanel.getWidgetCount() > 0) {
			selectTool(indexToSelectOnInit);
		}
	}


	/**
	 * Adds a selection handler.
	 *
	 * @param handler the selection handler.
	 */
	public void addSelectionHandler(ToolbarSelectionHandler handler) {
		selectionHandlers.add(handler);
	}


	/**
	 * Removes the selection handler.
	 *
	 * @param handler the handler.
	 */
	public void removeSelectionHandler(ToolbarSelectionHandler handler) {
		selectionHandlers.remove(handler);
	}


	/**
	 * Returns the content of the first element in the toolbar.
	 *
	 * @return the content.
	 */
	public ToolbarContent getContent() {
		Widget content = null;
		try {
			content = contentPanel.getWidget(0);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
		return (ToolbarContent) content;
	}


	public HasWidgets getContentArea() {
		return contentPanel;
	}


	/**
	 * Selects a tool on <code>index</code> position
	 *
	 * @param index the index position
	 */
	public void selectTool(int index) {
		selectTool(index, false);
	}


	/**
	 * Selects a tool on <code>index</code> position
	 *
	 * @param index            the index position
	 * @param enforceSelection the selection will always be updated
	 */
	public void selectTool(int index, boolean enforceSelection) {
		selectTool(index, enforceSelection, true);

	}


	/**
	 * Selects a tool on <code>index</code> position
	 *
	 * @param index            the index position
	 * @param enforceSelection the selection will always be updated
	 * @param fireUpdate       if true event handlers will be notified
	 */
	public void selectTool(int index, boolean enforceSelection, boolean fireUpdate) {
		if (selectedToolIndex == index && !enforceSelection) {
			return;
		}
		if (index >= 0 && index < this.myElements.size()) {
			unselectAllElements();
			this.myElements.get(index).setSelected(true, false);
		}
		if (fireUpdate) {
			fireSelectionChangeUpdate(index);
		} else {
			selectedToolIndex = index;
		}
	}

	/**
	 * Fires an selection changed update.
	 *
	 * @param index the selected index
	 */
	public void fireSelectionChangeUpdate(int index) {
		if (index >= 0 && index < this.myElements.size()) {
			selectedToolIndex = index;
			for (ToolbarSelectionHandler handler : selectionHandlers) {
				handler.toolSelected(selectedToolIndex, this.myElements.get(index));
			}

		}
	}


	public int getSelectedToolIndex() {
		return selectedToolIndex;
	}

	/**
	 * Sets the text to be displayed in form of a 'badge' on top of the header image of a tool.
	 *
	 * @param index      the index position
	 * @param badgeValue text to be displayed as badge. If badgeValue is {@code null}, the badge is hidden.
	 */
	public void setBadgeValue(int index, String badgeValue) {
		ToolbarElement to = (ToolbarElement) toolbarPanel.getWidget(index);
		to.getHeader().setBadgeValue(badgeValue);
	}


	@Override
	public Iterator<Widget> iterator() {
		return mainPanel.iterator();
	}


	@Override
	public boolean remove(Widget w) {
		return mainPanel.remove(w);
	}


	/**
	 * True if the header should be visible.
	 *
	 * @param visible true if visible
	 */
	public void setHeaderVisible(boolean visible) {
		toolbarPanel.setVisible(visible);
	}

}
