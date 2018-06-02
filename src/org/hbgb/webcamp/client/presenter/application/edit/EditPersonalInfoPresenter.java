/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.appengine.api.datastore.Text
 */
package org.hbgb.webcamp.client.presenter.application.edit;

import org.hbgb.webcamp.client.model.PersonalInfoBlockModel;
import org.hbgb.webcamp.client.view.application.edit.EditPersonalInfoViewImpl;
import org.hbgb.webcamp.shared.Burner;

public class EditPersonalInfoPresenter extends AbstractInfoBlockPresenter
{
	static EditPersonalInfoViewImpl view = new EditPersonalInfoViewImpl();
	static PersonalInfoBlockModel model = new PersonalInfoBlockModel();

	public EditPersonalInfoPresenter()
	{
		super(model, view);
	}

	@Override
	protected void setViewData()
	{
		Burner burner = model.getData();
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

	@Override
	protected void setModelData()
	{
		Burner burner = model.getData();
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
}
