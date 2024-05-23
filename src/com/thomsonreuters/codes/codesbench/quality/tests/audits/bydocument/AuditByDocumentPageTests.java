package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.CommonAuditsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class AuditByDocumentPageTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY -  Verifies that everything is displaying correctly on the audit by document page <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditByDocumentPageTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditByDocument();

        boolean isContentAuditLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_CONTENT_AUDIT_XPATH);
        boolean isHierarchyContextLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_HIERARCHY_CONTEXT_XPATH);
        boolean isVersionsAuditStartingLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_VERSIONS_AUDIT_STARTING_XPATH);
        boolean isValidationsLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_VALIDATIONS_XPATH);
        boolean isSourcesLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_SOURCES_XPATH);
        boolean isNODReportLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_NOD_REPORT_XPATH);
        boolean isVolumesLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditByDocumentPageElements.LABEL_VOLUMES_XPATH);
        boolean isSelectedVolumesLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditByDocumentPageElements.LABEL_SELECTED_VOLUMES_XPATH);
        boolean isSearchOptionsLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditByDocumentPageElements.LABEL_SEARCH_OPTIONS_XPATH);
        boolean isShortSameParagraphsLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_SHORT_SAME_PARAGRAPHS_XPATH);
        boolean isNotSameParagraphsLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_NOT_SAME_PARAGRAPHS_XPATH);
        boolean isAllTextLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_ALL_TEXT_XPATH);
        boolean isIgnorePubTagChangeLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_IGNORE_PUBTAG_CHANGE_XPATH);
        boolean isClassificationLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_CLASSIFICATION_XPATH);
        boolean isDateLabelDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.LABEL_DATE_XPATH);

        boolean isContentAuditCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.contentAuditCheckbox);
        boolean isHierarchyContextCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.hierarchyContextCheckbox);
        boolean isVersionsAuditStartingCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.versionsAuditStartingCheckbox);
        boolean isValidationCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.validationsCheckbox);
        boolean isSourcesCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.sourcesCheckbox);
        boolean isNODReportCheckboxChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.nodReportCheckbox);

        boolean isShortSameParagraphsRadioButtonChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.shortSameParagraphsRadioButton);
        boolean isNotSameParagraphsRadioButtonChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.notShortSameParagraphsRadioButton);
        boolean isAllTextRadioButtonChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.allTextRadioButton);
        boolean isIgnorePubtagChangeRadioButtonChecked = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.ignorePubtagChangeRadioButton);

        boolean isClassificationRadioButtonEnabled = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.classificationRadioButton);
        boolean isDateRadioButtonEnabled = auditByDocumentPage().isCheckboxChecked(CommonAuditsPageElements.dateRadioButton);
        boolean isFromDatesChangedTextboxDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.FROM_DATES_CHANGED_TEXTBOX_XPATH);
        boolean isToDatesChangedTextboxDisplayed = auditByDocumentPage().isElementDisplayed(CommonAuditsPageElements.TO_DATES_CHANGED_TEXTBOX_XPATH);
        boolean isUserToReportOnDropdownDisplayed = auditByDocumentPage().isElementDisplayed(AuditByDocumentPageElements.USER_TO_REPORT_ON_DROPDOWN_XPATH);

        //These should not be displayed as they are elements from AuditBySource Page
        boolean isUnusedReportLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_UNUSED_REPORT_XPATH);
        boolean isInstructionsReportLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_INSTRUCTIONS_REPORT_XPATH);
        boolean isStackingAndBlendingReportLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_STACKING_AND_BLENDING_REPORT_XPATH);
        boolean isTextMergeReportLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_TEXT_MERGE_REPORT_XPATH);
        boolean isResearchReferencesLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_RESEARCH_REFERENCES_XPATH);
        boolean isTextPreviewLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_TEXT_PREVIEW_XPATH);
        boolean isSourceLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditBySourcePageElements.LABEL_SOURCE_XPATH);

        //use assert.all
        Assertions.assertAll(
                ()->Assertions.assertTrue(isContentAuditLabelDisplayed,"Content Audit Label is not displayed"),
                ()->Assertions.assertTrue(isHierarchyContextLabelDisplayed,"Hierarchy Context Label is not displayed"),
                ()->Assertions.assertTrue(isVersionsAuditStartingLabelDisplayed,"Versions Audit Label is not displayed"),
                ()->Assertions.assertTrue(isValidationsLabelDisplayed,"Validations Label is not displayed"),
                ()->Assertions.assertTrue(isSourcesLabelDisplayed,"Sources Label is not displayed"),
                ()->Assertions.assertTrue(isNODReportLabelDisplayed,"NOD report label is not displayed"),
                ()->Assertions.assertTrue(isVolumesLabelDisplayed,"Volume(s) Label is not displayed"),
                ()->Assertions.assertTrue(isSelectedVolumesLabelDisplayed,"Selected Volume(s) Label is not displayed"),
                ()->Assertions.assertTrue(isSearchOptionsLabelDisplayed,"Search Options Label is not displayed"),
                ()->Assertions.assertTrue(isShortSameParagraphsLabelDisplayed,"Short Same Paragraph Label is not displayed"),
                ()->Assertions.assertTrue(isNotSameParagraphsLabelDisplayed,"Not Same Paragraphs Label is not displayed"),
                ()->Assertions.assertTrue(isAllTextLabelDisplayed,"All Text Label is not displayed"),
                ()->Assertions.assertTrue(isIgnorePubTagChangeLabelDisplayed,"Ignore PubTag Change Label is not displayed"),
                ()->Assertions.assertTrue(isClassificationLabelDisplayed,"Classifcation Label is not displayed"),
                ()->Assertions.assertTrue(isDateLabelDisplayed,"Date Label is not displayed"),

                ()->Assertions.assertTrue(isContentAuditCheckboxChecked,"Content Audit checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isHierarchyContextCheckboxChecked,"Hierarchy Context checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isVersionsAuditStartingCheckboxChecked,"Versions Audit checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isValidationCheckboxChecked,"Validations checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isSourcesCheckboxChecked,"Sources checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isNODReportCheckboxChecked,"NOD Report checkbox is not checked, when it should be"),

                ()->Assertions.assertTrue(isShortSameParagraphsRadioButtonChecked,"Short Same paragraphs radio button is not enabled when it should be"),
                ()->Assertions.assertFalse(isNotSameParagraphsRadioButtonChecked,"Not Same paragraphs radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isAllTextRadioButtonChecked,"All Text radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isIgnorePubtagChangeRadioButtonChecked,"Ignore Pubtag radio button is enabled when it should not be"),

                ()->Assertions.assertTrue(isClassificationRadioButtonEnabled,"Classification Radio button is not enabled when it should be"),
                ()->Assertions.assertFalse(isDateRadioButtonEnabled,"Date Radio button is enabled when it should not be"),
                ()->Assertions.assertTrue(isFromDatesChangedTextboxDisplayed,"From Dates Changed Textbox is not displayed"),
                ()->Assertions.assertTrue(isToDatesChangedTextboxDisplayed, "To Dates Changed Textbox is not displayed"),
                ()->Assertions.assertTrue(isUserToReportOnDropdownDisplayed, "User To Report On Dropdown is not displayed"),

                ()->Assertions.assertFalse(isUnusedReportLabelDisplayed,"Unused Report label is displayed when it should not be"),
                ()->Assertions.assertFalse(isInstructionsReportLabelDisplayed,"Instructions Report label is displayed when it should not be"),
                ()->Assertions.assertFalse(isStackingAndBlendingReportLabelDisplayed,"Stacking and Blending Report label is displayed when it should not be"),
                ()->Assertions.assertFalse(isTextMergeReportLabelDisplayed,"Text Merge label is displayed when it should not be"),
                ()->Assertions.assertFalse(isResearchReferencesLabelDisplayed,"Research References label is displayed when it should not be"),
                ()->Assertions.assertFalse(isTextPreviewLabelDisplayed,"Text Preview label is displayed when it should not be"),
                ()->Assertions.assertFalse(isSourceLabelDisplayed,"Source label is displayed when it should not be")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY -  Verifies that an alert appears when an audit identifier is not inputted <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditByDocumentAlertShowsUpWithoutIdentifier()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditByDocument();
        auditByDocumentPage().selectVolumeByVolumeNumber("0010");
        auditByDocumentPage().clickAddOneDocumentButton();
        auditByDocumentPage().clickNextButtonWithoutWaiting();
        boolean didAlertPopup = auditByDocumentPage().checkAlertTextMatchesGivenText("Audit Identifier needs to be provided.");
        Assertions.assertTrue(didAlertPopup, "Alert did not pop up when no audit Identifier was provided");
    }
}