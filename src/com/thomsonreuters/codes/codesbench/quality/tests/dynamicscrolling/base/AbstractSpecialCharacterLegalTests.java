package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.EnumMap;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.Keys.*;

public abstract class AbstractSpecialCharacterLegalTests extends TestService
{
    protected static final EnumMap<Keys, SpecialCharacters> KEYS_AND_SPECIAL_CHARACTERS_MAP;

    static
    {
        KEYS_AND_SPECIAL_CHARACTERS_MAP = new EnumMap<>(Keys.class);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD0, SECT);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD1, NDASH);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD3, KEY);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD4, ENSP);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD6, MDASH);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD7, THINSP);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD8, ENSP);
        KEYS_AND_SPECIAL_CHARACTERS_MAP.put(NUMPAD9, EMSP);
    }

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

    /*
     * Insert special characters
     * 1. Open document
     * 2. Scroll to chunk 2 or 3
     * 3. Insert a special character via the toolbar button into a text paragraph
     * 4. Select dagger from the entity set
     * VERIFY: '&dagger;' appears in the XML entity field
     * 5. Click Insert
     * VERIFY: A dagger character is inserted in the text paragraph where your cursor was.
     * I'd probably just ensure to insert it at the
     * beginning of the text paragraph and verify the first character is that.
     * 6. Insert another special character via the toolbar button into the text paragraph
     * 7. Select amp from the entity set
     * VERIFY: '&amp;' appears in the XML entity field
     * 8. Click Insert
     * VERIFY: A special entity 'amp' is inserted in the text paragraph where your cursor was.
     * I'd probably just ensure to insert it at the
     * beginning of the text paragraph and verify that way
     * 9. close and discard changes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertSpecialCharactersToolbarLegalTest()
    {
        // add a special character and verify symbol appeared
        assertThat(editorPage().insertSpecialCharacterInTextPara(PARA.getHtml(), PARA.getCharacter()))
                .as("Special character of Para should appear")
                .isTrue();

        // add another character like amp and verify symbol appeared
        assertThat(editorPage().insertSpecialCharacterInTextPara(AMP.getHtml(),
                format("<SPAN class=editorStyledEntity entity=\"%s\">%s</SPAN>", AMP.getEntity(), AMP.getEntity())))
                .as("Special entity of Amp should appear")
                .isTrue();
    }

//  ------------- Assistive methods: -------------

    protected void assertThatModifiedByTagDoesNotExistInElement(String xPath)
    {
        assertThat(editorTextPage().doesElementExist(xPath + editorTextPage().getModifiedByXpath(user())))
                .as(format("'Modified by' tag should not exist in element with %s xpath locator", xPath))
                .isFalse();
    }

    protected void replaceSpecialCharacterInElementWithKey(String xPath, Keys key)
    {
        editorTextPage().click(xPath);
        editorTextPage().sendKeys(HOME);
        editorTextPage().highlightHelper(1);
        editorTextPage().sendKeys(key);
    }

    protected void assertThatExpectedSpecialCharacterIsAppearedInTheElement(SpecialCharacters specialCharacter,
                                                                            String xPath)
    {
        assertThat(editorTextPage().getElement(xPath + "/span[1]").getAttribute("entity"))
                .as(format("There should be %s entity", specialCharacter.getEntity()))
                .isEqualTo(specialCharacter.getEntity());
    }

    protected void assertThatModifiedByTagExistsInElement(String xPath)
    {
        assertThat(editorTextPage().doesElementExist(xPath + editorTextPage().getModifiedByXpath(user())))
                .as(format("'Modified by' tag should exist in element with %s xpath locator", xPath))
                .isTrue();
    }

    protected void undoChanges()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorPage().switchDirectlyToTextFrame();
    }
}
