package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.ViewAssignmentStatusPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ViewAssignmentStatusPage extends BasePage
{

    public WebDriver driver;

    @Autowired
    public ViewAssignmentStatusPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewAssignmentStatusPageElements.class);
    }

    public boolean isOnlineProductAssignedToContentSets(List<String> contentSets)
    {
        waitForPageLoaded();
        for (String contentSet : contentSets)
        {
            if (!doesElementExist(String.format(ViewAssignmentStatusPageElements.CONTENT_SET_NAME, contentSet)))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isNodeAssignmentColumnBlank(List<String> contentSets)
    {
        for (String name : contentSets)
        {
            if (getNodeAssignmentByContentSetName(name).matches("Assigned"))
            {
                return false;
            }
        }
        return true;
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(ViewAssignmentStatusPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(ViewAssignmentStatusPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public String getNodeAssignmentByContentSetName(String contentSetName)
    {
        if (doesElementExist(String.format(ViewAssignmentStatusPageElements.CONTENT_SET_NAME, contentSetName)))
        {
            return getElement(String.format(ViewAssignmentStatusPageElements.NODE_ASSIGNMENT_BY_CONTENT_SET_NAME, contentSetName)).getText();
        }
        return String.format("%s DOESN'T exist", contentSetName);
    }


}
