set echo on
spool test.bdy.sql.err
set define off

CREATE OR REPLACE PACKAGE BODY test
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
	FUNCTION formatted
		(
		in_maskIn IN VARCHAR2,
		in_date IN VARCHAR2,
		in_maskOut IN VARCHAR2
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
				TO_CHAR(TO_DATE(in_date,in_maskIn),in_maskOut)
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
	FUNCTION echo
		(
		in_payload IN VARCHAR2
		)
		RETURN SYS_REFCURSOR
		AS
		/*-----------------------*/
		v_timestamp VARCHAR(16);
		v_error VARCHAR2(1024);
		v_echoDate VARCHAR(24);
		/*=======================*/
		BEGIN
		/*=======================*/
			tox.tox.begin_spool;
			v_timestamp:= tox.tox.timestamp;
		/*-----------------------*/
			SELECT 
				ExtractValue(Value(temp),'/root/date/text()')
			INTO
				v_echoDate
			FROM
				TABLE(XMLSequence(XMLType(in_payload))) temp;
		/*-----------------------*/
			tox.tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.tox.into_spool(v_echoDate);
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
		END echo;
	/*========================================================================*/
	END test;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE BODY test;

/*------------------------------------------------------------------------*/

spool off
