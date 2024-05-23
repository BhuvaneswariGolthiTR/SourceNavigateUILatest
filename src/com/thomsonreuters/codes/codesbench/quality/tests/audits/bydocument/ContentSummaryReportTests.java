package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;


import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
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
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.AUDIT_SINGLE_PAGE_LOCATION_PDF;

public class ContentSummaryReportTests extends TestService
{
        File auditPDF;
        File fullPDF;

        final String volumeNumber = "9999";

        String auditIdentifier;

        Connection connection;

        String currentDateOfAudit;

        String currentDateAndTimeOfAudit;

        boolean dataMockedUp = false;

        String modifiedFrom = "";

        HierarchyDatapodObject datapodObject = null;

        String iowaContentNumber = ContentSets.IOWA_DEVELOPMENT.getCode();

        String longNodeValue = "IABCDEFTHISISTESTINGLONGNODEVALUES9998499927581C999206999258EB11092AAEBESEDT";

        String expectedLongNodeValue = "IABCDEFTHISISTESTINGLONGNODEVALUES9998499927581C999206999258EB11092A...";

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
                String csvSourceInfo = "";
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
        * STORY/BUG -  HALCYONST-13408<br>
        * SUMMARY -  Verifies that the bookmarks take you to the first page of the node<br>
        * USER - Legal  <br>
        */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void linkInContentSummaryTakesYouToFirstPageOfNode()
        {
            String expectedNodeValueRegionText = "= " + HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(0).getContentUUID(), iowaContentNumber, connection);
            if(DesktopUtils.isDesktopSupported())
            {
                PDFUtils.saveFileWithOnlyOnePageFromPDDocument(1);
                fullPDF = auditPDF;
                auditPDF = new File(AUDIT_SINGLE_PAGE_LOCATION_PDF);
                DesktopUtils.openFileOnMonitor(auditPDF);
                RobotUtils.delayFifteenSeconds();
                RobotUtils.moveMouse(PDFElements.X_VALUE_OF_NODE_LINK_IN_CONTENT_SUMMARY, PDFElements.Y_VALUE_OF_NODE_LINK_IN_CONTENT_SUMMARY);
                RobotUtils.leftClickWithRobot();
                RobotUtils.delayOneSecond();
                String nodeValueRegionText = PDFElements.REGION_OF_NODE_VALUE_OF_FIRST_PAGE_OF_NODE_SECTION_FOR_LINK_TEST.text();
                Assertions.assertTrue(nodeValueRegionText.equals(expectedNodeValueRegionText) || nodeValueRegionText.replace('S','5').equals(expectedNodeValueRegionText),
                        "Actual Node Value:" + nodeValueRegionText +
                        "does not match the expected node value:" + expectedNodeValueRegionText);
            }
        }

        /**
         * STORY/BUG -  N/A <br>
        * SUMMARY -  Verifies that the Status, Start date, End date and volume of the node in the content summary is displaying correct based on the node's status, start date, end date and volume<br>
        * USER - Legal  <br>
        */
        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @CsvSource(value = {
                "Live", "Repeal", "Transfer", "Reserve"
        })
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void statusStartDateAndEndDateAndVolumeContentSummaryTest(String expectedNodeStatus)
        {
            PDFUtils.switchToPage(1);

            String actualTextFromStatusStartAndEndDateAndVolumeArea = PDFUtils.getTextFromArea(PDFElements.FIRST_NODE_FROM_SUMMARY_INFO_LISTING);
            Assertions.assertNotNull(actualTextFromStatusStartAndEndDateAndVolumeArea, "Text from Status, Start, End date and volume is null");
            String actualEndOfContentSummaryLine = PDFUtils.getTextFromArea(PDFElements.END_OF_CONTENT_NODE_SUMMARY_LINE);
            String actualNodeStatus = getNodeStatus(actualTextFromStatusStartAndEndDateAndVolumeArea);
            String actualNodeStartDate = getNodeStartDate(actualTextFromStatusStartAndEndDateAndVolumeArea);
            String actualNodeEndDate = getNodeEndDate(actualTextFromStatusStartAndEndDateAndVolumeArea);
            String actualNodeVolume = getNodeVolume(actualTextFromStatusStartAndEndDateAndVolumeArea);

            String expectedNodeStartDate = "12/31/2005";
            String expectedNodeEndDate = "9/25/2022";
            String expectedNodeVolume = "9999\r\n";
            String expectedEndOfContentSummaryLine = "END OF CONTENT AUDIT REPORT SUMMARY\r\n";

            Assertions.assertAll
            (
                    () -> Assertions.assertEquals(actualNodeStatus, expectedNodeStatus, "Actual Node Status:" + actualNodeStatus + " does not match the expected Node Status:" + expectedNodeStatus),
                    () -> Assertions.assertEquals(actualNodeStartDate, expectedNodeStartDate, "Actual Node Start Date:" + actualNodeStartDate + " does not match the expected Node Start Date:" + expectedNodeStartDate),
                    () -> Assertions.assertEquals(actualNodeEndDate, expectedNodeEndDate, "Actual Node End Date:" + actualNodeEndDate + " does not match the expected Node End Date:" + expectedNodeEndDate),
                    () -> Assertions.assertEquals(actualNodeVolume, expectedNodeVolume, "Actual Node Volume:" + actualNodeVolume + " does not match the expected Node Volume:" + expectedNodeVolume),
                    () -> Assertions.assertEquals(actualEndOfContentSummaryLine, expectedEndOfContentSummaryLine, "Actual End Of Content Summary Line:" + actualEndOfContentSummaryLine + " does not match the expected End Of Content Summary Line:" + expectedEndOfContentSummaryLine)
            );
        }
       /**
        * STORY/BUG -  HALCYONST-16593 <br>
        * SUMMARY -  Verifies that an ellipsis displays when a long node value is present in content summary<br>
        * USER - Legal  <br>
        */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void longNodeValueInContentSummaryTest()
        {
            boolean doesOneCOSDictionaryObjectContainLongNodeValueWithEllipsis = false;
            String expectedNameOfEntryIntoCOSSet = "MK";
            String expectedKeyForCOSDictionaryObject = "CA";
            PDFUtils.switchToPage(1);
            PDPage page = PDFUtils.getCurrentPDPage();
            List<PDAnnotation> annotations = PDFUtils.getPageAnnotations(page);
            for (PDAnnotation link : annotations)
            {
                Set<Map.Entry<COSName, COSBase>> cOSSet = link.getCOSObject().entrySet();
                for (Map.Entry<COSName, COSBase> cosNameCOSBaseEntry : cOSSet)
                {
                    if (cosNameCOSBaseEntry.getKey().getName().equals(expectedNameOfEntryIntoCOSSet) && cosNameCOSBaseEntry.getValue() instanceof COSDictionary)
                    {
                        COSDictionary cosDictionary = (COSDictionary) cosNameCOSBaseEntry.getValue();
                        if (cosDictionary.getString(expectedKeyForCOSDictionaryObject).equals("= " + expectedLongNodeValue))
                        {
                            doesOneCOSDictionaryObjectContainLongNodeValueWithEllipsis = true;
                        }
                    }
                }
            }
            Assertions.assertTrue(doesOneCOSDictionaryObjectContainLongNodeValueWithEllipsis,"No Node values was truncated with ellipsis at the end of the value");
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
        public File processAuditHelperContentOnly(String auditIdentifier, String nodeStatus)
        {
            String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
            String contentUUID = datapodObject.getSections().get(0).getContentUUID();
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUUID,"25-SEP-2022", connection);
            if (TestSetupEdge.testName.equals("linkInContentSummaryTakesYouToFirstPageOfNode"))
            {
                nodeStatus = "Live";
            }
            else if(TestSetupEdge.testName.equals("longNodeValueInContentSummaryTest"))
            {
                nodeStatus = "Live";
                HierarchyDatabaseUtils.setNodeValue(connection, nodeUUID, longNodeValue);
            }
            switch(nodeStatus)
            {
                case "Live":
                    HierarchyDatabaseUtils.setNodeToLiveStatus(contentUUID, iowaContentNumber, connection);
                    break;
                case "Repeal":
                    HierarchyDatabaseUtils.setNodeToRepealStatus(contentUUID, iowaContentNumber, connection);
                    break;
                case "Transfer":
                    HierarchyDatabaseUtils.setNodeToTransferStatus(contentUUID, iowaContentNumber, connection);
                    break;
                case "Reserve":
                    HierarchyDatabaseUtils.setNodeToReserveStatus(contentUUID, iowaContentNumber, connection);
                    break;
                default:
                    Assertions.fail("Unhandled status of node. Please insert case to handle this.");
                    break;
            }
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

            auditPDF = PDFUtils.savePDFWithName(auditUUID, auditIdentifier);
            reportCentralGridPage().deleteReport();
            return auditPDF;
        }

        public String getNodeStatus(String nodeInformation)
        {
            return nodeInformation.split(" ")[0];
        }

        public String getNodeStartDate(String nodeInformation)
        {
            return nodeInformation.split(" ")[1];
        }

        public String getNodeEndDate(String nodeInformation)
        {
            return nodeInformation.split(" ")[2];
        }

        public String getNodeVolume(String nodeInformation)
        {
            return nodeInformation.split(" ")[3];
        }

}

