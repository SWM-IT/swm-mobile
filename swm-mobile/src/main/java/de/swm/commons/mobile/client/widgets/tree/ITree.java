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

import java.util.List;

public interface ITree {
	
	ITreeNode getNode();

	List<ITree> getChildren();

	ITree getParent();

	void setParent(ITree parent);

	void appendChildren(List<ITree> children);

	/**
	 * Utility interface to specify logic for finding a specific (sub-)tree.
	 */
	interface ITreeFinder {
		/**
		 * Utility method to decide if given tree is the searched one.
		 * 
		 * @param t Given tree, will be set by search routine.
		 * @return <code>true</code> if the given tree matches the search criteria, <code>false</code> otherwise. 
		 */
		boolean findTree(ITree t);
	}

	/**
	 * Find a (sub-)tree with the help of an application specific ITreeFinder interface.
	 * 
	 * @param tf Given implementation of specific ITreeFinder interface.
	 * @return First tree found in a depth-first tree search.
	 */
	ITree findTree(ITreeFinder tf);
	
}
