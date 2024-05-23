package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.HeadnotesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeadnotesPageAngular extends BasePage
{

    @Autowired
    public HeadnotesPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HeadnotesPageElementsAngular.class);
    }

    
    public boolean isPageOpened()
    {
        try
        {
            waitForVisibleElement(HeadnotesPageElementsAngular.CASE_TITLE, 30);
        } catch (Exception ignored)
        {
        }
        return doesElementExist(HeadnotesPageElementsAngular.PAGE_TAG);
    }

    public String getCaseTitle()
    {
        return getElementsText(HeadnotesPageElementsAngular.caseTitle);
    }

    public void clickPreviousCaseButton()
    {
        sendEnterToElement(HeadnotesPageElementsAngular.PREVIOUS_CASE_BUTTON);
    }

    public void clickNextCaseButton()
    {
        sendEnterToElement(HeadnotesPageElementsAngular.NEXT_CASE_BUTTON);
    }

    public boolean isPreviousCaseButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.PREVIOUS_CASE_BUTTON);
    }

    public boolean isNextCaseButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.NEXT_CASE_BUTTON);
    }

    public boolean isUnignoreAllButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.UNIGNORE_ALL_BUTTON);
    }

    public boolean isIgnoreAllButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.IGNORE_ALL_BUTTON);
    }

    public boolean isCompletedByAndDateButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.COMPLETED_BY_AND_DATE_BUTTON);
    }

    public boolean isSignOutCaseButtonDisabled()
    {
        return isElementDisabled(HeadnotesPageElementsAngular.SIGN_OUT_CASE_BUTTON);
    }

    public void clickIgnoreAll()
    {
        waitForElementToBeEnabled(HeadnotesPageElementsAngular.IGNORE_ALL_BUTTON);
        sendEnterToElement(HeadnotesPageElementsAngular.IGNORE_ALL_BUTTON);
    }

    public void clickUnignoreAll()
    {
        sendEnterToElement(HeadnotesPageElementsAngular.UNIGNORE_ALL_BUTTON);
    }

    public void clickCompletedByAndDate()
    {
        click(HeadnotesPageElementsAngular.COMPLETED_BY_AND_DATE_BUTTON);
        //waitForElement(SubscribedCasesPageElementsAngular.COLUMN_HEADERS_TEXT_XPATH);
    }

    public void clickSignOutCase()
    {
        click(HeadnotesPageElementsAngular.SIGN_OUT_CASE_BUTTON);
    }

    public void clickClearSignOutCase()
    {
        click(HeadnotesPageElementsAngular.CLEAR_SIGN_OUT_CASE_BUTTON);
    }

    public String getHighlightingHeadnoteText() {return getElementsText(HeadnotesPageElementsAngular.HEADNOTE_HIGHLIGHTING_TEXT);}

    public String getBackGroundColor(){return getElement(HeadnotesPageElementsAngular.BACKGROUND).getCssValue("background-color");}

}
