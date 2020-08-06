set echo on
spool test.ddl.sql.err

/*------------------------------------------------------------------------*/

CREATE TABLE testing
	(
	key NUMBER(11) NOT NULL,
	txt VARCHAR2(255) NULL,
	seq NUMBER(11) NOT NULL
	);

INSERT INTO testing VALUES (1,'one',1000);
INSERT INTO testing VALUES (2,'two',1001);
INSERT INTO testing VALUES (3,'three',1002);
INSERT INTO testing VALUES (4,'four',1003);
INSERT INTO testing VALUES (5,'five',1004);
COMMIT;

/*------------------------------------------------------------------------*/

spool off
