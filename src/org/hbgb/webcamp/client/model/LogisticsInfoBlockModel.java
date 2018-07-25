/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.user.client.Window
 * com.google.gwt.user.client.rpc.AsyncCallback
 */
package org.hbgb.webcamp.client.model;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.presenter.IKeyedModelPresenter;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LogisticsInfoBlockModel implements IKeyedModel
{
	ApplicationServiceAsync rpcService = AsyncServiceFinder.getApplicationService();
	IKeyedModelPresenter presenter;
	LogisticsInfoBlock model;

	@Override
	public void setPresenter(IKeyedModelPresenter p)
	{
		presenter = p;
	}

	@Override
	public void fetchData(String key)
	{
		if (key != null)
		{
			rpcService.getApplicantsLogisticsInfoBlock(key, new AsyncCallback<LogisticsInfoBlock>()
			{

				@Override
				public void onSuccess(LogisticsInfoBlock result)
				{
					if (result == null)
					{
						Window.alert("Applicant's Payment Info returned as null");
						return;
					}
					model = result;
					presenter.onDataFetched();
				}

				@Override
				public void onFailure(Throwable caught)
				{
					Window.alert("DB Error retrieving Applicant's Payment Info");
				}
			});
			return;
		}
		Window.alert("Error no key for Applicant's Application!");
	}

	@Override
	public void putData()
	{
		rpcService.updateApplicantsLogisticsInfoBlock(model, new AsyncCallback<Boolean>()
		{

			@Override
			public void onSuccess(Boolean saved)
			{
				if (saved.booleanValue())
				{
					presenter.onDataPut();
					return;
				}
				Window.alert("DB Error saving Applicant's Shelter Info");
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("RPC Error saving Applicant's Shelter Info");
			}
		});
	}

	public LogisticsInfoBlock getData()
	{
		return model;
	}

	public void setData(LogisticsInfoBlock t)
	{
		model = t;
	}

}
