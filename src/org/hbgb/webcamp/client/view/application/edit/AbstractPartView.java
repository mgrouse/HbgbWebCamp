/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 * com.google.gwt.event.dom.client.ClickEvent
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiHandler com.google.gwt.user.client.ui.Button
 * com.google.gwt.user.client.ui.HorizontalPanel
 * com.google.gwt.user.client.ui.VerticalPanel
 */
package org.hbgb.webcamp.client.view.application.edit;

import org.hbgb.webcamp.client.presenter.IPartPresenter;
import org.hbgb.webcamp.client.view.AbstractView;
import org.hbgb.webcamp.client.view.IPartView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class AbstractPartView extends AbstractView implements IPartView
{

	Boolean isExpanded = true;

	// form is coded to be visible in ui.xml

	@UiField
	public VerticalPanel form;

	@UiField
	public HorizontalPanel buttonPanel;

	@UiField
	public Button expandButton;

	@UiField
	public Button saveButton;

	protected IPartPresenter presenter;

	@Override
	public void setPresenter(IPartPresenter p)
	{
		presenter = p;
	}

	@Override
	public void expand()
	{
		isExpanded = true;
		form.setVisible(true);
		expandButton.setHTML("-");
	}

	@Override
	public void contract()
	{
		isExpanded = false;
		form.setVisible(false);
		expandButton.setHTML("+");
	}

	@Override
	public boolean isExpanded()
	{
		return isExpanded;
	}

	@UiHandler(value = { "expandButton" })
	@Override
	public void onExpandButtonClicked(ClickEvent event)
	{
		if (isExpanded.booleanValue())
		{
			contract();
		}
		else
		{
			expand();
		}

		// TODO Not sure this is needed. Design, what would need to happen?
		if (presenter != null)
		{
			presenter.onExpandButtonClicked();
		}
	}

	@UiHandler(value = { "saveButton" })
	@Override
	public void onSaveButtonClicked(ClickEvent event)
	{
		if (this.presenter == null)
			return;
		setSaveButtonEnabled(false);
		presenter.onSaveButtonClicked();
	}

	@Override
	public void setExpandButtonEnabled(Boolean enabled)
	{
		expandButton.setEnabled(enabled.booleanValue());
	}

	@Override
	public void setSaveButtonEnabled(Boolean enabled)
	{
		saveButton.setEnabled(enabled.booleanValue());
	}
}
