package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularRenditionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceNavigateAngularRenditionPage extends BasePage {
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularRenditionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, SourceNavigateAngularRenditionPageElements.class);
    }

    public boolean verifyLockIconState(boolean state) {
        return (doesElementExist(SourceNavigateAngularRenditionPageElements.lockIcon) == state);
    }

    public boolean verifyPageTitle(String homePageLabel, boolean state) {
        return (doesElementExist(homePageLabel) == state);
    }
    public String renditionlockedByUsername()
    {
        return getElementsText(SourceNavigateAngularRenditionPageElements.RENDITION_LOCKED_BY ).trim();
    }
    public boolean getRenditionLevelInstructionsReadOnly()
    {
        return getElementsAttribute(SourceNavigateAngularRenditionPageElements.renditionLevelinstructions, "readonly").equals("true");
    }
    public void addRenditionNotes(String text)
    {
        sendTextToTextbox(SourceNavigateAngularRenditionPageElements.renditionLevelinstructions, text);
    }
    public void clearRenditionNotes(WebElement xpath)
    {
        click(xpath);
        editorTextPage().ctrlAUsingAction();
        editorTextPage().deleteUsingAction();
    }

    public boolean verifyLockIconStateOfRendition() {
        return (doesElementExist(SourceNavigateAngularRenditionPageElements.lockIcon));
    }
}
