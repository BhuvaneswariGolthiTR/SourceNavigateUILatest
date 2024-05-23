package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.edit;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit.EditScriptRulesConfirmationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditScriptRulesConfirmationPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EditScriptRulesConfirmationPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditScriptRulesConfirmationPageElements.class);
    }

    public String getContentSetsEffectedString()
    {
        return getElementsText(EditScriptRulesConfirmationPageElements.contentSetsEffectedListXpath);
    }

    public void clickOK()
    {
        click(EditScriptRulesConfirmationPageElements.okButton);
        waitForPageLoaded(); //this change isn't needed here but it sends the tests into GIGA OVERDRIVE
    }
}
