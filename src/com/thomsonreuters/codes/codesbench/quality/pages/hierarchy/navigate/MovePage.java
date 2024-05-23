package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.MovePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MovePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public MovePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, MovePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(MovePageElements.quickLoadButton);
        waitForPageLoaded();
        click(MovePageElements.okButton);
        waitForWindowGoneByTitle(MovePageElements.MOVE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
