
package org.hbgb.webcamp.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.hbgb.webcamp.client.async.ApplicationService;
import org.hbgb.webcamp.shared.Address;
import org.hbgb.webcamp.shared.Application;
import org.hbgb.webcamp.shared.ApplicationDetails;
import org.hbgb.webcamp.shared.ApplicationRow;
import org.hbgb.webcamp.shared.Burner;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.ContactInfo;
import org.hbgb.webcamp.shared.Demographics;
import org.hbgb.webcamp.shared.DietInfoBlock;
import org.hbgb.webcamp.shared.FindAppResult;
import org.hbgb.webcamp.shared.LogisticsInfoBlock;
import org.hbgb.webcamp.shared.MealsInfo;
import org.hbgb.webcamp.shared.MealsReport;
import org.hbgb.webcamp.shared.PaymentInfoBlock;
import org.hbgb.webcamp.shared.RosterDetails;
import org.hbgb.webcamp.shared.ShelterInfoBlock;
import org.hbgb.webcamp.shared.Utils;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.Circle;
import org.hbgb.webcamp.shared.enums.FindApp;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ApplicationServiceImpl extends RemoteServiceServlet implements ApplicationService
{
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());

	private static final ApplicationStatus noShows[] = { ApplicationStatus.ARCHIVED };

	@Override // returns only the Application Object, the child objects are not
				// present
	public Application getApplication(String encoded)
	{
		Application application = null;
		PersistenceManager pm = getPM();
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
		Application app = getApplication(encoded);
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
	public ArrayList<ApplicationDetails> getApplicationDetails(int year)
	{
		ArrayList<ApplicationDetails> details = new ArrayList<>();

		List<Application> applications = getApplications(year);

		Boolean add;

		for (Application app : applications)
		{
			add = true;

			for (ApplicationStatus bad : noShows)
			{
				if (bad == app.getStatus())
				{
					add = false;
					break;
				}
			}

			if (add)
			{
				details.add(application2Details(app));
			}
		}

		Collections.sort(details);

		return details;
	}

	@Override
	public ArrayList<ApplicationRow> getApplicationRows()
	{
		ArrayList<ApplicationRow> rows = new ArrayList<>();

		List<Application> applications = purgeNoShows(getApplications(Utils.getThisYearInt()));

		for (Application app : applications)
		{
			rows.add(Application2Row(app));
		}

		return rows;
	}

	private List<Application> purgeNoShows(List<Application> list)
	{
		boolean copy;
		ArrayList<Application> retList = new ArrayList<>();

		for (Application row : list)
		{
			copy = true;

			for (ApplicationStatus status : noShows)
			{
				if (status == row.getStatus())
				{
					copy = false;
					break;
				}
			}
			if (copy)
			{
				retList.add(row);
			}
		}

		return retList;
	}

	private ApplicationRow Application2Row(Application app)
	{
		ApplicationRow row = new ApplicationRow();

		row.setEncodedKey(app.getEncodedKey());
		row.setStatus(app.getStatus());

		row.setFirstName(app.getApplicant().getDemographics().getFirstName());
		row.setLastName(app.getApplicant().getDemographics().getLastName());
		row.setPlayaName(app.getApplicant().getDemographics().getPlayaName());

		row.setEmail(app.getEmail());

		row.setCommittee(app.getCommitteeInfoBlock().getAssignedCommittee());

		row.setDiet(app.getDietInfoBlock().getDietType());

		row.setHasTicket(app.getPaymentInfoBlock().getHasTicket());

		row.setIsET(app.getLogisticsInfoBlock().getIsAssignedEarlyTeam());
		row.setIsStrike(app.getLogisticsInfoBlock().getWantsStrikeTeam());

		row.setHasRV(app.getShelterInfoBlock().getIsBringingRv());
		row.setHasStructure(app.getShelterInfoBlock().getHasStructure());

		return row;
	}

	// this only lets you add if its not in there already
	// IFF it is not in there the new version of the object is returned (with
	// new keys etc.)
	// IF it IS in there already the object is returned indicating the object
	// was NOT added

	@Override
	public FindAppResult findOrAddApplication(String email)
	{
		FindAppResult retVal = new FindAppResult();

		Application test = getApplicationByEmail(email);

		if (null == test)
		{
			retVal.setKey(createBlankApplicationByEmail(email));
			retVal.setFind(FindApp.Created);
		}
		else
		{
			if (Utils.getThisYearInt() == test.getYear())
			{
				retVal.setKey(new String(test.getEncodedKey()));
				retVal.setFind(FindApp.Found);
			}
			else
			{
				retVal.setKey(createCopyApplicationForThisYear(test));
				retVal.setFind(FindApp.Copy);
			}
		}

		return retVal;
	}

	private String createBlankApplicationByEmail(String email)
	{
		String retVal = null;
		PersistenceManager pm = getPM();

		Application app = new Application(email);

		try
		{
			app = pm.makePersistent(app);

			// need to clone since app.<any member> will go way on pm.colse()
			retVal = new String(app.getEncodedKey());
		}
		finally
		{
			pm.close();
		}

		return retVal;
	}

	private String createCopyApplicationForThisYear(Application source)
	{
		String retVal = null;
		PersistenceManager pm = getPM();

		// source was gotten as a top level object only. It has no members
		// we need to fetch proper detached members and set them.
		// TODO figure out how to make a detachCopy(application) call cascade
		// all the way down
		// put that wisdom in getApplicationByEmail(String email)
		source.setApplicant(getApplicant(source.getEncodedKey()));
		source.setCommitteeInfoBlock(getApplicantsCommitteeInfoBlock(source.getEncodedKey()));
		source.setDietInfoBlock(getApplicantsDietInfoBlock(source.getEncodedKey()));
		source.setLogisticsInfoBlock(getApplicantsLogisticsInfoBlock(source.getEncodedKey()));
		source.setPaymentInfoBlock(getApplicantsPaymentInfoBlock(source.getEncodedKey()));
		source.setShelterInfoBlock(getApplicantsShelterInfoBlock(source.getEncodedKey()));

		Application copy = new Application(source);

		try
		{
			copy = pm.makePersistent(copy);

			// need to clone since app.<any member> will go way on pm.colse()
			retVal = new String(copy.getEncodedKey());
		}
		finally
		{
			pm.close();
		}

		return retVal;
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
		return getApplicationDetails(Utils.getThisYearInt());
	}

	@SuppressWarnings("unchecked")
	private List<Application> getApplications(int year)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Application.class);
		query.setFilter("year == yearParam");
		query.declareParameters("int yearParam");
		query.setOrdering("status ASC");

		List<Application> entries = null;

		try
		{
			entries = (List<Application>) query.execute(year);
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
			entries = (List<Application>) query.execute(Utils.getThisYearInt(), status.name());
		}
		catch (Exception e)
		{
			// String string = e.getMessage();
		}
		return entries;
	}

	@Override
	public List<String> getCircleEmailList(Circle circle)
	{
		ArrayList<String> emails = new ArrayList<>();
		List<Application> entries = getApplicationsByStatus(ApplicationStatus.ACCEPTED);

		if (entries != null)
		{
			for (Application app : entries)
			{
				CommitteeInfoBlock cib = app.getCommitteeInfoBlock();
				if (cib != null && circle == cib.getAssignedCommittee() && app.getEmail() != null
						&& !app.getEmail().isEmpty())
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

		String earlyTeam = "NULL";
		if (null != a.getLogisticsInfoBlock().getIsAssignedEarlyTeam())
		{
			earlyTeam = a.getLogisticsInfoBlock().getIsAssignedEarlyTeam().toString();
		}

		String ticket = "NULL";
		if (null != a.getPaymentInfoBlock().getHasTicket())
		{
			ticket = a.getPaymentInfoBlock().getHasTicket().toString();
		}

		// String invoiced = "NULL";
		// if (null != a.getPaymentInfoBlock().getHasBeenInvoiced())
		// {
		// invoiced = a.getPaymentInfoBlock().getHasBeenInvoiced().toString();
		// }

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

		ApplicationDetails d = new ApplicationDetails(encodedKey, status, displayName, playaName,
				committee, earlyTeam, ticket, paid, diet, email);

		return d;
	}

	@Override
	public MealsReport getMealsReport()
	{
		ArrayList<MealsInfo> meals = new ArrayList<>();

		List<Application> applications = getApplications(Utils.getThisYearInt());

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

	private MealsInfo application2MealsInfo(Application a)
	{
		MealsInfo meals = new MealsInfo();

		meals.setFullName(a.getApplicant().getDemographics().getFullName());

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
	private List<Application> getAcceptedApplicationsByYear(int year)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Application.class);
		query.setFilter("year == yearParam && status == statusParam");
		query.declareParameters("int yearParam, String statusParam");
		List<Application> entries = null;
		try
		{
			entries = (List<Application>) query.execute(year, "ACCEPTED");
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
		if ((null != dude) && (null != dude.getContactInfo())
				&& (null != dude.getContactInfo().getAddress()))
		{
			Address add = dude.getContactInfo().getAddress();
			homeTown = add.getCity() + ", " + add.getState();
		}

		String committee = "None";
		if ((null != app.getCommitteeInfoBlock())
				&& (null != app.getCommitteeInfoBlock().getAssignedCommittee()))
		{
			committee = app.getCommitteeInfoBlock().getAssignedCommittee().toString();
		}

		RosterDetails d = new RosterDetails(encodedKey, photoURL, playaName, firstName, homeTown,
				committee, bio);

		return d;
	}

	@Override
	public List<RosterDetails> getAcceptedRosterDetailsByYear(int year)
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
