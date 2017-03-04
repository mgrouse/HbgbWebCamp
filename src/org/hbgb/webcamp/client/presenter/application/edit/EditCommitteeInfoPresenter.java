/*
 * Decompiled with CFR 0_115.
 */
package org.hbgb.webcamp.client.presenter.application.edit;

import org.hbgb.webcamp.client.model.CommitteeInfoBlockModel;
import org.hbgb.webcamp.client.model.IKeyedModel;
import org.hbgb.webcamp.client.presenter.application.edit.AbstractInfoBlockPresenter;
import org.hbgb.webcamp.client.view.IPartView;
import org.hbgb.webcamp.client.view.application.edit.EditCommitteeInfoViewImpl;
import org.hbgb.webcamp.shared.CommitteeInfoBlock;
import org.hbgb.webcamp.shared.enums.Committee;

public class EditCommitteeInfoPresenter extends AbstractInfoBlockPresenter
{
	static EditCommitteeInfoViewImpl view = new EditCommitteeInfoViewImpl();
	static CommitteeInfoBlockModel model = new CommitteeInfoBlockModel();

	public EditCommitteeInfoPresenter()
	{
		super(model, view);
	}

	@Override
	protected void setViewData()
	{
		CommitteeInfoBlock block = model.getData();
		view.setReason1(block.getReason1());
		view.setReason2(block.getReason2());
		view.setCommittee1(block.getCommittee1());
		view.setCommittee2(block.getCommittee2());
		view.setAssignedCommittee(block.getAssignedCommittee());
		view.setIsAssignedLead(block.getIsAssignedLead());
	}

	@Override
	protected void setModelData()
	{
		CommitteeInfoBlock block = model.getData();
		block.setReason1(view.getReason1());
		block.setReason2(view.getReason2());
		block.setCommittee1(view.getCommittee1());
		block.setCommittee2(view.getCommittee2());
		block.setAssignedCommittee(view.getAssignedCommittee());
		block.setIsAssignedLead(view.getIsAssignedLead());
	}
}
