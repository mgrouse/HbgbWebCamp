package org.hbgb.webcamp.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.hbgb.webcamp.shared.enums.Gender;

import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Demographics implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String email = "";

	@Persistent
	private String firstName = "";

	@Persistent
	private String lastName = "";

	@Persistent
	private String playaName = "";

	@Persistent
	private Gender gender;

	@Persistent
	private Date birthDate;

	@Persistent
	private String defaultWorldJob = "";

	@Persistent
	private Text bio = new Text("");

	public Demographics()
	{
	}

	public Demographics(String emailText)
	{
		email = emailText;
	}

	public Demographics(String email, Demographics source)
	{
		if (null != source)
		{
			this.setEmail(source.getEmail());
			this.setFirstName(source.getFirstName());
			this.setLastName(source.getLastName());
			this.setPlayaName(source.getPlayaName());
			this.setGender(source.getGender());
			this.setBirthDate(source.getBirthDate());
			this.setDefaultWorldJob(source.getDefaultWorldJob());
			this.setBio(source.getBio());
		}
		else
		{
			this.setEmail(email);
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

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String fName)
	{
		this.firstName = fName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lName)
	{
		this.lastName = lName;
	}

	public String getPlayaName()
	{
		return playaName;
	}

	public void setPlayaName(String playaName)
	{
		this.playaName = playaName;
	}

	public String getFullName()
	{
		return getFirstName() + " " + getLastName();
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getDefaultWorldJob()
	{
		return defaultWorldJob;
	}

	public void setDefaultWorldJob(String defaultWorldJob)
	{
		this.defaultWorldJob = defaultWorldJob;
	}

	public String getBio()
	{
		return bio.getValue();
	}

	public void setBio(String txt)
	{
		this.bio = new Text(txt);
	}

}
