package gov.llnl.tox;
//---------------------------------------------------
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
//---------------------------------------------------
import gov.llnl.tox.api.*;
import gov.llnl.tox.util.*;
//----------------------------------------------------
public class toxServlet extends HttpServlet
	{
	//-----------------------------------------------
	private static final long serialVersionUID = 1961071705050000001L;
	//-----------------------------------------------
	public void init(ServletConfig config) throws ServletException
		{
		//-------------------------------------------
		try
			{
			debug.init(config);
			debug.logger("gov.llnl.tox.toxServlet","initialized tox version: "+toxServlet.class.getPackage().getImplementationVersion()+" with "+config.getInitParameter("debugLevel")+" logging");
			}
		catch (Exception e)
			{
			throw(new ServletException(e));
			}
		}
	//-----------------------------------------------
	private void doVerb(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		PrintWriter out = res.getWriter();
		//-------------------------------------------
		String execute = req.getPathInfo().substring(1);
		apiVerbage v = new apiVerbage();
		String p = getPostPayload(req);
		res.setContentType(v.getOutputMIME(req.getParameterMap()));
		String result = v.api(execute,req.getParameterMap(),p);
		if (result.matches("\\[gov\\.llnl\\.tox.*\\]@[0-9.]* - error:[\\s\\S.]*"))
			res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
			res.setStatus(HttpServletResponse.SC_OK);
		out.println(result);
		//-------------------------------------------
		debug.logger("gov.llnl.tox.toxServlet","doVerb >> ",req,p);
		out.close();
		}
	//-----------------------------------------------
	private void notImplemented(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		}
	//-----------------------------------------------
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	private String getPostPayload(HttpServletRequest req)
		{
		StringBuilder postBuffer = new StringBuilder(512);
		String result = "";
		try
			{
			BufferedReader postReader = req.getReader();
			String line = null;
			//---------------------------------------
			do
				{
				line = postReader.readLine();
				if (line != null)
					postBuffer.append(line);
				}
			while(line != null);
			//---------------------------------------
			String post = postBuffer.toString();
			if (post.startsWith("xml=%"))
				{
				result = URLDecoder.decode(postBuffer.toString().substring(4),"UTF-8");
				}
			else
				{
				result = post;
				}
			//---------------------------------------
			}
		catch(Exception e)
			{
			result = debug.logger("gov.llnl.tox.toxServlet","error: getPostPayload>> ",e);
			return(result);
			}
		return(result);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
