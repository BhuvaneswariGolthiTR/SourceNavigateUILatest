package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements.*;
import static java.lang.String.format;

@Component
public class IndexingSuggestedIndexEntriesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingSuggestedIndexEntriesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingSuggestedIndexEntriesPageElements.class);
    }

    public void clickSuggestedEntry(String entryName)
    {
        click(format(SUGGESTED_ENTRY_TEXT, entryName));
    }

    public List<String> splitSuggestedIndexEntryName(String suggestedIndexEntryName)
    {
        return List.of(suggestedIndexEntryName.trim().split(DASH));
    }

    public List<String> getListOfSuggestedEntries()
    {
        return driver.findElements(By.xpath(SUGGESTED_ENTRY))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getNumberOfSuggestedEntries()
    {
        return driver.findElements(By.xpath(SUGGESTED_ENTRY)).size();
    }

    public List<String> getTextOfSuggestedEntries()
    {
        List<String> itemsList = new ArrayList<>();
        for (int i = 0; i < getNumberOfSuggestedEntries(); i++)
        {
            itemsList.add(driver.findElements(By.xpath(SUGGESTED_ENTRY)).get(i).getText());
        }
        return itemsList;
    }

    public boolean isCheckboxSelected(String suggestedEntry)
    {
        return driver.findElement(By.xpath(format(SUGGESTED_ENTRY_CHECKBOX, suggestedEntry))).isSelected();
    }

    public void editSuggestedIndexEntry()
    {
        click(EDIT_SUGGESTED_INDEX_ENTRY_BUTTON);
    }

    public void saveSuggestedIndexEntry()
    {
        waitForElementToBeClickable(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);
        click(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);
    }

    public void pressEnterOnSuggestedIndexEntry()
    {
        sendKeys(Keys.ENTER);
    }

    public void pressArrowDownOnSuggestedIndexEntry()
    {
        sendKeys(Keys.ARROW_DOWN);
    }

    public void pressArrowUpOnSuggestedIndexEntry()
    {
        sendKeys(Keys.ARROW_UP);
    }
}
