<?xml version = '1.0' encoding = 'UTF-8'?>
<trigger xmlns="http://xmlns.oracle.com/jdeveloper/1111/offlinedb">
  <ID class="oracle.javatools.db.IdentifierBasedID">
    <identifier class="java.lang.String">bbd55e11-7a25-4379-881d-146be2ad2ad3</identifier>
  </ID>
  <name>UPDATE_EMPLOYEES_HIST</name>
  <baseType>TABLE</baseType>
  <code>DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM EMPLOYEES_HIST WHERE EMPLOYEE_ID=:OLD.EMPLOYEE_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO EMPLOYEES_HIST (EMPLOYEE_ID,DEPARTMENT_ID,LAST_NAME,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.EMPLOYEE_ID,:OLD.DEPARTMENT_ID,:OLD.LAST_NAME,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
  
  
END;</code>
  <columnIDs>
    <columnID class="oracle.javatools.db.IdentifierBasedID">
      <name>LAST_NAME</name>
      <identifier class="java.lang.String">c436b070-2bc7-4218-9876-657c6442ca97</identifier>
      <parent class="oracle.javatools.db.IdentifierBasedID">
        <name>EMPLOYEES</name>
        <identifier class="java.lang.String">b69e5485-f7dc-42f8-be10-717b4d8376a2</identifier>
        <schemaName>HR</schemaName>
        <type>TABLE</type>
      </parent>
      <schemaName>HR</schemaName>
      <type>COLUMN</type>
    </columnID>
    <columnID class="oracle.javatools.db.IdentifierBasedID">
      <name>DEPARTMENT_ID</name>
      <identifier class="java.lang.String">32baa44d-14d8-41d0-b03f-e5356035e737</identifier>
      <parent class="oracle.javatools.db.IdentifierBasedID">
        <name>EMPLOYEES</name>
        <identifier class="java.lang.String">b69e5485-f7dc-42f8-be10-717b4d8376a2</identifier>
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
        <name>UPDATE_EMPLOYEES_HIST</name>
        <identifier class="java.math.BigDecimal">74575</identifier>
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
  <source>TRIGGER UPDATE_EMPLOYEES_HIST 
AFTER UPDATE OF DEPARTMENT_ID, LAST_NAME ON EMPLOYEES 
FOR EACH ROW 
DECLARE
V_START NUMBER :=0;
V_END NUMBER :=0;
BEGIN
  SELECT NVL(MAX(VERSION_END),0) INTO V_START FROM EMPLOYEES_HIST WHERE EMPLOYEE_ID=:OLD.EMPLOYEE_ID;
  SELECT start_scn INTO V_END FROM v$transaction;
  if(V_START&lt;&gt;V_END)THEN
  INSERT INTO EMPLOYEES_HIST (EMPLOYEE_ID,DEPARTMENT_ID,LAST_NAME,LAST_CHANGE_DATE,LAST_CHANGE_USER,VERSION_START,VERSION_END) VALUES(:OLD.EMPLOYEE_ID,:OLD.DEPARTMENT_ID,:OLD.LAST_NAME,:OLD.LAST_CHANGE_DATE,:OLD.LAST_CHANGE_USER,V_START,V_END);
  END IF;
  
  
END;
</source>
  <statementLevel>false</statementLevel>
  <tableID class="oracle.javatools.db.IdentifierBasedID">
    <name>EMPLOYEES</name>
    <identifier class="java.lang.String">b69e5485-f7dc-42f8-be10-717b4d8376a2</identifier>
    <schemaName>HR</schemaName>
    <type>TABLE</type>
  </tableID>
  <timing>AFTER</timing>
</trigger>
