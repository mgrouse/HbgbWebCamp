package org.hbgb.webcamp.client;

import java.util.logging.Logger;

import org.hbgb.webcamp.client.applet.ApplicationListFancyApplet;
import org.hbgb.webcamp.client.applet.ApplicationProcessApplet;
import org.hbgb.webcamp.client.applet.HealerSheetListApplet;
import org.hbgb.webcamp.client.panel.AdminWindowManager;
import org.hbgb.webcamp.client.presenter.admin.RosterPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebCampEntryPoint implements EntryPoint, ValueChangeHandler<String>
{
	private static final Logger log = Logger.getLogger(WebCampEntryPoint.class.getName());
	private static final RootLayoutPanel rp = RootLayoutPanel.get();

	@Override
	public void onModuleLoad()
	{
		log.info("Module loaded. BaseURL: " + GWT.getModuleBaseURL());

		History.addValueChangeHandler(this);

		if ("".equals(History.getToken()))
		{
			History.newItem("apply");
		}
		else
		{
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event)
	{
		String token = event.getValue();

		if (token != null)
		{
			switch (token)
			{
			case "admin":
				startAdminPanel();
				break;

			case "apply":
				startApplication();
				break;

			case "roster":
				startRoster();
				break;

			case "sheet":
				startSheet();
				break;

			case "test":
				startTest();
			}
		}
	}

	private void startAdminPanel()
	{
		AdminWindowManager winMan = new AdminWindowManager();
		rp.clear();
		rp.add(winMan);
	}

	private void startApplication()
	{
		ApplicationProcessApplet appProcApp = new ApplicationProcessApplet();
		rp.clear();
		appProcApp.run(rp);
	}

	private void startSheet()
	{
		HealerSheetListApplet healerApp = new HealerSheetListApplet();
		rp.clear();
		healerApp.run(rp);
	}

	private void startRoster()
	{
		RosterPresenter presenter = new RosterPresenter();
		rp.clear();
		presenter.setScreen(rp);
		presenter.go();
	}

	private void startTest()
	{
		ApplicationListFancyApplet app = new ApplicationListFancyApplet();
		rp.clear();
		app.run(rp);
	}
}
