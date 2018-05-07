/*
 * This is an unpublished work protected by the United States copyright laws and
 * is proprietary to Michael A. Grouse. Disclosure, copying, reproduction,
 * merger translation, modification, enhancement or use by anyone other than
 * authorized employees or licensees of Michael A. Grouse without prior written
 * consent of Michael A. Grouse is prohibited.
 *
 * Copyright (C) 1992 - 2018 Michael A. Grouse, All Rights Reserved.
 *
 * This copyright notice should not be construed as evidence of publication.
 */
package org.hbgb.webcamp.shared;

import java.io.Serializable;

import org.hbgb.webcamp.shared.enums.ShelterType;

/**
 * @author Michael
 *
 */
@SuppressWarnings("serial")
public class Shelter implements Serializable
{
	private ShelterType type;
	private String name = "";
	private String email = "";
	private String description = "";

	public Shelter()
	{

	}

	/**
	 * @param type2
	 * @param name2
	 * @param email2
	 * @param desc
	 */
	public Shelter(ShelterType type, String name, String email, String desc)
	{
		this.type = type;
		this.name = name;
		this.email = email;
		this.description = desc;
	}

	public ShelterType getType()
	{
		return type;
	}

	public void setType(ShelterType type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
