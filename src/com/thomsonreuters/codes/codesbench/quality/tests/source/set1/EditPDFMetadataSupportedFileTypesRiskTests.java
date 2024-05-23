package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

import java.util.List;

public class EditPDFMetadataSupportedFileTypesRiskTests extends TestService
{

    SourceDatapodObject datapodObject;

    /**
     * STORY: N/A <br>
     * SUMMARY: Verifies Unsupported File Types for  <br>
     * USER: Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void editPDFMetadataUnsupportedFileVerificationRiskTest()
    {
        datapodObject = SourceDataMockingNew.CrownDependencies.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObject.getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstItem();
        clamshellPage().openEditPdfMetadata();

        List<String> fileTypesFalselySupported = editPDFMetadataPage().verifyEditPDFMetadataUnsupportedFileTypes();

        boolean fileTypesFalselySupportedBool = fileTypesFalselySupported.isEmpty();
        Assertions.assertTrue(fileTypesFalselySupportedBool, "File type(s) did trigger a file type unsupported message: "
              + fileTypesFalselySupported);
    }

    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void editPDFMetadataSupportedFileVerificationRiskTest()
    {
        datapodObject = SourceDataMockingNew.CrownDependencies.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObject.getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstItem();
        clamshellPage().openEditPdfMetadata();

        List<String> fileTypesSupported = editPDFMetadataPage().verifyEditPDFMetadataSupportedFileTypes();

        boolean fileTypeSupportedBool = fileTypesSupported.isEmpty();
        Assertions.assertTrue(fileTypeSupportedBool, "File type(s) triggered a file type unsupported message: " + fileTypesSupported);
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
