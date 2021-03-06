/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 * com.google.gwt.dom.client.Style com.google.gwt.event.dom.client.ClickEvent
 * com.google.gwt.uibinder.client.UiBinder
 * com.google.gwt.uibinder.client.UiField
 * com.google.gwt.uibinder.client.UiHandler
 * com.google.gwt.uibinder.client.UiTemplate com.google.gwt.user.client.Element
 * com.google.gwt.user.client.ui.CheckBox com.google.gwt.user.client.ui.HTML
 * com.google.gwt.user.client.ui.HTMLPanel
 * com.google.gwt.user.client.ui.TextArea com.google.gwt.user.client.ui.Widget
 */
package org.hbgb.webcamp.client.view.application.input;

import org.hbgb.webcamp.client.presenter.ISequentialPresenter;
import org.hbgb.webcamp.client.view.AbstractView;
import org.hbgb.webcamp.client.widget.MessagesWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InputShelterInfoViewImpl extends AbstractView implements InputShelterInfoView
{
	@UiTemplate(value = "InputShelterInfoView.ui.xml")
	static interface InputShelterInfoViewBinder extends UiBinder<Widget, InputShelterInfoViewImpl>
	{}

	private static UiBinder<Widget, InputShelterInfoViewImpl> binder = GWT.create(InputShelterInfoViewBinder.class);

	@UiField
	MessagesWidget messages;

	@UiField
	Label hasRvLabel;

	@UiField
	CheckBox hasRv;

	@UiField
	VerticalPanel rvInfoPanel;

	@UiField
	HTML rvInfoHTML;

	@UiField
	TextArea rvInfo;

	@UiField
	Label isInDormTentLabel;

	@UiField
	CheckBox isInDormTent;

	@UiField
	Label hasStructureLabel;

	@UiField
	CheckBox hasStructure;

	@UiField
	VerticalPanel structureInfoPanel;

	@UiField
	HTML structureInfoHTML;

	@UiField
	TextArea structureInfo;

	@UiField
	Button nextButton;

	private ISequentialPresenter presenter;

	public InputShelterInfoViewImpl()
	{
		initWidget(binder.createAndBindUi(this));

		structureInfo.getElement().setAttribute("maxlength", "50");
		rvInfo.getElement().setAttribute("maxlength", "50");

		messages.clear();
	}

	@Override
	public void setPresenter(ISequentialPresenter sp)
	{
		presenter = sp;
	}

	@UiHandler(value = { "nextButton" })
	void onNextButtonClicked(ClickEvent event)
	{
		if (presenter != null)
		{
			if (isFormComplete())
			{
				presenter.onNextButtonClicked();
			}
		}
	}

	protected Boolean isFormComplete()
	{
		Boolean retVal = true;
		clearErrorState();

		if (!getHasRv() && !getIsInDormTent() && !getHasStructure())
		{
			retVal = false;

			hasRvLabel.getElement().getStyle().setColor("red");
			isInDormTentLabel.getElement().getStyle().setColor("red");
			hasStructureLabel.getElement().getStyle().setColor("red");

			addMessage("Please pick at least one option in red");
		}

		// if rv is checked better have a rvInfo
		if (getHasRv() && this.getRvInfoText().isEmpty())
		{
			retVal = false;

			rvInfoHTML.getElement().getStyle().setColor("red");
			addMessage("Please describe your RV");
		}

		// if Structure is checked better have a structureInfo
		if (getHasStructure() && this.getStructureInfoText().isEmpty())
		{
			retVal = false;

			structureInfoHTML.getElement().getStyle().setColor("red");
			addMessage("Please describe your Structure");
		}

		return retVal;
	}

	private void clearErrorState()
	{
		hasRvLabel.getElement().getStyle().setColor("black");
		isInDormTentLabel.getElement().getStyle().setColor("black");
		hasStructureLabel.getElement().getStyle().setColor("black");

		rvInfoHTML.getElement().getStyle().setColor("black");
		structureInfoHTML.getElement().getStyle().setColor("black");

		messages.clear();
	}

	@UiHandler(value = { "hasRv", "hasStructure" })
	protected void onCheckBoxChange(ClickEvent event)
	{
		// if RV
		if (getHasRv())
		{

			rvInfoPanel.setVisible(true);
		}
		else
		{
			rvInfoPanel.setVisible(false);
		}

		// if Structure
		if (getHasStructure())
		{
			structureInfoPanel.setVisible(true);
		}
		else
		{
			structureInfoPanel.setVisible(false);
		}

	}

	@Override
	public void setHasRv(Boolean bool)
	{
		hasRv.setValue(bool, true);
		onCheckBoxChange(null);
	}

	@Override
	public Boolean getHasRv()
	{
		return hasRv.getValue();
	}

	@Override
	public void setRvInfoText(String text)
	{
		rvInfo.setText(text);
	}

	@Override
	public String getRvInfoText()
	{
		return rvInfo.getText();
	}

	@Override
	public void setIsInDormTent(Boolean bool)
	{
		isInDormTent.setValue(bool, false);
	}

	@Override
	public Boolean getIsInDormTent()
	{
		return isInDormTent.getValue();
	}

	@Override
	public void setHasStructure(Boolean bool)
	{
		hasStructure.setValue(bool, true);
		onCheckBoxChange(null);
	}

	@Override
	public Boolean getHasStructure()
	{
		return hasStructure.getValue();
	}

	@Override
	public void setStructureInfoText(String text)
	{
		structureInfo.setText(text);
	}

	@Override
	public String getStructureInfoText()
	{
		return structureInfo.getText();
	}

	@Override
	public void addMessage(String text)
	{
		if (text != null && !text.isEmpty())
		{
			messages.addMessageIfUnique(text);
			messages.setVisible(true);
		}
	}

	@Override
	public void setNextButtonActive(boolean b)
	{
		nextButton.setEnabled(b);
	}

}
