/*
 * Decompiled with CFR 0_115.
 */
package org.hbgb.webcamp.client.presenter.application.edit;

import org.hbgb.webcamp.client.model.LogisticsInfoBlockModel;
import org.hbgb.webcamp.client.view.application.edit.EditLogisticsInfoViewImpl;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;

public class EditLogisticsInfoPresenter extends AbstractInfoBlockPresenter
{
	static EditLogisticsInfoViewImpl view = new EditLogisticsInfoViewImpl();
	static LogisticsInfoBlockModel model = new LogisticsInfoBlockModel();

	public EditLogisticsInfoPresenter()
	{
		super(model, view);
	}

	@Override
	protected void setViewData()
	{
		LogisticsInfoBlock logisticsInfoBlock = model.getData();
		view.setWantsEarlyTeam(logisticsInfoBlock.getWantsEarlyTeam());
		view.setIsAssignedEarlyTeam(logisticsInfoBlock.getIsAssignedEarlyTeam());
		view.setWantsStrikeTeam(logisticsInfoBlock.getWantsStrikeTeam());
		view.setTransportation(logisticsInfoBlock.getTransType());
		view.setArrivalDoE(logisticsInfoBlock.getArrivalDoE());
		view.setArrivalTime(logisticsInfoBlock.getArrivalTime());
		view.setDepartureDoE(logisticsInfoBlock.getDepartureDoE());
		view.setDepartureTime(logisticsInfoBlock.getDepartureTime());
	}

	@Override
	protected void setModelData()
	{
		LogisticsInfoBlock logisticsInfoBlock = model.getData();
		logisticsInfoBlock.setWantsEarlyTeam(view.getWantsEarlyTeam());
		logisticsInfoBlock.setIsAssignedEarlyTeam(view.getIsAssignedEarlyTeam());
		logisticsInfoBlock.setWantsStrikeTeam(view.getWantsStrikeTeam());
		logisticsInfoBlock.setTransType(view.getTransportation());
		logisticsInfoBlock.setArrivalDoE(view.getArrivalDoE());
		logisticsInfoBlock.setArrivalTime(view.getArrivalTime());
		logisticsInfoBlock.setDepartureDoE(view.getDepartureDoE());
		logisticsInfoBlock.setDepartureTime(view.getDepartureTime());
	}
}
