package com.thomsonreuters.codes.codesbench.quality.tests.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.XmlExtractStateFeedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.ExtractedDataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.FileManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.StateFeedBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.statefeeds.StateFeedsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.xml.XmlUtils;
import org.custommonkey.xmlunit.Difference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.openqa.selenium.WebElement;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class StateFeedPositiveTests extends TestService
{
	private static final String TITLE_4 = "TITLE 4 - ";
	private static final String ALL_DATA = "All Data";
	private static final String TEXT_CREDIT_HISTORICAL = "Text Credit Historical";
	private static final String TEXT_CREDIT_ONLY = "Text Credit Only";
	private static final String CONTENT_SET = "South Carolina (Development)";
	private static final String TITLE_4_ALL_DATA = TITLE_4 + ALL_DATA + ".xml";
	private static final String TITLE_4_TEXT_CREDIT_HISTORICAL = TITLE_4 + TEXT_CREDIT_HISTORICAL + ".xml";
	private static final String TITLE_4_TEXT_CREDIT_ONLY = TITLE_4 + TEXT_CREDIT_ONLY + ".xml";
	private static final String FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE = "The %s file should be selected, but wasn't.";
	private static final String FILE_SHOULD_NOT_BE_SELECTED_ASSERTION_MESSAGE = "The %s file should NOT be selected, but was.";
	private static final String COMMON_FILES_PATH = "commonFiles\\TestFiles\\StateFeed\\";
	private static final String TITLE_4_ALL_DATA_REGEX_PATTERN = "TITLE\\s4\\s-\\sAll\\sData";
	private static final String TITLE_4_TEXT_CREDIT_HISTORICAL_REGEX_PATTERN = "TITLE\\s4\\s-\\sText\\sCredit\\sHistorical";
	private static final String TITLE_4_TEXT_CREDIT_ONLY_REGEX_PATTERN = "TITLE\\s4\\s-\\sText\\sCredit\\sOnly";

	private String groupName = null;
	private boolean deleteGroupNameFlag = false;

	/**
	 * STORY/BUG - HALCYONST-13060, 17201, 17204, 17198
	 * SUMMARY - Verify the Tools -> State Feed UI (check for tables, buttons, and content set.)
	 * USER - Legal
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void verifyStateFeedUITest()
	{
		String expectedExtractedDataHeaderValue = "Extracted data";
		String expectedFileManagementHeaderValue = "File management";

		homePage().goToHomePage();
		loginPage().logIn();
		String expectedContentSet = homePage().getCurrentContentSetFromHomepageDropdown();

		boolean stateFeedPageOpened = toolsMenu().goToStateFeed();
		Assertions.assertTrue(stateFeedPageOpened, "The State Feed Page should appear, but didn't.");

		boolean stateFeedPageHeaderIsDisplayed = stateFeedPage().isElementDisplayed(StateFeedBasePageElements.PAGE_SECTION_HEADER);
		boolean extractedDataHeaderIsDisplayed = extractedDataPage().isElementDisplayed(ExtractedDataPageElements.HEADER_XPATH);
		boolean fileManagementHeaderIsDisplayed = fileManagementPage().isElementDisplayed(FileManagementPageElements.HEADER_XPATH);

		//Check the Extracted Data section's UI
		String actualExtractDataHeaderValue = extractedDataPage().getTableHeader();
		boolean extractedDataClearFiltersAndSortsIsClickable = extractedDataPage().isClearFiltersAndSortsClickable();
		boolean extractedDataRefreshCurrentGridIsClickable = extractedDataPage().isRefreshCurrentGridClickable();
		int extractedDataRefreshCurrentGridYPosition = extractedDataPage().getElementLocation(ExtractedDataPageElements.refreshCurrentGrid).getY();
		int extractedDataRefreshCurrentGridXPosition = extractedDataPage().getElementLocation(ExtractedDataPageElements.refreshCurrentGrid).getX();
		int extractedDataClearFiltersAndSortsYPosition = extractedDataPage().getElementLocation(ExtractedDataPageElements.clearFiltersAndSorts).getY();
		int extractedDataClearFiltersAndSortsXPosition = extractedDataPage().getElementLocation(ExtractedDataPageElements.clearFiltersAndSorts).getX();
		boolean extractedDataTableHasJurisdictionHeader = extractedDataPage().isElementDisplayed(ExtractedDataPageElements.JURISDICTION_HEADER_XPATH);
		boolean extractedDataTableHasGroupHeader = extractedDataPage().isElementDisplayed(ExtractedDataPageElements.GROUP_HEADER_XPATH);

		//Check the File Management section's UI
		String actualFileManagementHeaderValue = fileManagementPage().getTableHeader();
		boolean fileManagementClearFiltersAndSortsClickable = fileManagementPage().isClearFiltersAndSortsClickable();
		boolean fileManagementRefreshCurrentGridClickable = fileManagementPage().isRefreshCurrentGridClickable();
		boolean fileManagementDownloadButtonPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.DOWNLOAD_BUTTON_XPATH);
		boolean fileManagementDeleteButtonPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.DELETE_BUTTON_XPATH);
		boolean fileManagementSendToStateButtonPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.SEND_TO_STATE_BUTTON_XPATH);
		int fileManagementRefreshCurrentGridYPosition = extractedDataPage().getElementLocation(FileManagementPageElements.refreshCurrentGrid).getY();
		int fileManagementRefreshCurrentGridXPosition = extractedDataPage().getElementLocation(FileManagementPageElements.refreshCurrentGrid).getX();
		int fileManagementClearFiltersAndSortsYPosition = extractedDataPage().getElementLocation(FileManagementPageElements.clearFiltersAndSorts).getY();
		int fileManagementClearFiltersAndSortsXPosition = extractedDataPage().getElementLocation(FileManagementPageElements.clearFiltersAndSorts).getX();
		int fileManagementDownloadButtonYPosition = extractedDataPage().getElementLocation(FileManagementPageElements.downloadButton).getY();
		int fileManagementDownloadButtonXPosition = extractedDataPage().getElementLocation(FileManagementPageElements.downloadButton).getX();
		int fileManagementDeleteButtonYPosition = extractedDataPage().getElementLocation(FileManagementPageElements.deleteButton).getY();
		int fileManagementDeleteButtonXPosition = extractedDataPage().getElementLocation(FileManagementPageElements.deleteButton).getX();
		int fileManagementSendToStateButtonYPosition = extractedDataPage().getElementLocation(FileManagementPageElements.sendToStateButton).getY();
		int fileManagementSendToStateButtonXPosition = extractedDataPage().getElementLocation(FileManagementPageElements.sendToStateButton).getX();
		boolean fileManagementXmlFileHeaderPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.XML_FILE_HEADER_XPATH);
		boolean fileManagementExtractDateHeaderPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.EXTRACT_DATE_HEADER_XPATH);
		boolean fileManagementXmlFileCheckboxPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.SELECT_ALL_HEADER_CHECKBOX_XPATH);

		String actualContentSet = stateFeedPage().getContentSetValue();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(stateFeedPageHeaderIsDisplayed, "The page headers for the state feed page should have appeared, but didn't."),
			() -> Assertions.assertTrue(extractedDataHeaderIsDisplayed, "The extracted data table should have appeared, but didn't."),
			() -> Assertions.assertTrue(fileManagementHeaderIsDisplayed, "The file management table should have appeared, but didn't."),
			() -> Assertions.assertEquals(expectedExtractedDataHeaderValue, actualExtractDataHeaderValue, "The table header for Extracted data should be 'Extracted data'."),
			() -> Assertions.assertTrue(extractedDataClearFiltersAndSortsIsClickable, "The Clear Filters and Sorts Grid button should be clickable for the extracted data table, but isn't."),
			() -> Assertions.assertTrue(extractedDataRefreshCurrentGridIsClickable, "The Refresh Grid button should be clickable for the extracted data table, but isn't."),
			() -> Assertions.assertEquals(extractedDataClearFiltersAndSortsYPosition, extractedDataRefreshCurrentGridYPosition, "The Refresh Current Grid and Clear Sorts buttons should have the same height, but didn't."),
			() -> Assertions.assertTrue(extractedDataClearFiltersAndSortsXPosition < extractedDataRefreshCurrentGridXPosition, "The Clear Filters and Sorts button should be to the left of the Refresh Current Grid button, but wasn't."),
			() -> Assertions.assertTrue(extractedDataTableHasJurisdictionHeader, "The extracted data table should have the jurisdiction table header."),
			() -> Assertions.assertTrue(extractedDataTableHasGroupHeader, "The extracted data table should have the group table header."),
			() -> Assertions.assertEquals(expectedFileManagementHeaderValue, actualFileManagementHeaderValue, "The table header for File management should be 'File management.'"),
			() -> Assertions.assertTrue(fileManagementClearFiltersAndSortsClickable, "The Clear Filters and Sorts Grid button should be clickable for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementRefreshCurrentGridClickable, "The Refresh Grid button should be clickable for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementDownloadButtonPresent, "The Download button should be present for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementDeleteButtonPresent, "The Delete button should be present for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementSendToStateButtonPresent, "The Send to State button should be present for the file management table, but isn't."),
			() -> Assertions.assertEquals(fileManagementClearFiltersAndSortsYPosition, fileManagementRefreshCurrentGridYPosition, "The Refresh Current Grid and Clear Sorts buttons should have the same height, but didn't."),
			() -> Assertions.assertTrue(fileManagementClearFiltersAndSortsXPosition < fileManagementRefreshCurrentGridXPosition, "The Clear Filters and Sorts button should be to the left of the Refresh Current Grid button, but wasn't."),
			() -> Assertions.assertEquals(fileManagementDownloadButtonYPosition, fileManagementDeleteButtonYPosition, "The Download and Delete buttons should have the same height, but didn't."),
			() -> Assertions.assertTrue(fileManagementDownloadButtonXPosition < fileManagementDeleteButtonXPosition, "The Download button should be to the left of the Delete button, but wasn't."),
			() -> Assertions.assertEquals(fileManagementDeleteButtonYPosition, fileManagementSendToStateButtonYPosition, "The Delete and Send to State buttons should have the same height, but didn't."),
			() -> Assertions.assertTrue(fileManagementDeleteButtonXPosition < fileManagementSendToStateButtonXPosition, "The Delete button should be to the left of the Send to State button, but wasn't."),
			() -> Assertions.assertTrue(fileManagementXmlFileHeaderPresent, "The XML file column header should be present for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementExtractDateHeaderPresent, "The Extract date column header should be present for the file management table, but isn't."),
			() -> Assertions.assertTrue(fileManagementXmlFileCheckboxPresent, "The XML file checkbox should be present for the file management table, but isn't."),
			() -> Assertions.assertEquals(expectedContentSet, actualContentSet, "The content set in the state feed page should match the content set for the user.")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-13084,13087,17186,17192,17207,17201,17204,17198,17195
	 * SUMMARY - This test creates an XML Extract State Feed from hierarchy -> navigate, then confirms that the process
	 * populates correctly in the tools -> state feed page tables. Specifically checks to make sure the XML extract is
	 * set under the proper year and content set groups in the extracted data table, and validates the XML file names
	 * in the file management table.
	 * USER - Legal
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void verifySingleStateFeedXmlExtractTablePopulationTest()
	{
		String nodeUuid = "I3C90ACF04E5611DBBC06E7A96B860EAF";
		String currentDateAndTime = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String currentDate = DateAndTimeUtils.getCurentDateMDYYYY();
		groupName = "Single State Feed Extract " + currentDateAndTime;
		String defaultYear = "2022";

		homePage().goToHomePage();
		loginPage().logIn();
		homePage().setMyContentSet(CONTENT_SET);

		hierarchyMenu().goToNavigate();

		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().xmlExtractStateFeed();

		xmlExtractStateFeedPage().setGroupName(groupName);
		xmlExtractStateFeedPage().clickSubmit();

		String workflowId = xmlExtractStateFeedPage().getWorkflowId();
		//The window gets focused into the iframe of the workflow popup to grab the workflow id, which prevents the ability to close the window
		xmlExtractStateFeedPage().switchToWindow(XmlExtractStateFeedPageElements.XML_EXTRACT_STATE_FEED_PAGE_TITLE);
		xmlExtractStateFeedPage().clickClose();

		//Check that workflow finished
		toolsMenu().goToWorkflowReportingSystem();
		workflowSearchPage().setWorkflowID(workflowId);
		workflowSearchPage().clickFilterButton();
		boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
		Assertions.assertTrue(workflowFinished, String.format("The workflow with id: %s did not finish", workflowId));

		workflowPropertiesPage().closeCurrentWindowIgnoreDialogue();
		hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
		toolsMenu().goToStateFeed();

		//Next we filter for the group, since angular lazy loads in elements in the table so if it's far down we might not be able to find it normally
		extractedDataPage().clickGroupFilterMenu();
		extractedDataPage().filterForGroup(groupName);

		//Next, validate the results to ensure that filtering worked correctly
		boolean groupNameExist = extractedDataPage().doesGivenGroupNameExist(groupName);
		List<WebElement> elementsInExtractedDataTable = extractedDataPage().getFilesGivenGroupNameList(groupName);
		int actualNumberOfResultsAfterFiltering = elementsInExtractedDataTable.size();
		boolean doesYearContainGroupName = extractedDataPage().doesGivenYearContainGroupName(defaultYear);
		boolean doesJurisdictionContainGroupName = extractedDataPage().doesGivenJurisdictionContainGroupName(CONTENT_SET);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(groupNameExist, "The given group name should exist in the table, but didn't."),
			() -> Assertions.assertTrue(doesYearContainGroupName, "The given year should contain the group name in the table, but didn't."),
			() -> Assertions.assertTrue(doesJurisdictionContainGroupName, "The given jurisdiction should contain the group name in the table, but didn't."),
			() -> Assertions.assertEquals(3, actualNumberOfResultsAfterFiltering, "The number of elements in the table after filtering should have been 3, but wasn't.")
		);

		//Now that we know the group name exists, set the deletion flag for the after each function to clean it up.
		deleteGroupNameFlag = true;

		extractedDataPage().clickGroupName(groupName);

		//Verify that collapsing/expanding works
		boolean allDataSectionPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.ALL_DATA_SECTION_XPATH);
		fileManagementPage().collapseAllDataSection();
		boolean allDataXmlFilePresentBeforeExpanding = fileManagementPage().isXmlFilePresent(ALL_DATA + ".xml");
		fileManagementPage().expandAllDataSection();
		boolean allDataXmlFilePresentAfterExpanding = fileManagementPage().isXmlFilePresent(ALL_DATA + ".xml");
		boolean allDataExtractDateCorrect = fileManagementPage().verifyExtractDateByFileName(fileManagementPage().getRowIdFromFileName(ALL_DATA + ".xml"), currentDate);
		fileManagementPage().collapseAllDataSection();

		boolean textCreditHistoricalSectionPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.TEXT_CREDIT_HISTORICAL_SECTION_XPATH);
		fileManagementPage().collapseTextCreditHistoricalSection();
		boolean textCreditHistoricalXmlFilePresentBeforeExpanding = fileManagementPage().isXmlFilePresent(TEXT_CREDIT_HISTORICAL + ".xml");
		fileManagementPage().expandTextCreditHistoricalSection();
		boolean textCreditHistoricalXmlFilePresentAfterExpanding = fileManagementPage().isXmlFilePresent(TEXT_CREDIT_HISTORICAL + ".xml");
		boolean isTextCreditHistoricalExtractDateCorrect = fileManagementPage().verifyExtractDateByFileName(fileManagementPage().getRowIdFromFileName(TEXT_CREDIT_HISTORICAL + ".xml"), currentDate);
		fileManagementPage().collapseTextCreditHistoricalSection();

		boolean textCreditOnlySectionPresent = fileManagementPage().isElementDisplayed(FileManagementPageElements.TEXT_CREDIT_ONLY_SECTION_XPATH);
		fileManagementPage().collapseTextCreditOnly();
		boolean textCreditOnlyXmlFilePresentBeforeExpanding = fileManagementPage().isXmlFilePresent(TEXT_CREDIT_ONLY + ".xml");
		fileManagementPage().expandTextCreditOnly();
		boolean textCreditOnlyXmlFilePresentAfterExpanding = fileManagementPage().isXmlFilePresent(TEXT_CREDIT_ONLY + ".xml");
		boolean isTextCreditOnlyExtractDateCorrect = fileManagementPage().verifyExtractDateByFileName(fileManagementPage().getRowIdFromFileName(TEXT_CREDIT_ONLY + ".xml"), currentDate);
		fileManagementPage().collapseTextCreditOnly();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(allDataSectionPresent, "The All Data section should be present, but wasn't."),
			() -> Assertions.assertTrue(textCreditHistoricalSectionPresent, "The Text Credit Historical section should be present, but wasn't."),
			() -> Assertions.assertTrue(textCreditOnlySectionPresent, "The Text Credit Only section should be present, but wasn't."),
			() -> Assertions.assertFalse(allDataXmlFilePresentBeforeExpanding, "The All Data.xml file should not be present under the All Data section before expanding it, but was."),
			() -> Assertions.assertTrue(allDataXmlFilePresentAfterExpanding, "The All Data.xml file should be present under the All Data section after expanding it, but wasn't."),
			() -> Assertions.assertFalse(textCreditHistoricalXmlFilePresentBeforeExpanding, "The Text Credit Historical.xml file should not be present under the Text Credit Historical section before expanding it, but was."),
			() -> Assertions.assertTrue(textCreditHistoricalXmlFilePresentAfterExpanding, "The Text Credit Historical.xml file should be present under the Text Credit Historical section after expanding it, but wasn't."),
			() -> Assertions.assertFalse(textCreditOnlyXmlFilePresentBeforeExpanding, "The Text Credit Only.xml file should not be present under the Text Credit Only section before expanding it, but was."),
			() -> Assertions.assertTrue(textCreditOnlyXmlFilePresentAfterExpanding, "The Text Credit Only.xml file should be present under the Text Credit Only section after expanding it, but wasn't."),
			() -> Assertions.assertTrue(allDataExtractDateCorrect, "The Extract Date for All Data should be the current date, but wasn't."),
			() -> Assertions.assertTrue(isTextCreditHistoricalExtractDateCorrect, "The Extract Date for Text Credit Historical should be the current date, but wasn't."),
			() -> Assertions.assertTrue(isTextCreditOnlyExtractDateCorrect, "The Extract Date for Text Credit Only should be the current date, but wasn't.")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-17198-17192 <br>
	 * SUMMARY - This test verifies users can select multiple documents using CTRL + Click and select all checkbox <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void stateFeedExtractMultiSelectTest()
	{
		groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
		deleteGroupNameFlag = true;

		navigateToStateFeedAndSelectGroupName();
		fileManagementPage().checkSelectAllCheckbox();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_ALL_DATA), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, ALL_DATA)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_HISTORICAL), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_HISTORICAL)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_ONLY), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_ONLY))
		);

		fileManagementPage().uncheckSelectAllCheckbox();

		Assertions.assertAll
		(
			() -> Assertions.assertFalse(fileManagementPage().isTableRowSelected(TITLE_4_ALL_DATA), String.format(FILE_SHOULD_NOT_BE_SELECTED_ASSERTION_MESSAGE, ALL_DATA)),
			() -> Assertions.assertFalse(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_HISTORICAL), String.format(FILE_SHOULD_NOT_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_HISTORICAL)),
			() -> Assertions.assertFalse(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_ONLY), String.format(FILE_SHOULD_NOT_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_ONLY))
		);

		fileManagementPage().selectMultipleFiles(TITLE_4_ALL_DATA, TITLE_4_TEXT_CREDIT_HISTORICAL, TITLE_4_TEXT_CREDIT_ONLY);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_ALL_DATA), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, ALL_DATA)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_HISTORICAL), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_HISTORICAL)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_ONLY), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_ONLY))
		);
	}

	/**
	 * STORY/BUG - HALCYONST-17198-17192 <br>
	 * SUMMARY - This test verifies users can select multiple documents using SHIFT + Click <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void stateFeedExtractMultiSelectShiftClickTest()
	{
		groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
		deleteGroupNameFlag = true;

		navigateToStateFeedAndSelectGroupName();

		fileManagementPage().selectFilesBetween(TITLE_4_ALL_DATA, TITLE_4_TEXT_CREDIT_ONLY);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_ALL_DATA), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, ALL_DATA)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_HISTORICAL), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_HISTORICAL)),
			() -> Assertions.assertTrue(fileManagementPage().isTableRowSelected(TITLE_4_TEXT_CREDIT_ONLY), String.format(FILE_SHOULD_BE_SELECTED_ASSERTION_MESSAGE, TEXT_CREDIT_ONLY))
		);
	}

	/**
	 * STORY/BUG - HALCYONST-17204 <br>
	 * SUMMARY - This test verifies files are removed from the grid when deleted through the context menu and the delete button at the top of the grid <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void deleteFilesTest()
	{
		groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
		deleteGroupNameFlag = true;

		navigateToStateFeedAndSelectGroupName();

		fileManagementPage().rightClickFile(TITLE_4_ALL_DATA);
		fileManagementContextMenu().delete();

		boolean deleteFileAlertAppears = fileManagementPage().doesElementExist(FileManagementPageElements.DELETE_FILE_CONFIRMATION_POPUP_XPATH);
		Assertions.assertTrue(deleteFileAlertAppears,"The delete file alert appears");

		fileManagementPage().acceptDeleteFileAlert();

		boolean isFileDeleted = fileManagementPage().isElementDisplayed(FileManagementPageElements.ALL_DATA_SECTION_XPATH);
		Assertions.assertFalse(isFileDeleted, "All Data.xml was deleted");

		fileManagementPage().checkSelectAllCheckbox();
		fileManagementPage().clickDeleteButton();
		fileManagementPage().acceptDeleteFileAlert();

		Assertions.assertEquals(0, fileManagementPage().getAllRows().size(), "The grid should be empty when all rows are deleted, but wasn't");
	}

	/**
	 * STORY/BUG - HALCYONST-17201 <br>
	 * SUMMARY - This test makes sure that the "send to state" button is enabled when selecting a group of files in an extract. <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void sendToStateButtonIsEnabledForXmlExtractTest()
	{
		groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
		deleteGroupNameFlag = true;

		navigateToStateFeedAndSelectGroupName();
		fileManagementPage().checkSelectAllCheckbox();
		Assertions.assertTrue(fileManagementPage().isElementEnabled(FileManagementPageElements.sendToStateButton), "The send to state button is enabled");
	}

    /**
     * STORY/BUG - HALCYONST-13084,13087,17186,17192,17207,17201,17204,17198,17195
     * SUMMARY - This will upload stored copies of xml files to validate against via an FTP upload, then downloads them
     * from the Tools -> State Feeds page and compares the 2 copies to ensure no data was changed in the upload/download process.
     * This is the single click test that downloads files individually.
     * USER - Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySingleStateFeedExtractXmlFilesUsingSingleSelectTest()
    {
        groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
        deleteGroupNameFlag = true;

		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, ALL_DATA);
		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, TEXT_CREDIT_HISTORICAL);
		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, TEXT_CREDIT_ONLY);

        navigateToStateFeedAndSelectGroupName();

        fileManagementPage().selectFile(TITLE_4_ALL_DATA);
        fileManagementPage().clickDownloadButton();
        AutoITUtils.clickKeepOnEdgePopupWithAutoIT();

        fileManagementPage().selectFile(TITLE_4_TEXT_CREDIT_HISTORICAL);
        fileManagementPage().clickDownloadButton();
        AutoITUtils.clickKeepOnEdgePopupWithAutoIT();

        fileManagementPage().selectFile(TITLE_4_TEXT_CREDIT_ONLY);
        fileManagementPage().clickDownloadButton();
        AutoITUtils.clickKeepOnEdgePopupWithAutoIT();

		boolean allDataFileDownloaded = FileUtils.waitForFileToExist(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_ALL_DATA + ".xml", DateAndTimeUtils.TEN_SECONDS);
		boolean textCreditHistoricalFileDownloaded = FileUtils.waitForFileToExist(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_TEXT_CREDIT_HISTORICAL + ".xml", DateAndTimeUtils.TEN_SECONDS);
		boolean textCreditOnlyFileDownloaded = FileUtils.waitForFileToExist(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_TEXT_CREDIT_ONLY + ".xml", DateAndTimeUtils.TEN_SECONDS);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(allDataFileDownloaded,"All Data.xml downloaded"),
			() -> Assertions.assertTrue(textCreditHistoricalFileDownloaded,"Text Credit Historical.xml downloaded"),
			() -> Assertions.assertTrue(textCreditOnlyFileDownloaded,"Text Credit Only.xml downloaded")
		);

        File[] downloadedFiles = new File[3];
        File[] expectedFiles = new File[3];
        //Grab both the downloaded and local copy of each file
        expectedFiles[0] = new File(COMMON_FILES_PATH + TITLE_4_ALL_DATA);
        downloadedFiles[0] = new File(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_ALL_DATA + ".xml");
        expectedFiles[1] = new File(COMMON_FILES_PATH + TITLE_4_TEXT_CREDIT_HISTORICAL);
        downloadedFiles[1] = new File(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_TEXT_CREDIT_HISTORICAL + ".xml");
        expectedFiles[2] = new File(COMMON_FILES_PATH + TITLE_4_TEXT_CREDIT_ONLY);
        downloadedFiles[2] = new File(FileUtils.DOWNLOAD_FOLDER_DIR + TITLE_4_TEXT_CREDIT_ONLY + ".xml");

        validateFiles(expectedFiles, downloadedFiles);
    }

    public void validateFiles(File[] expectedFiles, File[] actualFiles)
    {
        for (int i = 0; i < expectedFiles.length; i++) //expectedFiles.length
        {
            //Grabs the content of the xml file and saves it to a string
            String expectedXml = null;
            String actualXml = null;
            try
            {
                expectedXml = Files.readString(expectedFiles[i].toPath(), StandardCharsets.US_ASCII);
                actualXml = Files.readString(actualFiles[i].toPath(), StandardCharsets.US_ASCII);
            }
            catch (IOException e)
			{
				e.printStackTrace();
			}

            //Prepends the DTDs to the xml content to prevent errors from popping up later in validation
            expectedXml = XmlUtils.prependDTDs() + expectedXml;
            actualXml = XmlUtils.prependDTDs() + actualXml;

            File expectedFile = null;
            File actualFile = null;

            try
            {
                //Creates new temp files for validation, writes the prepended DTDs and xml content to them
                expectedFile = new File(COMMON_FILES_PATH + "expected.xml");
                actualFile = new File(COMMON_FILES_PATH + "actual.xml");
                String expectedNewPath = expectedFile.getAbsolutePath();
                String actualNewPath = actualFile.getAbsolutePath();
                PrintWriter writer = null;
                writer = new PrintWriter(expectedNewPath);
                writer.println(expectedXml);
                writer.close();
                writer = new PrintWriter(actualNewPath);
                writer.println(actualXml);
                writer.close();

                //This creates a list of differences between the files, so if that list is empty, there are no differences
                List<Difference> differences = XmlUtils.compare(expectedFile, actualFile);
                Assertions.assertEquals(0, differences.size(), "There should be no differences between the files " +
                        expectedFiles[i].getName() + " and " + actualFiles[i] + ", but there were.");
            }
            catch (IOException e)
            {
				logger.information("Failed to write to file");
                e.printStackTrace();
            }
            finally
            {
                //Delete temp files
                try
                {
                    Files.delete(expectedFile.toPath());
                    Files.delete(actualFile.toPath());
                }
                catch (IOException e)
                {
					logger.information("Failed to find file when deleting files " + expectedFile.getName() + " " + actualFile.getName());
                    e.printStackTrace();
                }
            }
		}
    }

	private void navigateToStateFeedAndSelectGroupName()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		homePage().setMyContentSet(CONTENT_SET);
		toolsMenu().goToStateFeed();

		extractedDataPage().clickGroupFilterMenu();
		extractedDataPage().filterForGroup(groupName);
		extractedDataPage().clickGroupName(groupName);
	}

	@AfterEach
	public void deleteGroupNameFromFtpServer()
	{
		if (deleteGroupNameFlag && groupName != null)
		{
			StateFeedsDataMocking.deleteGroup(groupName);
			deleteGroupNameFlag = false;
		}

		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, ALL_DATA);
		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, TEXT_CREDIT_HISTORICAL);
		FileUtils.deleteAllFilesContains(FileUtils.DOWNLOAD_FOLDER_DIR, TEXT_CREDIT_ONLY);
	}
}