package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.BODY_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.EMBEDDED_HTML;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.CROWN_DEPENDENCIES;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class TableValidationsTests extends TestService
{
    private static final String NODE_UUID = "IA79E62A329A011E6A46DB8CA3AB007EB";
    private static final String VALID_ENTRY_PART = "entry";
    private static final String INVALID_ENTRY_PART = "ntry";
    private static final String INVALID_ELEMENT_TAG = "bananas";
    private static final String INVALID_ELEMENT = format("<%s whatever=\"thirty\"></%s>",
            INVALID_ELEMENT_TAG, INVALID_ELEMENT_TAG);
    private static final String INVALID_ELEMENT_ERROR_MESSAGE =
            format("The element type \"%s\" must be terminated by the matching end-tag \"/%s\"",
            INVALID_ENTRY_PART, INVALID_ENTRY_PART);
    private static final String INVALID_CONTENT_ERROR_MESSAGE =
            format("Invalid content was found starting with element '%s'", INVALID_ELEMENT_TAG);

    @BeforeEach
    public void loginAndOpenNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(CROWN_DEPENDENCIES.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(NODE_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
    }

    @AfterEach
    public void closeNode()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void tableValidationsUsingValidateToolbarButtonTest()
    {
        //1. Insert risk formatted table below the Text Paragraph
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        editorTextPage().addTableUsingAltT();
        //2. In the table, delete one of the characters from one of the elements
        // Example: Delete 'e' from one of the <entry> elements
        //3. In the table, add an invalid element and attribute combination
        // Example: Add <bananas whatever="thirty">, and make sure to add the closing </bananas> element
        modifyTableContent(false, INVALID_ELEMENT, INVALID_ENTRY_PART);

        //4. Press the green validation button in the toolbar
        validate();

        assertThatTheExpectedErrorMessageIsAppearedInTheMessagePane(INVALID_ELEMENT_ERROR_MESSAGE);

        //5. Click on 'CLICK HERE' link in the message pane
        String clickHereLink = "//a[text()='CLICK HERE']";
        editorMessagePage().click(clickHereLink);
        editorPage().switchToEditorTextFrame();

        assertThatTheTableIsHighlighted();

        //6. Fix the error by adding the character 'e' back to an <entry> element
        modifyTableContent(true, INVALID_ELEMENT, VALID_ENTRY_PART);

        //7. Press the green validation button in the toolbar
        validate();

        assertThatTheExpectedErrorMessageIsAppearedInTheMessagePane(INVALID_CONTENT_ERROR_MESSAGE);

        String messagePaneContentWithErrors = editorMessagePage().getMessage();

        //8. Click on the second 'CLICK HERE' link in the message pane
        editorMessagePage().click(format("(%s)[2]", clickHereLink));
        editorPage().switchToEditorTextFrame();

        assertThatTheTableIsHighlighted();

        //9. Fix the error by removing the invalid elements
        modifyTableContent(true, "", VALID_ENTRY_PART);

        //10. Press the green validation button in the toolbar
        validate();

        //Assert that the node have no longer validation errors
        assertThat(editorMessagePage().getMessage()
                .replace(messagePaneContentWithErrors, "")
                .toLowerCase().contains("error"))
                .as("There should be no errors in the message pane after last validation")
                .isFalse();
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void tableValidationsUsingHotkeyTest()
    {
        //1. Insert risk formatted table below the Text Paragraph
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        editorTextPage().addTableUsingAltT();

        //2. In the table, delete one of the characters from one of the elements
        // Example: Delete 'e' from one of the <entry> elements
        //3. In the table, add an invalid element and attribute combination
        // Example: Add <bananas whatever="thirty">, and make sure to add the closing </bananas> element
        modifyTableContent(false, INVALID_ELEMENT, INVALID_ENTRY_PART);

        //4. Click Shift + Alt + T to open 'Table Editor'
        editorTextPage().editTableViaShiftAltT();

        assertThatTheExpectedErrorMessageIsAppearedInAnAlertPopup(INVALID_ELEMENT_ERROR_MESSAGE);

        //5. Fix the error by adding the character 'e' back to an <entry> element
        modifyTableContent(true, INVALID_ELEMENT, VALID_ENTRY_PART);

        //6. Click Shift + Alt + T to open 'Table Editor'
        editorTextPage().editTableViaShiftAltT();

        assertThatTheExpectedErrorMessageIsAppearedInAnAlertPopup(INVALID_CONTENT_ERROR_MESSAGE);

        //7. Fix the error by removing the invalid elements
        modifyTableContent(true, "", VALID_ENTRY_PART);

        //8. Click Shift + Alt + T to open 'Table Editor'
        editorTextPage().editTableViaShiftAltT();

        //Assert that the 'Table Editor' window is opened
        assertThat(tableEditorPage().switchToTableEditorPage())
                .as("'Table Editor' window should be opened")
                .isTrue();
    }

//  ------------- Assistive methods: -------------

    private void modifyTableContent(boolean additionalHighlighting, String... values)
    {
        String embeddedHtml = BODY_TAG + EMBEDDED_HTML;
        String highlightedEmbeddedHtml = embeddedHtml + CLASS_HIGHLIGHTED_POSTFIX;
        String tableContent =
                "<![CDATA[<table><title/><tgroup><colspec colname=\"c1\"/><colspec colname=\"c2\"/><thead/><tbody>" +
                        "%s<row><%s>Insert text here</entry><entry>Insert text here</entry></row></tbody><tfoot/></tgroup></table>]]>";

        //Place cursor at the beginning under the English label
        editorTextPage().click(embeddedHtml + SPAN);
        editorTextPage().waitForElement(highlightedEmbeddedHtml);
        RobotUtils.pressDownArrowUsingRobot();
        editorTextPage().waitForElementGone(highlightedEmbeddedHtml);
        RobotUtils.pressHomeUsingRobot();

        //Skip <html.text> part and Select table content
        editorTextPage().selectTable("<html.text>",additionalHighlighting);

        //Set system clipboard and paste specified table content according to test logic
        ClipboardUtils.setSystemClipboard(format(tableContent, values[0], values[1]));
        RobotUtils.pasteUsingRobotAction();
    }

    private void validate()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickValidate();
    }

    private void openTableEditor()
    {
        editorTextPage().sendKeys(Keys.chord(Keys.SHIFT, Keys.ALT, "t"));
    }

    private void assertThatTheExpectedErrorMessageIsAppearedInTheMessagePane(String expectedErrorMessage)
    {
        assertThat(editorMessagePage().checkMessage(expectedErrorMessage))
                .as(format("The [%s] error message should be appeared in the message pane", expectedErrorMessage))
                .isTrue();
    }

    private void assertThatTheExpectedErrorMessageIsAppearedInAnAlertPopup(String expectedErrorMessage)
    {
        editorTextPage().waitForAlert();
        assertThat(editorTextPage().getAlertText())
                .as(format("The [%s] error message should be appeared in an alert popup", expectedErrorMessage))
                .contains(expectedErrorMessage);
        editorTextPage().acceptAlert();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatTheTableIsHighlighted()
    {
        assertThat(editorTextPage().doesElementExist(
                editorTextPage().getModifiedByXpath(user()) + format(ANCESTOR, "embedded.html" + CLASS_HIGHLIGHTED_POSTFIX)))
                .as("The table should be highlighted")
                .isTrue();
    }
}
