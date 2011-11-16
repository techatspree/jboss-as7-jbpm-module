/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.task;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jbpm.task.utils.CollectionUtils;

@Entity
@Table(name = "JBPM_ESCALATION")
public class Escalation implements Externalizable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Escalation_Constraints_Id", nullable = true)
	private List<BooleanExpression> constraints = Collections.emptyList();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Escalation_Notifications_Id", nullable = true)
	private List<Notification> notifications = Collections.emptyList();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Escalation_Reassignments_Id", nullable = true)
	private List<Reassignment> reassignments = Collections.emptyList();

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeLong(id);

		if (name != null) {
			out.writeBoolean(true);
			out.writeUTF(name);
		} else {
			out.writeBoolean(false);
		}
		CollectionUtils.writeBooleanExpressionList(constraints, out);
		CollectionUtils.writeNotificationList(notifications, out);
		CollectionUtils.writeReassignmentList(reassignments, out);
	}

	public void readExternal(final ObjectInput in) throws IOException,
			ClassNotFoundException {
		id = in.readLong();
		if (in.readBoolean()) {
			name = in.readUTF();
		}
		constraints = CollectionUtils.readBooleanExpressionList(in);
		notifications = CollectionUtils.readNotificationList(in);
		reassignments = CollectionUtils.readReassignmentList(in);

	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<BooleanExpression> getConstraints() {
		return constraints;
	}

	public void setConstraints(final List<BooleanExpression> constraints) {
		this.constraints = constraints;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(final List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Reassignment> getReassignments() {
		return reassignments;
	}

	public void setReassignments(final List<Reassignment> reassignments) {
		this.reassignments = reassignments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CollectionUtils.hashCode(constraints);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + CollectionUtils.hashCode(notifications);
		result = prime * result + CollectionUtils.hashCode(reassignments);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Escalation)) {
			return false;
		}
		final Escalation other = (Escalation) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return CollectionUtils.equals(constraints, other.constraints)
				&& CollectionUtils.equals(notifications, other.notifications)
				&& CollectionUtils.equals(reassignments, other.reassignments);

	}

}
