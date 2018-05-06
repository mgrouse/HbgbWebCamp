/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 * com.google.gwt.user.client.rpc.RemoteService
 * com.google.gwt.user.client.rpc.RemoteServiceRelativePath
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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(value = "applicationService")
public interface ApplicationService extends RemoteService
{
	public String findApplicationKeyByEmail(String var1);

	public String findApplicationEmailByKey(String var1);

	public Application getApplication(String var1);

	public ArrayList<ApplicationDetails> getApplicationDetails(int year);

	public ArrayList<ApplicationRow> getApplicationRows(int year);

	public List<RosterDetails> getAcceptedRosterDetailsByYear(int var1);

	public FindAppResult findOrAddApplication(String var1);

	public Boolean updateApplication(Application var1);

	public Boolean deleteApplication(String var1);

	public ArrayList<ApplicationDetails> deleteApplications(ArrayList<String> var1);

	public List<String> getCircleEmailList(Circle var1);

	public MealsReport getMealsReport();

	public Burner getApplicant(String var1);

	public Boolean updateApplicant(Burner var1);

	public CommitteeInfoBlock getApplicantsCommitteeInfoBlock(String var1);

	public Boolean updateApplicantsCommitteeInfoBlock(CommitteeInfoBlock var1);

	public PaymentInfoBlock getApplicantsPaymentInfoBlock(String var1);

	public Boolean updateApplicantsPaymentInfoBlock(PaymentInfoBlock var1);

	public DietInfoBlock getApplicantsDietInfoBlock(String var1);

	public boolean updateApplicantsDietInfoBlock(DietInfoBlock var1);

	public ShelterInfoBlock getApplicantsShelterInfoBlock(String var1);

	public boolean updateApplicantsShelterInfoBlock(ShelterInfoBlock var1);

	public LogisticsInfoBlock getApplicantsLogisticsInfoBlock(String var1);

	public boolean updateApplicantsLogisticsInfoBlock(LogisticsInfoBlock var1);

	public String updateApplicationObjectSchema();
}
