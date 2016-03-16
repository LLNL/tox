package gov.llnl.tox.util;
//---------------------------------------------------
import org.xml.sax.*;
//----------------------------------------------------
//-- $Id: toxParser.java 5 2009-10-16 15:20:39Z dacracot $
//----------------------------------------------------
public class toxParser implements ContentHandler
	{
	//-----------------------------------------------
	private toxBean bean;
	//-----------------------------------------------
	public toxParser(toxBean b)
		{
		bean = b;
		}
	//-----------------------------------------------
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","startElement(namespaceURI,localName,qName)>> "+namespaceURI+","+localName+","+qName);
		if(localName.equals("model"))
			bean.startModel(atts.getValue("owner"), atts.getValue("package"), atts.getValue("function"));
		else if(localName.equals("parameter"))
			bean.startParameter(atts.getValue("type"), atts.getValue("value"), atts.getValue("name"));
		else if(localName.equals("view"))
			bean.startView(atts.getValue("map"));
		else if(localName.equals("param"))
			bean.startParam(atts.getValue("name"), atts.getValue("value"));
		else if(localName.equals("include"))
			bean.startInclude(atts.getValue("href"));
		else if(localName.equals("message"))
			bean.startMessage();
		}
	//-----------------------------------------------
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","endElement(namespaceURI,localName,qName)>> "+namespaceURI+","+localName+","+qName);
		if(localName.equals("model"))
			bean.endModel();
		else if(localName.equals("parameter"))
			bean.endParameter();
		else if(localName.equals("view"))
			bean.endView();
		else if(localName.equals("param"))
			bean.endParam();
		else if(localName.equals("include"))
			bean.endInclude();
		else if(localName.equals("message"))
			bean.endMessage();
		}
	//-----------------------------------------------
	public void characters(char[] ch, int start, int length) throws SAXException
		{
		String chars = new String(ch,start,length);
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","characters(ch,start,length)>> ["+chars+"],"+start+","+length);
		}
	//-----------------------------------------------
	public void skippedEntity(String name) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","skippedEntity(name)>> "+name);
		}
	//-----------------------------------------------
	public void processingInstruction(String target, String data) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","processingInstruction(target,data)>> "+target+","+data);
		}
	//-----------------------------------------------
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","ignorableWhitespace(ch,start,length)>> ["+new String(ch,start,length)+"],"+start+","+length);
		}
	//-----------------------------------------------
	public void startPrefixMapping(String prefix, String uri) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","startPrefixMapping(prefix,uri)>> "+prefix+","+uri);
		}
	//-----------------------------------------------
	public void endPrefixMapping(String prefix) throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","endPrefixMapping(prefix)>> "+prefix);
		}
	//-----------------------------------------------
	public void startDocument() throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","startDocument()");
		}
	//-----------------------------------------------
	public void endDocument() throws SAXException
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","endDocument()");
		}
	//-----------------------------------------------
	public void setDocumentLocator(Locator locator)
		{
		if (debug.DEBUG) debug.logger("gov.llnl.tox.util.toxParser","setDocumentLocator(locator)>> "+locator);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
