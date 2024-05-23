package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.EXCLUDE_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.RESEARCH_REFERENCE_BODY;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ToggleExcludeIncludeTests extends TestService
{
    private static final String UUID = "I56AF9D22977B11E6909FD94A4A856A13";
    private static final String RESEARCH_REFERENCE_BODY_7_SPAN = format("(%s)[%d]/span", RESEARCH_REFERENCE_BODY, 7);
    private static final String RESEARCH_REFERENCE_BODY_6_SPAN = format("(%s)[%d]/span", RESEARCH_REFERENCE_BODY, 6);
    private static final String PLACEHOLDER_RESEARCH_REFERENCE_4_SPAN = RESEARCH_REFERENCE_BODY_6_SPAN
            + format(FOLLOWING_SIBLING, "research.reference[4]/span");
    private static final String PLACEHOLDER_RESEARCH_REFERENCE_4 = RESEARCH_REFERENCE_BODY_6_SPAN
            + format(FOLLOWING_SIBLING, "research.reference[4]");
    private static final String ANY_EXCLUDED_ELEMENT = "/*[contains(@class,'excluded')]";
    private static final String CHUNK_1_RESEARCH_REFERENCE_BODY = format(LOADED_CHUNK, 1) + RESEARCH_REFERENCE_BODY;
    private static final String GRAY_COLOR = "rgba(231, 231, 231, 1)";

    @BeforeEach
    public void loginAndOpenSourceRenditionInDsEditor()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    @AfterEach
    public void closeSourceRendition()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /**
     * STORY/BUG - HALCYONST-2187/2467 <br>
     * SUMMARY - Toggle Exclude/Include and across chunks (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void toggleExcludeIncludeAndAcrossChunksSourceLegalTest()
    {
        //Toggle exclude/include Research Reference Body spanning chunk
        editorPage().scrollToChunk(1);
        toggleExcludeIncludeElement(RESEARCH_REFERENCE_BODY_7_SPAN);
        editorTextPage().click(RESEARCH_REFERENCE_BODY_6_SPAN);

        //Assert that the three children of Research Reference Body are highlighted gray
        List<WebElement> children = editorTextPage()
                .getElements(CHUNK_1_RESEARCH_REFERENCE_BODY + ANY_EXCLUDED_ELEMENT);
        assertThat(children.size())
                .as("The three children of Research Reference Body should be excluded")
                .isEqualTo(3);
        children.forEach(this::assertThatElementHighlightedGray);

        //Assert that the baggage tags are updated with modified by the user on today's date
        List<String> modifiedByTags = editorTextPage()
                .getElementsTextList(CHUNK_1_RESEARCH_REFERENCE_BODY + editorTextPage().getModifiedByXpath(user()));
        assertThat(modifiedByTags.size())
                .as("The number of modified by tags should be equal to the number of children")
                .isEqualTo(children.size());
        modifiedByTags.forEach(this::assertThatModifiedByTagContainsUserWithCurrentDate);

        //Assert that at the end of the baggage tags are an excluded tags with NX following it
        List<String> excludedTags = editorTextPage()
                .getElementsTextList(CHUNK_1_RESEARCH_REFERENCE_BODY + ANY_EXCLUDED_ELEMENT + EXCLUDE_TAG);
        assertThat(excludedTags.size())
                .as("The number of excluded tags should be equal to the number of children")
                .isEqualTo(children.size());
        excludedTags.forEach(this::assertThatExcludedTagContainsNxValue);

        //Toggle exclude/include Placeholder Research Reference
        toggleExcludeIncludeElement(PLACEHOLDER_RESEARCH_REFERENCE_4_SPAN);
        editorTextPage().click(RESEARCH_REFERENCE_BODY_7_SPAN);

        //Assert that the Placeholder Research Reference is highlighted gray
        assertThatElementHighlightedGray(editorTextPage().getElement(PLACEHOLDER_RESEARCH_REFERENCE_4_SPAN));

        //Assert that the baggage tag is updated with modified by the user on today's date
        assertThatModifiedByTagContainsUserWithCurrentDate(editorTextPage().
                getElementsText(PLACEHOLDER_RESEARCH_REFERENCE_4 + editorTextPage().getModifiedByXpath(user())));

        //Assert that at the end of the baggage tag is an excluded tag with NX following it
        assertThatExcludedTagContainsNxValue(editorTextPage()
                .getElementsText(PLACEHOLDER_RESEARCH_REFERENCE_4 + EXCLUDE_TAG));

        //Assert that there are no errors or warnings in the editor message pane
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();
        assertThat(editorMessagePage().checkForWarningInMessagePane())
                .as("There should be no warnings in the editor message pane")
                .isFalse();
    }

//  ------------- Assistive methods: -------------

    private void toggleExcludeIncludeElement(String xPath)
    {
        editorTextPage().click(xPath);
        editorTextPage().scrollToView(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().toggleExcludeInclude();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatElementHighlightedGray(WebElement element)
    {
        assertThat(element.getCssValue("background-color"))
                .as("The element should be highlighted gray")
                .isEqualTo(GRAY_COLOR);
    }

    private void assertThatModifiedByTagContainsUserWithCurrentDate(String modifiedByTag)
    {
        assertThat(modifiedByTag.contains(user().getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy()))
                .as("Modified by tag should contain the user and today's date")
                .isTrue();
    }

    private void assertThatExcludedTagContainsNxValue(String excludedTag)
    {
        assertThat(excludedTag.contains("NX"))
                .as("Excluded tag should contain NX value")
                .isTrue();
    }
}
