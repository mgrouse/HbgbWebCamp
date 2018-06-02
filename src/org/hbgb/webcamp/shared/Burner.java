package org.hbgb.webcamp.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Burner implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String email = "";

	@Persistent
	private Demographics demographics;

	@Persistent
	private ContactInfo contactInfo;

	@Persistent
	private List<Burn> burns = null;

	public Burner()
	{
	}

	public Burner(String emailText)
	{
		email = emailText;
		// burns = new ArrayList<>();
		demographics = new Demographics(emailText);
		contactInfo = new ContactInfo(emailText);
	}

	public Burner(String email, Burner source)
	{
		if (null != source)
		{
			this.setEmail(source.getEmail());
			this.setDemographics(new Demographics(source.getEmail(), source.getDemographics()));
			this.setContactInfo(new ContactInfo(source.getEmail(), source.getContactInfo()));

			// if (null != source.getBurns())
			// {
			// List<Burn> newBurns = new ArrayList<Burn>();
			// for (Burn current : source.getBurns())
			// {
			// newBurns.add(new Burn(current));
			// }
			// }
		}
		else
		{
			this.setEmail(email);
			this.setDemographics(new Demographics(email));
			this.setContactInfo(new ContactInfo(email));
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

	public Demographics getDemographics()
	{
		return demographics;
	}

	public void setDemographics(Demographics demographics)
	{
		this.demographics = demographics;
	}

	public ContactInfo getContactInfo()
	{
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo)
	{
		this.contactInfo = contactInfo;
	}

	public List<Burn> getBurns()
	{
		return burns;
	}

	public void setBurns(ArrayList<Burn> burns)
	{
		this.burns = burns;
	}

}
