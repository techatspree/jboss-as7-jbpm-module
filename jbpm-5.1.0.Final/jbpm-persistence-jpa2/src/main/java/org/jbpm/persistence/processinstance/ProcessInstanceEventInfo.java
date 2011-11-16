package org.jbpm.persistence.processinstance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "JBPM_PROCESSINSTANCE_EVENTINFO")
public class ProcessInstanceEventInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Version
	@Column(name = "OPTLOCK")
	private int version;

	private String eventType;
	private long processInstanceId;

	protected ProcessInstanceEventInfo() {
	}

	public long getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	public ProcessInstanceEventInfo(final long processInstanceId,
			final String eventType) {
		this.processInstanceId = processInstanceId;
		this.eventType = eventType;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public String getEventType() {
		return eventType;
	}

}
