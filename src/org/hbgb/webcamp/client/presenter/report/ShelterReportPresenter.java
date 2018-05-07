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
package org.hbgb.webcamp.client.presenter.report;

import org.hbgb.webcamp.client.model.IShelterReportModel;
import org.hbgb.webcamp.client.model.ShelterReportModel;
import org.hbgb.webcamp.client.presenter.IReportPresenter;
import org.hbgb.webcamp.client.view.report.IShelterReportView;
import org.hbgb.webcamp.client.view.report.ShelterReportViewImpl;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * @author Michael
 *
 */
public class ShelterReportPresenter implements IReportPresenter
{
	IShelterReportModel model = new ShelterReportModel();
	IShelterReportView view = new ShelterReportViewImpl();
	HasWidgets screen;

	public ShelterReportPresenter()
	{
		model.setPresenter(this);
		view.setPresenter(this);
	}

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
		screen.clear();
		view.clear();
		screen.add(view.asWidget());
	}

	@Override
	public void report()
	{
		model.fetchData();
	}

	@Override
	public void onDataFetched()
	{
		view.setReportData(model.getReportData());
	}

}
