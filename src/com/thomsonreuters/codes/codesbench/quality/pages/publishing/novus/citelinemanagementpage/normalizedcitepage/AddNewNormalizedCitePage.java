package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddOrEditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddNewNormalizedCitePage extends AddOrEditNormalizedCitePage
{
    private WebDriver driver;

    @Autowired
    public AddNewNormalizedCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditNormalizedCitePageElements.class);
        PageFactory.initElements(driver, AddNormalizedCitePageElements.class);
    }

    public void clickCreate()
    {
        click(AddNormalizedCitePageElements.create);
        waitForPageLoaded();
        waitForElementGone(AddNormalizedCitePageElements.ADD_WINDOW_TITLE_XPATH);
    }

    public void clickCreateAnother()
    {
        click(AddNormalizedCitePageElements.createAnother);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    public boolean clickCancelButton()
    {
        click(CiteLineManagementsCommonPageElements.cancel);
        waitForPageLoaded();
        waitForElementGone(AddNormalizedCitePageElements.ADD_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(AddNormalizedCitePageElements.ADD_WINDOW_TITLE_XPATH);
    }
}
