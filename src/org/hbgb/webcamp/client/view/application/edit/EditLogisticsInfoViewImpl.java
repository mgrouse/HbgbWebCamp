/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiTemplate
 * com.google.gwt.user.client.ui.CheckBox com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.view.application.edit;

import org.hbgb.webcamp.client.widget.DayOfEventListBox;
import org.hbgb.webcamp.client.widget.PlayaTimeListBox;
import org.hbgb.webcamp.client.widget.TransportationListBox;
import org.hbgb.webcamp.shared.enums.DayOfEvent;
import org.hbgb.webcamp.shared.enums.PlayaTime;
import org.hbgb.webcamp.shared.enums.Transportation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class EditLogisticsInfoViewImpl extends AbstractPartView implements IEditLogisticsInfoView
{
	@UiTemplate(value = "EditLogisticsInfoView.ui.xml")
	static interface EditLogisticsInfoViewImplBinder
			extends UiBinder<Widget, EditLogisticsInfoViewImpl>
	{
	}

	private static UiBinder<Widget, EditLogisticsInfoViewImpl> binder = GWT
			.create(EditLogisticsInfoViewImplBinder.class);

	@UiField
	CheckBox wantsEarlyTeam;

	@UiField
	CheckBox isAssignedEarlyTeam;

	@UiField
	CheckBox wantsStrikeTeam;

	@UiField
	TransportationListBox transportation;

	@UiField
	DayOfEventListBox arrivalDoE;

	@UiField
	PlayaTimeListBox arrivalTime;

	@UiField
	DayOfEventListBox departureDoE;

	@UiField
	PlayaTimeListBox departureTime;

	public EditLogisticsInfoViewImpl()
	{
		initWidget(binder.createAndBindUi(this));
		expand();
	}

	@Override
	public void setWantsEarlyTeam(Boolean bool)
	{
		wantsEarlyTeam.setValue(bool, false);
	}

	@Override
	public Boolean getWantsEarlyTeam()
	{
		return wantsEarlyTeam.getValue();
	}

	@Override
	public void setIsAssignedEarlyTeam(Boolean bool)
	{
		isAssignedEarlyTeam.setValue(bool, false);
	}

	@Override
	public Boolean getIsAssignedEarlyTeam()
	{
		return isAssignedEarlyTeam.getValue();
	}

	@Override
	public void setWantsStrikeTeam(Boolean bool)
	{
		wantsStrikeTeam.setValue(bool, false);
	}

	@Override
	public Boolean getWantsStrikeTeam()
	{
		return wantsStrikeTeam.getValue();
	}

	@Override
	public void setTransportation(Transportation t)
	{
		transportation.setSelectedValue(t);
	}

	@Override
	public Transportation getTransportation()
	{
		return transportation.getSelectedEnumValue();
	}

	@Override
	public void setArrivalDoE(DayOfEvent doe)
	{
		arrivalDoE.setSelectedValue(doe);
	}

	@Override
	public DayOfEvent getArrivalDoE()
	{
		return arrivalDoE.getSelectedEnumValue();
	}

	@Override
	public void setArrivalTime(PlayaTime pt)
	{
		arrivalTime.setSelectedValue(pt);
	}

	@Override
	public PlayaTime getArrivalTime()
	{
		return arrivalTime.getSelectedEnumValue();
	}

	@Override
	public void setDepartureDoE(DayOfEvent doe)
	{
		departureDoE.setSelectedValue(doe);
	}

	@Override
	public DayOfEvent getDepartureDoE()
	{
		return departureDoE.getSelectedEnumValue();
	}

	@Override
	public void setDepartureTime(PlayaTime pt)
	{
		departureTime.setSelectedValue(pt);
	}

	@Override
	public PlayaTime getDepartureTime()
	{
		return departureTime.getSelectedEnumValue();
	}

}
