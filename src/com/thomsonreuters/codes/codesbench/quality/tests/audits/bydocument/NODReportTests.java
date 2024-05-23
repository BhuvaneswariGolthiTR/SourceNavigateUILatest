package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMockingConstants.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;

public class NODReportTests extends TestService
{
    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    boolean dataMockedUp = false;

    String modifiedFrom;

    String iowaContentNumber = "106";

    HierarchyDatapodObject datapodObject = null;

    String userName;

    @BeforeEach
    public void auditMockUpBuild()
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            String nodSituation = TestSetupEdge.getDisplayName().split("Arguments: ")[1];
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            userName = user().getUsername();
            auditIdentifier = "automatedAudit" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            processAuditHelperContentOnly(auditIdentifier, nodSituation);
            auditPDF = new File(String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saving audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG - ADO 288732 <br>
     * SUMMARY - Verifies the NOD Report section displays correct text based on different NOD situations<br>
     *          Share: Current and prior version of node share an NOD container<br>
     *          Current Not; Prior Does: Current version does not have an NOD container; prior version does have an NOD container<br>
     *          Current Does; Prior Does Not:Current version has an NOD container; prior version does not have an NOD container<br>
     *          Not Shared:Current and prior version of nodes have different NOD containers<br>
     *          NOD Retained at Prior: Repealed section's NOD container was retained at prior version<br>
     *          Repealed Has NOD: Repealed section has NOD container; resolve<br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Share", "Current Not; Prior Does", "Current Does; Prior Does Not", "Not Shared", "NOD Retained at Prior", "Repealed Has NOD"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void NODReportTest(String nodSituation)
    {
        if(nodSituation.equals("NOD Retained at Prior"))
        {
            PDFUtils.switchToPage(2);
        }
        else
        {
            PDFUtils.switchToPage(3);
        }
        String actualTextFromNODReportRegion = "";
        String expectedTextFromNODReportRegion = "";
        PDFUtils.drawRec(PDFElements.TEXT_FROM_NOD_REPORT_NOT_SHARED);
        switch(nodSituation)
        {
            case "Share":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_SHARED);
                expectedTextFromNODReportRegion = "Current and prior version of node share an NOD container\r\n";
                break;
            case "Current Not; Prior Does":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_CURRENT_NOT_PRIOR_DOES);
                expectedTextFromNODReportRegion = "Current version does not have an NOD container; prior version does have an NOD container\r\n";
                break;
            case "Current Does; Prior Does Not":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_CURRENT_DOES_PRIOR_DOES_NOT );
                expectedTextFromNODReportRegion = "Current version has an NOD container; prior version does not have an NOD container\r\n";
                break;
            case "Not Shared":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_NOT_SHARED);
                expectedTextFromNODReportRegion = "Current and prior version of nodes have different NOD containers\r\n";
                break;
            case "NOD Retained at Prior":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_NOD_RETAINED_AT_PRIOR);
                expectedTextFromNODReportRegion = "Repealed section's NOD container was retained at prior version\r\n";
                break;
            case "Repealed Has NOD":
                actualTextFromNODReportRegion = PDFUtils.getTextFromArea(PDFElements.TEXT_FROM_NOD_REPORT_REPEALED_HAS_NOD);
                expectedTextFromNODReportRegion = "Repealed section has NOD container; resolve\r\n";
                break;
        }
        Assertions.assertEquals(expectedTextFromNODReportRegion,actualTextFromNODReportRegion, "Expected text:" + expectedTextFromNODReportRegion + "from the NOD Report region is not the same as the actual text from the region:" + actualTextFromNODReportRegion);
    }

    public File processAuditHelperContentOnly(String auditIdentifier, String nodSituation)
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, iowaContentNumber, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().selectNodes(value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        switch (nodSituation)
        {
            case "Share":
                siblingMetadataContextMenu().binFunctionsAddToBin();
                siblingMetadataPage().selectNodes(value);
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                value = "SHARE";
                siblingMetadataContextMenu().binFunctionsRelocateBinAsSibling();
                relocateBinAsChildSiblingPage().setNewValue(value);
                relocateBinAsChildSiblingPage().setEffectiveStartDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
                relocateBinAsChildSiblingPage().clickCreateDispDerivLinkOnlyNoTransferNodeRadioButton();
                relocateBinAsChildSiblingPage().setQuickLoad();
                relocateBinAsChildSiblingPage().clickShareNodsForwardRadioButton();
                relocateBinAsChildSiblingPage().clickOkButton();
                yourWorkflowHasBeenCreatedPage().clickClose();
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodes(value);
                break;
            case "Current Not; Prior Does":
                siblingMetadataContextMenu().binFunctionsAddToBin();
                siblingMetadataPage().selectNodes(value);
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                value = "CURRENTNOT";
                siblingMetadataContextMenu().binFunctionsRelocateBinAsSibling();
                relocateBinAsChildSiblingPage().setNewValue(value);
                relocateBinAsChildSiblingPage().setEffectiveStartDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
                relocateBinAsChildSiblingPage().clickCreateDispDerivLinkOnlyNoTransferNodeRadioButton();
                relocateBinAsChildSiblingPage().setQuickLoad();
                relocateBinAsChildSiblingPage().clickRetainNodsOnlyAtPriorNodeRadioButton();
                relocateBinAsChildSiblingPage().clickOkButton();
                yourWorkflowHasBeenCreatedPage().clickClose();
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodes(value);
                break;
            case "Current Does; Prior Does Not":
                siblingMetadataContextMenu().binFunctionsAddToBin();
                siblingMetadataPage().selectNodes(value);
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                siblingMetadataContextMenu().binFunctionsCopyBinAsSibling();
                copyPage().setCreateCopyOfNODs();
                copyPage().clickQuickLoadOk();
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                siblingMetadataContextMenu().binFunctionsAddToBin();
                value = "PRIORNOT";
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                siblingMetadataContextMenu().binFunctionsRelocateBinAsSibling();
                relocateBinAsChildSiblingPage().setNewValue(value);
                relocateBinAsChildSiblingPage().setEffectiveStartDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
                relocateBinAsChildSiblingPage().clickCreateDispDerivLinkOnlyNoTransferNodeRadioButton();
                relocateBinAsChildSiblingPage().setQuickLoad();
                relocateBinAsChildSiblingPage().clickMoveNodsFowardRadioButton();
                relocateBinAsChildSiblingPage().clickOkButton();
                yourWorkflowHasBeenCreatedPage().clickClose();
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodes(value);
                break;
            case "Not Shared":
                siblingMetadataContextMenu().binFunctionsAddToBin();
                siblingMetadataPage().selectNodes(value);
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                value = "NOTSHARED";
                siblingMetadataContextMenu().binFunctionsRelocateBinAsSibling();
                relocateBinAsChildSiblingPage().setNewValue(value);
                relocateBinAsChildSiblingPage().setEffectiveStartDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
                relocateBinAsChildSiblingPage().clickCreateDispDerivLinkOnlyNoTransferNodeRadioButton();
                relocateBinAsChildSiblingPage().setQuickLoad();
                relocateBinAsChildSiblingPage().clickCopyNodsForwardRadioButton();
                relocateBinAsChildSiblingPage().clickOkButton();
                yourWorkflowHasBeenCreatedPage().clickClose();
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodes(value);
                break;
            case "NOD Retained at Prior":
                siblingMetadataContextMenu().hierarchyFunctionsRepeal();
                repealPage().setCurrentDateAsEffectiveStartDate();
                repealPage().clickQuickLoad();
                repealPage().clickOK();
                AutoITUtils.verifyAlertTextContainsAndAccept(true, "THIS IS NOT AN ERROR, just a notice that the selected node");
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodeByValueAndStartDate(value, DateAndTimeUtils.getCurrentDateMMddyyyy());
                break;
            case "Repealed Has NOD":
                siblingMetadataContextMenu().hierarchyFunctionsRepeal();
                repealPage().clickShareNodsForward();
                repealPage().setCurrentDateAsEffectiveStartDate();
                repealPage().clickQuickLoad();
                repealPage().clickOK();
                AutoITUtils.verifyAlertTextContainsAndAccept(true, "THIS IS NOT AN ERROR, just a notice that the selected node");
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectNodeByValueAndStartDate(value, DateAndTimeUtils.getCurrentDateMMddyyyy());
                break;
        }

        String nodeUUIDOfNewNode = siblingMetadataPage().getSelectedNodeUuid();
        String contentUUIDOfNewNode = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUUIDOfNewNode,connection);
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion = 1;
        if (!(nodSituation.equals("Repealed Has NOD") || nodSituation.equals("NOD Retained at Prior")))
        {
            HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUIDOfNewNode, newWipVersion, userName, textForNODReportTest, newHeadText,
                    106, connection);
            long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

            HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUIDOfNewNode, connection);
            HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUIDOfNewNode, connection);
            HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUIDOfNewNode, contentUUIDOfNewNode,
                    materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                    0, 1, 1, 0, 0, iowaCode, connection);

            auditId++;
            HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUIDOfNewNode, contentUUIDOfNewNode,
                    materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                    0, 0, 1, 0, 0, iowaCode, connection);
        }
        hierarchyNavigatePage().switchToHierarchyEditPage();
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
        auditByDocumentPage().checkNODReport();
        auditByDocumentPage().setAuditIdentifier(auditIdentifier);
        auditByDocumentPage().selectShortSameParagraphsRadioButton();

        auditByDocumentPage().selectClassificationRadioButton();

        auditByDocumentPage().clickNextButton();
        auditByDocumentPage().waitForPageLoaded();

        String keyword = HierarchyDatabaseUtils.getKeywordDefaultDisplayName(connection, nodeUUIDOfNewNode);
        String legisStartDate = HierarchyDatabaseUtils.getLegisStartEffectiveDate(connection, nodeUUIDOfNewNode);

        auditByDocumentPage().selectDocumentByKeywordAndValueAndLegisEffectiveStartDate(keyword, value, legisStartDate);
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
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            disconnect(connection);
        }
        try
        {
            Files.deleteIfExists(auditPDF.toPath());
        }
        catch (IOException e)
        {
            logger.information("File was not deleted");
            e.printStackTrace();
        }
    }
}
