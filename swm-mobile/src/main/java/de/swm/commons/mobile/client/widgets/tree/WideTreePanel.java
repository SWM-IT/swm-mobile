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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.event.SelectionChangedHandler;
import de.swm.commons.mobile.client.event.SwipeEvent;
import de.swm.commons.mobile.client.event.SwipeEventsHandler;
import de.swm.commons.mobile.client.theme.components.TreePanelCss;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.DecoratedListItem;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.PanelBase;
import de.swm.commons.mobile.client.widgets.ScrollPanel;
import de.swm.commons.mobile.client.widgets.SimpleImageButton;
import de.swm.commons.mobile.client.widgets.tree.TreeChangedEvent.EventType;

public class WideTreePanel extends PanelBase implements SwipeEventsHandler {

	private static final String BREADCRUMB_DELIMITER = ">";

	private TreePanelCss css;

	private boolean hasBreadcrumb;
	private HorizontalPanel breadcrumbPanel;
	private Label breadcrumbLabel;
	private SimpleImageButton backButton;
	
	private HorizontalPanel container;
	private ScrollPanel scrollPanel0;
	private ScrollPanel scrollPanel1;
	private ScrollPanel scrollPanel2;
	private ScrollPanel scrollPanel3;

	private HandlerRegistration handlerRegistration1;
	private HandlerRegistration handlerRegistration2;
	private SelectionChangedHandler selectionHandler1;
	private SelectionChangedHandler selectionHandler2;
	private ITree selectedTree;
	private Map<ListItem, ITree> itemMap = new HashMap<ListItem, ITree>();

	public WideTreePanel() {
		this(false);
	}

