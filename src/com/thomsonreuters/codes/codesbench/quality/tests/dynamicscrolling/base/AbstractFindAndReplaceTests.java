package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractFindAndReplaceTests extends TestService
{
	private static final String CASE_SENSITIVE_PHRASE_1 = "pIcKlEpAsTeBuRgErS";
	private static final String CASE_SENSITIVE_PHRASE_2 = "picklePasteBurgers";
	private static final String REPLACEMENT_PHRASE = "tickleMeElmo";
	private static final String END_OF_DOCUMENT_MESSAGE = "The End of the document has been reached.  Would you like to continue from the beginning?";
	private static final String END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND = "The end-of-a-document alert is expected here, but wasn't found!";
	protected static final String COULDNT_FIND_S_MESSAGE = "Couldn't find '%s' in the Document.";
	private static final String PHRASE_S_SHOULDNT_BE_PRESENT = "Phrase \"%s\" shouldn't be present!";
	protected static final String PHRASE_S_WASNT_FOUND = "Something went wrong. Phrase \"%s\" wasn't found!";
	private static final int SECOND_CHUNK = 2;
	private static final int FOURTH_CHUNK = 4;
	private SoftAssertions softAssertions;

	@BeforeEach
	protected abstract void openDocument(TestInfo testInfo);

	@AfterEach
	protected abstract void closeDocument();

	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void caseSensitiveFindAndReplaceTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);
		softAssertions = new SoftAssertions();
		//verifying one of the phrases was found and replaced:
		openFindAndReplaceDialogAndFind(CASE_SENSITIVE_PHRASE_2, true);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_2)),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_2));
		setReplacementAndReplaceBy(REPLACEMENT_PHRASE);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(true, END_OF_DOCUMENT_MESSAGE),
			END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND);
		//verifying if CASE_SENSITIVE_PHRASE_2 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);
		switchToEditorAndClickToTopParagraphSpan();
		//verifying if CASE_SENSITIVE_PHRASE_1 phrase wasn't replaced:
		softAssertPhraseWasNotReplaced(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK);

		softAssertions.assertAll();
	}

	/*
	 * Not case sensitive find and replace
	 * 1. open document
	 * 2. scroll to chunk 2
	 * 3. insert a specific piece of text, such as "pIcKlEpAsTeBuRgErS"
	 * 4. scroll to chunk 4
	 * 5. insert a specific piece of text, such as "picklePasteBurgers"
	 * 6. scroll to chunk 1 - it is important to the 1st chunk because it was found during testing that find and replace didn't work across chunks
	 * 7. hit ctrl + f to invoke find and replace functionality
	 * VERIFY: Find and Replace window appears
	 * 8. set find field to "picklePasteBurgers"
	 * 9. set the replace field to "tickle me elmo"
	 * 10. uncheck the case sensitive check box
	 * 11. click find
	 * VERIFY: We are brought to the phrase "pIcKlEpAsTeBuRgErS" in the editor in the 2nd chunk
	 * 12. go back to the find and replace window
	 * 13. click replace
	 * VERIFY: "pIcKlEpAsTeBuRgErS" is replaced with "tickle me elmo" in the editor
	 * VERIFY: "picklePasteBurgers" is not replaced with "tickle me elmo" in the editor  ???checking this on 15 step
	 * 14. go back to the find and replace window
	 * 15. click find
	 * VERIFY: We are brought to the phrase "picklePasteBurgers" in the editor in the 4th chunk
	 * 16. go back to the find and replace window
	 * 17. click replace
	 * VERIFY: "picklePasteBurgers" is replaced with "tickle me elmo" in the editor
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void caseInsensitiveFindAndReplaceTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions = new SoftAssertions();

		//trying to find first occurrence - success:
		openFindAndReplaceDialogAndFind(CASE_SENSITIVE_PHRASE_1, false);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_1)),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_1));

		//trying to replace first occurrence and find second - success:
		setReplacementAndReplaceBy(REPLACEMENT_PHRASE);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_1)),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_1));

		//trying to replace second occurrence - success
		//and find next occurrence - fail:
		findAndReplacePage().clickReplaceButton();
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(true, END_OF_DOCUMENT_MESSAGE),
				END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND);
		switchToEditorAndClickToTopParagraphSpan();
		//verifying if CASE_SENSITIVE_PHRASE_1 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK);

		//verifying if CASE_SENSITIVE_PHRASE_2 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions.assertAll();
	}

	/*
	 * Replace all case sensitive
	 * 1. open document
	 * 2. scroll to chunk 2
	 * 3. insert a specific piece of text, such as "pIcKlEpAsTeBuRgErS "
	 * 4. scroll to chunk 4
	 * 5. insert a specific piece of text, such as "pIcKlEpAsTeBuRgErS "
	 * 6. scroll to chunk 1 - it is important to the 1st chunk because it was found during testing that find and replace didn't work across chunks
	 * 7. hit ctrl + f to invoke find and replace functionality
	 * VERIFY: Find and Replace window appears
	 * 8. set find field to "pIcKlEpAsTeBuRgErS " - it is important to have inserted this phrase in the 4th chunk because it was found during testing that find and replace didn't work across chunks
	 * 9. set the replace field to "tickle me elmo"
	 * 10. check the case sensitive check box
	 * 11. click replace all
	 * VERIFY: Only the 1 instance of "pIcKlEpAsTeBuRgErS " was found and replaced by "tickle me elmo"
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findAndReplaceAllCaseSensitiveTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions = new SoftAssertions();

		//verifying one of the phrases was replaced:
		openFindAndReplaceDialogAndReplaceAll(CASE_SENSITIVE_PHRASE_2, REPLACEMENT_PHRASE, true);
		softAssertions.assertThat(
				AutoITUtils.verifyAlertTextContainsAndAccept(true, "Finished searching the document.  '1' replacement(s) made."))
				.overridingErrorMessage("Alert pop-up should contain info about 1 phrase replacement!")
				.isTrue();

		switchToEditorAndClickToTopParagraphSpan();
		//verifying if CASE_SENSITIVE_PHRASE_1 phrase wasn't replaced:
		softAssertPhraseWasNotReplaced(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK);

		//verifying if CASE_SENSITIVE_PHRASE_2 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions.assertAll();
	}

	/*
	 * Replace all not case sensitive
	 * 1. open document
	 * 2. scroll to chunk 2
	 * 3. insert a specific piece of text, such as "pIcKlEpAsTeBuRgErS "
	 * 4. scroll to chunk 4
	 * 5. insert a specific piece of text, such as "pIcKlEpAsTeBuRgErS "
	 * 6. scroll to chunk 1 - it is important to the 1st chunk because it was found during testing that find and replace didn't work across chunks
	 * 7. hit ctrl + f to invoke find and replace functionality
	 * VERIFY: Find and Replace window appears
	 * 8. set find field to "pIcKlEpAsTeBuRgErS " - it is important to have inserted this phrase in the 4th chunk because it was found during testing that find and replace didn't work across chunks
	 * 9. set the replace field to "tickle me elmo"
	 * 10. uncheck the case sensitive check box
	 * 11. click replace all
	 * VERIFY: Both instances of "pIcKlEpAsTeBuRgErS " and "pIcKlEpAsTeBuRgErS " were found and replaced by "tickle me elmo".  we should see an alert telling us how many instances were replaced
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findAndReplaceAllCaseInsensitiveTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions = new SoftAssertions();

		//verifying notification pop-up contains info about 2 phrases replacements:
		openFindAndReplaceDialogAndReplaceAll(CASE_SENSITIVE_PHRASE_2, REPLACEMENT_PHRASE, false);
		softAssertions.assertThat(
				AutoITUtils.verifyAlertTextContainsAndAccept(true, "Finished searching the document.  '2' replacement(s) made."))
				.overridingErrorMessage("Alert pop-up should contain info about 2 phrases replacements!")
				.isTrue();
		switchToEditorAndClickToTopParagraphSpan();
		//verifying if CASE_SENSITIVE_PHRASE_1 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK);

		//verifying if CASE_SENSITIVE_PHRASE_2 phrase replacement was successful:
		softAssertPhraseWasCorrectlyReplaced(CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		softAssertions.assertAll();
	}

	/*
	 * Find and End of document reached
	 * 1. Open document
	 * 2. scroll to chunk 4
	 * 3. insert a piece of text that wouldn't normally exist in the document
	 * 4. scroll up to chunk 1
	 * 5. hit ctrl + f
	 * VERIFY: find and replace window appears
	 * 6. set the find field to text that wouldn't exist in the document normally
	 * 7. click find next
	 * VERIFY: we are brought to the piece of text we inserted in chunk 4
	 * 8. switch back to the find and replace window
	 * 9. click find next
	 * VERIFY: we get an alert saying we've reached the end of the document. this also asks if we want to start our search over from the beginning
	 * 10. click ok in the alert
	 * VERIFY: we are brought to the piece of text we inserted in chunk 4
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findAndReplaceEndOfDocumentReachedTest()
	{
		String uniquePhrase = CASE_SENSITIVE_PHRASE_1 + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		insertPhraseToChunkAndClickToTopParagraph(uniquePhrase, FOURTH_CHUNK);

		//trying to find first occurrence - success:
		openFindAndReplaceDialogAndFind(uniquePhrase, false);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, uniquePhrase)),
				format(PHRASE_S_WASNT_FOUND, uniquePhrase));

		//trying to find next occurrence - fail:
		findAndReplacePage().clickFindButton();
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(true, END_OF_DOCUMENT_MESSAGE),
				END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND);
	}	
		
	/*
	 * Find and replace all with no results
	 * 1. open document
	 * 2. hit ctrl + f
	 * VERIFY: find and replace window appears
	 * 3. set find field to text that wouldn't normally exist in the document
	 * 4. click find next
	 * VERIFY: we get an alert saying the find and replace functionality couldn't find the text in the document
	 * 5. accept this alert
	 * 6. switch back to the find and replace window
	 * 7. click replace all
	 * VERIFY: we get an alert saying the find and replace functionality couldn't find the text in the document
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findAndReplaceAllNoResultsTest()
	{
		String uniquePhrase = CASE_SENSITIVE_PHRASE_1 + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		switchToEditorAndClickToTopParagraphSpan();

		//trying to find first occurrence - fail:
		openFindAndReplaceDialogAndFind(uniquePhrase, false);
		assertTrue(AutoITUtils.verifyAlertTextAndAccept(true, format(COULDNT_FIND_S_MESSAGE, uniquePhrase)),
				format(PHRASE_S_SHOULDNT_BE_PRESENT, uniquePhrase));

		//trying to replace all occurrences - fail:
		setReplacementAndReplaceAllBy(REPLACEMENT_PHRASE);

		//Handle an alert with no visible text for autoit
		AutoITUtils.verifyAlertTextAndAccept(true);
		findAndReplacePage().close();
		editorPage().switchDirectlyToTextFrame();
		editorTextPage().insertPhrase(String.valueOf(System.currentTimeMillis()));
		openFindAndReplaceDialogAndReplaceAll(uniquePhrase, REPLACEMENT_PHRASE, false);

		assertTrue(AutoITUtils.verifyAlertTextAndAccept(true, format(COULDNT_FIND_S_MESSAGE, uniquePhrase)),
				format(PHRASE_S_SHOULDNT_BE_PRESENT, uniquePhrase));
	}

	/*
	 * Find in chunks case sensitive
	 * 1. open document
	 * 2. scroll to chunk 4
	 * 3. insert a piece of text that probably wouldn't normally appear in the content
	 * 4. scroll to another chunk
	 * 5. insert a piece of text similar to that in step 3, but change the capitalization of a few of the characters
	 * 6. hit ctrl + alt + f
	 * VERIFY: find in chunks window appears
	 * 7. set the find field to the text from step 3
	 * 8. check case sensitive check box
	 * 9. click find next
	 * NOTE: if you inserted the text from step 5 in a chunk above that inserted in step 3,
	 * you're not going to get an end of document alert.  Otherwise, you'll get an end of document alert and you can accept it
	 * VERIFY: the text inserted from step 3 was found in the editor
	 * 10. switch to the find in chunks window
	 * 11. click find next
	 * VERIFY: an end of document alert appears, and the functionality doesn't find the text from step 5
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findInChunksCaseSensitiveTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		//trying to find first occurrence - success:
		openFindInChunksDialogAndFind(CASE_SENSITIVE_PHRASE_2, true);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_2)),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_2));

		//trying to find next occurrence - fail:
		findInChunksPage().clickFindButton();
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(true, END_OF_DOCUMENT_MESSAGE),
				END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND);
	}

	/*
	 * *Find in chunks not case sensitive
	 * 1. open document
	 * 2. scroll to chunk 4
	 * 3. insert a piece of text that probably wouldn't normally appear in the content
	 * 4. scroll to another chunk
	 * 5. insert a piece of text similar to that in step 3, but change the capitalization of a few of the characters
	 * 5.1. Scroll to chunk 1
	 * 6. hit ctrl + alt + f
	 * VERIFY: find in chunks window appears
	 * 7. set the find field to the text from step 3
	 * 8. uncheck case sensitive check box
	 * 9. click find next
	 * VERIFY: the text inserted from step 3 was found in the editor
	 * 10. switch to the find in chunks window
	 * 11. click find next
	 * VERIFY: the text inserted from step 5 was found in the editor
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findInChunksCaseInsensitiveTest()
	{
		insertPhraseToChunkAndClickToTopParagraph(CASE_SENSITIVE_PHRASE_1, SECOND_CHUNK, CASE_SENSITIVE_PHRASE_2, FOURTH_CHUNK);

		//trying to find first occurrence - success:
		openFindInChunksDialogAndFind(CASE_SENSITIVE_PHRASE_2, false);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_2)),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_2));

		//trying to find second occurrence - success:
		//openFindInChunksDialogAndFind(CASE_SENSITIVE_PHRASE_2, false);
		editorPage().openFindInChunksDialog();
		findInChunksPage().clickFindButton();

		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false, format(COULDNT_FIND_S_MESSAGE, CASE_SENSITIVE_PHRASE_2 + "777777777777777777")),
				format(PHRASE_S_WASNT_FOUND, CASE_SENSITIVE_PHRASE_2));

		//trying to find next occurrence - fail:
		findInChunksPage().clickFindButton();
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(true, END_OF_DOCUMENT_MESSAGE),
				END_OF_DOCUMENT_EXPECTED_BUT_NOT_FOUND);
	}

	/*
	 * Find in chunks no results
	 * You can follow similar steps in the find in chunks test above, but you'll verify that the end of document has been reach alert appears
	 * after clicking find next
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findInChunksNoResultsTest()
	{
		String uniquePhrase = CASE_SENSITIVE_PHRASE_1 + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		switchToEditorAndClickToTopParagraphSpan();

		//trying to find any occurrence - fail:
		openFindInChunksDialogAndFind(uniquePhrase, false);
		assertTrue(AutoITUtils.verifyAlertTextAndAccept(true, format(COULDNT_FIND_S_MESSAGE, uniquePhrase)),
				format(PHRASE_S_SHOULDNT_BE_PRESENT, uniquePhrase));
	}

	/**
	 * STORY/BUG - HALCYONST-14915, HALCYONST-15016
	 * SUMMARY - Special symbols search and replacement
	 */
	@ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
	@CsvSource(value = {
			".", "\\", "/", "|", "*", "?", "!", "[", "]", "{", "}", "(", ")", "^", "+", "-", "=", ":", ";", "$", "&"
	})
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findSpecialSymbolsTest(String symbol)
	{
		String specialSymbolSWasNotFound = "Special symbol \"%s\" wasn't found";
		softAssertions = new SoftAssertions();

		openFindAndReplaceDialogAndReplaceAll(symbol, "", false);
		AutoITUtils.verifyAlertTextAndAccept(true);
		findAndReplacePage().close();
		switchToEditorAndClickToTopParagraphSpan();
		editorTextPage().insertPhraseUnderParaInChunk(format(" %s ", symbol), 3, FOURTH_CHUNK);
		//trying to find the occurrence with "Find in chunks":
		switchToEditorAndClickToTopParagraphSpan();
		// openFindInChunksDialogAndFind(symbol, false);
		editorPage().openFindInChunksDialog();
		findInChunksPage().clickFindButton();

		softAssertions.assertThat(AutoITUtils.verifyAlertTextAndCancel(false))
				.overridingErrorMessage(specialSymbolSWasNotFound + " by \"Find in chunks\"", symbol)
				.isTrue();
		findInChunksPage().close();

		//trying to find the occurrence with "Find and replace":
		switchToEditorAndClickToTopParagraphSpan();
		openFindAndReplaceDialogAndFind(symbol, false);

		//Handle end of document alert message
		AutoITUtils.verifyAlertTextAndAccept(true, END_OF_DOCUMENT_MESSAGE);

		softAssertions.assertThat(AutoITUtils.verifyAlertTextAndCancel(false))
				.overridingErrorMessage(specialSymbolSWasNotFound + " by \"Find and replace\"", symbol)
				.isTrue();

		softAssertions.assertAll();
	}

