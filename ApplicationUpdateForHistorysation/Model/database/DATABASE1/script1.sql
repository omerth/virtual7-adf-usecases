ALTER TABLE DEPARTMENTS 
DROP CONSTRAINT DEPT_LOC_FK;

ALTER TABLE DEPARTMENTS 
DROP CONSTRAINT DEPT_MGR_FK;

DROP TABLE DEPARTMENTS;

CREATE TABLE DEPARTMENTS 
(
  DEPARTMENT_ID NUMBER(4, 0) NOT NULL 
, DEPARTMENT_NAME VARCHAR2(30 BYTE) NOT NULL 
, MANAGER_ID NUMBER(6, 0) 
, LOCATION_ID NUMBER(4, 0) 
, IS_CURRENT_VERSION NUMBER(1, 0) 
, CREATION_DATE DATE 
, CONSTRAINT DEPT_ID_PK PRIMARY KEY 
  (
    DEPARTMENT_ID 
  )
  ENABLE 
);

ALTER TABLE DEPARTMENTS
ADD CONSTRAINT DEPT_LOC_FK FOREIGN KEY
(
  LOCATION_ID 
)
REFERENCES LOCATIONS
(
  LOCATION_ID 
)
ENABLE;

ALTER TABLE DEPARTMENTS
ADD CONSTRAINT DEPT_MGR_FK FOREIGN KEY
(
  MANAGER_ID 
)
REFERENCES EMPLOYEES
(
  EMPLOYEE_ID 
)
ENABLE;

ALTER TABLE DEPARTMENTS
ADD CONSTRAINT DEPT_NAME_NN CHECK 
(DEPARTMENT_NAME IS NOT NULL)
ENABLE;

COMMENT ON TABLE DEPARTMENTS IS 'Departments table that shows details of departments where employees
work. Contains 27 rows; references with locations, employees, and job_history tables.';

COMMENT ON COLUMN DEPARTMENTS.DEPARTMENT_ID IS 'Primary key column of departments table.';

COMMENT ON COLUMN DEPARTMENTS.DEPARTMENT_NAME IS 'A not null column that shows name of a department. Administration,
Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public
Relations, Sales, Finance, and Accounting. ';

COMMENT ON COLUMN DEPARTMENTS.MANAGER_ID IS 'Manager_id of a department. Foreign key to employee_id column of employees table. The manager_id column of the employee table references this column.';

COMMENT ON COLUMN DEPARTMENTS.LOCATION_ID IS 'Location id where a department is located. Foreign key to location_id column of locations table.';

CREATE INDEX DEPT_LOCATION_IX ON DEPARTMENTS (LOCATION_ID ASC) 
LOGGING 
TABLESPACE "USERS" 
PCTFREE 10 
INITRANS 2 
STORAGE 
( 
  INITIAL 65536 
  MINEXTENTS 1 
  MAXEXTENTS 2147483645 
  BUFFER_POOL DEFAULT 
);
