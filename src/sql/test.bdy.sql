set echo on
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
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
			SELECT
				TO_CHAR(SYSDATE,'DD-Mon-YYYY HH:MI am')
			INTO
				v_prettyDate
			FROM
				DUAL;
		/*-----------------------*/
			tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.into_spool(v_prettyDate);
			tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.end_spool;
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
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
			SELECT
				TO_CHAR(SYSDATE,in_mask)
			INTO
				v_prettyDate
			FROM
				DUAL;
		/*-----------------------*/
			tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.into_spool(v_prettyDate);
			tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.end_spool;
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
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
			SELECT
				TO_CHAR(TO_DATE(in_date,in_maskIn),in_maskOut)
			INTO
				v_prettyDate
			FROM
				DUAL;
		/*-----------------------*/
			tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.into_spool(v_prettyDate);
			tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.end_spool;
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
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
		--	<root><date yyyymmdd="19690720"/></root>
			SELECT 
				ExtractValue(Value(temp),'/root/date/@yyyymmdd')
			INTO
				v_echoDate
			FROM
				TABLE(XMLSequence(XMLType(in_payload))) temp;
		/*-----------------------*/
			tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="ok">');
			tox.into_spool(v_echoDate);
			tox.into_spool('</example>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool('<example timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.end_spool;
		/*=======================*/
		END echo;
	/*========================================================================*/
	FUNCTION testXml
		(
		in_root IN VARCHAR2
		)
		RETURN SYS_REFCURSOR
		AS
		/*-----------------------*/
		v_timestamp VARCHAR(16);
		v_error VARCHAR2(1024);
		/*-----------------------*/
		CURSOR c_testing IS
			SELECT
				*
			FROM
				testing;
		/*-----------------------*/
		v_testing c_testing%ROWTYPE;
		/*=======================*/
		BEGIN
		/*=======================*/
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
			tox.into_spool('<'||in_root||' timestamp="'||v_timestamp||'" feedback="ok">');
			OPEN c_testing;
			LOOP
				FETCH c_testing INTO v_testing;
				EXIT WHEN (c_testing%NOTFOUND);
 				tox.into_spool('<testing key="'||v_testing.key||'" txt="'||v_testing.txt||'" seq="'||v_testing.seq||'" />');
			END LOOP;
			CLOSE c_testing;
			tox.into_spool('</'||in_root||'>');
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool('<'||in_root||' timestamp="'||v_timestamp||'" feedback="error: '||v_error||'"/>');
				COMMIT;
				RETURN tox.end_spool;
		/*=======================*/
		END testXml;
	/*========================================================================*/
	FUNCTION testText
		RETURN SYS_REFCURSOR
		AS
		/*-----------------------*/
		v_timestamp VARCHAR(16);
		v_error VARCHAR2(1024);
		/*-----------------------*/
		CURSOR c_testing IS
			SELECT
				*
			FROM
				testing;
		/*-----------------------*/
		v_testing c_testing%ROWTYPE;
		/*=======================*/
		BEGIN
		/*=======================*/
			tox.begin_spool;
			v_timestamp:= tox.timestamp;
		/*-----------------------*/
			tox.into_spool(' timestamp >> '||v_timestamp);
			tox.into_spool(' feedback >> ok');
			OPEN c_testing;
			LOOP
				FETCH c_testing INTO v_testing;
				EXIT WHEN (c_testing%NOTFOUND);
 				tox.into_spool('     key >> '||v_testing.key||', txt >> '||v_testing.txt||', seq >> '||v_testing.seq);
			END LOOP;
			CLOSE c_testing;
		/*-----------------------*/
			COMMIT;
			RETURN tox.end_spool;
			EXCEPTION WHEN OTHERS THEN
				tox.reset_spool;
				v_error:= tox.encode(Sqlerrm);
				tox.into_spool(' timestamp >> '||v_timestamp);
				tox.into_spool(' feedback >> error: '||v_error);
				COMMIT;
				RETURN tox.end_spool;
		/*=======================*/
		END testText;
	/*========================================================================*/
	END test;
	/*========================================================================*/

/

SHOW ERRORS PACKAGE BODY test;

/*------------------------------------------------------------------------*/

