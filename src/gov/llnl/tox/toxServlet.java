package gov.llnl.tox;
//---------------------------------------------------
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
//---------------------------------------------------
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
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		toxBean xb = new toxBean(req.getServerName(),req.getServerPort());
		String[] xml = req.getParameterValues("xml");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		//-------------------------------------------
		out.println(xb.tox(xml[0]));
		//-------------------------------------------
		out.close();
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
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		toxBean xb = new toxBean(req.getServerName(),req.getServerPort());
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		//-------------------------------------------
		out.println(xb.tox(getPostPayload(req)));
		out.close();
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
