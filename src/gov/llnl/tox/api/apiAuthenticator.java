package gov.llnl.tox.api;
//---------------------------------------------------
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.binary.Base64;
//---------------------------------------------------
import gov.llnl.tox.util.*;
//----------------------------------------------------
public class apiAuthenticator extends authenticator
	{
	//-----------------------------------------------
	@Override
	public String getKeyword()
		{
		return("Authorization");
		}
	//-----------------------------------------------
	@Override
	public boolean authenticate(String credentials)
		{
		//-------------------------------------------
		if (null == credentials) return(false);
		//-------------------------------------------
		String encodedUserPass = credentials.replaceFirst("Basic ", "");
		String userPass = null;
		try
			{
			byte[] decodedBytes = Base64.decodeBase64(encodedUserPass);
			userPass = new String(decodedBytes, "UTF-8");
			}
		catch (IOException e)
			{
			debug.logger("gov.llnl.tox.authenticator","error: authenticate >> ",e);
			}
		StringTokenizer tokenizer = new StringTokenizer(userPass, ":");
		String user = tokenizer.nextToken();
		String pass = tokenizer.nextToken();
		//-------------------------------------------
		// we have fixed the username and password as tester
		// call some UserService/LDAP here
//		boolean authenticated = "tester".equals(user) && "testerpass".equals(pass);
		boolean authenticated = true;
		//-------------------------------------------
		return(authenticated);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
