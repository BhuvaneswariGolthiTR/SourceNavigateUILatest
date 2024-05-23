package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.DeleteOnlineProductPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DeleteOnlineProductPage extends BasePage
{

    private WebDriver driver;

    @Autowired
    public DeleteOnlineProductPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteOnlineProductPageElements.class);
    }

    public boolean clickDelete()
    {
        sendEnterToElement(DeleteOnlineProductPageElements.DELETE_BUTTON);
        waitForWindowGoneByTitle(DeleteOnlineProductPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(DeleteOnlineProductPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(DeleteOnlineProductPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(DeleteOnlineProductPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public boolean isOnlineProductAssigned()
    {
        boolean result = true;
        List<String> assignmentsList = getElementsTextList(DeleteOnlineProductPageElements.ASSIGNMENTS_LIST);
        if (assignmentsList.size() == 1 && assignmentsList.get(0).equalsIgnoreCase("Not assigned"))
        {
            result = false;
        }
        return result;
    }

    public String getMessage()
    {
        waitForPageLoaded();
        if (doesElementExist(DeleteOnlineProductPageElements.MESSAGE))
        {
            return getElement(DeleteOnlineProductPageElements.MESSAGE).getText();
        }
        return "There is NOT message on the Delete Online Product page";
    }

    public List<String> getListAssigned()
    {
        return getElementsTextList(DeleteOnlineProductPageElements.ASSIGNMENTS_LIST);
    }

    public boolean isDeleteButtonEnabled()
    {
        return isElementEnabled(DeleteOnlineProductPageElements.delete);
    }

}
