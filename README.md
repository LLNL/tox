tox - (Tomcat, Oracle & XML)
============================

What is it?
-----------

The tox (Tomcat Oracle & XML) web archive is a foundation for development of
HTTP based applications using Tomcat (or some other servlet container) and an
Oracle RDBMS. Use of tox requires coding primarily in PL/SQL, JavaScript, and
XSLT, but also in HTML, CSS, and potentially Java. Coded in Java and PL/SQL
itself, tox provides the foundation for more complex applications to be built.


Version
-------

This is version 2.9 of tox.

Change History
--------------
  
Version 2.9:

1. Removed unused and mostly commented out code which checked for username/password credentials.
  
Version 2.8.1:

1. Invoking tox with no PL/SQL reference resulted in an exception.  It will now report the tox version.
  
Version 2.8:

1. The XSLT transform now will pass a defined output media-type (MIME) to the HTTP header.
  
Version 2.6.1:

1. Returning proper HTTP status code for PL/SQL failures.
  
Version 2.6:

1. Parameterized both input and output Xform.
  
Version 2.5:

1. Renamed outputType to outputFormat.
1. Renamed xform to outputXform.
1. Added inputFormat.
1. Added inputXform.
  
Version 2.1:

1. Removed HTTP verb from PL/SQL call verbage.

Version 2.0:

1. Modernized for Java 8.
1. Simplified jar dependencies.
1. Self contained configuration.
1. Output JSON without XSLT tranform.
  
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
Software Foundation at http://tomcat.apache.org/.  Oracle's relational database
is a commercial database, available from Oracle Corp. at http://www.oracle.com.


Licensing
---------

This software and all of its components are published under the General Public
License (GPL) version 2 as open source.  Originally developed at the Lawrence
Livermore National Laboratory, it was initially released on July 2nd, 2008,
reference LLNL-CODE-404550.

