// $Id: test.js 10 2010-02-03 19:21:31Z dacracot $
//======================================================================
// 
// utility to help with encoding
// 
//----------------------------------------------------------------------
function substitute(str,from,to)
	{
	for (j=0; j < str.length; j++)
		{
		if (str.charAt(j) == from)
			{
			str = str.substring(0,j)+to+str.substring((j+1),str.length);
			j+=to.length;
			}
		}
	return(str);
	}
//======================================================================
// 
// insure that XML's special characters are encoded
// 
//----------------------------------------------------------------------
function encode(str)
	{
	str = substitute(str,'&','&amp;');
	str = substitute(str,'"','&quot;');
	str = substitute(str,'<','&lt;');
	str = substitute(str,'>','&gt;');
	return(str);
	}
//======================================================================
var req;
//======================================================================
// 
// given the proper return codes, the result is inserted into the DOM
// if "error" is embedded in the response, then the error is displayed
// 
//----------------------------------------------------------------------
function insertHtml()
	{
	if (req.readyState == 4) // 4 == "loaded"
		{
		if (req.status == 200) // 200 == "Ok"
			{
			if (req.responseText.indexOf("error") >= 0)
				{
				alert("Please report the following error...");
				pretty = req.responseText.substring(req.responseText.indexOf("error"),1200);
				pretty = pretty.substring(0,pretty.indexOf("\""));
				alert(pretty + "\n\n" + req.responseText.substring(0,1200));
				}
			else
				{
				div = document.getElementById("insertHere");
				div.innerHTML = req.responseText;
				}
			}
		else
			{
			alert("Could not retreive URL:\n" + req.statusText);
			}
		}
	}
//======================================================================
// 
// basic AJAX contruct for both IE and others
// 
//----------------------------------------------------------------------
function ajax(method,url,payload,action)
	{
//	alert(method);
//	alert(url);
//	alert(payload);
	if (window.XMLHttpRequest)
		{
		req = new XMLHttpRequest();
		req.onreadystatechange = action;
		req.open(method, url, true);
		req.send(payload);
		}
	else if (window.ActiveXObject)
		{
		req = new ActiveXObject("Microsoft.XMLHTTP");
		if (req)
			{
			req.onreadystatechange = action;
			req.open(method, url, true);
			req.send(payload);
			}
		else
			{
			alert("Could not create ActiveXObject(Microsoft.XMLHTTP)");
			}
		}
	}
//======================================================================
// 
// construct the tox XML to be transported via the AJAX pattern
// 
//----------------------------------------------------------------------
function showExamplePingFormatted()
	{
	div = document.getElementById("insertHere");
	div.innerHTML = "";
	var toxXML = '';
	toxXML += '<?xml version="1.0" encoding="UTF-8"?>';
	toxXML += '<!DOCTYPE tox:message SYSTEM "http://@TEST_HOST@:@TEST_PORT@/tox/dtd/tox.dtd">';
	toxXML += '<tox:message xmlns:tox="http://tox.chemTrack.llnl.gov/tox/">';
	toxXML += '<tox:view map="http://@TEST_HOST@:@TEST_PORT@/test/test.xsl">';
	toxXML += '<tox:param name="title" value="AJAX test"/>';
	toxXML += '<tox:param name="host" value="'+encode(document.getElementById("host").value)+'"/>';
	toxXML += '<tox:include href="http://@TEST_HOST@:@TEST_PORT@/test/test.xml"/>';
	toxXML += '<tox:model owner="example" package="ping" function="formatted">';
	toxXML += '<tox:parameter value="'+encode(document.getElementById("mask").value)+'"/>';
	toxXML += '</tox:model>';
	toxXML += '</tox:view>';
	toxXML += '</tox:message>';
	ajax('POST','/tox/do',toxXML,insertHtml);
	}
//======================================================================
