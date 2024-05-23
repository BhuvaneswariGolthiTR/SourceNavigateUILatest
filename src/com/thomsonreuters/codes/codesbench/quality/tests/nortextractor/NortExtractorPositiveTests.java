package com.thomsonreuters.codes.codesbench.quality.tests.nortextractor;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nortextractor.NortExtractorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.CsvFileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class NortExtractorPositiveTests extends TestService
{
    /**
     * SUMMARY: This test verifies everything appears on the Nort Extractor home page <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nortExtractorUiTest()
    {
        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        boolean pageHeaderIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.PAGE_HEADER_XPATH);
        boolean idListInputIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.ID_LIST_INPUT_XPATH);
        boolean targetConversionButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.TARGET_CONVESION_RADIO_BUTTON_XPATH);
        boolean statuteHistoryIdsButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.STATUTE_HISTORY_IDS_RADIO_BUTTON_XPATH);
        boolean sourceConversionTaxTypeButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_TAX_TYPE_RADIO_BUTTON_XPATH);
        boolean sourceConversionAmendmentNotesIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_AMENDMENT_NOTES_RADIO_BUTTON_XPATH);
        boolean submitButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.submitButton);
        boolean resetButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.resetButton);
        boolean logoutButtonIsDisplayed = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.logoutButton);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(pageHeaderIsDisplayed, "Nort Extractor page header appears"),
            () -> Assertions.assertTrue(idListInputIsDisplayed, "id list input appears"),
            () -> Assertions.assertTrue(targetConversionButtonIsDisplayed, "Target Conversion button appears"),
            () -> Assertions.assertTrue(statuteHistoryIdsButtonIsDisplayed, "Statute History IDs button appears"),
            () -> Assertions.assertTrue(sourceConversionTaxTypeButtonIsDisplayed, "Source Conversion - Tax Type button appears"),
            () -> Assertions.assertTrue(sourceConversionAmendmentNotesIsDisplayed, "Source Conversion - Amendment Notes button appears"),
            () -> Assertions.assertTrue(submitButtonIsDisplayed, "Submit button appears"),
            () -> Assertions.assertTrue(resetButtonIsDisplayed, "Reset button appears"),
            () -> Assertions.assertTrue(logoutButtonIsDisplayed, "Log Out button appears")
        );
    }

    /**
     * SUMMARY: This test verifies everything appears when the selecting the different buttons on the Nort Extractor homepage <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nortExtractorNavigationUiTest()
    {
        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        //click target conversion and verify nothing changes
        nortExtractorPage().clickTargetConversionRadioButton();
        boolean sourceConversionTaxTypeMenuIsHidden = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_TAX_TYPE_HIDDEN_MENU_XPATH);
        boolean sourceConversionAmendmentNotesMenuIsHidden = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_AMENDMENT_NOTES_HIDDEN_MENU_XPATH);

        //click statute history ids and verify nothing changes
        nortExtractorPage().clickStatuteHistoryIdsRadioButton();
        boolean sourceConversionTaxTypeMenuIsHiddenStatuteHistory = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_TAX_TYPE_HIDDEN_MENU_XPATH);
        boolean sourceConversionAmendmentNotesMenuIsHiddenStatuteHistory = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_AMENDMENT_NOTES_HIDDEN_MENU_XPATH);

        //click source conversion tax type and verify menu appears
        nortExtractorPage().clickSourceConversionTaxTypeRadioButton();
        boolean sourceConversionAmendmentNotesMenuIsHiddenTaxType = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_AMENDMENT_NOTES_HIDDEN_MENU_XPATH);
        boolean environmentDropdownAppearsForTaxType = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.TAX_TYPE_ENVIRONMENT_DROPDOWN_XPATH);
        boolean conentSetDropdownAppearsForTaxType = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH);
        boolean docNameInputAppearsForTaxType = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.DOC_NAME_INPUT_XPATH);

        //click source conversion amendment notes and verify menu appears
        nortExtractorPage().clickSourceConversionAmendmentNotesRadioButton();
        boolean sourceConversionTaxTypeMenuIsHiddenAmendmentNotes = !nortExtractorPage().isElementDisplayed(NortExtractorPageElements.SOURCE_CONVERSION_TAX_TYPE_HIDDEN_MENU_XPATH);
        boolean environmentDropdownAppearsForAmendmentNotes = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH);
        boolean conentSetDropdownAppearsForAmendmentNotes = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.AMENDMENT_NOTES_CONTENT_SET_DROPDOWN_XPATH);
        boolean docNameInputAppearsForAmendmentNotes = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_DOC_NAME_INPUT_XPATH);
        boolean attorneyTopicGapCheckboxAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_CHECKBOX_XPATH);
        boolean repealDocGapCheckboxAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.REPEAL_DOC_GAP_CHECKBOX_XPATH);
        boolean newCodeGapCheckboxAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.NEW_CODE_GAP_CHECKBOX_XPATH);
        boolean attorneyTopicGapChooseFileButtonAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_CHOOSE_FILE_XPATH);
        boolean repealDocGapChooseFileButtonAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.REPEAL_DOC_GAP_CHOOSE_FILE_XPATH);
        boolean newCodeGapChooseFileButtonAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.NEW_CODE_GAP_CHOOSE_FILE_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sourceConversionTaxTypeMenuIsHidden, "The source conversion tax type menus appeared"),
            () -> Assertions.assertTrue(sourceConversionAmendmentNotesMenuIsHidden, "The source conversion amendment notes menus appeared"),
            () -> Assertions.assertTrue(sourceConversionTaxTypeMenuIsHiddenStatuteHistory, "The source conversion tax type menus appeared when clicking statute history button"),
            () -> Assertions.assertTrue(environmentDropdownAppearsForTaxType, "The environment dropdown appears for Source Conversion - Tax Type"),
            () -> Assertions.assertTrue(conentSetDropdownAppearsForTaxType, "The content set dropdown appears for Source Conversion - Tax Type"),
            () -> Assertions.assertTrue(docNameInputAppearsForTaxType, "The doc name input appears for Source Conversion - Tax Type"),
            () -> Assertions.assertTrue(sourceConversionAmendmentNotesMenuIsHiddenStatuteHistory, "The source conversion amendment notes menus appeared when they should not when clicking statute history button"),
            () -> Assertions.assertTrue(sourceConversionAmendmentNotesMenuIsHiddenTaxType, "The source conversion amendment notes menus appeared when they should not when clicking source conversion tax type button"),
            () -> Assertions.assertTrue(sourceConversionTaxTypeMenuIsHiddenAmendmentNotes, "The source conversion tax type menus appeared didn't appear when they should when clicking source conversion amendment notes button"),
            () -> Assertions.assertTrue(environmentDropdownAppearsForAmendmentNotes, "The environment dropdown appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(conentSetDropdownAppearsForAmendmentNotes, "The content set dropdown appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(docNameInputAppearsForAmendmentNotes, "The doc name input appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(attorneyTopicGapCheckboxAppears, "The Attorney Topic Gap checkbox appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(repealDocGapCheckboxAppears, "The Repeal Doc Gap checkbox appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(newCodeGapCheckboxAppears, "The New Code Gap checkbox appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(attorneyTopicGapChooseFileButtonAppears, "The Attorney Topic Gap Choose File button appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(repealDocGapChooseFileButtonAppears, "The Repeal Doc Gap Choose File button appears for Source Conversion - Amendment Notes"),
            () -> Assertions.assertTrue(newCodeGapChooseFileButtonAppears, "The New Code Gap Choose File button appears for Source Conversion - Amendment Notes")
        );
    }

    /**
     * SUMMARY: This test verifies the source conversion functionality of source conversion tax types <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void sourceConversionTaxTypeTest()
    {
        String fileName = "T0SLCODAF:8910.1";
        String currentDate = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String docName = "TaxTypesTest" + currentDate;

        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        nortExtractorPage().clickSourceConversionTaxTypeRadioButton();
        nortExtractorPage().setIdList(fileName);
        nortExtractorPage().setEnvironmentDropdown("UAT");
        nortExtractorPage().setContentSetDropdown("Iowa (Development)");
        nortExtractorPage().setDocName(docName);
        nortExtractorPage().clickSubmit();

        boolean extractedFileLinkAppears = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_URL_XPATH);
        boolean convertedFilesLinkAppears = nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_URL_XPATH);
        boolean checkWorkflowLinkAppears = nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_URL_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(extractedFileLinkAppears, "The extracted file link appears"),
            () -> Assertions.assertTrue(convertedFilesLinkAppears, "The converted files link appears"),
            () -> Assertions.assertTrue(checkWorkflowLinkAppears, "The check workflows link appears")
        );

        nortExtractorPage().clickCheckWorkflowLink();
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "The CwbDataCaptureComEntry workflow finished");

        homePage().goToHomePage();
        loginPage().logIn();

        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docName);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        Assertions.assertEquals(1, sourceNavigateFooterToolsPage().getResults(), "1 com doc was created with the correct doc name");

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();
        sourcePage().publicViewAllCols();

        List<String> taxTypes = sourceNavigateGridPage().getAllTaxTypeAdd();
        boolean firstTaxTypeIsProp = taxTypes.get(0).equals("PROP");
        Assertions.assertTrue(firstTaxTypeIsProp, "The first delta has a tax type of PROP");
        taxTypes.remove(0);
        boolean allDeltasHaveTaxTypes = taxTypes.stream().allMatch(validationItem -> validationItem.equals("GEN"));
        Assertions.assertTrue(allDeltasHaveTaxTypes, "All but the first deltas have a tax type of GEN");

        //cleanup
        sourceNavigateTabsPage().goToRenditionTab();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        sourceNavigateGridPage().deleteFirstRendition();
        Assertions.assertEquals(0, sourceNavigateFooterToolsPage().getResults(), "The rendition was deleted");
    }

    /**
     * STORY: ADO 290419 <br>
     * SUMMARY: This test verifies every expected content set is in the content set dropdown including admin development code content sets <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyContentSetListTest()
    {
        final List<String> expectedConentSets = Arrays.asList("'04 Iowa Integration Old", "'06 Iowa Integration", "Alabama (Development)", "Alabama Admin Code (Development)", "Alaska (Development)", "Alaska Admin Code (Development)",
            "Arizona (Development)", "Arizona Admin Code (Development)", "Arkansas (Development)", "Arkansas Admin Code (Development)", "BPI 11", "BPI 14", "BPI 15", "BPI 16", "BPI 17", "BPI 18", "BPI 19",
            "BPI 20", "BPI 21", "BPI 22", "BPI 23", "BPI 24", "BPI 25", "BPI 26", "Barbados Statutes and Regulations (Development)", "California (Development)", "California Admin Code (Development)",
            "Canada Alberta (Development)", "Canada British Columbia (Development)", "Canada Delete", "Canada Delete", "Canada Federal (Development)", "Canada Federal Tax (Development)", "Canada Manitoba (Development)",
            "Canada New Brunswick (Development)", "Canada Newfoundland and Labrador (Development)", "Canada Northwest Territories (Development)", "Canada Nova Scotia (Development)", "Canada Nunavut (Development)",
            "Canada Ontario (Development)", "Canada Prince Edward Island (Development)", "Canada Quebec (Development)", "Canada Saskatchewan (Development)", "Canada Test 1", "Canada Test 2", "Canada Yukon (Development)",
            "Cayman Islands Admin Code (Development)", "Cayman Islands Statutes (Development)", "Cherokee Nation (Development).", "Code of Fed Regs (Development)", "Colorado (Development)", "Colorado Admin Code (Development)",
            "Connecticut (Development)", "Connecticut Admin Code (Development)", "DO NOT USE Pop Name (development)", "Delaware (Development)", "Delaware Admin Code (Development)", "District Of Columbia (Development)",
            "District Of Columbia Admin Code (Development)", "Florida (Development)", "Florida Admin Code (Development)", "Georgia (Development)", "Georgia Admin Code (Development)", "Guam (Develpment)", "Guam Admin Code (Development)",
            "Hawaii (Development)", "Hawaii Admin Code (Development)", "Idaho (Development)", "Idaho Admin Code (Development)", "Illinois (Development)", "Illinois Admin Code (Development)", "Indiana (Development)",
            "Indiana Admin Code (Development)", "Iowa (Development)", "Iowa Admin Code (Development)", "Kansas Admin Code (Development)", "Kansas(Development)", "Kentucky (Development)", "Kentucky Admin Code (Development)",
            "Louisiana (Development)", "Louisiana Admin Code (Development)", "Maine (Development)", "Maine Admin Code (Development)", "Maryland (Development)", "Maryland Admin Code (Development)", "Mashantucket Pequot Tribe",
            "Mashantucket Pequot Tribe (Development)", "Massachusetts (Development)", "Massachusetts Admin Code (Development)", "Michigan (Development)", "Michigan Admin Code (Development)", "Military Court of Criminal Appeals Air Force",
            "Military Court of Criminal Appeals Air Force(Development)", "Military Court of Criminal Appeals Army", "Military Court of Criminal Appeals Army(Development)", "Military Court of Criminal Appeals Coast Guard",
            "Military Court of Criminal Appeals Coast Guard(Development)", "Military Court of Criminal Appeals Joint Rules", "Military Court of Criminal Appeals Joint Rules(Development)", "Military Court of Criminal Appeals Navy Marines",
            "Military Court of Criminal Appeals Navy Marines(Development)", "Minnesota (Development)", "Minnesota Admin Code (Development)", "Mississippi (Development)", "Mississippi Admin Code (Development)", "Missouri (Development)",
            "Missouri Admin Code (Development)", "Montana (Development)", "Montana Admin Code (Development)", "Muscogee (Creek) Nation Tribe", "Muscogee (Creek) Nation Tribe (Development)", "Navajo Nation Tribe", "Navajo Nation Tribe (Development)",
            "Nebraska (Development)", "Nebraska Admin Code (Development)", "Nevada (Development)", "Nevada Admin Code (Development)", "New Hampshire (Development)", "New Hampshire Admin Code (Development)", "New Jersey (Development)",
            "New Jersey Admin Code (Development)", "New Mexico (Development)", "New Mexico Admin Code (Development)", "New York (Development)", "New York Admin Code (Development)", "North Carolina (Development)",
            "North Carolina Admin Code (Development)", "North Dakota (Development)", "North Dakota Admin Code (Development)", "Ohio (Development)", "Ohio Admin Code (Development)", "Oklahoma (Development)", "Oklahoma Admin Code (Development)",
            "Oregon (Development)", "Oregon Admin Code (Development)", "Pennsylvania (Development)", "Pennsylvania Admin Code (Development)", "Puerto Rico (Development)", "Puerto Rico Admin Code (Development)", "Regression Test (Development)",
            "Reserved for Tribal Codes 2 (Development)", "Reserved for Tribal Codes 3 (Development)", "Reserved for Tribal Codes 4 (Development)", "Reserved for Tribal Codes 5 (Development)", "Rhode Island (Development)",
            "Rhode Island Admin Code (Development)", "SEC Rules (Development)", "SMG United Kingdom (Development)", "Shakopee Mdewakanton Sioux Community (Development).", "Snap Shot Test (Development)", "South Carolina (Development)",
            "South Carolina Admin Code (Development)", "South Dakota (Development)", "South Dakota Admin Code (Development)", "Super Docs (Development)", "TRAIN01-TEST", "TRAIN02-TEST", "TRAIN03-TEST", "TRAIN04-TEST", "TRAIN05-TEST",
            "TRAIN06-TEST", "TRAIN07-TEST", "TRAIN08-TEST", "TRAIN09-TEST", "TRAIN10-TEST", "TRAIN11-TEST", "TRAIN12-TEST", "TRAIN13-TEST", "TRAIN14-TEST", "TRAIN15-TEST", "TRAIN16-TEST", "TRAIN17-TEST", "TRAIN18-TEST", "TRAIN19-TEST",
            "TRAIN20-TEST", "TRAIN21-TEST", "TRAIN22-TEST", "TRAIN23-TEST", "TRAIN24-TEST", "TRAIN25-TEST", "TRAIN26-TEST", "TRAIN27-TEST", "TRAIN28-TEST", "Tennessee (Development)", "Tennessee Admin Code (Development)", "Texas (Development)",
            "Texas Admin Code (Development)", "USCA(Development)", "Uniform Laws Annotated (Development)", "Utah (Development)", "Utah Admin Code (Development)", "Vermont (Development)", "Vermont Admin Code (Development)",
            "Virgin Islands (Development)", "Virgin Islands Admin Code (Development)", "Virginia (Development)", "Virginia Admin Code (Development)", "Washington (Development)", "Washington Admin Code (Development)", "West Virginia (Development)",
            "West Virginia Admin Code (Development)", "Wisconsin (Development)", "Wisconsin Admin Code (Development)", "Wyoming (Development)", "Wyoming Admin Code (Development)");

        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        nortExtractorPage().clickSourceConversionTaxTypeRadioButton();
        nortExtractorPage().setEnvironmentDropdown("UAT");
        nortExtractorPage().clickContentSetDropdown();
        boolean allContentSetsAppear = nortExtractorPage().getAllContentSets().containsAll(expectedConentSets);
        Assertions.assertTrue(allContentSetsAppear, "All expected content sets appear");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: This test verifies the source conversion amendment notes functionality <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void sourceConversionAmendmentNotesTest()
    {
        String fileName = "T0SLCODAF:8910.1";
        String currentDate = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String docName = "AmendmentNotesTest" + currentDate;

        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        nortExtractorPage().setIdList(fileName);
        nortExtractorPage().clickStatuteHistoryIdsRadioButton();
        nortExtractorPage().clickSubmit();

        String convertedFileName = nortExtractorPage().getConvertedFileName();
        nortExtractorPage().clickConvertedFilesLink(convertedFileName);
        nortExtractorPage().clickBack();

        nortExtractorPage().clickSourceConversionAmendmentNotesRadioButton();
        nortExtractorPage().setIdList(fileName);
        nortExtractorPage().setEnvironmentDropdown("UAT");
        nortExtractorPage().setContentSetDropdown("Iowa (Development)");
        nortExtractorPage().checkAttorneyTopicGapCheckbox();
        nortExtractorPage().setAttorneyTopicGapDocName(docName);
        nortExtractorPage().attorneyTopicGapChooseFile(convertedFileName);
        nortExtractorPage().clickSubmit();

        boolean extractedFileLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.EXTRACTED_FILE_URL_XPATH);
        boolean convertedFilesLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.CONVERTED_FILES_URL_XPATH);
        boolean checkWorkflowLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.CHECK_WORKFLOWS_URL_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(extractedFileLinkAppears, "The extracted file link appears"),
            () -> Assertions.assertTrue(convertedFilesLinkAppears, "The converted files link appears"),
            () -> Assertions.assertTrue(checkWorkflowLinkAppears, "The check workflows link appears")
        );

        nortExtractorPage().clickCheckWorkflowLink();
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "The CwbDataCaptureComEntry workflow finished");

        homePage().goToHomePage();
        loginPage().logIn();

        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docName);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        Assertions.assertEquals(1, sourceNavigateFooterToolsPage().getResults(), "1 com doc was created with the correct doc name");

        sourceNavigateGridPage().deleteFirstRendition();

        boolean renditionWasDeleted = sourceNavigateGridPage().isRenditionDeleted(1);
        Assertions.assertTrue(renditionWasDeleted, "Rendition was deleted");

//      Assertions.assertEquals(0, sourceNavigateFooterToolsPage().getResults(), "The rendition was deleted");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: This test clicks statute history and verifies a csv converted file is created and populated with the expected content <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void statuteHistoryIdsConvertedFileContainsUidsTest()
    {
        String fileName = "T0SLCODAF:8910.1";

        final List<String> expectedCSVContent = Arrays.asList("UID", "ar26-17-101", "ar26-17-201", "ar26-17-202", "ar26-17-203", "ar26-17-204", "ar26-17-301", "ar26-17-302", "ar26-17-303",
                "ar26-17-304", "ar26-17-401", "ar26-17-402", "ar26-17-403", "ar26-17-404", "ar26-17-501", "ar26-17-502", "ar26-17-503", "ar26-17-504");

        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        nortExtractorPage().setIdList(fileName);
        nortExtractorPage().clickStatuteHistoryIdsRadioButton();
        nortExtractorPage().clickSubmit();

        boolean extractedFileLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.EXTRACTED_FILE_URL_XPATH);
        boolean convertedFilesLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.CONVERTED_FILES_URL_XPATH);
        boolean checkWorkflowLinkAppears = nortExtractorPage().isElementDisplayed(NortExtractorPageElements.CHECK_WORKFLOWS_URL_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(extractedFileLinkAppears, "The extracted file link appears"),
            () -> Assertions.assertTrue(convertedFilesLinkAppears, "The converted files link appears"),
            () -> Assertions.assertFalse(checkWorkflowLinkAppears, "The check workflows link appears")
        );

        String name = nortExtractorPage().getConvertedFileName();
        nortExtractorPage().clickConvertedFilesLink(name);

        List<String> convertedFileContent = CsvFileUtils.getContent(FileUtils.DOWNLOAD_FOLDER_DIR + name);

        boolean convertedFileContentIsCorrect = convertedFileContent.containsAll(expectedCSVContent);
        Assertions.assertTrue(convertedFileContentIsCorrect, "The downloaded converted file matches what we expect with the given id list input");
    }
}
