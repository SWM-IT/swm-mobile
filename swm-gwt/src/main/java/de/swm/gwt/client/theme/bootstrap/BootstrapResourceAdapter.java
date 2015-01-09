package de.swm.gwt.client.theme.bootstrap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.DataGrid;

/**
 * Resources/Styles to remove the GWT styling of the tables!
 *
 * @author GwtBootstrap3, Marko Krajnc <br>
 *         copyright (C) 2013-14, GwtBootstrap3
 */
public class BootstrapResourceAdapter implements DataGrid.Resources {
	private static DataGrid.Resources defaultAdapter = null;

	/**
	 * Returns resource adapter singleton.
	 *
	 * @return .
	 */
	public static DataGrid.Resources getDefault() {
		if (defaultAdapter == null) {
			final DataGrid.Resources dataGridResources = GWT.create(DataGrid.Resources.class);
			defaultAdapter = new BootstrapResourceAdapter(dataGridResources);
		}
		return defaultAdapter;
	}

	private final DataGrid.Resources resources;
	private final BootstrapStyleAdapter style;

	/**
	 * Creates new resource adapter.
	 *
	 * @param resources original resources.
	 */
	public BootstrapResourceAdapter(final DataGrid.Resources resources) {
		this.resources = resources;
		this.style = new BootstrapStyleAdapter();
	}

	@Override
	public ImageResource dataGridLoading() {
		return resources.dataGridLoading();
	}

	@Override
	public ImageResource dataGridSortAscending() {
		return resources.dataGridSortAscending();
	}

	@Override
	public ImageResource dataGridSortDescending() {
		return resources.dataGridSortDescending();
	}

	@Override
	public DataGrid.Style dataGridStyle() {
		return style;
	}
}
