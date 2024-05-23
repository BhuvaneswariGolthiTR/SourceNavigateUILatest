package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODBatchMergePopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateNODBatchMergePopupAngular extends BasePage
{
    @Autowired
    public InitiateNODBatchMergePopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InitiateNODBatchMergePopupElementsAngular.class);
    }

    public String getStatus()
    {
        return InitiateNODBatchMergePopupElementsAngular.status.getText();
    }

    public String getCurrentUserName()
    {
        return InitiateNODBatchMergePopupElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(InitiateNODBatchMergePopupElementsAngular.MESSAGE);
    }

    public void setFileName(String fileName)
    {
        InitiateNODBatchMergePopupElementsAngular.fileName.sendKeys(fileName);
    }

    public void setSourceTag(String sourceTag)
    {
        InitiateNODBatchMergePopupElementsAngular.sourceTag.clear();
        InitiateNODBatchMergePopupElementsAngular.sourceTag.sendKeys(sourceTag);
    }

    public void clearSourceTag( )
    {
        int size = InitiateNODBatchMergePopupElementsAngular.sourceTag.getAttribute("value").length();
        while (size > 0)
        {
            InitiateNODBatchMergePopupElementsAngular.sourceTag.sendKeys(Keys.DELETE);
            size = size - 1;
        }
    }

    public boolean isSourceTagDisplayed()
    {return InitiateNODBatchMergePopupElementsAngular.sourceTag.isDisplayed();}

    public boolean isFileNameDisplayed()
    {return InitiateNODBatchMergePopupElementsAngular.fileName.isDisplayed();}

}
