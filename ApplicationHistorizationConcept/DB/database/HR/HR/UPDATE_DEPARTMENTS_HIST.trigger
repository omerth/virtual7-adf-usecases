<?xml version = '1.0' encoding = 'UTF-8'?>
<trigger xmlns="http://xmlns.oracle.com/jdeveloper/1111/offlinedb">
  <ID class="oracle.javatools.db.IdentifierBasedID">
    <identifier class="java.lang.String">05cbdc3b-005b-4fd7-9f31-e2ce008dc426</identifier>
  </ID>
  <name>UPDATE_DEPARTMENTS_HIST</name>
  <baseType>TABLE</baseType>
  <code>DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM DEPARTMENTS_HIST WHERE DEPARTMENT_ID=:OLD.DEPARTMENT_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO DEPARTMENTS_HIST (DEPARTMENT_ID,DEPARTMENT_NAME,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.DEPARTMENT_ID,:OLD.DEPARTMENT_NAME,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
END;</code>
  <columnIDs>
    <columnID class="oracle.javatools.db.IdentifierBasedID">
      <name>DEPARTMENT_NAME</name>
      <identifier class="java.lang.String">a6502d8b-f32c-45c3-8562-939f8dfaa7f9</identifier>
      <parent class="oracle.javatools.db.IdentifierBasedID">
        <name>DEPARTMENTS</name>
        <identifier class="java.lang.String">2d971aa0-6777-40aa-9458-7f4b343ad7db</identifier>
        <schemaName>HR</schemaName>
        <type>TABLE</type>
      </parent>
      <schemaName>HR</schemaName>
      <type>COLUMN</type>
    </columnID>
  </columnIDs>
  <enabled>true</enabled>
  <events>
    <event>UPDATE</event>
  </events>
  <properties>
    <entry>
      <key>OfflineDBConstants.IMPORT_SOURCE_CONNECTION</key>
      <value class="java.lang.String">hr</value>
    </entry>
    <entry>
      <key>OfflineDBConstants.IMPORT_SOURCE_ID</key>
      <value class="oracle.javatools.db.ReferenceID">
        <name>UPDATE_DEPARTMENTS_HIST</name>
        <identifier class="java.math.BigDecimal">74581</identifier>
        <schemaName>HR</schemaName>
        <type>TRIGGER</type>
      </value>
    </entry>
    <entry>
      <key>Timestamp</key>
    </entry>
  </properties>
  <schema>
    <name>HR</name>
  </schema>
  <source>TRIGGER UPDATE_DEPARTMENTS_HIST 
AFTER UPDATE OF DEPARTMENT_NAME ON DEPARTMENTS 
FOR EACH ROW 
DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM DEPARTMENTS_HIST WHERE DEPARTMENT_ID=:OLD.DEPARTMENT_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO DEPARTMENTS_HIST (DEPARTMENT_ID,DEPARTMENT_NAME,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.DEPARTMENT_ID,:OLD.DEPARTMENT_NAME,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
END;
</source>
  <statementLevel>false</statementLevel>
  <tableID class="oracle.javatools.db.IdentifierBasedID">
    <name>DEPARTMENTS</name>
    <identifier class="java.lang.String">2d971aa0-6777-40aa-9458-7f4b343ad7db</identifier>
    <schemaName>HR</schemaName>
    <type>TABLE</type>
  </tableID>
  <timing>AFTER</timing>
</trigger>
