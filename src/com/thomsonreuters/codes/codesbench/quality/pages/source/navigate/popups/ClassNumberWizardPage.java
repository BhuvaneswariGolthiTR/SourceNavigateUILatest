package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ClassNumberWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClassNumberWizardPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ClassNumberWizardPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ClassNumberWizardPageElements.class);
    }

    public boolean switchToClassNumberWizardWindow()
    {
        boolean windowAppears = switchToWindow(ClassNumberWizardPageElements.CLASS_NUMBER_WIZARD_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void setClassNumber(String classNum)
    {
        clearAndSendKeysToElement(ClassNumberWizardPageElements.classNumberTextBox, classNum);
    }

    public void replaceScpClassNumber(String replace)
    {
        checkCheckbox(ClassNumberWizardPageElements.scpReplaceRadioButton);
        selectDropdownOption(ClassNumberWizardPageElements.scpReplaceDropdown, replace);
    }

    public void selectScpNoChange()
    {
        checkCheckbox(ClassNumberWizardPageElements.scpNoChangeRadioButton);
    }

    public void selectDtypeNoChange()
    {
        checkCheckbox(ClassNumberWizardPageElements.dtypeNoChangeRadioButton);
    }

    public void selectHg1NoChange()
    {
        checkCheckbox(ClassNumberWizardPageElements.hg1NoChangeRadioButton);
    }

    public void selectHg2NoChange()
    {
        checkCheckbox(ClassNumberWizardPageElements.hg2NoChangeRadioButton);
    }

    public void clickSubmit()
    {
        sendEnterToElement(ClassNumberWizardPageElements.submitButton);
        waitForWindowGoneByTitle(ClassNumberWizardPageElements.CLASS_NUMBER_WIZARD_PAGE_TITLE);
    }

    public void clickCancel()
    {
        sendEnterToElement(ClassNumberWizardPageElements.cancelButton);
        waitForWindowGoneByTitle(ClassNumberWizardPageElements.CLASS_NUMBER_WIZARD_PAGE_TITLE);
    }
}
