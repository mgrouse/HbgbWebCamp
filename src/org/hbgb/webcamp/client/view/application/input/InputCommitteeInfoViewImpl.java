
package org.hbgb.webcamp.client.view.application.input;

import org.hbgb.webcamp.client.presenter.ISequentialPresenter;
import org.hbgb.webcamp.client.view.AbstractView;
import org.hbgb.webcamp.client.widget.CircleListBox;
import org.hbgb.webcamp.client.widget.MessagesWidget;
import org.hbgb.webcamp.shared.enums.Circle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InputCommitteeInfoViewImpl extends AbstractView implements InputCommitteeInfoView
{
	@UiTemplate(value = "InputCommitteeInfoView.ui.xml")
	static interface InputComitteeInfoViewImplBinder
			extends UiBinder<Widget, InputCommitteeInfoViewImpl>
	{
	}

	private static UiBinder<Widget, InputCommitteeInfoViewImpl> binder = GWT
			.create(InputComitteeInfoViewImplBinder.class);

	@UiField
	MessagesWidget messages;

	@UiField
	Label committee1Label;

	@UiField
	CircleListBox committee1ListBox;

	@UiField
	HTML reason1HTML;

	@UiField
	TextArea reason1;

	@UiField
	Label committee2Label;

	@UiField
	CircleListBox committee2ListBox;

	@UiField
	HTML reason2HTML;

	@UiField
	TextArea reason2;

	@UiField
	Panel healerSheetPanel;

	@UiField
	Label sessionLengthLabel;

	@UiField
	TextBox sessionLength;

	@UiField
	Label modality1Label;

	@UiField
	TextBox modality1;

	@UiField
	TextBox modality2;

	@UiField
	TextBox modality3;

	@UiField
	TextBox modality4;

	@UiField
	HTML bioHTML;

	@UiField
	TextArea bioBox;

	@UiField
	Button nextButton;

	private ISequentialPresenter presenter;

	public InputCommitteeInfoViewImpl()
	{
		initWidget(binder.createAndBindUi(this));

		messages.clear();

		reason1.getElement().setAttribute("maxlength", "250");
		reason2.getElement().setAttribute("maxlength", "250");

		healerSheetPanel.setVisible(false);

		bioBox.getElement().setAttribute("maxlength", "500");
	}

	@Override
	public void setPresenter(ISequentialPresenter sp)
	{
		presenter = sp;
	}

	@UiHandler(value = { "committee1ListBox", "committee2ListBox" })
	void onCommitteeChosen(ChangeEvent event)
	{
		setHealerSheetPanelVisibility();
	}

	@UiHandler(value = { "nextButton" })
	void onNextButtonClicked(ClickEvent event)
	{
		if ((presenter != null) && (formIsValid()))
		{
			nextButton.setEnabled(false);
			presenter.onNextButtonClicked();
		}
	}

	private void setHealerSheetPanelVisibility()
	{
		if (committee1ListBox.getSelectedEnumValue() == Circle.Healers
				|| committee2ListBox.getSelectedEnumValue() == Circle.Healers)
		{
			healerSheetPanel.setVisible(true);
		}
		else
		{
			healerSheetPanel.setVisible(false);
		}

	}

	private boolean formIsValid()
	{
		Boolean retVal = true;

		clearErrorState();

		// committee1ListBox
		if (null == committee1ListBox.getSelectedEnumValue())
		{
			addMessage("Please answer the question(s) in red.");
			committee1Label.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// committee2ListBox
		if (null == committee2ListBox.getSelectedEnumValue())
		{
			addMessage("Please answer the question(s) in red.");
			committee2Label.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// comittee choices different?
		if (null != committee1ListBox.getSelectedEnumValue() && (committee1ListBox
				.getSelectedEnumValue() == committee2ListBox.getSelectedEnumValue()))
		{
			addMessage("Please pick two different committees.");
			committee1Label.getElement().getStyle().setColor("red");
			committee2Label.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// reason1
		if (reason1.getText().isEmpty())
		{
			addMessage("Please answer the question(s) in red.");
			reason1HTML.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// reason2
		if (reason2.getText().isEmpty())
		{
			addMessage("Please answer the question(s) in red.");
			reason2HTML.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// sessionLength
		if ((healerSheetPanel.isVisible()) && (getSessionLength().isEmpty()))
		{
			addMessage("Please answer the question(s) in red.");
			sessionLengthLabel.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// modality1
		if ((healerSheetPanel.isVisible()) && getModality1().isEmpty())
		{
			addMessage("Please answer the question(s) in red.");
			modality1Label.getElement().getStyle().setColor("red");
			retVal = false;
		}

		// bioBox
		if ((healerSheetPanel.isVisible()) && getBioBoxText().isEmpty())
		{
			addMessage("Please answer the question(s) in red.");
			bioHTML.getElement().getStyle().setColor("red");
			retVal = false;
		}

		return retVal;
	}

	private void clearErrorState()
	{
		committee1Label.getElement().getStyle().setColor("black");
		reason1HTML.getElement().getStyle().setColor("black");

		committee2Label.getElement().getStyle().setColor("black");
		reason2HTML.getElement().getStyle().setColor("black");

		sessionLengthLabel.getElement().getStyle().setColor("black");
		modality1Label.getElement().getStyle().setColor("black");
		bioHTML.getElement().getStyle().setColor("black");

		messages.clear();
	}

	@Override
	public void setCommittee1(Circle c)
	{
		committee1ListBox.setSelectedValue(c);
		setHealerSheetPanelVisibility();
	}

	@Override
	public Circle getCommittee1()
	{
		return committee1ListBox.getSelectedEnumValue();
	}

	@Override
	public void setReason1(String text)
	{
		reason1.setValue(text, false);
	}

	@Override
	public String getReason1()
	{
		return reason1.getText();
	}

	@Override
	public void setCommittee2(Circle c)
	{
		committee2ListBox.setSelectedValue(c);
		setHealerSheetPanelVisibility();
	}

	@Override
	public Circle getCommittee2()
	{
		return committee2ListBox.getSelectedEnumValue();
	}

	@Override
	public void setReason2(String text)
	{
		reason2.setValue(text, false);
	}

	@Override
	public String getReason2()
	{
		return reason2.getText();
	}

	@Override
	public void setSessionLength(String text)
	{
		sessionLength.setText(text);
	}

	@Override
	public String getSessionLength()
	{
		return sessionLength.getText();
	}

	@Override
	public void setModality1(String text)
	{
		modality1.setText(text);
	}

	@Override
	public String getModality1()
	{
		return modality1.getText();
	}

	@Override
	public void setModality2(String text)
	{
		modality2.setText(text);
	}

	@Override
	public String getModality2()
	{
		return modality2.getText();
	}

	@Override
	public void setModality3(String text)
	{
		modality3.setText(text);
	}

	@Override
	public String getModality3()
	{
		return modality3.getText();
	}

	@Override
	public void setModality4(String text)
	{
		modality4.setText(text);
	}

	@Override
	public String getModality4()
	{
		return modality4.getText();
	}

	@Override
	public void setBioBoxText(String text)
	{
		bioBox.setValue(text, false);
	}

	@Override
	public String getBioBoxText()
	{
		return bioBox.getText();
	}

	@Override
	public void clear()
	{
		setCommittee1(null);
		setCommittee2(null);
		setReason1("");
		setReason2("");

		setModality1("");
		setModality2("");
		setModality3("");
		setModality4("");
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
