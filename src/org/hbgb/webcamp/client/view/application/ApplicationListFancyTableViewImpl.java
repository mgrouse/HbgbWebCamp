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
package org.hbgb.webcamp.client.view.application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hbgb.webcamp.client.view.AbstractView;
import org.hbgb.webcamp.client.widget.LoadingPopup;
import org.hbgb.webcamp.shared.ApplicationRow;
import org.hbgb.webcamp.shared.Utils;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.Circle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author Michael
 *
 */
public class ApplicationListFancyTableViewImpl extends AbstractView
		implements ApplicationFancyListTableView
{
	static final String EMPTY_TABLE_MSG = "No Data to Display";

	@UiTemplate(value = "ApplicationListFancyTableView.ui.xml")
	static interface ApplicationListTableViewUiBinder
			extends UiBinder<Widget, ApplicationListFancyTableViewImpl>
	{
	}

	private static ApplicationListTableViewUiBinder uiBinder = GWT
			.create(ApplicationListTableViewUiBinder.class);

	@UiField
	TextBox totalApps;

	@UiField
	TextBox newApps;

	@UiField
	TextBox acceptedApps;

	@UiField
	TextBox paidApps;

	@UiField
	TextBox ticketApps;

	@UiField
	TextBox faerieApps;

	@UiField
	TextBox healerApps;

	@UiField
	TextBox infraApps;

	@UiField
	TextBox kitchenApps;

	@UiField
	ListBox yearListBox;

	@UiField
	Button refreshButton;

	@UiField()
	DataGrid<ApplicationRow> appTable;

	private final SelectionModel<ApplicationRow> selectionModel;
	private ListHandler<ApplicationRow> sortHandler;

	private Presenter presenter;

	private LoadingPopup loadPop;

	public ApplicationListFancyTableViewImpl()
	{
		initWidget(uiBinder.createAndBindUi(this));

		loadPop = new LoadingPopup();

		// Selection Model
		selectionModel = new SingleSelectionModel<>(ApplicationRow.KEY_PROVIDER);

		appTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ApplicationRow> createCheckboxManager());

		// Sort Handler
		sortHandler = new ListHandler<>(new ArrayList<ApplicationRow>());
		appTable.addColumnSortHandler(sortHandler);

		appTable.setAutoHeaderRefreshDisabled(true);

		// blank message
		appTable.setEmptyTableWidget(new Label(EMPTY_TABLE_MSG));

		addColumnsToDataGrid();

		appTable.setWidth("100%");
		// does the table have scroll bars?
		// how to be able to make the table occupy all room necessary/avail
		appTable.setHeight("300px");

		yearListBox.addItem("2018");
		yearListBox.addItem("2017");
	}

	@Override
	public void setPresenter(Presenter p)
	{
		presenter = p;
	}

	@Override
	public void clear()
	{
		setTotalApps("0");
		setNewApps("0");
		setAcceptedApps("0");
		setTicketApps("0");
		setPaidApps("0");

		setFaerieApps("0");
		setHealerApps("0");
		setInfraApps("0");
		setKitchenApps("0");

		// remove rows from table?

	}

	@Override
	public int getYear()
	{
		return Integer.valueOf(yearListBox.getSelectedValue());
	}

	@UiHandler(value = { "refreshButton" })
	void onRefreshButtonClicked(ClickEvent event)
	{
		if ((presenter != null))
		{
			loadPop.go();
			refreshButton.setEnabled(false);
			presenter.onRefreshButtonClicked();
		}
	}

	@Override
	public void setRowData(ListDataProvider<ApplicationRow> dataProvider)
	{
		setTotalFields(dataProvider.getList());
		sortHandler.setList(dataProvider.getList());

		dataProvider.addDataDisplay(appTable);

		// appTable.setRowCount(dataProvider.getList().size(), true);
		// appTable.setRowData(0, dataProvider.getList());

		refreshButton.setEnabled(true);
		loadPop.stop();
	}

	private void setTotalFields(List<ApplicationRow> rows)
	{
		Integer kitchen = 0;
		Integer infra = 0;
		Integer healer = 0;
		Integer faerie = 0;
		Integer unAssigned = 0;

		Integer newApps = 0;
		Integer accepted = 0;
		Integer acceptedTicket = 0;
		Integer paid = 0;
		Integer total = rows.size();

		for (ApplicationRow row : rows)
		{
			if (row.getStatus().equals(ApplicationStatus.NEW))
			{
				newApps += 1;
			}

			// count the accepted member details
			if (row.getStatus().equals(ApplicationStatus.ACCEPTED))
			{
				accepted += 1;

				if (row.getHasTicket())
				{
					acceptedTicket += 1;
				}
				if (row.getHasPaid())
				{
					paid = paid + 1;
				}

				if (row.getCircle().equals(Circle.Faeries))
				{
					faerie += 1;
				}
				if (row.getCircle().equals(Circle.Healers))
				{
					healer += 1;
				}
				if (row.getCircle().equals(Circle.Infrastructure))
				{
					infra += 1;
				}
				if (row.getCircle().equals(Circle.Kitchen))
				{
					kitchen += 1;
				}
				if (null == row.getCircle())
				{
					unAssigned += 1;
				}
			}
		} // for row

		setTotalApps(total.toString());
		setNewApps(newApps.toString());
		setAcceptedApps(accepted.toString());
		setTicketApps(acceptedTicket.toString());
		setPaidApps(paid.toString());

		setFaerieApps(faerie.toString());
		setHealerApps(healer.toString());
		setInfraApps(infra.toString());
		setKitchenApps(kitchen.toString());

	}

	private void addColumnsToDataGrid()
	{

		// Headers

		// Columns ------------
		// status
		TextColumn<ApplicationRow> statusColumn = createStatusColumn();
		appTable.addColumn(statusColumn, "Status");
		appTable.setColumnWidth(statusColumn, 7, Unit.PCT);

		// firstName
		TextColumn<ApplicationRow> nameColumn = createNameColumn();
		appTable.addColumn(nameColumn, "Name");
		appTable.setColumnWidth(nameColumn, 15, Unit.PCT);

		// playaName
		TextColumn<ApplicationRow> playaNameColumn = createPlayaNameColumn();
		appTable.addColumn(playaNameColumn, "PLaya Name");
		appTable.setColumnWidth(playaNameColumn, 15, Unit.PCT);

		// email
		TextColumn<ApplicationRow> emailColumn = createEmailColumn();
		appTable.addColumn(emailColumn, "Email");
		appTable.setColumnWidth(emailColumn, 15, Unit.PCT);

		// circle
		TextColumn<ApplicationRow> circleColumn = createCircleColumn();
		appTable.addColumn(circleColumn, "Circle");
		appTable.setColumnWidth(circleColumn, 10, Unit.PCT);

		// diet
		TextColumn<ApplicationRow> dietColumn = createDietColumn();
		appTable.addColumn(dietColumn, "Diet");
		appTable.setColumnWidth(dietColumn, 10, Unit.PCT);

		// hasPaid
		TextColumn<ApplicationRow> paidColumn = createPaidColumn();
		appTable.addColumn(paidColumn, "Paid");
		appTable.setColumnWidth(paidColumn, 7, Unit.PCT);

		// hasTicket
		TextColumn<ApplicationRow> ticketColumn = createTicketColumn();
		appTable.addColumn(ticketColumn, "Ticket");
		appTable.setColumnWidth(ticketColumn, 7, Unit.PCT);

		// isET
		// TextColumn<ApplicationRow> etColumn = createEtColumn();
		// appTable.addColumn(etColumn, "ET");
		// appTable.setColumnWidth(etColumn, 5, Unit.PCT);

		// isStrike
		// TextColumn<ApplicationRow> strikeColumn = createStrikeColumn();
		// appTable.addColumn(strikeColumn, "Strike");
		// appTable.setColumnWidth(strikeColumn, 5, Unit.PCT);

		// hasRV
		// TextColumn<ApplicationRow> rvColumn = createRvColumn();
		// appTable.addColumn(rvColumn, "RV");
		// appTable.setColumnWidth(rvColumn, 5, Unit.PCT);

		// hasStructure
		// TextColumn<ApplicationRow> structureColumn = createStructureColumn();
		// appTable.addColumn(structureColumn, "Structure");
		// appTable.setColumnWidth(structureColumn, 5, Unit.PCT);

	}

	private TextColumn<ApplicationRow> createStatusColumn()
	{
		TextColumn<ApplicationRow> statusColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getStatus());
			}
		};

		statusColumn.setSortable(true);
		sortHandler.setComparator(statusColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return r1.getStatus().toString().compareTo(r2.getStatus().toString());
			}
		});

		// statusColumn.setFieldUpdater(null);
		// need to have a drop down here not edit text willy nilly
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return statusColumn;
	}

	private TextColumn<ApplicationRow> createNameColumn()
	{

		TextColumn<ApplicationRow> nameColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return row.getFirstName() + " " + row.getLastName();
			}
		};

		nameColumn.setSortable(true);
		sortHandler.setComparator(nameColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return r1.getLastName().compareTo(r2.getLastName());
			}
		});

		// nameColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return nameColumn;
	}

	private TextColumn<ApplicationRow> createPlayaNameColumn()
	{
		TextColumn<ApplicationRow> playaNameColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getPlayaName());
			}
		};

		playaNameColumn.setSortable(true);
		sortHandler.setComparator(playaNameColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return r1.getPlayaName().compareTo(r2.getPlayaName());
			}
		});

		// playaNameColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return playaNameColumn;
	}

	private TextColumn<ApplicationRow> createEmailColumn()
	{
		TextColumn<ApplicationRow> emailColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getEmail());
			}
		};

		emailColumn.setSortable(true);
		sortHandler.setComparator(emailColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return r1.getEmail().compareTo(r2.getEmail());
			}
		});

		// emailColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return emailColumn;
	}

	private TextColumn<ApplicationRow> createCircleColumn()
	{
		TextColumn<ApplicationRow> circleColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getCircle());
			}
		};

		circleColumn.setSortable(true);
		sortHandler.setComparator(circleColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return Utils.nullOrString(r1.getCircle())
						.compareTo(Utils.nullOrString(r2.getCircle()));
			}
		});

		// circleColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return circleColumn;
	}

	private TextColumn<ApplicationRow> createDietColumn()
	{
		TextColumn<ApplicationRow> dietColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getDiet());
			}
		};

		dietColumn.setSortable(true);
		sortHandler.setComparator(dietColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return Utils.nullOrString(r1.getDiet()).compareTo(Utils.nullOrString(r2.getDiet()));
			}
		});

		// dietColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return dietColumn;
	}

	private TextColumn<ApplicationRow> createPaidColumn()
	{
		TextColumn<ApplicationRow> paidColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getHasPaid());
			}
		};

		paidColumn.setSortable(true);
		sortHandler.setComparator(paidColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return Utils.nullOrString(r1.getHasPaid())
						.compareTo(Utils.nullOrString(r2.getHasPaid()));
			}
		});

		// paidColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return paidColumn;
	}

	private TextColumn<ApplicationRow> createTicketColumn()
	{
		TextColumn<ApplicationRow> ticketColumn = new TextColumn<ApplicationRow>()
		{
			@Override
			public String getValue(ApplicationRow row)
			{
				return Utils.nullOrString(row.getHasTicket());
			}
		};

		ticketColumn.setSortable(true);
		sortHandler.setComparator(ticketColumn, new Comparator<ApplicationRow>()
		{
			@Override
			public int compare(ApplicationRow r1, ApplicationRow r2)
			{
				return Utils.nullOrString(r1.getHasTicket())
						.compareTo(Utils.nullOrString(r2.getHasTicket()));
			}
		});

		// ticketColumn.setFieldUpdater(null);
		// probably shouldn't let Admins edit peoples' names
		// new FieldUpdater<ApplicationRow, String>()
		// {
		// @Override
		// public void update(int index, ApplicationRow row, String value)
		// {
		// // Called when the user changes the value.
		// row.setFirstName(value);
		// presenter.onRowEdit();
		// }
		// });

		return ticketColumn;
	}

	public void setTotalApps(String value)
	{
		totalApps.setValue(value);
	}

	public void setNewApps(String value)
	{
		newApps.setValue(value);
	}

	public void setAcceptedApps(String value)
	{
		acceptedApps.setValue(value);
	}

	public void setPaidApps(String value)
	{
		paidApps.setValue(value);
	}

	public void setFaerieApps(String value)
	{
		faerieApps.setValue(value);
	}

	public void setHealerApps(String value)
	{
		healerApps.setValue(value);
	}

	public void setInfraApps(String value)
	{
		infraApps.setValue(value);
	}

	public void setKitchenApps(String value)
	{
		kitchenApps.setValue(value);
	}

	public void setTicketApps(String value)
	{
		ticketApps.setValue(value);
	}

	@Override
	public Widget asWidget()
	{
		return this;
	}
}
