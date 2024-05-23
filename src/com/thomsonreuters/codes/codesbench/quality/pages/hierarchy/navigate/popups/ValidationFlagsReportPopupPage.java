package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ValidationFlagsReportPopupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ValidationFlagsReportPopupPage extends BasePage
{
    WebDriver driver;

    @Autowired
    public ValidationFlagsReportPopupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() { PageFactory.initElements(driver, ValidationFlagsReportPopupPageElements.class); }

    public boolean isNodeWithGivenValueDisplayed(String nodeValue)
    {
        return doesElementExist(String.format(ValidationFlagsReportPopupPageElements.NODE_WITH_GIVEN_VALUE,nodeValue));
    }

    public String getErrorValueForGivenNode(String nodeUUID)
    {
        return getElementsText(String.format(ValidationFlagsReportPopupPageElements.VALIDATION_ERROR_OF_GIVEN_NODE, nodeUUID));
    }


    public void rightClickNodeWithGivenValue(String nodeValue)
    {
        rightClick(String.format(ValidationFlagsReportPopupPageElements.NODE_WITH_GIVEN_VALUE,nodeValue));
    }

    public void clickClearWarningFlag()
    {
        click(ValidationFlagsReportPopupPageElements.clearWarningFlagOption);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clickClose()
    {
        click(ValidationFlagsReportPopupPageElements.closeButton);
        waitForWindowGoneByTitle(ValidationFlagsReportPopupPageElements.VALIDATION_FLAGS_REPORT_POPUP_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
