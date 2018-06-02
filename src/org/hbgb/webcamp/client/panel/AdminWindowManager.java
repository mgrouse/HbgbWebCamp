/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.core.client.RunAsyncCallback
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.user.client.ui.Composite
 * com.google.gwt.user.client.ui.HasWidgets com.google.gwt.user.client.ui.Label
 * com.google.gwt.user.client.ui.ResizeComposite
 * com.google.gwt.user.client.ui.ScrollPanel
 * com.google.gwt.user.client.ui.Widget
 * com.google.web.bindery.event.shared.Event
 * com.google.web.bindery.event.shared.Event$Type
 * com.google.web.bindery.event.shared.HandlerRegistration
 */
package org.hbgb.webcamp.client.panel;

import org.hbgb.webcamp.client.applet.ApplicationListByYearApplet;
import org.hbgb.webcamp.client.applet.ApplicationListFancyApplet;
import org.hbgb.webcamp.client.applet.EarlyTeamListApplet;
import org.hbgb.webcamp.client.applet.UserListApplet;
import org.hbgb.webcamp.client.event.MenuEvent;
import org.hbgb.webcamp.client.event.MenuHandler;
import org.hbgb.webcamp.client.event.SingletonEventBus;
import org.hbgb.webcamp.client.event.StatusEvent;
import org.hbgb.webcamp.client.event.StatusHandler;
import org.hbgb.webcamp.client.panel.element.ContentPanel;
import org.hbgb.webcamp.client.presenter.admin.SendEmailPresenter;
import org.hbgb.webcamp.client.presenter.report.MealsReportPresenter;
import org.hbgb.webcamp.client.presenter.report.ShelterReportPresenter;
import org.hbgb.webcamp.client.widget.StatusBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class AdminWindowManager extends ResizeComposite implements StatusHandler, MenuHandler
{
	static interface AdminWindowManagerBinder extends UiBinder<Widget, AdminWindowManager>
	{
	}

	private static UiBinder<Widget, AdminWindowManager> binder = GWT
			.create(AdminWindowManagerBinder.class);

	private static final String CAMPERS_APPLICATIONS = "campers-applications";

	private static final String SEND_MAIL = "send-email";

	private static final String CAMPERS_APPLICATIONS_FANCY = "campers-applications-fancy";

	private static final String REPORT_MEALS = "report-meals";

	private static final String REPORT_SHELTER = "report-shelter";

	private static final String UTILS_EARLY_TEAM = "utils-early-team";

	private static final String DEV_USERS = "dev-users";

	@UiField
	Label logoLabel;

	@UiField
	ContentPanel contentPanel;

	@UiField
	StatusBar status;

	public AdminWindowManager()
	{
		initWidget(binder.createAndBindUi(this));
		SingletonEventBus.get().addHandler(StatusEvent.TYPE, this);
		SingletonEventBus.get().addHandler(MenuEvent.TYPE, this);
		if (GWT.isProdMode())
		{
			contentPanel.addTab("Log", new LogTab());
		}
	}

	@Override
	public void onStatusChange(StatusEvent event)
	{
		this.status.setText(event.getStatus());
	}

	@Override
	public void onMenuSelection(MenuEvent menuEvent)
	{
		switch (menuEvent.getMenu())
		{
		case CAMPERS_APPLICATIONS:
			openApplications();
			break;

		case SEND_MAIL:
			openMail();
			break;

		case CAMPERS_APPLICATIONS_FANCY:
			openApplicationsFancy();
			break;

		case REPORT_MEALS:
			openMealseReport();
			break;

		case REPORT_SHELTER:
			openShelterReport();
			break;

		case UTILS_EARLY_TEAM:
			openEarlyTeam();
			break;

		case DEV_USERS:
			openUsers();
			break;

		}

	}

	private void openShelterReport()
	{
		WorkTab wTab = new WorkTab();
		ShelterReportPresenter report = new ShelterReportPresenter();
		report.setScreen(wTab.getScrollPanel());
		report.report();
		contentPanel.addTab("Shelter Report", wTab);
	}

	/**
	 * 
	 */
	private void openApplicationsFancy()
	{
		GWT.runAsync(new RunAsyncCallback()
		{

			@Override
			public void onFailure(Throwable caught)
			{
			}

			@Override
			public void onSuccess()
			{
				WorkTab wTab = new WorkTab();
				ApplicationListFancyApplet appList = new ApplicationListFancyApplet();
				appList.run(wTab.getScrollPanel());
				contentPanel.addTab("Fancy Application List", wTab);
			}
		});

	}

	private void openApplications()
	{
		GWT.runAsync(new RunAsyncCallback()
		{

			@Override
			public void onFailure(Throwable caught)
			{
			}

			@Override
			public void onSuccess()
			{
				WorkTab wTab = new WorkTab();
				ApplicationListByYearApplet appList = new ApplicationListByYearApplet();
				appList.run(wTab.getScrollPanel());
				contentPanel.addTab("Application List", wTab);
			}
		});
	}

	private void openMail()
	{
		GWT.runAsync(new RunAsyncCallback()
		{

			@Override
			public void onFailure(Throwable caught)
			{
			}

			@Override
			public void onSuccess()
			{
				WorkTab wTab = new WorkTab();

				SendEmailPresenter p = new SendEmailPresenter();
				p.setScreen(wTab.getScrollPanel());
				p.go();

				contentPanel.addTab("Send Email", wTab);
			}
		});
	}

	private void openMealseReport()
	{
		WorkTab wTab = new WorkTab();
		MealsReportPresenter report = new MealsReportPresenter();
		report.setScreen(wTab.getScrollPanel());
		report.report();
		contentPanel.addTab("Meals Report", wTab);
	}

	private void openUsers()
	{
		GWT.runAsync(new RunAsyncCallback()
		{

			@Override
			public void onFailure(Throwable caught)
			{
			}

			@Override
			public void onSuccess()
			{
				WorkTab wTab = new WorkTab();
				UserListApplet users = new UserListApplet();
				users.run(wTab.getScrollPanel());
				contentPanel.addTab("User List", wTab);
			}
		});
	}

	private void openEarlyTeam()
	{
		GWT.runAsync(new RunAsyncCallback()
		{

			@Override
			public void onFailure(Throwable caught)
			{
			}

			@Override
			public void onSuccess()
			{
				WorkTab wTab = new WorkTab();
				EarlyTeamListApplet et = new EarlyTeamListApplet();
				et.run(wTab.getScrollPanel());
				contentPanel.addTab("Early Team List", wTab);
			}
		});
	}

}