//	------------- Assistive methods: -------------

	protected void insertPhraseToChunkAndClickToTopParagraph(
			String phrase1, int chunk1Number, String phrase2, int chunk2Number)
	{
		insertPhraseToChunk(phrase1, chunk1Number);
		insertPhraseToChunkAndClickToTopParagraph(phrase2, chunk2Number);
	}

	protected void insertPhraseToChunkAndClickToTopParagraph(String phrase, int chunkNumber)
	{
		insertPhraseToChunk(phrase, chunkNumber);
		switchToEditorAndClickToTopParagraphSpan();
	}

	protected void insertPhraseToChunk(String phrase, int chunkNumber)
	{
		editorTextPage().insertPhraseUnderParaInChunk(phrase, 2, chunkNumber);
		assertTrue(editorPage().checkForPhraseInParatext(phrase), format("Phrase \"%s\" insertion failed!", phrase));
	}

	protected void switchToEditorAndClickToTopParagraphSpan()
	{
		editorPage().switchToEditorAndScrollToChunk(1);
		editorTextPage().click(
				format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK, 0, 1) + EditorTextPageElements.SPAN);
	}

	protected void openFindInChunksDialogAndFind(String searchPhrase, boolean caseSensitive)
	{
		editorPage().openFindInChunksDialog();
		findInChunksPage().setFindTerm(searchPhrase, caseSensitive);
		findInChunksPage().clickFindButton();
	}

	protected void openFindAndReplaceDialogAndFind(String searchPhrase, boolean caseSensitive)
	{
		editorPage().openFindAndReplaceDialog();
		findAndReplacePage().setFindTerm(searchPhrase, caseSensitive);
		findAndReplacePage().clickFindButton();
	}

	protected void openFindAndReplaceDialogAndReplaceAll(String searchPhrase, String replacementPhrase, boolean caseSensitive)
	{
		editorPage().openFindAndReplaceDialog();
		findAndReplacePage().setFindTerm(searchPhrase, caseSensitive);
		setReplacementAndReplaceAllBy(replacementPhrase);
	}

	protected void setReplacementAndReplaceBy(String replacementPhrase)
	{
		findAndReplacePage().setReplaceTerm(replacementPhrase);
		findAndReplacePage().clickReplaceButton();
	}

	protected void setReplacementAndReplaceAllBy(String replacementPhrase)
	{
		findAndReplacePage().setReplaceTerm(replacementPhrase);
		findAndReplacePage().clickReplaceAllButton();
	}

	protected void softAssertPhraseWasNotReplaced(String phrase, int phrasesChunkNumber) {
		editorPage().switchToEditorAndScrollToChunk(phrasesChunkNumber);
		softAssertions.assertThat(phraseReplacedCorrectly(phrase))
				.overridingErrorMessage("Phrase \"%s\" have to be present within document structure", phrase)
				.isFalse();
	}

	protected void softAssertPhraseWasCorrectlyReplaced(String phrase, int phrasesChunkNumber) {
		editorPage().switchToEditorAndScrollToChunk(phrasesChunkNumber);
		softAssertions.assertThat(phraseReplacedCorrectly(phrase))
				.overridingErrorMessage(
						"Phrase \"%s\" should have been replaced by \"%s\" in document structure", phrase, REPLACEMENT_PHRASE)
				.isTrue();
	}

	protected boolean phraseReplacedCorrectly(String phrase) {
		return !editorPage().checkForPhraseInParatext(phrase) && editorPage().checkForPhraseInParatext(REPLACEMENT_PHRASE);
	}
}
