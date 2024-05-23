package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeepClonePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeepClonePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeepClonePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeepClonePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(DeepClonePageElements.quickLoadButton);
        waitForPageLoaded();
        click(DeepClonePageElements.okButton);
    }

    public void setCurrentDateAsEffectiveStartDate()
    {
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendKeysToElement(DeepClonePageElements.effectiveStartDateTextBox, date);
    }
}