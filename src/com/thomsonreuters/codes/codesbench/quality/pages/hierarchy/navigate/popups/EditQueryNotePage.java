package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.EditQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditQueryNotePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EditQueryNotePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditQueryNotePageElements.class);
    }

    public void setDate(String date)
    {
        clearAndSendTextToTextbox(getElement(EditQueryNotePageElements.ACTION_DATE_INPUT),date);
    }

    public void clickSave()
    {
        sendEnterToElement(CommonPageElements.SAVE_BUTTON);
    }
}