	@UiConstructor	
	public WideTreePanel(final boolean hasBreadcrumb) {
		this.hasBreadcrumb = hasBreadcrumb;
		css = SWMMobile.getTheme().getMGWTCssBundle().getTreePanelCss();
		addStyleName(css.getTreePanel());

		if (hasBreadcrumb) {
			breadcrumbPanel = new HorizontalPanel();
			breadcrumbPanel.addStyleName(css.getTreeBreadcrumbPanel());
			backButton = new SimpleImageButton(SWMMobile.getTheme().getMGWTImageBundle().arrowleft(), new ClickHandler() {
				
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
		
		container = new HorizontalPanel();
		container.addStyleName(css.getWideTreeContainer());
		add(container);

		scrollPanel0 = new ScrollPanel();
		scrollPanel0.addStyleName(css.getWideTreeList());
		scrollPanel0.addStyleName(css.getWideTreeList0());		
		scrollPanel0.add(new ListPanel());
		container.add(scrollPanel0);
		
		scrollPanel1 = new ScrollPanel();
		scrollPanel1.addStyleName(css.getWideTreeList());
		scrollPanel1.addStyleName(css.getWideTreeList1());		
		scrollPanel1.add(new ListPanel());
		container.add(scrollPanel1);
		
		scrollPanel2 = new ScrollPanel();
		scrollPanel2.addStyleName(css.getWideTreeList());
		scrollPanel2.addStyleName(css.getWideTreeList2());		
		scrollPanel2.add(new ListPanel());
		container.add(scrollPanel2);
		
		scrollPanel3 = new ScrollPanel();
		scrollPanel3.addStyleName(css.getWideTreeList());
		scrollPanel3.addStyleName(css.getWideTreeList3());		
		scrollPanel3.add(new ListPanel());
		container.add(scrollPanel3);
		
		selectionHandler1 = new SelectionChangedHandler() {
			
			@Override
			public void onSelectionChanged(SelectionChangedEvent e) {
				int selectedIdex = e.getSelection();
				ListPanel list1 = (ListPanel) scrollPanel1.getWidget();
				ListItem selectedItem = list1.getItem(selectedIdex);
				ITree tree = itemMap.get(selectedItem);
				
				if (tree != null) {
					if (tree.getChildren().isEmpty() && tree.getNode().getDisplay() != null) {
						// use custom display for leaf of tree
						scrollPanel2.clear();
						scrollPanel2.add(tree.getNode().getDisplay().asWidget());
						tree.getNode().getDisplay().setNode(tree.getNode());
					} else {
						// use standard list to display children of left tree
						ListPanel rightList = (ListPanel) scrollPanel2.getWidget();
						populateList(rightList, tree);
					}
	
					selectedTree = tree;
					clearSelectionState(list1);
					selectedItem.addStyleName(css.getSelectedItem());
					
					// set selected icon if specified by node
					if ((selectedTree.getNode().getSelectedIcon() != null) && (selectedItem instanceof DecoratedListItem)) {
						((DecoratedListItem) selectedItem).setImage(selectedTree.getNode().getSelectedIcon());
					}
					
					if (hasBreadcrumb) {
						shortenBreadcrumb();
						breadcrumbLabel.setText(breadcrumbLabel.getText() + " " + BREADCRUMB_DELIMITER + " " + tree.getNode().getHeader());
					}
				
					TreeChangedEvent event = new TreeChangedEvent(tree, EventType.CHILD_SELECTED);
					fireEvent(event);
				}
			}
		};
		ListPanel list1 = (ListPanel) scrollPanel1.getWidget();
		handlerRegistration1 = list1.addSelectionChangedHandler(selectionHandler1);
		
		selectionHandler2 = new SelectionChangedHandler() {
			
			@Override
			public void onSelectionChanged(SelectionChangedEvent e) {
				int selectedIdex = e.getSelection();
				ListPanel list = (ListPanel) scrollPanel2.getWidget();
				ListItem selectedItem = list.getItem(selectedIdex);
				ITree tree = itemMap.get(selectedItem);
				if (hasDetails(tree)) {
					shiftRight(tree);
					selectedTree = tree;
					selectedItem.addStyleName(css.getSelectedItem());
					
					// set selected icon if specified by node
					if ((selectedTree.getNode().getSelectedIcon() != null) && (selectedItem instanceof DecoratedListItem)) {
						((DecoratedListItem) selectedItem).setImage(selectedTree.getNode().getSelectedIcon());
					}					
				}
				
				if (tree != null) {
					TreeChangedEvent event = new TreeChangedEvent(tree, EventType.CHILD_SELECTED);
					fireEvent(event);
				}				
			}
		};
		ListPanel list2 = (ListPanel) scrollPanel2.getWidget();
		handlerRegistration2 = list2.addSelectionChangedHandler(selectionHandler2);
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
		selectedTree = tree;
		
		if (hasBreadcrumb) {
			breadcrumbLabel.setText(tree.getNode().getHeader());
		}
		
		ListPanel list = (ListPanel) scrollPanel1.getWidget();
		populateList(list, tree);
		list = (ListPanel) scrollPanel2.getWidget();
		clearListEntries(list, tree.getChildren().size(), getIconHeight(tree));
		
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
			scrollPanel3.clear();
			scrollPanel3.add(tree.getNode().getDisplay().asWidget());
			tree.getNode().getDisplay().setNode(tree.getNode());
		} else {
			// use standard list to display children of tree
			ListPanel rightList = (ListPanel) scrollPanel3.getWidget();
			populateList(rightList, tree);
		}

		ListPanel leftList = (ListPanel) scrollPanel1.getWidget();
		removeItemsFromMap(leftList);
		
		scrollPanel3.addStyleName(css.getSlide());
		scrollPanel2.addStyleName(css.getSlide());
		scrollPanel1.addStyleName(css.getSlide());
		scrollPanel3.addStyleName(css.getLeft());
		scrollPanel2.addStyleName(css.getWideLeft());
		scrollPanel1.addStyleName(css.getLeft());
		
		Utils.addEventListenerOnce(scrollPanel3.getElement(), Utils.getTransitionEventName(scrollPanel3.getElement()), false, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				handlerRegistration1.removeHandler();
				handlerRegistration2.removeHandler();
								
				scrollPanel3.removeStyleName(css.getSlide());
				scrollPanel2.removeStyleName(css.getSlide());
				scrollPanel1.removeStyleName(css.getSlide());
				scrollPanel3.removeStyleName(css.getLeft());
				scrollPanel2.removeStyleName(css.getWideLeft());
				scrollPanel1.removeStyleName(css.getLeft());

				scrollPanel0.removeStyleName(css.getWideTreeList0());
				scrollPanel1.removeStyleName(css.getWideTreeList1());
				scrollPanel2.removeStyleName(css.getWideTreeList2());
				scrollPanel3.removeStyleName(css.getWideTreeList3());

				// shift panels
				ScrollPanel tmp = scrollPanel0;
				scrollPanel0 = scrollPanel1;
				scrollPanel1 = scrollPanel2;
				scrollPanel2 = scrollPanel3;
				scrollPanel3  = tmp;
				
				scrollPanel0.addStyleName(css.getWideTreeList0());
				scrollPanel1.addStyleName(css.getWideTreeList1());
				scrollPanel2.addStyleName(css.getWideTreeList2());
				scrollPanel3.addStyleName(css.getWideTreeList3());
				
				ListPanel list1 = (ListPanel) scrollPanel1.getWidget();
				handlerRegistration1 = list1.addSelectionChangedHandler(selectionHandler1);
				
				Widget rightContent = scrollPanel2.getWidget();
				if (rightContent instanceof ListPanel) {
					ListPanel list = (ListPanel) rightContent;
					handlerRegistration2 = list.addSelectionChangedHandler(selectionHandler2);
				}
			}
			
		});
	}
	
