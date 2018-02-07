set echo on
spool tox.usr.sql.err

/*------------------------------------------------------------------------*/

CREATE USER tox IDENTIFIED BY t0xb8by DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;

ALTER USER tox QUOTA UNLIMITED ON users; 

GRANT CONNECT TO tox;
GRANT RESOURCE TO tox;

/*------------------------------------------------------------------------*/

spool off
