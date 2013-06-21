SPOOL 001_create_tablespaces_and_users.log

DECLARE
  v_user_name	 		VARCHAR2(1024) :='&APP_USER_NAME';
  v_user_password	 	VARCHAR2(1024) :='&APP_USER_PASSWORD';
  
  v_user_aqadm_name 		VARCHAR2(1024) :='&APP_AQADM_USER_NAME';
  v_user_aqadm_password 	VARCHAR2(1024) :='&APP_AQADM_USER_PASSWORD';
  
  v_user_apps_utilities_name    VARCHAR2(1024) :='&APP_UTILS_USER_NAME';
  
  v_tablespace_path 		VARCHAR2(1024) :='';
  v_tablespace_name 		VARCHAR2(1024) :='v_user_name;
  
  v_queue_name  	 	VARCHAR2(1024) :='&APP_USER_NAME' || '_QUEUE';
  v_queue_table_name  		VARCHAR2(1024) :=v_queue_name||'_TABLE';
  
  v_count               	NUMBER;
BEGIN
	SELECT SUBSTR(name,0, INSTR(UPPER(name), 'SYSTEM',-1)-1) into  v_tablespace_path FROM v$datafile_header WHERE UPPER(tablespace_name)='SYSTEM';
	
	SELECT COUNT (1) INTO v_count FROM dba_users WHERE username = UPPER (v_user_name);
	IF v_count != 0  THEN
		EXECUTE IMMEDIATE 'DROP USER '||v_user_name||' cascade';
	END IF;
  
	SELECT COUNT (1) INTO v_count FROM dba_tablespaces WHERE tablespace_name = UPPER (v_tablespace_name);
	IF v_count != 0 THEN
		EXECUTE IMMEDIATE 'DROP TABLESPACE '||v_tablespace_name||' INCLUDING CONTENTS';
	END IF;
	
	EXECUTE IMMEDIATE 'CREATE TABLESPACE '||v_tablespace_name||' LOGGING DATAFILE '''||v_tablespace_path||v_tablespace_name||'.dbf'||''' SIZE 50M REUSE AUTOEXTEND ON';
	EXECUTE IMMEDIATE 'CREATE USER '||v_user_name||' IDENTIFIED BY '||v_user_password||' DEFAULT TABLESPACE '||v_tablespace_name||' PROFILE DEFAULT ACCOUNT UNLOCK';
	EXECUTE IMMEDIATE 'ALTER USER '||v_user_name||' TEMPORARY TABLESPACE TEMP';
	EXECUTE IMMEDIATE 'GRANT CONNECT TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT RESOURCE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE TABLE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE VIEW TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE SEQUENCE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE SYNONYM TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE PROCEDURE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE TRIGGER TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE TYPE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE OPERATOR TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE INDEXTYPE TO '||v_user_name;
	EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO '||v_user_name;
    
    EXECUTE IMMEDIATE 'ALTER USER '||v_user_name||' QUOTA UNLIMITED ON '||v_tablespace_name; 
	
	--define here the grants on other schema objects
  
  
	--create app user
	SELECT COUNT (1) INTO v_count FROM dba_users WHERE username = UPPER (v_user_aqadm_name);
	
	IF v_count != 0 THEN
	EXECUTE IMMEDIATE 'DROP USER '||v_user_aqadm_name||' CASCADE';
	END IF;
	    
	EXECUTE IMMEDIATE 'CREATE USER '||v_user_aqadm_name||' IDENTIFIED BY '||v_user_aqadm_password||' DEFAULT TABLESPACE '||v_tablespace_name||' TEMPORARY TABLESPACE temp PROFILE DEFAULT ACCOUNT UNLOCK';
	EXECUTE IMMEDIATE 'GRANT CONNECT TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT RESOURCE TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT AQ_USER_ROLE TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT AQ_ADMINISTRATOR_ROLE TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON DBMS_AQADM TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON DBMS_AQ TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON DBMS_AQIN TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON DBMS_AQJMS TO '||v_user_aqadm_name;
	EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO '||v_user_aqadm_name;
        
        IF v_user_apps_utilities_name != v_user_name THEN
            EXECUTE IMMEDIATE 'GRANT EXECUTE ON '||v_user_apps_utilities_name||'.CHARTABLETYPE TO '||v_user_name; 
            EXECUTE IMMEDIATE 'GRANT EXECUTE ON '||v_user_apps_utilities_name||'.NUMBERTABLETYPE TO '||v_user_name;
        END IF;
    
    EXECUTE IMMEDIATE 'ALTER USER '||v_user_aqadm_name||' QUOTA UNLIMITED ON '||v_tablespace_name; 
  
          
	DBMS_AQADM.GRANT_SYSTEM_PRIVILEGE(privilege => 'ENQUEUE_ANY',grantee=> v_user_aqadm_name,admin_option => FALSE);
	-- allow app user to read all messages from db ques
	DBMS_AQADM.GRANT_SYSTEM_PRIVILEGE(privilege => 'DEQUEUE_ANY',grantee => v_user_aqadm_name, admin_option => FALSE);
  
  
	--Create and start the queue:
	DBMS_AQADM.CREATE_QUEUE_TABLE (queue_table => v_user_aqadm_name||'.'||v_queue_table_name,multiple_consumers => false, queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE');
	DBMS_AQADM.CREATE_QUEUE (queue_name =>v_user_aqadm_name||'.'||v_queue_name,queue_table =>v_user_aqadm_name||'.'||v_queue_table_name);
	DBMS_AQADM.START_QUEUE (queue_name => v_user_aqadm_name||'.'||v_queue_name);



	EXECUTE IMMEDIATE
'
CREATE OR REPLACE TYPE '||v_user_aqadm_name||'.PARG AS OBJECT (
            name                VARCHAR2(1024),
            value               VARCHAR2(32767)
);

';
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON '||v_user_aqadm_name||'.PARG TO '||v_user_name; 
	EXECUTE IMMEDIATE
'
CREATE OR REPLACE TYPE '||v_user_aqadm_name||'.PARG_LIST AS TABLE OF PARG;
';
	EXECUTE IMMEDIATE 'GRANT EXECUTE ON '||v_user_aqadm_name||'.PARG_LIST TO '||v_user_name; 
	EXECUTE IMMEDIATE
'CREATE OR REPLACE PROCEDURE '||v_user_aqadm_name||'.ENQUEUE (
    p_params PARG_LIST
) 
AS 
	msg                       SYS.AQ$_JMS_TEXT_MESSAGE;
    queue_options             DBMS_AQ.ENQUEUE_OPTIONS_T;
    msg_properties            DBMS_AQ.MESSAGE_PROPERTIES_T;
    msg_id                    RAW(16);
    no_recipients_for_message EXCEPTION;
    PRAGMA EXCEPTION_INIT(no_recipients_for_message, -24033);
BEGIN
	msg := SYS.AQ$_JMS_TEXT_MESSAGE.CONSTRUCT();
    IF p_params.COUNT>0 THEN 
    FOR i IN p_params.FIRST..p_params.LAST
    LOOP
      msg.set_string_property(p_params(i).name, p_params(i).value);
    END LOOP;
    END IF;

    DBMS_AQ.ENQUEUE(
      queue_name => '''||v_user_aqadm_name||'.'||v_queue_name||''', 
      enqueue_options =>queue_options, 
      message_properties => msg_properties, 
      payload => msg,
      msgid => msg_id
    );
EXCEPTION
    WHEN no_recipients_for_message THEN NULL;-- ignore
END;
';
        EXECUTE IMMEDIATE 'GRANT EXECUTE ON '||v_user_aqadm_name||'.ENQUEUE TO '||v_user_name; 
END;
/
SPOOL OFF;