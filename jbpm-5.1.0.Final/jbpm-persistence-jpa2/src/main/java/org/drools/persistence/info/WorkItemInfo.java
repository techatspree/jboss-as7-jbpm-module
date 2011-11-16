package org.drools.persistence.info;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.drools.marshalling.impl.InputMarshaller;
import org.drools.marshalling.impl.MarshallerReaderContext;
import org.drools.marshalling.impl.MarshallerWriteContext;
import org.drools.marshalling.impl.OutputMarshaller;
import org.drools.process.instance.WorkItem;
import org.drools.runtime.Environment;

@Entity
@SequenceGenerator(name = "workItemInfoIdSeq", sequenceName = "WORKITEMINFO_ID_SEQ")
@Table(name = "JBPM_WORKITEMINFO")
public class WorkItemInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "workItemInfoIdSeq")
	private Long workItemId;

	@Version
	@Column(name = "OPTLOCK")
	private int version;

	private String name;
	private Date creationDate;
	private long processInstanceId;
	private long state;
	private @Lob
	byte[] workItemByteArray;
	private @Transient
	WorkItem workItem;

	private @Transient
	Environment env;

	protected WorkItemInfo() {
	}

	public WorkItemInfo(final WorkItem workItem, final Environment env) {
		this.workItem = workItem;
		this.name = workItem.getName();
		this.creationDate = new Date();
		this.processInstanceId = workItem.getProcessInstanceId();
		this.env = env;
	}

	public Long getId() {
		return workItemId;
	}

	public int getVersion() {
		return this.version;
	}

	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public long getState() {
		return state;
	}

	public WorkItem getWorkItem(final Environment env) {
		this.env = env;
		if (workItem == null) {
			try {
				final ByteArrayInputStream bais = new ByteArrayInputStream(
						workItemByteArray);
				final MarshallerReaderContext context = new MarshallerReaderContext(
						bais, null, null, null, env);
				workItem = InputMarshaller.readWorkItem(context);
				context.close();
			} catch (final IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(
						"IOException while loading process instance: "
								+ e.getMessage());
			}
		}
		return workItem;
	}

	@PreUpdate
	public void update() {
		this.state = workItem.getState();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			final MarshallerWriteContext context = new MarshallerWriteContext(
					baos, null, null, null, null, this.env);

			OutputMarshaller.writeWorkItem(context, workItem);

			context.close();
			this.workItemByteArray = baos.toByteArray();
		} catch (final IOException e) {
			throw new IllegalArgumentException(
					"IOException while storing workItem " + workItem.getId()
							+ ": " + e.getMessage());
		}
	}

	public void setId(final Long id) {
		this.workItemId = id;
	}
}
