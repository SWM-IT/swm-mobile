package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import de.swm.commons.mobile.client.widgets.tree.*;
import de.swm.commons.mobile.client.widgets.tree.ITreeNode.ITreeNodeDisplay;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;
import de.swm.mobile.kitchensink.client.theme.icon.IconResources;

import java.util.ArrayList;
import java.util.List;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"TreePanelPage.ui.xml"})
public class TreePanelPage extends ShowcaseDetailPage {

    private static TreePanelPageUiBinder uiBinder = GWT.create(TreePanelPageUiBinder.class);


    interface TreePanelPageUiBinder extends UiBinder<Widget, TreePanelPage> {
    }

    @UiField
    HTMLPanel content;
    @UiField
	HeaderPanel header;
    @UiField
    TreePanel treePanel;

    private IconResources res;

    public TreePanelPage() {
        super(TreePanelPage.class);
        res = GWT.create(IconResources.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
        treePanel.setTree(createTree());
    }

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "Tree panel";
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
