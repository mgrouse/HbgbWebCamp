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
package org.hbgb.webcamp.client.model;

import org.hbgb.webcamp.client.presenter.IReportPresenter;
import org.hbgb.webcamp.shared.ShelterReport;

/**
 * @author Michael
 *
 */
public interface IShelterReportModel
{
	public void setPresenter(IReportPresenter var1);

	public void fetchData();

	public ShelterReport getReportData();
}
