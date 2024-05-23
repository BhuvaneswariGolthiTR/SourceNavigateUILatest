package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping.DeltaGroupingNewGroupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupingNewGroupPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingNewGroupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingNewGroupPageElements.class);
    }

    public void setGroupName(String groupName)
    {
        sendKeysToElement(DeltaGroupingNewGroupPageElements.newGroupTitle, groupName);
    }

    public void clickSave()
    {
        click(DeltaGroupingNewGroupPageElements.saveButton);
    }

}
