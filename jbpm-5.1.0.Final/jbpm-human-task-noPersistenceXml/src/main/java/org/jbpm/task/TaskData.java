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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.FaultData;
import org.jbpm.task.utils.CollectionUtils;

@Embeddable
public class TaskData implements Externalizable {
  @Enumerated(EnumType.STRING)
  private Status status = Status.Created; // initial default state

  private Status previousStatus = null;

  @ManyToOne()
  private User actualOwner;

  @ManyToOne()
  private User createdBy;

  @Temporal(TemporalType.DATE)
  @Column(name = "createdOn")
  private Date createdOn;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "activationTime")
  private Date activationTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expirationTime")
  private Date expirationTime;

  private int skipable = 0;

  private long workItemId = -1;

  private long processInstanceId = -1;

  @Enumerated(EnumType.STRING)
  private AccessType documentAccessType;

  private String documentType;

  private long documentContentId = -1;

  @Enumerated(EnumType.STRING)
  private AccessType outputAccessType;

  private String outputType;

  private long outputContentId = -1;

  private String faultName;

  @Enumerated(EnumType.STRING)
  private AccessType faultAccessType;

  private String faultType;

  private long faultContentId = -1;

  private long parentId = -1;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "TaskData_Comments_Id", nullable = true)
  private List<Comment> comments = Collections.emptyList();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "TaskData_Attachments_Id", nullable = true)
  private List<Attachment> attachments = Collections.emptyList();

  public void writeExternal(final ObjectOutput out) throws IOException {
    if (status != null) {
      out.writeBoolean(true);
      out.writeUTF(status.toString());
    } else {
      out.writeBoolean(false);
    }

    if (previousStatus != null) {
      out.writeBoolean(true);
      out.writeUTF(previousStatus.toString());
    } else {
      out.writeBoolean(false);
    }

    if (actualOwner != null) {
      out.writeBoolean(true);
      actualOwner.writeExternal(out);
    } else {
      out.writeBoolean(false);
    }

    if (createdBy != null) {
      out.writeBoolean(true);
      createdBy.writeExternal(out);
    } else {
      out.writeBoolean(false);
    }

    if (createdOn != null) {
      out.writeBoolean(true);
      out.writeLong(createdOn.getTime());
    } else {
      out.writeBoolean(false);
    }

    if (activationTime != null) {
      out.writeBoolean(true);
      out.writeLong(activationTime.getTime());
    } else {
      out.writeBoolean(false);
    }

    if (expirationTime != null) {
      out.writeBoolean(true);
      out.writeLong(expirationTime.getTime());
    } else {
      out.writeBoolean(false);
    }

    out.writeInt(skipable);

    if (workItemId != -1) {
      out.writeBoolean(true);
      out.writeLong(workItemId);
    } else {
      out.writeBoolean(false);
    }

    if (processInstanceId != -1) {
      out.writeBoolean(true);
      out.writeLong(processInstanceId);
    } else {
      out.writeBoolean(false);
    }

    if (documentAccessType != null) {
      out.writeBoolean(true);
      out.writeObject(documentAccessType);
    } else {
      out.writeBoolean(false);
    }

    if (documentType != null) {
      out.writeBoolean(true);
      out.writeUTF(documentType);
    } else {
      out.writeBoolean(false);
    }

    if (documentContentId != -1) {
      out.writeBoolean(true);
      out.writeLong(documentContentId);
    } else {
      out.writeBoolean(false);
    }

    if (outputAccessType != null) {
      out.writeBoolean(true);
      out.writeObject(outputAccessType);
    } else {
      out.writeBoolean(false);
    }

    if (outputType != null) {
      out.writeBoolean(true);
      out.writeUTF(outputType);
    } else {
      out.writeBoolean(false);
    }

    if (outputContentId != -1) {
      out.writeBoolean(true);
      out.writeLong(outputContentId);
    } else {
      out.writeBoolean(false);
    }

    if (faultName != null) {
      out.writeBoolean(true);
      out.writeUTF(faultName);
    } else {
      out.writeBoolean(false);
    }

    if (faultAccessType != null) {
      out.writeBoolean(true);
      out.writeObject(faultAccessType);
    } else {
      out.writeBoolean(false);
    }

    if (faultType != null) {
      out.writeBoolean(true);
      out.writeUTF(faultType);
    } else {
      out.writeBoolean(false);
    }

    if (faultContentId != -1) {
      out.writeBoolean(true);
      out.writeLong(faultContentId);
    } else {
      out.writeBoolean(false);
    }

    if (parentId != -1) {
      out.writeBoolean(true);
      out.writeLong(parentId);
    } else {
      out.writeBoolean(false);
    }

    CollectionUtils.writeCommentList(comments, out);
    CollectionUtils.writeAttachmentList(attachments, out);
  }

  public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
    if (in.readBoolean()) {
      status = Status.valueOf(in.readUTF());
    }

    if (in.readBoolean()) {
      previousStatus = Status.valueOf(in.readUTF());
    }

    if (in.readBoolean()) {
      actualOwner = new User();
      actualOwner.readExternal(in);
    }

    if (in.readBoolean()) {
      createdBy = new User();
      createdBy.readExternal(in);
    }

    if (in.readBoolean()) {
      createdOn = new Date(in.readLong());
    }

    if (in.readBoolean()) {
      activationTime = new Date(in.readLong());
    }

    if (in.readBoolean()) {
      expirationTime = new Date(in.readLong());
    }

    skipable = in.readInt();

    if (in.readBoolean()) {
      workItemId = in.readLong();
    }

    if (in.readBoolean()) {
      processInstanceId = in.readLong();
    }

    if (in.readBoolean()) {
      documentAccessType = (AccessType) in.readObject();
    }

    if (in.readBoolean()) {
      documentType = in.readUTF();
    }

    if (in.readBoolean()) {
      documentContentId = in.readLong();
    }

    if (in.readBoolean()) {
      outputAccessType = (AccessType) in.readObject();
    }

    if (in.readBoolean()) {
      outputType = in.readUTF();
    }

    if (in.readBoolean()) {
      outputContentId = in.readLong();
    }

    if (in.readBoolean()) {
      faultName = in.readUTF();
    }

    if (in.readBoolean()) {
      faultAccessType = (AccessType) in.readObject();
    }

    if (in.readBoolean()) {
      faultType = in.readUTF();
    }

    if (in.readBoolean()) {
      faultContentId = in.readLong();
    }

    if (in.readBoolean()) {
      parentId = in.readLong();
    }
    comments = CollectionUtils.readCommentList(in);
    attachments = CollectionUtils.readAttachmentList(in);

  }

  /**
   * Initializes the state of the TaskData, i.e. sets the <field>createdOn</field>, <field>activationTime</field> and sets the state to
   * <code>Status.Created</code>.
   * 
   * @return returns the current state of the TaskData
   */
  public Status initialize() {
    Date createdOn = getCreatedOn();
    // set the CreatedOn date if it's not already set
    if (createdOn == null) {
      createdOn = new Date();
      setCreatedOn(createdOn);
    }

    // @FIXME for now we activate on creation, unless date is supplied
    if (getActivationTime() == null) {
      setActivationTime(createdOn);
    }

    setStatus(Status.Created);

    return Status.Created;
  }

  /**
   * This method will potentially assign the actual owner of this TaskData and set the status of the data. <li>If there is only 1 potential
   * owner, and it is a <code>User</code>, that will become the actual owner of the TaskData and the status will be set to
   * <code>Status.Reserved</code>.</li> <li>f there is only 1 potential owner, and it is a <code>Group</code>, no owner will be assigned and
   * the status will be set to <code>Status.Ready</code>.</li> <li>If there are more than 1 potential owners, the status will be set to
   * <code>Status.Ready</code>.</li> <li>otherwise, the task data will be unchanged</li>
   * 
   * @param potentialOwners
   *          - list of potential owners
   * @return current status of task data
   */
  public Status assignOwnerAndStatus(final List<OrganizationalEntity> potentialOwners) {
    if (getStatus() != Status.Created) {
      throw new IllegalStateException("Can only assign task owner if status is Created!");
    }

    Status assignedStatus = null;

    if (potentialOwners.size() == 1) {
      // if there is a single potential owner, assign and set status to Reserved
      final OrganizationalEntity potentialOwner = potentialOwners.get(0);
      // if there is a single potential user owner, assign and set status to Reserved
      if (potentialOwner instanceof User) {
        setActualOwner((User) potentialOwner);

        assignedStatus = Status.Reserved;
      }
      // If there is a group set as potentialOwners, set the status to Ready ??
      if (potentialOwner instanceof Group) {

        assignedStatus = Status.Ready;
      }
    } else if (potentialOwners.size() > 1) {
      // multiple potential owners, so set to Ready so one can claim.
      assignedStatus = Status.Ready;
    } else {
      // @TODO we have no potential owners
    }

    if (assignedStatus != null) {
      setStatus(assignedStatus);
    } else {
      // status wasn't assigned, so just return the currrent status
      assignedStatus = getStatus();
    }

    return assignedStatus;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(final Status status) {
    previousStatus = this.status;
    this.status = status;
  }

  public Status getPreviousStatus() {
    return previousStatus;
  }

  public void setPreviousStatus(final Status previousStatus) {
    this.previousStatus = previousStatus;
  }

  public User getActualOwner() {
    return actualOwner;
  }

  public void setActualOwner(final User actualOwner) {
    this.actualOwner = actualOwner;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(final User createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(final Date createdOn) {
    this.createdOn = createdOn;
  }

  Date getActivationTime() {
    return activationTime;
  }

  public void setActivationTime(final Date activationTime) {
    this.activationTime = activationTime;
  }

  public Date getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(final Date expirationTime) {
    this.expirationTime = expirationTime;
  }

  public boolean isSkipable() {
    return skipable > 0 ? true : false;
  }

  public void setSkipable(final boolean isSkipable) {
    this.skipable = isSkipable ? 1 : 0;
  }

  public void setWorkItemId(final long workItemId) {
    this.workItemId = workItemId;
  }

  public long getWorkItemId() {
    return workItemId;
  }

  public void setProcessInstanceId(final long processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public long getProcessInstanceId() {
    return processInstanceId;
  }

  /**
   * Sets the document content data for this task data. It will set the <field>documentContentId</field> from the specified documentID,
   * <field>documentAccessType</field>, <field>documentType</field> from the specified documentConentData.
   * 
   * @param documentID
   *          id of document content
   * @param documentConentData
   *          ContentData
   */
  public void setDocument(final long documentID, final ContentData documentConentData) {
    setDocumentContentId(documentID);
    setDocumentAccessType(documentConentData.getAccessType());
    setDocumentType(documentConentData.getType());
  }

  public AccessType getDocumentAccessType() {
    return documentAccessType;
  }

  public void setDocumentAccessType(final AccessType accessType) {
    this.documentAccessType = accessType;
  }

  public String getDocumentType() {
    return documentType;
  }

  public long getDocumentContentId() {
    return documentContentId;
  }

  public void setDocumentContentId(final long documentContentId) {
    this.documentContentId = documentContentId;
  }

  public void setDocumentType(final String documentType) {
    this.documentType = documentType;
  }

  /**
   * Sets the content data for this task data. It will set the <field>outputContentId</field> from the specified outputContentId,
   * <field>outputAccessType</field>, <field>outputType</field> from the specified outputContentData.
   * 
   * @param outputContentId
   *          id of output content
   * @param outputContentData
   *          contentData
   */
  public void setOutput(final long outputContentId, final ContentData outputContentData) {
    setOutputContentId(outputContentId);
    setOutputAccessType(outputContentData.getAccessType());
    setOutputType(outputContentData.getType());
  }

  public AccessType getOutputAccessType() {
    return outputAccessType;
  }

  void setOutputAccessType(final AccessType outputAccessType) {
    this.outputAccessType = outputAccessType;
  }

  public String getOutputType() {
    return outputType;
  }

  void setOutputType(final String outputType) {
    this.outputType = outputType;
  }

  public long getOutputContentId() {
    return outputContentId;
  }

  void setOutputContentId(final long outputContentId) {
    this.outputContentId = outputContentId;
  }

  /**
   * Sets the fault data for this task data. It will set the <field>faultContentId</field> from the specified faultContentId,
   * <field>faultAccessType</field>, <field>faultType</field>, <field>faultName</field> from the specified faultData.
   * 
   * @param faultContentId
   *          id of fault content
   * @param faultData
   *          FaultData
   */
  public void setFault(final long faultContentId, final FaultData faultData) {
    setFaultContentId(faultContentId);
    setFaultAccessType(faultData.getAccessType());
    setFaultType(faultData.getType());
    setFaultName(faultData.getFaultName());
  }

  public String getFaultName() {
    return faultName;
  }

  void setFaultName(final String faultName) {
    this.faultName = faultName;
  }

  public AccessType getFaultAccessType() {
    return faultAccessType;
  }

  void setFaultAccessType(final AccessType faultAccessType) {
    this.faultAccessType = faultAccessType;
  }

  public String getFaultType() {
    return faultType;
  }

  void setFaultType(final String faultType) {
    this.faultType = faultType;
  }

  public long getFaultContentId() {
    return faultContentId;
  }

  void setFaultContentId(final long faultContentId) {
    this.faultContentId = faultContentId;
  }

  public List<Comment> getComments() {
    return comments;
  }

  /**
   * Adds the specified comment to our list of comments.
   * 
   * @param comment
   *          comment to add
   */
  public void addComment(final Comment comment) {
    if (comments == null || comments == Collections.<Comment> emptyList()) {
      comments = new ArrayList<Comment>();
    }

    comments.add(comment);
  }

  /**
   * Removes the Comment specified by the commentId.
   * 
   * @param commentId
   *          id of Comment to remove
   * @return removed Comment or null if one was not found with the id
   */
  public Comment removeComment(final long commentId) {
    Comment removedComment = null;

    if (comments != null) {
      for (int index = comments.size() - 1; index >= 0; --index) {
        final Comment currentComment = comments.get(index);

        if (currentComment.getId() == commentId) {
          removedComment = comments.remove(index);
          break;
        }
      }
    }

    return removedComment;
  }

  public void setComments(final List<Comment> comments) {
    this.comments = comments;
  }

  public List<Attachment> getAttachments() {
    return attachments;
  }

  /**
   * Adds the specified attachment to our list of Attachments.
   * 
   * @param attachment
   *          attachment to add
   */
  public void addAttachment(final Attachment attachment) {
    if (attachments == null || attachments == Collections.<Attachment> emptyList()) {
      attachments = new ArrayList<Attachment>();
    }

    attachments.add(attachment);
  }

  /**
   * Removes the Attachment specified by the attachmentId.
   * 
   * @param attachmentId
   *          id of attachment to remove
   * @return removed Attachment or null if one was not found with the id
   */
  public Attachment removeAttachment(final long attachmentId) {
    Attachment removedAttachment = null;

    if (attachments != null) {
      for (int index = attachments.size() - 1; index >= 0; --index) {
        final Attachment currentAttachment = attachments.get(index);

        if (currentAttachment.getId() == attachmentId) {
          removedAttachment = attachments.remove(index);
          break;
        }
      }
    }

    return removedAttachment;
  }

  public void setAttachments(final List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public long getParentId() {
    return parentId;
  }

  public void setParentId(final long parentId) {
    this.parentId = parentId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activationTime == null) ? 0 : activationTime.hashCode());
    result = prime * result + CollectionUtils.hashCode(attachments);
    result = prime * result + CollectionUtils.hashCode(comments);
    result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
    result = prime * result + ((expirationTime == null) ? 0 : expirationTime.hashCode());
    result = prime * result + (skipable);
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((previousStatus == null) ? 0 : previousStatus.hashCode());
    result = prime * result + ((workItemId == -1) ? 0 : (int) workItemId);
    // Should I add parentId to this hashCode?
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
    if (!(obj instanceof TaskData)) {
      return false;
    }
    final TaskData other = (TaskData) obj;

    if (actualOwner == null) {
      if (other.actualOwner != null) {
        return false;
      }
    } else if (!actualOwner.equals(other.actualOwner)) {
      return false;
    }

    if (createdBy == null) {
      if (other.createdBy != null) {
        return false;
      }
    } else if (!createdBy.equals(other.createdBy)) {
      return false;
    }

    if (createdOn == null) {
      if (other.createdOn != null) {
        return false;
      }
    } else if (createdOn.getTime() != other.createdOn.getTime()) {
      return false;
    }
    if (expirationTime == null) {
      if (other.expirationTime != null) {
        return false;
      }
    } else if (expirationTime.getTime() != other.expirationTime.getTime()) {
      return false;
    }
    if (skipable != other.skipable) {
      return false;
    }
    if (workItemId != other.workItemId) {
      return false;
    }
    if (status == null) {
      if (other.status != null) {
        return false;
      }
    } else if (!status.equals(other.status)) {
      return false;
    }
    if (previousStatus == null) {
      if (other.previousStatus != null) {
        return false;
      }
    } else if (!previousStatus.equals(other.previousStatus)) {
      return false;
    }
    if (activationTime == null) {
      if (other.activationTime != null) {
        return false;
      }
    } else if (activationTime.getTime() != other.activationTime.getTime()) {
      return false;
    }

    if (workItemId != other.workItemId) {
      return false;
    }

    if (documentAccessType == null) {
      if (other.documentAccessType != null) {
        return false;
      }
    } else if (!documentAccessType.equals(other.documentAccessType)) {
      return false;
    }

    if (documentContentId != other.documentContentId) {
      return false;
    }
    if (documentType == null) {
      if (other.documentType != null) {
        return false;
      }
    } else if (!documentType.equals(other.documentType)) {
      return false;
    }
    // I think this is OK!
    if (parentId != other.parentId) {
      return false;
    }

    return CollectionUtils.equals(attachments, other.attachments) && CollectionUtils.equals(comments, other.comments);
  }

}
