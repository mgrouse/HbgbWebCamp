/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes: com.google.appengine.api.datastore.Text
 * com.google.gwt.user.server.rpc.RemoteServiceServlet
 * javax.jdo.PersistenceManager javax.jdo.Query
 */
package org.hbgb.webcamp.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.hbgb.webcamp.client.async.ApplicationService;
import org.hbgb.webcamp.shared.Address;
import org.hbgb.webcamp.shared.Application;
import org.hbgb.webcamp.shared.ApplicationDetails;
import org.hbgb.webcamp.shared.Burner;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.ContactInfo;
import org.hbgb.webcamp.shared.Demographics;
import org.hbgb.webcamp.shared.DietInfoBlock;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;
import org.hbgb.webcamp.shared.MealsInfo;
import org.hbgb.webcamp.shared.MealsReport;
import org.hbgb.webcamp.shared.PaymentInfoBlock;
import org.hbgb.webcamp.shared.RosterDetails;
import org.hbgb.webcamp.shared.ShelterInfoBlock;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.Committee;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService
{
	private static final int THIS_YEAR = 2017;

	@Override
	public Application getApplication(String encoded)
	{
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, encoded);
			application = pm.detachCopy(application);
		}
		finally
		{
			pm.close();
		}

		return application;
	}

	@Override
	public String findApplicationKeyByEmail(String email)
	{
		String retVal = null;

		Application app = getApplicationByEmail(email);

		if (null != app)
		{
			retVal = app.getEncodedKey();
		}

		return retVal;
	}

	@Override
	public String findApplicationEmailByKey(String encoded)
	{
		String retVal = null;
		Application app = this.getApplication(encoded);
		if (app != null)
		{
			retVal = app.getEmail();
		}

		return retVal;
	}

	public Application getApplicationByEmail(String email)
	{
		Application app = null;

		PersistenceManager pm = getPM();

		Query q = pm.newQuery(Application.class);
		q.setFilter("email == emailParam");
		q.declareParameters("String emailParam");
		q.setOrdering("year DESC");

		try
		{
			@SuppressWarnings("unchecked")
			List<Application> results = (List<Application>) q.execute(email);
			if (!results.isEmpty())
			{
				app = results.get(0);
				app = pm.detachCopy(app);
			}
		}
		finally
		{
			// TODO find out how to make all detaches cascade
			pm.close();
		}

		return app;
	}

	@Override
	public MealsReport getMealsReport()
	{
		ArrayList<MealsInfo> meals = new ArrayList<>();

		List<Application> applications = getApplications();

		for (Application app : applications)
		{
			if (app.getStatus() == ApplicationStatus.ACCEPTED)
			{
				meals.add(application2MealsInfo(app));
			}
		}

		MealsReport report = new MealsReport();

		for (MealsInfo mi : meals)
		{
			report.addMealsInfo(mi);
		}

		return report;
	}

	@Override
	public ArrayList<ApplicationDetails> getApplicationDetails()
	{
		ArrayList<ApplicationDetails> details = new ArrayList<>();

		List<Application> applications = getApplications();

		for (Application app : applications)
		{
			details.add(application2Details(app));
		}

		Collections.sort(details);

		return details;
	}

	// this only lets you add if its not in there already
	// IFF it is not in there the new version of the object is returned (with
	// new keys etc.)
	// IF it IS in there already the object is returned indicating the object
	// was NOT added

	@Override
	public Application findOrAddApplication(String email)
	{
		Application retVal = null;

		Application test = getApplicationByEmail(email);

		if (null == test)
		{
			retVal = createBlankApplicationByEmail(email);
		}
		else
		{
			if (THIS_YEAR == test.getYear())
			{
				retVal = test;
			}
			else
			{
				retVal = createCopyApplicationForThisYear(test);
			}
		}

		return retVal;
	}

	private Application createBlankApplicationByEmail(String email)
	{
		PersistenceManager pm = getPM();

		Application app = new Application(email);

		try
		{
			pm.makePersistent(app);
			app = pm.detachCopy(app);
		}
		finally
		{
			pm.close();
		}

		return app;
	}

	private Application createCopyApplicationForThisYear(Application source)
	{
		PersistenceManager pm = getPM();

		Application dbApp = pm.getObjectById(Application.class, source.getEncodedKey());

		Application copy = new Application(dbApp);

		try
		{
			pm.makePersistent(copy);
			copy = pm.detachCopy(copy);
		}
		finally
		{
			pm.close();
		}

		return copy;
	}

	@Override
	public Boolean updateApplication(Application app)
	{
		PersistenceManager pm = getPM();
		try
		{
			Application dbApp = pm.getObjectById(Application.class, app.getEncodedKey());

			dbApp.setEdited(new Date());

			dbApp.setStatus(app.getStatus());
			dbApp.setYear(app.getYear());
			dbApp.setImageURL(app.getImageURL());

			pm.makePersistent(dbApp);
		}
		finally
		{
			pm.close();
		}

		return true;
	}

	@Override
	public Boolean deleteApplication(String encoded)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			Application app = pm.getObjectById(Application.class, encoded);
			pm.deletePersistent(app);
			return true;
		}
		finally
		{
			pm.close();
		}
	}

	@Override
	public ArrayList<ApplicationDetails> deleteApplications(ArrayList<String> encodedStrings)
	{
		for (String encoded : encodedStrings)
		{
			deleteApplication(encoded);
		}
		return getApplicationDetails();
	}

	@SuppressWarnings("unchecked")
	private List<Application> getApplications()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Application.class);
		query.setFilter("year == yearParam");
		query.declareParameters("int yearParam");
		query.setOrdering("status ASC");

		List<Application> entries = null;

		try
		{
			entries = (List<Application>) query.execute(THIS_YEAR);
		}
		catch (Exception e)
		{
			// String text = e.getMessage();
		}

		return entries;
	}

	@SuppressWarnings("unchecked")
	private List<Application> getApplicationsByStatus(ApplicationStatus status)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query query = pm.newQuery(Application.class);
		query.setFilter("year == yearParam && status == statusParam");
		query.declareParameters("int yearParam, String statusParam");
		query.setOrdering("status ASC");

		List<Application> entries = null;
		try
		{
			entries = (List<Application>) query.execute(THIS_YEAR, status.name());
		}
		catch (Exception e)
		{
			// String string = e.getMessage();
		}
		return entries;
	}

	@Override
	public List<String> getCommitteeEmails(Committee com)
	{
		ArrayList<String> emails = new ArrayList<>();
		List<Application> entries = getApplicationsByStatus(ApplicationStatus.ACCEPTED);

		if (entries != null)
		{
			for (Application app : entries)
			{
				CommitteeInfoBlock cib = app.getCommitteeInfoBlock();
				if (cib != null && com == cib.getAssignedCommittee() && app.getEmail() != null && !app.getEmail().isEmpty())
				{
					emails.add(app.getEmail());
				}
			}
		}
		return emails;
	}

	private ApplicationDetails application2Details(Application a)
	{
		String encodedKey = a.getEncodedKey();

		String status = "NULL";
		if (null != a.getStatus())
		{
			status = a.getStatus().toString();
		}

		String displayName = a.getApplicant().getDemographics().getFullName();
		String playaName = a.getApplicant().getDemographics().getPlayaName();

		String committee = "None";
		if (null != a.getCommitteeInfoBlock().getAssignedCommittee())
		{
			committee = a.getCommitteeInfoBlock().getAssignedCommittee().toString();
		}

		String ticket = "NULL";
		if (null != a.getPaymentInfoBlock().getHasTicket())
		{
			ticket = a.getPaymentInfoBlock().getHasTicket().toString();
		}

		String invoiced = "NULL";
		if (null != a.getPaymentInfoBlock().getHasBeenInvoiced())
		{
			invoiced = a.getPaymentInfoBlock().getHasBeenInvoiced().toString();
		}

		String paid = "NULL";
		if (null != a.getPaymentInfoBlock().getHasPaidDues())
		{
			paid = a.getPaymentInfoBlock().getHasPaidDues().toString();
		}

		String diet = "NULL";
		if (null != a.getDietInfoBlock().getDietType())
		{
			diet = a.getDietInfoBlock().getDietType().toString();
		}

		String email = a.getEmail();

		ApplicationDetails d = new ApplicationDetails(encodedKey, status, displayName, playaName, committee, ticket, invoiced, paid, diet, email);

		return d;
	}

	private MealsInfo application2MealsInfo(Application a)
	{
		MealsInfo meals = new MealsInfo();

		meals.setArrivalDate(a.getLogisticsInfoBlock().getArrivalDoE());
		meals.setArrivalTime(a.getLogisticsInfoBlock().getArrivalTime());
		meals.setDepartureDate(a.getLogisticsInfoBlock().getDepartureDoE());
		meals.setDepartureTime(a.getLogisticsInfoBlock().getDepartureTime());

		meals.setDietType(a.getDietInfoBlock().getDietType());
		meals.setIsGlutenFree(a.getDietInfoBlock().getIsGlutenFree());

		meals.setDietaryRestrictions(a.getDietInfoBlock().getDietaryRestrictions());
		return meals;
	}

	private PersistenceManager getPM()
	{
		return PMF.get().getPersistenceManager();
	}

	@Override
	public CommitteeInfoBlock getApplicantsCommitteeInfoBlock(String encoded)
	{
		CommitteeInfoBlock cBlock = null;
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, encoded);
			cBlock = application.getCommitteeInfoBlock();
			cBlock = pm.detachCopy(cBlock);

		}
		finally
		{
			pm.close();
		}
		return cBlock;
	}

	@Override
	public Boolean updateApplicantsCommitteeInfoBlock(CommitteeInfoBlock ciBlock)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent((Object) ciBlock);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public Burner getApplicant(String encoded)
	{
		Burner b = null;
		Demographics d = null;
		ContactInfo ci = null;
		Address a = null;

		Application application = null;

		PersistenceManager pm = getPM();
		try
		{
			application = pm.getObjectById(Application.class, encoded);
			// we will build up the full list of "touches" to return
			// a COMPLETE Burner Object. Probably gonna b slow!!!

			if (null != application)
			{
				b = application.getApplicant();

				d = b.getDemographics();
				d = pm.detachCopy(d);

				ci = b.getContactInfo();

				if (null != ci)
				{
					a = ci.getAddress();
					a = pm.detachCopy(a);
				}
				ci = pm.detachCopy(ci);
				ci.setAddress(a);
			}

			b = pm.detachCopy(b);
			b.setDemographics(d);
			b.setContactInfo(ci);

			// a = pm.detachCopy(a);
			// b.getContactInfo().setAddress(a);
		}
		finally
		{
			pm.close();
		}
		return b;
	}

	@Override
	public Boolean updateApplicant(Burner b)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent((Object) b);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public PaymentInfoBlock getApplicantsPaymentInfoBlock(String key)
	{
		PaymentInfoBlock piBlock = null;
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, key);
			piBlock = application.getPaymentInfoBlock();
			piBlock = pm.detachCopy(piBlock);
			return piBlock;
		}
		finally
		{
			pm.close();
		}
	}

	@Override
	public Boolean updateApplicantsPaymentInfoBlock(PaymentInfoBlock piBlock)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent(piBlock);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public DietInfoBlock getApplicantsDietInfoBlock(String key)
	{
		DietInfoBlock diBlock = null;
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, key);
			diBlock = application.getDietInfoBlock();
			diBlock = pm.detachCopy(diBlock);

		}
		finally
		{
			pm.close();
		}
		return diBlock;
	}

	@Override
	public boolean updateApplicantsDietInfoBlock(DietInfoBlock diBlock)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent(diBlock);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public ShelterInfoBlock getApplicantsShelterInfoBlock(String key)
	{
		ShelterInfoBlock siBlock = null;
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, key);
			siBlock = application.getShelterInfoBlock();
			siBlock = pm.detachCopy(siBlock);

		}
		finally
		{
			pm.close();
		}
		return siBlock;
	}

	@Override
	public boolean updateApplicantsShelterInfoBlock(ShelterInfoBlock shelterInfoBlock)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent(shelterInfoBlock);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public LogisticsInfoBlock getApplicantsLogisticsInfoBlock(String key)
	{
		LogisticsInfoBlock liBlock = null;
		Application application = null;
		PersistenceManager pm = this.getPM();
		try
		{
			application = pm.getObjectById(Application.class, key);
			liBlock = application.getLogisticsInfoBlock();
			liBlock = pm.detachCopy(liBlock);

		}
		finally
		{
			pm.close();
		}
		return liBlock;
	}

	@Override
	public boolean updateApplicantsLogisticsInfoBlock(LogisticsInfoBlock logisticsInfoBlock)
	{
		PersistenceManager pm = this.getPM();
		try
		{
			pm.makePersistent(logisticsInfoBlock);

		}
		finally
		{
			pm.close();
		}
		return true;
	}

	@Override
	public String updateApplicationObjectSchema()
	{
		return "Not Implemented";
	}

	@SuppressWarnings("unchecked")
	private List<Application> getAcceptedApplicationsByYear(String year)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Application.class);
		query.setFilter("year == yearParam && status == statusParam");
		query.declareParameters("int yearParam, String statusParam");
		List<Application> entries = null;
		try
		{
			entries = (List<Application>) query.execute(new Integer(year), "ACCEPTED");
		}
		finally
		{

		}

		return entries;
	}

	private RosterDetails application2RosterDetails(Application app)
	{
		String encodedKey = app.getEncodedKey();

		// look up "Photo"
		String photoURL = "http://storage.googleapis.com/hbgbwebcamp.appspot.com/PhotoNotAvailable.jpg";
		if ((null != app.getImageURL()) && !app.getImageURL().isEmpty())
		{
			photoURL = app.getImageURL();
		}

		String playaName = "";
		String firstName = "";
		String bio = "";

		Burner dude = app.getApplicant();
		if ((null != dude) && (null != dude.getDemographics()))
		{
			Demographics demos = dude.getDemographics();
			playaName = demos.getPlayaName();
			firstName = demos.getFirstName();
			bio = demos.getBio().getValue();
		}

		String homeTown = "";
		if ((null != dude) && (null != dude.getContactInfo()) && (null != dude.getContactInfo().getAddress()))
		{
			Address add = dude.getContactInfo().getAddress();
			homeTown = add.getCity() + ", " + add.getState();
		}

		String committee = "None";
		if ((null != app.getCommitteeInfoBlock()) && (null != app.getCommitteeInfoBlock().getAssignedCommittee()))
		{
			committee = app.getCommitteeInfoBlock().getAssignedCommittee().toString();
		}

		RosterDetails d = new RosterDetails(encodedKey, photoURL, playaName, firstName, homeTown, committee, bio);

		return d;
	}

	@Override
	public List<RosterDetails> getAcceptedRosterDetailsByYear(String year)
	{
		List<RosterDetails> details = new ArrayList<>();

		List<Application> roster = getAcceptedApplicationsByYear(year);

		for (Application app : roster)
		{
			details.add(application2RosterDetails(app));
		}

		Collections.sort(details);

		return details;
	}
}
