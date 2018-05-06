package org.hbgb.webcamp.shared;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.hbgb.webcamp.shared.enums.Circle;

import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class CommitteeInfoBlock implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	@Persistent
	private String email = "";

	@Persistent
	private Boolean isProfile = false;

	@Persistent
	private Circle circle1;

	@Persistent
	private Text reason1 = new Text("");

	@Persistent
	private Circle circle2;

	@Persistent
	private Text reason2 = new Text("");

	@Persistent
	private Circle assignedCircle;

	@Persistent
	private Boolean isAssignedLead = false;

	public CommitteeInfoBlock()
	{
	}

	public CommitteeInfoBlock(String emailText)
	{
		this.setEmail(emailText);
	}

	public CommitteeInfoBlock(String email, CommitteeInfoBlock source)
	{
		if (null != source)
		{
			this.setEmail(source.getEmail());
			this.setIsProfile(source.getIsProfile());
			this.setCircle1(source.getCircle1());
			this.setReason1(source.getReason1());
			this.setCircle2(source.getCircle2());
			this.setReason2(source.getReason2());
			// this.setAssignedCommittee(source.getAssignedCommittee());
			// this.setIsAssignedLead(source.getIsAssignedLead());
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

	public Boolean getIsProfile()
	{
		return isProfile;
	}

	public void setIsProfile(Boolean isProfile)
	{
		this.isProfile = isProfile;
	}

	public Circle getCircle1()
	{
		return circle1;
	}

	public void setCircle1(Circle committee1)
	{
		this.circle1 = committee1;
	}

	public String getReason1()
	{
		return reason1.getValue();
	}

	public void setReason1(String reason1)
	{
		this.reason1 = new Text(reason1);
	}

	public Circle getCircle2()
	{
		return circle2;
	}

	public void setCircle2(Circle committee2)
	{
		this.circle2 = committee2;
	}

	public String getReason2()
	{
		return reason2.getValue();
	}

	public void setReason2(String reason2)
	{
		this.reason2 = new Text(reason2);
	}

	public Circle getAssignedCircle()
	{
		return assignedCircle;
	}

	public void setAssignedCircle(Circle assignedCommittee)
	{
		this.assignedCircle = assignedCommittee;
	}

	public Boolean getIsAssignedLead()
	{
		return isAssignedLead;
	}

	public void setIsAssignedLead(Boolean isAssignedLead)
	{
		this.isAssignedLead = isAssignedLead;
	}
}
