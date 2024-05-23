package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeepCopyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeepCopyPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeepCopyPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeepCopyPageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(DeepCopyPageElements.quickLoadButton);
        waitForPageLoaded();
        click(DeepCopyPageElements.okButton);
        waitForWindowGoneByTitle(DeepCopyPageElements.DEEP_COPY_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
