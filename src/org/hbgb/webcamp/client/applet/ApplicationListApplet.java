/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 * com.google.gwt.event.shared.EventHandler com.google.gwt.event.shared.GwtEvent
 * com.google.gwt.event.shared.GwtEvent$Type
 * com.google.gwt.event.shared.HandlerManager
 * com.google.gwt.event.shared.HandlerRegistration
 * com.google.gwt.user.client.ui.HasWidgets
 */
package org.hbgb.webcamp.client.applet;

import org.hbgb.webcamp.client.event.application.EditApplicationEvent;
import org.hbgb.webcamp.client.event.application.EditApplicationEventHandler;
import org.hbgb.webcamp.client.event.application.ListApplicationEvent;
import org.hbgb.webcamp.client.event.application.ListApplicationEventHandler;
import org.hbgb.webcamp.client.presenter.application.ApplicationListPresenter;
import org.hbgb.webcamp.client.presenter.application.EditWholeApplicationPresenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

@Deprecated
public class ApplicationListApplet implements IApplet
{
	private final HandlerManager eventBus = new HandlerManager(null);
	private HasWidgets screen;
	private final ApplicationListPresenter applicationListPresenter = new ApplicationListPresenter(
			eventBus);
	private final EditWholeApplicationPresenter editWholeApplicationPresenter = new EditWholeApplicationPresenter(
			eventBus);

	@Deprecated
	public ApplicationListApplet()
	{
		registerEvents();
	}

	private void registerEvents()
	{
		eventBus.addHandler(EditApplicationEvent.TYPE, new EditApplicationEventHandler()
		{

			@Override
			public void onEditApplication(EditApplicationEvent event)
			{
				screen.clear();
				editWholeApplicationPresenter.setScreen(screen);
				editWholeApplicationPresenter.setKey(event.getKey());
				editWholeApplicationPresenter.setKeyList(event.getKeyList());
			}
		});
		eventBus.addHandler(ListApplicationEvent.TYPE, new ListApplicationEventHandler()
		{

			@Override
			public void onListApplication(ListApplicationEvent event)
			{
				screen.clear();
				applicationListPresenter.go();
			}
		});
	}

	@Override
	public void run(HasWidgets container)
	{
		screen = container;
		applicationListPresenter.setScreen(container);
		applicationListPresenter.go();
	}

}
