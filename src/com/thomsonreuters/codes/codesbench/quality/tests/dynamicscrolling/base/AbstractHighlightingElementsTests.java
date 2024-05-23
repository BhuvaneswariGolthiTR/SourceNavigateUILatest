package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHighlightingElementsTests extends TestService
{
    private String latestHighlightedElement;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void highlightingElementsUsingF7AndApplyingMarkupTest()
    {
        latestHighlightedElement = PARA + CLASS_HIGHLIGHTED_POSTFIX;

        //Click on text paragraph
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().click(SUBSECTION + TEXT_PARAGRAPH_SPAN);
        editorTextPage().waitForElement(latestHighlightedElement);

        //Highlight elements one by one using F7 until the whole document will be highlighted.
        // Assert highlighting order:
        // if element we started highlight from has a sibling, that sibling highlights
        // if element we started highlight from doesn't have any siblings, the parent highlights
        highlightElementsUsingF7AndAssertThatElementsHighlightedCorrectly();

        //Assert that there are no errors in the message pane
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the message pane")
                .isFalse();

        //Highlight some text paragraphs
        int numberOfParagraphsToHighlight = 2;
        editorPage().switchToEditorTextFrame();
        editorTextPage().click(SUBSECTION + TEXT_PARAGRAPH_SPAN);
        editorTreePage().highlightFollowingSiblings(numberOfParagraphsToHighlight);
        waitForParagraphsToBeHighlighted(numberOfParagraphsToHighlight);
        editorTextPage().waitForPageLoaded();
        editorTextPage().scrollToView(SUBSECTION + PARA + PARATEXT);
        editorTextPage().waitForPageLoaded();

        List<String> allParagraphsContent = editorTextPage().getElementsTextList(SUBSECTION + PARA + PARATEXT);

        //Apply bold markup
        editorPage().switchToEditor();
        editorToolbarPage().clickBold();
        editorPage().waitForPageLoaded();
        editorPage().switchDirectlyToTextFrame();

        List<String> boldedContent = editorTextPage().getElementsTextList(
                editorTextPage().getModifiedByXpath(user()) + format(ANCESTOR, "para/paratext/bold"));

        //Assert that bold markup applied correctly
        assertThat(boldedContent.size())
                .as("Bold markup should be applied to each highlighted text paragraph's text content")
                .isEqualTo(numberOfParagraphsToHighlight);
        assertThat(allParagraphsContent)
                .as("Text content within each bold markup should contain the whole paragraph's text content")
                .anyMatch(boldedContent.get(0)::equals)
                .anyMatch(boldedContent.get(1)::equals);
    }

    /**
     * STORY/BUG - HALCYONST-10933 <br>
     * SUMMARY - F7 highlights only text within paragraph <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void highlightingTextWithinTheParagraphUsingF7Test()
    {
        //Click on text in paragraph
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(SUBSECTION + PARA + PARATEXT);
        editorTextPage().click(SUBSECTION + PARA + PARATEXT);

        //Click F7 to highlight text within paragraph
        editorTextPage().sendKeys(Keys.F7);

        String highlightedTextToCopy = editorTextPage().getSelectedText();

        //Assert that only text within paragraph is highlighted
        assertThat(highlightedTextToCopy)
                .as("The whole text within paragraph should be highlighted")
                .isEqualTo(editorTextPage().getElementsText(SUBSECTION + PARA + PARATEXT));

        String phrase = String.valueOf(System.currentTimeMillis());

        //Copy highlighted text and paste it to the end of the paragraph
        editorTextPage().ctrlCUsingAction();
        editorTextPage().sendKeys(Keys.END);
        editorTextPage().sendKeys(phrase);
        editorTextPage().ctrlVUsingAction();

        //Assert that text of the copied paragraph is inserted to the end of selected paragraph
        assertThat(editorTextPage().getSelectedText())
                .as("Text of the copied paragraph should be inserted to the end of selected paragraph")
                .isEqualTo(highlightedTextToCopy);

        //Assert that text of the copied paragraph is appeared twice
        assertThat(editorTextPage().getElementsText(SUBSECTION + PARA + PARATEXT).split(phrase))
                .as("Text of the copied paragraph should be appeared twice")
                .allMatch(text -> text.equals(highlightedTextToCopy));

        //Assert that paragraph has no sibling paragraph
        assertThat(editorTextPage().doesElementExist(SUBSECTION + PARA + format(FOLLOWING_SIBLING, "para")))
                .as("Paragraph should not have sibling paragraph")
                .isFalse();

        //Click on text paragraph
        editorTextPage().click(SUBSECTION + TEXT_PARAGRAPH_SPAN);
        editorTextPage().click(SUBSECTION + TEXT_PARAGRAPH_SPAN);
        editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);

        //Paste copied earlier text
        editorTextPage().ctrlVUsingAction();

        //Assert that sibling paragraph is pasted
        assertThat(editorTextPage().doesElementExist(SUBSECTION + PARA + format(FOLLOWING_SIBLING, "para")))
                .as("Sibling paragraph should be pasted")
                .isTrue();

        //Assert that sibling paragraph contains text of the paragraph copied earlier
        assertThat(editorTextPage().getElementsText(PARA + CLASS_HIGHLIGHTED_POSTFIX + PARATEXT))
                .as("Sibling paragraph should contain text of the paragraph copied earlier")
                .isEqualTo(highlightedTextToCopy);
    }

//  ------------- Assistive methods: -------------

    private void highlightElementsUsingF7AndAssertThatElementsHighlightedCorrectly()
    {
        String any = "*";
        String parentAny = format(PARENT, any);
        String siblingAny = format(FOLLOWING_SIBLING, any);
        String highlightedLast = CLASS_HIGHLIGHTED_POSTFIX + "[last()]";
        String highlightedFirst = CLASS_HIGHLIGHTED_POSTFIX + "[1]";

        //When the document will be entirely highlighted, the parent of the latest highlighted element will have 'div'
        // tag name. In this case method performing will be stopped
        if (editorTextPage().getElement(latestHighlightedElement + parentAny).getTagName().equals("div"))
        {
            return;
        }

        //While the element we started highlighting from has siblings, these siblings will be highlighted one by one
        // till the last sibling will be highlighted
        while (editorTextPage().doesElementExist(latestHighlightedElement + siblingAny))
        {
            WebElement sibling = editorTextPage().getElement(latestHighlightedElement + siblingAny);

            editorTextPage().sendKeys(Keys.F7);
            editorTextPage().waitForElement(getXpathFromElement(sibling) + highlightedLast);

            assertThatElementIsHighlighted(sibling, "Sibling");

            latestHighlightedElement = getXpathFromElement(sibling) + highlightedLast;
        }

        //While the latest highlighted element has no siblings and has parent, it's parent will be highlighted
        while (editorTextPage().doesElementExist(latestHighlightedElement + parentAny)
                && !editorTextPage().doesElementExist(latestHighlightedElement + siblingAny))
        {
            WebElement parent = editorTextPage().getElement(latestHighlightedElement + parentAny);

            editorTextPage().sendKeys(Keys.F7);
            editorTextPage().waitForElement(getXpathFromElement(parent) + highlightedFirst);

            assertThatElementIsHighlighted(parent, "Parent");

            latestHighlightedElement = getXpathFromElement(parent) + highlightedFirst;
        }

        //Recursive call is used here to highlight all elements in the document one by one
        highlightElementsUsingF7AndAssertThatElementsHighlightedCorrectly();
    }

    private void assertThatElementIsHighlighted(WebElement element, String siblingOrParent)
    {
        assertThat(element.getAttribute("class"))
                .as(format("%s element with [%s] tag name should be highlighted", siblingOrParent, element.getTagName()))
                .contains("highlighted");
    }

    private String getXpathFromElement(WebElement element)
    {
        return format("//%s", element.getTagName());
    }

    private void waitForParagraphsToBeHighlighted(int paragraphsNumber)
    {
        new FluentWait<>(driver())
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .until(f -> editorTextPage().getElements(
                        PARA + CLASS_HIGHLIGHTED_POSTFIX).size() == paragraphsNumber);
    }
}
