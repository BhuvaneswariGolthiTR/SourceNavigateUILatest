package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HeadnotesClassificationFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeadnotesClassificationFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public HeadnotesClassificationFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HeadnotesClassificationFragmentElementsAngular.class);
    }

    
    public boolean isClassificationAreaShown()
    {
        return doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_COLUMN, "HN"))
                && doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_COLUMN, "RL"))
                && doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_COLUMN, "Headnote Text"))
                && doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_COLUMN, "Classification"));
    }

    public int getNumberOfHeadnotesInTable()
    {
        return getElements(HeadnotesClassificationFragmentElementsAngular.HEADNOTE_ROW).size();
    }

    public boolean doAllRowsHaveTextInColumnsHnRlHeadnotesText()
    {
        List<Boolean> allColumnsHaveText = new ArrayList<>();
        int numberOfHeadnotes = getNumberOfHeadnotesInTable();
        for (int i = 1; i <= numberOfHeadnotes; i++)
        {
            allColumnsHaveText.add(!getElementsText(
                    String.format(HeadnotesClassificationFragmentElementsAngular.HN_COLUMN_VALUE_BY_ROW, i))
                    .replace(" ", "").equals(""));
            allColumnsHaveText.add(!getElementsText(
                    String.format(HeadnotesClassificationFragmentElementsAngular.RL_COLUMN_VALUE_BY_ROW, i))
                    .replace(" ", "").equals(""));
            allColumnsHaveText.add(!getElementsText(
                    String.format(HeadnotesClassificationFragmentElementsAngular.HEADNOTE_TEXT_COLUMN_VALUE_BY_ROW, i))
                    .replace(" ", "").equals(""));
        }
        return allColumnsHaveText.stream().noneMatch(element -> element.equals(false));
    }

    
    public void clickHeadnoteLinkByRowNumber(int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.COLUMN_VALUES_BY_ROW_AND_COLUMN,
                rowNumber, 2) + "/a";
        waitForElementExists(xpath);
        click(xpath);
        headnoteDetailsPopupAngular().waitForPageLoaded();
    }

    public void clickIgnoreForHeadnoteByRowNumber(int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, rowNumber);
        waitForElementExists(xpath);
        sendEnterToElement(xpath);
    }

    public void clickUnignoreForHeadnoteByRowNumber(int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.UNIGNORE_BUTTON_BY_ROW, rowNumber);
        waitForElementExists(xpath);
        sendEnterToElement(xpath);
    }

    public void clickClassifyForHeadnoteByRowNumber(int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, rowNumber);
        waitForElementToBeEnabled(xpath);
        sendEnterToElement(xpath);
        waitForGridRefresh();
    }

    public void clickDeleteClassification(int rowNumber, int classificationNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_BLOCK_BY_ROW, rowNumber) +
                String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_DELETE_BUTTON, classificationNumber);
        waitForElementExists(xpath);
        click(xpath);
    }

    public String getHeadnoteTextByRowNumber(int rowNumber)
    {
        return getElementsText(String.format(
                HeadnotesClassificationFragmentElementsAngular.HEADNOTE_TEXT_COLUMN_VALUE_BY_ROW, rowNumber));
    }

    public String getHeadnoteTopicAndKeyNumberTextByRowNumber(int rowNumber)
    {
        return getElementsText(String.format(
                HeadnotesClassificationFragmentElementsAngular.TOPIC_KEY_COLUMN_VALUE_BY_ROW, rowNumber));
    }

    public boolean areAllHeadnotesUnignored(int headnotesNumber)
    {
        boolean areAllHeadnotesIgnored = true;
        for (int i = 1; i <= headnotesNumber; i++)
        {
            areAllHeadnotesIgnored = areAllHeadnotesIgnored && doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, i));
        }
        return areAllHeadnotesIgnored;
    }

    public boolean areAllHeadnotesUnclassified(int headnotesNumber)
    {
        boolean areAllHeadnotesIgnored = true;
        for (int i = 1; i <= headnotesNumber; i++)
        {
            areAllHeadnotesIgnored = areAllHeadnotesIgnored && doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, i));
        }
        return areAllHeadnotesIgnored;
    }

    public boolean isHeadnoteClassified(int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_TABLE_BY_ROW, rowNumber);
        return doesElementExist(xpath, 7);
    }

    public boolean isHeadnoteIgnored(int rowNumber)
    {
        String uningoreXpath = String.format(HeadnotesClassificationFragmentElementsAngular.UNIGNORE_BUTTON_BY_ROW, rowNumber);
        boolean unignoreButtonDisappeared = doesElementExist(uningoreXpath, 5);
        String ingoreXpath = String.format(HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, rowNumber);
        boolean ignoreButtonDisappeared = !doesElementExist(ingoreXpath);
        String classifyXpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_TABLE_BY_ROW, rowNumber);
        boolean classifyButtonDisappeared = !doesElementExist(classifyXpath);

        return ignoreButtonDisappeared && classifyButtonDisappeared && unignoreButtonDisappeared;
    }

    public boolean isHeadnoteUnignored(int rowNumber)
    {
        String ingoreXpath = String.format(HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, rowNumber);
        boolean ignoreButtonAppeared = doesElementExist(ingoreXpath, 5);
        String classifyXpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, rowNumber);
        boolean classifyButtonAppeared = doesElementExist(classifyXpath);
        String uningoreXpath = String.format(HeadnotesClassificationFragmentElementsAngular.UNIGNORE_BUTTON_BY_ROW, rowNumber);
        boolean unignoreButtonAppeared = !doesElementExist(uningoreXpath);

        return ignoreButtonAppeared && classifyButtonAppeared && unignoreButtonAppeared;
    }

    public String getClassificationText(int classificationNumber, int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_TEXT, rowNumber, classificationNumber);
        return getElementsText(xpath);
    }

    public void clickClassificationText(int classificationNumber, int rowNumber)
    {
        String xpath = String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_TEXT, rowNumber, classificationNumber);
        click(xpath);
    }
}
