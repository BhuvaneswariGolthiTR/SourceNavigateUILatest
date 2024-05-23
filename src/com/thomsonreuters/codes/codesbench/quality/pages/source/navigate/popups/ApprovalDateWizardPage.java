package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ApprovalDateWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApprovalDateWizardPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ApprovalDateWizardPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ApprovalDateWizardPageElements.class);
    }

    public boolean switchToApprovalDateWizardWindow()
    {
        boolean windowAppeared = switchToWindow(ApprovalDateWizardPageElements.APPROVAL_DATE_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public void setApprovalDate(String date)
    {
        clearAndSendKeysToElement(ApprovalDateWizardPageElements.approvalDateTextBox, date);
    }

    public void selectApvReplace(String replace)
    {
        checkCheckbox(ApprovalDateWizardPageElements.apvReplaceRadioButton);
        selectDropdownOption(ApprovalDateWizardPageElements.apvReplaceDropdown, replace);
    }

    public void selectApvNoChange()
    {
        checkCheckbox(ApprovalDateWizardPageElements.apvNoChangeRadioButton);
    }

    public void clickSubmit()
    {
        sendEnterToElement(ApprovalDateWizardPageElements.submitButton);
        waitForWindowGoneByTitle(ApprovalDateWizardPageElements.APPROVAL_DATE_PAGE_TITLE);
    }

    public void clickCancel()
    {
        sendEnterToElement(ApprovalDateWizardPageElements.cancelButton);
        waitForWindowGoneByTitle(ApprovalDateWizardPageElements.APPROVAL_DATE_PAGE_TITLE);
    }
}
