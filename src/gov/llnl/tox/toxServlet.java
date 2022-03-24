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
	private String version = toxServlet.class.getPackage().getImplementationVersion();;
	//-----------------------------------------------
	public void init(ServletConfig config) throws ServletException
		{
		//-------------------------------------------
		try
			{
			debug.init(config);
			debug.logger("gov.llnl.tox.toxServlet","initialized tox version: "+version+" with "+config.getInitParameter("debugLevel")+" logging");
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
		String result = null;
		String p = null;
		String execute = "";
		//-------------------------------------------
		String path = req.getPathInfo();
		if (path != null) execute = path.substring(1);
		if (execute.isEmpty())
			{
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			result = "<tox version=\""+version+"\"/>";
			}
		else
			{
			apiVerbage v = new apiVerbage();
			p = getPostPayload(req);
			result = v.api(execute,req.getParameterMap(),p);
			// set MIME after result in case XSLT sets it
			res.setContentType(v.getOutputMIME(req.getParameterMap()));
			if (result.matches("\\[gov\\.llnl\\.tox.*\\]@[0-9.]* - error:[\\s\\S.]*"))
				res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			else
				res.setStatus(HttpServletResponse.SC_OK);
			}
		out.println(result);
		//-------------------------------------------
		if (debug.DEBUG) debug.logger("gov.llnl.tox.toxServlet","doVerb >> ",req,p);
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
					postBuffer.append(line).append("\n");
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
