/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 * com.google.gwt.user.client.ui.HasWidgets com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.presenter.application.input;

import org.hbgb.webcamp.client.model.CommitteeInfoBlockModel;
import org.hbgb.webcamp.client.model.HealerSheetInfoBlockModel;
import org.hbgb.webcamp.client.presenter.IKeyPresenter;
import org.hbgb.webcamp.client.presenter.IKeyedModelPresenter;
import org.hbgb.webcamp.client.presenter.ISequentialPresenter;
import org.hbgb.webcamp.client.view.ViewFinder;
import org.hbgb.webcamp.client.view.application.input.InputCommitteeInfoView;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.HealerSheetInfoBlock;

import com.google.gwt.user.client.ui.HasWidgets;

public class InputCommitteeNHealerSheetInfoPresenter
		implements ISequentialPresenter, IKeyedModelPresenter
{
	private static final int NUM_MODELS = 2;
	private int num_calls = 0;
	private String key;
	private InputCommitteeInfoView view;
	private HasWidgets screen;
	private IKeyPresenter nextPresenter;
	private HealerSheetInfoBlockModel hsModel;
	private CommitteeInfoBlockModel ciModel;

	public InputCommitteeNHealerSheetInfoPresenter(String k)
	{
		key = k;
		hsModel = new HealerSheetInfoBlockModel();
		ciModel = new CommitteeInfoBlockModel();
		hsModel.setPresenter(this);
		ciModel.setPresenter(this);
		view = ViewFinder.getCommitteeInfoView();
		view.setPresenter(this);
	}

	@Override
	public void setKey(String k)
	{
		key = k;
		num_calls = 0;
	}

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
	}

	@Override
	public void go()
	{
		screen.clear();
		view.clear();
		hsModel.fetchDataByAppKey(key);
		ciModel.fetchData(key);
	}

	@Override
	public void onDataFetched()
	{
		setView();
		screen.clear();
		screen.add(view.asWidget());
	}

	@Override
	public void onDataPut()
	{
		++num_calls;
		if (NUM_MODELS == num_calls)
		{
			screen.clear();
			nextPresenter.setKey(key);
			nextPresenter.setScreen(screen);
			nextPresenter.go();
		}
	}

	@Override
	public void setNextPresenter(IKeyPresenter next)
	{
		nextPresenter = next;
	}

	@Override
	public void onNextButtonClicked()
	{
		setModel();
		hsModel.putData();
		ciModel.putData();
	}

	private void setView()
	{
		CommitteeInfoBlock ciBlock = ciModel.getData();
		HealerSheetInfoBlock hsBlock = hsModel.getData();

		if (hsBlock != null)
		{
			view.setSessionLength(hsBlock.getSessionLength());
			view.setModality1(hsBlock.getModality1());
			view.setModality2(hsBlock.getModality2());
			view.setModality3(hsBlock.getModality3());
			view.setModality4(hsBlock.getModality4());
			view.setBioBoxText(hsBlock.getBio());
		}

		if (ciBlock != null)
		{
			view.setReason1(ciBlock.getReason1());
			view.setReason2(ciBlock.getReason2());
			view.setCommittee1(ciBlock.getCircle1());
			view.setCommittee2(ciBlock.getCircle2());
		}
	}

	private void setModel()
	{
		HealerSheetInfoBlock block = hsModel.getData();
		block.setSessionLength(view.getSessionLength());
		block.setModality1(view.getModality1());
		block.setModality2(view.getModality2());
		block.setModality3(view.getModality3());
		block.setModality4(view.getModality4());
		block.setBio(view.getBioBoxText());

		hsModel.setData(block);

		CommitteeInfoBlock ciBlock = ciModel.getData();
		ciBlock.setReason1(view.getReason1());
		ciBlock.setReason2(view.getReason2());
		ciBlock.setCircle1(view.getCommittee1());
		ciBlock.setCircle2(view.getCommittee2());

		ciModel.setData(ciBlock);
	}

}
