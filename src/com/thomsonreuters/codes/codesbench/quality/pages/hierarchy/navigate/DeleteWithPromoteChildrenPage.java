package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeleteWithPromoteChildrenPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class DeleteWithPromoteChildrenPage extends BasePage
{
    private WebDriver driver;
    @Autowired
    public DeleteWithPromoteChildrenPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteWithPromoteChildrenPageElements.class);
    }

    
    public void pressQuickLoad()
    {
        click(DeleteWithPromoteChildrenPageElements.quickLoadButton);
        waitForPageLoaded();
    }

    
    public void pressSubmit()
    {
        click(DeleteWithPromoteChildrenPageElements.submitButton);
        waitForWindowGoneByTitle(DeleteWithPromoteChildrenPageElements.PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
