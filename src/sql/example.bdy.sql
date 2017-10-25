set echo on
spool ping.bdy.sql.err
set define off

CREATE OR REPLACE PACKAGE BODY ping
	AS
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
	FUNCTION alive
		RETURN SYS_REFCURSOR
		AS
		/*-----------------------*/
		v_timestamp VARCHAR(16);
		v_error VARCHAR2(1024);
		v_prettyDate VARCHAR(24);
		/*=======================*/
		BEGIN
		/*=======================*/
			tox.tox.begin_spool;
			v_timestamp:= tox.tox.timestamp;
		/*-----------------------*/
			SELECT
				TO_CHAR(SYSDATE,'DD-Mon-YYYY HH:MI am')
			INTO
				v_prettyDate
			FROM
				DUAL;
		/*-----------------------*/
			tox.tox.into_spool('<ping timestamp="'||v_timestamp||'" feedback="ok">');
			tox.tox.into_spool(v_prettyDate);
			tox.tox.into_spool('</ping>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.tox.reset_spool;
				v_error:= encode(Sqlerrm);
				tox.tox.into_spool('<ping timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.tox.end_spool;
		/*=======================*/
		END alive;
	/*========================================================================*/
	FUNCTION formatted
		(
		in_mask IN VARCHAR2
		)
		RETURN SYS_REFCURSOR
		AS
		/*-----------------------*/
		v_timestamp VARCHAR(16);
		v_error VARCHAR2(1024);
		v_prettyDate VARCHAR(24);
		/*=======================*/
		BEGIN
		/*=======================*/
			tox.tox.begin_spool;
			v_timestamp:= tox.tox.timestamp;
		/*-----------------------*/
			SELECT
				TO_CHAR(SYSDATE,in_mask)
			INTO
				v_prettyDate
			FROM
				DUAL;
		/*-----------------------*/
			tox.tox.into_spool('<ping timestamp="'||v_timestamp||'" feedback="ok">');
			tox.tox.into_spool(v_prettyDate);
			tox.tox.into_spool('</ping>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.tox.reset_spool;
				v_error:= encode(Sqlerrm);
				tox.tox.into_spool('<ping timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.tox.end_spool;
		/*=======================*/
		END formatted;
	/*========================================================================*/
	END ping;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE BODY ping;

/*------------------------------------------------------------------------*/

spool off
