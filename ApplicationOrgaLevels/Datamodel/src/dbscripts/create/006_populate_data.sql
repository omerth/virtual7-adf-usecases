
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (1,'Orga 1',1);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (2,'Orga 2',2);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (3,'Orga 3',3);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (4,'Orga 4',4);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (5,'Orga 5',5);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (6,'Org 2',22);
Insert into HR.ORGA_HEAD (ORGA_SET_ID,ORGA_VAL,ORGA_COST) values (7,'Org 3',33);


Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (null,1);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (1,2);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (2,3);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (3,4);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (4,5);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (1,6);
Insert into HR.ORGA_NODE (ORGA_SET_ID,ORGA_SUBSET_ID) values (7,7);


Insert into HR.ORGA_PHASE (PHASE_ID,PHASE_VAL,PHASE_DESC,ORGA_ID) values (1,1,'Phase 1',5);
Insert into HR.ORGA_PHASE (PHASE_ID,PHASE_VAL,PHASE_DESC,ORGA_ID) values (2,1,'Phase 2',5);

Commit;
