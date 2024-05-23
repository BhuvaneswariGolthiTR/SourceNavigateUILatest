package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.Keys;


import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertItalicNoteMultipleFullSectionTests extends TestService
{
    public enum DeltaInsertMethod
    {
        DELTA_AMEND_SECTION(EditorTextPageElements.DELTA_IN_TREE, "amend", "section", "Amend Section"),
        DELTA_AMEND_SUBSECTION(FIRST_DELTA_AMEND_SUBSECTION, "amend", "subsection", "Amend Subsection"),
        DELTA_ADD_SECTION(EditorTextPageElements.FIRST_DELTA_ADD_SECTION, "ADD", "SECTION", "Add Section"),
        DELTA_ADD_SUBSECTION(EditorTextPageElements.FIRST_DELTA_ADD_SUBSECTION, "ADD", "SUBSECTION", "Add Subsection"),
        DELTA_ADD_NOTE(EditorTextPageElements.FIRST_DELTA_ADD_NOTE, "ADD", "NOTE.CLASSIFIED", "Add Note"),
        DELTA_DELETE_SUBSECTION(FIRST_DELTA_DELETE_SUBSECTION, "DELETE", "SUBSECTION", "Delete Subsection"),
        DELTA_REPEAL_SECTION(EditorTextPageElements.DELTA_REPEAL_SECTION, "REPEAL", "SECTION", "Add Repeal"),
        DELTA_ADD_GRADE(FIRST_DELTA_GRADE_SECTION,"ADD","GRADE","Add Grade");

        private final String xpath;
        private final String deltaAction;
        private final String deltaLevel;
        private final String description;

        DeltaInsertMethod(String xpath, String deltaAction, String deltaLevel, String description)
        {
            this.xpath = xpath;
            this.deltaAction = deltaAction;
            this.deltaLevel = deltaLevel;
            this.description = description;
        }

        public String getXpath()
        {
            return xpath;
        }

        public String getDeltaAction()
        {
            return deltaAction;
        }

        public String getDeltaLevel()
        {
            return deltaLevel;
        }

        public String getDescription()
        {
            return description;
        }
    }
    private static final String WYOMING_RENDITION_UUID = "I141F23F07A5711EA91DDA15824C9FC6E";
    private static final String UUID = "I20DB18804C4711EAA224F7A255AA9973";
    private static final String UUID2 = "I56C1B130ACA011E9B60AC67ECAC8B48F";
    private static final String PART_START_SECTION_ITALIC = "(//*[contains(text(),'Part Start Section Italic')])[%d]";
    private static final String PARA = "//para[@full-display-name='Italic Note Wide Centered']";
    public static final String ITALIC_NOTE_WIDE_CENTERED = HIGHLIGHTED_DELTA + PART + ED_NOTE + PARA  ;

    private static final String HIGHLIGHTED_MNEMONIC = HIGHLIGHTED_PARA + MD_MNEM_MNEMONIC;
    private static final String MNEMONIC_VALUE = "WISC";
    private static final String HIGHLIGHTED_SOURCE_TAG = HIGHLIGHTED_PARA + SOURCE_TAG;
    private static final String SOURCE_TAG_VALUE = "21";

    private static final String SECOND_DELTA_IN_TREE = "(//a[contains(text(),'(delta subsection amend)')])[%d]";
    private static final String FIRST_DELTASECTION_IN_TREE = "(//a[contains(text(),'(delta section amend)')])[%d]";
    private static final String ITALIC_NOTE_OF_INSERTED_DELTA = format(MULTIPLE_FULL_SECTION_ITALIC_NOTE, " ");
    private static final String INSERTED_DELTA_LABEL_AFTER_CHANGES = format(DELTA_BY_CLASS_PATTERN, "") +
            format(SPAN_CONTAINS_TEXT, "Delta Amend Section");

    /**
     * June 2022:
     * The Rendition IAC6C3730858111EA9E09A689256BBE1E has no deltas.
     * Now the Rendition UUID with exactly the same content is I141F23F07A5711EA91DDA15824C9FC6E.
     * If the data will be corrupted again, try to refactor the test using the following renditions:
     * I7160BDF0448911E2A870C2A2591809FF
     * I72419670633E11E3B5A086DD4F762955
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertItalicNoteAndVerifySavePointsCreatedCorrectlyTest()
    {
        String[] nodesToExpand = {"source.body", "source.section \"1\""};
        String testPhrase = String.valueOf(System.currentTimeMillis());
        String testPhraseInPara = format(MULTIPLE_FULL_SECTION_ITALIC_NOTE_TEXT, testPhrase);

        // Filter for Rendition UUID
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(WYOMING_RENDITION_UUID);
        sourceNavigateGridPage().clickFirstRendition();

        // Go to the delta tab
        sourceNavigateTabsPage().goToDeltaTab();
        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateGridPage().click(format(ROW, 2));

        assertThatSelectedRowColumnValuesAreCorrect("targetNode", "/a", "22-3-117");
        assertThatSelectedRowColumnValuesAreCorrect("TargetSubNode", "", "(a),(tm)");
        assertThatSelectedRowColumnValuesAreCorrect("intStatus", "/a", "Target located and ready");

        // Go to the rendition tab and edit document with Dynamic Scrolling editor
        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateGridPage().firstRenditionEditContent();

        // In the navigation pane, expand "source body" -> "source section 1" -> "(delta subsection amend) 22-3-117(a),(tm)"
        editorTreePage().expandEditorsTreeAndClickNode(nodesToExpand);
        editorTextPage().waitForPageLoaded();
        editorTreePage().click(format(SECOND_DELTA_IN_TREE, 2));
        editorTextPage().waitForPageLoaded();
        editorTreePage().switchToInnerIFrameByIndex(1);
        editorPage().switchDirectlyToTextFrame();
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_DELTA))
                .as("Selected Delta Amend Subsection should be highlighted")
                .isTrue();

        // From the Delta Amend Subsection, right click and from the context menu and select:
        // Insert Italic Note -> Deferred Italic Note setup -> Multiple Full Section
        editorTextPage().rightClick(HIGHLIGHTED_DELTA + format(SPAN_CONTAINS_TEXT, "Delta Amend Subsection"));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertItalicNoteMultipleFullSection();

        // VERIFY: The stocknote is inserted into text and is visible in the Editor Tree
        editorPage().switchToEditor();
        assertThat(editorTreePage().getElementsText((format(FIRST_DELTASECTION_IN_TREE, 1))))
                .as("The (delta section amend)§  22-3-117(a),(tm) should be displayed in the Editor Tree")
                .contains("delta section amend", "22-3-117(a),(tm)");
        editorPage().switchDirectlyToTextFrame();
        assertThat(editorTextPage()
                .doesElementExist(format(DELTA_BY_CLASS_PATTERN, " ") +
                        format(SPAN_CONTAINS_TEXT, "Delta Amend Section")))
                .as("Delta Amend Section should be inserted into the document")
                .isTrue();

        // VERIFY: The stocknote should NOT insert § after the "section" before "22-3-117":
        assertThat(editorTextPage()
                .doesElementExist(ITALIC_NOTE_OF_INSERTED_DELTA + PARATEXT_SPAN + format(ENTITY_PATTERN, "sect")))
                .as("The Section character should not be inserted into Italic Note paragraph")
                .isFalse();

        // VERIFY: The 'Modified by' tags should appear for all the affected data:
        assertThatEveryAffectedParagraphHasModifiedByTagWithUserNameAndCurrentDate();

        // Type some text.
        // Press Undo once.
        editorTextPage().click(ITALIC_NOTE_OF_INSERTED_DELTA + PARATEXT);
        editorTextPage().waitForPageLoaded();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(testPhrase,ITALIC_NOTE_OF_INSERTED_DELTA + PARATEXT);
        assertThat(editorTextPage().doesElementExist(testPhraseInPara))
                .as("The test phrase should be inserted")
                .isTrue();
        clickUndoInToolbar();

        // VERIFY:
        // Text typing is undone but the stocknote is present.
        assertThat(editorTextPage().doesElementExist(testPhraseInPara))
                .as("The inserted test phrase should disappear after clicking undo")
                .isFalse();
        assertThat(editorTextPage().doesElementExist(INSERTED_DELTA_LABEL_AFTER_CHANGES))
                .as("Inserted Delta Amend Section should stay in the document")
                .isTrue();
        assertThatEveryAffectedParagraphHasModifiedByTagWithUserNameAndCurrentDate();

        // Press Undo one more time.
        clickUndoInToolbar();

        // VERIFY:
        // The stocknote is undone.
        assertThat(editorTextPage().doesElementExist(INSERTED_DELTA_LABEL_AFTER_CHANGES))
                .as("Inserted Delta Amend Section should disappear")
                .isFalse();
        editorTextPage().click(format(SPAN_CONTAINS_TEXT, "Part Text"));
        assertThat(editorTextPage().doesElementExist(PART + CLASS_HIGHLIGHTED_POSTFIX + ED_NOTE))
                .as("The Italic Note block in existed Delta Amend Subsection should be undone")
                .isFalse();

        // Close and discard changes. Switch to Delta tab
        editorPage().closeDocumentAndDiscardChanges();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateTabsPage().goToDeltaTab();

        // Scroll right to the Location/Integration Status column
        // Right-click on any delta that is 'Target located and ready'
        // Go to 'View > Target Content Dynamic Scrolling'
        sourceNavigateGridPage().rightClick(SELECTED_DELTA_ROW + "/td[2]");
        sourceNavigateGridPage().breakOutOfFrame();
        deltaContextMenu().goToViewTargetContent();
        editorPage().switchDirectlyToTextFrame();

        // VERIFY: Target node is opened in View mode
        assertThat(editorTextPage().doesElementExist(BODY_TAG + "[@read-only='true' and contains(@class,'read-only')]"))
                .as("Read-only flag must be true")
                .isTrue();
        assertThat(editorTextPage().getBackgroundColor(BODY_TAG))
                .as("The background should be grey")
                .isEqualTo("rgba(204, 204, 204, 1)");

        // VERIFY: Target node is opened in Dynamic Scrolling editor (not Common editor)
        assertThat(editorPage().getTitle())
                .as("Target node should be opened in Dynamic Scrolling Editor")
                .contains("Dynamic Scrolling Editor");
    }

    /**
     * April 2023:
     * 719166 [HALCYONST-12035] Dynamic Scrolling: Import Italic notes from Target docs
     * Verify an option added to the context menu in source documents, it should be called "Import target text - Italic Text"
     * pull in the italic notes from the target document. Italic notes in target documents will have either WISC or INPCP mnemonic with an Editorial Note english label
     * The "Import Target Text - Italic Note" function should only be available for the following Deltas types:
     *         1) Section Amend: action="amend" level="section"
     *         2) Subsection Amend: action="amend" level="subsection"
     * When imported, it should come into the source PREP document as a separate Part.start.section.italic with the target ital note. If there is already an existing italic note in the delta, then it should keep the original italic note and then palace the imported target italic note on the top (the new imported italic note should come first)
     * The imported italic note should also contain baggage information that says "Modified by Imported by USER NAME and DATE MM/DD/YYYY"
     * Example: "Modified by Imported by Melissa Wolf 02/01/2021
     */
    @ParameterizedTest(name="{displayName}[{index}]{arguments}")
    @EnumSource
            (
                    names = {"DELTA_AMEND_SECTION","DELTA_AMEND_SUBSECTION"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertImportTargetTextItalicTextAndVerifySavePointsCreatedCorrectlyTest(DeltaInsertMethod deltaInsertMethod)
    {
        // Filter for Rendition UUID
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToRenditionTab();

        // Go to the rendition tab and edit document with Dynamic Scrolling editor
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(1);
        editorPage().switchDirectlyToTextFrame();

        // From the Delta Amend Subsection, right click and from the context menu and select:
        // Import Target Text – Italic Text
        editorTextPage().scrollToElement(format(deltaInsertMethod.getXpath(), 1));
        editorTextPage().click(format(deltaInsertMethod.getXpath(), 1));
        editorTextPage().rightClick(format(deltaInsertMethod.getXpath(), 1));
        editorTextPage().breakOutOfFrame();

        softAssertThatImportTargetTextItalicTextOptionIsPlacedCorrectly();
        editorTextContextMenu().insertImportTargetTextItalicText();
        editorPage().switchDirectlyToTextFrame();

//        VERIFY:
//        Italicnote should come into the source PREP document as a separate Part.start.section.italic with the target italic note. If there is already an existing italic note in the delta, then it should keep the original italic note and then palace the imported target italic note on the top (the new imported italic note should come first)
//        The italic notes should come in under the part text wrapper as a WISC note
//        The imported italic note should also contain baggage information that says "Modified by Imported by USER NAME and DATE MM/DD/YYYY"
//        Example: "Modified by Imported by Melissa Wolf 02/01/2021"

        assertThat(editorTextPage().doesElementExist(ITALIC_NOTE_WIDE_CENTERED))
                .as("The Italic Note block in existed Delta Amend Subsection should be undone")
                .isTrue();

        assertThat(editorTextPage().getElementsText(ITALIC_NOTE_WIDE_CENTERED + MD_MNEM_MNEMONIC))
                .as(format("Imported paragraph should have %s default mnemonic value", MNEMONIC_VALUE))
                .isEqualTo(MNEMONIC_VALUE);

        assertThat(editorTextPage().getElementsText(ITALIC_NOTE_WIDE_CENTERED + SOURCE_TAG))
                .as(format("Imported paragraph should have %s default source tag", SOURCE_TAG_VALUE))
                .isEqualTo(SOURCE_TAG_VALUE);

        assertThat(editorTextPage().getElementsText("//para[@full-display-name='Italic Note Wide Centered']"+ editorTextPage().getImportedModifiedByXpath(user())))
                .as("Pasted content should have a Modified by tag with user's name and current date")
                .contains(user().getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy());

        editorPage().closeDocumentWithNoChanges();


    }

    /**
     * April 2023:
     * 719166 [HALCYONST-12035] Dynamic Scrolling: Import Italic notes from Target docs
     * Verify an option added to the context menu in source documents, it should be called "Import target text - Italic Text"
     * Right click a Delta Subsection Add
     *Click Import Target Text – Italic Text
     *VERIFY:
     *A warning appears in the editor message pane
     *"warn: Import target text - Italic Text is unavailable for the delta with action 'add' & delta level 'subsection'"
     */
    @ParameterizedTest(name="{displayName}[{index}]{arguments}")
    @EnumSource
            (
                    names = {"DELTA_REPEAL_SECTION","DELTA_ADD_GRADE","DELTA_ADD_SECTION","DELTA_ADD_SUBSECTION","DELTA_ADD_NOTE","DELTA_DELETE_SUBSECTION"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertImportTargetTextItalicTextForDeltas(DeltaInsertMethod deltaInsertMethod)
    {
        // Filter for Rendition UUID
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(UUID2);
        sourceNavigateGridPage().clickFirstRendition();

        // Go to the Delta tab
        sourceNavigateTabsPage().goToDeltaTab();
        sourceNavigateTabsPage().waitForPageLoaded();
        sourceNavigateFiltersAndSortsPage().waitForPageLoaded();

        sourceNavigateFiltersAndSortsPage().setFilterDeltaLevel(deltaInsertMethod.deltaLevel);
        sourceNavigateFiltersAndSortsPage().waitForPageLoaded();
        sourceNavigateFiltersAndSortsPage().setFilterAction(deltaInsertMethod.deltaAction);

        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClick(format(ROW, 2));
        deltaContextMenu().editDelta();

//        Right click a Delta Section Add
//        Click Import Target Text – Italic Text
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().click(deltaInsertMethod.getXpath());
        editorTextPage().rightClick(deltaInsertMethod.getXpath());
        editorTextPage().breakOutOfFrame();

        softAssertThatImportTargetTextItalicTextOptionIsPlacedCorrectly();
        editorTextContextMenu().insertImportTargetTextItalicText();
        editorPage().switchDirectlyToTextFrame();

//        VERIFY:
//        A warning appears in the editor message pane
//        "warn: Import target text - Italic Text is unavailable for the delta with action 'add' & delta level 'section'."

        assertThat(editorTextPage().doesElementExist(ITALIC_NOTE_WIDE_CENTERED))
                .as("The Italic Note block in existed Delta Amend Subsection should be undone")
                .isFalse();
        editorTextPage().breakOutOfFrame();
        assertThat(editorMessagePage().checkMessagePaneForText(format("warn: Import target text - Italic Text is unavailable for the delta with action '%s' & delta level '%s'.", deltaInsertMethod.deltaAction.toLowerCase(),deltaInsertMethod.deltaLevel.toLowerCase())))
                .as("The warning message should appear after pasting")
                .isTrue();
        editorPage().closeDocumentWithNoChanges();
    }

    // ---------- Assertions ----------

    private void assertThatEveryAffectedParagraphHasModifiedByTagWithUserNameAndCurrentDate()
    {
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate(
                format(PARA_BY_DISPLAY_NAME, "Subsection Text Amend"), "Subsection Text Amend");
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate(
                PART_SECTION_NAMELINE + CODES_HEAD, "Section Nameline");
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate(
                PART_TEXT_BY_DISPLAY_NAME + ED_NOTE + PARA, "Italic Note");
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate(
                PART_TEXT_BY_DISPLAY_NAME + CODES_TEXT + PARA, "Text Paragraph");
    }

    private void assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate(String xpathToParagraph,
                                                                                    String paragraphName)
    {
        assertThat(editorTextPage()
                .doesElementExist(DELTA + xpathToParagraph + editorTextPage().getModifiedByXpath(user())))
                .as(format("'Modified by' tag with the user's name and current date should exist for %s", paragraphName))
                .isTrue();
    }

    private void assertThatSelectedRowColumnValuesAreCorrect(String columnName, String appendix, String columnValue)
    {
        String xpathToValue = sourceNavigateGridPage()
                .getElementsText(format(SELECTED_DELTA_COLUMN, columnName) + appendix);
        assertThat(xpathToValue)
                .as(format("%s of the selected row should be %s", columnName, columnValue))
                .contains(columnValue);
    }

    // ---------- Assistive Methods ----------

    private void clickUndoInToolbar()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorPage().switchDirectlyToTextFrame();
    }

    private void softAssertThatImportTargetTextItalicTextOptionIsPlacedCorrectly()
    {
        List<String> allAvailableContextMenuOptionsList = editorTextContextMenu().getAllAvailableContextMenuOptions();
        int index = allAvailableContextMenuOptionsList.indexOf("Import Target Text - Italic Text");
        assertThat(index)
                .as("The 'Import Target Text - Italic Text' option should present in context menu")
                .isNotEqualTo(-1);
       assertThat(allAvailableContextMenuOptionsList.get(index - 1))
                .as("The 'Import Target Text - Italic Text' should be under the 'Import Target Text with NL'" +
                        " context menu option")
                .isEqualTo("Import Target Text with NL");
    }
}