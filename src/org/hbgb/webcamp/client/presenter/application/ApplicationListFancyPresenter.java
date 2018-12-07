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
package org.hbgb.webcamp.client.presenter.application;

import java.util.ArrayList;

import org.hbgb.webcamp.client.model.ApplicationTableModel;
import org.hbgb.webcamp.client.presenter.IModelPresenter;
import org.hbgb.webcamp.client.view.application.ApplicationFancyListTableView;
import org.hbgb.webcamp.client.view.application.ApplicationListFancyTableViewImpl;
import org.hbgb.webcamp.shared.ApplicationRow;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author Michael
 *
 */
public class ApplicationListFancyPresenter
		implements IModelPresenter, ApplicationFancyListTableView.Presenter
{
	private ApplicationTableModel model;
	private ApplicationFancyListTableView view;

	private ListDataProvider<ApplicationRow> dataProvider;

	private final HandlerManager eventBus;
	private HasWidgets screen;

	public ApplicationListFancyPresenter(HandlerManager bus)
	{
		eventBus = bus;

		model = new ApplicationTableModel();
		model.setPresenter(this);

		dataProvider = new ListDataProvider<>(new ArrayList<ApplicationRow>(),
				ApplicationRow.KEY_PROVIDER);

		// this should be gotten from View Finder
		view = new ApplicationListFancyTableViewImpl();
		view.setPresenter(this);
		view.clear();
	}

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
		screen.add(view.asWidget());
	}

	@Override
	public void go()
	{
		model.setYear(view.getYear());
		model.fetch();
	}

	@Override
	public void onFetchComplete()
	{
		dataProvider.setList(model.getData());

		view.setRowData(dataProvider);

		// screen.clear();
		// screen.add(view.asWidget());
	}

	@Override
	public void onPutComplete()
	{
		// TODO Auto-generated method stub
		// refresh displays
	}

	@Override
	public void onRowSelect()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onRowEdit(ApplicationRow row)
	{
		// send new data to database here
		// model.xxx()
		// model.put()
	}

	@Override
	public void onRefreshButtonClicked()
	{
		model.setYear(view.getYear());
		model.fetch();
	}

}
