package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFinalIndexEntriesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFinalIndexEntriesPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.INDEXING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN;
import static java.lang.String.format;

@Component
public class IndexingFinalIndexEntriesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingFinalIndexEntriesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingFinalIndexEntriesPageElements.class);
    }

    public int getNumberOfFinalEntries()
    {
        return driver.findElements(By.xpath(FINAL_INDEX_ENTRY)).size();
    }

    public List<String> getTextOfFinalEntries()
    {
        List<String> itemsList = new ArrayList<>();
        for (int i = 0; i < getNumberOfFinalEntries(); i++)
        {
            itemsList.add(driver.findElements(By.xpath(FINAL_INDEX_ENTRY)).get(i).getText());
        }
        return itemsList;
    }

    public String getFirstFinalIndexEntryText()
    {
        return getTextOfFinalEntries().get(0);
    }

    public String getExpectedIndexEntry(List<String> list)
    {
        return String.join(DASH, list);
    }

    public void clickSpecificFinalIndexEntry(String finalIndexEntryText)
    {
        click(format(FINAL_INDEX_ENTRIES_PATTERN, finalIndexEntryText));
    }

    public void editFinalIndexEntry()
    {
        click(EDIT_FINAL_INDEX_ENTRIES_BUTTON);
    }

    public void deleteFinalIndexEntry()
    {
        click(REMOVE_FINAL_INDEX_ENTRIES_BUTTON);
    }

    public void saveExitFinalIndexEntry()
    {
        click(SAVE_EXIT_FINAL_INDEX_ENTRIES_BUTTON);
        waitForElementGone(format(UI_TITLE_PATTERN, INDEXING));
    }

    public void saveNextFinalIndexEntry()
    {
        click(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON);
    }

    public void moveCursorToPreviousFinalIndexEntry()
    {
        sendKeys(Keys.ARROW_UP);
    }

    public void moveCursorToNextFinalIndexEntry()
    {
        sendKeys(Keys.ARROW_DOWN);
    }

    public List<String> splitFinalIndexEntryName(String finalIndexEntryName)
    {
        return List.of(finalIndexEntryName.trim().split(DASH));
    }

    public List<WebElement> getValidationDotsList(String finalIndexEntryText)
    {
        return driver.findElements(By.xpath(format(VALIDATION_DOT_STYLE, finalIndexEntryText)));
    }

    public void hoverOverValidationDots(String finalIndexEntryName)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(format(VALIDATION_DOT, finalIndexEntryName))))
                .pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }

    public void clearFinalIndexEntryBox()
    {
        getTextOfFinalEntries()
                .forEach(
                        s -> {
                            clickSpecificFinalIndexEntry(s);
                            deleteFinalIndexEntry();
                        }
                );
    }

}
