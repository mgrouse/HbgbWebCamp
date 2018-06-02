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
import org.hbgb.webcamp.shared.CommitteeInfoBlock;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CommitteeInfoBlockModel implements IKeyedModel
{
	ApplicationServiceAsync rpcService = AsyncServiceFinder.getApplicationService();
	IKeyedModelPresenter presenter;
	CommitteeInfoBlock model;

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
			rpcService.getApplicantsCommitteeInfoBlock(key, new AsyncCallback<CommitteeInfoBlock>()
			{

				@Override
				public void onSuccess(CommitteeInfoBlock result)
				{
					if (result == null)
					{
						Window.alert("Applicant's Committee Info reurned as null");
						return;
					}
					model = result;
					presenter.onDataFetched();
				}

				@Override
				public void onFailure(Throwable caught)
				{
					Window.alert("DB Error retrieving Applicant's Committee Info");
				}
			});
			return;
		}
		Window.alert("Error no key for Applicant's Application!");
	}

	@Override
	public void putData()
	{
		rpcService.updateApplicantsCommitteeInfoBlock(model, new AsyncCallback<String>()
		{

			@Override
			public void onSuccess(String msg)
			{
				if (msg.equals(""))
				{
					presenter.onDataPut();
					return;
				}
				Window.alert("DB Error saving Applicant's Committee Info: " + msg);
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("RPC Error saving Applicant's Committee Info");
			}
		});
	}

	public CommitteeInfoBlock getData()
	{
		return model;
	}

	public void setData(CommitteeInfoBlock t)
	{
		model = t;
	}

}
