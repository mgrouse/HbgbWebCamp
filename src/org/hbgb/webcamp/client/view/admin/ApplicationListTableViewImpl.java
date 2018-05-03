/*
 * This is an unpublished work protected by the United States copyright laws and
 * is proprietary to Michael A. Grouse. Disclosure, copying, reproduction,
 * merger translation, modification, enhancement or use by anyone other than
 * authorized employees or licensees of Michael A. Grouse without prior written
 * consent of Michael A. Grouse is prohibited.
 *
 * Copyright (C) 1992 - 2017 Michael A. Grouse, All Rights Reserved.
 *
 * This copyright notice should not be construed as evidence of publication.
 */
package org.hbgb.webcamp.client.view.admin;

import java.util.ArrayList;
import java.util.Comparator;

import org.hbgb.webcamp.client.view.AbstractView;
import org.hbgb.webcamp.shared.ApplicationRow;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author Michael
 *
 */
public class ApplicationListTableViewImpl extends AbstractView implements ApplicationListTableView
{
	static final String EMPTY_TABLE_MSG = "No Data to Display";

	@UiTemplate(value = "ApplicationListTableView.ui.xml")
	static interface ApplicationListTableViewUiBinder
			extends UiBinder<Widget, ApplicationListTableViewImpl>
	{
	}

	private static ApplicationListTableViewUiBinder uiBinder = GWT
			.create(ApplicationListTableViewUiBinder.class);

	@UiField()
	DataGrid<ApplicationRow> appTable;

	private Presenter presenter;

	// ListDataProvider<ApplicationRow> dataProvider;

	private final SelectionModel<ApplicationRow> selectionModel;
	private ListHandler<ApplicationRow> sortHandler;

	public ApplicationListTableViewImpl()
	{
		appTable = new DataGrid<>(ApplicationRow.KEY_PROVIDER);

		initWidget(uiBinder.createAndBindUi(this));

		selectionModel = new SingleSelectionModel<>(ApplicationRow.KEY_PROVIDER);

		sortHandler = new ListHandler<>(new ArrayList<ApplicationRow>());
	}

	@Override
	public void setPresenter(Presenter p)
	{
		presenter = p;
	}

	@Override
	public void clear()
	{
		// start loading pop up
	}

	@Override
	public void setRowData(ListDataProvider<ApplicationRow> dataProvider)
	{
		sortHandler.setList(dataProvider.getList());

		// Attach a column sort handler to the ListData to sort the list.
		setUpDataGrid();

		dataProvider.addDataDisplay(appTable);
	}

	private void setUpDataGrid()
	{
		appTable.setWidth("800px");
		appTable.setHeight("800px");
		appTable.setAutoHeaderRefreshDisabled(true);

		// blank message
		appTable.setEmptyTableWidget(new Label("EMPTY_TABLE_MSG"));

		// selection model
		appTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ApplicationRow> createCheckboxManager());

		appTable.addColumnSortHandler(sortHandler);

		// Headers

		// Columns ------------
		// status

		// firstName
		TextColumn<ApplicationRow> firstNameColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return row.getFirstName();
			}
		};

		firstNameColumn.setSortable(true);
		sortHandler.setComparator(firstNameColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return r1.getFirstName().compareTo(r2.getFirstName());
			}
		});

		appTable.addColumn(firstNameColumn, "First Name");

		firstNameColumn.setFieldUpdater(new FieldUpdater<ApplicationRow, String>()
		{
			@Override
			public void update(int index, ApplicationRow row, String value)
			{
				// Called when the user changes the value.
				row.setFirstName(value);
				presenter.onRowEdit();
			}
		});

		appTable.setColumnWidth(firstNameColumn, 20, Unit.PCT);

		// lastName
		// playaName
		// email
		// committee
		// diet
		// hasTicket
		// isET
		// isStrike
		// hasRV
		// hasStructure
	}

}
