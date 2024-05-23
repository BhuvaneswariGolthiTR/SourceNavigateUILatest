package com.thomsonreuters.codes.codesbench.quality.tests.audits.bysource;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.CommonAuditsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.ConfirmAuditRequestPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class AuditBySourcePageTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY -  Verifies that everything is displaying correctly on the audit by source page <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditBySourcePageTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditBySource();

        boolean isContentAuditLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_CONTENT_AUDIT_XPATH);
        boolean isHierarchyContextLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_HIERARCHY_CONTEXT_XPATH);
        boolean isVersionsAuditStartingLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_VERSIONS_AUDIT_STARTING_XPATH);
        boolean isValidationsLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_VALIDATIONS_XPATH);
        boolean isUnusedReportLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_UNUSED_REPORT_XPATH);
        boolean isSourcesLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_SOURCES_XPATH);
        boolean isNODReportLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_NOD_REPORT_XPATH);
        boolean isInstructionsReportLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_INSTRUCTIONS_REPORT_XPATH);
        boolean isStackingAndBlendingReportLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_STACKING_AND_BLENDING_REPORT_XPATH);
        boolean isTextMergeReportLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_TEXT_MERGE_REPORT_XPATH);
        boolean isResearchReferencesLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_RESEARCH_REFERENCES_XPATH);
        boolean isTextPreviewLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_TEXT_PREVIEW_XPATH);
        boolean isShortSameParagraphsLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_SHORT_SAME_PARAGRAPHS_XPATH);
        boolean isNotSameParagraphsLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_NOT_SAME_PARAGRAPHS_XPATH);
        boolean isAllTextLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_ALL_TEXT_XPATH);
        boolean isIgnorePubTagChangeLabelDisplayed = auditBySourcePage().isElementDisplayed(CommonAuditsPageElements.LABEL_IGNORE_PUBTAG_CHANGE_XPATH);
        boolean isMostRecentPublishedDocumentLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_MOST_RECENT_PUBLISHED_DOCUMENT_XPATH);
        boolean isMostRecentHistoricalVersionLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_MOST_RECENT_HISTORICAL_VERSION_XPATH);
        boolean isMostRecentWIPDocumentLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_MOST_RECENT_WIP_DOCUMENT_XPATH);
        boolean isWIPvsWIPLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_WIP_VS_WIP_XPATH);

        boolean isContentAuditCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.contentAuditCheckbox);
        boolean isHierarchyContextCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.hierarchyContextCheckbox);
        boolean isVersionsAuditStartingCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.versionsAuditStartingCheckbox);
        boolean isValidationCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.validationsCheckbox);
        boolean isUnusedReportCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.unusedReportCheckbox);
        boolean isSourcesCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.sourcesCheckbox);
        boolean isNODReportCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.nodReportCheckbox);
        boolean isInstructionsReportCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.instructionsReportCheckbox);
        boolean isStackingAndBlendReportCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.stackingAndBlendReportCheckbox);
        boolean isTextMergeCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.textMergeCheckbox);
        boolean isResearchReferencesCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.researchReferencesCheckbox);
        boolean isTextPreviewCheckboxChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.textPreviewCheckbox);

        boolean isMostRecentPublishedDocumentRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.mostRecentPublishedDocumentRadioButton);
        boolean isMostRecentHistoricalVersionRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.mostRecentHistoricalVersionRadioButton);
        boolean isMostRecentWIPDocumentRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.mostRecentWipDocumentRadioButton);
        boolean isWIPvsWIPRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.wipVsWipRadioButton);

        boolean isAdditionalIdentifierTextBoxDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.ADDITIONAL_IDENTIFIER_TEXTBOX_XPATH);

        boolean isShortSameParagraphsRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.shortSameParagraphsRadioButton);
        boolean isNotSameParagraphsRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.notShortSameParagraphsRadioButton);
        boolean isAllTextRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.allTextRadioButton);
        boolean isIgnorePubtagChangeRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.ignorePubtagChangeRadioButton);

        boolean isSourceLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_SOURCE_XPATH);
        boolean isClassificationLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_CLASSIFICATION_XPATH);
        boolean isDateLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.LABEL_DATE_XPATH);
        boolean isClassificationRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.classificationRadioButton);
        boolean isDateRadioButtonChecked = auditBySourcePage().isCheckboxChecked(AuditBySourcePageElements.dateRadioButton);
        boolean isFromDatesChangedTextboxDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.FROM_DATES_CHANGED_TEXTBOX_XPATH);
        boolean isToDatesChangedTextboxDisplayed = auditBySourcePage().isElementDisplayed(AuditBySourcePageElements.TO_DATES_CHANGED_TEXTBOX_XPATH);

        //These should not be displayed as they are elements from AuditByDocument Page
        boolean isVolumesLabelDisplayed = auditByDocumentPage().isElementDisplayed(AuditByDocumentPageElements.LABEL_VOLUMES_XPATH);
        boolean isSelectedVolumesLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditByDocumentPageElements.LABEL_SELECTED_VOLUMES_XPATH);
        boolean isSearchOptionsLabelDisplayed = auditBySourcePage().isElementDisplayed(AuditByDocumentPageElements.LABEL_SEARCH_OPTIONS_XPATH);
        boolean isUserToReportOnDropdownDisplayed = auditBySourcePage().isElementDisplayed(AuditByDocumentPageElements.USER_TO_REPORT_ON_DROPDOWN_XPATH);

        Assertions.assertAll(
                ()->Assertions.assertTrue(isContentAuditLabelDisplayed,"Content Audit Label is not displayed"),
                ()->Assertions.assertTrue(isHierarchyContextLabelDisplayed,"Hierarchy Context Label is not displayed"),
                ()->Assertions.assertTrue(isVersionsAuditStartingLabelDisplayed,"Versions Audit Label is not displayed"),
                ()->Assertions.assertTrue(isValidationsLabelDisplayed,"Validations Label is not displayed"),
                ()->Assertions.assertTrue(isUnusedReportLabelDisplayed,"Unused Report label is not displayed"),
                ()->Assertions.assertTrue(isSourcesLabelDisplayed,"Sources Label is not displayed"),
                ()->Assertions.assertTrue(isNODReportLabelDisplayed,"NOD report label is not displayed"),
                ()->Assertions.assertTrue(isInstructionsReportLabelDisplayed,"Instructions Report label is not displayed"),
                ()->Assertions.assertTrue(isStackingAndBlendingReportLabelDisplayed,"Stacking and Blending Report label is not displayed"),
                ()->Assertions.assertTrue(isTextMergeReportLabelDisplayed,"Text Merge label is not displayed"),
                ()->Assertions.assertTrue(isResearchReferencesLabelDisplayed,"Research References label is not displayed"),
                ()->Assertions.assertTrue(isTextPreviewLabelDisplayed,"Text Preview label is not displayed"),
                ()->Assertions.assertTrue(isShortSameParagraphsLabelDisplayed,"Short Same Paragraph Label is not displayed"),
                ()->Assertions.assertTrue(isNotSameParagraphsLabelDisplayed,"Not Same Paragraphs Label is not displayed"),
                ()->Assertions.assertTrue(isAllTextLabelDisplayed,"All Text Label is not displayed"),
                ()->Assertions.assertTrue(isIgnorePubTagChangeLabelDisplayed,"Ignore PubTag Change Label is not displayed"),
                ()->Assertions.assertTrue(isClassificationLabelDisplayed,"Classification Label is not displayed"),
                ()->Assertions.assertTrue(isDateLabelDisplayed,"Date Label is not displayed"),
                ()->Assertions.assertTrue(isMostRecentPublishedDocumentLabelDisplayed,"Most Recent Published Document Label is not displayed"),
                ()->Assertions.assertTrue(isMostRecentHistoricalVersionLabelDisplayed, "Most Recent Historical Version Label is not displayed"),
                ()->Assertions.assertTrue(isMostRecentWIPDocumentLabelDisplayed,"Most Recent WIP Document Label is not displayed"),
                ()->Assertions.assertTrue(isWIPvsWIPLabelDisplayed,"WIP vs. WIP Label is not displayed"),

                ()->Assertions.assertTrue(isContentAuditCheckboxChecked,"Content Audit checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isHierarchyContextCheckboxChecked,"Hierarchy Context checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isVersionsAuditStartingCheckboxChecked,"Versions Audit checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isValidationCheckboxChecked,"Validations checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isUnusedReportCheckboxChecked,"Unused Report checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isSourcesCheckboxChecked,"Sources checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isNODReportCheckboxChecked,"NOD Report checkbox is not checked, when it should be"),
                ()->Assertions.assertTrue(isInstructionsReportCheckboxChecked, "Instructions Report checkbox is not checked when it should be"),
                ()->Assertions.assertTrue(isStackingAndBlendReportCheckboxChecked, "Stacking and Blend report checkbox is not checked when it should be"),
                ()->Assertions.assertFalse(isTextMergeCheckboxChecked,"Text Merge checkbox is checked when it should not be"),
                ()->Assertions.assertFalse(isResearchReferencesCheckboxChecked, "Research References is checked when it should not be"),
                ()->Assertions.assertFalse(isTextPreviewCheckboxChecked,"Text preview checkbox is checked when it should not be"),

                ()->Assertions.assertTrue(isMostRecentPublishedDocumentRadioButtonChecked, "Most Recent Published document radio button is not enabled when it should be"),
                ()->Assertions.assertFalse(isMostRecentHistoricalVersionRadioButtonChecked, "Most Recent Historical version radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isMostRecentWIPDocumentRadioButtonChecked, "Most Recent WIP document radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isWIPvsWIPRadioButtonChecked, "WIP vs WIP radio button is enabled when it should not be"),

                ()->Assertions.assertTrue(isAdditionalIdentifierTextBoxDisplayed,"Additional Identifier Textbox is not displayed when it should"),

                ()->Assertions.assertTrue(isShortSameParagraphsRadioButtonChecked,"Short Same paragraphs radio button is not enabled when it should be"),
                ()->Assertions.assertFalse(isNotSameParagraphsRadioButtonChecked,"Not Same paragraphs radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isAllTextRadioButtonChecked,"All Text radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isIgnorePubtagChangeRadioButtonChecked,"Ignore Pubtag radio button is enabled when it should not be"),

                ()->Assertions.assertFalse(isClassificationRadioButtonChecked,"Classification Radio button is enabled when it should not be"),
                ()->Assertions.assertFalse(isDateRadioButtonChecked,"Date Radio button is enabled when it should not be"),
                ()->Assertions.assertTrue(isFromDatesChangedTextboxDisplayed,"From Dates Changed Textbox is not displayed"),
                ()->Assertions.assertTrue(isToDatesChangedTextboxDisplayed, "To Dates Changed Textbox is not displayed"),

                ()->Assertions.assertFalse(isSourceLabelDisplayed,"Source label is displayed when it should not be"),

                ()->Assertions.assertFalse(isUserToReportOnDropdownDisplayed, "User To Report On Dropdown is displayed"),
                ()->Assertions.assertFalse(isVolumesLabelDisplayed,"Volume(s) Label is displayed when it should not be"),
                ()->Assertions.assertFalse(isSelectedVolumesLabelDisplayed,"Selected Volume(s) Label is displayed when it should not be"),
                ()->Assertions.assertFalse(isSearchOptionsLabelDisplayed,"Search Options Label is displayed when it should not be")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY -  Verifies that no alert appears after not inputting audit identifier for audit by source <br>
     * USER - Legal  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void auditBySourceAlertShowsUpWithoutIdentifier()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditBySource();
        int year = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1;
        auditBySourcePage().selectMaterialByTitle(String.valueOf(year));
        auditBySourcePage().clickAddOneDocumentButton();
        auditBySourcePage().clickNextButtonWithoutWaiting();
        boolean didAlertPopup = auditBySourcePage().isAlertPresent();
        boolean didConfirmAuditRequestWindowAppear = auditBySourcePage().switchToWindow(ConfirmAuditRequestPageElements.CONFIRM_AUDIT_REQUEST_PAGE_TITLE);
        Assertions.assertAll
        (
                ()->Assertions.assertFalse(didAlertPopup, "Alert did not pop up when no audit Identifier was provided"),
                ()->Assertions.assertTrue(didConfirmAuditRequestWindowAppear,"Confirm Audit Request window did not appear when it should've.")
        );
    }
}
