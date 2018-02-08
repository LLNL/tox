package gov.llnl.tox.api;
//---------------------------------------------------
import java.io.*;
import java.util.*;
//----------------------------------------------------
import org.json.*;
//---------------------------------------------------
import gov.llnl.tox.util.*;
//----------------------------------------------------
public class apiVerbage
	{
	//-----------------------------------------------
	protected database db;
	//-----------------------------------------------
	public apiVerbage()
		{
		try
			{
			db = new database();
			}
		catch (Exception e)
			{
			debug.logger("gov.llnl.tox.util.apiVerbage","error: constructor >> ",e);
			}
		}
	//-----------------------------------------------
	private enum outputs
		{
		JSON,
		XML,
		CSV
		}
	//-----------------------------------------------
	public String getOutputMIME()
		{
		return("application/json");
		}
	//-----------------------------------------------
	public String api(String call, Map<String, String[]> lockedParams, String payload)
		{
		String result = "";
		String outputType = "XML";
		String xslUrl = "";
		Map<String, String[]> params = new HashMap<>(lockedParams);
		try
			{
			db.getConn();
			StringBuffer buf = new StringBuffer(call);
			// params appear to be in order of the query string
			Set<String> keys = params.keySet();
			// deal with outputType param if it exists
			if (keys.contains("outputType"))
				{
				outputType = params.get("outputType")[0];
				keys.remove("outputType");
				params.remove("outputType");
				}
			// deal with xform param if it exists
			if (keys.contains("xform"))
				{
				xslUrl = params.get("xform")[0];
				keys.remove("xform");
				params.remove("xform");
				}
			// add params to pl/sql call
			int paramCount = keys.size();
			if (paramCount != 0)
				{
				if (!payload.isEmpty())
					buf.append("(in_payload=>'"+payload+"',");
				else
					buf.append("(");
				int i = 1;
				for (String key: keys)
					{
					buf.append(key);
					buf.append("=>'");
					buf.append(params.get(key)[0]);
					if (i < paramCount)
						buf.append("','");
					i++;
					}
				buf.append("')");
				}
			else
				{
				if (!payload.isEmpty())
					buf.append("(in_payload=>'"+payload+"')");
				}
			result = db.plsql(buf.toString());
			db.releaseConn();
			//---------------------------------------
			outputs output = null;
			try
				{
				output = outputs.valueOf(outputType.toUpperCase());
				switch (output)
					{
					case XML:
						{
						// already in XML format
						if (!xslUrl.equals(""))
							{
							// TO DO: need a way to pass params to XSLT that
							// is not confused with passing params to PL/SQL
							Vector<String> xsltParams = new Vector<String>();
							xslt xform = new xslt();
							result = xform.morph(result,xslUrl,xsltParams);
							}
						break;
						}
					case JSON:
						{
						result = XML.toJSONObject(result).toString();
						break;
						}
					case CSV:
						{
						// only flat structures can be CSV
						// result = CDL.toString(XML.toJSONObject(result));
						break;
						}
					}
				}
			catch (IllegalArgumentException e)
				{
				result = debug.logger("gov.llnl.tox.util.verbage","no such output type >> "+outputType);
				}
			//---------------------------------------
			}
		catch (Exception e)
			{
			result = debug.logger("gov.llnl.tox.util.verbage","error: api >> ",e);
			}
		return(result);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
