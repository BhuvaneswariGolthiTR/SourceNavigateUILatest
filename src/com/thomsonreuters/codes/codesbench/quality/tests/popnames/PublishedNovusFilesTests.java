package com.thomsonreuters.codes.codesbench.quality.tests.popnames;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.PopNamesLandingStripsConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class PublishedNovusFilesTests extends TestService
{
	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY - Checks if certain files Novus expects were created and are not empty. &lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void popNamesPublishedNovusFilesLegalTest()
	{
		String baseDir = String.format(PopNamesLandingStripsConstants.POPNAMES_DIRECTORY_FOR_DATE, DateAndTimeUtils.getCurrentYearyyyy(), (DateAndTimeUtils.getCurrentMonthMM() + DateAndTimeUtils.getCurrentDayDD()));
		String nodeUUID = "IA4D7FE10CE3911DE89F0CC6BC455EA95";
		String contentSet = "PopName (UPT)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(contentSet);
		boolean pubNavigatePageAppeared = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(pubNavigatePageAppeared, "The pub navigate page did appear");

		hierarchySearchPage().searchNodeUuid(nodeUUID);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().publishingWorkflowsRepublishByHierarchy();
		bulkPublishPage().clickPublishButton();
		String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
		yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
		pubNavigatePageAppeared = hierarchyPubNavigatePage().switchToPubNavigatePage();
		Assertions.assertTrue(pubNavigatePageAppeared, "The pub navigate page did appear");

		boolean workflowReportingSystemAppeared = toolsMenu().goToWorkflowReportingSystem();
		Assertions.assertTrue(workflowReportingSystemAppeared, "The workflow reporting system did appear");

		workflowSearchPage().setWorkflowID(workflowId);
		workflowSearchPage().clickFilterButton();
		boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
		Assertions.assertTrue(workflowFinished, String.format("The workflow with Id %s did finish",workflowId));

		workflowSearchPage().openWorkflow(workflowId);
		workflowPropertiesPage().clickWorkflowPropertiesButton();
		String jobId = workflowPropertiesPage().getWorkflowPropertyValueByName("jobId");
		String nodeDir = baseDir + "/" + jobId;

		LandingStripsFTPClient landingStripsFTPClient = new LandingStripsFTPClient();
		landingStripsFTPClient.connectToServer();
		int docFileCount = landingStripsFTPClient.getFileCountInDirectory(nodeDir, "doc.load.xml");
		int nimsFileCount = landingStripsFTPClient.getFileCountInDirectory(nodeDir, "nims.load.xml");
		int nortFileCount = landingStripsFTPClient.getFileCountInDirectory(nodeDir, "nort.load.xml");
		landingStripsFTPClient.disconnectFromServer();

		Assertions.assertAll
		(
				() -> Assertions.assertEquals(1, nortFileCount, "Not all *.nort.load.xml files have been created in the " + nodeDir + " directory."),
				() -> Assertions.assertEquals(1, nimsFileCount, "Not all *.nims.load.xml files have been created in the " + nodeDir + " directory."),
				() -> Assertions.assertEquals(1, docFileCount, "Not all *.doc.load.xml files have been created in the " + nodeDir + " directory.")
		);

	}
}