	private void shiftLeft(final ITree tree, final ITree rightTree) {
		shortenBreadcrumb();
		
		ListPanel parentList = (ListPanel) scrollPanel0.getWidget();
		populateList(parentList, tree.getParent());
		updateSelectionState(parentList);
		
		ListPanel childList = (ListPanel) scrollPanel1.getWidget();
		clearSelectionState(childList);
		
		if (!rightTree.getChildren().isEmpty() || (rightTree.getNode().getDisplay() == null)) {
			// current child tree node is no leaf or has no custom display -> remove standard list items from map
			ListPanel grandchildrenList = (ListPanel) scrollPanel2.getWidget();
			removeItemsFromMap(grandchildrenList);
		}

		scrollPanel0.addStyleName(css.getSlide());
		scrollPanel1.addStyleName(css.getSlide());		
		scrollPanel2.addStyleName(css.getSlide());		
		scrollPanel0.addStyleName(css.getRight());
		scrollPanel1.addStyleName(css.getWideRight());		
		scrollPanel2.addStyleName(css.getRight());		

		Utils.addEventListenerOnce(scrollPanel1.getElement(), Utils.getTransitionEventName(scrollPanel1.getElement()), false, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				handlerRegistration1.removeHandler();
				
				if (!rightTree.getChildren().isEmpty() || (rightTree.getNode().getDisplay() == null)) {
					// current child tree node is no leaf or has no custom display -> there was a standard selection listener which must be removed
					handlerRegistration2.removeHandler();
				}
				
				scrollPanel0.removeStyleName(css.getSlide());
				scrollPanel1.removeStyleName(css.getSlide());		
				scrollPanel2.removeStyleName(css.getSlide());		
				scrollPanel0.removeStyleName(css.getRight());
				scrollPanel1.removeStyleName(css.getWideRight());		
				scrollPanel2.removeStyleName(css.getRight());		

				scrollPanel0.removeStyleName(css.getWideTreeList0());
				scrollPanel1.removeStyleName(css.getWideTreeList1());
				scrollPanel2.removeStyleName(css.getWideTreeList2());
				scrollPanel3.removeStyleName(css.getWideTreeList3());
				
				// shift panels
				ScrollPanel tmp = scrollPanel3;
				scrollPanel3 = scrollPanel2;
				scrollPanel2 = scrollPanel1;
				scrollPanel1 = scrollPanel0;
				scrollPanel0 = tmp;
								
				scrollPanel0.addStyleName(css.getWideTreeList0());
				scrollPanel1.addStyleName(css.getWideTreeList1());
				scrollPanel2.addStyleName(css.getWideTreeList2());
				scrollPanel3.addStyleName(css.getWideTreeList3());
				
				ListPanel list1 = (ListPanel) scrollPanel1.getWidget();
				handlerRegistration1 = list1.addSelectionChangedHandler(selectionHandler1);
				ListPanel list2 = (ListPanel) scrollPanel2.getWidget();
				handlerRegistration2 = list2.addSelectionChangedHandler(selectionHandler2);
				
				if (rightTree.getChildren().isEmpty() && (rightTree.getNode().getDisplay() != null)) {
					// current tree child node uses a custom display -> recreate list for new far right scroll panel
					scrollPanel3.clear();
					scrollPanel3.add(new ListPanel());
				}

			}
			
		});
	}

	private void shortenBreadcrumb() {
		if (hasBreadcrumb) {
			String breadcrumb = breadcrumbLabel.getText();
			int pos = breadcrumb.lastIndexOf(BREADCRUMB_DELIMITER);
			if (pos > 1) {
				breadcrumbLabel.setText(breadcrumb.substring(0, pos - 1));
			}
		}
	}
	
	private void updateSelectionState(ListPanel list) {
		if (selectedTree != null) {
			String selectedHeader = selectedTree.getNode().getHeader();
			for (int i = 0; i < list.getWidgetCount(); i++) {
				ListItem item = list.getItem(i);
				ITree t = itemMap.get(item);
				if (t != null) {
					if (selectedHeader.equals(t.getNode().getHeader())) {
						item.addStyleName(css.getSelectedItem());
						
						// set selected icon if specified by node
						if ((t.getNode().getSelectedIcon() != null) && (item instanceof DecoratedListItem)) {
							DecoratedListItem decoratedItem = (DecoratedListItem) item;
							decoratedItem.setImage(t.getNode().getSelectedIcon());
						}
					}
				}
			}
		}
	}
	
	private void clearSelectionState(ListPanel list) {
		for (int i = 0; i < list.getWidgetCount(); i++) {
			ListItem item = list.getItem(i);
			item.removeStyleName(css.getSelectedItem());

			// set normal icon if specified by node
			ITree t = itemMap.get(item);
			if ((t != null) && (t.getNode().getIcon() != null) && (item instanceof DecoratedListItem)) {
				DecoratedListItem decoratedItem = (DecoratedListItem) item;
				decoratedItem.setImage(t.getNode().getIcon());
			}				
		}		
	}

	private void populateList(ListPanel list, ITree tree) {
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
	
	private void clearListEntries(ListPanel list, int num, int iconHeight) {
		list.clear();
		for (int i = 0; i < num; i++) {
			ListItem item = null;
			if (iconHeight > 0) {
				item = new DecoratedListItem();
				item.setTitle(" ");
				((DecoratedListItem) item).getImage().setPixelSize(0, iconHeight);
			} else {
				item = new ListItem();
				item.add(new HTML("&nbsp;"));
			}
			item.setShowArrow(false);
			list.add(item);
		}
	}

	private void removeItemsFromMap(ListPanel list) {
		for (int i = 0; i < list.getWidgetCount(); i++) {
			ListItem item = list.getItem(i);
			if (item != null) {
				itemMap.remove(item);
			}
		}
	}
	
	private int getIconHeight(ITree tree) {
		List<ITree> children = tree.getChildren();
		for (ITree child : children) {
			ITreeNode node = child.getNode();
			if (node.getIcon() != null) {
				return node.getIcon().getHeight();
			}
		}
		return -1;
	}

	private void navigateBack() {
		if (selectedTree.getParent() != null) {
			ITree child = selectedTree;
			selectedTree = selectedTree.getParent();
			if (selectedTree.getParent() != null) { // another back step possible
				shiftLeft(selectedTree, child);
			} else { // next step is root of tree
				shortenBreadcrumb();
				ListPanel list = (ListPanel) scrollPanel1.getWidget();
				clearSelectionState(list);
				list = (ListPanel) scrollPanel2.getWidget();
				clearListEntries(list, selectedTree.getChildren().size(), getIconHeight(selectedTree));
			}
			
			TreeChangedEvent backEvent = new TreeChangedEvent(selectedTree, EventType.BACK_EVENT);
			fireEvent(backEvent);
		}
	}
	
}
