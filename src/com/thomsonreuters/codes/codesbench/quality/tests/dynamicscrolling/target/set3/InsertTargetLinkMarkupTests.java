package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TargetLocatorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.TARGET_INPUT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods.CONTEXT_MENU;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertTargetLinkMarkupTests extends TestService
{
        private static final String UNIQUE_PHRASE = "Banana boom!"; // this phrase shouldn't occur within the document initially
        private static final String CITE_QUERY = "//cite.query";
        private static final String CITE_QUERY_IN_PARAGRAPH = PARA + PARATEXT + CITE_QUERY;

        @Test
        @IE_EDGE_MODE
        @RISK
        @LOG
        public void insertTargetLinkMarkupTargetTest()
        {
                String uuid = "I793927B01B0711E8B6BDA19A0134DE22";

                homePage().goToHomePage();
                loginPage().logIn();
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchToEditor();
                editorPage().closeEditorWithDiscardingChangesIfAppeared();

                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchToEditor();

                int firstChunkNumber = 1;
                String firstChunk = String.format(EditorTextPageElements.LOADED_CHUNK, firstChunkNumber - 1);
                int secondChunkNumber = 2;
                String secondChunk = String.format(EditorTextPageElements.LOADED_CHUNK, secondChunkNumber - 1);

                editorPage().scrollToChunk(firstChunkNumber);
                String englishLabel = EditorTextPageElements.TEXT_PARAGRAPH_LABEL;
                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1);
                editorTextPage().click(firstChunk + firstPara + englishLabel);

                // add a phrase and mark it up
                editorPage().insertNewParagraphVia(CONTEXT_MENU);
                WebElement highlightedPara = editorPage().getElement(EditorTextPageElements.HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
                editorTextPage().click(EditorTextPageElements.HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
                editorTextPage().sendKeys(UNIQUE_PHRASE);
                editorTextPage().highlightHelper(UNIQUE_PHRASE);
                editorPage().rightClick(String.format(EditorTextPageElements.TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN, UNIQUE_PHRASE));
                editorPage().breakOutOfFrame();
                editorTextContextMenu().markupInsertTargetLinkMarkup();
                insertTargetCiteReferencePage().waitForPageLoaded();
                insertTargetCiteReferencePage().click(InsertTargetCiteReferencePageElements.LOCATE_TARGET_BUTTON);
                insertTargetCiteReferencePage().waitForPageLoaded();
                insertTargetCiteReferencePage().switchToWindow(TargetLocatorPageElements.PAGE_TITLE);

                String hierarchyNode = String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE, "3.4 Ongoing Monitoring");
                targetLocatorPage().click(hierarchyNode);
                //Rightclick on the selected target link using key combination Shift+F10
                editorTextPage().shiftF10UsingAction();
                targetLocatorPage().selectNodeForTargetLinkMarkup();
                insertTargetCiteReferencePage().clickSave();

                // verify markup
                editorPage().switchDirectlyToTextFrame();
                String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 2);
                boolean markupAdded = editorTextPage().doesElementExist(firstChunk +
                        secondPara + EditorTextPageElements.PARATEXT
                        + String.format(EditorTextPageElements.MANUAL_TARGET_LINK_TEXT, UNIQUE_PHRASE));

                // add more phrases to the first chunk
                int phrasesToAddInFirstChunk = 2; // keep this number updated to reflect how many phrases being added below

                String sixthPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 6);
                editorPage().scrollToElement(firstChunk + sixthPara + englishLabel);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE, firstChunk + sixthPara + englishLabel);

                String twelfthPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 12);
                editorPage().scrollToElement(firstChunk + twelfthPara + englishLabel);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE, firstChunk + twelfthPara + englishLabel);

                // add more phrases to the second chunk
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(secondChunkNumber);
                int phrasesToAddInSecondChunk = 4; // keep this number updated to reflect how many phrases being added below

                editorPage().scrollToElement(secondChunk + firstPara);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE,secondChunk + firstPara + englishLabel);

                editorPage().scrollToElement(secondChunk + secondPara + englishLabel);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE,secondChunk + secondPara + englishLabel);

                String fifthPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 5);
                editorPage().scrollToElement(secondChunk + fifthPara + englishLabel);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE,secondChunk + fifthPara + englishLabel);

                String ninthPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 7);
                editorPage().scrollToElement(secondChunk + ninthPara + englishLabel);
                editorTextPage().insertPhraseUnderGivenLabel(UNIQUE_PHRASE,secondChunk + ninthPara + englishLabel);

                // apply all
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(firstChunkNumber);
                editorPage().scrollToTop();
                editorTextPage().click(String.format(EditorTextPageElements.MANUAL_TARGET_LINK_TEXT, UNIQUE_PHRASE) + "/*");
                editorTextPage().rightClick(String.format(EditorTextPageElements.MANUAL_TARGET_LINK_TEXT, UNIQUE_PHRASE) + "/*");
                editorPage().breakOutOfFrame();
                editorTextContextMenu().markupApplyLinkMarkup();
                applyLinkMarkupPage().clickApplyAll();

                int phrasesAddedToBothChunks = phrasesToAddInFirstChunk + phrasesToAddInSecondChunk;
                String expectedAlertTextTemplate = "Finished searching the document.  '%d' replacement(s) made.";
                String expectedAlertText = String.format(expectedAlertTextTemplate, phrasesAddedToBothChunks);
                boolean alertTextShouldBeAsExpected = AutoITUtils.verifyAlertTextAndAccept(true, expectedAlertText);

                browseLinkMarkupPage().waitForPageLoaded();
                browseLinkMarkupPage().clickClose();
                applyLinkMarkupPage().clickCancel();

                // check markup
                editorPage().switchToEditor();
                editorPage().scrollToChunk(firstChunkNumber);
                String targetLinkGreenText = String.format(EditorTextPageElements.TARGET_LINK_TEXT_GREEN, UNIQUE_PHRASE);
                editorPage().scrollTo(firstChunk + targetLinkGreenText);
                int newCiteQueryInTheFirstChunk = editorTextPage().countElements(firstChunk + targetLinkGreenText);
                boolean newCiteQueryShouldAppearInTheFirstChunk = newCiteQueryInTheFirstChunk == phrasesToAddInFirstChunk;

                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(secondChunkNumber);
                editorPage().scrollTo(secondChunk + targetLinkGreenText);
                int newCiteQueryInTheSecondChunk = editorTextPage().countElements(secondChunk + targetLinkGreenText);
                boolean newCiteQueryShouldAppearInTheSecondChunk = newCiteQueryInTheSecondChunk == phrasesToAddInSecondChunk;

                // confirm
                editorToolbarPage().clickConfirmLinkMarkup();

                // reopen
                editorPage().closeCurrentWindowIgnoreDialogue();
                editorPage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchToEditor();

                // check markup
                editorPage().scrollToChunk(firstChunkNumber);
                String targetLinkText = String.format(EditorTextPageElements.TARGET_LINK_TEXT, UNIQUE_PHRASE);
                int citeQueryInTheFirstChunk = editorTextPage().countElements(firstChunk + targetLinkText);
                boolean citeQueryLostGreenHighlightInTheFirstChunk = citeQueryInTheFirstChunk == phrasesToAddInFirstChunk + 1
                                                         // +1 because initial markup is covered with the xpath too
                                                      && !editorTextPage().doesElementExist(targetLinkGreenText);
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(secondChunkNumber);
                editorPage().scrollTo(secondChunk + targetLinkText);
                int citeQueryInTheSecondChunk = editorTextPage()
                        .countElements(secondChunk + targetLinkText);
                boolean citeQueryLostGreenHighlightInTheSecondChunk = citeQueryInTheSecondChunk == phrasesToAddInSecondChunk
                                               && !editorTextPage().doesElementExist(targetLinkGreenText);

                editorPage().closeEditorWithDiscardingChangesIfAppeared();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(markupAdded, "Added phrase should get wrapped with Target Link markup tag"),
                                () -> Assertions.assertTrue(alertTextShouldBeAsExpected, "Alert text should be: '" + expectedAlertText + "'"),
                                () -> Assertions.assertTrue(newCiteQueryShouldAppearInTheFirstChunk, String.format(
                                        "Expected number of green cite queries should be displayed in the first chunk: %d, but found %d",
                                        phrasesToAddInFirstChunk, newCiteQueryInTheFirstChunk)),
                                () -> Assertions.assertTrue(newCiteQueryShouldAppearInTheSecondChunk, String.format(
                                        "Expected number of green cite queries should be displayed in the second chunk: %d, but found: %d",
                                        phrasesToAddInSecondChunk, newCiteQueryInTheSecondChunk)),
                                () -> Assertions.assertTrue(citeQueryLostGreenHighlightInTheFirstChunk, String.format(
                                        "Expected number of cite queries should be displayed without green highlight in the first chunk: %d, but found %d",
                                        phrasesToAddInFirstChunk + 1, citeQueryInTheFirstChunk)),
                                () -> Assertions.assertTrue(citeQueryLostGreenHighlightInTheSecondChunk, String.format(
                                        "Expected number of cite queries should be displayed without green highlight in the second chunk: %d, but found %d",
                                        phrasesToAddInSecondChunk, citeQueryInTheSecondChunk))
                        );
        }

        /**
         * STORY - HALCYONST-10365 <br>
         * SUMMARY -  Manual Links – Full Section <br>
         * USER - LEGAL <br>
         * CONTENT SET - USCA (Development) <br>
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertManualLinkTypeLQTest()
        {
                String uuid = "IC64746DBE08A48C2B984E294DEC68F61";
                int chunkNumber = 1;
                String node = "Determination of controversy as to appointment of electors";
                String subsectionText = "";
                ElementsAttribute expectedElement = new ElementsAttribute(" Manual Edit(Yes) Westlaw Display(Yes) Citation(3USCAS5) Content Set(1000546)",
                        "true","true","LQ","3USCAS5","1000546");
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.USCA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.USCA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + EditorTextPageElements.PARATEXT;

                // add a phrase and mark it up
                addPhraseAndMarkItUp(firstPara, UNIQUE_PHRASE, node, subsectionText);

                editorPage().waitForWindowGoneByTitle(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
                editorTextPage().switchToEditorTextArea();
                ElementsAttribute actualElement = getElementsAttribute(String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1));

                editorPage().closeDocumentAndDiscardChanges();
                assertAllElementsAttribute(actualElement, expectedElement);
        }

        /**
         * STORY - HALCYONST-10923 <br>
         * SUMMARY -  Manual Links – RB <br>
         * USER - LEGAL <br>
         * CONTENT SET - ONLY USCA (Development) <br>
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertManualLinkTypeRBTest()
        {
                String uuid = "IC64746DBE08A48C2B984E294DEC68F61";
                int chunkNumber = 1;
                String node = "Determination of controversy as to appointment of electors";
                String subsectionText = "TEST";
                ElementsAttribute expectedElement = new ElementsAttribute(" Manual Edit(Yes) Westlaw Display(RB) w-pinpoint-page(862a00006de57) Citation(3USCAS5) Content Set(1000546)",
                        "true","true","RB","3USCAS5","1000546");
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.USCA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.USCA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + EditorTextPageElements.PARATEXT;

                // add a phrase and mark it up
                addPhraseAndMarkItUp(firstPara, UNIQUE_PHRASE, node, subsectionText);

                editorPage().waitForWindowGoneByTitle(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
                editorTextPage().switchToEditorTextArea();
                ElementsAttribute actualElement = getElementsAttribute(String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1));

                editorPage().closeDocumentAndDiscardChanges();
                assertAllElementsAttribute(actualElement, expectedElement);
        }

        /**
         * STORY - HALCYONST-10922 <br>
         * SUMMARY -  Manual Links – Subsection level <br>
         * USER - LEGAL <br>
         * CONTENT SET - Texas (Development) <br>
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertManualLinkTypeSPTest()
        {
                String uuid = "IDD9C56F0CBB511D9BC96EEF6E875F343";
                int chunkNumber = 1;
                String node = "Equal rights";
                String subsectionField = "TEST";
                ElementsAttribute expectedElement = new ElementsAttribute(" Manual Edit(Yes) Westlaw Display(SP) w-pinpoint-page(862a00006de57) Citation(TXCNART1S3) Content Set(1000171)",
                        "true","true","SP","TXCNART1S3","1000171");
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.TEXAS_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.TEXAS_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + EditorTextPageElements.PARATEXT;

                // add a phrase and mark it up
                addPhraseAndMarkItUp(firstPara, UNIQUE_PHRASE, node, subsectionField);

                editorPage().waitForWindowGoneByTitle(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
                editorTextPage().switchToEditorTextArea();
                ElementsAttribute actualElement = getElementsAttribute(String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1));

                editorPage().closeDocumentAndDiscardChanges();
                assertAllElementsAttribute(actualElement, expectedElement);
        }

        /**
         * STORY - HALCYONST-10923 <br>
         * SUMMARY -  Manual Links – VP <br>
         * USER - LEGAL <br>
         * CONTENT SET - Pennsylvainia Admin Code(Development) (Any Admin Content Set) <br>
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertManualLinkTypeVPTest()
        {
                String uuid = "IBCBB0460FC4711E7826FD99F8B23008B";
                int chunkNumber = 1;
                String node = "Definitions.";
                String subsectionField = "";
                ElementsAttribute expectedElement = new ElementsAttribute(" Manual Edit(Yes) Westlaw Display(VP) Citation(10PAADCS1.1) Content Set(1000636)",
                        "true","true","VP","10PAADCS1.1","1000636");
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + EditorTextPageElements.PARATEXT;

                // add a phrase and mark it up
                addPhraseAndMarkItUp(firstPara, UNIQUE_PHRASE, node, subsectionField);

                editorPage().waitForWindowGoneByTitle(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
                editorTextPage().switchToEditorTextArea();
                ElementsAttribute actualElement = getElementsAttribute(String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1));

                editorPage().closeDocumentAndDiscardChanges();
                assertAllElementsAttribute(actualElement, expectedElement);
        }

        /**
         * STORY - HALCYONST-10923 <br>
         * SUMMARY -  Manual Links – VP <br>
         * USER - LEGAL <br>
         * CONTENT SET - Pennsylvainia Admin Code(Development) (Any Admin Content Set) <br>
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertManualLinkTypeVSTest()
        {
                String uuid = "IBCBB0460FC4711E7826FD99F8B23008B";
                int chunkNumber = 1;
                String node = "Definitions.";
                String subsectionField = "TEST";
                ElementsAttribute expectedElement = new ElementsAttribute(" Manual Edit(Yes) Westlaw Display(VS) w-pinpoint-page(862a00006de57) Citation(10PAADCS1.1TEST) Content Set(1000636)",
                        "true","true","VS","10PAADCS1.1TEST","1000636");
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + EditorTextPageElements.PARATEXT;

                // add a phrase and mark it up
                addPhraseAndMarkItUp(firstPara, UNIQUE_PHRASE, node, subsectionField);

                editorPage().waitForWindowGoneByTitle(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
                editorTextPage().switchToEditorTextArea();
                ElementsAttribute actualElement = getElementsAttribute(String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1));

                editorPage().closeDocumentAndDiscardChanges();
                assertAllElementsAttribute(actualElement, expectedElement);
        }

        @Test
        @IE_EDGE_MODE
        @RISK
        @LOG
        public void insertTargetLinkWhenSelectingFutureEffectiveDateFirstNodeInDocFamilyTest()
        {
                String initialNodeUuid = "IA79EB0C029A011E6A46DB8CA3AB007EB";
                String testNodeUuid = "I028B5CE129A111E6BDEEB8CA3AB007EB";
                String phrase = String.format(" Test %s", System.currentTimeMillis());

                //Open node in DS editor
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.CROWN_DEPENDENCIES);
                openNodeInDsEditor(initialNodeUuid);

                //Insert phrase into paragraph
                editorTextPage().click(TEXT_PARAGRAPH_SPAN);
                editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
                editorTextPage().sendKeys(Keys.ARROW_DOWN);
                editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
                editorTextPage().sendKeys(Keys.END);
                editorTextPage().sendKeys(phrase);

                //Click Close toolbar button
                editorPage().switchToEditor();
                editorToolbarPage().clickToolbarClose();

                //Set future Effective Date, select Quick Load and click Check-in
                editorPage().closeAndCheckInChangesWithGivenDateAndHandleSpellcheckWindowIfAppeared(
                        DateAndTimeUtils.getTomorrowDateMMddyyyy());
                editorPage().switchToEditor();
                editorPage().closeCurrentWindowIgnoreDialogue();

                //Assert that the initial node has an End Date of a date 1 day prior to the Start Date of the new node
                hierarchyNavigatePage().switchToHierarchyEditPage();
                assertThat(siblingMetadataPage().getSelectedNodeEndDate())
                        .as("The initial node should have an End Date 1 day prior to the Start Date of the new node")
                        .isEqualTo(DateAndTimeUtils.getCurrentDateMMddyyyy());

                String initialNodeValue = siblingMetadataPage().getSelectedNodeValue();

                //Assert that in the hierarchy grid an additional node with the same value of the node just edited is appeared
                siblingMetadataPage().selectSelectedNodesNextNode();
                assertThat(siblingMetadataPage().getSelectedNodeValue())
                        .as("An additional node should have the same value of the node just edited")
                        .isEqualTo(initialNodeValue);

                //Assert that new node has a Start Date of the date selected in the Document Closure window
                assertThat(siblingMetadataPage().getSelectedNodeStartDate())
                        .as("The new node should have a Start Date of the date selected in the Document Closure window")
                        .isEqualTo(DateAndTimeUtils.getTomorrowDateMMddyyyy());

                String additionalNodeUuid = siblingMetadataPage().getSelectedNodeUuid();

                //Open another node in DS editor
                openNodeInDsEditor(testNodeUuid);

                //Highlight phrase in paragraph
                editorTextPage().insertPhraseAndHighlight(phrase.trim());

                //Open Insert Target Cite Reference window and click Locate target
                editorTextPage().ctrlLUsingAction();
                insertTargetCiteReferencePage().switchToInsertTargetCiteReferencePage();
                insertTargetCiteReferencePage().clickLocateTarget();

                //Find and select the new node created, right click and click Select Target for Link
                hierarchySearchPage().searchNodeUuid(additionalNodeUuid);
                targetLocatorPage().switchToTargetLocatorPage();
                WebElement additionalNodeElement = targetLocatorPage().getElement(
                        String.format("(//td[contains(text(),'%s')])[last()]", initialNodeValue));
                String additionalNodeText = additionalNodeElement.getText();
                targetLocatorPage().rightClick(additionalNodeElement);
                targetLocatorPage().selectNodeForTargetLinkMarkup();

                //Assert that the Target field contains the node value for the selected node
                assertThat(insertTargetCiteReferencePage().getElementsAttribute(TARGET_INPUT, "value"))
                        .as("The Target field should contain the node value for the selected node")
                        .isEqualTo(additionalNodeText);

                //Save
                insertTargetCiteReferencePage().clickSave();

                //Assert that the cite reference markup surrounds the highlighted phrase in the editor
                editorPage().switchDirectlyToTextFrame();
                assertThat(editorTextPage().getElementsText(CITE_QUERY_IN_PARAGRAPH))
                        .as("Cite reference markup should surround the highlighted phrase in the editor")
                        .isEqualTo(phrase.trim());

                //Assert that the cite reference markup has correct attributes
                assertThatCiteReferenceMarkupAttributeHasValue("manual-edit", String.valueOf(true));
                assertThatCiteReferenceMarkupAttributeHasValue("w-ref-type", "GO");
                assertThatCiteReferenceMarkupHasNonNullAttributeValue("w-docfamily-uuid");
                assertThatCiteReferenceMarkupHasNonNullAttributeValue("w-pub-number");

                //Close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                editorPage().switchToEditor();
                editorPage().closeCurrentWindowIgnoreDialogue();

                //Go back to the new node we created and delete this new node
                hierarchyNavigatePage().switchToHierarchyEditPage();
                hierarchySearchPage().searchNodeUuid(additionalNodeUuid);
                siblingMetadataPage().deleteSelectedSiblingMetadata();

                //Reset the End Date of the prior node by right clicking the node and selecting Update Metadata
                //Clear the Effective End Date field
                //Click Quick Load
                //Click Update
                hierarchySearchPage().searchNodeUuid(initialNodeUuid);
                siblingMetadataPage().updateSelectedSiblingMetadata();
                updateMetadataPage().clearEffectiveEndDate();
                updateMetadataPage().clickQuickLoadOk();
                hierarchyNavigatePage().switchToHierarchyEditPage();

                //Assert that we now see 'no date' in the End Date cell for this node
                assertThat(siblingMetadataPage().getSelectedNodeEndDate())
                        .as("We should see 'no date' in the End Date cell for initial node")
                        .isEqualTo("no date");
        }

//  ------------- Assistive methods: -------------

        private static class ElementsAttribute
        {
                private String title;
                private String markup ;
                private String manualEdit;
                private String wRefType;
                private String wNormalizedCite;
                private String wPubNumber;

                public ElementsAttribute(String title, String markup, String manualEdit, String wRefType, String wNormalizedCite, String wPubNumber)
                {
                        this.title = title;
                        this.markup = markup;
                        this.manualEdit = manualEdit;
                        this.wRefType = wRefType;
                        this.wNormalizedCite = wNormalizedCite;
                        this.wPubNumber = wPubNumber;
                }

                public ElementsAttribute()
                {
                        this.title = "";
                        this.markup = "";
                        this.manualEdit = "";
                        this.wRefType = "";
                        this.wNormalizedCite = "";
                        this.wPubNumber = "";
                }

                public String getTitle() {
                        return title;
                }

                public void setTitle(String title) {
                        this.title = title;
                }

                public String getMarkup() {
                        return markup;
                }

                public void setMarkup(String markup) {
                        this.markup = markup;
                }

                public String getManualEdit() {
                        return manualEdit;
                }

                public void setManualEdit(String manualEdit) {
                        this.manualEdit = manualEdit;
                }

                public String getWRefType() {
                        return wRefType;
                }

                public void setWRefType(String wRefType) {
                        this.wRefType = wRefType;
                }

                public String getwNormalizedCite() {
                        return wNormalizedCite;
                }

                public void setWNormalizedCite(String wNormalizedCite) {
                        this.wNormalizedCite = wNormalizedCite;
                }

                public String getWPubNumber() {
                        return wPubNumber;
                }

                public void setWPubNumber(String wPubNumber) {
                        this.wPubNumber = wPubNumber;
                }

        }

        private void addPhraseAndMarkItUp(String xpathPara, String phrase, String nodeName, String subsectionField)
        {
                editorTextPage().click(xpathPara);
                editorTextPage().sendKeys(Keys.F7);
                editorTextPage().sendKeys(Keys.DELETE);
                editorTextPage().sendKeys(phrase);
                editorTextPage().doubleClick(xpathPara);
                editorPage().rightClick(xpathPara);
                editorPage().breakOutOfFrame();
                editorTextContextMenu().markupInsertTargetLinkMarkup();
                insertTargetCiteReferencePage().sendTextToSubsection(subsectionField);
                insertTargetCiteReferencePage().clickLocateTarget();
                String hierarchyNode = String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE, nodeName);
                insertTargetCiteReferencePage().click(hierarchyNode);
                insertTargetCiteReferencePage().getElement(hierarchyNode).sendKeys(Keys.SHIFT, Keys.F10);
                targetLocatorPage().selectNodeForTargetLinkMarkup();
                insertTargetCiteReferencePage().clickSave();
        }

        private ElementsAttribute getElementsAttribute (String elementXpath)
        {
                ElementsAttribute actualElement = new ElementsAttribute();
                actualElement.setTitle(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"title"));
                actualElement.setMarkup(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"Markup"));
                actualElement.setManualEdit(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"Manual-edit"));
                actualElement.setWRefType(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"W-ref-type"));
                actualElement.setWNormalizedCite(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"W-normalized-cite"));
                actualElement.setWPubNumber(editorPage().getElementsAttribute(elementXpath + CITE_QUERY,"W-pub-number"));
                return actualElement;
        }

        private void assertAllElementsAttribute(ElementsAttribute actualElement, ElementsAttribute expectedElement)
        {
                Assertions.assertAll
                        (
                                () -> Assertions.assertEquals(expectedElement.getTitle(),actualElement.getTitle(), String.format("Element attribute 'title' is '%s' instead of '%s'",actualElement.getTitle(),expectedElement.getTitle())),
                                () -> Assertions.assertEquals(expectedElement.getMarkup(),actualElement.getMarkup(), String.format("Element attribute 'markup' is '%s' instead of '%s'", actualElement.getMarkup(),expectedElement.getMarkup())),
                                () -> Assertions.assertEquals(expectedElement.getManualEdit(),actualElement.getManualEdit(), String.format("Element attribute 'manual edit' is '%s' instead of '%s'", actualElement.getManualEdit(),expectedElement.getManualEdit())),
                                () -> Assertions.assertEquals(expectedElement.getWRefType(),actualElement.getWRefType(), String.format("Element attribute 'WRefType' is '%s' instead of '%s'", actualElement.getWRefType(),expectedElement.getWRefType())),
                                () -> Assertions.assertEquals(expectedElement.getwNormalizedCite(),actualElement.getwNormalizedCite(),String.format( "Element attribute 'wNormalizedCite' is '%s' instead of '%s'",actualElement.getwNormalizedCite(),expectedElement.getwNormalizedCite())),
                                () -> Assertions.assertEquals(expectedElement.getWPubNumber(),actualElement.getWPubNumber(),String.format("Element attribute 'wPubNumber' is '%s' instead of '%s'", actualElement.getWPubNumber(),expectedElement.getWPubNumber()))

                        );
        }

        private void openNodeInDsEditor(String nodeUuid)
        {
                hierarchySearchPage().searchNodeUuid(nodeUuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchDirectlyToTextFrame();
        }

        private void assertThatCiteReferenceMarkupAttributeHasValue(String attributeName, String expectedValue)
        {
                assertThat(editorTextPage().getElementsAttribute(CITE_QUERY_IN_PARAGRAPH, attributeName))
                        .as("Cite reference markup should have '%s' attribute with '%s' value", attributeName, expectedValue)
                        .isEqualTo(expectedValue);
        }

        private void assertThatCiteReferenceMarkupHasNonNullAttributeValue(String attributeName)
        {
                assertThat(editorTextPage().getElementsAttribute(CITE_QUERY_IN_PARAGRAPH, attributeName))
                        .as("Cite reference markup should have '%s' attribute with 'non null' value", attributeName)
                        .isNotEqualTo("");
        }
}
