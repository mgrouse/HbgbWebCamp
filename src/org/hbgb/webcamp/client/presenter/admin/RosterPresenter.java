/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.user.client.Window
 * com.google.gwt.user.client.rpc.AsyncCallback
 * com.google.gwt.user.client.ui.HasWidgets com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.presenter.admin;

import java.util.List;

import org.hbgb.webcamp.client.async.ApplicationServiceAsync;
import org.hbgb.webcamp.client.async.AsyncServiceFinder;
import org.hbgb.webcamp.client.presenter.IPresenter;
import org.hbgb.webcamp.client.view.application.HTMLPanelView;
import org.hbgb.webcamp.client.view.application.HTMLPanelViewImpl;
import org.hbgb.webcamp.shared.RosterDetails;
import org.hbgb.webcamp.shared.Utils;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class RosterPresenter implements IPresenter
{
	private final ApplicationServiceAsync rpcService = AsyncServiceFinder.getApplicationService();
	HTMLPanelView view = new HTMLPanelViewImpl();
	HasWidgets screen;

	@Override
	public void setScreen(HasWidgets container)
	{
		screen = container;
	}

	@Override
	public void go()
	{
		view.clear();
		fetchData();
	}

	private void fetchData()
	{
		rpcService.getAcceptedRosterDetailsByYear(Utils.getThisYearInt(),
				new AsyncCallback<List<RosterDetails>>()
				{

					@Override
					public void onSuccess(List<RosterDetails> result)
					{
						setViewHTML(result);
						screen.add(view.asWidget());
					}

					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("Error fetching RosterDetails");
					}
				});
	}

	private void setViewHTML(List<RosterDetails> roster)
	{
		StringBuilder html = new StringBuilder();
		html.append("<table class=\"page-break\">");

		if (roster.isEmpty())
		{
			html.append("<tr>");

			html.append("<td class=\"rosterBio\">");
			html.append("No Data to Display.");
			html.append("</td>");

			html.append("</tr>");
		}
		else
		{
			for (RosterDetails details : roster)
			{
				html.append("<tr border=1; class=\"page-break\">");

				html.append("<td class=\"rosterPic\">");
				html.append("<img src=" + details.getPhotoURL() + "=s600-c>");

				html.append("</td>");

				html.append("<td class=\"rosterBio\">");

				html.append("<h2>First Name: " + details.getFirstName() + " </h2> <br/>");

				if (details.getPlayaName() != null && !details.getPlayaName().isEmpty())
				{
					html.append("<h2>Playa Name: " + details.getPlayaName() + " </h2> <br/>");
				}

				html.append("Home town: " + details.getHomeTown() + " <br/>");
				html.append("Circle: " + details.getCommittee() + " <br/>");
				html.append("Bio: " + details.getBio());
				html.append("</td>");
				html.append("</tr>");
			}
		}
		html.append("</table>");
		view.setHTML(html.toString());
	}

}
