package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;

public class TaxTypeReportTests extends TestService
{
    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    boolean dataMockedUp = false;

    String modifiedFrom;

    HierarchyDatapodObject datapodObject = null;

    @BeforeEach
    public void auditMockUpBuild(TestInfo testInfo)
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            String userName = user().getUsername();
            String taxType = "";
            if(testInfo.getDisplayName().contains("Arguments: "))
            {
                if (testInfo.getDisplayName().split("Arguments: ")[1].equals("Tax Type and Product Tag"))
                {
                    taxType = "Tax Type and Product Tag";
                }
                else if (testInfo.getDisplayName().split("Arguments: ")[1].equals("Tax Type"))
                {
                    taxType = "Tax Type";
                }
                else
                {
                    taxType = "Product Tag";
                }
            }
            datapodObject = AuditsDataMocking.mockUpForTaxTypeReportTests(connection, userName, taxType);
            auditIdentifier = "automatedAudit" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            processAuditHelperContentOnly(auditIdentifier);
            auditPDF = new File(String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saving audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG -  N/A<br>
     * SUMMARY -  Verifies that the Tax Type report is displaying the correct text based on whether a
     * tax type and product tag is assigned: Tax Type Report Review Tax Type(s) for Checkpoint Node: (View/Tag) -- (ProductName) <br>
     * just a tax type is assigned:Tax Type Report Tax Type Assigned to node not tagged for Checkpoint: Tax Type has been assigned to a non-Checkpoint node, either remove the Tax Type Tag or Add a Checkpoint Tag, as appropriate. <br>
     * or just a product tag is assigned:Tax Type Report Tax Type Missing for Checkpoint node where Tax Type is required Checkpoint Node requires at least one Tax Type. Evaluate the correct Tax Types for assignment to this section or remove the Checkpoint Tag if improperly tagged for the Checkpoint product. <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Tax Type and Product Tag", "Tax Type", "Product Tag"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeReportTest(String taxType)
    {
        PDFUtils.switchToPage(2);
        String actualTextFromTaxTypeReportRegion = "";
        String expectedTextFromTaxTypeReportRegion = "";
        switch(taxType)
        {
            case "Tax Type and Product Tag":
                actualTextFromTaxTypeReportRegion = PDFUtils.getTextFromArea(PDFElements.TAX_TYPE_REPORT_SECTION );
                expectedTextFromTaxTypeReportRegion = "Tax Type Report\r\nReview Tax Type(s) for Checkpoint Node:\r\nBO -- Business and Occupation Tax\r\n";
                break;
            case "Tax Type":
                actualTextFromTaxTypeReportRegion = PDFUtils.getTextFromArea(PDFElements.TAX_TYPE_REPORT_SECTION );
                expectedTextFromTaxTypeReportRegion = "Tax Type Report\r\nTax Type Assigned to node not tagged for Checkpoint:\r\nTax Type has been assigned to a non-Checkpoint node, either remove the Tax Type Tag or Add a Checkpoint Tag, as appropriate.\r\n";
                break;
            case "Product Tag":
                actualTextFromTaxTypeReportRegion = PDFUtils.getTextFromArea(PDFElements.TAX_TYPE_REPORT_SECTION  );
                expectedTextFromTaxTypeReportRegion = "Tax Type Report\r\nTax Type Missing for Checkpoint node where Tax Type is required:\r\nCheckpoint Node requires at least one Tax Type. Evaluate the correct Tax Types for assignment to this section or remove the Checkpoint Tag if \r\nimproperly tagged for the Checkpoint product.\r\n";
                break;
        }
        Assertions.assertEquals(expectedTextFromTaxTypeReportRegion,actualTextFromTaxTypeReportRegion, "The expected text from the Tax Type Report region does not equal the actual text from the Tax Type Report region.");
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
        auditByDocumentPage().checkTaxTypeReport();

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
