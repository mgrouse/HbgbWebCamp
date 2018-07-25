package org.hbgb.webcamp.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.hbgb.webcamp.shared.enums.DayOfEvent;
import org.hbgb.webcamp.shared.enums.PlayaTime;
import org.hbgb.webcamp.shared.enums.Transportation;

@SuppressWarnings("serial")
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class LogisticsInfoBlock implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String email = "";

	@Persistent
	private Boolean isProfile = false;

	@Persistent
	private Boolean wantsEarlyTeam = false;

	@Persistent
	private Boolean isAssignedEarlyTeam = false;

	@Persistent
	private Boolean wantsStrikeTeam = false;

	@Persistent
	private Transportation transType;

	@Persistent
	private Date arrivalDate;

	@Persistent
	private DayOfEvent arrivalDoE;

	@Persistent
	private PlayaTime arrivalTime;

	@Persistent
	private Date departureDate;

	@Persistent
	private DayOfEvent departureDoE;

	@Persistent
	private PlayaTime departureTime;

	public LogisticsInfoBlock()
	{
	}

	public LogisticsInfoBlock(String emailText)
	{
		setEmail(emailText);
	}

	public LogisticsInfoBlock(String email, LogisticsInfoBlock source)
	{
		if (null != source)
		{
			setEmail(source.getEmail());
			setIsProfile(source.getIsProfile());
			setWantsEarlyTeam(source.getWantsEarlyTeam());
			// setIsAssignedEarlyTeam(source.getIsAssignedEarlyTeam());
			setWantsStrikeTeam(source.getWantsStrikeTeam());
			setTransType(source.getTransType());
			setArrivalDoE(source.getArrivalDoE());
			setArrivalTime(source.getArrivalTime());
			setDepartureDoE(source.getDepartureDoE());
			setDepartureTime(source.getDepartureTime());
		}
		else
		{
			setEmail(email);
		}
	}

	public String getEncodedKey()
	{
		return encodedKey;
	}

	public void setEncodedKey(String s)
	{
		encodedKey = s;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String s)
	{
		email = s;
	}

	public Boolean getIsProfile()
	{
		return isProfile;
	}

	public void setIsProfile(Boolean b)
	{
		isProfile = b;
	}

	public Boolean getWantsEarlyTeam()
	{
		return wantsEarlyTeam;
	}

	public void setWantsEarlyTeam(Boolean b)
	{
		wantsEarlyTeam = b;
	}

	public Boolean getIsAssignedEarlyTeam()
	{
		return isAssignedEarlyTeam;
	}

	public void setIsAssignedEarlyTeam(Boolean b)
	{
		isAssignedEarlyTeam = b;
	}

	public Boolean getWantsStrikeTeam()
	{
		return wantsStrikeTeam;
	}

	public void setWantsStrikeTeam(Boolean b)
	{
		wantsStrikeTeam = b;
	}

	public Transportation getTransType()
	{
		return transType;
	}

	public void setTransType(Transportation t)
	{
		transType = t;
	}

	@Deprecated
	public Date getArrivalDate()
	{
		return arrivalDate;
	}

	@Deprecated
	public void setArrivalDate(Date d)
	{
		arrivalDate = d;
	}

	public PlayaTime getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(PlayaTime p)
	{
		arrivalTime = p;
	}

	@Deprecated
	public Date getDepartureDate()
	{
		return departureDate;
	}

	@Deprecated
	public void setDepartureDate(Date d)
	{
		departureDate = d;
	}

	public PlayaTime getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(PlayaTime p)
	{
		departureTime = p;
	}

	public DayOfEvent getArrivalDoE()
	{
		return arrivalDoE;
	}

	public void setArrivalDoE(DayOfEvent d)
	{
		arrivalDoE = d;
	}

	public DayOfEvent getDepartureDoE()
	{
		return departureDoE;
	}

	public void setDepartureDoE(DayOfEvent d)
	{
		departureDoE = d;
	}
}
