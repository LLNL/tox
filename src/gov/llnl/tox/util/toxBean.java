package gov.llnl.tox.util;
//---------------------------------------------------
import java.io.*;
import java.util.*;
//----------------------------------------------------
import javax.xml.parsers.*;
//----------------------------------------------------
import org.xml.sax.*;
//----------------------------------------------------
//-- $Id: toxBean.java 5 2009-10-16 15:20:39Z dacracot $
//----------------------------------------------------
public class toxBean
	{
	//-----------------------------------------------
	private StringBuilder resultBuffer;
	private StringBuilder xmlBuffer;
	private String plsql;
	private String xsl;
	private Vector<String> params;
	private Vector<String> spaces;
	private boolean parameter;
	private href http;
	private xslt trans;
	private ora db;
	private String host;
	private int port;
	//-----------------------------------------------
	public toxBean(String h, int p)
		{
		host = h;
		port = p;
		resultBuffer = new StringBuilder(1024);
		xmlBuffer = new StringBuilder(1024);
		plsql = "";
		xsl = "";
		params = new Vector<String>();
		spaces = new Vector<String>();
		parameter = false;
		try
			{
			trans = new xslt();
			db = new ora();
			}
		catch (Exception e)
			{
			debug.logger("gov.llnl.tox.util.toxBean","error: construct >> ",e);
			}
		}
	//-----------------------------------------------
	public void startModel(String own, String pkg, String func)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","startModel(own, pkg, func)>> "+own+","+pkg+","+func);
		plsql = own+"."+pkg+"."+func;
		}
	//-----------------------------------------------
	public void endModel()
		{
		if (parameter)
			plsql += ")";
		xmlBuffer.append(db.plsql(plsql));
		plsql = "";
		parameter = false;
		}
	//------------------------------------------------
	private String noSingleQuotes (String str)
		{
		String result = str;
		int found = result.indexOf("'");
		while (found != -1)
			{
			result = result.substring(0,found) + "'" + result.substring(found);
			found = result.indexOf("'",found + 2);
			}
		return(result);
		}
	//-----------------------------------------------
	private String formatParameter(String type, String value, String name)
		{
		String result = "";
		boolean quote = false;
		//-------------------------------------------
		try
			{
			if (name != null)
				{
				result = name+"=>";
				}
			if (type.equalsIgnoreCase("VARCHAR") ||
				type.equalsIgnoreCase("VARCHAR2") ||
				type.equalsIgnoreCase("CHAR"))
				{
				result += "'"+noSingleQuotes(value);
				quote = true;
				}
			if (quote)
				result += "'";
			}
		catch (Exception e)
			{
			return(debug.logger("gov.llnl.tox.util.toxBean","error: formatParameter >> ",e));
			}
		//-------------------------------------------
		return(result);
		}
	//-----------------------------------------------
	public void startParameter(String type, String value, String name)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","startParameter(type, value, name)>> "+type+","+value+","+name);
		if(parameter)
			{
			if (type == null)
				plsql += ","+formatParameter("VARCHAR2",value,name);
			else
				plsql += ","+formatParameter(type,value,name);
			}
		else
			{
			if (type == null)
				plsql += "("+formatParameter("VARCHAR2",value,name);
			else
				plsql += "("+formatParameter(type,value,name);
			parameter = true;
			}
		}
	//-----------------------------------------------
	public void endParameter()
		{
		}
	//-----------------------------------------------
	public void startView(String map)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","startView(map)>> "+map);
		xsl = map;
		if (xmlBuffer.length() > 0)
			{
			resultBuffer.append(xmlBuffer.toString());
			xmlBuffer.setLength(0);
			}
		}
	//-----------------------------------------------
	public void endView()
		{
		resultBuffer.append(trans.morph(wrapper(xmlBuffer.toString()),xsl,params));
		xmlBuffer.setLength(0);
		xsl = "";
		}
	//-----------------------------------------------
	public void startParam(String name, String value)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","startParam(name, value)>> "+name+","+value);
		String param = name+":"+value;
		params.add(param);
		}
	//-----------------------------------------------
	public void endParam()
		{
		}
	//-----------------------------------------------
	public void startInclude(String href)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","startInclude(href)>> "+href);
		http = new href();
		xmlBuffer.append(http.get(href));
		}
	//-----------------------------------------------
	public void endInclude()
		{
		}
	//-----------------------------------------------
	public void startMessage()
		{
		}
	//-----------------------------------------------
	public void endMessage()
		{
		if (xmlBuffer.length() > 0)
			{
			String result = resultBuffer.toString()+xmlBuffer.toString();
			resultBuffer.setLength(0);
			resultBuffer.append(wrapper(result));
			}
		}
	//-----------------------------------------------
	private String wrapper(String result)
		{
		StringBuilder resultBuffer = new StringBuilder(1024);
		resultBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE tox SYSTEM \"http://");
		resultBuffer.append(host);
		if (port != 80)
			{
			resultBuffer.append(":");
			resultBuffer.append(port);
			}
		resultBuffer.append("/tox/dtd/tox.dtd\"><tox:result xmlns:tox=\"http://tox.llnl.gov/tox/\">");
		resultBuffer.append(result);
		resultBuffer.append("</tox:result>");
		return(resultBuffer.toString());
		}
	//-----------------------------------------------
	public String tox(String buffer)
		{
		try
			{
			if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","tox(xml)>> "+buffer);
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer.getBytes());
			//---------------------------------------			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser jaxpParser = factory.newSAXParser();
			XMLReader parser = jaxpParser.getXMLReader();
			//---------------------------------------
			toxParser tp = new toxParser(this);
			toxParseError tpe = new toxParseError();
			parser.setContentHandler(tp);
			parser.setErrorHandler(tpe);
			InputSource is = new InputSource(bais);
			db.getConn();
			parser.parse(is);
			db.releaseConn();
			if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxBean","tox(output)>> "+resultBuffer.toString());
			return(resultBuffer.toString());
			}
		catch(Exception e)
			{
			return(debug.logger("gov.llnl.tox.util.toxBean","error: parse >> ",e));
			}
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
