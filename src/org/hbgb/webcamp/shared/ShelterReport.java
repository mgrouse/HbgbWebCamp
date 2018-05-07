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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 *
 */

@SuppressWarnings("serial")
public class ShelterReport implements Serializable
{
	private ArrayList<Shelter> shelters;

	private ArrayList<Car> cars;

	private ArrayList<String> badList;

	public ShelterReport()
	{
		shelters = new ArrayList<>();
		cars = new ArrayList<>();
		badList = new ArrayList<>();
	}

	public void addShelter(Shelter s)
	{
		shelters.add(s);
	}

	public List<Shelter> getShelters()
	{
		return shelters;
	}

	public void addCar(Car car)
	{
		cars.add(car);
	}

	public List<Car> getCars()
	{
		return cars;
	}

	public void addBadEmail(String email)
	{
		badList.add(email);
	}

	public List<String> getBadEmails()
	{
		return badList;
	}
}
