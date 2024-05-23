package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ConvertToTableFunctionalityTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-9855 <br>
     * SUMMARY - Convert to table functionality (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void convertToTableFunctionalitySourceLegalTest()
    {
        String uuid = "I31E72730751D11E289DBE4E232D95F35";

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String paraTextXpath = EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION + "[3]//part/%s"
                + EditorTextPageElements.PARA + EditorTextPageElements.PARATEXT;
        String subsection = "subsection[1]";
        List<String> expectedParaTextList = new ArrayList<>();
        editorTextPage().getElements(String.format(paraTextXpath, subsection)).forEach(
                element -> expectedParaTextList.add(element.getText())
        );
        editorTextPage().scrollToView(EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION +
                String.format("[3]//part/%s/span", subsection));
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION +
                String.format("[3]//part/%s/span", subsection));
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.INSERT_TABULAR_SIBLING,
                EditorTextContextMenuElements.CONVERT_TO_TABLE);
        editorPage().switchToEditorTextFrame();

        List<String> actualParaTextList = new ArrayList<>();
        editorTextPage().getElements(String.format(paraTextXpath, "xampex.table")).forEach(
                element -> actualParaTextList.add(element.getText())
        );
        actualParaTextList.remove(0);
        actualParaTextList.remove(5);

        editorPage().switchToEditor();
        boolean doesMessagePaneContainError = editorMessagePage().checkForErrorInMessagePane();

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedParaTextList, actualParaTextList, "The expected para text list doesn't match actual"),
                () -> Assertions.assertFalse(doesMessagePaneContainError, "No error message should appear")
        );
    }
}
