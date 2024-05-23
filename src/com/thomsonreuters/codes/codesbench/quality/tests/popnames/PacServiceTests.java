package com.thomsonreuters.codes.codesbench.quality.tests.popnames;

import java.net.HttpURLConnection;
import java.sql.Connection;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.PopNamesRestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.RestAnnotations.REST;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.RestUtils;

/**
 *         The PAC project will occasionally request a list of all the PopNames
 *         in the database. The service is just a 'GET' request and returns a list of
 *         all the PopNames. This test makes sure the string isn't null and
 *         contains the same number of names as the database.
 */
public class PacServiceTests extends TestSetupEdge
{
	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY - Sends a 'GET' request to the UAT database for all PopNames and checks to see if a list was returned. If nothing is returned the test fails.&lt;br&gt;
	 * USER - N/A &lt;br&gt;
	 */
	@Test
	@REST
	@LOG
	public void responseFromRestConnectionWithoutWriteIsNotNullTest()
	{
		HttpURLConnection restConnection = RestUtils.connectToRestConnectionWithNoRequestProperty(PopNamesRestConstants.UAT_REST_PAC_VIEWS_CONNECTION_URL, RestUtils.GET);
		String nimsRecordResponse = RestUtils.getRestServiceResponseFromInputStreamWithoutWrite(restConnection);
		RestUtils.disconnectFromRestConnection(restConnection);

		Assertions.assertNotNull(nimsRecordResponse, "The response was null. We expect to get a list of PopNames returned.");
	}

	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY -  Sends a 'GET' request to the pacViews webservice for a list of all PopNames. A second query is sent to the database to get the
	 * total count of all PopNames. The list and the count are compared, if they do not match then the test fails. &lt;br&gt;
	 * USER - N/A &lt;br&gt;
	 */
	@Test
	@REST
	@LOG
	public void popNameCountReturnedMatchesDbCountTest()
	{
		String publicLawSection = "ROOT";

		HttpURLConnection restConnection = RestUtils.connectToRestConnectionWithNoRequestProperty(PopNamesRestConstants.UAT_REST_PAC_VIEWS_CONNECTION_URL, RestUtils.GET);
		String nimsRecordResponse = RestUtils.getRestServiceResponseFromInputStreamWithoutWrite(restConnection);
		RestUtils.disconnectFromRestConnection(restConnection);

		int totalFromService = StringUtils.countOccurrencesOf(nimsRecordResponse, "relationshipUuid");

		Connection uatPopnamesConnection = BaseDatabaseUtils.connectToDatabasePopnamesUAT();
		int totalFromDatabase = PopNameDatabaseUtils.getNumberOfPopNamesReturned(uatPopnamesConnection, publicLawSection);
		BaseDatabaseUtils.disconnect(uatPopnamesConnection);

		Assertions.assertEquals(totalFromDatabase, totalFromService, String.format("The total number of PopNames returned from our GET request: %s does not match the number of PopNames in the Database: %s", totalFromService, totalFromDatabase));
	}
}
