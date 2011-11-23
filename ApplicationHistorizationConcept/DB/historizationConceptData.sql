--reset
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President'
where JOB_ID='AD_PRES';

update JOBS set JOB_TITLE = 'Administration Vice President'
where JOB_ID='AD_VP';

delete from employees_hist;
delete from jobs_hist;
delete from departments_hist;
commit;

--version1
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v1'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v1'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v1'
where JOB_ID='AD_PRES';
commit;

--version2
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v2'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v2'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v2'
where JOB_ID='AD_PRES';
commit;

--version3
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v3'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v3'
where EMPLOYEE_ID=100;

commit;

--version4
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v4'
where DEPARTMENT_ID=90;

update JOBS set JOB_TITLE = 'President v4'
where JOB_ID='AD_PRES';
commit;

--version5
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v5'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v5'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v5'
where JOB_ID='AD_PRES';
commit;

--version6
update DEPARTMENTS set DEPARTMENT_NAME = 'Executive v6'
where DEPARTMENT_ID=90;

update EMPLOYEES set LAST_NAME = 'King v6'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v6'
where JOB_ID='AD_PRES';
commit;

--version7
update EMPLOYEES set LAST_NAME = 'King v7 1'
where EMPLOYEE_ID=100;

update EMPLOYEES set LAST_NAME = 'King v7 2'
where EMPLOYEE_ID=100;

update EMPLOYEES set LAST_NAME = 'King v7 3'
where EMPLOYEE_ID=100;

commit;

--version8
update EMPLOYEES set JOB_ID = 'AD_VP', LAST_NAME = 'King v8'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'Administration Vice President v8'
where JOB_ID='AD_VP';
commit;

--version9

update EMPLOYEES set JOB_ID = 'AD_PRES'
where EMPLOYEE_ID=100;

update JOBS set JOB_TITLE = 'President v9'
where JOB_ID='AD_PRES';
commit;