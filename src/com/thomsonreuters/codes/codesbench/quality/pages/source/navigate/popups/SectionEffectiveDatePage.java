package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SectionEffectiveDatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionEffectiveDatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionEffectiveDatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionEffectiveDatePageElements.class);
    }

    public void setEffectiveDate(String date)
    {
        sendTextToTextbox(SectionEffectiveDatePageElements.effectiveDateInput, date);
    }

    public void checkRunCiteLocateToUpdateIntegrationStatusCheckbox()
    {
        checkCheckbox(SectionEffectiveDatePageElements.runCiteLocateCheckbox);
    }

    public void clickSave(){
        click(SectionEffectiveDatePageElements.saveButton);
    }

}
