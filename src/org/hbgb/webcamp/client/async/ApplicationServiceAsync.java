/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 * com.google.gwt.user.client.rpc.AsyncCallback
 */
package org.hbgb.webcamp.client.async;

import java.util.ArrayList;
import java.util.List;

import org.hbgb.webcamp.shared.Application;
import org.hbgb.webcamp.shared.ApplicationDetails;
import org.hbgb.webcamp.shared.ApplicationRow;
import org.hbgb.webcamp.shared.Burner;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.DietInfoBlock;
import org.hbgb.webcamp.shared.FindAppResult;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;
import org.hbgb.webcamp.shared.MealsReport;
import org.hbgb.webcamp.shared.PaymentInfoBlock;
import org.hbgb.webcamp.shared.RosterDetails;
import org.hbgb.webcamp.shared.ShelterInfoBlock;
import org.hbgb.webcamp.shared.enums.Circle;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ApplicationServiceAsync
{
	public void findApplicationKeyByEmail(String var1, AsyncCallback<String> var2);

	public void findApplicationEmailByKey(String var1, AsyncCallback<String> var2);

	public void getApplication(String var1, AsyncCallback<Application> var2);

	public void getApplicationDetails(int i, AsyncCallback<ArrayList<ApplicationDetails>> var1);

	void getApplicationRows(int year, AsyncCallback<ArrayList<ApplicationRow>> callback);

	public void findOrAddApplication(String var1, AsyncCallback<FindAppResult> var2);

	public void updateApplication(Application var1, AsyncCallback<Boolean> var2);

	public void deleteApplication(String var1, AsyncCallback<Boolean> var2);

	public void deleteApplications(ArrayList<String> var1,
			AsyncCallback<ArrayList<ApplicationDetails>> var2);

	public void getAcceptedRosterDetailsByYear(int var1, AsyncCallback<List<RosterDetails>> var2);

	public void getCircleEmailList(Circle var1, AsyncCallback<List<String>> var2);

	public void getMealsReport(AsyncCallback<MealsReport> var1);

	public void getApplicantsCommitteeInfoBlock(String var1,
			AsyncCallback<CommitteeInfoBlock> var2);

	public void updateApplicantsCommitteeInfoBlock(CommitteeInfoBlock var1,
			AsyncCallback<String> var2);

	public void getApplicant(String var1, AsyncCallback<Burner> var2);

	public void updateApplicant(Burner var1, AsyncCallback<Boolean> var2);

	public void getApplicantsPaymentInfoBlock(String var1, AsyncCallback<PaymentInfoBlock> var2);

	public void updateApplicantsPaymentInfoBlock(PaymentInfoBlock var1,
			AsyncCallback<Boolean> var2);

	public void getApplicantsDietInfoBlock(String var1, AsyncCallback<DietInfoBlock> var2);

	public void updateApplicantsDietInfoBlock(DietInfoBlock var1, AsyncCallback<Boolean> var2);

	public void getApplicantsShelterInfoBlock(String var1, AsyncCallback<ShelterInfoBlock> var2);

	public void updateApplicantsShelterInfoBlock(ShelterInfoBlock var1,
			AsyncCallback<Boolean> var2);

	public void getApplicantsLogisticsInfoBlock(String var1,
			AsyncCallback<LogisticsInfoBlock> var2);

	public void updateApplicantsLogisticsInfoBlock(LogisticsInfoBlock var1,
			AsyncCallback<Boolean> var2);

	public void updateApplicationObjectSchema(AsyncCallback<String> var1);

}
