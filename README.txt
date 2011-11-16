1. HOWTO TO INSTALL INTO JBOSS 7

Unpack target/jbpm-as7-module-assemble-module.zip into JBOSS/modules

Edit JBOSS/standalone/configuration/standalone.xml:

Replace the line

<subsystem xmlns="urn:jboss:domain:ee:1.0"/>

with

<subsystem xmlns="urn:jboss:domain:ee:1.0">
	 <global-modules>
	        <module name="de.akquinet.jbosscc.jbpm" slot="main" />
	  </global-modules>
</subsystem>

2. HOW TO USE IN YOUR APPLICATION

- edit contrib/persistence.xml and add it to your project's META-INF/ directory
- Add (at least) the following dependencies to your pom.xml

		<dependency>
			<groupId>org.jbpm</groupId>
			<artifactId>jbpm-human-task-noPersistenceXml</artifactId>
			<version>${jbpm.version}</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.jbpm</groupId>
			<artifactId>jbpm-workitems</artifactId>
			<version>${jbpm.version}</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.jbpm</groupId>
			<artifactId>jbpm-bpmn2</artifactId>
			<version>${jbpm.version}</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.jbpm</groupId>
			<artifactId>jbpm-persistence-jpa2</artifactId>
			<version>${jbpm.version}</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.drools</groupId>
			<artifactId>drools-compiler</artifactId>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.drools</groupId>
			<artifactId>drools-core</artifactId>
			<scope>provided</scope>

		</dependency>

		<dependency>
			<groupId>org.drools</groupId>
			<artifactId>drools-persistence-jpa</artifactId>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.drools</groupId>
			<artifactId>knowledge-api</artifactId>
			<scope>provided</scope>
		</dependency>




3. HOWTO BUILD FROm SCRATCH

THIS WILL WORK WITH *NIX SYSTEM ONLY!!

(Windows users may take the generated file in the contrib directory)

Step 1: Build patched JPA-relevant modules

cd jbpm-5.1.0.Final
mvn clean install

Step 2: Build module zip

cd jbpm-module
mvn clean assembly:single exec:exec assembly:single

Result is in target/jbpm-as7-module-assemble-module.zip




