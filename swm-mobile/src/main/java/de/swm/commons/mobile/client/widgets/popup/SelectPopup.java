package de.swm.commons.mobile.client.widgets.popup;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.event.SelectionChangedHandler;
import de.swm.commons.mobile.client.widgets.SelectItem;
import de.swm.commons.mobile.client.widgets.SelectPanel;

public class SelectPopup extends SimplePopup {

	private String selectedKey;
	private final SelectPanel sPanel;

	public SelectPopup() {
		super();
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().selectPopup());
		sPanel = new SelectPanel();
		sPanel.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent e) {
				int selectedIndex = e.getSelection();
				if (selectedIndex >= 0) {
					SelectItem sItem = sPanel.getItem(selectedIndex);
					if (sItem != null) {
						selectedKey = sItem.getKey();
					}
				}
				hide();
			}
		});
		setWidget(sPanel);
	}

	@Override
	public void clear() {
		sPanel.clear();
		selectedKey = null;
	}

	public void addItem(String text, String key, boolean selected) {
		SelectItem sItem = new SelectItem(text, key, selected);
		sPanel.add(sItem);
		if (selected) {
			selectedKey = key;
		}
	}

	public String getSelectedKey() {
		return selectedKey;
	}

	/**
	 * Removes the current selection.
	 */
	public void clearSelection() {
		selectedKey = null;
		sPanel.clearSelection();
	}
}
