package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class PubTagsTests extends TestService
{
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void pubtagsSourceLegalTest()
	{
		String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
		int targetChunkNumber = 1;
		
		// open DS editor
		sourcePage().goToSourcePageWithRenditionUuids(uuid);
		loginPage().logIn();
		sourceNavigateGridPage().firstRenditionEditContent();
		
		// scroll to chunk
		editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
		
		boolean nopubMnemonicExists = editorTextPage().checkForExistanceOfPubtag();
		Assertions.assertFalse(nopubMnemonicExists, "There should be no pubtags in a source document");
		
		editorPage().switchToEditor();
		boolean pubtagRefreshButtonDisabled = editorToolbarPage().isPubtagRefreshButtonEnabled();
		Assertions.assertTrue(pubtagRefreshButtonDisabled, "'Pubtag Refresh' toolbar button should be disabled");
		
		// close editor
		editorPage().closeDocumentWithNoChanges();
	}
}
