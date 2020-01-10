package gov.llnl.tox.util;
//---------------------------------------------------
import java.util.*;
import java.io.*;
//---------------------------------------------------
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
//----------------------------------------------------
public class xslt
	{
	//-----------------------------------------------
	TransformerFactory tFactory;
	//-----------------------------------------------
	public xslt()
		{
		try
			{
			tFactory = TransformerFactory.newInstance();
			}
		catch (Exception e)
			{
			debug.logger("gov.llnl.tox.util.xslt","error: construct>> ",e);
			}
		}
	//-----------------------------------------------
	public String[] morph(String xml, String xslUrl, Vector<String> params)
		{
		String[] result;
		String output;
		ByteArrayInputStream xmlStream;
		if (debug.VERBOSE) debug.logger("gov.llnl.tox.util.xslt","morph(xslUrl)>> "+xslUrl);
		//-------------------------------------------
		try
			{
			//-----------------------------------
			Transformer transformer = tFactory.newTransformer(new StreamSource(xslUrl));
			//-----------------------------------
			for (int i=0; i<params.size(); i++)
				{
				String param = params.elementAt(i);
				if (debug.VERBOSE) debug.logger("gov.llnl.tox.util.xslt","morph(param)>> "+param);
				StringTokenizer toke = new StringTokenizer(param,"=");
				transformer.setParameter(toke.nextToken(),toke.nextToken());
				}
			//-----------------------------------
			if (xml != null)
				xmlStream = new ByteArrayInputStream(xml.getBytes());
			else
				xmlStream = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(baos);
			//-----------------------------------
			transformer.transform(new StreamSource(xmlStream), new StreamResult(new OutputStreamWriter(dataOut)));
			String mime = transformer.getOutputProperty(OutputKeys.MEDIA_TYPE);
			output = baos.toString();
			result = new String[]{mime, output};
			if (debug.VERBOSE) debug.logger("gov.llnl.tox.util.xslt","morph(output)>> "+output);
			//-----------------------------------
			}
		catch (Exception e)
			{
			output = debug.logger("gov.llnl.tox.util.xslt","error: morph>> ",e);
			result = new String[]{"text/plain", output};
			}
		return(result);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
