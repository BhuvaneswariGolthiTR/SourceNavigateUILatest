package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.desktop.DesktopUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.getNumberOfBookmarks;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.REGION_OF_NODE_VALUE_ON_FIRST_PAGE_OF_NODE_SECTION;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.Y_VALUE_OF_SPACE_IN_BETWEEN_BOOKMARKS;


public class BookmarkTests extends TestService
{
    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    boolean dataMockedUp = false;

    String modifiedFrom;

    String iowaContentNumber = "106";

    HierarchyDatapodObject datapodObject = null;

    @BeforeEach
    public void auditMockUpBuild()
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            String userName = user().getUsername();
            datapodObject = AuditsDataMocking.mockUpForHeaderAndBookmarkTests(connection, userName);
            auditIdentifier = "automatedAudit" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            processAuditHelperContentOnly(auditIdentifier);
            auditPDF = new File(String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saving audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG -  <br>
     * SUMMARY -  Verifies that the bookmarks take the user to the correct page in the PDF <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void bookmarksGoToRightPlacetest()
    {
        if (DesktopUtils.isDesktopSupported())
        {
            DesktopUtils.openFileOnMonitor(auditPDF);
            String expectedNodeValueRegionText = "J= " + HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(0).getContentUUID(), iowaContentNumber, connection);
            Robot robot = RobotUtils.getRobot();
            // We wait 15 seconds for the pdf to load so we can interact with it
            robot.delay(DateAndTimeUtils.FIFTEEN_SECONDS);
            robot.mouseMove(PDFElements.X_VALUE_OF_PDF_BOOKMARK_EXPANDER,PDFElements.Y_VALUE_OF_PDF_BOOKMARK_EXPANDER);
            RobotUtils.leftClickWithRobot();
            for(int i = 1; i < getNumberOfBookmarks();i++)
            {
                robot.delay(DateAndTimeUtils.ONE_SECOND);
                robot.mouseMove(PDFElements.X_VALUE_OF_PDF_BOOKMARKS,PDFElements.Y_VALUE_OF_PDF_BOOKMARKS + (i * Y_VALUE_OF_SPACE_IN_BETWEEN_BOOKMARKS));
                RobotUtils.leftClickWithRobot();
                String actualNodeValueRegionText = REGION_OF_NODE_VALUE_ON_FIRST_PAGE_OF_NODE_SECTION.text();
                Assertions.assertTrue(actualNodeValueRegionText.equals(expectedNodeValueRegionText), "Actual Node Value:" + actualNodeValueRegionText +
                        "does not match the expected node value:" + expectedNodeValueRegionText);
            }
        }
        PDFUtils.closePDF();
    }

    /**
     * STORY/BUG -  HALCYONST-13858 <br>
     * SUMMARY -  Verifies that the bookmarks have the correct name with respect to their node value <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void bookmarksCorrectTitleTest()
    {
        int startOfNodeValueSubstring = 2;
        PDOutlineItem currentBookmark = PDFUtils.getPDFOutline().getFirstChild().getNextSibling();
        for(int i = 1; i < PDFUtils.getNumberOfBookmarks(); i++)
        {
            String nodeUUID = AuditsDataMocking.getNodeUUID(i);
            String expectedNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, iowaContentNumber, connection);
            String actualNodeValue = currentBookmark.getTitle();
            actualNodeValue = actualNodeValue.substring(startOfNodeValueSubstring);
            Assertions.assertTrue(actualNodeValue.equals(expectedNodeValue), "Actual node value: " + actualNodeValue + "does not match the expected node value:" + expectedNodeValue);
            currentBookmark = currentBookmark.getNextSibling();
        }
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
        modifiedFrom = auditByDocumentPage().getCutOffDate();
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
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
        String auditUUID = reportCentralGridPage().getReportUUID();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        File auditPDF = PDFUtils.savePDFWithName(auditUUID, auditIdentifier);
        reportCentralGridPage().deleteReport();
        return auditPDF;
    }

    @AfterEach
    public void clearMockup()
    {
        closePDF();
        AuditsDataMocking.deleteDatapod(connection);
        //FileUtils.deleteIfExists(auditPDF);
    }
}