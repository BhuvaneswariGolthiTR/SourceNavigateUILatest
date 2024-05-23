package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.CROWN_DEPENDENCIES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.REG_GUIDANCE_SUMMARY_UK;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.REG_GUIDANCE_SUMMARY_US;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CiteQueryValidationsTests extends TestService
{
    private static final String TARGET_NODE_UUID_US = "I29F62200213111E7A1D8180373BE7E39";
    private static final String TARGET_NODE_UUID_UK = "I9D707600260311E7B1D7180373BE7E39";
    private static final String SNP_LABEL_XPATH = format(SPAN_BY_TEXT, "Source Note Paragraph");
    private static final String CITE_QUERY_XPATH = "(" + SNP_LABEL_XPATH + format(PARENT, "para)[1]/paratext/cite.query");
    private static final String SNPS_MISSING_CITE_QUERY_MARKUP_ASSERTION_MSG =
            "Warning should not be generated for SNPs missing cite.query markup";
    private static final String SNPS_THAT_CONTAIN_CITE_QUERY_MARKUP_ASSERTION_MSG =
            "Warning should not be generated for SNPs that contain cite.query markup";
    private static final String NODE_TO_SELECT = "@ Isle of Man FSA AML/CFT Handbook(32)";

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void citeQueryValidationsTurnedOnUSTargetRiskTest()
    {
        String warningMessage = "SNP para must contain at least one, but not more than one cite.query";

        openNodeRelatedToContentSetInDsEditor(TARGET_NODE_UUID_US, REG_GUIDANCE_SUMMARY_US);

        runValidations();

        //Assert that warning is not generated for SNPs because they all contain cite.query markup
        assertThatMessagePaneHasNoWarnings(
                "Warning should not be generated because all SNPs should contain cite.query markup");

        //Add additional cite.query markup to one of the SNPs
        editorPage().switchDirectlyToTextFrame();
        selectCiteQueryMarkup();
        editorPage().copyPasteCiteQuery();
       // editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "c"));
        //editorTextPage().sendKeys(Keys.END);
        //editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "v"));

        runValidations();
        String messagePaneContent = editorMessagePage().getMessage();

        //Assert that warning is generated for SNPs that contain more than one cite.query markup
        assertThat(editorMessagePage().checkMessage(warningMessage))
                .as("Warning should be generated for SNPs that contain more than one cite.query markup")
                .isTrue();

        //Remove all cite.query markup from an SNP
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().getElements(CITE_QUERY_XPATH).forEach(citeQueryMarkup -> {
            selectCiteQueryMarkup();
            editorTextPage().sendKeys(Keys.DELETE);
        });

        runValidations();

        //Assert that warning is generated for SNPs that are missing cite.query markup
        assertThat(editorMessagePage().getMessage().replace(messagePaneContent, ""))
                .as("Warning should be generated for SNPs that are missing cite.query markup")
                .contains(warningMessage);
        editorPage().closeDocumentAndDiscardChanges();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void citeQueryValidationsTurnedOffUKAndCheckInTargetRiskTest()
    {
        openNodeRelatedToContentSetInDsEditor(TARGET_NODE_UUID_UK, REG_GUIDANCE_SUMMARY_UK);

        runValidations();

        //Assert that warning is not generated for SNPs missing cite.query markup
        assertThatMessagePaneHasNoWarnings(SNPS_MISSING_CITE_QUERY_MARKUP_ASSERTION_MSG);

        insertCiteQueryMarkupIntoSnp();

        assertThatCiteQueryMarkupIsInserted();

        openInsertTargetCiteReferenceAndOpenTargetLocator();

        //Assert that you are brought to the previously selected node for Crown Dependencies
        assertThatYouAreBroughtToSpecifiedNodeForCrownDependencies(NODE_TO_SELECT);

        //Right click one of the crown dependencies nodes and click Select Target
        selectNodeFromTargetLocator();

        //Select Cancel
        insertTargetCiteReferencePage().closeCurrentWindowIgnoreDialogue();

        runValidations();

        //Assert that warning is not generated for SNPs that contain cite.query markup,
        // as well as for cite.query markup that has a w-ref-type value that is not LQ or SP
        assertThatMessagePaneHasNoWarnings(SNPS_THAT_CONTAIN_CITE_QUERY_MARKUP_ASSERTION_MSG);

        //Close and check in changes
        //NOTE: It was found in April 2019 that there were unexpected issues when checking in a document with a link
        // to another content set, so instead of discarding changes, we are going to perform a check in and verify a few additional items
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextAndAccept(
                true, "Subsection validation failed, continue with check-in?");
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Re-edit the node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Assert that the cite.query still exists on the text
        assertThatCiteQueryMarkupIsInserted();

        closeNodeWithWindow();

        //Right click the node and go to View/Modify Previous WIP Version
        // Restore the WIP version
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataPage().breakOutOfFrame();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().restoreOriginalWIPVersion();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void citeQueryValidationsTurnedOffUKAndCloseWithDiscardingChangesTargetRiskTest()
    {
        openNodeRelatedToContentSetInDsEditor(TARGET_NODE_UUID_UK, REG_GUIDANCE_SUMMARY_UK);

        runValidations();

        //Assert that warning is not generated for SNPs missing cite.query markup
        assertThatMessagePaneHasNoWarnings(SNPS_MISSING_CITE_QUERY_MARKUP_ASSERTION_MSG);

        insertCiteQueryMarkupIntoSnp();

        assertThatCiteQueryMarkupIsInserted();

        runValidations();

        //Assert that warning is not generated for SNPs that contain cite.query markup,
        // as well as for cite.query markup that has a w-ref-type value that is not LQ or SP
        assertThatMessagePaneHasNoWarnings(SNPS_THAT_CONTAIN_CITE_QUERY_MARKUP_ASSERTION_MSG);

        closeNodeWithWindow();

        //After the discard is complete, right click a different node in the same parentage (sibling)
        // Select Publishing Workflows -> Bulk Publish Rulebook by Hierarchy
        // Click Bulk Publish
        runBulkPublishRuleBookByHierarchyOnSiblingNode();

        //Open the workflow via the workflow link provided
        // Assert that the Workflow Properties -> contentSet value is that of the Reg Guidance Summary UK content set,
        // not the Crown Dependencies content set that was linked in the cite reference markup (HALCYONST-10250)
        assertThatContentSetWorkflowPropertyHasCorrectValue();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void citeQueryValidationsTurnedOffUKAndLeaveTheDocumentOpenedTargetRiskTest()
    {
        openNodeRelatedToContentSetInDsEditor(TARGET_NODE_UUID_UK, REG_GUIDANCE_SUMMARY_UK);

        runValidations();

        //Assert that warning is not generated for SNPs missing cite.query markup
        assertThatMessagePaneHasNoWarnings(SNPS_MISSING_CITE_QUERY_MARKUP_ASSERTION_MSG);

        insertCiteQueryMarkupIntoSnp();

        assertThatCiteQueryMarkupIsInserted();

        runValidations();

        //Assert that warning is not generated for SNPs that contain cite.query markup,
        // as well as for cite.query markup that has a w-ref-type value that is not LQ or SP
        assertThatMessagePaneHasNoWarnings(SNPS_THAT_CONTAIN_CITE_QUERY_MARKUP_ASSERTION_MSG);

        //Leave the editor opened, so we should have a Reg Guidance Summaries node open for edit with a
        // Crown Dependencies cite reference markup inserted
        // Switch back to Hierarchy Navigate and select a different Reg Guidance Summaries node in the sibling metadata
        // Right click this newly selected node
        runBulkPublishRuleBookByHierarchyOnSiblingNode();

        //Click on the workflow link that appears
        // Assert that the Workflow Properties -> contentSet value is that of the Reg Guidance Summary UK content set,
        // not the Crown Dependencies content set that was linked in the cite reference markup
        assertThatContentSetWorkflowPropertyHasCorrectValue();
        editorPage().closeDocumentAndDiscardChanges();
    }

// ---------- Assistive methods ----------

    private void openNodeRelatedToContentSetInDsEditor(String nodeUuid, ContentSets contentSet)
    {
        hierarchyNavigatePage().goToHierarchyPage(contentSet.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    private void selectCiteQueryMarkup()
    {
        String citeQueryImg = CITE_QUERY_XPATH + "/img";
        editorTextPage().click(citeQueryImg);
        editorTextPage().waitForElementsAttributeToBe(
                editorTextPage().getElement(citeQueryImg),
                "background-color", "rgba(49, 106, 197, 1)");
    }

    private void runValidations()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickValidate();
    }

    private void closeNodeWithWindow()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
    }

    private void runBulkPublishRuleBookByHierarchyOnSiblingNode()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectSelectedNodesNextNode();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().publishingWorkflowsBulkPublishRulebookByHierarchy();
        bulkPublishPage().clickPublishButton();
    }

    private void insertCiteQueryMarkupIntoSnp()
    {
        //Select some text and right click
        editorPage().switchDirectlyToTextFrame();
        String insertedText = String.valueOf(System.currentTimeMillis());
        editorTextPage().insertPhraseUnderGivenLabel(insertedText, SNP_LABEL_XPATH);
        editorTextPage().highlightHelper(insertedText);

        openInsertTargetCiteReferenceAndOpenTargetLocator();

        //Assert that you are brought to the SET node for Crown Dependencies
        assertThatYouAreBroughtToSpecifiedNodeForCrownDependencies("SET RB.CDRb");

        //Right click one of the crown dependencies nodes and click Select Target
        selectNodeFromTargetLocator();

        // Select Save
        // This will insert cite.query markup with a w-ref-type value of GO
        insertTargetCiteReferencePage().clickSave();
    }

    private void openInsertTargetCiteReferenceAndOpenTargetLocator()
    {
        //Open Insert Target Cite Reference page
        //editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "l"));
        editorPage().openInsertTargetCite();

        insertTargetCiteReferencePage().switchToInsertTargetCiteReferencePage();

        //Select Additional Content Sets
        insertTargetCiteReferencePage().clickAdditionalContentSetsCheckbox();

        //Set Content Set to Crown Dependencies
        insertTargetCiteReferencePage().selectContentSet(CROWN_DEPENDENCIES);

        //Click Locate Target
        insertTargetCiteReferencePage().clickLocateTarget();
        targetLocatorPage().switchToTargetLocatorPage();
    }

    private void selectNodeFromTargetLocator()
    {
        targetLocatorPage().rightClick(format("//td[text()='%s']", NODE_TO_SELECT));
        targetLocatorPage().selectNodeForTargetLinkMarkup();
    }

    private void assertThatMessagePaneHasNoWarnings(String message)
    {
        assertThat(editorMessagePage().checkForWarningInMessagePane())
                .as(message)
                .isFalse();
    }

    private void assertThatCiteQueryMarkupIsInserted()
    {
        editorPage().switchDirectlyToTextFrame();
        assertThat(editorTextPage().doesElementExist(CITE_QUERY_XPATH))
                .as("cite.query markup should be inserted")
                .isTrue();
    }

    private void assertThatYouAreBroughtToSpecifiedNodeForCrownDependencies(String specifiedNodeName)
    {
        assertThat(targetLocatorPage().getBackgroundColor(format("//*[contains(text(),'%s')]/../../..", specifiedNodeName)))
                .as("You should be brought to the %s node for Crown Dependencies", specifiedNodeName)
                .isEqualTo("rgba(65, 105, 225, 1)");
    }

    private void assertThatContentSetWorkflowPropertyHasCorrectValue()
    {
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        workflowSearchPage().openFirstWorkflow();
        workflowDetailsPage().expandWorkflowProperties();
        String contentSetProperty = workflowPropertiesPage().getPropertyValue(PropertiesElements.Property.CONTENT_SET);
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        assertThat(contentSetProperty)
                .as("Workflow Properties -> contentSet value should be related to Reg Guidance Summary UK")
                .isEqualTo("HRGS.UK");
    }
}
