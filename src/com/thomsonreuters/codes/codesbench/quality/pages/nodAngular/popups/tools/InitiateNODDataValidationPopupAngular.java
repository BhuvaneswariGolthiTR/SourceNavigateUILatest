package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODDataValidationPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateNODDataValidationPopupAngular extends BasePage
{

    @Autowired
    public InitiateNODDataValidationPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InitiateNODDataValidationPopupElementsAngular.class);
    }

    public String getStatus()
    {
        return InitiateNODDataValidationPopupElementsAngular.status.getText();
    }

    public String getCurrentUserName()
    {
        return InitiateNODDataValidationPopupElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(InitiateNODDataValidationPopupElementsAngular.MESSAGE);
    }

    public void setFileName(String fileName)
    {
        InitiateNODDataValidationPopupElementsAngular.fileName.sendKeys(fileName);
    }

    public boolean isFileNameDisplayed()
    {
        return InitiateNODDataValidationPopupElementsAngular.fileName.isDisplayed();
    }

}
