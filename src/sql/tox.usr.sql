set echo on
spool tox.usr.sql.err

-- $Id: tox.usr.sql 5 2009-10-16 15:20:39Z dacracot $

/*------------------------------------------------------------------------*/

CREATE USER tox IDENTIFIED BY toxbaby DEFAULT TABLESPACE data TEMPORARY TABLESPACE temp;

GRANT connect, resource TO tox;

/*------------------------------------------------------------------------*/

spool off
