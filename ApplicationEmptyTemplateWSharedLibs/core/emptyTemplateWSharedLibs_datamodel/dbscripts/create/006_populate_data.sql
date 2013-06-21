SPOOL 006_populate_data.log

SET DEFINE OFF;

----------------------------------
-- START Set CONFIG VALUES.
----------------------------------
DELETE FROM CONFIG;

INSERT INTO CONFIG (ID,CONFIG_KEY,CONFIG_VALUE) VALUES(1,'SCHEMA_VERSION','1.0.1');


----------------------------------
-- END Set CONFIG VALUES.
----------------------------------

COMMIT;

SET DEFINE ON;

SPOOL OFF;