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
package de.swm.commons.mobile.client.widgets.tree;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.theme.components.TreePanelCss;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.*;
import de.swm.commons.mobile.client.widgets.tree.TreeChangedEvent.EventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreePanel extends PanelBase implements SwipeEventsHandler {
	
	private static final String BREADCRUMB_DELIMITER = ">";
	
	private final TreePanelCss css;
	
	private final boolean hasBreadcrumb;
	private Label breadcrumbLabel;

	private ScrollPanelWithScrollbar leftScrollPanel;
	private ScrollPanelWithScrollbar middleScrollPanel;
	private ScrollPanelWithScrollbar rightScrollPanel;
	
	private ITree selectedTree;
	private HandlerRegistration selectionHandlerRegistration;
	private final SelectionChangedHandler selectionHandler;
	private final Map<ListItem, ITree> itemMap = new HashMap<ListItem, ITree>();
	
	public TreePanel() {
		this(false);
	}

	@UiConstructor
	public TreePanel(boolean hasBreadcrumb) {
		this.hasBreadcrumb = hasBreadcrumb;
		css = SWMMobile.getTheme().getMGWTCssBundle().getTreePanelCss();
		addStyleName(css.getTreePanel());
		
		if (hasBreadcrumb) {
			HorizontalPanel breadcrumbPanel = new HorizontalPanel();
			breadcrumbPanel.addStyleName(css.getTreeBreadcrumbPanel());
			ImageHighlightButton backButton = new ImageHighlightButton(SWMMobile.getTheme().getMGWTImageBundle().arrowleft(), new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					navigateBack();
				}
			});
			backButton.addStyleName(css.getTreeBreadcrumbButton());
			breadcrumbPanel.add(backButton);
			breadcrumbLabel = new Label("-");
			breadcrumbLabel.addStyleName(css.getTreeBreadcrumbLabel());
			breadcrumbPanel.add(breadcrumbLabel);
			add(breadcrumbPanel);
		}

		HorizontalPanel container = new HorizontalPanel();
		container.addStyleName(css.getTreeContainer());
		add(container);
		
		leftScrollPanel = new ScrollPanelWithScrollbar();
		leftScrollPanel.addStyleName(css.getTreeList());
		leftScrollPanel.addStyleName(css.getTreeLeftList());		
		leftScrollPanel.add(new ListPanel());
		container.add(leftScrollPanel);
		
		rightScrollPanel = new ScrollPanelWithScrollbar();
		rightScrollPanel.addStyleName(css.getTreeList());
		rightScrollPanel.addStyleName(css.getTreeRightList());
		rightScrollPanel.add(new ListPanel());
		container.add(rightScrollPanel);
		
		middleScrollPanel = new ScrollPanelWithScrollbar();
		middleScrollPanel.addStyleName(css.getTreeList());
		middleScrollPanel.addStyleName(css.getTreeMiddleList());
		middleScrollPanel.add(new ListPanel());		
		container.add(middleScrollPanel);
		
		selectionHandler = new SelectionChangedHandler() {
			
			@Override
			public void onSelectionChanged(SelectionChangedEvent e) {
				int selectedIdex = e.getSelection();
				ListPanel list = (ListPanel) middleScrollPanel.getWidget();
				ListItem selectedItem = list.getItem(selectedIdex);
				ITree tree = itemMap.get(selectedItem);
				if (hasDetails(tree)) {
					shiftRight(tree);
					selectedTree = tree;
				}
				
				if (tree != null) {
					TreeChangedEvent event = new TreeChangedEvent(tree, EventType.CHILD_SELECTED);
					fireEvent(event);
				}
			}
		};
		ListPanel list = (ListPanel) middleScrollPanel.getWidget();
		selectionHandlerRegistration = list.addSelectionChangedHandler(selectionHandler);
	}
	
	private boolean hasDetails(ITree tree) {
		if (tree != null) {
			if (!tree.getChildren().isEmpty()) {
				return true;
			} else {
				ITreeNode node = tree.getNode();
				if (node != null && node.getDisplay() != null) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setTree(ITree tree) {
		ListPanel list = (ListPanel) middleScrollPanel.getWidget();
		populateList(list, tree);
		selectedTree = tree;
		if (hasBreadcrumb) {
			breadcrumbLabel.setText(tree.getNode().getHeader());
		}
		
		TreeChangedEvent event = new TreeChangedEvent(selectedTree, EventType.INIT_COMPLETE);
		this.fireEvent(event);
	}
	
	/**
	 * Adds a tree changed handler.
	 * 
	 * @param handler
	 *            tree changed handler
	 * @return handle to remove this handler.
	 */
	public HandlerRegistration addTreeChangedHandler(TreeChangedHandler handler) {
		return this.addHandler(handler, TreeChangedEvent.TYPE);
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addSwipeEventsHandler(this);
	}

	@Override
	protected void onUnload() {
		DragController.get().removeSwipeEventHandler(this);
		super.onUnload();
	}

	@Override
	public void onSwipeHorizontal(SwipeEvent e) {
		e.stopPropagation();
		if (e.getSpeed() > 0) {
			navigateBack();
		}
	}

	@Override
	public void onSwipeVertical(SwipeEvent e) {
		// Do nothing.
	}
	
	private void shiftRight(ITree tree) {
		if (hasBreadcrumb) {
			breadcrumbLabel.setText(breadcrumbLabel.getText() + " " + BREADCRUMB_DELIMITER + " " + tree.getNode().getHeader());
		}
		
		if (tree.getChildren().isEmpty() && tree.getNode().getDisplay() != null) {
			// use custom display for leaf of tree
			rightScrollPanel.clear();
			rightScrollPanel.add(tree.getNode().getDisplay().asWidget());
			tree.getNode().getDisplay().setNode(tree.getNode());
		} else {
			// use standard list to display children of tree
			ListPanel list = (ListPanel) rightScrollPanel.getWidget();
			populateList(list, tree);
		}
		
		middleScrollPanel.addStyleName(css.getSlide());
		rightScrollPanel.addStyleName(css.getSlide());
		middleScrollPanel.addStyleName(css.getLeft());
		rightScrollPanel.addStyleName(css.getLeft());
		
		Utils.addEventListenerOnce(rightScrollPanel.getElement(), Utils.getTransitionEventName(rightScrollPanel.getElement()), false, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				selectionHandlerRegistration.removeHandler();
								
				middleScrollPanel.removeStyleName(css.getSlide());
				rightScrollPanel.removeStyleName(css.getSlide());
				middleScrollPanel.removeStyleName(css.getLeft());
				rightScrollPanel.removeStyleName(css.getLeft());

				leftScrollPanel.removeStyleName(css.getTreeLeftList());
				middleScrollPanel.removeStyleName(css.getTreeMiddleList());
				rightScrollPanel.removeStyleName(css.getTreeRightList());

				// shift panels
				ScrollPanelWithScrollbar tmp = leftScrollPanel;
				leftScrollPanel = middleScrollPanel;
				middleScrollPanel = rightScrollPanel;
				rightScrollPanel  = tmp;
				
				leftScrollPanel.addStyleName(css.getTreeLeftList());
				middleScrollPanel.addStyleName(css.getTreeMiddleList());
				rightScrollPanel.addStyleName(css.getTreeRightList());
				
				Widget content = middleScrollPanel.getWidget();
				if (content instanceof ListPanel) {
					ListPanel list = (ListPanel) content;
					selectionHandlerRegistration = list.addSelectionChangedHandler(selectionHandler);
				}
			}
			
		});
	}
	
	private void shiftLeft(final ITree tree) {
		if (hasBreadcrumb) {
			String breadcrumb = breadcrumbLabel.getText();
			int pos = breadcrumb.lastIndexOf(BREADCRUMB_DELIMITER);
			if (pos > 1) {
				breadcrumbLabel.setText(breadcrumb.substring(0, pos - 1));
			}
		}
		
		final ListPanel list = (ListPanel) leftScrollPanel.getWidget();
		populateList(list, tree.getParent());
		
		middleScrollPanel.addStyleName(css.getSlide());
		leftScrollPanel.addStyleName(css.getSlide());		
		middleScrollPanel.addStyleName(css.getRight());
		leftScrollPanel.addStyleName(css.getRight());		

		Utils.addEventListenerOnce(middleScrollPanel.getElement(), Utils.getTransitionEventName(middleScrollPanel.getElement()), false, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				if (!tree.getChildren().isEmpty() || (tree.getNode().getDisplay() == null)) {
					// current tree node is no leaf or has no custom display -> there was a standard selection listener which must be removed
					selectionHandlerRegistration.removeHandler();
				}
				
				middleScrollPanel.removeStyleName(css.getSlide());
				leftScrollPanel.removeStyleName(css.getSlide());		
				middleScrollPanel.removeStyleName(css.getRight());
				leftScrollPanel.removeStyleName(css.getRight());		

				leftScrollPanel.removeStyleName(css.getTreeLeftList());
				middleScrollPanel.removeStyleName(css.getTreeMiddleList());
				rightScrollPanel.removeStyleName(css.getTreeRightList());
				
				// shift panels
				ScrollPanelWithScrollbar tmp = rightScrollPanel;
				rightScrollPanel = middleScrollPanel;
				middleScrollPanel = leftScrollPanel;
				leftScrollPanel = tmp;
								
				leftScrollPanel.addStyleName(css.getTreeLeftList());
				middleScrollPanel.addStyleName(css.getTreeMiddleList());
				rightScrollPanel.addStyleName(css.getTreeRightList());
				
				if (tree.getChildren().isEmpty() && (tree.getNode().getDisplay() != null)) {
					// current tree node uses a custom display -> recreate list for new right scroll panel
					rightScrollPanel.clear();
					rightScrollPanel.add(new ListPanel());
				}

				selectionHandlerRegistration = list.addSelectionChangedHandler(selectionHandler);
			}
			
		});
	}
	
	private void populateList(ListPanel list, ITree tree) {
		itemMap.clear();
		list.clear();
		List<ITree> children = tree.getChildren();
		for (ITree child : children) {
			ITreeNode node = child.getNode();
			ListItem item = null;
			if (node.getIcon() != null) {
				item = new DecoratedListItem(node.getIcon(), node.getHeader(), null);
			} else {
				item = new ListItem();
				item.add(new Label(node.getHeader()));
			}
			item.setShowArrow(!child.getChildren().isEmpty());
			if (node.getStyleName() != null) {
				item.addStyleName(node.getStyleName());
			}
			list.add(item);
			itemMap.put(item, child);
		}		
	}

	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		leftScrollPanel.setHeight(height);
		rightScrollPanel.setHeight(height);
		middleScrollPanel.setHeight(height);
	}

	private void navigateBack() {
		if (selectedTree.getParent() != null) {
			shiftLeft(selectedTree);
			selectedTree = selectedTree.getParent();
			
			TreeChangedEvent backEvent = new TreeChangedEvent(selectedTree, EventType.BACK_EVENT);
			fireEvent(backEvent);
		}
	}
}
