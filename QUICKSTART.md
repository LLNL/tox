Quick Start Guide
=================

Steps
-----

1. Create the tox database user via tox.usr.sql.
1. Connect as tox and create its schema via tox.ddl.sql, then create its PL/SQL package via tox.pkg.sql, tox.bdy.sql, and tox.prv.sql.
1. Create the example database user via example.usr.sql.
1. Connect as example and create its PL/SQL package via test.pkg.sql and test.bdy.sql, then expose them to tox via example.prv.sql.
	* None of the example schema is exposed to tox and the servlet, only the package calls you grant.
1. Change the JDBC parameters to reflect your environment in context.xml.
1. Alter the allowed IP patterns for your web clients in context.xml.
1. Alter the logging level in web.xml to debug or verbose.
1. Execute the go script after you configure where your Tomcat instance is located.
	* I put my Tomcat in the same directory where I checked out tox.
1. Confirm tox is working by executing the unitTest script.
	* Test 14 fails intentionally.
	* Test 15 returns the current version.
	* Logs are in {tomcat}/logs/tox-YYYY-MM-DD.log.