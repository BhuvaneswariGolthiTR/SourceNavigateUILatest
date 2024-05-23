package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchySetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HierarchySetLawTrackingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public HierarchySetLawTrackingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchySetLawTrackingPageElements.class);
    }

    public void quickLoadOk()
    {
        click(HierarchySetLawTrackingPageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(HierarchySetLawTrackingPageElements.okButton);
        waitForWindowGoneByTitle(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickCancel()
    {
        click(HierarchySetLawTrackingPageElements.cancelButton);
        waitForWindowGoneByTitle(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}

