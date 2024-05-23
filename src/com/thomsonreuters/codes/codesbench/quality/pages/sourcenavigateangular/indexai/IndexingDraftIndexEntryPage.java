package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingDraftIndexEntryPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingDraftIndexEntryPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.MISSPELLED_WORD_CONTAINER;
import static java.lang.String.format;

@Component
public class IndexingDraftIndexEntryPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingDraftIndexEntryPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingDraftIndexEntryPageElements.class);
    }

    public void enterDraftIndexEntryWithoutMovingToFinalIndexEntries(String entry)
    {
        click(PLUS_DRAFT_INDEX_ENTRY_BUTTON);
        sendKeys(entry);
        sendKeys(Keys.ENTER);
        waitForElementExists(format(READONLY_DRAFT_INDEX_ENTRY_SPAN, entry));
    }

    public void enterDraftIndexEntryAndMoveToFinalIndexEntries(String entry)
    {
        click(PLUS_DRAFT_INDEX_ENTRY_BUTTON);
        sendKeys(entry);
        click(SAVE_DRAFT_INDEX_ENTRY_BUTTON);
        waitForElementExists(format(READONLY_DRAFT_INDEX_ENTRY_SPAN, entry));
    }

    public void clickDraftEntryByNumber(int draftEntryNumber)
    {
        click(format(INPUT_DRAFT_INDEX_ENTRY_BY_NUMBER, draftEntryNumber));
    }

    public void doubleClickDraftEntry(String xpath)
    {
        new Actions(driver)
                .doubleClick(driver.findElement(By.xpath(xpath)))
                .build()
                .perform();
    }

    public void clearDraftEntryField(int length)
    {
        Actions clear = new Actions(driver);

        for (int i = 0; i < length; i++)
        {
            clear.sendKeys(Keys.BACK_SPACE);
        }

        clear.build().perform();
    }

    public void moveCursorToBeginningOfFragment()
    {
        new Actions(driver).sendKeys(Keys.HOME).build().perform();
    }

    public void highlightBeginningCharactersInsideDraftEntry(int numberOfSymbols)
    {
        Actions actions = new Actions(driver).keyDown(Keys.SHIFT);
        for (int i = 0; i < numberOfSymbols; i++)
        {
            actions.sendKeys(Keys.ARROW_RIGHT);
        }
        actions.keyUp(Keys.SHIFT).build().perform();
    }

    public void highlightEndingCharactersInsideDraftEntry(int numberOfSymbols)
    {
        Actions actions = new Actions(driver).keyDown(Keys.SHIFT);
        for (int i = 0; i < numberOfSymbols; i++)
        {
            actions.sendKeys(Keys.ARROW_LEFT);
        }
        actions.keyUp(Keys.SHIFT).build().perform();
    }

    public List<String> getMisspelledWords()
    {
        List<String> misspelledWordsList = new ArrayList<>();
        driver.findElements(By.xpath(MISSPELLED_WORD_CONTAINER))
                .stream()
                .map(WebElement::getText)
                .forEachOrdered(misspelledWordsList::add);
        return misspelledWordsList;
    }

    public int getNumberOfDraftIndexEntries()
    {
        return driver.findElements(By.xpath(DRAFT_INDEX_ENTRY_BOX + "/div")).size();
    }

    public String getAfterPseudoElement(String xpath)
    {
        return (String) ((JavascriptExecutor) driver)
                .executeScript(format("return window.getComputedStyle(document.querySelector('%s'),'::after').getPropertyValue('content')", xpath));
    }

    public String getTextOfSpecificDraftIndexEntry(int draftEntryNumber)
    {
        WebElement element = driver.findElement(By.xpath(format(DATA_ENTRY_FIELD, draftEntryNumber)));
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent", element);
    }

    public List<String> getListOfAllTextDraftIndexEntries()
    {
        List<String> draftEntriesList = new ArrayList<>();
        for (int i = 1; i <= getNumberOfDraftIndexEntries(); i++)
        {
            draftEntriesList.add(getTextOfSpecificDraftIndexEntry(i));
        }
        return draftEntriesList;
    }

    public void pressEnterToSaveDraftIndexEntry(String chars)
    {
        pressEnter();
        waitForElementExists(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, chars));
    }

    public void addDraftIndexEntryAndTypeCharacters(String chars)
    {
        indexingDraftIndexEntryPage().click(PLUS_DRAFT_INDEX_ENTRY_BUTTON);
        indexingDraftIndexEntryPage().sendKeys(chars);
    }

    public void reorderDraftIndexEntries()
    {
        click(SWAP_DRAFT_INDEX_ENTRY_BUTTON);
    }

    public void addEmptyDraftIndexEntry()
    {
        click(PLUS_DRAFT_INDEX_ENTRY_BUTTON);
    }

    public void deleteDraftIndexEntries()
    {
        click(REMOVE_DRAFT_INDEX_ENTRY_BUTTON);
    }

    public void saveDraftIndexEntries()
    {
        click(SAVE_DRAFT_INDEX_ENTRY_BUTTON);
    }

    public void pressEnter()
    {
        sendKeys(Keys.ENTER);
    }
}
