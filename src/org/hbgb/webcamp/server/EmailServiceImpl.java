
package org.hbgb.webcamp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hbgb.webcamp.client.async.EmailService;
import org.hbgb.webcamp.shared.Application;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.Utils;
import org.hbgb.webcamp.shared.enums.ApplicationStatus;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class EmailServiceImpl extends RemoteServiceServlet implements EmailService
{
	// private static final ApplicationService appServ = new
	// ApplicationServiceImpl();
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());

	@Override
	public String sendApplicationRecievedEmail(String emailAddress)
	{
		List<String> recipient = new ArrayList<>();
		InternetAddress replyTo = getRegistrationLeadAddress();

		// first to the Lead
		recipient.add(getRegistrationLeadAddressAsText());

		sendMail(recipient, "HeeBee application received! for: " + emailAddress,
				getNotificationEmailBody(), replyTo);

		// then to the camper
		recipient.clear();
		recipient.add(emailAddress);

		// for testing
		recipient.add("michael.grouse@gmail.com");

		return sendMail(recipient, "Your HeeBee application was received!",
				getApplicationEmailBody(), replyTo);
	}

	@Override
	public String sendEmailToCircle(String circle, String subject, String message, String replyName,
			String replyAddress)
	{
		List<String> recipients = getCircleEmailList(circle);

		if (0 == recipients.size())
		{
			return "NoRecipients";
		}

		InternetAddress replyTo = asInternetAddress(replyName, replyAddress);

		return sendMail(recipients, subject, message, replyTo);
	}

	private String sendMail(List<String> recipients, String subject, String message,
			InternetAddress replyTo)
	{
		String output = "Success";
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, (Authenticator) null);
		try
		{
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(getWebmasterAddress());

			for (String address : recipients)
			{
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			}

			// add me for tracking performance
			msg.addRecipient(Message.RecipientType.TO, getWebmasterAddress());

			msg.setSubject(subject);
			msg.setText(message);
			msg.setReplyTo(new InternetAddress[] { replyTo });

			Transport.send(msg);
		}
		catch (Exception e)
		{
			output = "Failure";

			log.severe(e.getMessage());
		}

		return output;
	}

	private List<String> getCircleEmailList(String circle)
	{
		ArrayList<String> emails = new ArrayList<>();

		if (null != circle)
		{
			List<Application> entries = getApplicationsByStatus(ApplicationStatus.ACCEPTED);

			if (entries != null)
			{
				for (Application app : entries)
				{
					CommitteeInfoBlock cib = app.getCircleInfoBlock();

					if (null != cib && null != cib.getAssignedCommittee()
							&& circle.contentEquals(cib.getAssignedCommittee().toString())
							&& app.getEmail() != null && !app.getEmail().isEmpty())
					{
						emails.add(app.getEmail());
					}
				}
			}
		}
		return emails;
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
			log.log(Level.WARNING, e.getMessage());
		}
		return entries;
	}

	/**
	 * @return
	 */
	private String getNotificationEmailBody()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("A new application has been created.\n");
		sb.append("\n\n");
		sb.append("http://www.hbgbwebcamp.appspot.com/#admin");
		return sb.toString();
	}

	private String getApplicationEmailBody()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Thank you for applying to be part of the HeeBee camp.\n");
		sb.append("If you don't hear from us soon, email ");
		sb.append(getRegistrationLeadAddressAsText());
		sb.append("\nPlease don't reply to this email address.\n");
		sb.append("Meet the rest of us by joining the HeeBeeGeeBee Healers Facebook Group at: ");
		sb.append("https://www.facebook.com/groups/23767999265 \n");
		sb.append("\n");
		sb.append("This year's co-leads, \n");
		sb.append("Quiggles and DragonHeart\n");
		return sb.toString();
	}

	private InternetAddress getWebmasterAddress() throws Exception
	{
		return new InternetAddress("michael.grouse@gmail.com", "Scarab");
	}

	private InternetAddress getRegistrationLeadAddress()
	{
		return asInternetAddress("Quiggles", "shanalory@hotmail.com");
	}

	private String getRegistrationLeadAddressAsText()
	{
		return "shanalory@hotmail.com";
	}

	private InternetAddress asInternetAddress(String name, String address)
	{
		InternetAddress retVal = null;

		try
		{
			retVal = new InternetAddress(address, name);
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, e.getMessage());
		}

		return retVal;
	}
}
