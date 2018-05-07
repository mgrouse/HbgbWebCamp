/*
 * This is an unpublished work protected by the United States copyright laws and
 * is proprietary to Michael A. Grouse. Disclosure, copying, reproduction,
 * merger translation, modification, enhancement or use by anyone other than
 * authorized employees or licensees of Michael A. Grouse without prior written
 * consent of Michael A. Grouse is prohibited.
 *
 * Copyright (C) 1992 - 2018 Michael A. Grouse, All Rights Reserved.
 *
 * This copyright notice should not be construed as evidence of publication.
 */
package org.hbgb.webcamp.client.view.report;

import org.hbgb.webcamp.client.presenter.IReportPresenter;
import org.hbgb.webcamp.client.widget.LoadingPopup;
import org.hbgb.webcamp.shared.Car;
import org.hbgb.webcamp.shared.Shelter;
import org.hbgb.webcamp.shared.ShelterReport;
import org.hbgb.webcamp.shared.enums.ShelterType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Michael
 *
 */
public class ShelterReportViewImpl extends Composite implements IShelterReportView
{
	@UiTemplate(value = "ShelterReportView.ui.xml")
	static interface ShelterReportViewImplBinder extends UiBinder<Widget, ShelterReportViewImpl>
	{
	}

	private static UiBinder<Widget, ShelterReportViewImpl> binder = GWT
			.create(ShelterReportViewImplBinder.class);

	@UiField
	FlexTable table;

	LoadingPopup loadPop;

	IReportPresenter presenter;

	public ShelterReportViewImpl()
	{
		initWidget(binder.createAndBindUi(this));
		table.setBorderWidth(1);
		table.setCellPadding(5);
		loadPop = new LoadingPopup();
	}

	@Override
	public void clear()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(IReportPresenter p)
	{
		presenter = p;
	}

	@Override
	public void setReportData(ShelterReport report)
	{
		loadPop.go();

		table.removeAllRows();

		formatShelters(report);
		formatCars(report);
		formatBadEmails(report);

		loadPop.stop();
	}

	private void formatShelters(ShelterReport report)
	{
		FlexTable.FlexCellFormatter formatter = table.getFlexCellFormatter();
		int i = table.getRowCount();

		// Table Title
		// table.setWidget(i, 0, new HTML("Shelters") );
		// formatter.setColSpan(i, 0, 3);

		// Tent Header
		table.setWidget(i, 0, new HTML("Dorm Tents"));
		formatter.setColSpan(i, 0, 3);

		// Tent rows
		table.setWidget(++i, 0, new HTML("Name"));
		table.setWidget(i, 1, new HTML("Email"));

		for (Shelter s : report.getShelters())
		{
			if (ShelterType.Tent == s.getType())
			{
				table.setWidget(++i, 0, new HTML(s.getName()));
				table.setWidget(i, 1, new HTML(s.getEmail()));
			}
		}

		// Structure Header
		table.setWidget(++i, 0, new HTML("Free Structures"));
		formatter.setColSpan(i, 0, 3);

		// Structure rows
		table.setWidget(++i, 0, new HTML("Name"));
		table.setWidget(i, 1, new HTML("Email"));
		table.setWidget(i, 2, new HTML("Description"));

		for (Shelter s : report.getShelters())
		{
			if (ShelterType.Structure == s.getType())
			{
				table.setWidget(++i, 0, new HTML(s.getName()));
				table.setWidget(i, 1, new HTML(s.getEmail()));
				table.setWidget(i, 2, new HTML(s.getDescription()));
			}
		}

		// RV Header
		table.setWidget(++i, 0, new HTML("RV's"));
		formatter.setColSpan(i, 0, 3);

		// RV rows
		table.setWidget(++i, 0, new HTML("Name"));
		table.setWidget(i, 1, new HTML("Email"));
		table.setWidget(i, 2, new HTML("Description"));

		for (Shelter s : report.getShelters())
		{
			if (ShelterType.Structure == s.getType())
			{
				table.setWidget(++i, 0, new HTML(s.getName()));
				table.setWidget(i, 1, new HTML(s.getEmail()));
				table.setWidget(i, 2, new HTML(s.getDescription()));
			}
		}
	}

	private void formatCars(ShelterReport report)
	{
		FlexTable.FlexCellFormatter formatter = table.getFlexCellFormatter();
		int i = table.getRowCount();

		// Car Header
		table.setWidget(i, 0, new HTML("Cars"));
		formatter.setColSpan(i, 0, 3);

		// Car rows
		table.setWidget(++i, 0, new HTML("Name"));
		table.setWidget(i, 1, new HTML("Email"));

		for (Car c : report.getCars())
		{
			table.setWidget(++i, 0, new HTML(c.getName()));
			table.setWidget(i, 1, new HTML(c.getEmail()));
		}
	}

	private void formatBadEmails(ShelterReport report)
	{
		FlexTable.FlexCellFormatter formatter = table.getFlexCellFormatter();
		int i = table.getRowCount();

		// Car Header
		table.setWidget(i, 0, new HTML("Bad Data"));
		formatter.setColSpan(i, 0, 3);

		// Car rows
		table.setWidget(++i, 0, new HTML("Email"));

		for (String s : report.getBadEmails())
		{
			table.setWidget(++i, 0, new HTML(s));
		}
	}
}
