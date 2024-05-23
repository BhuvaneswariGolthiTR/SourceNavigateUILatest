package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.MD_MNEM_MNEMONIC;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractInsertNewParagraphTests extends TestService
{
    protected static final String FIRST_PARA = format(TEXT_PARAGRAPH_FROM_CHUNK, 1, 1);
    protected static final String SECOND_PARA = FIRST_PARA + format(FOLLOWING_SIBLING, "para[1]");
    protected static final String THIRD_PARA = FIRST_PARA + format(FOLLOWING_SIBLING, "para[2]");
    private String modifiedBy;
    private String mnemonic;

    @BeforeEach
    public void login()
    {
        modifiedBy = editorTextPage().getModifiedByXpath(user());
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

// ---------- Assistive methods ----------

    protected void addParagraph(ParagraphInsertMethods insertMethod, String xPath)
    {
        mnemonic = editorPage().getElementsText(FIRST_PARA + MD_MNEM_MNEMONIC);
        editorTextPage().click(xPath);
        editorPage().insertNewParagraphVia(insertMethod, xPath);
        editorTextPage().waitForElementExists(SECOND_PARA);
        editorTextPage().waitForPageLoaded();
    }

    protected void assertThatOnlyNewParagraphIsHighlighted(String xPath)
    {
        // HALCYONST-2011
        assertThat(editorPage().checkIfElementsClassAttributeContainsHighlighted(xPath)
                && editorPage().getElements(HIGHLIGHTED_PARA).size() == 1)
                .as("Only one paragraph (which was added right now) should be highlighted")
                .isTrue();
    }

    protected void assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic()
    {
        assertThat(editorPage().getElementsText(HIGHLIGHTED_PARA + MD_MNEM_MNEMONIC))
                .as("Mnemonic of the freshly added Text Paragraph should be the same")
                .isEqualTo(mnemonic);
    }

    protected void assertThatModifiedByTagContainsRightNameAndDate()
    {
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + modifiedBy))
                .as("Modified By tag should contain right name and date")
                .isTrue();
    }

    protected void assertThatParagraphHasCorrectText(ParagraphInsertMethods insertMethod)
    {
        String text = editorTextPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT);
        String initialText = insertMethod.equals(ParagraphInsertMethods.AS_SIBLING) ? "Insert text" : " ";
        assertThat(initialText.equals(text) || (initialText + "i").equals(text))
                .as("New Paragraph text should be correct")
                .isTrue();
    }

    protected void assertThatParagraphHasCorrectText()
    {
        String paraText = editorTextPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT);
        assertThat(paraText.equals(" ") || paraText.equals(" i"))
                .as("New Paragraph text should be correct")
                .isTrue();
    }

    protected void assertThatEditorMessagePaneHasNoErrors()
    {
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();
    }
}
