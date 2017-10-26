set echo on
spool get.bdy.sql.err
set define off

CREATE OR REPLACE PACKAGE BODY get
	AS
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
			tox.tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.tox.into_spool(v_prettyDate);
			tox.tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.tox.reset_spool;
				v_error:= tox.tox.encode(Sqlerrm);
				tox.tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
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
			tox.tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.tox.into_spool(v_prettyDate);
			tox.tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.tox.reset_spool;
				v_error:= tox.tox.encode(Sqlerrm);
				tox.tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.tox.end_spool;
		/*=======================*/
		END formatted;
	/*========================================================================*/
	END get;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE BODY get;

/*------------------------------------------------------------------------*/

spool off
