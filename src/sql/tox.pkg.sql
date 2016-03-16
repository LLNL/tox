set echo on
spool tox.pkg.sql.err

CREATE OR REPLACE PACKAGE tox IS
	/*========================================================================*/
-- $Id: tox.pkg.sql 5 2009-10-16 15:20:39Z dacracot $
	/*========================================================================*/
		g_spool_key spool.key%TYPE := NULL;
		TYPE t_spool IS REF CURSOR RETURN spool%ROWTYPE;
	/*=======================*/
		PROCEDURE begin_spool;
	/*=======================*/
		PROCEDURE into_spool
			(
			in_txt IN spool.txt%TYPE
			);
	/*=======================*/
		PROCEDURE reset_spool;
	/*=======================*/
		FUNCTION end_spool
			RETURN t_spool;
	/*=======================*/
		FUNCTION timestamp
			RETURN VARCHAR2;
	/*=======================*/
	END tox;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE tox;

/*------------------------------------------------------------------------*/

spool off
