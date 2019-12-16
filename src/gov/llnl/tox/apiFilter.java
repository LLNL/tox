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
public class apiFilter implements Filter
	{
	//-----------------------------------------------
	@Override
	public void init(FilterConfig config) throws ServletException
		{
		//-------------------------------------------
		}
	//-----------------------------------------------
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException
		{
		//-------------------------------------------
		if (req instanceof HttpServletRequest)
			{
			HttpServletRequest hreq = (HttpServletRequest) req;
			chain.doFilter(req,res);
			}
		//-------------------------------------------
		}
	//-----------------------------------------------
	@Override
	public void destroy()
		{
		//-------------------------------------------
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
