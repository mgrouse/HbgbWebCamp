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
package org.hbgb.webcamp.client.model;

import java.util.ArrayList;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.presenter.IModelPresenter;
import org.hbgb.webcamp.shared.ApplicationRow;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Michael
 *
 */
public class ApplicationTableModel implements IModel<ArrayList<ApplicationRow>>
{
	ApplicationServiceAsync appServAsync;

	private ArrayList<ApplicationRow> data;

	IModelPresenter presenter;

	public ApplicationTableModel()
	{
		appServAsync = AsyncServiceFinder.getApplicationService();
		data = new ArrayList<>();
	}

	@Override
	public void setPresenter(IModelPresenter mp)
	{
		presenter = mp;
	}

	@Override
	public void fetch()
	{
		appServAsync.getApplicationRows(new AsyncCallback<ArrayList<ApplicationRow>>()
		{

			@Override
			public void onSuccess(ArrayList<ApplicationRow> result)
			{
				data = result;
				presenter.onFetchComplete();
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("DB Error: " + caught.getMessage());
			}
		});
	}

	@Override
	public void put()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<ApplicationRow> getData()
	{
		// TODO Auto-generated method stub
		return null; // copy
	}

	@Override
	public void setData(ArrayList<ApplicationRow> t)
	{
		// TODO Auto-generated method stub
		// copy
	}
}
