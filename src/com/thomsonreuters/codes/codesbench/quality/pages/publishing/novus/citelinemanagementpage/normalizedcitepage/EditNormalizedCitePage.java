package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddOrEditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.EditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditNormalizedCitePage extends AddOrEditNormalizedCitePage
{
    private WebDriver driver;

    @Autowired
    public EditNormalizedCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditNormalizedCitePageElements.class);
        PageFactory.initElements(driver, EditNormalizedCitePageElements.class);
    }

    public boolean clickCancelButton()
    {
        click(CiteLineManagementsCommonPageElements.cancel);
        waitForPageLoaded();
        waitForElementGone(EditNormalizedCitePageElements.EDIT_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(EditNormalizedCitePageElements.EDIT_WINDOW_TITLE_XPATH);
    }

    public boolean clickUpdateValues()
    {
        click(EditNormalizedCitePageElements.updateValues);
        waitForPageLoaded();
        waitForElementGone(EditNormalizedCitePageElements.EDIT_WINDOW_TITLE_XPATH);
        return !isElementDisplayed(EditNormalizedCitePageElements.EDIT_WINDOW_TITLE_XPATH);
    }
}
