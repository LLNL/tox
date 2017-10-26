package gov.llnl.tox.util;
//---------------------------------------------------
import java.io.*;
import java.text.*;
import javax.servlet.*;
//----------------------------------------------------
public class debug
	{
	//-----------------------------------------------
	private static SimpleDateFormat dateFormat;
	private static ServletContext context;
	private static String debugLevel;
	public static boolean DEBUG;
	//-----------------------------------------------
	public static String stackTrace(Exception e)
		{
		StringWriter out = new StringWriter();
		e.printStackTrace(new PrintWriter(out));
		return out.toString();
		}
	//-----------------------------------------------
	public static void init(ServletConfig c)
		{
		context = c.getServletContext();
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		debugLevel = c.getInitParameter("debugLevel");
		DEBUG = debugLevel.equals("verbose");
		}
	//-----------------------------------------------
	private static String txtFormat(String who, String txt)
		{
		return("[" + who + "]@" + dateFormat.format(new java.util.Date()) + " - " + txt);
		}
	//-----------------------------------------------
	public static String logger(String who, String txt)
		{
		String line = txtFormat(who,txt);
		context.log(line);
		return(line);
		}
	//-----------------------------------------------
	public static String logger(String who, String txt, Exception e)
		{
		String line = txtFormat(who,txt);
		context.log(line,e);
		return(line + "\n" + stackTrace(e));
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
