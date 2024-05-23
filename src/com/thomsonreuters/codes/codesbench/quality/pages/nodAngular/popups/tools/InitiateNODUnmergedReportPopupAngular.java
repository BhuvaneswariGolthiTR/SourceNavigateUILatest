package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODUnmergedReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitiateNODUnmergedReportPopupAngular extends BasePage
{
    @Autowired
    public InitiateNODUnmergedReportPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, InitiateNODUnmergedReportPopupElementsAngular.class);
    }

    public String getStatus() {
        return InitiateNODUnmergedReportPopupElementsAngular.status.getText();
    }

    public String getCurrentUserName() {
        return InitiateNODUnmergedReportPopupElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(InitiateNODUnmergedReportPopupElementsAngular.MESSAGE);
    }

    public void setFileName(String fileName)
    {
        InitiateNODUnmergedReportPopupElementsAngular.fileName.sendKeys(fileName);
    }

    public void setSourceTag(String sourceTag)
    {
        InitiateNODUnmergedReportPopupElementsAngular.sourceTag.clear();
        InitiateNODUnmergedReportPopupElementsAngular.sourceTag.sendKeys(sourceTag);
    }

    public void clearSourceTag( )
    {
        int size = InitiateNODUnmergedReportPopupElementsAngular.sourceTag.getAttribute("value").length();
        while (size > 0)
        {
            InitiateNODUnmergedReportPopupElementsAngular.sourceTag.sendKeys(Keys.DELETE);
            size = size - 1;
        }
    }

    public boolean isSourceTagDisplayed() {
        return InitiateNODUnmergedReportPopupElementsAngular.sourceTag.isDisplayed();
    }

    public boolean isFileNameDisplayed() {
        return InitiateNODUnmergedReportPopupElementsAngular.fileName.isDisplayed();
    }

}