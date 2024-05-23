package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.InsertBluelinePopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertBluelinePopupAngular extends BasePage
{
    @Autowired
    public InsertBluelinePopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertBluelinePopupElementsAngular.class);
    }

    public boolean isPageOpened()
    {
        return (doesElementExist(InsertBluelinePopupElementsAngular.PAGE_TAG));
    }

    public void waitPopupClosed()
    {
        WebElement popup = getElement(InsertBluelinePopupElementsAngular.PAGE_TAG);
        waitUntilElementIsStale(popup);
    }

    public String getHeaderText()
    {
        return getElement(InsertBluelinePopupElementsAngular.HEADER).getText();
    }

    public boolean waitHeaderWithText(String headerText)
    {
        return waitForElementExists(InsertBluelinePopupElementsAngular.HEADER +
                String.format("[text()='%s']", headerText));
    }

    public boolean waitForSecondStepHeaderToAppear()
    {
        String expectedHeaderSecondStep = insertBluelinePopupAngular().getExpectedHeaderForStep(2);
        return insertBluelinePopupAngular().waitHeaderWithText(expectedHeaderSecondStep);
    }

    public String getExpectedHeaderForStep(int step)
    {
        return String.format(InsertBluelinePopupElementsAngular.HEADER_TEXT, step);
    }

    public String getExpectedErrorMessageBlueilineNumberAlreadyExists()
    {
        return InsertBluelinePopupElementsAngular.ERROR_BLUELINE_NUMBER_ALREADY_EXISTS;
    }

    public String getExpectedGapWarning(int lastBluelineNumber)
    {
        return String.format(InsertBluelinePopupElementsAngular.GAP_WARNING, lastBluelineNumber);
    }

    public String getBreadcrumbs()
    {
        return getElementsText(InsertBluelinePopupElementsAngular.BREADCRUMBS);
    }

    public void typeToBluelineNumberInput(int number)
    {
        String numberAsString = Integer.toString(number);
        sendKeysToElement(InsertBluelinePopupElementsAngular.BLUELINE_NUMBER_INPUT, numberAsString);
    }

    public void clearBluelineNumberInput()
    {
        getElement(InsertBluelinePopupElementsAngular.BLUELINE_NUMBER_INPUT).clear();
    }

    public void clickNext()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        waitForElementToBeEnabled(InsertBluelinePopupElementsAngular.NEXT_BUTTON);
        click(InsertBluelinePopupElementsAngular.NEXT_BUTTON);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    public boolean isErrorMessageShown()
    {
        return doesElementExist(InsertBluelinePopupElementsAngular.ERROR_MESSAGE, 2);
    }

    public String getAlertMessageText()
    {
        return getElementsText(InsertBluelinePopupElementsAngular.ERROR_MESSAGE);
    }

    public void clickSave() { click(InsertBluelinePopupElementsAngular.saveButton); }

    public void clickCancel()
    {
        click(InsertBluelinePopupElementsAngular.cancelButton);
    }

    public void clickInsert()
    {
        click(InsertBluelinePopupElementsAngular.insertButton);
    }

    public void clickDelete()
    {
        click(InsertBluelinePopupElementsAngular.deleteButton);
    }

    public void selectFlushType()
    {
        click(InsertBluelinePopupElementsAngular.flushCheckbox);
    }

    public void selectIndentType()
    {
        click(InsertBluelinePopupElementsAngular.indentCheckbox);
    }

    public void selectStartOfIndentType()
    {
        click(InsertBluelinePopupElementsAngular.startOFIndentCheckbox);
    }

    public void selectBluelineType(String bluelineType)
    {
        if (bluelineType.equals("Flush"))
        {
            selectFlushType();
        }
        else if (bluelineType.equals("Indent"))
        {
            selectIndentType();
        }
        else
        {
            selectStartOfIndentType();
        }
    }

    public void clearTextField()
    {
        clear(getElement(InsertBluelinePopupElementsAngular.TEXT_INPUT));
    }

    public void typeToTextField(String text)
    {
        sendKeysToElement(InsertBluelinePopupElementsAngular.TEXT_INPUT, text);
    }

    public void typeToSecondTextField(String text)
    {
        sendKeysToElement(InsertBluelinePopupElementsAngular.SECOND_TEXT_INPUT, text);
    }

    public void clearFlushAnalysisField()
    {
        getElement(InsertBluelinePopupElementsAngular.TEXT_INPUT).clear();
    }

    public void typeToFlushAnalysisField(String text)
    {
        sendKeysToElement(InsertBluelinePopupElementsAngular.FLUSH_ANALYSIS_INPUT, text);
    }

    public String getTextFromTextField()
    {
        return getElement(InsertBluelinePopupElementsAngular.TEXT_INPUT).getAttribute("value");
    }

    public String getFlushAnalysisFieldText()
    {
       return getElement(InsertBluelinePopupElementsAngular.FLUSH_ANALYSIS_INPUT).getAttribute("value");
    }

    public String getIndentAnalysisFieldText()
    {
        return getElement(InsertBluelinePopupElementsAngular.FLUSH_ANALYSIS_INPUT).getAttribute("value");
    }

    public void typeTagsToFlushAnalysisIndexAlsoField(String text)
    {
        sendKeysToElement(InsertBluelinePopupElementsAngular.FLUSH_ANALYSIS_INDEX_ALSO_INPUT, text);
        sendEnterToElement(InsertBluelinePopupElementsAngular.FLUSH_ANALYSIS_INDEX_ALSO_INPUT);
    }

    public WebElement getFlushAnalysisIndexAlsoTagByText(String text)
    {
        return getElement(String.format(InsertBluelinePopupElementsAngular.TAG_BY_TEXT, text));
    }

    public String getTagTextInFlushAnalysisIndexAlso()
    {
        String textWithSpaces = getElementsText(InsertBluelinePopupElementsAngular.TAG);
        return textWithSpaces.substring(1, textWithSpaces.length() -1);
    }

    public boolean isTagPresentInFlushAnalysisIndexAlso(String text)
    {
        return doesElementExist(String.format(InsertBluelinePopupElementsAngular.TAG_BY_TEXT, text));
    }

    public void removeTagFromFlushAnalysisIndexAlso(String text)
    {
        click(String.format(InsertBluelinePopupElementsAngular.TAG_CLOSE_BUTTON_BY_TAG_TEXT, text));
    }

    public boolean textFieldDropdownIsShown()
    {
        return doesElementExist(InsertBluelinePopupElementsAngular.TEXT_INPUT_DROPDOWN, 5);
    }

    public void clickTextInputDropdownOptionByText(String text)
    {
        click(String.format(InsertBluelinePopupElementsAngular.TEXT_INPUT_DROPDOWN_BUTTON_BY_TEXT, text));
    }
}
