package de.swm.mobile.kitchensink.client.showcase.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import de.swm.commons.mobile.client.widgets.tree.DefaultTreeImpl;
import de.swm.commons.mobile.client.widgets.tree.DefaultTreeNodeImpl;
import de.swm.commons.mobile.client.widgets.tree.ITree;
import de.swm.commons.mobile.client.widgets.tree.ITreeNode;
import de.swm.commons.mobile.client.widgets.tree.TreePanel;
import de.swm.commons.mobile.client.widgets.tree.ITreeNode.ITreeNodeDisplay;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.icon.IconResources;

public class TreePanelPage extends SimplePage {

	private static TreePanelPageUiBinder uiBinder = GWT.create(TreePanelPageUiBinder.class);

	interface TreePanelPageUiBinder extends UiBinder<Widget, TreePanelPage> {}

	@UiField HTMLPanel content;
	@UiField SimpleHeaderPanel header;
	@UiField TreePanel treePanel;
	
	private IconResources res;

	public TreePanelPage() {
		res = GWT.create(IconResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		treePanel.setTree(createTree());
	}

	@Override
	public String getName() {
		return "TreePanel";
	}

	private class CustomNodeDisplay extends VerticalPanel implements ITreeNodeDisplay {
		
		Label header = new Label("-");
		
		public CustomNodeDisplay() {
			add(new HTML("<i>Custom Display</i>"));
			add(header);
		}

		@Override
		public void setNode(ITreeNode node) {
			header.setText(node.getHeader());
		}
		
	}

	private ITree createTree() {
		ITreeNode root = new DefaultTreeNodeImpl("Root", null);
		ITree result = new DefaultTreeImpl(root, null);
		
		ITreeNodeDisplay nodeDisplay = new CustomNodeDisplay();

		List<ITree> childTrees = new ArrayList<ITree>();
		for (int i = 0; i < 20; i++) {
			DefaultTreeImpl childTree = new DefaultTreeImpl(new DefaultTreeNodeImpl("Subtree_" + i, null, res.folder()), result);
			childTrees.add(childTree);
			
			List<ITree> childChildTrees = new ArrayList<ITree>();
			for (int j = 0; j < 20; j++) {
				DefaultTreeImpl childChildTree = new DefaultTreeImpl(new DefaultTreeNodeImpl("Subsubtree_" + i + "_" + j, null, res.folder()), childTree);
				childChildTrees.add(childChildTree);
				
				List<ITree> leaves = new ArrayList<ITree>();
				for (int k = 0; k < 20; k++) {
					DefaultTreeNodeImpl node = new DefaultTreeNodeImpl("Child_" + i + "_" + j + "_" + k, null, res.leaf());
					node.setDisplay(nodeDisplay);
					DefaultTreeImpl leafTree = new DefaultTreeImpl(node, childChildTree);
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
