package gov.llnl.tox.util;
//---------------------------------------------------
import org.xml.sax.*;
//----------------------------------------------------
//-- $Id: toxParseError.java 5 2009-10-16 15:20:39Z dacracot $
//----------------------------------------------------
public class toxParseError implements ErrorHandler
	{
	//-----------------------------------------------
	public void error(SAXParseException e)
		{
		debug.logger("gov.llnl.tox.util.toxParseError","error(SAXParseException)>> ",e);
		}
	//-----------------------------------------------
	public void fatalError(SAXParseException e)
		{
		debug.logger("gov.llnl.tox.util.toxParseError","fatalError(SAXParseException)>> ",e);
		}
	//-----------------------------------------------
	public void warning(SAXParseException e)
		{
		debug.logger("gov.llnl.tox.util.toxParseError","warning(SAXParseException)>> ",e);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
