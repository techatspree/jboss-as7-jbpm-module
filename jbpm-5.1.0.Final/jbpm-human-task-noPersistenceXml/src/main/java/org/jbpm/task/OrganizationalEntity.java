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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "JBPM_ORGANIZATIONALENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OrganizationalEntity implements Externalizable {

  @Id
  private String id;

  public OrganizationalEntity() {
  }

  public OrganizationalEntity(final String id) {
    this.id = id;
  }

  public void writeExternal(final ObjectOutput out) throws IOException {
    out.writeUTF(id);

  }

  public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
    id = in.readUTF();
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    if (!(obj instanceof OrganizationalEntity)) {
      return false;
    }
    final OrganizationalEntity other = (OrganizationalEntity) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "[" + getClass().getSimpleName() + ":'" + id + "']";
  }
}
