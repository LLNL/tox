set echo on
spool ping.pkg.sql.err

CREATE OR REPLACE PACKAGE ping IS
	/*========================================================================*/
-- $Id: ping.pkg.sql 5 2009-10-16 15:20:39Z dacracot $
	/*========================================================================*/
	/*=======================*/
		FUNCTION alive
			RETURN SYS_REFCURSOR;
	/*=======================*/
		FUNCTION formatted
			(
			in_mask IN VARCHAR2
			)
			RETURN SYS_REFCURSOR;
	/*=======================*/
	END ping;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE ping;

/*------------------------------------------------------------------------*/

spool off
