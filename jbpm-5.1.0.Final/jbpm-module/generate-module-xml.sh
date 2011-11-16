#!/bin/sh

TARGET_DIR="target/generated"
MODULE_XML=$TARGET_DIR/module.xml

mkdir -p $TARGET_DIR

echo "Generating module.xml"

cat > $MODULE_XML << _EOF_
<?xml version="1.0" encoding="UTF-8"?>
 
<module xmlns="urn:jboss:module:1.0" name="de.akquinet.jbosscc.jbpm">
    <resources>
_EOF_


for file in `jar tf target/jbpm-as7-module-assemble-module.zip | grep jar | sort` ; do
	jar=`echo $file | cut -c31-`

cat >> $MODULE_XML << _EOF_
	<resource-root path="$jar" />
_EOF_
done

cat >> $MODULE_XML << _EOF_
    </resources>

  <dependencies>
    <module name="javax.persistence.api" />
    <module name="javax.transaction.api" />
    <module name="org.hibernate" />
    <module name="org.antlr" />
    <module name="org.javassist" />
    <module name="javax.persistence.api" />
    <module name="javaee.api" />
    <module name="org.jboss.as.jpa" />
  </dependencies>
</module>
_EOF_

