/*
 * Decompiled with CFR 0_115.
 */
package org.hbgb.webcamp.client.view.application.edit;

import org.hbgb.webcamp.shared.enums.DayOfEvent;
import org.hbgb.webcamp.shared.enums.PlayaTime;
import org.hbgb.webcamp.shared.enums.Transportation;

public interface IEditLogisticsInfoView
{
	public void setWantsEarlyTeam(Boolean var1);

	public Boolean getWantsEarlyTeam();

	public void setIsAssignedEarlyTeam(Boolean var1);

	public Boolean getIsAssignedEarlyTeam();

	public void setWantsStrikeTeam(Boolean var1);

	public Boolean getWantsStrikeTeam();

	public void setTransportation(Transportation var1);

	public Transportation getTransportation();

	public void setArrivalDoE(DayOfEvent var1);

	public DayOfEvent getArrivalDoE();

	public void setArrivalTime(PlayaTime var1);

	public PlayaTime getArrivalTime();

	public void setDepartureDoE(DayOfEvent var1);

	public DayOfEvent getDepartureDoE();

	public void setDepartureTime(PlayaTime var1);

	public PlayaTime getDepartureTime();
}
