package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CreateWipVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPubNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class CreateWipVersionPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateWipVersionPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateWipVersionPageElements.class);
    }

    public void clickQuickLoadButton()
    {
        click(CreateWipVersionPageElements.quickLoadButton);
    }

    public void clickSubmitButton()
    {
        click(CreateWipVersionPageElements.submitButton);
        waitForWindowGoneByTitle(CreateWipVersionPageElements.CREATE_WIP_VERSION_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
    }

    public void clickSubmitButtonWithoutLawTrackingSelected()
    {
        click(CreateWipVersionPageElements.submitButton);
    }

    public void clickCancelButton()
    {
        click(CreateWipVersionPageElements.CANCEL_BUTTON_XPATH);
        waitForWindowGoneByTitle(CreateWipVersionPageElements.CREATE_WIP_VERSION_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
    }

    public boolean isOnlyOneErrorMessageDisplayed()
    {
        return getElements(CreateWipVersionPageElements.PLEASE_SET_LAW_TRACKING_ERROR_MESSAGE_XPATH).size() == 1;
    }
}