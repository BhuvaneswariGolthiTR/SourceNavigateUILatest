package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.AuditCorrectionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class AuditCorrectionsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AuditCorrectionsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AuditCorrectionsPageElements.class);
    }

    public boolean switchToAuditCorrectionsWindow()
    {
        boolean windowAppears = switchToWindow(AuditCorrectionsPageElements.AUDIT_CORRECTIONS_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void selectCorrectionsAuditor(String auditor)
    {
        selectDropdownOption(AuditCorrectionsPageElements.CORRECTIONS_AUDITOR_DROPDOWN, auditor);
    }

    public void clickSave()
    {
        click(AuditCorrectionsPageElements.saveButton);
        waitForWindowGoneByTitle(AuditCorrectionsPageElements.AUDIT_CORRECTIONS_PAGE_TITLE);
        waitForGridRefresh();
    }

    public void clickCancel()
    {
        sendEnterToElement(AuditCorrectionsPageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
    }
}
