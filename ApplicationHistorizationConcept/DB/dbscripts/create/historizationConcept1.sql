SELECT t1.s as t1_s,
  t1.e as t1_e,
  t1.LAST_NAME,
  t2.s as t2_s,
  t2.e as t2_e,
  t2.JOB_TITLE,
  t3.s as t3_s,
  t3.e as t3_e,
  t3.DEPARTMENT_NAME
FROM
  EMPLOYEES_HIST1 t1,
  JOBS_HIST1 t2,
  DEPARTMENTS_HIST1 t3
WHERE t1.JOB_ID = t2.JOB_ID
AND t1.DEPARTMENT_ID = t3.DEPARTMENT_ID
AND (t1.s=t1.e OR t2.s=t2.e OR (t1.s=t2.s AND t1.e=t2.e) OR (t2.s < t1.e AND t2.e > t1.s) OR (t1.s < t2.e AND t1.e > t2.s))
AND (t1.s=t1.e OR t3.s=t3.e OR (t1.s=t3.s AND t1.e=t3.e) OR (t3.s < t1.e AND t3.e > t1.s) OR (t1.s < t3.e AND t1.e > t3.s))
AND (t2.s=t2.e OR t3.s=t3.e OR (t2.s=t3.s AND t2.e=t3.e) OR (t3.s < t2.e AND t3.e > t2.s) OR (t2.s < t3.e AND t2.e > t3.s))
AND t1.EMPLOYEE_ID = 100;
