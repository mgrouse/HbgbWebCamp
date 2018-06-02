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

import java.util.Date;

/**
 * @author Michael
 *
 */
public class Utils
{

	static public Boolean isNullOrEmpty(String text)
	{
		return ((null == text) || (text.isEmpty()));
	}

	static public String nullOrString(Object o)
	{
		if (null != o)
		{
			return o.toString();
		}
		else
		{
			return "Null";
		}
	}

	@SuppressWarnings("deprecation")
	static public int getThisYearInt()
	{
		Date today = new Date();

		// return 2017;
		return today.getYear() + 1900;
	}
}
