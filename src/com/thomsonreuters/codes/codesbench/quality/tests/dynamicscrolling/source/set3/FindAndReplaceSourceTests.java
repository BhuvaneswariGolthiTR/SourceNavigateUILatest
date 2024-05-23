package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractFindAndReplaceTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindAndReplaceSourceTests extends AbstractFindAndReplaceTests
{
	private static final String RENDITION_UUID = "I56B71CE0BB2311E7AE1DDC37F9621E15"; //for DEV: UUID = "I0AA0C20034FC11E7ACDACFC8F254A8C8";

	@BeforeEach
	protected void openDocument(TestInfo testInfo)
	{
		if(!testInfo.getDisplayName().equals("findAndReplaceForElementsWithHintsTest()"))
		{
			loginAndOpenSourceRenditionWithDSEditor(RENDITION_UUID);
		}
	}

	@AfterEach
	protected void closeDocument()
	{
		editorPage().closeEditorWithDiscardingChangesIfAppeared();
	}

	/*
	 * STORY/BUG - HALCYONST-13789
	 * SUMMARY - Find functionality in the elements with hints (Source)
	 * USER - LEGAL
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void findAndReplaceForElementsWithHintsTest()
	{
		String renditionUuid = "I20DB18804C4711EAA224F7A255AA9973";
		String repealedByPhrase = "Repealed by";
		String phraseToReplace = String.format("Autotest %s", System.currentTimeMillis());
		int expectedHintsSize = 2;
		int expectedSectionNumber = 4;

		loginAndOpenSourceRenditionWithDSEditor(renditionUuid);

		openFindAndReplaceDialogAndFind(repealedByPhrase, false);
		assertTrue(AutoITUtils.verifyAlertTextAndCancel(false),
				String.format(PHRASE_S_WASNT_FOUND, repealedByPhrase));

		editorPage().switchDirectlyToTextFrame();
		editorTextPage().sendKeys(phraseToReplace);
		int actualSectionNumber = Integer.parseInt(
				editorTextPage()
						.getElement(String.format(EditorTextPageElements.SOURCE_SECTION_BY_PARATEXT, phraseToReplace))
						.getAttribute("num"));

		String hintsXpath = EditorTextPageElements.SOURCE_BODY
				+ EditorTextPageElements.SOURCE_SECTION
				+ String.format("[@num='%d']/delta/part", expectedSectionNumber)
				+ EditorTextPageElements.SUBSECTION
				+ EditorTextPageElements.PARA
				+ EditorTextPageElements.PARATEXT
				+ "/hint";
		int actualHintsSize = editorTextPage().getElements(hintsXpath).size();

		assertAll(
				() -> Assertions.assertEquals(expectedHintsSize, actualHintsSize,
						String.format("The number of presented hints should be equal to %d", expectedHintsSize)),
				() -> Assertions.assertEquals(expectedSectionNumber, actualSectionNumber,
						String.format("Phrase %s should be found in %d source section", repealedByPhrase, expectedSectionNumber))
		);
	}

//	------------- Assistive methods: -------------

	private void loginAndOpenSourceRenditionWithDSEditor(String renditionUuid) {
		homePage().goToHomePage();
		loginPage().logIn();
		sourcePage().goToSourcePageWithRenditionUuids(renditionUuid);
		openInFocusNodeInDSEditor();
		editorPage().closeEditorWithDiscardingChangesIfAppeared();
		sourcePage().switchToSourceNavigatePage();
		openInFocusNodeInDSEditor();
	}

	private void openInFocusNodeInDSEditor() {
		sourceNavigateGridPage().firstRenditionEditContent();
		editorPage().switchToEditor();
	}
}
