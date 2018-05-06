package org.hbgb.webcamp.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.hbgb.webcamp.shared.enums.ApplicationStatus;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Application implements Serializable
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String email = "";

	@Persistent
	private Date created;

	@Persistent
	private Date edited;

	@Persistent
	private int year;

	@Persistent
	private String imageURL = "";

	@Persistent
	private ApplicationStatus status;

	@Persistent
	private Burner applicant;

	@Persistent
	private PaymentInfoBlock paymentInfoBlock;

	@Persistent
	private CommitteeInfoBlock committeeInfoBlock;

	@Persistent
	private LogisticsInfoBlock logisticsInfoBlock;

	@Persistent
	private ShelterInfoBlock shelterInfoBlock;

	@Persistent
	private DietInfoBlock dietInfoBlock;

	@Persistent
	private VettingInfoBlock vettingInfoBlock;

	@Persistent
	private OfficeUse officeUse;

	// on save of last page send invite link to yahoo group

	public Application()
	{
	}

	public Application(String emailText)
	{
		email = emailText;

		created = new Date();
		edited = null;

		// TODO needs to look up the current year.
		year = Utils.getThisYearInt();
		status = ApplicationStatus.NEW;
		applicant = new Burner(emailText);
		paymentInfoBlock = new PaymentInfoBlock(emailText);
		committeeInfoBlock = new CommitteeInfoBlock(emailText);
		logisticsInfoBlock = new LogisticsInfoBlock(emailText);
		shelterInfoBlock = new ShelterInfoBlock(emailText);
		dietInfoBlock = new DietInfoBlock(emailText);
		vettingInfoBlock = null; // new VettingInfoBlock(emailText);
		officeUse = null; // new OfficeUse(emailText);
	}

	public Application(Application source)
	{
		created = new Date();
		edited = null;

		year = Utils.getThisYearInt();
		status = ApplicationStatus.NEW;

		if (null != source)
		{
			this.setImageURL(source.getImageURL());
			email = source.getEmail();
			paymentInfoBlock = new PaymentInfoBlock(email, source.getPaymentInfoBlock());
			committeeInfoBlock = new CommitteeInfoBlock(email, source.getCircleInfoBlock());
			logisticsInfoBlock = new LogisticsInfoBlock(email, null);
			shelterInfoBlock = new ShelterInfoBlock(email, source.getShelterInfoBlock());
			dietInfoBlock = new DietInfoBlock(email, source.getDietInfoBlock());

			applicant = new Burner(email, source.getApplicant());
			// vettingInfoBlock = new
			// VettingInfoBlock(source.getVettingInfoBlock());
			// officeUse = new OfficeUse(source.getOfficeUse());
		}

	}

	public String getEncodedKey()
	{
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey)
	{
		this.encodedKey = encodedKey;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public Date getEdited()
	{
		return edited;
	}

	public void setEdited(Date edited)
	{
		this.edited = edited;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public ApplicationStatus getStatus()
	{
		return status;
	}

	public void setStatus(ApplicationStatus status)
	{
		this.status = status;
	}

	public Burner getApplicant()
	{
		return applicant;
	}

	public void setApplicant(Burner applicant)
	{
		this.applicant = applicant;
	}

	public PaymentInfoBlock getPaymentInfoBlock()
	{
		return paymentInfoBlock;
	}

	public void setPaymentInfoBlock(PaymentInfoBlock payment)
	{
		this.paymentInfoBlock = payment;
	}

	public CommitteeInfoBlock getCircleInfoBlock()
	{
		return committeeInfoBlock;
	}

	public void setCircleInfoBlock(CommitteeInfoBlock commitee)
	{
		this.committeeInfoBlock = commitee;
	}

	public LogisticsInfoBlock getLogisticsInfoBlock()
	{
		return logisticsInfoBlock;
	}

	public void setLogisticsInfoBlock(LogisticsInfoBlock logisticsInfoBlock)
	{
		this.logisticsInfoBlock = logisticsInfoBlock;
	}

	public ShelterInfoBlock getShelterInfoBlock()
	{
		return shelterInfoBlock;
	}

	public void setShelterInfoBlock(ShelterInfoBlock shelterInfoBlock)
	{
		this.shelterInfoBlock = shelterInfoBlock;
	}

	public DietInfoBlock getDietInfoBlock()
	{
		return dietInfoBlock;
	}

	public void setDietInfoBlock(DietInfoBlock dietInfoBlock)
	{
		this.dietInfoBlock = dietInfoBlock;
	}

	public VettingInfoBlock getVettingInfoBlock()
	{
		return vettingInfoBlock;
	}

	public void setVettingInfoBlock(VettingInfoBlock vettingInfoBlock)
	{
		this.vettingInfoBlock = vettingInfoBlock;
	}

	public OfficeUse getOfficeUse()
	{
		return officeUse;
	}

	public void setOfficeUse(OfficeUse officeUse)
	{
		this.officeUse = officeUse;
	}

	public String getImageURL()
	{
		return imageURL;
	}

	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}
}
