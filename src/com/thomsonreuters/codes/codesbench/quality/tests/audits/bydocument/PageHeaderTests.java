package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;


import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;

/**
 * Used to test the heading of each page using multiple different tests
 * Also can be used as a skeleton for the other tests, keep this formatting
 * Most of the code in these methods here can probably be put into the PDFUtils as methods there, but I made them here because they will only be used once anyway
 * Kept the test in the test and not in pdf utils class
 * Make sure the after each is always the same so the datamocked nodes are always deleted afterwards
 */
public class PageHeaderTests extends TestService
{
    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    String currentDateOfAudit;

    String currentDateAndTimeOfAudit;

    boolean dataMockedUp = false;

    String modifiedFrom = "";

    HierarchyDatapodObject datapodObject = null;


    /**
     * Initializes all the necessary parts to create an audit.
     * Create a connection to the database, mock up the data for the audit,create the audit through the UI and save the audit pdf to a place in memory.
     */
    @BeforeEach
    public void auditMockUpBuild(TestInfo testInfo)
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            String userName = user().getUsername();
            modifiedFrom = "07/01/2005";
            String csvSourceInfo ="";
            if(testInfo.toString().contains("Arguments"))
            {
                csvSourceInfo = testInfo.getDisplayName().split("Arguments: ")[1];
            }
            if(csvSourceInfo.equals("Change Modified Date")) // Change Modified Date comes from one of Summary Header test parameters: testFirstPageSummaryHeaderNoChangeToModifiedDate
            {
                modifiedFrom = "06/01/2005";
            }
            datapodObject =  AuditsDataMocking.mockUpForHeaderAndBookmarkTests(connection, userName);

