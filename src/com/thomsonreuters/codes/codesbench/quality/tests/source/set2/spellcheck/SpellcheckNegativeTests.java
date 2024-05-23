package com.thomsonreuters.codes.codesbench.quality.tests.source.set2.spellcheck;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;

public class SpellcheckNegativeTests extends TestService
{
    /**
     * STORY/BUGS - HALCYONST-6755/6670/5470 <br>
     * SUMMARY - Verifies after running a spellcheck report that the mnemonic of the SMP paragraph shows in the report.
     *           Verifies after running a spellcheck report that the mnemonics SCP, SCH, DTYPE, HG1, HG2 in 'Front', and
     *           SSTE, SSTEA, SSTER, SSTRT, SSTRF in 'Body' do not show in the report<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkMisspelledWordsInMnemonicsTest()
    {
        //Hardcoded APV doc for spellcheck tests
        //If data is missing, check the 'spellcheckAPVBackup' file under commonFiles > TestFiles
        String renditionUUID = "IF4BB8E306B0F11E291FECDF020830FCE";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for rendition
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setRenditionUuidFilter(renditionUUID);
        documentSearchFilterPage().clickRenditionUuidFilterButton();

        //create a spellcheck report on the rendition
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean spellcheckReportPageAppeared = renditionContextMenu().goToReportSpellcheckReport();
        Assertions.assertTrue(spellcheckReportPageAppeared, "Spell check page did not appear when it should");

        //Follow workflow link and get the pdf report
        spellcheckReportModal().clickWorkflowLink();
        boolean didWorkflowFinish = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(didWorkflowFinish, "Workflow didn't finish");
        workflowPropertiesPage().clickWorkflowPropertiesButton();
        String spellCheckReportLink = workflowPropertiesPage().getPropertyValue(PropertiesElements.Property.PDF_REPORT_LINK);
        workflowPropertiesPage().closeCurrentWindowIgnoreDialogue();

        //get spellcheck report as a string to be parsed
        File pdfReportFile = FileUtils.loadFileFromUrl(spellCheckReportLink);
        String content = PDFUtils.getPdfContent(pdfReportFile);

        //Parse Spellcheck report, check it does not contain SCPError, SCHError, DTYPEError, HGOneError, HGTwoError,
        //      SSTEError, SSTEAError, SSTERError, SSTRTError, SSTRFError words.
        boolean scpSpellTest = content.contains("SCPError");
        boolean schSpellTest = content.contains("SCHError");
        boolean dtypeSpellTest = content.contains("DTYPEError");
        boolean hgoneSpellTest = content.contains("HGOneError");
        boolean hgtwoSpellTest = content.contains("HGTwoError");
        boolean ssteSpellTest = content.contains("SSTEError");
        boolean ssteaSpellTest = content.contains("SSTEAError");
        boolean ssterSpellTest = content.contains("SSTERError");
        boolean sstrtSpellTest = content.contains("SSTRTError");
        boolean sstrfSpellTest = content.contains("SSTRFError");

        //Halcyonst-6670: Spellcheck report contains 'SMPError' and an SMP mnemonic
        boolean smpMnemonicTest = content.contains("\r\nSMP\r\n");
        boolean smpSpellTest = content.contains("SMPError");

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(scpSpellTest, "SCPError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(schSpellTest, "SCHError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(dtypeSpellTest, "DTYPEError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(hgoneSpellTest, "HGOneError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(hgtwoSpellTest, "HGTwoError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(ssteSpellTest, "SSTEError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(ssteaSpellTest, "SSTEAError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(ssterSpellTest, "SSTERError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(sstrtSpellTest, "SSTRTError did not appear in the spellcheck report"),
            () -> Assertions.assertFalse(sstrfSpellTest, "SSTRFError did not appear in the spellcheck report"),

            () -> Assertions.assertTrue(smpMnemonicTest, "SMP mnemonic did appear in the spellcheck report"),
            () -> Assertions.assertTrue(smpSpellTest, "SMPError did appear in the spellcheck report")
        );
    }

    /**
     * STORY: HALCYONST-6160/3492 <br>
     * SUMMARY: This test verifies the spellcheck report workflow does not kick off when syncing non APV or PREP document for a legal user <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void runSyncForNonApvOrPrepDocsDoesNotKickOffSpellcheckLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //filter for non APV or PREP doc
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("AD");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();

        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();

        boolean publishWorkflowStarted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbBillTextPublishing",
                "", "", user().getUsername());
        boolean syncWorkflowStarted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        workflowSearchPage().openFirstWorkflow();
        workflowDetailsPage().expandWorkflowProperties();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(syncWorkflowStarted,"The sync workflow started)"),
                () -> Assertions.assertTrue(publishWorkflowStarted,"The publishing workflow finished"),
                () -> Assertions.assertEquals(workflowPropertiesPage().getWorkflowPropertyValueByName("skipSpellcheckReport"),"true","The spellcheck report was skipped")
        );
    }

    /**
     * STORY: HALCYONST-6160/3492 <br>
     * SUMMARY: This test verifies the spellcheck report workflow does not kick off when syncing non APV or PREP document for a risk user <br>
     * USER: RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void runSyncForNonApvOrPrepDocsDoesNotKickOffSpellcheckRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //filter for risk doc
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Crown Dependencies");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("13083813");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();

        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();

        boolean publishWorkflowStarted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbRiskShelldocPublishing",
                "", "", user().getUsername());
        boolean syncWorkflowStarted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing",
                "", "", user().getUsername());
        workflowSearchPage().openFirstWorkflow();
        workflowDetailsPage().expandWorkflowProperties();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(syncWorkflowStarted,"The sync workflow finished"),
                () -> Assertions.assertTrue(publishWorkflowStarted,"The publishing workflow finished"),
                () -> Assertions.assertEquals(workflowPropertiesPage().getWorkflowPropertyValueByName("skipSpellcheckReport"),"true","The spellcheck report was skipped")
        );
    }

    /**
     * STORY: HALCYONST-6160/3492 <br>
     * SUMMARY: This test verifies the spellcheck report context menu item is not selectable for a non APV or PREP document <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void isSpellcheckDisabledOnNonApvPrep()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //filter for multiple non APV docs
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2019");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("AD");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateFooterToolsPage().selectAllOnPage();
        sourceNavigateGridPage().rightClickRenditions();

        boolean verifySpellcheckIsDisabled = renditionContextMenu().isContextMenuElementDisabled(RenditionContextMenuElements.reportSpellcheckReport);
        Assertions.assertTrue(verifySpellcheckIsDisabled,"The spellcheck report context menu element is not selectable");

        //filter for multiple APV and non APV docs
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("75");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateFooterToolsPage().selectAllOnPage();
        sourceNavigateGridPage().rightClickRenditions();

        boolean verifySpellcheckIsDisabledWithAPV = renditionContextMenu().isContextMenuElementDisabled(RenditionContextMenuElements.reportSpellcheckReport);
        Assertions.assertTrue(verifySpellcheckIsDisabledWithAPV,"The spellcheck report context menu element is not selectable");
    }

    /**
     * STORY/BUGS - HALCYONST- 6181/5374 <br>
     * SUMMARY - Verifies that spellcheck report is disabled when selecting multiple renditions in different content sets<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkSpellcheckReportDifferentContentSetsRestrictionTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filters for renditions, then mass selects them
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterClassNumber("4, 11, 6, 44");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateFooterToolsPage().clickSelectAllOnPage();

        sourceNavigateGridPage().rightClickRenditions();
        renditionContextMenu().openReport();
        boolean isSpellCheckReportDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.reportSpellcheckReport);
        Assertions.assertTrue(isSpellCheckReportDisabled, "Spellcheck report is disabled for multiple rendition selection across different content sets");
    }

    @AfterEach
    public void afterClass()
    {
        FileUtils.deleteFile(FileUtils.PDF_REPORT_FILE_PATH);
    }
}
