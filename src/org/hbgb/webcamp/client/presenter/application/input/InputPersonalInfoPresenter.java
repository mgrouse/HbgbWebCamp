/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.appengine.api.datastore.Text
 * com.google.gwt.user.client.Window
 * com.google.gwt.user.client.rpc.AsyncCallback
 * com.google.gwt.user.client.ui.HasWidgets com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.presenter.application.input;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.presenter.IKeyPresenter;
import org.hbgb.webcamp.client.presenter.ISequentialPresenter;
import org.hbgb.webcamp.client.view.ViewFinder;
import org.hbgb.webcamp.client.view.application.input.InputPersonalInfoView;
import org.hbgb.webcamp.shared.Burner;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class InputPersonalInfoPresenter implements ISequentialPresenter
{
	private final ApplicationServiceAsync rpcService = AsyncServiceFinder.getApplicationService();
	private final InputPersonalInfoView view;
	private String key;
	private Burner burner;
	private HasWidgets screen;
	private IKeyPresenter nextPresenter;

	public InputPersonalInfoPresenter(String k)
	{
		key = k;
		view = ViewFinder.getPersonalInfoView();
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

	private void setView()
	{
		view.setEmailText(burner.getEmail());
		view.setFirstNameText(burner.getDemographics().getFirstName());
		view.setLastNameText(burner.getDemographics().getLastName());
		view.setPlayaNameText(burner.getDemographics().getPlayaName());
		view.setGender(burner.getDemographics().getGender());
		view.setBirthDate(burner.getDemographics().getBirthDate());
		view.setDefaultWorldJobText(burner.getDemographics().getDefaultWorldJob());
		view.setBioText(burner.getDemographics().getBio().toString());
		view.setPhoneText(burner.getContactInfo().getPhone());
		view.setSkypeNameText(burner.getContactInfo().getSkypeName());
		view.setContactMethod(burner.getContactInfo().getContactMethod());
		view.setCallTime(burner.getContactInfo().getCallTime());
		view.setAddress(burner.getContactInfo().getAddress());
	}

	private void setModel()
	{
		burner.setEmail(view.getEmailText());
		burner.getDemographics().setFirstName(view.getFirstNameText());
		burner.getDemographics().setLastName(view.getLastNameText());
		burner.getDemographics().setPlayaName(view.getPlayaNameText());
		burner.getDemographics().setGender(view.getGender());
		burner.getDemographics().setBirthDate(view.getBirthDate());
		burner.getDemographics().setDefaultWorldJob(view.getDefaultWorldJobText());
		burner.getDemographics().setBio(view.getBioText());
		burner.getContactInfo().setEmail(view.getEmailText());
		burner.getContactInfo().setPhone(view.getPhoneText());
		burner.getContactInfo().setSkypeName(view.getSkypeNameText());
		burner.getContactInfo().setContactMethod(view.getContactMethod());
		burner.getContactInfo().setCallTime(view.getCallTime());
		burner.getContactInfo().setAddress(view.getAddress());
	}

	public void fetchData()
	{
		if (key != null)
		{
			rpcService.getApplicant(key, new AsyncCallback<Burner>()
			{

				@Override
				public void onSuccess(Burner result)
				{
					burner = result;
					setView();
					screen.add(view.asWidget());
				}

				@Override
				public void onFailure(Throwable caught)
				{
					Window.alert("Error retrieving Applicant");
				}
			});
		}
		else
		{
			Window.alert("Error no key for Applicant's Application!");
		}
	}

	@Override
	public void go()
	{
		screen.clear();
		fetchData();
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
		rpcService.updateApplicant(burner, new AsyncCallback<Boolean>()
		{

			@Override
			public void onSuccess(Boolean saved)
			{
				if (saved)
				{
					screen.clear();
					nextPresenter.setKey(key);
					nextPresenter.setScreen(screen);
					nextPresenter.go();
				}
				else
				{
					Window.alert("DB Error saving Applicant's Info");
				}
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert("RPC Error saving Applicant's Info");
			}
		});
	}
}
