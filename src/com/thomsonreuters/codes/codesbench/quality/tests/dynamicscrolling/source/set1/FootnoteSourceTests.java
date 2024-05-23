package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractFootnoteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DELTA_AMEND_SUBSECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOOTNOTE_PARAGRAPH_BOTTOM_OF_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOOTNOTE_REFERENCE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class FootnoteSourceTests extends AbstractFootnoteTests
{
	private static final String SOURCE_RENDITION_APV_UUID = "IC2E80C51B13211EAAA96BB9C7D19D48A";
	private static final String SOURCE_RENDITION_PREP_UUID = "IAFD750F0FD6811E8ADFEF8453224EA08";
	private static final String FOOTNOTE_PARAGRAPH_TITLE = "Footnote Paragraph Bottom of Text";
	private static final String TEXT_PARAGRAPH_XPATH =
			DELTA_AMEND_SUBSECTION + TEXT_PARAGRAPH_SPAN + FOLLOWING_SIBLING_PARATEXT;

	@BeforeEach
	public void openTargetNodeInDsEditor()
	{
		openSourceRenditionInDsEditor(SOURCE_RENDITION_PREP_UUID);
		editorPage().switchToEditorTextFrame();

		//Insert footnote in the first Delta Amend Subsection's text paragraph
		editorPage().scrollToView(format(LOADED_CHUNK, 0) + TEXT_PARAGRAPH_XPATH);
	}

	/**
	 * STORY/BUG - HALCYONST-13221 <br>
	 * SUMMARY - Inserting footnotes in the same delta (Source) <br>
	 * USER - LEGAL <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void insertingFootnotesInTheSameDeltaSourceLegalTest()
	{
		insertFootnoteIntoParagraphInChunk(ZERO_CHUNK);

		assertThatFootnoteReferenceIsAppearedInTextParagraphInChunk(ZERO_CHUNK);
		assertThatDeltaFeatureFootnoteIsInsertedInChunk(ZERO_CHUNK);

		//Insert another footnote in the first Delta Amend Subsection's text paragraph with the same footnote reference
		editorPage().scrollToView(format(LOADED_CHUNK, 0) + TEXT_PARAGRAPH_XPATH);
		insertFootnoteIntoParagraphInChunk(ZERO_CHUNK);

		//Assert that another footnote is not inserted
		assertThat(editorTextPage().countElements(FOOTNOTE_PARAGRAPH_BOTTOM_OF_TEXT + editorTextPage().getModifiedByXpath(user())))
				.as("Another footnote should not be inserted")
				.isEqualTo(1);
		assertThatExpectedErrorIsAppearedInTheEditorMessagePane();
	}

	/**
	 * STORY/BUG - HALCYONST-13221 <br>
	 * SUMMARY - Inserting footnotes in different deltas (Source) <br>
	 * USER - LEGAL <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void insertingFootnotesInDifferentDeltasSourceLegalTest()
	{
		//Insert footnote in the first Delta Amend Subsection's text paragraph
		insertFootnoteIntoParagraphInChunk(ZERO_CHUNK);

		assertThatFootnoteReferenceIsAppearedInTextParagraphInChunk(ZERO_CHUNK);
		assertThatDeltaFeatureFootnoteIsInsertedInChunk(ZERO_CHUNK);

		editorPage().switchToEditorAndScrollToChunk(2);

		//Insert another footnote in the another Delta Amend Subsection's text paragraph with the same footnote reference
		insertFootnoteIntoParagraphInChunk(FIRST_CHUNK);

		assertThatFootnoteReferenceIsAppearedInTextParagraphInChunk(FIRST_CHUNK);
		assertThatDeltaFeatureFootnoteIsInsertedInChunk(FIRST_CHUNK);

		//Assert that expected error message is not appeared in the editor message pane
		editorPage().switchToEditor();
		assertThat(editorMessagePage().checkMessage(EXPECTED_ERROR_MESSAGE))
				.as("The [%s] error message should not be appeared in the editor message pane", EXPECTED_ERROR_MESSAGE)
				.isFalse();
	}

//  ------------- Assistive methods: -------------

	private void openSourceRenditionInDsEditor(String uuid)
	{
		sourcePage().goToSourcePageWithRenditionUuids(uuid);
		sourceNavigateGridPage().firstRenditionEditContent();
	}

	private void insertFootnoteIntoParagraphInChunk(int chunkNumber)
	{
		editorTextPage().click(format(LOADED_CHUNK, chunkNumber) + TEXT_PARAGRAPH_XPATH);
		editorPage().openInsertFootnoteDialog();
		insertFootnotePage().setFootnoteReference(FOOTNOTE_REFERENCE_VALUE_1);
		insertFootnotePage().clickSaveFootnote();
		editorPage().switchDirectlyToTextFrame();
	}

	private void assertThatFootnoteReferenceIsAppearedInTextParagraphInChunk(int chunkNumber)
	{
		assertThat(editorTextPage().getElementsText(format(LOADED_CHUNK, chunkNumber) + TEXT_PARAGRAPH_XPATH + FOOTNOTE_REFERENCE))
				.as(format("Footnote reference [%s] should be appeared in text paragraph", FOOTNOTE_REFERENCE_VALUE_1))
				.isEqualTo(FOOTNOTE_REFERENCE_VALUE_1);
	}

	private void assertThatDeltaFeatureFootnoteIsInsertedInChunk(int chunkNumber)
	{
		assertThat(editorTextPage().getElementsText(format(LOADED_CHUNK, chunkNumber)
				+ HIGHLIGHTED_PARA
				+ format(SPAN_BY_TEXT, FOOTNOTE_PARAGRAPH_TITLE)
				+ FOLLOWING_SIBLING_PARATEXT
				+ "/super"))
				.as(format("[%s] should be inserted with footnote reference [%s]", FOOTNOTE_PARAGRAPH_TITLE, FOOTNOTE_REFERENCE_VALUE_1))
				.isEqualTo(FOOTNOTE_REFERENCE_VALUE_1);
	}
}
