set echo on

CREATE OR REPLACE PACKAGE tox AS
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
	FUNCTION encode
		(
		in_original IN VARCHAR2
		)
		RETURN VARCHAR2;
	/*=======================*/
	END tox;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE tox;

/*------------------------------------------------------------------------*/

