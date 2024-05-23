package com.thomsonreuters.codes.codesbench.quality.tests.popnames;

import java.net.HttpURLConnection;
import java.sql.Connection;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.PopNamesRestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.RestAnnotations.REST;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.RestUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.xml.XmlUtils;

/**
 *         This class tests the Bermuda resolution Service. Bermuda will query
 *         our DB with a pop name. We return to them the uuid of the popname,
 *         the text that was found as a match, and whether it was a full match
 *         or not.
 *
 */
public class BermudaResolutionRestServiceTest extends TestSetupEdge
{

	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY -  Queries the Popanames database for a given PopName and then parses the return values to see if they match the expected. &lt;br&gt;
	 * USER -  N/A &lt;br&gt;
	 */
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@CsvSource
	(
		value =
		{
			"Housing Assistance Tax Act of 2008",
			"National Sea Grant College Program Act Amendments of 2002",
			"Independent Offices and Department of Housing and Urban Development Appropriation Act, 1968",
			"Civil Service Due Process Amendments",
			"Clark Amendment",
			"Education Amendments of 1972",
			"Brown-Lodge Bill",
			"Brooke Amendment (Public Housing)",
			"Foreign Corrupt Practices Act of 1977",
			"Food for Peace Act",
			"Agricultural Trade Development and Assistance Act of 1954",
		},
		delimiterString = "!"
	) //If you are adding strings to this list and if any of them contain ! then change the delimiter string to a different character that doesn't appear in any of the strings
	@REST
	@LOG
	public void checkIfPopNameMatchesDataInDatabaseTest(String popName)
	{
		HttpURLConnection restConnection = RestUtils.connectToRestConnection(PopNamesRestConstants.UAT_REST_RESOLUTION_CONNECTION_URL, RestUtils.POST, RestUtils.CONTENT_TYPE, RestUtils.TEXT_PLAIN);

		String nimsRecordResponse = RestUtils.writeToRestServiceAndGetStringResponseFromInputStream(restConnection, popName);
		RestUtils.disconnectFromRestConnection(restConnection);

		String uuid = PopNameDatabaseUtils.getUuidOfPopNameFromDatabase(popName, PopNameDatabaseConstants.GET_UUID_FROM_POPNAME_QUERY);

		Document doc = XmlUtils.documentCreator(nimsRecordResponse);

		boolean partialMatchIsFalse = XmlUtils.extractValueFromDocXml(doc, "is.partial.match").equals("false");
		boolean uuidMatches = XmlUtils.extractValueFromDocXml(doc, "relationship.uuid").equals(uuid);
		boolean textMatches = XmlUtils.extractValueFromDocXml(doc, "matched.text").equals(popName);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(partialMatchIsFalse, "Our data came back with a partial match, we expected a full match."),
			() -> Assertions.assertTrue(uuidMatches, "Our UUID did match the UUID that we got from the Database."),
			() -> Assertions.assertTrue(textMatches, "Our matched matched.text text did match our PopName title.")
		);
	}
	
	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY -  Queries the Popanames database for a given popname and also queries the popname directly using a sql statement and the compares data. &lt;br&gt;
	 * USER -  N/A &lt;br&gt;
	 */
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@CsvSource
	(
		value =
		{
			"Section 109 of the Child Abuse Prevention and Treatment Act!Child Abuse Prevention and Treatment Act!109!false!!Section 109 of the Child Abuse Prevention and Treatment Act",
			"Insect Control Act, Section 1!!!true!I54165A6ECD804F3EAFFB49EFB1036BA2!Insect Control Act",
			"Food for Peace Act Section!!!true!IBC8AFBA14CD942A58C321D8FBF00C13B!Food for Peace Act",
			"Section 3 of the Food for Peace Act!!!false!IA892A81EC4D74D50A8E993EA442167BA!Section 3 of the Food for Peace Act",
			"Food for Peace TEST Act!!!false!IBC8AFBA14CD942A58C321D8FBF00C13B!Food for Peace TEST Act",
			"Food for Peace TEST Act Section 12345!!!true!IBC8AFBA14CD942A58C321D8FBF00C13B!Food for Peace TEST Act",
			"Agricultural Trade Development and Assistance Act of 1954 Section 999 !!!true!IBC8AFBA14CD942A58C321D8FBF00C13B!Agricultural Trade Development and Assistance Act of 1954",
			"Section 123NOT of the Food for Peace Act!!!true!IBC8AFBA14CD942A58C321D8FBF00C13B!Food for Peace Act"
		},
		delimiterString = "!"
	)
	@REST
	@LOG
	public void checkIfPopNameMatchesWithPartialPopName(String popName, String popNameDescription, String popNameSectionNumber, String extractPartialMatch, String relationshipUuid, String extractText)
	{
		HttpURLConnection restConnection = RestUtils.connectToRestConnection(PopNamesRestConstants.UAT_REST_RESOLUTION_CONNECTION_URL, RestUtils.POST, RestUtils.CONTENT_TYPE, RestUtils.TEXT_PLAIN);
		String nimsRecordResponse = RestUtils.writeToRestServiceAndGetStringResponseFromInputStream(restConnection, popName);
		RestUtils.disconnectFromRestConnection(restConnection);

		if(relationshipUuid == null)
		{
			Connection uatPopnamesConnection = BaseDatabaseUtils.connectToDatabasePopnamesUAT();
			relationshipUuid = PopNameDatabaseUtils.getRelationshipUuidFromPopName(uatPopnamesConnection, popNameDescription, popNameSectionNumber);
			BaseDatabaseUtils.disconnect(uatPopnamesConnection);
		}
		Document doc = XmlUtils.documentCreator(nimsRecordResponse);

		boolean partialMatches  = XmlUtils.extractValueFromDocXml(doc, "is.partial.match").equals(extractPartialMatch);
		boolean uuidMatches = XmlUtils.extractValueFromDocXml(doc, "relationship.uuid").equals(relationshipUuid);
		boolean textMatches = XmlUtils.extractValueFromDocXml(doc, "matched.text").equals(extractText);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(partialMatches, "Our data came back with a partial match, we expected a full match."),
			() -> Assertions.assertTrue(uuidMatches, "Our UUID did not match the UUID that we got from the Database."),
			() -> Assertions.assertTrue(textMatches, "Our matched matched.text text did match our PopName title.")
		);
	}

	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY -  Queries the Popanames database for an incomplete popname and checks to see if the popname was found &lt;br&gt;
	 * USER -  N/A &lt;br&gt;
	 */
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@CsvSource
	(
		{
			"Peace Act",
			"Peace Test Act",
			"Section123NOT Agricultural Development and Assistance Act of 1954"
		}
	)
	@REST
	@LOG
	public void isPartialPopNameFoundInDatabase(String popName)
	{
		HttpURLConnection restConnection = RestUtils.connectToRestConnection(PopNamesRestConstants.UAT_REST_RESOLUTION_CONNECTION_URL, RestUtils.POST, RestUtils.CONTENT_TYPE, RestUtils.TEXT_PLAIN);
		RestUtils.writeToRestService(restConnection, popName);

		String expectedResponse = String.format("Unable to resolve \"%s\". No Pop Names found for reference \"%s\".", popName, popName);

		boolean responseMessageIsEqualToNotFound = expectedResponse.equals(RestUtils.getResponseBody(restConnection));
		RestUtils.disconnectFromRestConnection(restConnection);
		Assertions.assertTrue(responseMessageIsEqualToNotFound, expectedResponse);
	}
}
