package gov.llnl.tox.util;
//---------------------------------------------------
import java.net.*;
//---------------------------------------------------
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
//---------------------------------------------------
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
//----------------------------------------------------
//-- $Id: tag.java 5 2009-10-16 15:20:39Z dacracot $
//----------------------------------------------------
public class tag extends BodyTagSupport
	{
	//-----------------------------------------------
	private static final long serialVersionUID = 1961071705050000001L;
	private int timeOut = -1;
	//-----------------------------------------------
	public void setTimeOut (int t)
		{
		timeOut = t;
		}
	//-----------------------------------------------
	private String doPost(String host, int port, String xml)
		{
		String result  = "";
		URL targetURL = null;
		int httpCode = 0;
		//-------------------------------------------
		try
			{
			//---------------------------------------
			targetURL = new URL("http", host, port, "/tox/do");
			PostMethod post = new PostMethod(targetURL.toString());
			NameValuePair[] payload = {new NameValuePair("xml",xml)};
			post.setRequestBody(payload);
			HttpClient hc = new HttpClient();
			httpCode = hc.executeMethod(post);
			result = post.getResponseBodyAsString();
			post.releaseConnection();
			}
		catch (Exception e)
			{
			result = "error: doPost>> " + httpCode + "\n>> " + targetURL.toString() + "\n>> " + xml + "\n>> " + result + "\n>> " + debug.stackTrace(e);
			}
		//-------------------------------------------
		return (result);
		}
	//-----------------------------------------------
	public int doAfterBody() throws JspTagException
		{
		try
			{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			BodyContent bc = getBodyContent();
			JspWriter outToJsp = bc.getEnclosingWriter();
			String tagContent = bc.getString();
			if (timeOut == -1)
				throw new JspTagException("gov.llnl.tox.util.tag: timeOut not set ");
			//---------------------------------------
			URL toxDTD = new URL("http", request.getServerName(), request.getServerPort(), "/tox/dtd/tox.dtd");
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE tox:message SYSTEM \""+toxDTD.toString()+"\">";
			outToJsp.print(doPost(request.getServerName(),request.getServerPort(),xml+tagContent));
			}
		catch(Exception e)
			{
			throw new JspTagException("gov.llnl.tox.util.tag: " + debug.stackTrace(e));
			}
		return SKIP_BODY;
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
