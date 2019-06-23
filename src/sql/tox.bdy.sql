set echo on
set define off

CREATE OR REPLACE PACKAGE BODY tox
	AS
	/*========================================================================*/
	PROCEDURE begin_spool
		AS
		/*-----------------------*/
		/*=======================*/
		BEGIN
		/*=======================*/
			SELECT
				key.NEXTVAL
			INTO
				g_spool_key
			FROM
				DUAL;
		/*=======================*/
		END begin_spool;
	/*========================================================================*/
	PROCEDURE into_spool
		(
		in_txt IN spool.txt%TYPE
		)
		AS
		/*-----------------------*/
		/*=======================*/
		BEGIN
		/*=======================*/
			INSERT INTO
				spool
			VALUES
				(
				g_spool_key,
				in_txt,
				seq.NEXTVAL
				);
		/*=======================*/
		END into_spool;
	/*========================================================================*/
	PROCEDURE reset_spool
		AS
		/*-----------------------*/
		/*=======================*/
		BEGIN
		/*=======================*/
			DELETE
				spool
			WHERE
				key = g_spool_key;
			COMMIT;
			begin_spool;
		/*=======================*/
		END reset_spool;
	/*========================================================================*/
	FUNCTION end_spool
		RETURN t_spool
		AS
		/*-----------------------*/
		v_spool t_spool;
		/*=======================*/
		BEGIN
		/*=======================*/
		/*-----------------------*/
			COMMIT;
			OPEN v_spool FOR
				SELECT
					*
				FROM
					spool
				WHERE
					key = g_spool_key
				ORDER BY
					seq;
			RETURN v_spool;
		/*=======================*/
		END end_spool;
	/*========================================================================*/
	FUNCTION timestamp
		RETURN VARCHAR2
		AS
		/*-----------------------*/
		v_result VARCHAR2(14);
		/*=======================*/
		BEGIN
		/*=======================*/
			SELECT
				TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')
			INTO
				v_result
			FROM
				DUAL;
			RETURN v_result;
		/*=======================*/
		END timestamp;
	/*========================================================================*/
	FUNCTION encode
		(
		in_original IN VARCHAR2
		)
		RETURN VARCHAR2
		AS
		/*-----------------------*/
		v_result VARCHAR2(2048);
		/*=======================*/
		BEGIN
		/*=======================*/
		IF (in_original IS NOT NULL) THEN
			v_result:= REPLACE(in_original,'&','&amp;');
			v_result:= REPLACE(v_result,'"','&quot;');
			v_result:= REPLACE(v_result,'<','&lt;');
			v_result:= REPLACE(v_result,'>','&gt;');
		ELSE
			v_result:= NULL;
		END IF;
		/*-----------------------*/
		RETURN v_result;
		/*=======================*/
		END encode;
	/*========================================================================*/
	END tox;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE BODY tox;

/*------------------------------------------------------------------------*/