            currentDateAndTimeOfAudit = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            auditIdentifier = "automatedAudit" + currentDateAndTimeOfAudit;
            currentDateOfAudit = DateAndTimeUtils.getCurrentDateMMddyyyy();
            auditPDF = processAuditHelperContentOnly(auditIdentifier, csvSourceInfo);

            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saved audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG - HALCYONST-9924  <br>
     * SUMMARY -  Verifies that the top left corner piece of every page is correctly displayed  <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void requestedByAuditIdentifierAuditDateLeftCornerpieceEachPageTest()
    {
        String firstName = user().getFirstname();
        String lastName = user().getLastname();
        String expectedRequestedBy = String.format("Requested by:%s, %s",lastName,firstName); //double check that the paragraph should be in
        int startOfTimeIndex = 10;
        int endOfDateIndex = 11;
        for (int i = 1; i < PDFUtils.getNumberOfPages(); i++)
        {
            PDFUtils.switchToPage(i);

            String actualTextFromArea = PDFUtils.getTextFromArea(PDFElements.LEFT_CORNERPIECE);
            String actualRequestByFromPDF = PDFUtils.getRequestByFromAuditPDF(actualTextFromArea);
            String actualAuditIdentifierFromPDF = PDFUtils.getAuditIdentifierFromAuditPDF(actualTextFromArea);
            String actualDateAndTimeFromPDF = PDFUtils.getDateAndTimeFromAuditPDF(actualTextFromArea);
            String actualTimeFromArea = actualDateAndTimeFromPDF.substring(actualDateAndTimeFromPDF.length() - startOfTimeIndex);
            String actualDateFromArea = actualDateAndTimeFromPDF.substring(0,actualDateAndTimeFromPDF.length() - endOfDateIndex);

            boolean doesRequestedByMatch = actualRequestByFromPDF.equals(expectedRequestedBy);
            boolean doesAuditIdentifierMatch = actualAuditIdentifierFromPDF.equals(auditIdentifier);
            boolean doesDateMatch = actualDateFromArea.equals(currentDateOfAudit);
            boolean doesTimeFormatMatch = DateAndTimeUtils.timeFormatValidation(actualTimeFromArea);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(doesRequestedByMatch, "Requested By user (Actual):" + actualRequestByFromPDF + " does not match the expected user:" + expectedRequestedBy),
                () -> Assertions.assertTrue(doesAuditIdentifierMatch, "Audit Identifier (Actual): " + actualAuditIdentifierFromPDF + " does not match the expected Audit Identifier: " + auditIdentifier),
                () -> Assertions.assertTrue(doesDateMatch, "Date (Actual): " + actualDateFromArea + "does not match the expected date: " + currentDateOfAudit),
                () -> Assertions.assertTrue(doesTimeFormatMatch, "Time format does not match the expected time format, actual time: " + actualTimeFromArea)
            );
        }
    }

    /**
     * STORY/BUG - HALCYONST-9924  <br>
     * SUMMARY -  Verifies that the top right corner piece of every page is correctly displayed  <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Short Same Paragraphs", "Not Same Paragraphs", "All Text", "Ignore PubTag Change"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pageInfoTextParagraphFormatRightCornerpieceTest(String paragraphFormat)
    {
        for (int currentPageNumber = 1; currentPageNumber < PDFUtils.getNumberOfPages(); currentPageNumber++)
        {
            PDFUtils.switchToPage(currentPageNumber);
            String expectedPageNumberText = String.format("Page %s of %s",currentPageNumber,PDFUtils.getNumberOfPages());

            String actualTextFromArea = PDFUtils.getTextFromArea(PDFElements.RIGHT_CORNERPIECE);
            String actualPageNumberText = PDFUtils.getPageTextFromAuditPDF(actualTextFromArea);
            String actualParagraphFormatText = PDFUtils.getParagraphFormatFromPDF(actualTextFromArea);

            boolean doesPageNumberTextMatch = actualPageNumberText.equals(expectedPageNumberText);
            boolean doesParagraphFormatTextMatch = actualParagraphFormatText.equals(paragraphFormat);

            Assertions.assertAll
            (
                    () -> Assertions.assertTrue(doesPageNumberTextMatch, "Page Number Text (Actual): " + actualPageNumberText + " does not match the expected Page Number Text: " + expectedPageNumberText),
                    () -> Assertions.assertTrue(doesParagraphFormatTextMatch, "Paragraph Format Text (Actual): " + actualParagraphFormatText  + " does not match the expected Paragraph Format Text: " + paragraphFormat)
            );
        }
    }

    /**
     * STORY/BUG - HALCYONST-9924  <br>
     * SUMMARY -  Verifies that the center heading of every page (except for the first page) is correctly displayed.
     * We are not testing the first page because that will have the summary header while all other pages have this center heading.<br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nodeInfoCurrentEffectiveDateVolumeOfNodeNodeUUIDCenterHeadingEachPageTest()
    {
        int currentIndexOfNode = 1; //index 0 is index of summary of audit
        int pageNumberOfFirstNode = PDFUtils.getPageNumberOfNode(currentIndexOfNode);
        PDDocumentOutline pdfOutline = PDFUtils.getPDFOutline();
        PDOutlineItem summaryHeaderOutlineItem = pdfOutline.getFirstChild();
        PDOutlineItem currentOutlineItem = summaryHeaderOutlineItem.getNextSibling();
        PDOutlineItem nextOutlineItem = currentOutlineItem.getNextSibling();
        PDPageDestination nextPageDestination = null;
        if(nextOutlineItem != null)
        {
            nextPageDestination = PDFUtils.getNextPageDestination(nextOutlineItem, currentIndexOfNode);
        }

        for (int currentPageNumber = pageNumberOfFirstNode; currentPageNumber < PDFUtils.getNumberOfPages(); currentPageNumber++)
        {
            int pageNumberOfNextNode = nextPageDestination.retrievePageNumber()+1;
            if(currentPageNumber == pageNumberOfNextNode && nextOutlineItem != null)
            {
                currentIndexOfNode++;
                currentOutlineItem = nextOutlineItem; // update curr item with next item
                nextOutlineItem = nextOutlineItem.getNextSibling();
                nextPageDestination = PDFUtils.getNextPageDestination(nextOutlineItem,currentIndexOfNode);
            }
            PDFUtils.switchToPage(currentPageNumber);
            String nodeUUID = AuditsDataMocking.getNodeUUID(currentIndexOfNode);

            String expectedNodeTitle = String.format("Iowa-%s", currentOutlineItem.getTitle());
            String expectedEffectiveDate = "Current Effective Date " + DateAndTimeUtils.reformatEffectiveDate(HierarchyDatabaseUtils.getLegisStartEffectiveDate(connection,nodeUUID))+ "-" + DateAndTimeUtils.reformatEffectiveDate(HierarchyDatabaseUtils.getLegisEndEffectiveDate(connection,nodeUUID));
            String expectedVolumeText = String.format("Vols: %s", volumeNumber);
            String expectedNodeUUID = String.format("Node Uuid: %s", nodeUUID);

            String actualTextFromArea = PDFUtils.getTextFromArea(PDFElements.PAGE_HEADER);
            String actualNodeTitle = PDFUtils.getNodeTitleFromPDF(actualTextFromArea);
            String actualCurrentEffectiveDate = PDFUtils.getCurrentEffectiveDateFromPDF(actualTextFromArea);
            String actualVolumeNumberText = PDFUtils.getVolumeNumberTextFromPDF(actualTextFromArea);
            String actualNodeUUIDText = PDFUtils.getNodeUUIDTextFromPDF(actualTextFromArea);

            Assertions.assertAll
            (
                    () -> Assertions.assertEquals(expectedNodeTitle,expectedNodeTitle, "Node Title (Actual): " +  actualNodeTitle + " does not match the expected Node Title: " + expectedNodeTitle),
                    () -> Assertions.assertEquals(expectedEffectiveDate,actualCurrentEffectiveDate, "Current Effective date (Actual): " +  actualCurrentEffectiveDate + " does not match the expected Current Effective Date: " + expectedEffectiveDate),
                    () -> Assertions.assertEquals(expectedVolumeText, actualVolumeNumberText, "Volume Number Text (Actual): " + actualVolumeNumberText + " does not match the expected Volume Number Text: " + expectedVolumeText),
                    () -> Assertions.assertEquals(expectedNodeUUID,actualNodeUUIDText, "Node UUID Text (Actual): " +  actualNodeUUIDText + " does not match the expected Node UUID Text: " + expectedNodeUUID)
            );
        }
    }

    /**
     * STORY/BUG - HALCYONST-9924  <br>
     * SUMMARY -  Verifies that the Summary Heading of the first page is correctly displayed  <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Change Modified Date", "No Change"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void testFirstPageSummaryHeaderNoChangeToModifiedDate(String expectedNodeStatus)
    {
        modifiedFrom = DateAndTimeUtils.reformatModifiedDate(modifiedFrom);
        PDFUtils.switchToPage(1);

        String expectedAuditIdentifier = auditIdentifier.toUpperCase();
        String expectedAuditType = "Audit by Document";
        String expectedJurisdictionInfo = "Jurisdiction: Iowa";
        String expectedCompareInfo = "Compare: Current WIP Document Against: most recent WIP document";
        String expectedSortByInfo = "Sort By: target";
        String expectedModifiedFrom = String.format("Modified From: %s to: Not selected", modifiedFrom);

        String actualTextFromArea = PDFUtils.getTextFromArea(PDFElements.SUMMARY_HEADER);
        String actualAuditIdentifier = PDFUtils.getAuditIdentifier(actualTextFromArea);
        String actualAuditType = PDFUtils.getAuditType(actualTextFromArea);
        String actualJurisdictionInfo = PDFUtils.getJurisdictionInfo(actualTextFromArea);
        String actualCompareInfo = PDFUtils.getCompareInfo(actualTextFromArea);
        String actualSortByInfo = PDFUtils.getSortByInfo(actualTextFromArea);
        String actualModifiedFrom = PDFUtils.getModifiedFrom(actualTextFromArea);

        String actualTextFromAreaPageHeader = PDFUtils.getTextFromArea(PDFElements.PAGE_HEADER);
        String expectedTextOfPageHeader = "\r\n";

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(expectedAuditIdentifier,actualAuditIdentifier, "Audit Identifier (Actual): " + actualAuditIdentifier + " does not match the expected Audit Identifier: " + expectedAuditIdentifier),
            () -> Assertions.assertEquals(expectedAuditType,actualAuditType, "Audit Type (Actual): " + actualAuditType + " does not match the expected Audit Type: " + expectedAuditType),
            () -> Assertions.assertEquals(expectedJurisdictionInfo,actualJurisdictionInfo, "Jurisdiction Info (Actual): " + actualJurisdictionInfo + " does not match the expected Jurisdiction Info: " + expectedJurisdictionInfo),
            () -> Assertions.assertEquals(expectedCompareInfo,actualCompareInfo, "Compare Info (Actual): "+ actualCompareInfo +" does not match the expected Compare Info: " + expectedCompareInfo),
            () -> Assertions.assertEquals(expectedSortByInfo,actualSortByInfo, "Sort By Info (Actual): " + actualSortByInfo + " does not match the expected Soft By Info: " + expectedSortByInfo),
            () -> Assertions.assertEquals(expectedModifiedFrom,actualModifiedFrom, "Modified From (Actual): " + actualModifiedFrom + " does not match the expected Modified From: " + expectedModifiedFrom),
            () -> Assertions.assertEquals(expectedTextOfPageHeader,actualTextFromAreaPageHeader, "Page Header text (Actual): " +  actualTextFromAreaPageHeader + " does not match the expected Page Header text which is no text")
        );
    }

    @AfterEach
    public void clearMockup()
    {
        closePDF();
        AuditsDataMocking.deleteDatapod(connection);
        FileUtils.deleteIfExists(auditPDF);
    }

    /**
     * This may become standard for running audits to have this method in each test but change one to have all checkboxes for a mega audit
     * May run into problems later if you want to run more than one section for an audit
     * Creates an audit of the mocked data
     * Creates the audit through the UI and saves the PDF within the commonfiles/TestFiles/AuditRedesignFiles/TempFiles
     */
    public File processAuditHelperContentOnly(String auditIdentifier, String paragraphFormat)
    {
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditByDocument();

        auditByDocumentPage().selectVolumeByVolumeNumber(volumeNumber);

        auditByDocumentPage().clickAddOneDocumentButton();

        //This section is subject to change based on what kind of report you want to run
        auditByDocumentPage().checkContentAudit();
        auditByDocumentPage().uncheckHierarchyContext();
        auditByDocumentPage().uncheckVersionsAuditStarting();
        modifiedFrom = auditByDocumentPage().getCutOffDate();
        auditByDocumentPage().uncheckValidations();
        auditByDocumentPage().uncheckSources();
        auditByDocumentPage().uncheckNODReport();
        auditByDocumentPage().setAuditIdentifier(auditIdentifier);

        switch (paragraphFormat)
        {
            case "Short Same Paragraphs":
                auditByDocumentPage().selectShortSameParagraphsRadioButton();
                break;

            case "Not Same Paragraphs":
                auditByDocumentPage().selectNotShortSameParagraphsRadioButton();
                break;

            case "All Text":
                auditByDocumentPage().selectAllTextRadioButton();
                break;

            case "Ignore PubTag Change":
                auditByDocumentPage().selectIgnorePubTagChangeRadioButton();
                break;

            default:
                auditByDocumentPage().selectShortSameParagraphsRadioButton();
                break;
        }
        auditByDocumentPage().selectClassificationRadioButton();


        auditByDocumentPage().clickNextButton();
        auditByDocumentPage().waitForPageLoaded();
        String keyword = HierarchyDatabaseUtils.getKeywordDefaultDisplayName(connection, nodeUUID);
        String valueOfNode = HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(0).getContentUUID(),ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String legisStartDate = HierarchyDatabaseUtils.getLegisStartEffectiveDate(connection, nodeUUID);

        auditByDocumentPage().selectDocumentByKeywordAndValueAndLegisEffectiveStartDate(keyword, valueOfNode, legisStartDate);
        auditByDocumentPage().clickAddOneDocumentButton();
        auditByDocumentPage().selectMostRecentWIPDocumentRadioButton();
        auditByDocumentPage().uncheckResearchReferencesCheckbox();
        auditByDocumentPage().clickProcessAuditButton();

        reportCentralFiltersPage().setReportIdentifier(auditIdentifier);
        reportCentralFiltersPage().setRequestersName("TCBA-BOT, TLE");

        reportCentralGridPage().waitForExistenceOfWorkflow("Audit Report", auditIdentifier, "TCBA-BOT, TLE");
        reportCentralGridPage().verifyFirstWorkflowFinishes();

        String auditUUID = reportCentralGridPage().getReportUUID();

        File auditPDF = PDFUtils.savePDFWithName(auditUUID, auditIdentifier);
        reportCentralGridPage().deleteReport();
        return auditPDF;
    }

}
