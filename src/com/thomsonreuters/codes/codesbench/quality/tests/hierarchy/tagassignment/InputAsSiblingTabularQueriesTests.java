package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.*;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class InputAsSiblingTabularQueriesTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-13636<br>
     * SUMMARY - Adding Tabular Queries to STBLs During Hierarchy Input as Sibling  <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsSiblingWithTabularQueriesTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();
        String fileToInput = "TaxTypesXml\\TabularQueries.xml";
        String value = "888";
        String keyword = "SECTION";

        //Sign in as the legal user. Go to Hierarchy -> Navigate. Quick search Node
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as sibling
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsSibling();
        hierarchyInputAsSiblingPage().selectFileToUpload(fileToInput);
        hierarchyInputAsSiblingPage().clickSave();
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().waitForPageLoaded();

        //check workflow
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        assertThat(workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes())
                .as(format("The workflow %s is not finished", workflowId)).isTrue();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the file was inputted as a sibling
        assertThat(siblingMetadataPage().isNodeDisplayedWithValue(value))
                .as("The sibling metadata node did not appear").isTrue();

        //Open new node in DS. VERIFY: there are Tabular entries and STBL mnemonic
        hierarchySearchPage().quickSearch(keyword, value);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        boolean isTabularEntriesExist = editorTextPage().doesElementExist(EditorTextPageElements.TABULAR_ENTRIES_IN_BODY_PART);
        boolean isStblMnemonicExist = editorTextPage().doesElementExist(EditorTextPageElements.STBL_MNEMONIC);
        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Go to Tools -> Query Note Report. VERIFY: there is the new query with type TABULAR and status ACTIVE
        toolsMenu().goToQueryNoteReport();
        queryNoteReportFiltersPage().setFilterType("TABULAR");
        queryNoteReportFiltersPage().setFilterVols(initialNodeVols);
        queryNoteReportFiltersPage().setFilterStatus("ACTIVE");
        queryNotePage().clickRefreshButton();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isTabularEntriesExist, "VERIFY: tabular entries exist in the document"),
            () -> Assertions.assertTrue(isStblMnemonicExist, "VERIFY: mnemonic STBL exist in the document"),
            () -> Assertions.assertEquals(1, numberOfQueryNotes, "VERIFY: There is one new query note for the new node")
        );
    }

    @AfterEach
    public void deleteNode()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
