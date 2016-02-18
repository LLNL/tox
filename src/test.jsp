<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="/WEB-INF/tox.tld" prefix="tox" %>
<!-- $Id: test.jsp 5 2009-10-16 15:20:39Z dacracot $ -->
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:svg="http://www.w3.org/2000/svg">
<head>
	<title>tox tester</title> 
	<script language="JavaScript1.2" src="test.js" type="text/javascript">
		<!-- IE bug requires an explicit close tag else you will only get a blank page -->
	</script>
	<link rel="Stylesheet" href="test.css" type="text/css"/>
</head>
<body>
<center>
	<table border="1" width="800" cellpadding="10">
		<tr>
			<td align="left" width="520" valign="top" class="OuterTable">
				<!-- ======================================= -->
				<!--                                         -->
				<!-- tox can be embedded via a custom tag   -->
				<!-- as defined by the following and the     -->
				<!-- above tablib reference                  -->
				<!--                                         -->
				<!-- ======================================= -->
				an embedded tox tag
				<hr/>
				<div id="toxTable">
<tox:toxTalk timeOut="15">
	<tox:message xmlns:tox="http://@TEST_HOST@:@TEST_PORT@/tox/">
		<tox:view map="http://@TEST_HOST@:@TEST_PORT@/test/test.xsl">
			<tox:param name="title" value="embedded test"/>
			<tox:include href="http://@TEST_HOST@:@TEST_PORT@/test/test.xml"/>
			<tox:model owner="example" package="ping" function="alive"/>
		</tox:view>
	</tox:message>
</tox:toxTalk>
				</div>
			</td>
		</tr>
		<tr>
			<td align="left" width="520" valign="top" class="OuterTable">
				<!-- ======================================= -->
				<!--                                         -->
				<!-- tox can envoked via JavaScript using an -->
				<!-- AJAX pattern as coded in the test.js    -->
				<!-- referenced above                        -->
				<!--                                         -->
				<!-- JSP allows access to values from HTTP   -->
				<!-- to be embedded a later utilized in tox  -->
				<!--                                         -->
				<!-- ======================================= -->
				an AJAX tox call
				<hr/>
				<input id="mask" type="text" value="DD-Mon HH:MI:SS am"/>
				<input id="host" type="hidden" value="<%=request.getRemoteHost()%>"/>
				<br/>
				<br/>
				<input type="button" value="AJAX" onclick="showExamplePingFormatted()"/>
				<hr/>
				<div id="insertHere">insert here</div>
			</td>
		</tr>
		<tr align="center">
			<td align="left" width="520" valign="top" class="OuterTable">
				<!-- ======================================= -->
				<!--                                         -->
				<!-- tox will accept a HTTP GET with XML in  -->
				<!-- the query string as constructed by the  -->
				<!-- following form                          -->
				<!--                                         -->
				<!-- ======================================= -->
				paste your xml and GET
				<hr/>
				<form method="GET" action="/tox/do">
					<textarea name="xml" rows="10" cols="96"></textarea> 
					<br/>
					<br/>
					<input type="submit" value="GET"/> 
				</form>
			</td>
		</tr>
		<tr>
			<td align="left" width="520" valign="top" class="OuterTable">
				<!-- ======================================= -->
				<!--                                         -->
				<!-- tox will accept a HTTP POST with XML in -->
				<!-- the payload as constructed by the       -->
				<!-- following form                          -->
				<!--                                         -->
				<!-- ======================================= -->
				paste your xml and POST
				<hr/>
				<form method="POST" action="/tox/do">
					<textarea name="xml" rows="10" cols="96"></textarea> 
					<br/>
					<br/>
					<input type="submit" value="POST"/> 
				</form>
			</td>
		</tr>
		<tr>
			<td align="left" width="520" valign="top" class="OuterTable">
<pre>
&lt;?xml version="1.0" encoding="UTF-8"?>
&lt;!DOCTYPE tox:message SYSTEM "http://@TEST_HOST@:@TEST_PORT@/tox/dtd/tox.dtd">
&lt;tox:message xmlns:tox="http://@TEST_HOST@:@TEST_PORT@/tox/">
	&lt;tox:view map="http://@TEST_HOST@:@TEST_PORT@/example/map/yourTranform.xsl">
		&lt;tox:param name="xslParameter" value="cothren2"/>
		&lt;tox:include href="http://@TEST_HOST@:@TEST_PORT@/example/static/yourData.xml"/>
		&lt;tox:model owner="example" package="packageSomething" function="doSomething">
			&lt;tox:parameter type="VARCHAR2" value="usefulData" name="yourParameterName"/>
		&lt;/tox:model>
	&lt;/tox:view>
&lt;/tox:message>
</pre>
			</td>
		</tr>
	</table>
</center>
</body>
</html>
