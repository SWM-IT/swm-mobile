package de.swm.commons.mobile.client.widgets.scroll;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;
import de.swm.commons.mobile.client.widgets.toolbar.ToolbarPanel;

import java.util.Iterator;

/**
 * This Scrollpanel detects which OS it is running on and shows the SimpleScrollPanelWithScrollbar for iOS
 * and the SimpleScrollPanel for all Other OSes.
 *
 * @author karsunke.franziskus
 *         <br>
 *         copyright (C) 10.11.2014, Stadtwerke München GmbH
 */
public class AdaptiveScrollPanel extends PanelBase implements IScrollPanel {

	private IScrollPanel realPanel;

	private void createScrollPanelIfNeeded() {
		if (this.realPanel == null) {
			if (SWMMobile.getOsDetection().isIOs()) {
				ScrollPanelWithScrollbar scrollPanelWithScrollbar = new ScrollPanelWithScrollbar();
				this.getElement().getStyle().setProperty("display", "-webkit-box");
				this.realPanel = scrollPanelWithScrollbar;
			} else {
				SimpleScrollPanel scrollPanel = new SimpleScrollPanel();
				scrollPanel.getElement().getStyle().setOverflowY(Style.Overflow.HIDDEN);
				this.getElement().getStyle().setOverflowY(Style.Overflow.SCROLL);
				this.realPanel = scrollPanel;
			}
			super.add((PanelBase) realPanel);
			this.getElement().getStyle().setProperty("WebkitBoxFlex", "1");
			this.setHeight("100%");
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.createScrollPanelIfNeeded();
	}

	@Override
	public void setScrollMonitor(IScrollMonitor scrollMonitor) {
		createScrollPanelIfNeeded();
		this.realPanel.setScrollMonitor(scrollMonitor);
	}

	@Override
	public void setPostionToTop() {
		createScrollPanelIfNeeded();
		this.realPanel.setPostionToTop();
	}

	@Override
	public void setPositionToBottom() {
		createScrollPanelIfNeeded();
		this.realPanel.setPositionToBottom();
	}

	@Override
	public void setScrollPosition(int pos) {
		createScrollPanelIfNeeded();
		this.realPanel.setScrollPosition(pos);
	}

	@Override
	public int getScrollPosition() {
		createScrollPanelIfNeeded();
		return this.realPanel.getScrollPosition();
	}

	@Override
	public int getScrollToPosition() {
		createScrollPanelIfNeeded();
		return this.realPanel.getScrollToPosition();
	}

	@Override
	public void setOffsetHeight(int offsetHeight) {
		createScrollPanelIfNeeded();
		this.realPanel.setOffsetHeight(offsetHeight);
	}

	@Override
	public void add(Widget w) {
		createScrollPanelIfNeeded();
		((PanelBase) realPanel).add(w);
	}

	@Override
	public void clear() {
		createScrollPanelIfNeeded();
		((PanelBase) realPanel).clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		createScrollPanelIfNeeded();
		return ((PanelBase) realPanel).iterator();
	}

	@Override
	public boolean remove(Widget widget) {
		createScrollPanelIfNeeded();
		return ((PanelBase) realPanel).remove(widget);
	}
}
