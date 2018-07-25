/*
 * Decompiled with CFR 0_115.
 */
package org.hbgb.webcamp.client.view;

import org.hbgb.webcamp.client.view.admin.EarlyTeamMemberViewImpl;
import org.hbgb.webcamp.client.view.admin.IEarlyTeamMemberView;
import org.hbgb.webcamp.client.view.admin.ISendEmailView;
import org.hbgb.webcamp.client.view.admin.IUserEditView;
import org.hbgb.webcamp.client.view.admin.SendEmailViewImpl;
import org.hbgb.webcamp.client.view.admin.UserEditViewImpl;
import org.hbgb.webcamp.client.view.admin.UserListView;
import org.hbgb.webcamp.client.view.admin.UserListViewImpl;
import org.hbgb.webcamp.client.view.application.ApplicationListByYearView;
import org.hbgb.webcamp.client.view.application.ApplicationListByYearViewImpl;
import org.hbgb.webcamp.client.view.application.ApplicationListView;
import org.hbgb.webcamp.client.view.application.ApplicationListViewImpl;
import org.hbgb.webcamp.client.view.application.EditApplicationViewImpl;
import org.hbgb.webcamp.client.view.application.HealerSheetListView;
import org.hbgb.webcamp.client.view.application.HealerSheetListViewImpl;
import org.hbgb.webcamp.client.view.application.IEditApplicationView;
import org.hbgb.webcamp.client.view.application.input.IStartView;
import org.hbgb.webcamp.client.view.application.input.InputCommitteeInfoView;
import org.hbgb.webcamp.client.view.application.input.InputCommitteeInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.InputDietInfoView;
import org.hbgb.webcamp.client.view.application.input.InputDietInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.InputLogisticsInfoView;
import org.hbgb.webcamp.client.view.application.input.InputLogisticsInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.InputPaymentInfoView;
import org.hbgb.webcamp.client.view.application.input.InputPaymentInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.InputPersonalInfoView;
import org.hbgb.webcamp.client.view.application.input.InputPersonalInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.InputShelterInfoView;
import org.hbgb.webcamp.client.view.application.input.InputShelterInfoViewImpl;
import org.hbgb.webcamp.client.view.application.input.StartViewImpl;
import org.hbgb.webcamp.client.view.application.input.UploadPhotoView;
import org.hbgb.webcamp.client.view.application.input.UploadPhotoViewImpl;
import org.hbgb.webcamp.shared.ApplicationDetails;
import org.hbgb.webcamp.shared.HbgbUserDetails;
import org.hbgb.webcamp.shared.HealerSheetDetails;

public class ViewFinder
{
	private static IEarlyTeamMemberView earlyTeamMemberView;
	private static IUserEditView editUserView;
	private static UserListView<HbgbUserDetails> listUserView;
	private static IEditApplicationView editApplicationView;
	private static ApplicationListView<ApplicationDetails> applicationListView;
	private static ApplicationListByYearView<ApplicationDetails> applicationListPastView;
	private static IStartView enterView;
	private static InputLogisticsInfoView editLogisticsView;
	private static InputShelterInfoView editShelterView;
	private static InputDietInfoView editDietView;
	private static InputCommitteeInfoView editCommitteeView;
	private static InputPaymentInfoView editPaymentView;
	private static InputPersonalInfoView editPersonalView;
	private static HealerSheetListView<HealerSheetDetails> healerSheetListView;
	private static UploadPhotoView uplaodPhotoView;
	private static ISendEmailView sendEmailView;

	private ViewFinder()
	{
	}

	public static IEarlyTeamMemberView getEarlyTeamMemberView()
	{
		if (earlyTeamMemberView != null)
			return earlyTeamMemberView;
		earlyTeamMemberView = new EarlyTeamMemberViewImpl();
		return earlyTeamMemberView;
	}

	public static IUserEditView getEditUserView()
	{
		if (editUserView != null)
			return editUserView;
		editUserView = new UserEditViewImpl();
		return editUserView;
	}

	public static UserListView<HbgbUserDetails> getUserListView()
	{
		if (listUserView != null)
			return listUserView;
		listUserView = new UserListViewImpl<>();
		return listUserView;
	}

	public static IEditApplicationView getEditApplicationView()
	{
		if (editApplicationView != null)
			return editApplicationView;
		editApplicationView = new EditApplicationViewImpl();
		return editApplicationView;
	}

	public static ApplicationListView<ApplicationDetails> getApplicationListView()
	{
		if (applicationListView != null)
			return applicationListView;
		applicationListView = new ApplicationListViewImpl<>();
		return applicationListView;
	}

	public static ApplicationListByYearView<ApplicationDetails> getApplicationListPastView()
	{
		if (applicationListPastView != null)
			return applicationListPastView;
		applicationListPastView = new ApplicationListByYearViewImpl<>();
		return applicationListPastView;
	}

	public static HealerSheetListView<HealerSheetDetails> getHealerSheetListView()
	{
		if (healerSheetListView != null)
			return healerSheetListView;
		healerSheetListView = new HealerSheetListViewImpl<>();
		return healerSheetListView;
	}

	public static InputCommitteeInfoView getCommitteeInfoView()
	{
		if (editCommitteeView != null)
			return editCommitteeView;
		editCommitteeView = new InputCommitteeInfoViewImpl();
		return editCommitteeView;
	}

	public static InputPersonalInfoView getPersonalInfoView()
	{
		if (editPersonalView != null)
			return editPersonalView;
		editPersonalView = new InputPersonalInfoViewImpl();
		return editPersonalView;
	}

	public static IStartView getEnterView()
	{
		if (enterView != null)
			return enterView;
		enterView = new StartViewImpl();
		return enterView;
	}

	public static InputPaymentInfoView getPaymentInfoView()
	{
		if (editPaymentView != null)
			return editPaymentView;
		editPaymentView = new InputPaymentInfoViewImpl();
		return editPaymentView;
	}

	public static InputDietInfoView getDietInfoView()
	{
		if (editDietView != null)
			return editDietView;
		editDietView = new InputDietInfoViewImpl();
		return editDietView;
	}

	public static InputShelterInfoView getShelterInfoView()
	{
		if (editShelterView != null)
			return editShelterView;
		editShelterView = new InputShelterInfoViewImpl();
		return editShelterView;
	}

	public static InputLogisticsInfoView getLogisticsInfoView()
	{
		if (editLogisticsView == null)
		{
			editLogisticsView = new InputLogisticsInfoViewImpl();
		}
		return editLogisticsView;
	}

	public static UploadPhotoView getUploadPhotoView()
	{
		if (uplaodPhotoView != null)
			return uplaodPhotoView;
		uplaodPhotoView = new UploadPhotoViewImpl();
		return uplaodPhotoView;
	}

	public static ISendEmailView getSendEmailView()
	{
		if (sendEmailView != null)
			return sendEmailView;
		sendEmailView = new SendEmailViewImpl();
		return sendEmailView;
	}

}
