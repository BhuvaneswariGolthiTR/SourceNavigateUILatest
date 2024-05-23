package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ValidationReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ValidationReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ValidationReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ValidationReportPageElements.class);
    }

    public boolean switchToValidationReportWindow()
    {
        boolean windowAppears = switchToWindow(ValidationReportPageElements.VALIDATION_REPORT_PAGE_TITLE);
        waitForPageLoaded();
        waitForGridRefresh();
        return windowAppears;
    }

    public boolean validationReportContainsRendition()
    {
        return isElementDisplayed(ValidationReportPageElements.FIRST_RENDITION_VALIDATE);
    }

    public boolean clearRendition()
    {
        int countBeforeClear = getElements(ValidationReportPageElements.VALIDATE_COUNT_RENDITION).size();

        rightClick(ValidationReportPageElements.FIRST_RENDITION_VALIDATE);
        breakOutOfFrame();
        click(ValidationReportPageElements.clearWarningFlags);
        AutoITUtils.verifyAlertTextAndAccept(true, "Clear warning flags on the selected renditions?");
        sendKeys(Keys.ENTER);
        waitForGridRefresh();

        int countAfterClear = getElements(ValidationReportPageElements.VALIDATE_COUNT_RENDITION).size();

        return (countBeforeClear - 1) == countAfterClear;
    }

    public void closeFromInsideIframe()
    {
        switchToWindow(ValidationReportPageElements.VALIDATION_REPORT_PAGE_TITLE);
        click(ValidationReportPageElements.closeButton);
    }
}
