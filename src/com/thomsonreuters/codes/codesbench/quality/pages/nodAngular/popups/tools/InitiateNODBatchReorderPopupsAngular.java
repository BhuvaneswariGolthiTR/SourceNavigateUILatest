package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODBatchReorderPopupsElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateNODBatchReorderPopupsAngular extends BasePage
{
    @Autowired
    public InitiateNODBatchReorderPopupsAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InitiateNODBatchReorderPopupsElementsAngular.class);
    }

    public String getStatus()
    {
        return InitiateNODBatchReorderPopupsElementsAngular.status.getText();
    }

    public String getCurrentUserName()
    {
        return InitiateNODBatchReorderPopupsElementsAngular.currentUser.getText();
    }

}
