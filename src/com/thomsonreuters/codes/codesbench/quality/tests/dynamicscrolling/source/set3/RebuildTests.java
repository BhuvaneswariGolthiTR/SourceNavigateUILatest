package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_SECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.mnemonics.SCP3;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class RebuildTests extends TestService
{
    private static final String APV_RENDITION_UUID = "IE715AA40749611E3B36998C6A8F13A44";
    private static final String PARA_WITH_SCP_3_MNEMONIC = SCP3.xpath() + format(ANCESTOR, "para");
    private static final String FIRST_PARA_WITH_SCP_3_MNEMONIC_SPAN = format("(%s)%s", PARA_WITH_SCP_3_MNEMONIC, "[1]/span");
    private int numberOfSCP3RecordsToPaste;
    private int sourceSectionWrappersNumberBeforeRebuild;
    private List<String> insertedPhrasesToSCP3RecordsList;

    @BeforeEach
    public void loginAndOpenRenditionInDsEditor()
    {
        //1. Open an APV document
        sourcePage().goToSourcePageWithRenditionUuids(APV_RENDITION_UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();
    }

    @AfterEach
    public void restoreRenditionBaseline()
    {
        //11. Delete any data created with this test
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        sourceNavigateGridPage().breakOutOfFrame();
        sourceNavigateContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().switchToViewBaselinesPage();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rebuildTest()
    {
        //2. Select and copy an SCP3 record
        openContextMenuOnSCP3Record();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchDirectlyToTextFrame();

        //3. Paste it under the one just copied
        //4. Add a bit of additional text to the pasted SCP3 record
        //5. Repeat Steps 3 - 4 about 10 times (HALCYONST-4710)
        sourceSectionWrappersNumberBeforeRebuild = editorTextPage().countElements(SOURCE_SECTION);
        numberOfSCP3RecordsToPaste = 10;
        pasteSCP3Records();

        //6. Click Rebuild in the toolbar (if an alert appears for grouping, accept it)
        clickRebuild();
        assertThatTheNumberOfSourceSectionWrappersIsIncreasedByTheNumberOfPastedSCP3Records();
        assertThatTheSourceSectionWrapperExistsForEachSCP3Record();
        assertThatNoDataWasRemovedFromTheContentArea("[position() != 1]");
        assertThatNoDataWasRemovedFromTheTree("");

        //7. Repeat Steps 3 - 4 about 5 more times (HALCYONST-4710)
        numberOfSCP3RecordsToPaste = 5;
        pasteSCP3Records();

        //8. Click Rebuild in the toolbar (if an alert appears for grouping, accept it)
        sourceSectionWrappersNumberBeforeRebuild = editorTextPage().countElements(SOURCE_SECTION);
        clickRebuild();

        assertThatTheNumberOfSourceSectionWrappersIsIncreasedByTheNumberOfPastedSCP3Records();
        assertThatTheSourceSectionWrapperExistsForEachSCP3Record();
        assertThatNoDataWasRemovedFromTheContentArea("[position() > 1 and position() < 7]");
        assertThatNoDataWasRemovedFromTheTree("[position() < 6]");

        //Assert that there are no errors in the message pane
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the message pane")
                .isFalse();

        //9. Click the green close button and check-in the document
        editorPage().closeAndCheckInChanges();

        //10. Re-open the document
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();

        //Assert that the pasted content still exists
        assertThat(editorTextPage().countElements(PARA_WITH_SCP_3_MNEMONIC))
                .as("The pasted content should exist after re-opening")
                .isEqualTo(sourceSectionWrappersNumberBeforeRebuild + numberOfSCP3RecordsToPaste);
    }

//  ------------- Assistive methods: -------------

    private void openContextMenuOnSCP3Record()
    {
        editorTextPage().click(FIRST_PARA_WITH_SCP_3_MNEMONIC_SPAN);
        editorTextPage().rightClick(FIRST_PARA_WITH_SCP_3_MNEMONIC_SPAN);
        editorTextPage().breakOutOfFrame();
    }

    private void pasteSCP3Records()
    {
        insertedPhrasesToSCP3RecordsList = new ArrayList<>();

        for (int i = 0; i < numberOfSCP3RecordsToPaste; i++)
        {
            //Paste previously copied SCP3 record under the one just copied
            openContextMenuOnSCP3Record();
            editorTextContextMenu().pasteSiblingCtrlV();

            //Accepting alert while pasting if it appears
            if (editorPage().isAlertPresent())
            {
                editorPage().acceptAlert();
            }

            editorPage().switchDirectlyToTextFrame();

            //Add a bit of additional text to the pasted SCP3 record
            editorTextPage().sendKeys(Keys.ARROW_DOWN);
            editorTextPage().waitForElementGone(PARA_WITH_SCP_3_MNEMONIC + CLASS_HIGHLIGHTED_POSTFIX);
            editorTextPage().sendKeys(Keys.END);
            String insertedPhraseToSCP3Record = format(" Test %s", System.currentTimeMillis());
            insertedPhrasesToSCP3RecordsList.add(insertedPhraseToSCP3Record);
            editorTextPage().sendKeys(insertedPhraseToSCP3Record);
            editorTextPage().click(FIRST_PARA_WITH_SCP_3_MNEMONIC_SPAN);
        }

        insertedPhrasesToSCP3RecordsList.sort(Comparator.reverseOrder());
    }

    private void clickRebuild()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickRebuild();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatTheNumberOfSourceSectionWrappersIsIncreasedByTheNumberOfPastedSCP3Records()
    {
        assertThat(editorTextPage().countElements(SOURCE_SECTION))
                .as(format("The number of source section wrappers should be increased by %d", numberOfSCP3RecordsToPaste))
                .isEqualTo(sourceSectionWrappersNumberBeforeRebuild + numberOfSCP3RecordsToPaste);
    }

    private void assertThatTheSourceSectionWrapperExistsForEachSCP3Record()
    {
        for (int i = 1; i <= sourceSectionWrappersNumberBeforeRebuild + numberOfSCP3RecordsToPaste; i++)
        {
            assertThat(editorTextPage().countElements(SOURCE_SECTION + format("[%d]", i) + PARA_WITH_SCP_3_MNEMONIC))
                    .as("Each source section wrapper should contain only one SCP3 record")
                    .isEqualTo(1);
        }
    }

    private void assertThatNoDataWasRemovedFromTheContentArea(String position)
    {
        List<String> textContentFromSCP3RecordsList = new ArrayList<>();
        editorTextPage().getElementsTextList(SOURCE_SECTION + position + PARA_WITH_SCP_3_MNEMONIC + PARATEXT)
                .forEach(textContent -> textContentFromSCP3RecordsList.add(textContent.substring(3)));
        assertThat(textContentFromSCP3RecordsList)
                .as("No data should be removed from the content area after rebuild")
                .isEqualTo(insertedPhrasesToSCP3RecordsList);
    }

    private void assertThatNoDataWasRemovedFromTheTree(String position)
    {
        editorTextPage().click(FIRST_PARA_WITH_SCP_3_MNEMONIC_SPAN);
        editorPage().switchToEditor();

        List<String> nodeNamesOfTheSCP3RecordsFromTheTreeList = new ArrayList<>();
        editorTreePage().getElementsTextList("//div[contains(@treeNodeName,'Test')]" + position)
                .forEach(nodeName -> nodeNamesOfTheSCP3RecordsFromTheTreeList.add(
                        nodeName.substring(17, nodeName.length() - 1)));
        assertThat(nodeNamesOfTheSCP3RecordsFromTheTreeList)
                .as("No data should be removed from the tree after rebuild")
                .isEqualTo(insertedPhrasesToSCP3RecordsList);

        editorPage().switchToEditorTextFrame();
    }
}
