/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.user.client.Window
 * com.google.gwt.user.client.rpc.AsyncCallback
 */
package org.hbgb.webcamp.client.model;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.async.HealerSheetServiceAsync;
import org.hbgb.webcamp.client.presenter.IKeyedModelPresenter;
import org.hbgb.webcamp.shared.HealerSheetInfoBlock;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HealerSheetInfoBlockModel implements IKeyedModel
{
	HealerSheetServiceAsync hsRpcService = AsyncServiceFinder.getHealerSheetService();
	ApplicationServiceAsync appRpcService = AsyncServiceFinder.getApplicationService();
	IKeyedModelPresenter presenter;
	HealerSheetInfoBlock model;

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
			hsRpcService.getHealerSheetInfoBlock(key, new AsyncCallback<HealerSheetInfoBlock>()
			{

				@Override
				public void onSuccess(HealerSheetInfoBlock result)
				{
					if (result == null)
					{
						Window.alert("Camper's Healer Sheet info returned as null");
						return;
					}
					model = result;
					presenter.onDataFetched();
				}

				@Override
				public void onFailure(Throwable caught)
				{
					Window.alert("DB Error retrieving Camper's Healer Sheet");
				}
			});
			return;
		}
		Window.alert("Error no key for Camper's Healer Sheet!");
	}

	private void fetcHSInfoBlockByEmail(String email)
	{
		hsRpcService.getHealerSheetInfoBlockByEmail(email, true,
				new AsyncCallback<HealerSheetInfoBlock>()
				{

					@Override
					public void onSuccess(HealerSheetInfoBlock result)
					{
						if (result == null)
						{
							Window.alert("Camper's Healer Sheet info returned as null");
							return;
						}
						model = result;
						presenter.onDataFetched();
					}

					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("DB Error retrieving Camper's Healer Sheet: ");
					}
				});
	}

	public void fetchDataByAppKey(String key)
	{
		if (key == null)
			return;
		appRpcService.findApplicationEmailByKey(key, new AsyncCallback<String>()
		{

			@Override
			public void onSuccess(String result)
			{
				if (result == null)
				{
					Window.alert("Camper's email returned as null");
					return;
				}
				fetcHSInfoBlockByEmail(result);
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("DB Error retrieving Camper's Email");
			}
		});
	}

	@Override
	public void putData()
	{
		hsRpcService.updateHealerSheetInfoBlock(this.model, new AsyncCallback<Boolean>()
		{

			@Override
			public void onSuccess(Boolean saved)
			{
				if (saved.booleanValue())
				{
					presenter.onDataPut();
					return;
				}
				Window.alert("DB Error saving Healer Sheet Info");
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("RPC Error saving Healer Sheet Info: " + caught);
			}
		});
	}

	public HealerSheetInfoBlock getData()
	{
		return model;
	}

	public void setData(HealerSheetInfoBlock t)
	{
		model = t;
	}

}
