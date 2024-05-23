package com.thomsonreuters.codes.codesbench.quality.tests.popnames;

import java.net.HttpURLConnection;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.PopNamesRestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.RestAnnotations.REST;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.RestUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.xml.XmlUtils;

/**
 *         This class tests the Authority Populate Service. An xml string is
 *         sent to the rest service and a nims record string is returned. we're
 *         looking that the service found the correct popname uuid and popname
 *         for the relationship that was sent.
 */
public class AuthorityPopulateRestServiceTests extends TestSetupEdge
{
	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY - Makes a REST POST call and parses the response to check a guid and name &lt;br&gt;
	 * USER -  N/A &lt;br&gt;
	 */
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@CsvSource
	(
		value =
		{
			"<pop.name.relationships><pop.name.guid>I87ED8540CFC911DEA64BC13F4D131DCF</pop.name.guid><public.law><guid>IBCBBB6FD7E274A02A5F3CB13E6E03F60</guid><bermuda.publication.id>1077005</bermuda.publication.id><display.name>Pub.L. 102–338</display.name></public.law><relationships></relationships></pop.name.relationships>" + "MARKYMARK" +
					"I87ED8540CFC911DEA64BC13F4D131DCF" + "MARKYMARK" +
					"Zuni River Watershed Act of 1992",
			"<pop.name.relationships><pop.name.guid>ICC9A0120CFC711DE89F0CC6BC455EA95</pop.name.guid><public.law><guid>IAFB81376AE6B4DB582DB1724BE1313BF</guid><bermuda.publication.id>1077005</bermuda.publication.id><display.name>Pub.L. 102?325, Title X, ??1004</display.name></public.law><relationships><relationship><public.law.section>1081</public.law.section><statute.sections><statute.section><find.orig>20USCAS1135F</find.orig><bermuda.publication.id>1000546</bermuda.publication.id><display.name>20 USCA ??1135f</display.name></statute.section></statute.sections></relationship></relationships></pop.name.relationships>" + "MARKYMARK" +
					"ICC9A0120CFC711DE89F0CC6BC455EA95" + "MARKYMARK" +
					"Dwight D. Eisenhower Leadership Development Act of 1992",
			"<pop.name.relationships><pop.name.guid>IB20C6860CFC811DE89F0CC6BC455EA95</pop.name.guid><public.law><guid>I26462E04300C4E3380CD4DA5C3B9C342</guid><bermuda.publication.id>1077005</bermuda.publication.id><display.name>Pub.L. 100?107</display.name></public.law><relationships><relationship><public.law.section>3(a)</public.law.section><statute.sections><statute.section><find.orig>15USCAS3711A</find.orig><bermuda.publication.id>1000546</bermuda.publication.id><display.name>15 USCA ??3711a</display.name></statute.section></statute.sections></relationship></relationships></pop.name.relationships>" + "MARKYMARK" +
					"IB20C6860CFC811DE89F0CC6BC455EA95" + "MARKYMARK" +
					"Malcolm Baldrige National Quality Improvement Act of 1987"
		},
		delimiterString = "MARKYMARK"
	)
	@REST
	@LOG
	public void checkIfPopNameReturnedMatchesGivenXMLTest(String popNameXML, String expectedGuid, String expectedTitle)
	{
		HttpURLConnection restConnection = RestUtils.connectToRestConnection(PopNamesRestConstants.UAT_REST_CONNECTION_URL, RestUtils.POST, RestUtils.CONTENT_TYPE, RestUtils.APPLICATION_XML);
		String nimsRecordResponse = RestUtils.writeToRestServiceAndGetStringResponseFromInputStream(restConnection, popNameXML);
		boolean responseResult = RestUtils.isResponseCodeOk(restConnection);
		Assertions.assertTrue(responseResult, "The rest connection worked.");

		RestUtils.disconnectFromRestConnection(restConnection);

		Document doc = XmlUtils.documentCreator(nimsRecordResponse);

		boolean guidMatchesExpected = XmlUtils.extractValueFromDocXml(doc,"pop.name.guid").equals(expectedGuid);
		boolean titleMatchesExpected = XmlUtils.extractValueFromDocXml(doc,"pop.name").equals(expectedTitle);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(guidMatchesExpected, "Our PopName GUID in our nims record response did match the GUID we were expecting to see."),
			() -> Assertions.assertTrue(titleMatchesExpected, "Our PopName title in our nims record response did match the title we were expecting to see.")
		);
	}
}
