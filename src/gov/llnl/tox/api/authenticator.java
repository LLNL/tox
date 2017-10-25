package gov.llnl.restful.api;
//---------------------------------------------------
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.binary.Base64;
//---------------------------------------------------
import gov.llnl.restful.util.*;
//----------------------------------------------------
public abstract class authenticator
	{
	//-----------------------------------------------
	public abstract String getKeyword();
	//-----------------------------------------------
	public abstract boolean authenticate(String credentials);
	//-----------------------------------------------
	}
//---------------------------------------------------
