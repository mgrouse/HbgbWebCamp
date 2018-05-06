/*
 * This is an unpublished work protected by the United States copyright laws and
 * is proprietary to Michael A. Grouse. Disclosure, copying, reproduction,
 * merger translation, modification, enhancement or use by anyone other than
 * authorized employees or licensees of Michael A. Grouse without prior written
 * consent of Michael A. Grouse is prohibited.
 *
 * Copyright (C) 1992 - 2017 Michael A. Grouse, All Rights Reserved.
 *
 * This copyright notice should not be construed as evidence of publication.
 */
package org.hbgb.webcamp.shared;

import java.io.Serializable;

import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.Circle;
import org.hbgb.webcamp.shared.enums.DayOfEvent;
import org.hbgb.webcamp.shared.enums.DietType;

import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Michael
 *
 */

@SuppressWarnings("serial")
public class ApplicationRow implements Comparable<ApplicationRow>, Serializable
{

	public static final ProvidesKey<ApplicationRow> KEY_PROVIDER = new ProvidesKey<ApplicationRow>()
	{
		@Override
		public Object getKey(ApplicationRow item)
		{
			return item == null ? null : item.getEncodedKey();
		}
	};

	private String encodedKey;

	private ApplicationStatus status;

	private String firstName;
	private String lastName;
	private String playaName;
	private String email;

	private Circle choice1;
	private Circle choice2;
	private Circle circle;

	private DietType diet;

	private Boolean hasPaid;
	private Boolean hasTicket;

	private Boolean wantsET;
	private Boolean isET;
	private Boolean isStrike;

	private DayOfEvent arrive;
	private DayOfEvent depart;

	private Boolean hasRV;
	private Boolean hasTent;
	private Boolean hasStructure;

	public ApplicationRow()
	{

	}

	public String getEncodedKey()
	{
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey)
	{
		this.encodedKey = encodedKey;
	}

	public ApplicationStatus getStatus()
	{
		return status;
	}

	public void setStatus(ApplicationStatus status)
	{
		this.status = status;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getPlayaName()
	{
		return playaName;
	}

	public void setPlayaName(String playaName)
	{
		this.playaName = playaName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Circle getCircle()
	{
		return circle;
	}

	public void setCircle(Circle committee)
	{
		this.circle = committee;
	}

	public DietType getDiet()
	{
		return diet;
	}

	public void setDiet(DietType diet)
	{
		this.diet = diet;
	}

	public Boolean getHasTicket()
	{
		return hasTicket;
	}

	public void setHasTicket(Boolean hasTicket)
	{
		this.hasTicket = hasTicket;
	}

	public Boolean getIsET()
	{
		return isET;
	}

	public void setIsET(Boolean isET)
	{
		this.isET = isET;
	}

	public Boolean getIsStrike()
	{
		return isStrike;
	}

	public void setIsStrike(Boolean isStrike)
	{
		this.isStrike = isStrike;
	}

	public Boolean getHasRV()
	{
		return hasRV;
	}

	public void setHasRV(Boolean hasRV)
	{
		this.hasRV = hasRV;
	}

	public Boolean getHasStructure()
	{
		return hasStructure;
	}

	public Boolean getHasPaid()
	{
		return hasPaid;
	}

	public void setHasPaid(Boolean hasPaid)
	{
		this.hasPaid = hasPaid;
	}

	public Boolean getHasTent()
	{
		return hasTent;
	}

	public void setHasTent(Boolean hasTent)
	{
		this.hasTent = hasTent;
	}

	public void setHasStructure(Boolean hasStructure)
	{
		this.hasStructure = hasStructure;
	}

	public Circle getChoice1()
	{
		return choice1;
	}

	public void setChoice1(Circle choice1)
	{
		this.choice1 = choice1;
	}

	public Circle getChoice2()
	{
		return choice2;
	}

	public void setChoice2(Circle choice2)
	{
		this.choice2 = choice2;
	}

	public Boolean getWantsET()
	{
		return wantsET;
	}

	public void setWantsET(Boolean wantsET)
	{
		this.wantsET = wantsET;
	}

	public DayOfEvent getArrive()
	{
		return arrive;
	}

	public void setArrive(DayOfEvent arrive)
	{
		this.arrive = arrive;
	}

	public DayOfEvent getDepart()
	{
		return depart;
	}

	public void setDepart(DayOfEvent depart)
	{
		this.depart = depart;
	}

	@Override
	public int compareTo(ApplicationRow row)
	{
		int retVal = status.compareTo(row.status);

		if (0 == retVal)
		{
			retVal = circle.compareTo(row.circle);
		}

		if (0 == retVal)
		{
			retVal = lastName.compareTo(row.lastName);
		}

		return retVal;
	}

}
