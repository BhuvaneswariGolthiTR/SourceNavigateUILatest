package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateXUSSCUpdatePopupsElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateXUSSCUpdatePopupAngular extends BasePage
{
    @Autowired
    public InitiateXUSSCUpdatePopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InitiateXUSSCUpdatePopupsElementsAngular.class);
    }

    public String getStatus()
    {
        return InitiateXUSSCUpdatePopupsElementsAngular.status.getText();
    }

    public String getCurrentUserName()
    {
        return InitiateXUSSCUpdatePopupsElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(InitiateXUSSCUpdatePopupsElementsAngular.MESSAGE);
    }

    public void setFileName(String fileName)
    {
        InitiateXUSSCUpdatePopupsElementsAngular.fileName.sendKeys(fileName);
    }

    public void setSourceTag(String sourceTag)
    {
        InitiateXUSSCUpdatePopupsElementsAngular.sourceTag.clear();
        InitiateXUSSCUpdatePopupsElementsAngular.sourceTag.sendKeys(sourceTag);
    }

    public void clearSourceTag( )
    {
        int size = InitiateXUSSCUpdatePopupsElementsAngular.sourceTag.getAttribute("value").length();
        while (size > 0)
        {
            InitiateXUSSCUpdatePopupsElementsAngular.sourceTag.sendKeys(Keys.DELETE);
            size = size - 1;
        }
    }

    public boolean isSourceTagDisplayed()
    {
        return InitiateXUSSCUpdatePopupsElementsAngular.sourceTag.isDisplayed();
    }

    public boolean isFileNameDisplayed()
    {
        return InitiateXUSSCUpdatePopupsElementsAngular.fileName.isDisplayed();
    }

}
