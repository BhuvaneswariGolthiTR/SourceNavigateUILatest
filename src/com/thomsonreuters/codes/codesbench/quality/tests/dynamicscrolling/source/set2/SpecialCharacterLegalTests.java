package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSpecialCharacterLegalTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA_BY_DISPLAY_NAME;

public class SpecialCharacterLegalTests extends AbstractSpecialCharacterLegalTests
{
	private static final String RENDITION_UUID = "I2B6D1EE043EA11E8B306BDCAB99DF270";
	private static final String PARA_SOURCE_SECTION_NUMBER = String.format(PARA_BY_DISPLAY_NAME, "Source Section Number");

	@BeforeEach
	public void openRenditionInDsEditor()
	{
		sourcePage().goToSourcePageWithRenditionUuids(RENDITION_UUID);
		sourceNavigateGridPage().firstRenditionEditContent();
		editorPage().switchDirectlyToTextFrame();
	}

	/**
	 * STORY/BUG - HALCYONST-11161 <br>
	 * SUMMARY - Replacing special characters updates Modified by tags (Source) <br>
	 * USER - LEGAL <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void replacingSpecialCharactersUpdatesModifiedByTagsSourceLegalTest()
	{
		KEYS_AND_SPECIAL_CHARACTERS_MAP.keySet().forEach(key ->
		{
			//Assert that 'Modified by' tag does not exist before replacing special character
			assertThatModifiedByTagDoesNotExistInElement(PARA_SOURCE_SECTION_NUMBER);

			//Replace special character
			replaceSpecialCharacterInElementWithKey(PARA_SOURCE_SECTION_NUMBER + PARATEXT, key);

			//Assert that correct special character inserted after replacing
			assertThatExpectedSpecialCharacterIsAppearedInTheElement(
					KEYS_AND_SPECIAL_CHARACTERS_MAP.get(key), PARA_SOURCE_SECTION_NUMBER + PARATEXT);
			//Assert that 'Modified by' tag exists after replacing special character
			assertThatModifiedByTagExistsInElement(PARA_SOURCE_SECTION_NUMBER);

			undoChanges();
		});
	}
}
