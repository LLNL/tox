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
// not always JSON
		}
	//-----------------------------------------------
	public String api(String call, Map<String, String[]> lockedParams, String payload)
		{
		String result = "";
		String inputType = "XML"; // default is XML
		String inputXslUrl = "";
		String outputType = "XML"; // default is XML
		String outputXslUrl = "";
		Map<String, String[]> params = new HashMap<>(lockedParams);
		try
			{
			// params from the query string
			Set<String> keys = params.keySet();
			// deal with inputType param if it exists
			if (keys.contains("inputType"))
				{
				inputType = params.get("inputType")[0];
				keys.remove("inputType");
				params.remove("inputType");
// change to XML if not already
				}
			// deal with inputXform param if it exists
			if (keys.contains("inputXform"))
				{
				inputXslUrl = params.get("inputXform")[0];
				keys.remove("inputXform");
				params.remove("inputXform");
// apply XSLT
				}
			// deal with outputType param if it exists (after DB call)
			if (keys.contains("outputType"))
				{
				outputType = params.get("outputType")[0];
				keys.remove("outputType");
				params.remove("outputType");
				}
			// deal with outputXform param if it exists (after DB call)
			if (keys.contains("outputXform"))
				{
				outputXslUrl = params.get("outputXform")[0];
				keys.remove("outputXform");
				params.remove("outputXform");
				}
			// add params to pl/sql call
			db.getConn();
			StringBuffer buf = new StringBuffer(call);
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
						buf.append("',");
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
				// transform first if asked
				// output depends on the result
				if (!outputXslUrl.equals(""))
					{
					// TO DO: need a way to pass params to XSLT that
					// is not confused with passing params to PL/SQL
					Vector<String> xsltParams = new Vector<String>();
					xslt xform = new xslt();
					result = xform.morph(result,outputXslUrl,xsltParams);
					}
				//-----------------------------------
				output = outputs.valueOf(outputType.toUpperCase());
				switch (output)
					{
					case XML:
						{
						// XML format is the default
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
