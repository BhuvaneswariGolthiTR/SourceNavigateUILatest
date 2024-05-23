package com.thomsonreuters.codes.codesbench.quality.utilities.xml;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Assertions;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.sql.Connection;
import java.util.List;

public class XmlUtils
{
	public static Document documentCreator(String str)
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc = null;

        if(!str.contains("<!DOCTYPE"))
        {
			//Add test tags
			str = str.replaceFirst("[>]", "><test>");
			str += "</test>";
		}

		try
		{
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(new StringReader(str)));
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}

		return doc;
	}

	public static String extractValueFromDocXml(Document doc, String target)
	{
		return doc.getElementsByTagName(target).item(0).getTextContent();
	}
	
	public static String extractPopNameUuidFromXml(String data)
	{
		return StringUtils.substringBetween(data, "<pop.name.guid>", "</pop.name.guid>");
	}

	public static String extractPopNameFromXml(String data)
	{
		return StringUtils.substringBetween(data, "<pop.name>", "</pop.name>");
	}

	public static String extractUuidFromRelationshipInXml(String data)
	{
		return StringUtils.substringBetween(data, "<relationship.uuid>", "</relationship.uuid>");
	}

	public static String extractPartialMatchFromXml(String data)
	{
		return StringUtils.substringBetween(data, "match>", "</is.partial");
	}

	public static String extractMatchedTextFromCDataInXml(String data)
	{
		return StringUtils.substringBetween(data, "![CDATA[", "]]");
	}

	public static String prependDTDs()
	{
		return  "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
				"<!DOCTYPE test [" +
				"<!ENTITY sect \"&#167;\">" +
				"<!ENTITY ensp \"&#8194;\">" +
				"<!ENTITY emsp \"&#8195;\">" +
				"<!ENTITY nbsp \"&#160;\">" +
				"<!ENTITY mdash \"&#8212;\">" +
				"<!ENTITY ndash \"&#8211;\">" +
				"<!ENTITY ldquo \"&#8220;\">" +
				"<!ENTITY rdquo \"&#8221;\">" +
				"<!ENTITY lrm \"&#8206;\">" +
				"<!ENTITY rlm \"&#8207;\">" +
				"<!ENTITY lsquo \"&#8216;\">" +
				"<!ENTITY rsquo \"&#8217;\">" +
				"<!ENTITY thinsp \"&#8201;\">" +
				"<!ENTITY squ \"&#9633;\">" +
				"<!ENTITY TLRnbensp \"&#9999;\">" +
				"<!ENTITY apos \"&#39;\">" +
				"<!ENTITY lt \"&#60;\">" +
				"<!ENTITY gt \"&#62;\">" +
				"<!ENTITY bull \"&#8226;\">" +
				"<!ENTITY deg \"&#176;\">" +
				"<!ENTITY prime \"&#8242;\">" +
				"<!ENTITY Prime \"&#8243;\">" +
				"<!ENTITY plusmn \"&#177;\">" +
				"<!ENTITY TLRnbthsp \" \">" +
				"<!ENTITY TLRdotl \"&#183;\">" +
				"]>";
	}

	public static void writeXmlToFile(String xml) throws FileNotFoundException
	{
		String fileToWritePath = new File("commonFiles\\TestFiles\\Temp XML Doc For Comparisons.xml").getAbsolutePath();
		xml = prependDTDs() + xml;
		PrintWriter fileWriter = new PrintWriter(fileToWritePath);
		fileWriter.println(xml);
		fileWriter.close();
	}

	public static List<Difference> compare(File file1, File file2)
	{
		String file1Path = file1.getAbsolutePath();
		String file2Path = file2.getAbsolutePath();

		FileInputStream file1InputStream = null;
		FileInputStream file2InputStream = null;
		try
		{
			file1InputStream = new FileInputStream(file1Path);
			file2InputStream = new FileInputStream(file2Path);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		BufferedReader file1BufferedReader = new BufferedReader(new InputStreamReader(file1InputStream));
		BufferedReader file2BufferedReader = new BufferedReader(new InputStreamReader(file2InputStream));

		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);

		Diff xmlDiff = null;
		try
		{
			xmlDiff = new Diff(file1BufferedReader, file2BufferedReader);
		}
		/*
		 * I could use a generic catch Exception, but this will allow better output logging.
		 */
		catch(SAXParseException e)
		{
			//System.out.println(String.format("Comparing Output Test File: %s and Output Gold File: %s", file1.getName(), file2.getName()));
			e.printStackTrace();
		}
		catch(SAXException e)
		{
			//System.out.println(String.format("Comparing Output Test File: %s and Output Gold File: %s", file1.getName(), file2.getName()));
			e.printStackTrace();
		}
		catch(IOException e)
		{
			//System.out.println(String.format("Comparing Output Test File: %s and Output Gold File: %s", file1.getName(), file2.getName()));
			e.printStackTrace();
		}

		xmlDiff.overrideDifferenceListener(getDifferenceListenerOverrider());

		DetailedDiff detailedDiff = new DetailedDiff(xmlDiff);

		return detailedDiff.getAllDifferences();
	}

	public static DifferenceListenerOverriderUtility getDifferenceListenerOverrider()
	{
		return new DifferenceListenerOverriderUtility();
	}

	public static String getNodeParaUUID(String contentUUID, Connection connection)
	{
		String xmlDoc = XmlUtils.prependDTDs();
		String docBody = "<test>" + HierarchyDatabaseUtils.getXmlTextOfNodeWithContentUuid(contentUUID, connection) + "</test>";
		xmlDoc += docBody;
		Document doc = XmlUtils.documentCreator(xmlDoc);
		return evaluateDocWithXpath(doc, XmlUtilsConstants.paraUUIDXpath);
	}

	/**
	 * This method takes in a prebuilt xml document, and an xpath and returns a Node List containing all the specific nodes that are identified by the xpath
	 * @param doc
	 * @param xpathTarget
	 * @return
	 * @throws XPathExpressionException
	 */
	public static String evaluateDocWithXpath(Document doc, String xpathTarget)
	{
		NodeList nodes = null;
		//Creating xpath to target first para uuid
		XPathFactory xpathfactory = XPathFactory.newInstance();
		XPath xpath = xpathfactory.newXPath();
		try
		{
			XPathExpression expr = xpath.compile(xpathTarget);
			//Evaluating document using xpath
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			nodes = (NodeList) result;
		}
		catch(XPathExpressionException e)
		{
			e.printStackTrace();
			Assertions.fail("Could not evaluate xpath expression from target");
		}
		return nodes.item(0).getAttributes().getNamedItem("TITLE").getTextContent();
	}

}
