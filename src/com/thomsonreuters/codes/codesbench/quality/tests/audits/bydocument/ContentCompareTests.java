package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMockingConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.desktop.DesktopUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sikuli.script.*;

import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.sikuli.SikuliUtils.NINETY_PERCENT_MINIMUM_SIMILARITY_SCORE;

public class ContentCompareTests extends TestService
{
    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    boolean dataMockedUp = false;

    HierarchyDatapodObject datapodObject = null;

    File fullPDF;

    int pageOfContentCompareSectionForCtabAndMarkupTests = 2;

    int pageOfDeletedAndAddedContentCompareSection = 3;

    int pageOfDeletedFootnoteContentCompareSection = 3;


    @BeforeEach
    public void auditMockUpBuild(TestInfo testInfo)
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            String userName = user().getUsername();
            if(testInfo.getDisplayName().contains("Arguments: "))
            {
                if (testInfo.getDisplayName().split("Arguments: ")[1].equals("Added and Deleted"))
                {
                    datapodObject = AuditsDataMocking.mockUpForContentCompareMiddleColumnTest(connection, userName);
                }
                else
                {
                    datapodObject = AuditsDataMocking.mockUpForMarkupsTest(connection, userName);
                }
            }
            else if(testInfo.getDisplayName().contains("footnoteDeletedMiddleColumnTest"))
            {
                datapodObject = AuditsDataMocking.mockUpForFootnoteDeleteTests(connection, userName);
            }
            else if(testInfo.getDisplayName().contains("ctabsDisplayingCorrectlyInContentCompareTest"))
            {
                datapodObject = AuditsDataMocking.mockUpForCTabTest(connection,userName);
            }
            else
            {
                datapodObject = AuditsDataMocking.mockUpForMarkupsTest(connection, userName);
            }
            auditIdentifier = "automatedAudit" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            processAuditHelperContentOnly(auditIdentifier);
            auditPDF = new File(String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saving audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG -  ADO 351381,HALCYONST-15350,HALCYONST-12900 <br>
     * SUMMARY -  Verifies that the markups in the audit appear correctly <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void markupsDisplayingCorrectlyInContentCompareTest()
    {
        if (DesktopUtils.isDesktopSupported())
        {
            PDFUtils.saveFileWithOnlyOnePageFromPDDocument(pageOfContentCompareSectionForCtabAndMarkupTests);
            fullPDF = auditPDF;
            auditPDF = new File(AUDIT_SINGLE_PAGE_LOCATION_PDF);
            DesktopUtils.openFileOnMonitor(auditPDF);
            RobotUtils.delayTenSeconds();
            Screen screen = new Screen();
            Match contentCompareMatch;
            Pattern contentComparePattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToContentComparePicture));
            contentComparePattern.similar(0.2);
            contentCompareMatch = screen.exists(contentComparePattern, 10);
            Assertions.assertTrue(contentCompareMatch.getScore() > NINETY_PERCENT_MINIMUM_SIMILARITY_SCORE, "Content Compare Markup Score is lower than 0.9 which probably means a markup or two is not displaying correctly");
        }
    }

    /**
     * STORY/BUG -  ADO 351669 <br>
     * SUMMARY -  Verifies that the ctabs in the audit appear correctly <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void ctabsDisplayingCorrectlyInContentCompareTest()
    {
        if (DesktopUtils.isDesktopSupported())
        {
            PDFUtils.saveFileWithOnlyOnePageFromPDDocument(pageOfContentCompareSectionForCtabAndMarkupTests);
            fullPDF = auditPDF;
            auditPDF = new File(AUDIT_SINGLE_PAGE_LOCATION_PDF);
            DesktopUtils.openFileOnMonitor(auditPDF);
            RobotUtils.delayTenSeconds();
            Screen screen = new Screen();
            Match ctabContentCompareMatch;
            Pattern ctabContentComparePattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToCtabsContentComparePicture));
            ctabContentComparePattern.similar(0.2);
            ctabContentCompareMatch = screen.exists(ctabContentComparePattern, 10);
            Assertions.assertTrue(ctabContentCompareMatch.getScore() > NINETY_PERCENT_MINIMUM_SIMILARITY_SCORE, "ctab Content Compare Score is lower than 0.9 which probably means a markup or two is not displaying correctly");
        }
    }

    /**
     * STORY/BUG -   <br>
     * SUMMARY -  Verifies that middle column displays the correct text based on how the text was modified <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Added and Deleted","Modified;Modified Undesignated;Added Undesignated;Same"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void middleColumnOfContentCompareSectionTest(String middleColumnToBeExpected)
    {
        List<String> listOfExpectedStringFromMiddleColumn;
        List<String> listOfActualStringsFromMiddleColumn;
        PDFUtils.switchToPage(pageOfDeletedAndAddedContentCompareSection);
        if(middleColumnToBeExpected.equals("Added and Deleted"))
        {
            String actualStringFromDeletedRegion = PDFUtils.getTextFromArea(DELETED_REGION_FROM_CONTENT_COMPARE);
            String actualStringFromAddedRegion = PDFUtils.getTextFromArea(ADDED_REGION_FROM_CONTENT_COMPARE);
            listOfActualStringsFromMiddleColumn = Arrays.asList
            (
                    actualStringFromDeletedRegion,
                    actualStringFromAddedRegion
            );
            listOfExpectedStringFromMiddleColumn = Arrays.asList(
                    "Deleted\r\n1a\r\n",
                    "Added\r\n2a\r\n"
            );
        }
        else
        {
            String actualStringFromModifiedRegion = PDFUtils.getTextFromArea(MODIFIED_REGION_FROM_CONTENT_COMPARE);
            String actualStringFromModifiedUndesignatedRegion = PDFUtils.getTextFromArea(MODIFIED_UNDESIGNATED_REGION_FROM_CONTENT_COMPARE);
            String actualStringFromAddedUndesignatedRegion = PDFUtils.getTextFromArea(ADDED_UNDESIGNATED_REGION_FROM_CONTENT_COMPARE);
            String actualStringFromSameRegion = PDFUtils.getTextFromArea(SAME_REGION_FROM_CONTENT_COMPARE);
            listOfActualStringsFromMiddleColumn = Arrays.asList
            (
                    actualStringFromModifiedRegion,
                    actualStringFromModifiedUndesignatedRegion,
                    actualStringFromAddedUndesignatedRegion,
                    actualStringFromSameRegion
            );
            listOfExpectedStringFromMiddleColumn = Arrays.asList
                    (
                            "Modified\r\n",
                            "Modified \r\nUndesignate\r\nd\r\n",
                            "Added \r\nUndesignate\r\nd\r\n",
                            "Same\r\n"
                    );
        }
        Assertions.assertTrue(ListUtils.areListsEqual(listOfActualStringsFromMiddleColumn,listOfExpectedStringFromMiddleColumn),"Actual list of Strings: " + listOfActualStringsFromMiddleColumn + "does not have all the same strings as" +
                "the expected list of strings:" + listOfExpectedStringFromMiddleColumn);
    }

    /**
     * STORY/BUG -  HALCYONST-12903<br>
     * SUMMARY -  Verifies that middle column displays deleted after deleting a footnote<br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void footnoteDeletedMiddleColumnTest()
    {
        PDFUtils.switchToPage(pageOfDeletedFootnoteContentCompareSection);
        String expectedDeletedMiddleColumnText = "Deleted\r\n";
        String actualDeletedMiddleColumnText = PDFUtils.getTextFromArea(DELETED_REGION_FOR_FOOTNOTE_DELETED);
        Assertions.assertEquals(expectedDeletedMiddleColumnText,actualDeletedMiddleColumnText,"Actual Middle column text:" + actualDeletedMiddleColumnText + "does not equal the expected middle column text:" + expectedDeletedMiddleColumnText);
    }

    public File processAuditHelperContentOnly(String auditIdentifier)
    {
        String nodeUUID = AuditsDataMocking.getNodeUUID(1);
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditByDocument();

        auditByDocumentPage().selectVolumeByVolumeNumber(volumeNumber);

        auditByDocumentPage().clickAddOneDocumentButton();

        //This section is subject to change based on what kind of report you want to run
        auditByDocumentPage().checkContentAudit();
        auditByDocumentPage().uncheckHierarchyContext();
        auditByDocumentPage().uncheckVersionsAuditStarting();
        auditByDocumentPage().checkValidations();
        auditByDocumentPage().uncheckSources();
        auditByDocumentPage().uncheckNODReport();
        auditByDocumentPage().setAuditIdentifier(auditIdentifier);
        auditByDocumentPage().selectShortSameParagraphsRadioButton();

        auditByDocumentPage().selectClassificationRadioButton();

        auditByDocumentPage().clickNextButton();
        auditByDocumentPage().waitForPageLoaded();

        String keyword = HierarchyDatabaseUtils.getKeywordDefaultDisplayName(connection, nodeUUID);
        String valueOfNode = HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(0).getContentUUID(), ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
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

    @AfterEach
    public void clearMockup()
    {
        closePDF();
        AuditsDataMocking.deleteDatapod(connection);
        if(testName.equals("ctabsDisplayingCorrectlyInContentCompareTest") || testName.equals("markupsDisplayingCorrectlyInContentCompareTest"))
        {
            FileUtils.deleteIfExists(fullPDF);
            FileUtils.deleteIfExists(auditPDF);
        }
        else
        {
            FileUtils.deleteIfExists(auditPDF);
        }
    }
}
