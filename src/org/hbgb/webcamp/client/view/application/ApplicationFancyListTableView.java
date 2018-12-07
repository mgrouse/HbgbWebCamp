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
package org.hbgb.webcamp.client.view.application;

import org.hbgb.webcamp.client.view.IView;
import org.hbgb.webcamp.shared.ApplicationRow;

import com.google.gwt.view.client.ListDataProvider;

/**
 * @author Michael
 *
 */
public interface ApplicationFancyListTableView extends IView
{

	public interface Presenter
	{
		public void onRowSelect();

		public void onRowEdit(ApplicationRow row);

		public void onRefreshButtonClicked();
	}

	public void setPresenter(Presenter p);

	public void setRowData(ListDataProvider<ApplicationRow> dataProvider);

	public int getYear();

}
