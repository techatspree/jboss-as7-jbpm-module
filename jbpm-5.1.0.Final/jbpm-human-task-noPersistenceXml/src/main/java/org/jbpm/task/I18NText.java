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
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "JBPM_I18NTEXT")
public class I18NText implements Externalizable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String language;

	@Lob
	private String text;

	public I18NText() {

	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeUTF(language);
		out.writeUTF(text);
	}

	public void readExternal(final ObjectInput in) throws IOException,
			ClassNotFoundException {
		id = in.readLong();
		language = in.readUTF();
		text = in.readUTF();
	}

	public I18NText(final String language, final String text) {
		this.language = language;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		if (!(obj instanceof I18NText)) {
			return false;
		}
		final I18NText other = (I18NText) obj;
		if (language == null) {
			if (other.language != null) {
				return false;
			}
		} else if (!language.equals(other.language)) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	public static String getLocalText(final List<I18NText> list,
			final String prefferedLanguage, final String defaultLanguage) {
		for (final I18NText text : list) {
			if (text.getLanguage().equals(prefferedLanguage)) {
				return text.getText();
			}
		}
		if (defaultLanguage == null) {
			for (final I18NText text : list) {
				if (text.getLanguage().equals(defaultLanguage)) {
					return text.getText();
				}
			}
		}
		return "";
	}

}
