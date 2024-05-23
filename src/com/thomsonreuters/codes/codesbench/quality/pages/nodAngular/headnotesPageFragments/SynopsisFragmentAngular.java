package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.SynopsisFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.NotesPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SynopsisFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SynopsisFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SynopsisFragmentElementsAngular.class);
    }

    public void expandSynopsisBackground()
    {
        SynopsisFragmentElementsAngular.synopsisBackground.click();
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.synopsisBackground, "class", SynopsisFragmentElementsAngular.EXPANDED_CARD_CLASS);
    }

    public void expandSynopsisHoldings()
    {
        SynopsisFragmentElementsAngular.synopsisHoldings.click();
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.synopsisHoldings, "class", SynopsisFragmentElementsAngular.EXPANDED_CARD_CLASS);
    }

    public void expandNotes()
    {
        click(SynopsisFragmentElementsAngular.notes);
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.notes, "class", SynopsisFragmentElementsAngular.EXPANDED_CARD_CLASS);
    }

    public void collapseSynopsisBackground()
    {
        SynopsisFragmentElementsAngular.synopsisBackground.click();
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.synopsisBackground, "class", SynopsisFragmentElementsAngular.COLLAPSED_CARD_CLASS);
    }

    public void collapseSynopsisHoldings()
    {
        SynopsisFragmentElementsAngular.synopsisHoldings.click();
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.synopsisHoldings, "class", SynopsisFragmentElementsAngular.COLLAPSED_CARD_CLASS);
    }

    public void collapseNotes()
    {
        SynopsisFragmentElementsAngular.notes.click();
        waitForElementsAttributeToBe(SynopsisFragmentElementsAngular.notes, "class", SynopsisFragmentElementsAngular.COLLAPSED_CARD_CLASS);
    }

    public boolean waitForSynopsisBackgroundContentToShow()
    {
        return waitForElementExists(SynopsisFragmentElementsAngular.SYNOPSIS_BACKGROUND_CONTENT);
    }

    public boolean waitForSynopsisHoldingsContentToShow()
    {
        return waitForElementExists(SynopsisFragmentElementsAngular.SYNOPSIS_HOLDINGS_CONTENT);
    }

    public boolean waitForNotesContentToShow()
    {
        return waitForElementExists(SynopsisFragmentElementsAngular.NOTES_TEXTAREA);
    }

    public String getSynopsisBackgroundContent()
    {
        return getElementsText(SynopsisFragmentElementsAngular.SYNOPSIS_BACKGROUND_CONTENT);
    }

    public String getNotesText()
    {
        return getElement(NotesPopupElementsAngular.TEXT_AREA).getAttribute("value");
    }

    public String getSynopsisHoldingsContent()
    {
        return getElementsText(SynopsisFragmentElementsAngular.SYNOPSIS_HOLDINGS_CONTENT);
    }

    public boolean checkIfSynopsisBackgroundContentIsShown()
    {
        return isElementDisplayed(SynopsisFragmentElementsAngular.SYNOPSIS_BACKGROUND_CONTENT);
    }

    public boolean checkIfSynopsisHoldingsContentIsShown()
    {
        return isElementDisplayed(SynopsisFragmentElementsAngular.SYNOPSIS_HOLDINGS_CONTENT);
    }

    public boolean checkIfNotesContentIsShown()
    {
        return isElementDisplayed(SynopsisFragmentElementsAngular.NOTES_CONTENT);
    }

    public boolean isNotesSaveButtonEnabled()
    {
        return getElement(SynopsisFragmentElementsAngular.NOTES_SAVE_BUTTON).isEnabled();
    }

    public void typeTextIntoNotesTextArea(String text)
    {
        click(SynopsisFragmentElementsAngular.NOTES_TEXTAREA);
        sendKeysToElement(SynopsisFragmentElementsAngular.NOTES_TEXTAREA, text);
    }

    public void clickNotesSaveButton()
    {
        click(SynopsisFragmentElementsAngular.NOTES_SAVE_BUTTON);
    }

}
