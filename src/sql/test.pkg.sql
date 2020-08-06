set echo on

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
		FUNCTION formatted
			(
			in_maskIn IN VARCHAR2,
			in_date IN VARCHAR2,
			in_maskOut IN VARCHAR2
			)
			RETURN SYS_REFCURSOR;
	/*=======================*/
		FUNCTION echo
			(
			in_payload IN VARCHAR2
			)
			RETURN SYS_REFCURSOR;
	/*=======================*/
		FUNCTION testXml
			(
			in_root IN VARCHAR2
			)
			RETURN SYS_REFCURSOR;
	/*=======================*/
		FUNCTION testText
			RETURN SYS_REFCURSOR;
	/*=======================*/
	END test;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE test;

/*------------------------------------------------------------------------*/

