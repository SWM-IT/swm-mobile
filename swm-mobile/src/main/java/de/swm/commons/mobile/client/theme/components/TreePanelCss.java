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
package de.swm.commons.mobile.client.theme.components;

import com.google.gwt.resources.client.CssResource;

/**
 * Represents a css resource for a {@link TreePanel} or a {@link WideTreePanel}.
 * 
 */
public interface TreePanelCss extends CssResource {

	@CssResource.ClassName("treePanel")
	String getTreePanel();

	@CssResource.ClassName("treeBreadcrumbPanel")
	String getTreeBreadcrumbPanel();

	@CssResource.ClassName("treeBreadcrumbButton")
	String getTreeBreadcrumbButton();

	@CssResource.ClassName("treeBreadcrumbLabel")
	String getTreeBreadcrumbLabel();

	@CssResource.ClassName("treeContainer")
	String getTreeContainer();

	@CssResource.ClassName("treeList")
	String getTreeList();

	@CssResource.ClassName("treeLeftList")
	String getTreeLeftList();

	@CssResource.ClassName("treeMiddleList")
	String getTreeMiddleList();

	@CssResource.ClassName("treeRightList")
	String getTreeRightList();

	@CssResource.ClassName("wideTreeContainer")
	String getWideTreeContainer();

	@CssResource.ClassName("wideTreeList")
	String getWideTreeList();

	@CssResource.ClassName("wideTreeList0")
	String getWideTreeList0();

	@CssResource.ClassName("wideTreeList1")
	String getWideTreeList1();

	@CssResource.ClassName("wideTreeList2")
	String getWideTreeList2();

	@CssResource.ClassName("wideTreeList3")
	String getWideTreeList3();

	@CssResource.ClassName("selectedItem")
	String getSelectedItem();

	@CssResource.ClassName("slide")
	String getSlide();
	
	@CssResource.ClassName("left")
	String getLeft();
	
	@CssResource.ClassName("right")
	String getRight();
		
	@CssResource.ClassName("wideLeft")
	String getWideLeft();
	
	@CssResource.ClassName("wideRight")
	String getWideRight();
}
