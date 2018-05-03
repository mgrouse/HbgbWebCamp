
package org.hbgb.webcamp.client.presenter.application.edit;

import org.hbgb.webcamp.client.model.IKeyedModel;
import org.hbgb.webcamp.client.presenter.IKeyedModelPresenter;
import org.hbgb.webcamp.client.presenter.IPartPresenter;
import org.hbgb.webcamp.client.view.IPartView;

import com.google.gwt.user.client.ui.HasWidgets;

public abstract class AbstractInfoBlockPresenter implements IPartPresenter, IKeyedModelPresenter
{
	String key;
	IKeyedModel keyedModel;
	IPartView view;
	HasWidgets screen;
	Boolean isViewAdded = false;

	public AbstractInfoBlockPresenter()
	{
	}

	public AbstractInfoBlockPresenter(IKeyedModel keyedModel, IPartView view)
	{
		view.setSaveButtonEnabled(false);
		setView(view);
		setKeyedModel(keyedModel);
	}

	protected abstract void setViewData();

	protected abstract void setModelData();

	protected void setView(IPartView v)
	{
		view = v;
		view.setPresenter(this);
	}

	protected void setKeyedModel(IKeyedModel m)
	{
		keyedModel = m;
		keyedModel.setPresenter(this);
	}

	@Override
	public void setKey(String key)
	{
		this.key = key;
		view.clear();
		keyedModel.fetchData(key);
	}

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
		screen.add(view.asWidget());
	}

	@Override
	public void onDataFetched()
	{
		view.setSaveButtonEnabled(true);
		setViewData();
	}

	@Override
	public void onDataPut()
	{
		view.setSaveButtonEnabled(true);
	}

	@Override
	public void onExpandButtonClicked()
	{
	}

	@Override
	public void onSaveButtonClicked()
	{
		view.setSaveButtonEnabled(false);
		setModelData();
		keyedModel.putData();
	}
}
