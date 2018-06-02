/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.event.dom.client.ClickEvent
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiHandler
 * com.google.gwt.uibinder.client.UiTemplate
 * com.google.gwt.user.client.ui.Composite
 * com.google.gwt.user.client.ui.FlexTable com.google.gwt.user.client.ui.HTML
 * com.google.gwt.user.client.ui.HTMLTable
 * com.google.gwt.user.client.ui.HTMLTable$Cell
 * com.google.gwt.user.client.ui.TextBox com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.view.application;

import java.util.List;

import org.hbgb.webcamp.client.common.ColumnDefinition;
import org.hbgb.webcamp.client.widget.LoadingPopup;
import org.hbgb.webcamp.shared.ApplicationDetails;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.Circle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationListByYearViewImpl<T> extends Composite
		implements ApplicationListByYearView<T>
{
	@SuppressWarnings("rawtypes")
	@UiTemplate(value = "ApplicationListByYearView.ui.xml")
	static interface ApplicationViewUiBinder extends UiBinder<Widget, ApplicationListByYearViewImpl>
	{
	}

	private static ApplicationViewUiBinder uiBinder = GWT.create(ApplicationViewUiBinder.class);

	@UiField
	TextBox totalApps;

	@UiField
	TextBox newApps;

	@UiField
	TextBox acceptedApps;

	// @UiField
	// TextBox invoicedApps;

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

	@UiField
	FlexTable table;

	LoadingPopup loadPop;

	private ApplicationListByYearView.Presenter<T> presenter;
	private List<ColumnDefinition<T>> columnDefinitions;
	private List<T> rowData;

	public ApplicationListByYearViewImpl()
	{
		initWidget(uiBinder.createAndBindUi(this));
		table.setBorderWidth(1);
		table.setCellPadding(5);
		loadPop = new LoadingPopup();

		yearListBox.addItem("2018");
		yearListBox.addItem("2017");
		// yearListBox.addItem("2016");
		// yearListBox.addItem("2015");
		// yearListBox.addItem("2014"); there are errors with this year

	}

	@Override
	public void setPresenter(ApplicationListByYearView.Presenter<T> pres)
	{
		presenter = pres;
	}

	@Override
	public void setColumnDefinitions(List<ColumnDefinition<T>> definitions)
	{
		columnDefinitions = definitions;
	}

	@Override
	public int getYearValue()
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
	public void setRowData(List<T> data)
	{
		Integer kitchen = 0;
		Integer infra = 0;
		Integer healer = 0;
		Integer faerie = 0;
		Integer acceptedTicket = 0;
		Integer paid = 0;
		Integer accepted = 0;
		Integer newApps = 0;
		Integer total = data.size();
		table.removeAllRows();

		rowData = data;

		table.setWidget(0, 1, new HTML("Status"));
		table.setWidget(0, 2, new HTML("First & Last Name"));
		table.setWidget(0, 3, new HTML("Playa Name"));
		table.setWidget(0, 4, new HTML("Email"));
		table.setWidget(0, 5, new HTML("Circle"));
		table.setWidget(0, 6, new HTML("Early Team"));
		table.setWidget(0, 7, new HTML("Diet"));
		// table.setWidget(0, 7, new HTML("Invoiced"));
		table.setWidget(0, 8, new HTML("Paid"));
		table.setWidget(0, 9, new HTML("ticket"));

		int i = 0;
		while (i < rowData.size())
		{
			T t = rowData.get(i);
			int j = 0;
			while (j < columnDefinitions.size())
			{
				ColumnDefinition<T> columnDefinition = columnDefinitions.get(j);
				table.setWidget(i + 1, j, columnDefinition.render(t));
				++j;
			}

			ApplicationDetails ad = (ApplicationDetails) t;
			if (ad.getStatus().equals(ApplicationStatus.ACCEPTED.toString()))
			{
				accepted = accepted + 1;
				if (ad.getCircle().equals(Circle.Faeries.toString()))
				{
					faerie = faerie + 1;
				}
				if (ad.getCircle().equals(Circle.Healers.toString()))
				{
					healer = healer + 1;
				}
				if (ad.getCircle().equals(Circle.Infrastructure.toString()))
				{
					infra = infra + 1;
				}
				if (ad.getCircle().equals(Circle.Kitchen.toString()))
				{
					kitchen = kitchen + 1;
				}
				if (ad.getTicket().equals("true"))
				{
					acceptedTicket = acceptedTicket + 1;
				}
			}
			if (ad.getStatus().equals(ApplicationStatus.NEW.toString()))
			{
				newApps = newApps + 1;
			}
			// if (ad.getInvoiced().equals("true"))
			// {
			// invoiced = invoiced + 1;
			// }
			if (ad.getPaid().equals("true"))
			{
				paid = paid + 1;
			}
			++i;
		}

		setTotalApps(total.toString());
		setNewApps(newApps.toString());
		setAcceptedApps(accepted.toString());
		setTicketApps(acceptedTicket.toString());
		setPaidApps(paid.toString());

		setFaerieApps(faerie.toString());
		setHealerApps(healer.toString());
		setInfraApps(infra.toString());
		setKitchenApps(kitchen.toString());

		refreshButton.setEnabled(true);
		loadPop.stop();
	}

	void onAddButtonClicked(ClickEvent event)
	{
		if (presenter == null)
			return;
		presenter.onAddButtonClicked();
	}

	void onDeleteButtonClicked(ClickEvent event)
	{
		if (presenter == null)
			return;
		presenter.onDeleteButtonClicked();
	}

	@UiHandler(value = { "table" })
	void onTableClicked(ClickEvent event)
	{
		if (presenter == null)
			return;
		HTMLTable.Cell cell = table.getCellForEvent(event);
		if (cell == null)
			return;
		if (shouldFireClickEvent(cell))
		{
			presenter.onItemClicked(rowData.get(cell.getRowIndex() - 1));
		}
		if (!shouldFireSelectEvent(cell))
			return;
		presenter.onItemSelected(rowData.get(cell.getRowIndex() - 1));
	}

	private boolean shouldFireClickEvent(HTMLTable.Cell cell)
	{
		boolean shouldFireClickEvent = false;
		if (cell == null)
			return shouldFireClickEvent;
		if (cell.getRowIndex() == 0)
			return shouldFireClickEvent;
		ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());
		if (columnDefinition == null)
			return shouldFireClickEvent;
		return columnDefinition.isClickable();
	}

	private boolean shouldFireSelectEvent(HTMLTable.Cell cell)
	{
		boolean shouldFireSelectEvent = false;
		if (cell == null)
			return shouldFireSelectEvent;
		if (cell.getRowIndex() == 0)
			return shouldFireSelectEvent;
		ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());
		if (columnDefinition == null)
			return shouldFireSelectEvent;
		return columnDefinition.isSelectable();
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

	@Override
	public void clear()
	{
		loadPop.go();
		table.removeAllRows();
	}

}
