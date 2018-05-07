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
package org.hbgb.webcamp.client.model;

import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.async.ShelterReportServiceAsync;
import org.hbgb.webcamp.client.presenter.IReportPresenter;
import org.hbgb.webcamp.shared.ShelterReport;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Michael
 *
 */
public class ShelterReportModel implements IShelterReportModel
{
	ShelterReportServiceAsync rpcService = AsyncServiceFinder.getShelterReportService();
	IReportPresenter presenter;
	ShelterReport model;

	@Override
	public void setPresenter(IReportPresenter p)
	{
		this.presenter = p;
	}

	@Override
	public void fetchData()
	{
		this.rpcService.getShelterReport(new AsyncCallback<ShelterReport>()
		{

			@Override
			public void onSuccess(ShelterReport result)
			{
				if (result == null)
				{
					Window.alert("Shelter Report Info reurned as null");
					return;
				}
				model = result;
				presenter.onDataFetched();
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("RPC Error retrieving Shelter report: " + caught.getMessage());
			}
		});
	}

	@Override
	public ShelterReport getReportData()
	{
		return model;
	}

}
