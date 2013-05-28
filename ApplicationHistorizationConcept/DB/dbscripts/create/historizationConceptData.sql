begin 

--reset
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive' where DEPARTMENT_ID=90;

update DEPARTMENTS set DEPARTMENT_NAME = 'IT' where DEPARTMENT_ID=60;

update EMPLOYEES set LAST_NAME = 'King', JOB_ID = 'AD_PRES' where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President' where JOB_ID='AD_PRES';

update DEPARTMENTS set LAST_CHANGE_DATE = SYSTIMESTAMP;
update EMPLOYEES set LAST_CHANGE_DATE = SYSTIMESTAMP;
update JOBS set LAST_CHANGE_DATE = SYSTIMESTAMP;

delete from employees_hist;
delete from jobs_hist;
delete from departments_hist;

commit;

--DBMS_LOCK.SLEEP(0.001);


--version1
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v1' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;


update EMPLOYEES set LAST_NAME = 'King v1' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v1' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;

--DBMS_LOCK.SLEEP(0.001);

--version2
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v2' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v2' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v2' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;

--DBMS_LOCK.SLEEP(0.001);
--version3
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v3' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v3' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

commit;

--DBMS_LOCK.SLEEP(0.001);

--version4
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v4' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;

update JOBS set JOB_TITLE = 'President v4' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;

--DBMS_LOCK.SLEEP(0.001);

--version5
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v5' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v5' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v5' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;

--DBMS_LOCK.SLEEP(0.001);

--version6
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v6' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v6' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v6' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;

--DBMS_LOCK.SLEEP(0.001);

--version7
update EMPLOYEES set LAST_NAME = 'King v7 1' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

--update EMPLOYEES set LAST_NAME = 'King v7 2' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

--update EMPLOYEES set LAST_NAME = 'King v7 3' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

commit;

--DBMS_LOCK.SLEEP(0.001);

--version8
update EMPLOYEES set DEPARTMENT_ID = 60, LAST_NAME = 'King v8' ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update DEPARTMENTS set DEPARTMENT_NAME = 'IT v8' ,LAST_CHANGE_DATE = SYSTIMESTAMP where DEPARTMENT_ID=60;
commit;

--DBMS_LOCK.SLEEP(0.001);

--version9

update EMPLOYEES set DEPARTMENT_ID = 90 ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v9' ,LAST_CHANGE_DATE = SYSTIMESTAMP where JOB_ID='AD_PRES';
commit;
--DBMS_LOCK.SLEEP(0.001);


--version9++
for i in 1..100 loop
update EMPLOYEES set LAST_NAME = 'King v'||(8+i) ,LAST_CHANGE_DATE = SYSTIMESTAMP where EMPLOYEE_ID=100;
commit;
--DBMS_LOCK.SLEEP(0.001);
end loop;


end;
