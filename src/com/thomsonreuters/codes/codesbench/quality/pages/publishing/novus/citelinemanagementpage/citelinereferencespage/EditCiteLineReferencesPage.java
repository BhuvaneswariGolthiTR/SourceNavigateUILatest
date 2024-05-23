package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.AddOrEditCiteLineReferencesPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditCiteLineReferencesPage extends AddOrEditCiteLineReferencesPage
{
    @Autowired
    public EditCiteLineReferencesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditCiteLineReferencesPageElements.class);
    }

    public boolean clickUpdateValues()
    {
        click(CiteLineManagementsCommonPageElements.updateValues);
        waitForPageLoaded();
        waitForElementGone(AddOrEditCiteLineReferencesPageElements.EDIT_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EDIT_WINDOW_TITLE_XPATH);
    }

    public boolean clickCancel()
    {
        click(CiteLineManagementsCommonPageElements.cancel);
        waitForPageLoaded();
        waitForElementGone(AddOrEditCiteLineReferencesPageElements.EDIT_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EDIT_WINDOW_TITLE_XPATH);
    }
}
