package com.thomsonreuters.codes.codesbench.quality.tests.source.set2.spellcheck;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements.Property;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails.WorkflowPropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SpellcheckTests extends TestService
{

    static Stream<Arguments> renditionsAndUuidsProvider()
    {
        return Stream.of
                (
                        /**
                         * STORY/BUGS - HALCYONST-6669
                         *
                         * NOTE ABOUT SORTING for all checkDocumentsSortingInSummary tests:
                         * Docs in report summary should appear:
                         * 1) in Class Number order,
                         * 1.1) Class Numbers should sort as follows:
                         * for numeric strings, or for numeric + alpha strings, these should all sort together,
                         * and the numeric portion of the value should first be sorted upon, then the alpha value (if present).
                         * So, for example, the below class values should sort in this shown order:
                         * 3
                         * 5c
                         * 21
                         * 42a
                         * 123
                         * All values containing a dash can be sorted AFTER any entirely numeric or numeric + alpha strings,
                         * and all such values (values containing a dash) should be sorted as follows: first sort by the
                         * numeric value prior to the dash, then sort by the numeric value following the dash.
                         * For example, the below values should sort in this order:
                         * 2018-3
                         * 2018-5
                         * 2018-21
                         * 2019-4
                         * 2019-23
                         * 2) if no Class Number is present, in West ID order,
                         * 3) if no West ID is present, then in order of Doc Type (alphabetical),
                         * 4) then Doc Number (numerical).
                         *
                         * Test 1
                         * SUMMARY - Run spellcheck report on several renditions with non-empty Class Numbers. Also,
                         * at least one of Class Numbers need to contain a letter. The test checks the order of documents
                         * in the pdf report.
                         *
                         * 1. Log in as a legal user to uat
                         * 2. Navigate to Source Navigate
                         * 3. Set 'Not Deleted', 'Iowa' content set, '2012' year and 'PREP' rendition status.
                         * 4. Set 1226, 2464 and 2472 values for Doc Number filter and click Refresh
                         * 4.1 In this test renditionUuid url parameter is used, it opens directly the necessary documents.
                         * 5. Go to 'Select' / 'Select All on Page'
                         * 6. Right-click on selected documents and select Report - Spellcheck Report
                         *      Spellcheck Report popup appeared
                         * 7. Click on the link in Spellcheck Report popup
                         * 8. Wait while workflow will be finished
                         * 9. Expand Workflow Properties, copy and open the pdf from pdfReportLink
                         *      Documents must be in the following order:
                         *      IA 2012 2RG CHAPTER 44 HF 2472
                         *      IA 2012 2RG CHAPTER 198A HF 1226
                         *      IA 2012 2RG CHAPTER 400 HF 2464
                         *      IA 2012 2RG CHAPTER 888 HF 1226
                         *
                         * NOTE: If Class Numbers are present, documents in spellcheck report must be sorted by Class
                         * Numbers. The numeric portion of the value must first be sorted on, then the alpha value:
                         * 44, 198A, 400, 888.
                         */
                        arguments((Object) new String[]
                                        {
                                                "IA 2012 2RG CHAPTER 44 HF 2472",
                                                "IA 2012 2RG CHAPTER 198A HF 1226",
                                                "IA 2012 2RG CHAPTER 400 HF 2464",
                                                "IA 2012 2RG CHAPTER 888 HF 1226"
                                        },
                                (Object) new String[]
                                        {
                                                //Class Number = 198A
                                                "I7A0ADE20F26811E3A381FF3367ADCFD4",
                                                // Class Number = 888
                                                "IF89D0F10F26811E3A381FF3367ADCFD4",
                                                //Class Number = 400
                                                "I7160BDF0448911E2A870C2A2591809FF",
                                                //Class Number = 44
                                                "IB092EFB0810711E2AFE0831CF66877D8"
                                        }),
                        /**
                         * Test 2
                         * SUMMARY - Run spellcheck report on several renditions with non-empty Class Numbers. Also,
                         * at least one of Class Numbers need to contain a dash. The test checks the order of documents
                         * in the pdf report.
                         *
                         * 1. Log in as a legal user to uat
                         * 2. Navigate to Source Navigate
                         * 3. Set 'Not Deleted', 'Colorado' content set, 2014 year and 'APV' rendition status and click Refresh
                         * 4. Set 1, 208 and 427 values for Tracking Number filter and click Refresh
                         * 4.1 In this test renditionUuid url parameter is used, it opens directly the necessary documents.
                         * 5. Go to 'Select' / 'Select All on Page'
                         * 6. Right-click on selected documents and select Report - Spellcheck Report
                         *      Spellcheck Report popup appeared
                         * 7. Click on the link in Spellcheck Report popup
                         * 8. Wait while workflow will be finished
                         * 9. Expand Workflow Properties, copy and open the pdf from pdfReportLink
                         *      Documents must be in the following order:
                         *      CO 2014 2RG CHAPTER 1 H 1019
                         *      CO 2014 2RG CHAPTER 188 H 1021
                         *      CO 2014 2RG CHAPTER 14-135 IBP 14-135
                         *
                         * NOTE: If Class Numbers are present, documents in spellcheck report are sorted by Class Numbers.
                         * All values containing a dash must be sorted after any entirely numeric or numeric + alpha
                         * strings: 1, 188, 14-135.
                         */
                        arguments((Object) new String[]
                                        {
                                                "CO 2014 2RG CHAPTER 1 H 1019",
                                                "CO 2014 2RG CHAPTER 188 H 1021",
                                                "CO 2014 2RG CHAPTER 14-135 IBP 14-135"
                                        },
                                (Object) new String[]
                                        {
                                                //Class Number = 1
                                                "IF09C88C1990A11E3953C988ECCB5CF80",
                                                //Class Number = 188
                                                "ICF0AA2C1E17911E3AA79E85DF2B014EA",
                                                //Class Number = 14-135
                                                "IA6B4B511233F11E494A5918B12C8482B"
                                        }),
                        /**
                         * Test 3
                         * SUMMARY - Run spellcheck report on several renditions with empty Class Numbers and with non-
                         * empty West ID. Also, at least one of West IDs need to contain a dash. The test checks the
                         * order of documents in the pdf report.
                         *
                         * 1. Log in as a legal user to uat
                         * 2. Navigate to the Source navigate
                         * 3. Set 'Not Deleted', 'Colorado' content set, 2012 year and 'APV' rendition status
                         * 4. Set values for West IDs: 2012co-a008a010, 2012co-a008a131, 2012co-a008a345,
                         * 2012co-a009a010, 2012co-a009a345 and click Refresh
                         * 4.1 In this test renditionUuid url parameter is used, it opens directly the necessary documents.
                         * 5. Go to 'Select' / 'Select All on Page'
                         * 6. Right-click on selected documents and select Report - Spellcheck Report
                         *      Spellcheck Report popup appeared
                         * 7. Click on the link in Spellcheck Report popup
                         * 8. Wait while workflow will be finished
                         * 9. Expand Workflow Properties, copy and open the pdf from pdfReportLink
                         *      Documents must be in the following order:
                         *      CO 2012 West ID No. 2012co-a008a010 FN 275629
                         *      CO 2012 West ID No. 2012co-a008a131 FN 287264
                         *      CO 2012 West ID No. 2012co-a008a345 FN 307413
                         *      CO 2012 West ID No. 2012co-a009a010 FN 275631
                         *      CO 2012 West ID No. 2012co-a009a345 FN 307415
                         *
                         * NOTE: If no Class Number is present, documents are sorted by West ID. All values containing a
                         * dash should be sorted as follows: first sort by the numeric value prior to the dash, then
                         * sort by the numeric value following the dash: 2012co-a008a010, 2012co-a008a131,
                         * 2012co-a008a345, 2012co-a009a010, 2012co-a009a345.
                         */
                        arguments((Object) new String[]
                                        {
                                                "CO 2012 West ID No. 2012co-a008a010 FN 275629",
                                                "CO 2012 West ID No. 2012co-a008a131 FN 287264",
                                                "CO 2012 West ID No. 2012co-a008a345 FN 307413",
                                                "CO 2012 West ID No. 2012co-a009a010 FN 275631",
                                                "CO 2012 West ID No. 2012co-a009a345 FN 307415"
                                        },
                                (Object) new String[]
                                        {
                                                //West ID = 2012co-a008a010
                                                "I701003B1E43A11E2BEFADFC648D5B5F6",
                                                //West ID = 2012co-a009a010
                                                "I713DD551E43A11E2BEFADFC648D5B5F6",
                                                //West ID = 2012co-a008a131
                                                "I705B6491E43A11E2BEFADFC648D5B5F6",
                                                //West ID = 2012co-a008a345
                                                "I70A12021E43A11E2BEFADFC648D5B5F6",
                                                //West ID = 2012co-a009a345
                                                "I71DBC301E43A11E2BEFADFC648D5B5F6"
                                        }),
                        /**
                         * Test 4
                         * SUMMARY - Run spellcheck report on several renditions with empty Class Numbers and empty West
                         * IDs and non-empty Doc Types. The test checks the order of documents in the pdf report.
                         *
                         * 1. Log in as a legal user to uat
                         * 2. Navigate to the Source navigate
                         * 3. Set 'Not Deleted', 'Colorado' content set, 2012 year and 'APV' rendition status and click
                         * Refresh
                         * 4. Set 169, 407, 232 and 375 values for Tracking Number filter and click Refresh
                         * 4.1 In this test renditionUuid url parameter is used, it opens directly the necessary documents.
                         * 5. Go to 'Select' / 'Select All on Page'
                         * 6. Right-click on selected documents and select Report - Spellcheck Report
                         *      Spellcheck Report popup appeared
                         * 7. Click on the link in Spellcheck Report popup
                         * 8. Wait while workflow will be finished
                         * 9. Expand Workflow Properties, copy and open the pdf from pdfReportLink
                         *      Documents must be in the following order:
                         *      CO 2012 2RG HCR 12-1001
                         *      CO 2012 1RG IBP 12-82
                         *      CO 2012 1RG ORD 0006
                         *      CO 2012 RTRK 287260
                         *
                         * NOTE: if no Class Numbers and no West IDs are present, then documents in spellcheck are sorted
                         * by Doc Type (alphabetical): HCR, IBP, ORD, RTRK
                         */
                        arguments((Object) new String[]
                                        {
                                                "CO 2012 2RG HCR 12-1001",
                                                "CO 2012 1RG IBP 12-82",
                                                "CO 2012 1RG ORD 0006",
                                                "CO 2012 RTRK 287260"
                                        },
                                (Object) new String[]
                                        {
                                                //Doc Type = IBP
                                                "I40552DC0E8CD11E283739FEC27DDDA55",
                                                //Doc Type = ORD
                                                "I0DF137709F0211E3A2BC949C076A0706",
                                                //Doc Type = HCR
                                                "IB0D957C1A93911E2BF2CEFC1B9C06E28",
                                                //Doc Type = RTRK
                                                "IF0B5DCF0E43B11E2BEFADFC648D5B5F6"
                                        }),
                        /**
                         * Test 5
                         * SUMMARY: Run spellcheck report on several renditions with empty Class Numbers, empty West
                         * IDs, identical Doc Types and with non-empty Doc Numbers. The test checks the order of
                         * documents in the pdf report.
                         *
                         * 1. Log in as a legal user to uat
                         * 2. Navigate to the Source navigate
                         * 3. Set 'Not Deleted', 'Colorado' content set, 2012 year, 'APV' rendition status and 'ORD' Doc
                         * Type and click Refresh
                         * 4. Set 0006, 0003, 0012, 0001, 0002 values for Doc Number filter and click Refresh
                         * 4.1 In this test renditionUuid url parameter is used, it opens directly the necessary documents.
                         * 5. Go to 'Select' / 'Select All on Page'
                         * 6. Right-click on selected documents and select Report - Spellcheck Report
                         *      Spellcheck Report popup appeared
                         * 7. Click on the link in Spellcheck Report popup
                         * 8. Wait while workflow will be finished
                         * 9. Expand Workflow Properties, copy and open the pdf from pdfReportLink
                         *      Documents must be in the following order:
                         *      CO 2012 1RG ORD 0001
                         *      CO 2012 1RG ORD 0002
                         *      CO 2012 1RG ORD 0003
                         *      CO 2012 1RG ORD 0006
                         *      CO 2012 1RG ORD 0012
                         *
                         * NOTE: If no Class Numbers and no West IDs are present, and Doc Types are identical, then Doc
                         * Number (numerical): 0001, 0002, 0003, 0006, 0012
                         */
                        arguments((Object) new String[]
                                        {
                                                "CO 2012 1RG ORD 0001",
                                                "CO 2012 1RG ORD 0002",
                                                "CO 2012 1RG ORD 0003",
                                                "CO 2012 1RG ORD 0006",
                                                "CO 2012 1RG ORD 0012"
                                        },
                                (Object) new String[]
                                        {
                                                //Doc Number = 0001
                                                "I0DB0AC009F0211E3A2BC949C076A0706",
                                                //Doc Number = 0002
                                                "I0D571A509F0211E3A2BC949C076A0706",
                                                //Doc Number = 0003
                                                "I0CCA43A09F0211E3A2BC949C076A0706",
                                                //Doc Number = 0006
                                                "I0DF137709F0211E3A2BC949C076A0706",
                                                //Doc Number = 0012
                                                "I1427CAF09F0211E3A2BC949C076A0706"
                                        })
                );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionsAndUuidsProvider")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    void checkDocumentsSortingInSummary(String[] documents, String[] uuids)
    {
        sourcePage().goToSourcePageWithRenditionUuids(uuids);
        loginPage().logIn();
        sourceNavigateFooterToolsPage().clickSelectAllOnPage();

        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().goToReportSpellcheckReport();

        spellcheckReportModal().clickWorkflowLink();

        WorkflowDetailsPage workflowDetailsPage = workflowDetailsPage();
        Assertions.assertTrue(workflowDetailsPage.verifyWorkflowFinished(), "Workflow didn't finish");

        WorkflowPropertiesPage workflowPropertiesPage = workflowPropertiesPage();
        workflowPropertiesPage.clickWorkflowPropertiesButton();
        String pdfReportLink = workflowPropertiesPage.getPropertyValue(Property.PDF_REPORT_LINK);

        File pdfReportFile = FileUtils.loadFileFromUrl(pdfReportLink);
        String content = PDFUtils.getPdfContent(pdfReportFile);

        for (int i = 0; i < documents.length - 1; i++)
        {
            Assertions.assertTrue(content.indexOf(documents[i]) < content.indexOf(documents[i + 1]),
                    String.format("The %s document must be next to the %s document. PDF content is: %s",
                            documents[i + 1], documents[i], content));
        }
    }

    private static String[] renditionUuidFiltering()
    {
        return new String[]
                {
                        "IF4BB8E306B0F11E291FECDF020830FCE", //APV
                        "I3BBCE840179811E2A2CCBB59A0353111", //PREP
                        "IDF8AF8D28A8011E28D21EB3971A3080B" //COM
                };
    }
    /**
     * STORY: HALCYONST-6160/3492 <br>
     * SUMMARY: This test verifies spellcheck catches misspellings in the source body for APV, PREP, and COM documents
     * USER: LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuidFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    void runSpellCheckReportForApvPrepCom(String renditionUuid)
    {
        String misspelledWord = "tetstingg";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //filter for document
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setRenditionUuidFilter(renditionUuid);
        documentSearchFilterPage().clickRenditionUuidFilterButton();

        //run spellcheck report
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().goToReportSpellcheckReport();
        String workflowId = spellcheckReportModal().getWorkflowId();
        spellcheckReportModal().clickClose();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        Assertions.assertTrue(workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes(), "Workflow didn't finish");
        workflowSearchPage().openFirstWorkflow();

        //verify misspelling is caught in the spellcheck pdf report
        workflowPropertiesPage().clickWorkflowPropertiesButton();
        String pdfReportLink = workflowPropertiesPage().getPropertyValue(Property.PDF_REPORT_LINK);

        File pdfReportFile = FileUtils.loadFileFromUrl(pdfReportLink);
        String content = PDFUtils.getPdfContent(pdfReportFile);

        Assertions.assertTrue(content.contains(misspelledWord), "The spelling check report contained the misspelled word");
    }

    /**
     * STORY: HALCYONST-6160 <br>
     * SUMMARY: This test verifies the spellcheck report catches misspellings in the topical heading of a source doc, and verifies the spellcheck report does not
     * contain 'lt' for a special character markup '<' (less than) symbol and only catches the misspelling in the topical heading <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    void runSpellCheckReportForTopicalHeading()
    {
        String renditionUuid = "IF8637A2133F111E2BB6CC38AA2DE39A4";
        String addedPhrase = "death";
        String misspelledPhrase = "imbaddatspellin";
        String misspelledPhraseContainingLt = "testing pt."; //please do not use lt. since it is conflicting with special character conversion
        String expectedTopicalHeading =  addedPhrase +"\u2014"+ misspelledPhrase +"\u2014"+ misspelledPhraseContainingLt;

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //search for rendition and edit topical heading
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setRenditionUuidFilter(renditionUuid);
        documentSearchFilterPage().clickRenditionUuidFilterButton();
        sourceNavigateGridPage().unlockFirstRendition();
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean topicalHeadingHighlightWindowAppears = renditionContextMenu().editTopicalHeadingIndexEntryFeatures();
        Assertions.assertTrue(topicalHeadingHighlightWindowAppears, "Topical Heading/Highlight window appears");

        //move one phrase to the selected phrases
        topicalHeadingHighlightPage().movePhraseToTheRightAddedList(addedPhrase);
        boolean phrase2AddedToSelected = topicalHeadingHighlightPage().isPhraseInSelectedList(addedPhrase);
        Assertions.assertTrue(phrase2AddedToSelected,"The first phrase was added to selected phrases");

        //add first misspelled phrase
        topicalHeadingHighlightPage().addUserPhrase();
        userPhrasePage().enterPhrase(misspelledPhrase);
        userPhrasePage().clickSubmit();
        boolean phraseAdded = topicalHeadingHighlightPage().isPhraseInSelectedList(misspelledPhrase);
        Assertions.assertTrue(phraseAdded, "Misspelled phrase was not added to selected list");

        //add second misspelled phrase containing 'lt.'
        topicalHeadingHighlightPage().addUserPhrase();
        userPhrasePage().enterPhrase(misspelledPhraseContainingLt);
        userPhrasePage().clickSubmit();
        boolean phraseAddedContainingIt = topicalHeadingHighlightPage().isPhraseInSelectedList(misspelledPhraseContainingLt);
        Assertions.assertTrue(phraseAddedContainingIt, "Phrase containing \"Lt.\" was not added to the selected list");

        //click create topical heading button
        topicalHeadingHighlightPage().clickCreateTopicalHeading(expectedTopicalHeading);
        topicalHeadingHighlightPage().clickOk();

        //run spellcheck report and verify workflow finished
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().goToReportSpellcheckReport();
        spellcheckReportModal().clickWorkflowLink();
        boolean didWorkflowFinish = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(didWorkflowFinish, "Spellcheck workflow did not finish");

        //verify misspelling is caught in the spellcheck pdf report
        workflowPropertiesPage().clickWorkflowPropertiesButton();
        String pdfReportLink = workflowPropertiesPage().getPropertyValue(Property.PDF_REPORT_LINK);
        File pdfReportFile = FileUtils.loadFileFromUrl(pdfReportLink);
        String content = com.thomsonreuters.codes.codesbench.quality.utilities.files.PDFUtils.getPdfContent(pdfReportFile);

        //go back to source grid page and delete all phrases in selected phrases
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        pendingRenditionNavigatePage().waitForPageLoaded();
        spellcheckReportModal().closeCurrentWindowIgnoreDialogue();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editTopicalHeadingIndexEntryFeatures();
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
        topicalHeadingHighlightPage().deletePhrase(addedPhrase);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
        topicalHeadingHighlightPage().deletePhrase(misspelledPhrase);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
        topicalHeadingHighlightPage().deletePhrase(misspelledPhraseContainingLt);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
        topicalHeadingHighlightPage().clickCreateTopicalHeading("");
        topicalHeadingHighlightPage().clickOk();

        Assertions.assertTrue(content.contains(misspelledPhrase), "The spellcheck report found the misspelled word in the topical heading");
        if(content.contains("pt"))
        {
            int index = content.indexOf("pt");
            char numOccurrences = content.charAt(index + 3);
            int value = Character.getNumericValue(numOccurrences);
            Assertions.assertTrue(value == 1,String.format("'pt' appeared %d times when it should only be caught once for the topical heading misspelling and not the '<' less than special character markup inside the table", value));
        }
        else
        {
            Assertions.fail("'lt' from the second misspelled phrase did not appear in the in the spellcheck report when it should have");
        }
    }

    /**
     * STORY/BUGS - HALCYONST-6442/6443/5603/5607 <br>
     * SUMMARY - In the spellcheck report there should not be an extra space between words with dots.
     * Example: "seq." not "seq ." <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkExtraSpacingInSpellcheckReportTest()
    {
        //Hardcoded APV doc
        String renditionUUID = "IF4BB8E306B0F11E291FECDF020830FCE";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        File pdfReportFile = runSpellcheckOnRenditionAndReturnReport(renditionUUID);
        boolean containsWordWithSpaceBeforeDot = PDFUtils.getPdfContent(pdfReportFile).contains("seq .");
        boolean containsWordWithNoSpaceBeforeDot = PDFUtils.getPdfContent(pdfReportFile).contains("seq.");
        boolean containsWordsWithSecSec = PDFUtils.getPdfContent(pdfReportFile).contains("Seq.Seq");

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(containsWordWithSpaceBeforeDot, "There should not be a space before the dot"),
            () -> Assertions.assertTrue(containsWordWithNoSpaceBeforeDot, "The word properly appears in the pdf"),
            () -> Assertions.assertFalse(containsWordsWithSecSec, "The mnemonic belonging to the text paragraph should appear in the spell check report")
        );
    }

    /**
     * STORY/BUGS - HALCYONST- 6182 <br>
     * SUMMARY - Validates that the misspelled word in tabular appears in the spellcheck report pdf<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkMisspelledWordInTabularEntryTest()
    {
        String misspelledWord = "tabularEntryTestsss";
        String renditionUUID = "IF4BB8E306B0F11E291FECDF020830FCE";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        File pdfReportFile = runSpellcheckOnRenditionAndReturnReport(renditionUUID);
        boolean containsMisspelledWord = PDFUtils.getPdfContent(pdfReportFile).contains(misspelledWord);
        Assertions.assertTrue(containsMisspelledWord, "The misspelled word should be contained in the spell check pdf");
    }

    /**
     * STORY/BUGS - HALCYONST- 6182 <br>
     * SUMMARY - validate that the misspelled markup word appears in the spellcheck report pdf<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkMisspelledWordInMarkup()
    {
        String misspelledWord = "markupMisspelledTestsss";
        String renditionUUID = "IF4BB8E306B0F11E291FECDF020830FCE";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        File pdfReportFile = runSpellcheckOnRenditionAndReturnReport(renditionUUID);
        boolean containsMisspelledWord = PDFUtils.getPdfContent(pdfReportFile).contains(misspelledWord);
        Assertions.assertTrue(containsMisspelledWord, "The misspelled word should be contained in the spell check pdf");
    }

    private File runSpellcheckOnRenditionAndReturnReport(String renditionUUID)
    {
        //filter for rendition
        sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        documentSearchFilterPage().setRenditionUuidFilter(renditionUUID);
        documentSearchFilterPage().clickRenditionUuidFilterButton();
        sourceNavigateGridPage().waitForGridRefresh();

        //Create spellcheck report, assert the workflow completes.
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().goToReportSpellcheckReport();
        spellcheckReportModal().clickWorkflowLink();
        boolean didWorkflowDetailsPageAppear = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(didWorkflowDetailsPageAppear, "Workflow didn't finish");

        //Check the spellcheck report for the correct spelling error
        workflowPropertiesPage().clickWorkflowPropertiesButton();
        String pdfReportLink = workflowPropertiesPage().getPropertyValue(Property.PDF_REPORT_LINK);
        File pdfReportFile = FileUtils.loadFileFromUrl(pdfReportLink);
        return pdfReportFile;
    }

    @AfterEach
    public void afterTest()
    {
        try
        {
            Files.delete(Paths.get(FileUtils.PDF_REPORT_FILE_PATH));
        }
        catch (IOException e)
        {
            logger.severe("Issue deleting spellcheck report");
            e.printStackTrace();
        }
    }
}
