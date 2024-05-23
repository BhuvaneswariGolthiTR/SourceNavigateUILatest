package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.KeywordFindPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KeywordFoundPopupAngular extends BasePage
{
    @Autowired
    public KeywordFoundPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, KeywordFindPopupElementsAngular.class);
    }

    public boolean isPageOpened()
    {
        return (doesElementExist(KeywordFindPopupElementsAngular.PAGE_TAG));
    }

    public String getHeaderText()
    {
        return bluelineAnalysisPopupAngular().getElement(KeywordFindPopupElementsAngular.HEADER).getText();
    }

    public String getFirstSelectOptionText()
    {
        return getSelectedDropdownOptionText(KeywordFindPopupElementsAngular.firstSelect);
    }

    public String getSecondSelectOptionText()
    {
        return getSelectedDropdownOptionText(KeywordFindPopupElementsAngular.secondSelect);
    }

    public String getThirdSelectOptionText()
    {
        return getSelectedDropdownOptionText(KeywordFindPopupElementsAngular.thirdSelect);
    }

    public void typeToFirstInput(String text)
    {
        click(KeywordFindPopupElementsAngular.firstInput);
        sendKeysToElement(KeywordFindPopupElementsAngular.firstInput, text);
    }

    public void typeToSecondInput(String text)
    {
        click(KeywordFindPopupElementsAngular.secondInput);
        sendKeysToElement(KeywordFindPopupElementsAngular.secondInput, text);
    }

    public void typeToThirdInput(String text)
    {
        click(KeywordFindPopupElementsAngular.thirdInput);
        sendKeysToElement(KeywordFindPopupElementsAngular.thirdInput, text);
    }

    public void clickSearchButton()
    {
        click(KeywordFindPopupElementsAngular.searchButton);
    }

    public void clickCancelButton()
    {
        click(KeywordFindPopupElementsAngular.cancelButton);
    }
}
