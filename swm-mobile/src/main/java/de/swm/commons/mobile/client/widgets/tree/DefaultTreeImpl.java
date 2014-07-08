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

import java.util.ArrayList;
import java.util.List;

public class DefaultTreeImpl implements ITree {
	
	final ITreeNode node;
	ITree parent;
	final List<ITree> children = new ArrayList<ITree>();
	
	public DefaultTreeImpl(ITreeNode node, ITree parent) {
		this.node = node;
		this.parent = parent;
	}

	@Override
	public ITreeNode getNode() {
		return node;
	}

	@Override
	public List<ITree> getChildren() {
		return children;
	}

	@Override
	public ITree getParent() {
		return parent;
	}
	
	@Override
	public void setParent(ITree parent) {
		this.parent = parent;
	}

	@Override
	public void appendChildren(List<ITree> children) {
		for (ITree child : children) {
			child.setParent(this);
			this.children.add(child);
		}
	}

	@Override
	public ITree findTree(ITreeFinder tf) {
		if (tf.findTree(this)) {
			return this;
		} else {
			for (ITree child : children) {
				ITree result = child.findTree(tf);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

}
