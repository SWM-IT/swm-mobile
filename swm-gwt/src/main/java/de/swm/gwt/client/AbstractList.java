package de.swm.gwt.client;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.*;
import de.swm.gwt.client.authorization.IAccessRight;
import de.swm.gwt.client.authorization.IRightsDependentUIUpdater;
import de.swm.gwt.client.enums.I18NEnumRenderer;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.interfaces.IModelData;

import java.util.Date;

/**
 * Abstrakte Basisklasse fuer Listen.
 *
 * @param <D> Klasse der DTOs.
 * @author Steiner.Christian<br>
 *         Marko Krajnc<br>
 *         copyright (C) 2014 SWM Services GmbH
 */
public abstract class AbstractList<D extends IModelData> extends Composite implements IList<D> {

	/**
	 * Diese Klasse ermöglich verwendung von unterklassen von DataGrid.
	 *
	 * @author Marko Krajnc <br> copyright (C) 2010, SWM Services GmbH
	 */
	public interface DataGridFactory {
		/**
		 * Create new instance of Datagrid object.
		 *
		 * @param pageSize    the page size
		 * @param resources   the resources to use for this widget
		 * @param keyProvider an instance of ProvidesKey<T>, or null if the record
		 *                    object should act as its own key
		 * @param <T>         the data type of each row
		 * @return New Datagrid instance.
		 */
		public <T> DataGrid<T> newDataGrid(int pageSize,
		                                   com.google.gwt.user.cellview.client.DataGrid.Resources resources,
		                                   com.google.gwt.view.client.ProvidesKey<T> keyProvider);
	}

	private static final int PAGE_SIZE = 10;
	private static final String NO_DATA_AVAILABLE = "no data available.";


	private final AsyncDataProvider<D> dataStore;
	private final String i18ValueForEmptyTable;
	private final IRightsDependentUIUpdater rightsDependentUIUpdater;
	private final DataGridFactory dataGridFactory;

	private SingleSelectionModel<D> selectionModel;

	private IListPresenter<D> presenter;
	private DataGrid<D> dataGrid;
	private SimplePager pager;


	/**
	 * Constructor for the AbstractList.
	 *
	 * @param dataStore provides the data shown in the dataGrid.
	 */
	public AbstractList(AsyncDataProvider<D> dataStore) {
		this(dataStore, null, NO_DATA_AVAILABLE);
	}


	/**
	 * Constructor for the AbstractList.
	 *
	 * @param dataStore                provides the data shown in the dataGrid.
	 * @param rightsDependentUiUpdater updates the ui components dependent on the user's access rights.
	 */
	public AbstractList(AsyncDataProvider<D> dataStore, IRightsDependentUIUpdater rightsDependentUiUpdater) {
		this(dataStore, rightsDependentUiUpdater, NO_DATA_AVAILABLE);
	}


	/**
	 * Constructor for the AbstractList.
	 *
	 * @param dataStore                provides the data shown in the dataGrid.
	 * @param rightsDependentUiUpdater updates the ui components dependent on the user's access rights.
	 * @param i18ValueForEmptyTable    text wenn die Tabelle leer ist
	 */
	public AbstractList(AsyncDataProvider<D> dataStore, IRightsDependentUIUpdater rightsDependentUiUpdater,
	                    String i18ValueForEmptyTable) {
		this(dataStore, rightsDependentUiUpdater, i18ValueForEmptyTable, null);
	}

	/**
	 * Constructor for the AbstractList.
	 *
	 * @param dataStore                provides the data shown in the dataGrid.
	 * @param rightsDependentUiUpdater updates the ui components dependent on the user's access rights.
	 * @param i18ValueForEmptyTable    text wenn die Tabelle leer ist
	 * @param dataGridFactory          factory for DataGrid object.
	 */
	public AbstractList(AsyncDataProvider<D> dataStore, IRightsDependentUIUpdater rightsDependentUiUpdater,
	                    String i18ValueForEmptyTable, DataGridFactory dataGridFactory) {
		this.rightsDependentUIUpdater = rightsDependentUiUpdater;
		this.dataStore = dataStore;
		this.i18ValueForEmptyTable = i18ValueForEmptyTable;
		this.dataGridFactory = dataGridFactory;
	}

	/**
	 * Initialisiert den List controller mit Plain GWT Data Grid. Bei nicht Plain-GWT implementierungen
	 * sollte die init method enicht aufgerufen werden.
	 *
	 * @return initialisiert ein plain GWT Data grid.
	 */
	public InitializationResult<D> init() {

		initDataGrid();
		return new InitializationResult<D>() {
			@Override
			public DataGrid<D> grid() {
				return dataGrid;
			}

			@Override
			public SimplePager pager() {
				return pager;
			}
		};
	}


	/**
	 * initializes the datagrid columns.
	 *
	 * @param dataGrid the grid.
	 */
	protected abstract void initTableColumns(DataGrid<D> dataGrid);


	/**
	 * Creates the widget shown when the data grid is empty. override to show specific messages.
	 *
	 * @return the widget.
	 */
	protected Label createEmptyTableWidget() {
		return new Label(i18ValueForEmptyTable);
	}

