/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.user.client.Window
 * com.google.gwt.user.client.rpc.AsyncCallback
 * com.google.gwt.user.client.ui.HasWidgets com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.presenter.application.input;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.presenter.IKeyPresenter;
import org.hbgb.webcamp.client.presenter.ISequentialPresenter;
import org.hbgb.webcamp.client.view.ViewFinder;
import org.hbgb.webcamp.client.view.application.input.InputLogisticsInfoView;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class InputLogisticsInfoPresenter implements ISequentialPresenter
{
	private final ApplicationServiceAsync rpcService = AsyncServiceFinder.getApplicationService();
	private final InputLogisticsInfoView view;
	private String key;
	private LogisticsInfoBlock logisticsInfoBlock;
	private HasWidgets screen;
	private IKeyPresenter nextPresenter;

	public InputLogisticsInfoPresenter(String k)
	{
		key = k;
		view = ViewFinder.getLogisticsInfoView();
		view.setPresenter(this);
	}

	@Override
	public void setKey(String k)
	{
		key = k;
	}

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
	}

	@Override
	public void setNextPresenter(IKeyPresenter next)
	{
		nextPresenter = next;
	}

	@Override
	public void go()
	{
		screen.clear();
		fetchData();
	}

	private void setView()
	{
		view.setWantsEarlyTeam(logisticsInfoBlock.getWantsEarlyTeam());
		if (logisticsInfoBlock.getWantsEarlyTeam())
		{
			view.setETArrivalDates();
		}
		else
		{
			view.setEventArrivalDates();
		}

		view.setWantsStrikeTeam(logisticsInfoBlock.getWantsStrikeTeam());
		if (logisticsInfoBlock.getWantsStrikeTeam())
		{
			view.setStrikeDepartureDates();
		}
		else
		{
			view.setEventDepartureDates();
		}

		view.setTransportation(logisticsInfoBlock.getTransType());
		view.setArrivalDoE(logisticsInfoBlock.getArrivalDoE());
		view.setArrivalTime(logisticsInfoBlock.getArrivalTime());
		view.setDepartureDoE(logisticsInfoBlock.getDepartureDoE());
		view.setDepartureTime(logisticsInfoBlock.getDepartureTime());
	}

	private void setModel()
	{
		logisticsInfoBlock.setWantsEarlyTeam(view.getWantsEarlyTeam());
		logisticsInfoBlock.setWantsStrikeTeam(view.getWantsStrikeTeam());
		logisticsInfoBlock.setTransType(view.getTransportation());
		logisticsInfoBlock.setArrivalDoE(view.getArrivalDoE());
		logisticsInfoBlock.setArrivalTime(view.getArrivalTime());
		logisticsInfoBlock.setDepartureDoE(view.getDepartureDoE());
		logisticsInfoBlock.setDepartureTime(view.getDepartureTime());
	}

	public void fetchData()
	{
		if (key != null)
		{
			rpcService.getApplicantsLogisticsInfoBlock(key, new AsyncCallback<LogisticsInfoBlock>()
			{
				@Override
				public void onSuccess(LogisticsInfoBlock result)
				{
					logisticsInfoBlock = result;

					if (result == null)
					{
						Window.alert("Applicant's Logistics Info reurned as null");
						return;
					}

					setView();
					screen.add(view.asWidget());
				}

				@Override
				public void onFailure(Throwable caught)
				{
					Window.alert("DB Error retrieving Applicant's Logistics Info");
				}
			});
		}
		else
		{
			Window.alert("Error no key for Applicant's Application!");
		}
	}

	@Override
	public void onNextButtonClicked()
	{
		setModel();
		if (modelValidates())
		{

			rpcService.updateApplicantsLogisticsInfoBlock(logisticsInfoBlock,
					new AsyncCallback<Boolean>()
					{

						@Override
						public void onSuccess(Boolean saved)
						{
							if (saved.booleanValue())
							{
								screen.clear();
								nextPresenter.setKey(key);
								nextPresenter.setScreen(screen);
								nextPresenter.go();
							}
							else
							{
								Window.alert("DB Error saving Applicant's Shelter Info");
							}
						}

						@Override
						public void onFailure(Throwable caught)
						{
							Window.alert("RPC Error saving Applicant's Shelter Info");
						}
					});
		}
	}

	/**
	 * @return
	 */
	private Boolean modelValidates()
	{
		Boolean retVal = true;

		// negative integer means arrival less than departure, which is good
		// if
		// (logisticsInfoBlock.getArrivalDoE().compareTo(logisticsInfoBlock.getDepartureDoE())
		// >= 0)
		// {
		// retVal = false;
		//
		// view.addMessage("Your arrival date is after or equal to your
		// departure date.");
		// }

		// wants strike but leaves before monday

		return retVal;
	}
}
