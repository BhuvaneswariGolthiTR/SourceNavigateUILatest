package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.DeleteCitelineReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.CiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeleteCitelineReferencePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeleteCitelineReferencePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteCitelineReferencePageElements.class);
    }

    public void clickYes()
    {
        click(DeleteCitelineReferencePageElements.yesButton);
        waitForGridRefresh();
    }

    public void clickNo()
    {
        click(DeleteCitelineReferencePageElements.noButton);
    }
}
