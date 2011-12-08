
This project nearly corresponds to the original project 'jbpm-persistence-jpa' with version 5.1.0.Final.
We had to make some changes to get it running on JBOss As 7 with JPA2.0. What we did is:

- remove all dependencies to hibernate with version 3
- patch class: org.jbpm.persistence.processinstance.ProcessInstanceInfo
  - replace hibernate 3 annotations and with jpa2.0 annotations (@CollectionOfElements -> @ElementCollection)

