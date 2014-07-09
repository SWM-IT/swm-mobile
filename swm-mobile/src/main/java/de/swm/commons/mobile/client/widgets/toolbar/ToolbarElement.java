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
package de.swm.commons.mobile.client.widgets.toolbar;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.SWMMobileWidgetBase;

import java.util.Iterator;


/**
 * A {@link ToolbarPanel} contains different {@link ToolbarElement}s. e.g.:
 * <p/>
 * <pre>
 * <m:ToolbarElement>
 * 				<m:ToolbarHeader headerText='Item 1' headerImage='{icon.tab0}' highlightImage='{icon.tab0selected}' />
 * 				<m:ToolbarContent>
 * 					<g:HTMLPanel styleName="Toolbar-Content">
 * 						<g:Label>Tab1 Content</g:Label>
 * 					</g:HTMLPanel>
 * 				</m:ToolbarContent>
 * 			</m:ToolbarElement>
 * </pre>
 */
public class ToolbarElement extends SWMMobileWidgetBase implements HasWidgets {

	private ToolbarHeader header;
	private ToolbarContent content;
	private ToolbarPanel owner;
	/**
	 * Every element in the toolbar has knowledge obout his index. *
	 */
	private int myIndex;


	/**
	 * Default constructor.
	 */
	public ToolbarElement() {
	}


	public ToolbarHeader getHeader() {
		return header;
	}


	public ToolbarContent getContent() {
		return content;
	}


	@Override
	public void add(Widget w) {
		if (this.getWidget() == null) {
			assert w instanceof ToolbarHeader : "Expected a ToolbarHeader widget in a ToolbarElement";
			header = (ToolbarHeader) w;
			header.setOuterToolebarElement(this);
			initWidget(header);
			header.selectImage(false);
		} else {
			assert w instanceof ToolbarContent : "Expected a ToolbarContent widget in a ToolbarElement";
			content = (ToolbarContent) w;
		}
	}


	@Override
	public void clear() {
	}


	@Override
	public Iterator<Widget> iterator() {
		return null;
	}


	@Override
	public boolean remove(Widget w) {
		return false;
	}

	/**
	 * Will unselect all elements placed in one ToolbarPanel.
	 */
	public void unselectAllElements() {
		this.owner.unselectAllElements();
	}

	/**
	 * Will select/unselect only the current element.
	 *
	 * @param selected    true if selected
	 * @param fireUpdates
	 */
	public void setSelected(boolean selected, boolean fireUpdates) {
		this.header.setSelected(selected);
		if (fireUpdates) {
			this.owner.fireSelectionChangeUpdate(myIndex);
		}
	}

	/**
	 * Will set the owning panel of this element
	 *
	 * @param owner    the owner of this element
	 * @param indexPos the index position where this element is placed at (owners index)
	 */
	public void setOwningPanel(ToolbarPanel owner, int indexPos) {
		this.owner = owner;
		this.myIndex = indexPos;
	}
}
