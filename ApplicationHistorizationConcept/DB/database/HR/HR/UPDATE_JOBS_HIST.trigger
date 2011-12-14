<?xml version = '1.0' encoding = 'UTF-8'?>
<trigger xmlns="http://xmlns.oracle.com/jdeveloper/1111/offlinedb">
  <ID class="oracle.javatools.db.IdentifierBasedID">
    <identifier class="java.lang.String">e13c5dd6-ddf5-4efa-9029-92f548a2c125</identifier>
  </ID>
  <name>UPDATE_JOBS_HIST</name>
  <baseType>TABLE</baseType>
  <code>DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM JOBS_HIST WHERE JOB_ID=:OLD.JOB_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO JOBS_HIST (JOB_ID,JOB_TITLE,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.JOB_ID,:OLD.JOB_TITLE,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
END;</code>
  <columnIDs>
    <columnID class="oracle.javatools.db.IdentifierBasedID">
      <name>JOB_TITLE</name>
      <identifier class="java.lang.String">7edafd95-4ab9-44f7-bfad-b3e8f5f835c3</identifier>
      <parent class="oracle.javatools.db.IdentifierBasedID">
        <name>JOBS</name>
        <identifier class="java.lang.String">2f5c1e05-bdb3-4c25-bcd9-72c03429b30a</identifier>
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
        <name>UPDATE_JOBS_HIST</name>
        <identifier class="java.math.BigDecimal">74578</identifier>
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
  <source>TRIGGER UPDATE_JOBS_HIST 
AFTER UPDATE OF JOB_TITLE ON JOBS 
FOR EACH ROW 
DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM JOBS_HIST WHERE JOB_ID=:OLD.JOB_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO JOBS_HIST (JOB_ID,JOB_TITLE,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.JOB_ID,:OLD.JOB_TITLE,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
END;
</source>
  <statementLevel>false</statementLevel>
  <tableID class="oracle.javatools.db.IdentifierBasedID">
    <name>JOBS</name>
    <identifier class="java.lang.String">2f5c1e05-bdb3-4c25-bcd9-72c03429b30a</identifier>
    <schemaName>HR</schemaName>
    <type>TABLE</type>
  </tableID>
  <timing>AFTER</timing>
</trigger>
