drop table orga_head;
drop table orga_node;
drop table ORGA_PHASE;

create table orga_head 
(
orga_set_id number(10)
orga_val varchar(30)
orga_cost number(10)
);


create table orga_node
(
orga_set_id number(10)
orga_subset_id number(10)
);


CREATE TABLE ORGA_PHASE
   (	"PHASE_ID" NUMBER(10,0), 
	"PHASE_VAL" NUMBER(10,0), 
	"PHASE_DESC" VARCHAR2(20 BYTE), 
	"ORGA_ID" NUMBER(10,0)
   );


CREATE OR REPLACE VIEW ORGA_VIEW ("PARENT_ID", "ID", "LV", "VAL", "DESCR") AS 
  select n.parent_id, n.id, level as lv, h.orga_cost val, h.orga_val descr
from
  (select orga_set_id parent_id, orga_subset_id id
   from orga_node 
      union all 
      select t.orga_subset_id, t.orga_subset_id 
      from orga_node t 
      where not exists (select 1 from orga_node t2 where t2.orga_set_id=t.orga_subset_id)
  ) n, orga_head h
where n.id = h.orga_set_id
connect by NOCYCLE level <=7 and prior n.id = n.parent_id AND PRIOR SYS_GUID () IS NOT NULL

start with n.parent_id is null;


CREATE OR REPLACE VIEW ORGA_VIEW_WITH_GROUP ("GROUP_ID", "GROUP_DESC", "ID", "DESCR", "VAL") AS 
  select l2.id group_id, l2.descr group_desc, l6.id,l6.descr ,l6.val 
	from 
	  (select parent_id, id, descr,val from orga_view where lv=6) l6,
	  (select parent_id, id, descr,val from orga_view where lv=5) l5,
	  (select parent_id, id, descr,val from orga_view where lv=4) l4,
	  (select parent_id, id, descr,val from orga_view where lv=3) l3,
	  (select parent_id, id, descr,val from orga_view where lv=2) l2

where l6.parent_id=l5.id and l5.parent_id=l4.id and l4.parent_id=l3.id and l3.parent_id=l2.id;



CREATE OR REPLACE VIEW ORGA_VIEW_WITH_GROUP_AND_PHASE ("PHASE_VAL", "PHASE_DESC", "PHASE_ID", "VAL", "DESCR", "GROUP_ID", "ID", "GROUP_DESC") AS 
  select p.phase_val, p.phase_desc, p.phase_id, v.val, v.descr, v.group_id, v.id, v.group_desc 
from orga_phase p, orga_view_with_group v
where p.orga_id = v.id;


CREATE OR REPLACE VIEW ORGA_PIVOT ("GROUP_ID", "ID", "DESCR", "VAL", "PHASE_ID", "PHASE_VAL", "PHASE_DESC") AS 
  select 'GROUP_ID' group_id, 'ID' id, 'DESCR' descr, 'VAL' VAL, o1.phase_id, 'PHASE_VAL' PHASE_VAL, 'PHASE_DESC' phase_desc from orga_phase o1 group by o1.phase_id
	union all 
	select "GROUP_ID","ID","DESCR","TO_CHAR(O.VAL)","PHASE_ID","TO_CHAR(O.PHASE_VAL)","PHASE_DESC" from (
	    select to_char(o.group_id) group_id, to_char(o.id) id, o.descr, to_char(o.val), o.phase_id, to_char(o.phase_val), o.phase_desc from orga_view_with_group_and_phase o
	    union all
	    select to_char(t.group_id), to_char(t.group_id), t.group_desc, to_char(sum(t.val)), t.phase_id, to_char(sum(t.phase_val)), '' 
	    from orga_view_with_group_and_phase t
	    group by t.group_id,t.group_desc, t.phase_id
	    order by group_id, id, phase_id
	    );



ALTER TABLE ORGA_HEAD ADD CONSTRAINT "ORGA_HEAD_PK" PRIMARY KEY ("ORGA_SET_ID");
ALTER TABLE ORGA_HEAD MODIFY ("ORGA_SET_ID" NOT NULL ENABLE);

ALTER TABLE ORGA_NODE ADD CONSTRAINT "ORGA_NODE_PK" PRIMARY KEY ("ORGA_SUBSET_ID");
ALTER TABLE ORGA_NODE MODIFY ("ORGA_SUBSET_ID" NOT NULL ENABLE);
ALTER TABLE ORGA_NODE ADD CONSTRAINT "ORGA_NODE_ORGA_HEAD_FK1" FOREIGN KEY ("ORGA_SET_ID")
	  REFERENCES "HR"."ORGA_HEAD" ("ORGA_SET_ID") ON DELETE CASCADE ENABLE;
          
ALTER TABLE ORGA_PHASE ADD CONSTRAINT "ORGA_PHASE_PK" PRIMARY KEY ("PHASE_ID");
ALTER TABLE ORGA_PHASE ADD CONSTRAINT "ORGA_PHASE_ORGA_HEAD_FK1" FOREIGN KEY ("ORGA_ID")
	  REFERENCES "HR"."ORGA_HEAD" ("ORGA_SET_ID") ON DELETE CASCADE ENABLE;