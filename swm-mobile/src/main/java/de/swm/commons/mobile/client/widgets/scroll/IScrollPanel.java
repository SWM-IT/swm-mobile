package de.swm.commons.mobile.client.widgets.scroll;

/**
 * SWM S-IP-AN
 * User: wiesed
 * Date: 16.07.14
 * Time: 10:43
 */
public interface IScrollPanel {
	/**
	 * A scroll monitor provides callback when the scrolling is done.
	 *
	 * @param scrollMonitor
	 */
	void setScrollMonitor(IScrollMonitor scrollMonitor);

	/**
	 * Scrolls to the top.
	 */
	void setPostionToTop();

	/**
	 * Scrolls to the button.
	 */
	void setPositionToBottom();

	/**
	 * Sets the croll position
	 *
	 * @param pos the x axis pos
	 */
	void setScrollPosition(int pos);

	/**
	 * Returns the current scroll position.
	 *
	 * @return the position
	 */
	int getScrollPosition();

	/**
	 * Returns the next scroll position
	 *
	 * @return then next scroll position
	 */
	int getScrollToPosition();

	/**
	 * A value greater zero will set the offset height of the panel manually.
	 * Otherwise it will be calculated automatically.
	 *
	 * @param offsetHeight the offset height.
	 */
	void setOffsetHeight(int offsetHeight);
}
