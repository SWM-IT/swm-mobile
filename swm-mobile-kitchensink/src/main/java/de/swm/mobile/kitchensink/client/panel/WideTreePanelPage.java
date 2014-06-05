package de.swm.mobile.kitchensink.client.panel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.tree.DefaultTreeImpl;
import de.swm.commons.mobile.client.widgets.tree.DefaultTreeNodeImpl;
import de.swm.commons.mobile.client.widgets.tree.ITree;
import de.swm.commons.mobile.client.widgets.tree.ITreeNode;
import de.swm.commons.mobile.client.widgets.tree.TreeChangedEvent;
import de.swm.commons.mobile.client.widgets.tree.TreeChangedHandler;
import de.swm.commons.mobile.client.widgets.tree.WideTreePanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.icon.IconResources;

public class WideTreePanelPage extends SimplePage {

	private static WideTreePanelPageUiBinder uiBinder = GWT.create(WideTreePanelPageUiBinder.class);

	interface WideTreePanelPageUiBinder extends UiBinder<Widget, WideTreePanelPage> {
	}

	@UiField
	HTMLPanel content;
	@UiField
	SimpleHeaderPanel header;
	@UiField
	WideTreePanel treePanel;

	interface ItemStyle extends CssResource {
		String specialItem();
	}

	@UiField
	ItemStyle style;

	private IconResources res;

	public WideTreePanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		res = GWT.create(IconResources.class);
		treePanel.addTreeChangedHandler(new TreeChangedHandler() {
			
			@Override
			public void onTreeChanged(TreeChangedEvent e) {
				String selectedTree = "Unknown";
				ITree tree = e.getNewRoot();
				if (tree != null) {
					ITreeNode node = tree.getNode();
					selectedTree = node.getHeader();
				}
				switch (e.getType()) {
				case BACK_EVENT:
					Utils.console("back event received, selected tree is: " + selectedTree);
					break;
				case INIT_COMPLETE:
					Utils.console("tree initialized");
					break;
				case CHILD_SELECTED:
					Utils.console("tree selected: " + selectedTree);
					break;
				default:
					break;
				}
			}
		});

		treePanel.setTree(createTree());
	}

	@Override
	public String getName() {
		return "WideTreePanel";
	}

	private ITree createTree() {
		ITreeNode root = new DefaultTreeNodeImpl("Root", null);
		ITree result = new DefaultTreeImpl(root, null);

		List<ITree> childTrees = new ArrayList<ITree>();
		for (int i = 0; i < 20; i++) {
			DefaultTreeImpl childTree = new DefaultTreeImpl(
					new DefaultTreeNodeImpl("Subtree_" + i, null, res.folder(), res.folder_bookmark()), result);
			childTrees.add(childTree);

			List<ITree> childChildTrees = new ArrayList<ITree>();
			for (int j = 0; j < 20; j++) {
				DefaultTreeImpl childChildTree = new DefaultTreeImpl(new DefaultTreeNodeImpl("Subsubtree_" + i + "_"
						+ j, null, res.folder(), res.folder_bookmark()), childTree);
				childChildTrees.add(childChildTree);

				List<ITree> leaves = new ArrayList<ITree>();
				for (int k = 0; k < 20; k++) {
					String styleName = null;
					if (k % 3 == 0) {
						styleName = style.specialItem();
					}
					DefaultTreeImpl leafTree = new DefaultTreeImpl(new DefaultTreeNodeImpl("Child_" + i + "_" + j + "_"
							+ k, styleName, res.leaf()), childChildTree);
					leaves.add(leafTree);
				}
				childChildTree.appendChildren(leaves);
			}

			childTree.appendChildren(childChildTrees);
		}
		result.appendChildren(childTrees);

		return result;
	}

}
