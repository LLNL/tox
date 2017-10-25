package gov.llnl.restful.api;
//---------------------------------------------------
import java.io.*;
import java.util.*;
//----------------------------------------------------
import org.json.*;
//---------------------------------------------------
import gov.llnl.restful.util.*;
//----------------------------------------------------
public class apiVerbage
	{
	//-----------------------------------------------
	protected verbs verb;
	protected database db;
	//-----------------------------------------------
	public apiVerbage(String verb)
		{
		this.verb = verbs.valueOf(verb);
		try
			{
			db = new database();
			}
		catch (Exception e)
			{
			debug.logger("gov.llnl.restful.util.apiVerbage","error: constructor >> ",e);
			}
		}
	//-----------------------------------------------
	private enum verbs
		{
		DELETE(".del."),
		GET(".get."),
		HEAD(".nada."),
		OPTIONS(".nada."),
		POST(".post."),
		PUT(".put."),
		TRACE(".nada.");
		//-------------------------------------------
		private final String description;
		//-------------------------------------------
		verbs(String d)
			{
			description = d;
			}
		//-------------------------------------------
		public String getValue()
			{
			return(description);
			}
		//-------------------------------------------
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
	public String api(String schema, String call, Map<String, String[]> lockedParams)
		{
		String result = "";
		String outputType = "XML";
		Map<String, String[]> params = new HashMap<>(lockedParams);
		try
			{
			db.getConn();
			switch (verb)
				{
				case HEAD:
					{
					// servlet will handle the response
					break;
					}
				case GET:
				case POST:
				case PUT:
				case DELETE:
					{
					StringBuffer buf = new StringBuffer(schema+verb.getValue()+call);
					// params appear to be in order of the query string
					Set<String> keys = params.keySet();
					// deal with outputType param if it exists
					if (keys.contains("outputType"))
						{
						outputType = params.get("outputType")[0];
						keys.remove("outputType");
						params.remove("outputType");
						}
					// add params to pl/sql call
					int paramCount = keys.size();
					if (paramCount != 0)
						{
						buf.append("('");
						int i = 1;
						for (String key: keys)
							{
							buf.append(params.get(key)[0]);
							if (i < paramCount)
								buf.append("','");
							i++;
							}
						buf.append("')");
						}
					result = db.plsql(buf.toString());
					break;
					}
				case OPTIONS:
				case TRACE:
					{
					result = "How did we get here?";
					break;
					}
				}
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
				result = debug.logger("gov.llnl.restful.util.verbage","no such output type >> "+outputType);
				}
			//---------------------------------------
			}
		catch (Exception e)
			{
			result = debug.logger("gov.llnl.restful.util.verbage","error: api >> ",e);
			}
		return(result);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
