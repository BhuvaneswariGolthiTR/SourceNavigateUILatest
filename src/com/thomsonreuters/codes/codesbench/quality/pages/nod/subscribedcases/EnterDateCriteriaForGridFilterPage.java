package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.EnterDateCriteriaForGridFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EnterDateCriteriaForGridFilterPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EnterDateCriteriaForGridFilterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EnterDateCriteriaForGridFilterPageElements.class);
    }

    public void clickDateRadioButton()
    {
        click(EnterDateCriteriaForGridFilterPageElements.dateRadioButton);
    }

    public void enterGivenDateIntoTextBox(String date)
    {
        sendTextToTextbox(EnterDateCriteriaForGridFilterPageElements.dateTextBox,date);
    }

    public void clickSubmit()
    {
        click(EnterDateCriteriaForGridFilterPageElements.submitButton);
        waitForWindowGoneByTitle(EnterDateCriteriaForGridFilterPageElements.POPUP_WINDOW_TITLE);
        switchToWindow(SubscribedCasesPageElements.SUBSCRIBED_CASES_PAGE_TITLE);
    }
}
