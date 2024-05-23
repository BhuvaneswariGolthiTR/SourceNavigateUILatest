package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.AddOrEditCiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddNewCiteLineReferencesPage extends AddOrEditCiteLineReferencesPage
{
    @Autowired
    public AddNewCiteLineReferencesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditCiteLineReferencesPageElements.class);
    }

    public boolean clickCreate()
    {
        click(CiteLineManagementsCommonPageElements.createButton);
        waitForPageLoaded();
        waitForElementGone(AddOrEditCiteLineReferencesPageElements.ADD_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(AddOrEditCiteLineReferencesPageElements.ADD_WINDOW_TITLE_XPATH);
    }

    public void clickCreateAnother()
    {
        click(CiteLineManagementsCommonPageElements.createAnotherButton);
        waitForPageLoaded();
    }

    public boolean clickCancel()
    {
        click(CiteLineManagementsCommonPageElements.cancel);
        waitForPageLoaded();
        waitForElementGone(AddOrEditCiteLineReferencesPageElements.ADD_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(AddOrEditCiteLineReferencesPageElements.ADD_WINDOW_TITLE_XPATH);
    }
}
