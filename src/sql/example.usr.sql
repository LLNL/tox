set echo on
spool ping.usr.sql.err

/*------------------------------------------------------------------------*/

CREATE USER example IDENTIFIED BY examplebaby DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;

ALTER USER example QUOTA UNLIMITED ON users; 

GRANT CONNECT TO example;
GRANT RESOURCE TO example;

/*------------------------------------------------------------------------*/

spool off
