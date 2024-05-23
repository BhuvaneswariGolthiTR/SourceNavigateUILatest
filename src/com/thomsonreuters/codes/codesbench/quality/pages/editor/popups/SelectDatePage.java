package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SelectDateElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SelectDatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SelectDatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SelectDateElements.class);
    }

    public void setDate(String Date)
    {
        clearAndSendTextToTextbox(getElement(SelectDateElements.INPUT_DATE), Date);
    }

    public void clickOK()
    {
        sendEnterToElement(CommonPageElements.OK_BUTTON);
    }
}
