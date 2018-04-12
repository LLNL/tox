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
	private enum formats
		{
		JSON,
		XML
		}
	//-----------------------------------------------
	public String getOutputMIME(Map<String, String[]> urlParams)
		{
		Map<String, String[]> params = new HashMap<>(urlParams);
		Set<String> keys = params.keySet();
		if (keys.contains("outputFormat"))
			{
			String outputFormat = params.get("outputFormat")[0];
			formats format = formats.valueOf(outputFormat.toUpperCase());
			switch (format)
				{
				case XML:
					{
					return("text/xml"); 
					}
				case JSON:
					{
					return("application/json"); 
					}
				default:
					{
					return("text/plain");
					}
				}
			}
		else
			return("text/xml"); // default is XML
		}
	//-----------------------------------------------
	public String api(String call, Map<String, String[]> urlParams, String payload)
		{
		String result = "";
		String inputFormat = "XML"; // default is XML
		String inputXslUrl = "";
		String outputFormat = "XML"; // default is XML
		String outputXslUrl = "";
		Map<String, String[]> params = new HashMap<>(urlParams);
		try
			{
			// params from the query string
			Set<String> keys = params.keySet();
			// deal with inputFormat param if it exists (before DB call)
			if (keys.contains("inputFormat"))
				{
				inputFormat = params.get("inputFormat")[0];
				keys.remove("inputFormat");
				params.remove("inputFormat");
				formats format = formats.valueOf(inputFormat.toUpperCase());
				switch (format)
					{
					case XML:
						{
						// XML format is the default
						break;
						}
					case JSON:
						{
						payload = XML.toString(new JSONObject(payload));
						break;
						}
					default:
						{
						// throw IllegalArgumentException?
						break;
						}
					}
				}
			// deal with inputXform param if it exists (before DB call)
			if (keys.contains("inputXform"))
				{
				inputXslUrl = params.get("inputXform")[0];
				keys.remove("inputXform");
				params.remove("inputXform");
				Vector<String> xsltParams = new Vector<String>();
				xslt xform = new xslt();
				payload = xform.morph(payload,inputXslUrl,xsltParams);
				}
			// deal with outputFormat param if it exists (after DB call)
			if (keys.contains("outputFormat"))
				{
				outputFormat = params.get("outputFormat")[0];
				keys.remove("outputFormat");
				params.remove("outputFormat");
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
			formats format = null;
			try
				{
				// transform first if asked
				// format depends on the result
				if (!outputXslUrl.equals(""))
					{
					// TO DO: need a way to pass params to XSLT that
					// is not confused with passing params to PL/SQL
					Vector<String> xsltParams = new Vector<String>();
					xslt xform = new xslt();
					result = xform.morph(result,outputXslUrl,xsltParams);
					}
				//-----------------------------------
				format = formats.valueOf(outputFormat.toUpperCase());
				switch (format)
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
					default:
						{
						// throw exception?
						break;
						}
					}
				}
			catch (IllegalArgumentException e)
				{
				result = debug.logger("gov.llnl.tox.util.verbage","no such output format >> "+outputFormat);
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
