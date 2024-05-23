package com.thomsonreuters.codes.codesbench.quality.tests.nortextractor;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nortextractor.NortExtractorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NortExtractorNegativeTests extends TestService
{
    /**
     * SUMMARY: This test verifies alerts appear when there is invalid entry when clicking submit for the Nort Extractor ui <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nortExtractorNavigationNegativeTest()
    {
        nortExtractorPage().goToNortExtractor();
        loginPage().logIn();

        nortExtractorPage().clickSourceConversionAmendmentNotesRadioButton();
        nortExtractorPage().clickSubmit();
        boolean idListAlertAppears = nortExtractorPage().getElement(NortExtractorPageElements.ID_LIST_INPUT_XPATH).getAttribute("required").equals("true");
        boolean nextPageAppearsInvalidIdList = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_PARAGRAPH_XPATH)
                && nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_PARAGRAPH_XPATH)
                && nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_PARAGRAPH_XPATH);

        nortExtractorPage().setIdList("Test");
        nortExtractorPage().clickSubmit();
        boolean environmentAlertAppears =  nortExtractorPage().getElement(NortExtractorPageElements.AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH).getAttribute("required").equals("true");
        boolean nextPageAppearsNoEnvironments = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_PARAGRAPH_XPATH);

        nortExtractorPage().setEnvironmentDropdown("DEV");
        boolean contentSetPopulatesAutomatically = !nortExtractorPage().getElementsText(NortExtractorPageElements.AMENDMENT_NOTES_CONTENT_SET_DROPDOWN_XPATH).equals("");
        nortExtractorPage().setContentSetDropdown("Iowa (Development)");
        nortExtractorPage().clickSubmit();
        boolean noCheckboxSelectedAlertAppears = nortExtractorPage().checkAlertTextMatchesGivenText("At least one Source Amendment Notes checkboxes must be activated");
        boolean nextPageAppearsTopicGapNotChecked = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_PARAGRAPH_XPATH);

        nortExtractorPage().checkAttorneyTopicGapCheckbox();
        nortExtractorPage().clickSubmit();
        boolean emptyDocNameAlertAppears = nortExtractorPage().getElement(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_DOC_NAME_INPUT_XPATH).getAttribute("required").equals("true");
        boolean nextPageAppearsEmptyDocName = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_PARAGRAPH_XPATH);

        nortExtractorPage().setAttorneyTopicGapDocName("Tests");
        nortExtractorPage().clickSubmit();
        boolean noFileChosenAlertAppears = nortExtractorPage().getElement(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_CHOOSE_FILE_XPATH).getAttribute("required").equals("true");
        boolean nextPageAppearsNoFileChosen = nortExtractorPage().doesElementExist(NortExtractorPageElements.EXTRACTED_FILE_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CONVERTED_FILES_PARAGRAPH_XPATH)
            && nortExtractorPage().doesElementExist(NortExtractorPageElements.CHECK_WORKFLOWS_PARAGRAPH_XPATH);

        nortExtractorPage().clickReset();
        boolean isIdListEmpty = nortExtractorPage().getElementsText(NortExtractorPageElements.ID_LIST_INPUT_XPATH).equals("");
        boolean targetConversionIsSelected = nortExtractorPage().getElement(NortExtractorPageElements.TARGET_CONVESION_RADIO_BUTTON_XPATH).isSelected();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(idListAlertAppears, "An alert should appear for an empty id list but doesn't"),
            () -> Assertions.assertFalse(nextPageAppearsInvalidIdList, "Submit does not go to next page for empty id list"),
            () -> Assertions.assertTrue(environmentAlertAppears, "An alert should appear if no environment is selected but doesn't"),
            () -> Assertions.assertFalse(nextPageAppearsNoEnvironments, "Submit does not go to next page if no environment is selected"),
            () -> Assertions.assertTrue(contentSetPopulatesAutomatically, "After selecting an environment the content set should automatically populate"),
            () -> Assertions.assertTrue(noCheckboxSelectedAlertAppears, "An alert appeared for no checkboxes checked"),
            () -> Assertions.assertFalse(nextPageAppearsTopicGapNotChecked, "The next page should not appear if no checkbox is checked for amendment notes"),
            () -> Assertions.assertTrue(emptyDocNameAlertAppears, "An alert appears for an empty doc name"),
            () -> Assertions.assertFalse(nextPageAppearsEmptyDocName, "The next page should not appear for an empty doc name"),
            () -> Assertions.assertTrue(noFileChosenAlertAppears, "An alert appears if no file is chosen"),
            () -> Assertions.assertFalse(nextPageAppearsNoFileChosen, "The next page should not appear if no file is chosen"),
            () -> Assertions.assertTrue(isIdListEmpty, "The id list is reset when reset is clicked"),
            () -> Assertions.assertTrue(targetConversionIsSelected, "Target Conversion radio button is selected on reset")
        );
    }
}
