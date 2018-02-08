set echo on
spool example.usr.sql.err

/*------------------------------------------------------------------------*/

CREATE USER example IDENTIFIED BY ex8mpleb8by DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;

ALTER USER example QUOTA UNLIMITED ON users; 

GRANT CONNECT TO example;
GRANT RESOURCE TO example;

/*------------------------------------------------------------------------*/

spool off
