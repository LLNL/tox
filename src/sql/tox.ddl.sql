set echo on
spool tox.ddl.sql.err

-- $Id: tox.ddl.sql 13 2010-10-13 17:54:42Z dacracot $

/*------------------------------------------------------------------------*/

CREATE SEQUENCE	   key START WITH 1;
CREATE SEQUENCE	   seq START WITH 1;

/*------------------------------------------------------------------------*/

CREATE TABLE		spool
					(
					key			NUMBER(9)		NOT NULL,
					txt			CLOB				NULL,
					seq			NUMBER(9)		NOT NULL
					)
STORAGE
					(
					INITIAL		1M
					NEXT		1M
					PCTINCREASE	0
					MINEXTENTS	1
					MAXEXTENTS	UNLIMITED
					)
TABLESPACE data
;

ALTER TABLE spool NOLOGGING;

/*------------------------------------------------------------------------*/

spool off
