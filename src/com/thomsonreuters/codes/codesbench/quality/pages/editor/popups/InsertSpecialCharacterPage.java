package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSpecialCharacterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertSpecialCharacterPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertSpecialCharacterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertSpecialCharacterPageElements.class);
    }

    public void sendTextInXmlEntity(String xmlEntity)
    {
        sendKeysToElement(InsertSpecialCharacterPageElements.XML_ENTITY_BOX, xmlEntity);
    }

    public void clickInsert()
    {
        click(InsertSpecialCharacterPageElements.INSERT_BUTTON);
    }

    public boolean switchToInsertSpecialCharacterWindow()
    {
        return switchToWindow(InsertSpecialCharacterPageElements.PAGE_TITLE);
    }
}
