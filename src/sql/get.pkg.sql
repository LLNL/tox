set echo on
spool get.pkg.sql.err

CREATE OR REPLACE PACKAGE get AS
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
	END get;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE get;

/*------------------------------------------------------------------------*/

spool off
