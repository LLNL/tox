tox - (Tomcat, Oracle & XML)
============================

What is it?
-----------

The tox (Tomcat Oracle & XML) web archive is a foundation for development of
HTTP based applications using Tomcat (or some other servlet container) and an
Oracle RDBMS. Use of tox requires coding primarily in PL/SQL, JavaScript, and
XSLT, but also in HTML, CSS, and potentially Java. Coded in Java and PL/SQL
itself, tox provides the foundation for more complex applications to be built.

The tox framework enables the construction of applications using the
model/view/controller (MVC) design pattern. With a controller that executes
interpreted XML for creating the model and view, developers can construct new
functionality. The model is retrieved either via includes or by the execution of
Oracle's stored procedures and then passed to an XML Stylesheet transform (XSLT)
to construct and return the view. Different combinations and options provide
rich dynamic content.

Version
-------

This is version 1.6.1 of tox.

Change History
--------------
  
Version 1.6.1:

1. Using CLOB rather than VARCHAR2 in spool table.
1. Lowered logging threshold to console (catalina.out).
1. Stripped XML declaration from includes.

Version 1.6:

1. Localized the logging so that you no longer edit the logging.properties which belongs to Tomcat.

Version 1.5:

1. Removed root and space features that no one ever used and cluttered the code.

Version 1.2:

1. Added tomcat logging in $TOMCAT_HOME/logs.

Version 1.1:

1. Corrected the bug where model parameters could not contain a single quote.
1. Improved performance by using StringBuilder instead of StringBuffer.
1. Improved performance by testing for verbose debugging outside of the debug class rather than inside the debug class method.
1. Utilized Java 1.5 URLConnection.setReadTimeout in the href class, thus eliminating the need for a sub-class thread to perform the timeout function.

Version 1:

1. Initial release. Stable and production worthy.

Requirements
------------

The tox web archive (war) is designed to run within a tomcat installation
against an Oracle relational database. Tomcat is available from the Apache
Software Foundation at http://tomcat.apache.org/. The tox war has been tested
with version 5.5.x of tomcat. Oracle's relational database is a commercial
database, available from Oracle Corp. at http://www.oracle.com.  There are some
dependencies upon Java 1.5.

Installation
------------

Recompile the wars with the "go" shell script after you edit for your development environment's configuration. (Note 1)

OR

Recompile the wars with ant using the build.xml.

AND

Edit your tomcat configuration's `server.xml` to provide the "jdbc/tox" resource.

AND

Copy the Oracle JDBC jar, ojdbc14.jar, to your tomcat's "common/lib". (Note 2)

THEN

Run the run the sql scripts in this order... (Note 3)

```
tox.usr.sql
tox.ddl.sql
tox.pkg.sql
tox.bdy.sql
tox.prv.sql

# and to include the testing functionality...
ping.usr.sql
ping.pkg.sql
ping.bdy.sql
ping.prv.sql
```

THEN

Open a browser and address http://yourTomcatInstance/test/test.jsp and work
through the examples using test.jsp, test.js, test.xml, and test.xsl.

Licensing
---------

This software and all of its components are published under the General Public
License (GPL) version 2 as open source.  Originally developed at the Lawrence
Livermore National Laboratory, it was initially released on July 2nd, 2008,
reference LLNL-CODE-404550.

Notes
-----

1. I use bash on MacOSX, so your script will almost certainly vary if you use a different OS or shell.
1. I have, on a few installations, had to revert back to the old Oracle JDBC driver contained in their classes12.jar.
1. The tablespace I specified will need to be changed to match your configuration.