	/**
	 * setter.     	 *
	 *
	 * @param presenter the presenter for the list.
	 */
	public void setPresenter(IListPresenter<D> presenter) {
		this.presenter = presenter;
	}


	/**
	 * Initializes the DataGrid. Must be called in the constructor, before uiBinder.createAndBindUi(Widget).
	 */
	protected void initDataGrid() {
		DataGrid.Resources dataGridResources = (SWMGwt.getTheme() != null) ? SWMGwt.getTheme().getCssBundle() : null;
		if (dataGridFactory != null) {
			dataGrid = dataGridFactory.newDataGrid(getPageSize(), dataGridResources, dataStore.getKeyProvider());
		} else {
			if (dataGridResources != null) {
				dataGrid = new DataGrid<D>(getPageSize(), dataGridResources, dataStore.getKeyProvider());
			} else {
				dataGrid = new DataGrid<D>(getPageSize(), dataStore.getKeyProvider());
			}
			if (SWMGwt.getTheme() != null) {
				dataGrid.addStyleName(SWMGwt.getTheme().getCssBundle().getGridCss().defaultDataGrid());
			}
		}

		selectionModel = new SingleSelectionModel<D>(dataStore.getKeyProvider());
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				D selectedObject = selectionModel.getSelectedObject();
				onRowSelected(selectedObject);

			}
		});
		dataGrid.setSelectionModel(selectionModel);


		dataGrid.setEmptyTableWidget(createEmptyTableWidget());

		ColumnSortEvent.AsyncHandler sortHandler = new ColumnSortEvent.AsyncHandler(dataGrid);
		dataGrid.addColumnSortHandler(sortHandler);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
		if (SWMGwt.getTheme() != null) {
			pager.addStyleName(SWMGwt.getTheme().getCssBundle().getGridCss().defaultSimplePager());
		}
		pager.setDisplay(dataGrid);


		initTableColumns(dataGrid);

	}


	/**
	 * registers access rights to UIObjects.
	 * <p/>
	 * Registrierte UI-Objekte werden entsprechend den Ausfuehrungsrechten aktiviert oder deaktiviert.
	 *
	 * @param uiObject       das UI-Objekt
	 * @param executionRight die Ausfuehrungsrechte.
	 */
	protected void registerAccessRight(UIObject uiObject, IAccessRight executionRight) {
		if (this.rightsDependentUIUpdater != null) {
			rightsDependentUIUpdater.register(uiObject, executionRight);
		}
	}


	/**
	 * Returns the default page size used in this grid.
	 *
	 * @return the page size
	 */
	protected int getPageSize() {
		return PAGE_SIZE;
	}


	/**
	 * Rendert das Formular in die entsprechende Location.
	 *
	 * @param location die Location, in die das Formular gerendert werden soll.
	 */
	public void render(ILocation location) {
		this.getWidget().setVisible(true);
		location.add(this.getWidget());
		location.render();
	}

	/**
	 * Entfernt die Liste aus der Location.
	 *
	 * @param location Location, aus der die Liste entfernt werden soll
	 */
	public void remove(ILocation location) {
		location.remove(this.getWidget());
	}


	/**
	 * reloads the list data.
	 */
	public void onLoad() {
		if (dataStore.getDataDisplays().isEmpty()) {
			dataStore.addDataDisplay(dataGrid);
			dataGrid.setVisibleRangeAndClearData(new Range(0, dataGrid.getPageSize()), true);
		}
		if (rightsDependentUIUpdater != null) {
			rightsDependentUIUpdater.updateComponents();
		}
		RangeChangeEvent.fire(dataGrid, dataGrid.getVisibleRange());
	}


	/**
	 * selects a row in the datagrid.
	 *
	 * @param toSelect the object/row to select
	 */
	public void selectRow(D toSelect) {
		selectionModel.setSelected(toSelect, true);
	}


	/**
	 * handles the selection of a datagrid row, informs the presenter.
	 *
	 * @param selectedRow the selected object/row
	 */
	protected void onRowSelected(D selectedRow) {
		if (getPresenter() != null) {
			getPresenter().onRowSelected(selectedRow);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Widget getWidget() {
		return this;
	}


	/**
	 * Getter for subclasses.
	 *
	 * @return the selectionModel.
	 */
	protected SingleSelectionModel<D> getSelectionModel() {
		return selectionModel;
	}


	/**
	 * Setter for subclasses.
	 *
	 * @param selectionModel sets a new selectionModel
	 */
	protected void setSelectionModel(SingleSelectionModel<D> selectionModel) {
		this.selectionModel = selectionModel;
	}


	/**
	 * Getter for subclasses.
	 *
	 * @return the IListPresenter.
	 */
	protected IListPresenter<D> getPresenter() {
		return presenter;
	}


	/**
	 * Getter for subclasses.
	 *
	 * @return the DataGrid
	 */
	protected DataGrid<D> getDataGrid() {
		return dataGrid;
	}


	/**
	 * Setter for subclasses.
	 *
	 * @param dataGrid sets a new DataGrid
	 */
	protected void setDataGrid(DataGrid<D> dataGrid) {
		this.dataGrid = dataGrid;
	}


	/**
	 * Getter for subclasses.
	 *
	 * @return the Pager
	 */
	protected SimplePager getPager() {
		return pager;
	}


	/**
	 * Setter for subclasses.
	 *
	 * @param pager sets a new Pager
	 */
	protected void setPager(SimplePager pager) {
		this.pager = pager;
	}

	/**
	 * Getter for subclasses.
	 *
	 * @return the AsyncDataProvider.
	 */
	protected AsyncDataProvider<D> getDataStore() {
		return dataStore;
	}


	/**
	 * Getter for subclasses.
	 *
	 * @return the IRightsDependentUIUpdater.
	 */
	protected IRightsDependentUIUpdater getAccessRightsChecker() {
		return rightsDependentUIUpdater;
	}

	/**
	 * Erzeugt eine Tabellenspalte vom Typ Text.
	 *
	 * @param propertyDTOConstant die Kontante im DTO mit der ein Attribut referenziert werden kann.
	 * @return die Spalte.
	 */
	protected Column<D, String> createCellTypeText(final String propertyDTOConstant) {
		// Last name
		Column<D, String> col1 = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				// Nicht so cool: object kann null sein, wenn man ueber den previous page Button zurueckblaettert.
				// GWT Issue 7030 https://code.google.com/p/google-web-toolkit/issues/detail?id=7030
				if (object != null) {
					final Object o = object.get(propertyDTOConstant);
					if (o != null) {
						return String.valueOf(o);
					}
				}
				return null;
			}
		};

		return col1;
	}

	/**
	 * Erzeugt eine Tabellenspalte vom Typ Text.
	 *
	 * @param propertyDTOConstant die Kontante im DTO mit der ein Attribut referenziert werden kann.
	 * @param renderer            der renderer fuer das jeweilige Enum
	 * @return die Spalte.
	 */
	protected Column<D, String> createCellTypeEnum(final String propertyDTOConstant,
	                                               final I18NEnumRenderer renderer) {
		// Last name
		Column<D, String> col1 = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				// Nicht so cool: object kann null sein, wenn man ueber den previous page Button zurueckblaettert.
				// GWT Issue 7030 https://code.google.com/p/google-web-toolkit/issues/detail?id=7030
				if (object != null) {
					final Object o = object.get(propertyDTOConstant);
					if (o != null && o instanceof I18NEnum) {
						return renderer.render((I18NEnum) o);
					}
				}
				return null;
			}
		};

		return col1;
	}

	/**
	 * Erzeugt eine Tabellenspalte vom Typ Text.
	 *
	 * @param propertyDTOConstant die Kontante im DTO mit der ein Attribut referenziert werden kann.
	 * @return die Spalte.
	 */
	protected Column<D, String> createCellTypeBoolean(final String propertyDTOConstant) {
		// Last name
		Column<D, String> col1 = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				// Nicht so cool: object kann null sein, wenn man ueber den previous page Button zurueckblaettert.
				// GWT Issue 7030 https://code.google.com/p/google-web-toolkit/issues/detail?id=7030
				if (object != null) {
					final Boolean o = object.get(propertyDTOConstant);
					if (o != null && o) {
						return "Ja";
					}
				}
				return "Nein";
			}
		};

		return col1;
	}

	/**
	 * Erzeugt eine Tabellenspalte vom Typ Date.
	 *
	 * @param propertyDTOConstant die Kontante im DTO mit der ein Attribut referenziert werden kann.
	 * @param format              z.B. DateTimeFormat.PredefinedFormat.DATE_SHORT,
	 *                            DateTimeFormat.PredefinedFormat.TIME_SHORT,
	 *                            DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT
	 * @return die Spalte.
	 */
	protected Column<D, Date> createCellTypeDate(final String propertyDTOConstant,
	                                             DateTimeFormat.PredefinedFormat format) {
		// Last name
		Column<D, Date> col1 = new Column<D, Date>(new DateCell(
				DateTimeFormat.getFormat(format))) {
			@Override
			public Date getValue(D object) {
				// Nicht so cool: object kann null sein, wenn man ueber den previous page Button zurueckblaettert.
				// GWT Issue 7030 https://code.google.com/p/google-web-toolkit/issues/detail?id=7030
				if (object != null) {
					final Date date = object.get(propertyDTOConstant);
					if (date != null) {
						return date;
					}
				}
				return null;
			}
		};

		return col1;
	}


	/**
	 * Ergebniss der Initialisierung.
	 *
	 * @param <D> Klasse der DTOs.
	 * @author Daniel.Wiese<br>
	 *         copyright 2014 SWM Service GmbH
	 */
	public static interface InitializationResult<D extends IModelData> {
		/**
		 * Liefert das Data grid.
		 *
		 * @return das data grid
		 */
		DataGrid<D> grid();

		/**
		 * Den mit dem Data Grid verbundenen Pager.
		 *
		 * @return der pager.
		 */
		SimplePager pager();
	}
}
