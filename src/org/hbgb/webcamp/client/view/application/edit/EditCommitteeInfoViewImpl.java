/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiTemplate
 * com.google.gwt.user.client.ui.CheckBox com.google.gwt.user.client.ui.TextArea
 * com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.view.application.edit;

import org.hbgb.webcamp.client.widget.CircleListBox;
import org.hbgb.webcamp.shared.enums.Circle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EditCommitteeInfoViewImpl extends AbstractPartView implements IEditCommitteeInfoView
{

	@UiTemplate(value = "EditCommitteeInfoView.ui.xml")
	static interface EditCommitteeInfoViewImplBinder
			extends UiBinder<Widget, EditCommitteeInfoViewImpl>
	{
	}

	private static UiBinder<Widget, EditCommitteeInfoViewImpl> binder = GWT
			.create(EditCommitteeInfoViewImplBinder.class);

	@UiField
	CircleListBox committee1;

	@UiField
	TextArea reason1;

	@UiField
	CircleListBox committee2;

	@UiField
	TextArea reason2;

	@UiField
	CircleListBox assignedCommittee;

	@UiField
	CheckBox isAssignedLead;

	public EditCommitteeInfoViewImpl()
	{
		initWidget(binder.createAndBindUi(this));
		expand();
	}

	@Override
	public void setCommittee1(Circle c)
	{
		committee1.setSelectedValue(c);
	}

	@Override
	public Circle getCommittee1()
	{
		return committee1.getSelectedEnumValue();
	}

	@Override
	public void setReason1(String text)
	{
		reason1.setValue(text, false);
	}

	@Override
	public String getReason1()
	{
		return reason1.getText();
	}

	@Override
	public void setCommittee2(Circle c)
	{
		committee2.setSelectedValue(c);
	}

	@Override
	public Circle getCommittee2()
	{
		return committee2.getSelectedEnumValue();
	}

	@Override
	public void setReason2(String text)
	{
		reason2.setValue(text, false);
	}

	@Override
	public String getReason2()
	{
		return reason2.getText();
	}

	@Override
	public void setAssignedCommittee(Circle c)
	{
		assignedCommittee.setSelectedValue(c);
	}

	@Override
	public Circle getAssignedCommittee()
	{
		return assignedCommittee.getSelectedEnumValue();
	}

	@Override
	public void setIsAssignedLead(Boolean bool)
	{
		isAssignedLead.setValue(bool, false);
	}

	@Override
	public Boolean getIsAssignedLead()
	{
		return isAssignedLead.getValue();
	}

	@Override
	public void clear()
	{
		setCommittee1(null);
		setCommittee2(null);
		setReason1("");
		setReason2("");
	}

}
