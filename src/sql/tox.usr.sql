set echo on
spool tox.usr.sql.err

/*------------------------------------------------------------------------*/

CREATE USER tox IDENTIFIED BY toxbaby DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;

ALTER USER tox QUOTA UNLIMITED ON users; 

GRANT CONNECT TO tox;
GRANT RESOURCE TO tox;

/*------------------------------------------------------------------------*/

spool off
