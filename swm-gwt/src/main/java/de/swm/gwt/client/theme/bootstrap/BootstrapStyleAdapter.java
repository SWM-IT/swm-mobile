package de.swm.gwt.client.theme.bootstrap;

import com.google.gwt.user.cellview.client.DataGrid;

/**
 * Resources/Styles to remove the GWT styling of the tables!
 *
 * @author GwtBootstrap3, Marko Krajnc <br>
 *         copyright (C) 2013-14, GwtBootstrap3
 */
public class BootstrapStyleAdapter  implements DataGrid.Style {
	private static final String B = "gwtb3-";
	private static final String DUMMY = B + "d";

	@Override
	public boolean ensureInjected() {
		return true;
	}

	@Override
	public String getText() {
		return B;
	}

	@Override
	public String getName() {
		return B;
	}

	@Override
	public String dataGridCell() {
		return B + "cell";
	}

	@Override
	public String dataGridEvenRow() {
		return "even";
	}

	@Override
	public String dataGridEvenRowCell() {
		return DUMMY;
	}

	@Override
	public String dataGridFirstColumn() {
		return DUMMY; // Bootstrap3 uses "smart selectors"
	}

	@Override
	public String dataGridFirstColumnFooter() {
		return DUMMY;
	}

	@Override
	public String dataGridFirstColumnHeader() {
		return DUMMY;
	}

	@Override
	public String dataGridFooter() {
		return DUMMY;
	}

	@Override
	public String dataGridHeader() {
		return B + "header";
	}

	@Override
	public String dataGridHoveredRow() {
		return "active";
	}

	@Override
	public String dataGridHoveredRowCell() {
		return "active";
	}

	@Override
	public String dataGridKeyboardSelectedCell() {
		return DUMMY;
	}

	@Override
	public String dataGridKeyboardSelectedRow() {
		return DUMMY;
	}

	@Override
	public String dataGridKeyboardSelectedRowCell() {
		return DUMMY;
	}

	@Override
	public String dataGridLastColumn() {
		return DUMMY;
	}

	@Override
	public String dataGridLastColumnFooter() {
		return DUMMY;
	}

	@Override
	public String dataGridLastColumnHeader() {
		return DUMMY;
	}

	@Override
	public String dataGridOddRow() {
		return "odd";
	}

	@Override
	public String dataGridOddRowCell() {
		return DUMMY;
	}

	@Override
	public String dataGridSelectedRow() {
		return "info";
	}

	@Override
	public String dataGridSelectedRowCell() {
		return DUMMY;
	}

	@Override
	public String dataGridSortableHeader() {
		return DUMMY;
	}

	@Override
	public String dataGridSortedHeaderAscending() {
		return DUMMY;
	}

	@Override
	public String dataGridSortedHeaderDescending() {
		return DUMMY;
	}

	@Override
	public String dataGridWidget() {
		return "table";
	}
}