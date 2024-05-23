package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPasteAsMultipleParagraphsTests extends TestService
{
    protected static final String SINGLE_PARA_THAT_SHOULD_BE_PASTED_AS_7_PARAS =
            "A. The Financial Conduct Authority (\"the FCA\") makes this instrument in the exercise \n" +
            "of the following powers and related provisions in the Financial Services and Markets \n" +
            "Act 2000 (\"the Act\"): \n" +
                "(1) section 73A (Part 6 Rules); \n" +
                "(2) section 96 (Obligations of issuers of listed securities); \n" +
                "(3) section 137A (General rule-making power); \n" +
                "(4) section 137T (General supplementary powers); and \n" +
                "(5) section 139A (Guidance). \n" +
            "B. The rule-making provisions listed above are specified for the purposes of section \n" +
            "138G(2) of the Act.";
    protected static final String THREE_LINES_WITH_ROMAN_NUMERALS_THAT_SHOULD_BE_PASTED_AS_3_PARAS =
            "i. This is line 1 \n" +
            "ii. This is line 2 \n" +
            "iii. This is line 3";

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

//  ------------- Assistive methods: -------------

    protected void assertThatParaIsPastedAsMultipleParas(int numberOfParas)
    {
        assertThat(editorTextPage().countElements(TEXT_PARAGRAPH_SPAN))
                .as("The para should be pasted as multiple paras")
                .isEqualTo(numberOfParas);
    }

    protected void assertThatAllPastedContentIsAppearedInTheDocument(String content)
    {
        List<String> pastedParasBeforeEditing = editorTextPage().getElementsTextList(PARATEXT + CLASS_HIGHLIGHTED_POSTFIX);
        List<String> contentToReplace = editorTextPage().getElementsTextList(PARATEXT + CLASS_HIGHLIGHTED_POSTFIX + SPAN);
        List<String> pastedParasAfterEditing = new ArrayList<>();
        for (int i = 0; i < pastedParasBeforeEditing.size(); i++)
        {
            pastedParasAfterEditing.add(pastedParasBeforeEditing.get(i).replace(contentToReplace.get(i), " "));
        }
        pastedParasAfterEditing.forEach(pastedPara ->
                assertThat(content.replace("\n", "").contains(pastedPara))
                        .as(format("%s should be appeared in the document", pastedPara))
                        .isTrue());
    }

    protected void pasteContent(String content)
    {
        ClipboardUtils.setSystemClipboard(content);
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN);
        editorTextPage().rightClick(TEXT_PARAGRAPH_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchDirectlyToTextFrame();
    }

    protected void undoChanges()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorPage().switchDirectlyToTextFrame();
    }

    protected int getParasNumber()
    {
        editorPage().switchToEditorTextFrame();
        return editorTextPage().countElements(TEXT_PARAGRAPH_SPAN);
    }
}
