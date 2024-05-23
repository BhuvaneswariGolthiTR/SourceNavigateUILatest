package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeltaEffectiveDatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaEffectiveDatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaEffectiveDatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaEffectiveDatePageElements.class);
    }
    public void setCurrentDateInEffectiveDateCalender()
    {
        enterTheInnerFrame();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendTextToTextbox(DeltaEffectiveDatePageElements.effectiveDateTextBox, currentDate);
    }
    public void pressSave()
    {
        click(DeltaEffectiveDatePageElements.saveButton);
    }
}
