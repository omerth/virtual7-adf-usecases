<?xml version = '1.0' encoding = 'UTF-8'?>
<trigger xmlns="http://xmlns.oracle.com/jdeveloper/1111/offlinedb">
  <ID class="oracle.javatools.db.IdentifierBasedID">
    <identifier class="java.lang.String">e87d7797-d635-4df5-b263-8b99b8836cd1</identifier>
  </ID>
  <name>MESSAGES_TRG</name>
  <baseType>TABLE</baseType>
  <code>BEGIN
  IF :NEW.ID IS NULL OR :NEW.ID&lt;0
  THEN
    SELECT MESSAGES_SEQ.NEXTVAL into :NEW.ID FROM DUAL;
  END IF;
END;</code>
  <enabled>true</enabled>
  <events>
    <event>INSERT</event>
  </events>
  <schema>
    <name>HR</name>
  </schema>
  <source>CREATE OR REPLACE TRIGGER MESSAGES_TRG 
BEFORE INSERT ON MESSAGES
FOR EACH ROW
BEGIN
  IF :NEW.ID IS NULL OR :NEW.ID&lt;0
  THEN
    SELECT MESSAGES_SEQ.NEXTVAL into :NEW.ID FROM DUAL;
  END IF;
END;</source>
  <statementLevel>false</statementLevel>
  <tableID class="oracle.javatools.db.IdentifierBasedID">
    <name>MESSAGES</name>
    <identifier class="java.lang.String">fb79e5f2-f94c-4a3d-9a54-add2ecce15ca</identifier>
    <schemaName>HR</schemaName>
    <type>TABLE</type>
  </tableID>
  <timing>BEFORE</timing>
</trigger>
