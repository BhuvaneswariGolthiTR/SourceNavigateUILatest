package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.CreditPhraseEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SectionCreditPhrasingTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-12764 <br>
     * SUMMARY - Section and STAT page information comes up in the UI (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionAndStatPageInformationComesUpInTheUISourceLegalTest()
    {
        String uuid = "IA22326D05FC911EBA691C22B874B8044";
        String statutePageParaTitle = "Statute Page";
        String sourceSectionNumberParaTitle = "Source Section Number";

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstItem();
        sourceNavigateGridPage().rightClickFirstRendition();
        sourceNavigateGridPage().breakOutOfFrame();
        renditionContextMenu().viewSourceFront();
        editorPage().switchDirectlyToTextFrame();

        //Right click on Source Front Material and select Show/Hide
        editorPage().rightClick(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorPage().switchDirectlyToTextFrame();

        // Get the StatutePage value
        String commonXpath = EditorTextPageElements.PARA_SPAN + "[text()[contains(.,'%s')]]"
                + String.format(EditorTextPageElements.FOLLOWING_SIBLING, "paratext");
        editorTextPage().waitForElement(String.format(commonXpath, statutePageParaTitle));
        String statutePageValue = editorTextPage().getElementsText(String.format(commonXpath, statutePageParaTitle));
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
        editorPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Reopen rendition --> Edit content
        editorPage().switchToWindow(PendingRenditionNavigatePageElements.PAGE_TITLE);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String sourceSectionNumberValue = editorTextPage().getElementsText(String.format(commonXpath, sourceSectionNumberParaTitle));

        editorTextPage().rightClick(String.format(
                EditorTextPageElements.SOURCE_SECTION + "[@num='%s']" + EditorTextPageElements.SPAN, sourceSectionNumberValue));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().creditPhrase();
        creditPhraseEditorPage().switchToWindow(CreditPhraseEditorPageElements.PAGE_TITLE);
        creditPhraseEditorPage().enterTheInnerFrame();

        String creditPhraseSectionsValue = creditPhraseEditorPage().getSectionsValue();
        String creditPhraseStatPageValue = creditPhraseEditorPage().getStatPageValue();

        creditPhraseEditorPage().clickCancel();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll(
                () -> Assertions.assertEquals(sourceSectionNumberValue, creditPhraseSectionsValue,
                        "Actual value doesn't match expected"),
                () -> Assertions.assertTrue(creditPhraseStatPageValue.contains(statutePageValue.substring(0, statutePageValue.length() - 1)),
                        "Actual value doesn't contain expected")
        );
    }
}
