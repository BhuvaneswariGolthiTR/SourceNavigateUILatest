package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.OnlineProductAssignmentsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnlineProductAssignmentsPage extends AssignmentsPage
{
    private WebDriver driver;

    @Autowired
    public OnlineProductAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductAssignmentsPageElements.class);
    }

    public void switchToOnlineProductAssignmentsPage()
    {
        switchToWindow(OnlineProductAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

}
