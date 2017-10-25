set echo on
spool tox.ddl.sql.err

/*------------------------------------------------------------------------*/

CREATE SEQUENCE key START WITH 10000;
CREATE SEQUENCE seq START WITH 1;

/*------------------------------------------------------------------------*/

CREATE TABLE spool
	(
	key NUMBER(11) NOT NULL,
	txt CLOB NULL,
	seq NUMBER(11) NOT NULL
	)
STORAGE
	(
	INITIAL		1M
	NEXT		1M
	PCTINCREASE	0
	MINEXTENTS	1
	MAXEXTENTS	UNLIMITED
	)
TABLESPACE users
;

ALTER TABLE spool NOLOGGING;

/*------------------------------------------------------------------------*/

spool off
