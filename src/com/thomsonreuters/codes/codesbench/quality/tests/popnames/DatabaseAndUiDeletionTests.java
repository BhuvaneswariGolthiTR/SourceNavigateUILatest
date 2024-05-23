package com.thomsonreuters.codes.codesbench.quality.tests.popnames;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.connectToDatabasePopnamesUAT;

public class DatabaseAndUiDeletionTests extends TestService
{
	private static final String TEST_POPNAME_LETTER = "YACHTTAXACTS";
	private static final String CONTENT_SET = "PopName (UPT)";
	private static final String NODE_UUID = "I7FBE9DF0CE3911DEA64BC13F4D131DCF";

	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY - This test bulk publishes a certain node then checks the database to see if the correct amount of nodes appear.&lt;br&gt;
	 * USER - Legal &lt;br&gt;
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void totalProcessedRecordsCountTest()
	{
		int numPopNames = 1;
		String today = DateAndTimeUtils.getCurrentDateMMddyyyy();

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(CONTENT_SET);
		boolean hierarchyPubNavigatePageAppeared = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(hierarchyPubNavigatePageAppeared, "The hierarchy pub navigate page appeared");

		hierarchySearchPage().searchNodeUuid(NODE_UUID);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		boolean bulkPublishPageAppeared = siblingMetadataContextMenu().publishingWorkflowsRepublishByHierarchy();
		Assertions.assertTrue(bulkPublishPageAppeared, "The bulk publish page did appear");

		bulkPublishPage().clickPublishButton();
		String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
		yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
		boolean hierarchyNavigatePageAppeared = hierarchyPubNavigatePage().switchToPubNavigatePage();
		Assertions.assertTrue(hierarchyNavigatePageAppeared, "The hierarchy navigate page did appear");

		boolean workflowReportingPageAppeared = toolsMenu().goToWorkflowReportingSystem();
		Assertions.assertTrue(workflowReportingPageAppeared, "The workflow reporting page did appear");

		workflowSearchPage().setWorkflowID(workflowId);
		workflowSearchPage().clickFilterButton();
		boolean allWorkflowsFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
		Assertions.assertTrue(allWorkflowsFinished, String.format("Workflow with Id: %s did finish.", workflowId));

		Connection uatPopnamesConnection = connectToDatabasePopnamesUAT();
		int totalProcessedRecords = PopNameDatabaseUtils.getPopNameRecordsCount(uatPopnamesConnection, TEST_POPNAME_LETTER, today, NODE_UUID);
		BaseDatabaseUtils.disconnect(uatPopnamesConnection);

		Assertions.assertEquals(numPopNames, totalProcessedRecords, "Number of PopNames in the database does not equal 1");
	}
}
