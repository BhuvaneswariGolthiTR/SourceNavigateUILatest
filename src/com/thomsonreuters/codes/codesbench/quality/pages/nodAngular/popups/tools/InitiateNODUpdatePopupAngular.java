package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODUpdatePopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateNODUpdatePopupAngular extends BasePage
{
    @Autowired
    public InitiateNODUpdatePopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InitiateNODUpdatePopupElementsAngular.class);
    }

    public String getStatus()
    {
        return InitiateNODUpdatePopupElementsAngular.status.getText();
    }

    public String getCurrentUserName()
    {
        return InitiateNODUpdatePopupElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(InitiateNODUpdatePopupElementsAngular.MESSAGE);
    }

    public void setFileName(String fileName)
    {
        InitiateNODUpdatePopupElementsAngular.fileName.sendKeys(fileName);
    }

    public void setSourceTag(String sourceTag)
    {
        InitiateNODUpdatePopupElementsAngular.sourceTag.clear();
        InitiateNODUpdatePopupElementsAngular.sourceTag.sendKeys(sourceTag);
    }

    public void clearSourceTag( )
    {
        int size = InitiateNODUpdatePopupElementsAngular.sourceTag.getAttribute("value").length();
        while (size > 0)
        {
            InitiateNODUpdatePopupElementsAngular.sourceTag.sendKeys(Keys.DELETE);
            size = size - 1;
        }
    }

    public boolean isSourceTagDisplayed()
    {
        return InitiateNODUpdatePopupElementsAngular.sourceTag.isDisplayed();
    }

    public boolean isFileNameDisplayed()
    {
        return InitiateNODUpdatePopupElementsAngular.fileName.isDisplayed();
    }

}
