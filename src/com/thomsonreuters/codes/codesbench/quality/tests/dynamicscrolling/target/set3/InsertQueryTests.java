package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class InsertQueryTests extends TestService
{
    /* Insert Date Query
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Select and right click a text paragraph here
     * 4. Go to Insert Query
     * VERIFY: Create Query Note window appears
     * 5. Set Type: DATE
     * 6. Set Action Date: a future date
     * 7. Set Query Note Text: Hello bob
     * 8. Click Save
     *
     * VERIFY: A Query is inserted on the Text Paragraph.
     * VERIFY: The query type is Date Query
     * VERIFY: The Action Date is the date you set
     * VERIFY: The Query Text is the text you set
     *
     * 9. Close and check-in changes
     * 10. Reopen the document
     * 11. Scroll to chunk 2 or 3
     * VERIFY: The query we inserted appears
     *
     * 12. Delete the query
     * 13. VERIFY: The query is deleted
     */
    /* Insert Contingency Query
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Select and right click a text paragraph here
     * 4. Go to Insert Query
     * VERIFY: Create Query Note window appears
     * 5. Set Type: CONTINGENCY
     * 6. Set Action Date: a future date
     * 7. Set Query Note Text: Hello bob
     * 8. Click Save
     *
     * VERIFY: A Query is inserted on the Text Paragraph.
     * VERIFY: The query type is Contingency Query
     * VERIFY: The Query Text is the text you set
     *
     * 9. Close and check-in changes
     * 10. Reopen the document
     * 11. Scroll to chunk 2 or 3
     * VERIFY: The query we inserted appears
     *
     * 12. Delete the query
     * 13. VERIFY: The query is deleted
     */
    /* Insert Critical Issue Query
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Select and right click a text paragraph here
     * 4. Go to Insert Query
     * VERIFY: Create Query Note window appears
     * 5. Set Type: CRITICAL_ISSUE
     * 7. Set Query Note Text: Hello bob
     * 8. Click Save
     *
     * VERIFY: A Query is inserted on the Text Paragraph.
     * VERIFY: The query type is Critical Issue Query
     * VERIFY: The Query Text is the text you set
     *
     * 9. Close and check-in changes
     * 10. Reopen the document
     * 11. Scroll to chunk 2 or 3
     * VERIFY: The query we inserted appears
     *
     * 12. Delete the query
     * 13. VERIFY: The query is deleted
     */
    /* Insert Revisor Query
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Select and right click a text paragraph here
     * 4. Go to Insert Query
     * VERIFY: Create Query Note window appears
     * 5. Set Type: REVISOR
     * 6. Set Action Date: a future date
     * 7. Set Query Note Text: Hello bob
     * 8. Click Save
     *
     * VERIFY: A Query is inserted on the Text Paragraph.
     * VERIFY: The query type is Revisor Query
     * VERIFY: The Action Date is the date you set
     * VERIFY: The Query Text is the text you set
     *
     * 9. Close and check-in changes
     * 10. Reopen the document
     * 11. Scroll to chunk 2 or 3
     * VERIFY: The query we inserted appears
     *
     * 12. Delete the query
     * 13. VERIFY: The query is deleted
     */
    /* Insert Tabular Query
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Select and right click a text paragraph here
     * 4. Go to Insert Query
     * VERIFY: Create Query Note window appears
     * 5. Set Type: TABULAR
     * 7. Set Query Note Text: Hello bob
     * 8. Click Save
     *
     * VERIFY: A Query is inserted on the Text Paragraph.
     * VERIFY: The query type is Tabular Query
     * VERIFY: The Query Text is the text you set
     *
     * 9. Close and check-in changes
     * 10. Reopen the document
     * 11. Scroll to chunk 2 or 3
     * VERIFY: The query we inserted appears
     *
     * 12. Delete the query
     * 13. VERIFY: The query is deleted
     */
    private static Stream<Arguments> provideDataForInsertDateQueryTargetLegalTest()
    {
        String phrase = "Hello Bob";
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        return Stream.of(
                Arguments.of("Date", EditorTextPageElements.QUERY_NOTE_TYPE_DATE, new String[] {phrase, date}),
                Arguments.of("Revisor", EditorTextPageElements.QUERY_NOTE_TYPE_REVISOR, new String[] {phrase, date}),
                Arguments.of("Contingency", EditorTextPageElements.QUERY_NOTE_TYPE_CONTINGENCY, new String[] {phrase, date}),
                Arguments.of("Critical Issue", EditorTextPageElements.QUERY_NOTE_TYPE_CRITICAL_ISSUE, new String[] {phrase}),
                Arguments.of("Tabular", EditorTextPageElements.QUERY_NOTE_TYPE_TABULAR, new String[] {phrase})
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForInsertDateQueryTargetLegalTest")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertDateQueryTargetLegalTest(String queryType, String elementXpath, String[] queryContent)
    {
        String uuid = "I4DA87570055F11DCBC5783D1051FCA68";
        int targetChunk = 2;
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().scrollToChunk(targetChunk);
        while(editorTextPage().doesElementExist(EditorTextPageElements.QUERY_NOTE_BLOCK))
        {
            editorTextPage().rightClickQuery();
            editorTextPage().breakOutOfFrame();
            editorQueryContextMenu().delete();
            deleteQueryNotePage().clickDeleteButton();
            editorPage().switchToEditor();
            editorTextPage().switchToEditorTextArea();
        }

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        editorTextPage().scrollToView(firstParaSpan);
        editorTextPage().click(firstParaSpan);
        editorTextPage().rightClick(firstParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertQuery();
        queryNotePage().setType(queryType.toUpperCase().replace(" ", "_"));
        queryNotePage().setQueryNoteText(queryContent[0]);

        if (queryContent.length == 2)
        {
            queryNotePage().setQueryNoteActionDateToCurrentDate();
        }
        queryNotePage().save();
        editorTextPage().switchToEditorTextArea();

        // check note inserted and check in
        boolean queryDisplaysExpectedContent = editorTextPage().checkQueryContent(queryType, queryContent, elementXpath);
		editorPage().closeAndCheckInChanges();

		if(queryType.equals("Critical Issue"))
        {
            AutoITUtils.verifyAlertTextAndAccept(true, "Document validation failed, continue with check-in?");
        }

		// reopen DS
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);

		// check query is still here
		boolean queryDisplaysExpectedContentAfterReopening =
                editorTextPage().checkQueryContent(queryType, queryContent, elementXpath);

        // delete query
        editorTextPage().scrollToView(EditorTextPageElements.QUERY_NOTE_OF_ANY_TYPE);
        editorTextPage().rightClickQuery();
        editorTextPage().breakOutOfFrame();
        editorQueryContextMenu().delete();
        deleteQueryNotePage().clickDeleteButton();
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();
        boolean queryIsStillShownAfterDeleting = editorTextPage().doesElementExist(elementXpath);
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll(
               () -> Assertions.assertTrue(queryDisplaysExpectedContent,
                      "Query should display expected content"),
                () -> Assertions.assertTrue(queryDisplaysExpectedContentAfterReopening,
                        "Query should display expected content after reopening"),
                () -> Assertions.assertFalse(queryIsStillShownAfterDeleting,
                        "Query should disappear after deleting")
        );
    }
}
