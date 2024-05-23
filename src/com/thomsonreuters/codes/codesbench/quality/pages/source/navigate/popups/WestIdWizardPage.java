package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.WestIdWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WestIdWizardPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public WestIdWizardPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, WestIdWizardPageElements.class);
    }

    public boolean switchToWestIdWizardWindow()
    {
        boolean windowAppears = switchToWindow(WestIdWizardPageElements.WEST_ID_WIZARD_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppears;
    }

    public void enterWestId(String id)
    {
        clearAndSendKeysToElement(WestIdWizardPageElements.westIdTextBox, id);
    }

    public void replaceScpClassNumber(String replace)
    {
        checkCheckbox(WestIdWizardPageElements.scpReplaceRadioButton);
        selectDropdownOption(WestIdWizardPageElements.scpReplaceDropdown, replace);
    }

    public void selectScpNoChange()
    {
        checkCheckbox(WestIdWizardPageElements.scpNoChangeRadioButton);
    }

    public void selectHg2NoChange()
    {
        checkCheckbox(WestIdWizardPageElements.hg2NoChangeRadioButton);
    }

    public void clickSubmit()
    {
        sendEnterToElement(WestIdWizardPageElements.submitButton);
        waitForWindowGoneByTitle(WestIdWizardPageElements.WEST_ID_WIZARD_PAGE_TITLE);
    }

    public void clickCancel()
    {
        sendEnterToElement(WestIdWizardPageElements.cancelButton);
        waitForWindowGoneByTitle(WestIdWizardPageElements.WEST_ID_WIZARD_PAGE_TITLE);
    }
}
