package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.AssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.OnlineProductAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class AssignmentsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AssignmentsPageElements.class);
    }

    public boolean isViewTagInTheGrid(String viewTag)
    {
        return doesElementExist(String.format(AssignmentsPageElements.GRID_VIEW_TAG, viewTag));
    }

    public String getAssignmentStatusByViewTag(String viewTag)
    {
        if (doesElementExist(String.format(AssignmentsPageElements.GRID_ASSIGNMENT_STATUS_BY_VIEW_TAG, viewTag)))
        {
            return getElement(String.format(AssignmentsPageElements.GRID_ASSIGNMENT_STATUS_BY_VIEW_TAG, viewTag)).getText();
        }
        return String.format("There ISN'T '%s' in the grid", viewTag);
    }

    public String getProductTypeByViewTag(String viewTag)
    {
        if (doesElementExist(String.format(AssignmentsPageElements.GRID_PRODUCT_TYPE_BY_VIEW_TAG, viewTag)))
        {
            return getElement(String.format(AssignmentsPageElements.GRID_PRODUCT_TYPE_BY_VIEW_TAG, viewTag)).getText();
        }
        return String.format("There ISN'T '%s' in the grid", viewTag);
    }

    public String getProductNameByViewTag(String viewTag)
    {
        if (doesElementExist(String.format(AssignmentsPageElements.GRID_PRODUCT_NAME_BY_VIEW_TAG, viewTag)))
        {
            return getElement(String.format(AssignmentsPageElements.GRID_PRODUCT_NAME_BY_VIEW_TAG, viewTag)).getText();
        }
        return String.format("There ISN'T '%s' in the grid", viewTag);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CommonPageElements.CLOSE_BUTTON);
        waitForWindowGoneByTitle(OnlineProductAssignmentsPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(OnlineProductAssignmentsPageElements.PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        return windowClosed;
    }

    public void rightClickProductTag(String viewTag)
    {
        rightClick(String.format(AssignmentsPageElements.GRID_VIEW_TAG, viewTag));
    }

    public void selectMultipleTaxTypes(List<String> taxTypes)
    {
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            for (String taxType: taxTypes)
            {
                click(String.format(AssignmentsPageElements.GRID_VIEW_TAG, taxType));
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

}
