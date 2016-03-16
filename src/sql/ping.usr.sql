set echo on
spool ping.usr.sql.err

-- $Id: ping.usr.sql 5 2009-10-16 15:20:39Z dacracot $

/*------------------------------------------------------------------------*/

CREATE USER example IDENTIFIED BY examplebaby DEFAULT TABLESPACE data TEMPORARY TABLESPACE temp;

GRANT connect, resource TO example;

/*------------------------------------------------------------------------*/

spool off
