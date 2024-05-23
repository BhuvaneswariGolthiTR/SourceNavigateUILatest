package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeleteQueryNotePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeleteQueryNotePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeleteQueryNotePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteQueryNotePageElements.class);
    }

    public void clickDeleteButton()
    {
        waitForElement(DeleteQueryNotePageElements.deleteButton);
        click(DeleteQueryNotePageElements.deleteButton);
    }

}