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
	// STEP 7
	// Make the call to authenticator implmentation you
	// created.
	//-----------------------------------------------
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException
		{
		//-------------------------------------------
		if (req instanceof HttpServletRequest)
			{
			apiAuthenticator auth = new apiAuthenticator();
			HttpServletRequest hreq = (HttpServletRequest) req;
			String credential = hreq.getHeader(auth.getKeyword());
			//---------------------------------------
			if (auth.authenticate(credential))
				{
				chain.doFilter(req,res);
				}
			else
				{
				if (res instanceof HttpServletResponse)
					{
					HttpServletResponse hres = (HttpServletResponse)res;
					hres.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
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
