set echo on
spool test.pkg.sql.err

CREATE OR REPLACE PACKAGE test AS
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
		FUNCTION echo
			(
			in_payload IN VARCHAR2
			)
			RETURN SYS_REFCURSOR;
	/*=======================*/
	END test;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE test;

/*------------------------------------------------------------------------*/

spool off
