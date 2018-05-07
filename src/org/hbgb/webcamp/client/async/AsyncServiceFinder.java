/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.gwt.core.client.GWT
 */
package org.hbgb.webcamp.client.async;

import com.google.gwt.core.client.GWT;

public class AsyncServiceFinder
{
	private static ApplicationServiceAsync applicationService;
	private static HealerSheetServiceAsync healerSheetService;
	private static EmailServiceAsync emailService;
	private static BlobStoreUploadURLServiceAsync blobstoreURLService;
	private static LoginServiceAsync loginService;
	private static HbgbAppUserServiceAsync usrService;
	private static ShelterReportServiceAsync shelterService;

	private AsyncServiceFinder()
	{
	}

	public static ApplicationServiceAsync getApplicationService()
	{
		if (applicationService != null)
			return applicationService;
		applicationService = (ApplicationServiceAsync) GWT.create(ApplicationService.class);
		return applicationService;
	}

	public static HealerSheetServiceAsync getHealerSheetService()
	{
		if (healerSheetService != null)
			return healerSheetService;
		healerSheetService = (HealerSheetServiceAsync) GWT.create(HealerSheetService.class);
		return healerSheetService;
	}

	public static EmailServiceAsync getEmailService()
	{
		if (emailService != null)
			return emailService;
		emailService = (EmailServiceAsync) GWT.create(EmailService.class);
		return emailService;
	}

	public static BlobStoreUploadURLServiceAsync getBlobStoreUploadURLService()
	{
		if (blobstoreURLService != null)
			return blobstoreURLService;
		blobstoreURLService = (BlobStoreUploadURLServiceAsync) GWT
				.create(BlobStoreUploadURLService.class);
		return blobstoreURLService;
	}

	public static LoginServiceAsync getLoginService()
	{
		if (loginService != null)
			return loginService;
		loginService = (LoginServiceAsync) GWT.create(LoginService.class);
		return loginService;
	}

	public static HbgbAppUserServiceAsync getHbgbUserService()
	{
		if (usrService != null)
			return usrService;
		usrService = (HbgbAppUserServiceAsync) GWT.create(HbgbAppUserService.class);
		return usrService;
	}

	public static ShelterReportServiceAsync getShelterReportService()
	{
		if (shelterService != null)
			return shelterService;
		shelterService = (ShelterReportServiceAsync) GWT.create(ShelterReportService.class);
		return shelterService;
	}
}
