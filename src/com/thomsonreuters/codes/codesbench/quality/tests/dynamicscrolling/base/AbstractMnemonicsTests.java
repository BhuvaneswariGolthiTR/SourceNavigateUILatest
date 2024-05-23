package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.MD_MNEM_MNEMONIC;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractMnemonicsTests extends AbstractTagsTests
{
    private static final String HIGHLIGHTED_MNEMONIC = HIGHLIGHTED_PARA + MD_MNEM_MNEMONIC;
    private static final String MNEMONIC_VALUE = "CILP";

    @BeforeEach
    protected abstract void beforeEachTestMethod();

    /**
     * STORY/BUG - HALCYONST-2116 <br>
     * SUMMARY - Changing Mnemonics across chunks<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changingMnemonicsAcrossChunksLegalTest()
    {
        //Open Content Editorial System window, select Mnemonic and click Save
        editorTextPage().rightClick(HIGHLIGHTED_MNEMONIC);
        editorTextPage().breakOutOfFrame();
        contentEditorialSystemPage().switchToContentEditorialSystemWindow();
        contentEditorialSystemPage().selectMnemonic(MNEMONIC_VALUE.toLowerCase());
        contentEditorialSystemPage().clickSave();

        //Assert that the count of changed Mnemonics is the same as the count of selected paragraphs
        int selectedParagraphs = countHighlightedParagraphs();
        List<String> changedMnemonics = editorTextPage().getElementsTextList(HIGHLIGHTED_MNEMONIC);
        assertThat(changedMnemonics.size())
                .as("The count of changed Mnemonics should be the same as the count of selected paragraphs")
                .isEqualTo(selectedParagraphs);

        //Assert that all changed Mnemonics have specified Mnemonic value
        assertThat(changedMnemonics)
                .as(String.format("All selected Mnemonics should have [%s] value", MNEMONIC_VALUE))
                .allMatch(sourceTag -> sourceTag.equals(MNEMONIC_VALUE));
    }
}
