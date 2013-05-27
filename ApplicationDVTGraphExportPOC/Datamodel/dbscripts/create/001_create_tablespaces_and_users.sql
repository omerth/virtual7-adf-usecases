
DECLARE
  v_user_name	 		VARCHAR2(1024) :='&APP_USER_NAME';
  v_user_password	 	VARCHAR2(1024) :='&DVT_POC_PASSWORD';
  
  v_tablespace_path 		VARCHAR2(1024) :='';
  v_tablespace_name 		VARCHAR2(1024) :='TS_'||v_user_name;
  
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
	EXECUTE IMMEDIATE 'GRANT CREATE MATERIALIZED VIEW TO '||v_user_name;
    
    EXECUTE IMMEDIATE 'ALTER USER '||v_user_name||' QUOTA UNLIMITED ON '||v_tablespace_name; 
	
END;
/
