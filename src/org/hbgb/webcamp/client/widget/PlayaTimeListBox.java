/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiFactory
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiTemplate
 * com.google.gwt.user.client.ui.Composite com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.widget;

import org.hbgb.webcamp.shared.enums.PlayaTime;

public class PlayaTimeListBox extends EnumListBox<PlayaTime>
{

	public PlayaTimeListBox()
	{
		super(PlayaTime.class);
	}

	// private static UiBinder<Widget, PlayaTimeListBox> binder =
	// GWT.create(PlayaTimeListBoxBinder.class);
	// @UiField
	// EnumListBox<PlayaTime> list;
	//
	// PlayaTimeListBox()
	// {
	// this.initWidget(binder.createAndBindUi(this));
	// }
	//
	// public void setSelectedValue(PlayaTime value)
	// {
	// this.list.setSelectedValue(value);
	// }
	//
	// public PlayaTime getSelectedValue()
	// {
	// return this.list.getSelectedEnumValue();
	// }
	//
	// @UiFactory
	// EnumListBox<PlayaTime> listBoxFactory()
	// {
	// return new EnumListBox<>(PlayaTime.class);
	// }
	//
	// @UiTemplate(value = "PLayaTimeListBox.ui.xml")
	// static interface PlayaTimeListBoxBinder extends UiBinder<Widget,
	// PlayaTimeListBox>
	// {}

}
