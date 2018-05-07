/*
 * This is an unpublished work protected by the United States copyright laws and
 * is proprietary to Michael A. Grouse. Disclosure, copying, reproduction,
 * merger translation, modification, enhancement or use by anyone other than
 * authorized employees or licensees of Michael A. Grouse without prior written
 * consent of Michael A. Grouse is prohibited.
 *
 * Copyright (C) 1992 - 2018 Michael A. Grouse, All Rights Reserved.
 *
 * This copyright notice should not be construed as evidence of publication.
 */
package org.hbgb.webcamp.server;

import java.util.List;
import java.util.logging.Logger;

import org.hbgb.webcamp.client.async.ShelterReportService;
import org.hbgb.webcamp.shared.Application;
import org.hbgb.webcamp.shared.Burner;
import org.hbgb.webcamp.shared.Car;
import org.hbgb.webcamp.shared.Shelter;
import org.hbgb.webcamp.shared.ShelterInfoBlock;
import org.hbgb.webcamp.shared.ShelterReport;
import org.hbgb.webcamp.shared.Utils;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;
import org.hbgb.webcamp.shared.enums.ShelterType;
import org.hbgb.webcamp.shared.enums.Transportation;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Michael
 *
 */
@SuppressWarnings("serial")
public class ShelterReportServiceImpl extends RemoteServiceServlet implements ShelterReportService
{
	private static final Logger log = Logger.getLogger(ShelterReportServiceImpl.class.getName());

	ApplicationServiceImpl service = new ApplicationServiceImpl();

	@Override
	public ShelterReport getShelterReport()
	{
		ShelterReport report = new ShelterReport();

		List<Application> applications = service.getApplications(Utils.getThisYearInt());

		for (Application app : applications)
		{
			if (app.getStatus() == ApplicationStatus.ACCEPTED)
			{
				Shelter s = asShelter(app);
				if (null != s)
				{
					report.addShelter(s);
				}
				else
				{
					report.addBadEmail(app.getEmail());
				}

				Car c = asCar(app);
				if (null != c)
				{
					report.addCar(c);
				}

			} // if Accepted

		} // for app

		return report;
	}

	private Car asCar(Application app)
	{
		Car c = null;

		if (Transportation.Drive == app.getLogisticsInfoBlock().getTransType())
		{
			String name = app.getApplicant().getDemographics().getFirstName() + " "
					+ app.getApplicant().getDemographics().getLastName() + " ("
					+ app.getApplicant().getDemographics().getPlayaName() + ")";

			c = new Car(name, app.getEmail());
		}

		return c;
	}

	private Shelter asShelter(Application app)
	{
		Shelter s = null;

		ShelterType type = null;
		String desc = "";

		ShelterInfoBlock block = app.getShelterInfoBlock();

		if (block.getIsInDormTent())
		{
			type = ShelterType.Tent;
		}
		if (block.getHasStructure())
		{
			type = ShelterType.Structure;
			desc = block.getStructureInfo();
		}
		if (block.getIsBringingRv())
		{
			type = ShelterType.RV;
			desc = block.getRvInfo();
		}

		if (null != type)
		{
			Burner b = app.getApplicant();

			String name = b.getDemographics().getFirstName() + " "
					+ b.getDemographics().getLastName() + " (" + b.getDemographics().getPlayaName()
					+ ")";
			String email = app.getEmail();

			s = new Shelter(type, name, email, desc);
		}

		return s;
	}
}
