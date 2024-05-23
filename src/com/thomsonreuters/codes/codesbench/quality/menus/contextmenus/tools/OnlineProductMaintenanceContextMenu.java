package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.OnlineProductMaintenanceContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenanceGridPageElement;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnlineProductMaintenanceContextMenu extends ContextMenu
{

    private WebDriver driver;

    @Autowired
    public OnlineProductMaintenanceContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductMaintenanceContextMenuElements.class);
        PageFactory.initElements(driver, OnlineProductMaintenanceGridPageElement.class);
    }

    public boolean createNew()
    {
        rightClick(OnlineProductMaintenanceGridPageElement.ROWS);
        openContextMenu(OnlineProductMaintenanceContextMenuElements.CREATE_NEW);
        boolean createNewProductWindowAppeared = switchToWindow(CreateNewOnlineProductPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return createNewProductWindowAppeared;
    }

    public boolean editNamesAndAssignments()
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.EDIT_NAMES_AND_ASSIGNMENTS);
        boolean editOnlineProductWindowAppeared = switchToWindow(EditNamesAndAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return editOnlineProductWindowAppeared;
    }

    public boolean viewAssignmentStatus()
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.VIEW_ASSIGNMENT_STATUS);
        boolean viewAssignmentStatusWindowAppeared = switchToWindow(ViewAssignmentStatusPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return viewAssignmentStatusWindowAppeared;
    }

    public boolean delete()
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.DELETE_ONLINE_PRODUCT);
        boolean deleteOnlineProductWindowAppeared = switchToWindow(DeleteOnlineProductPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return deleteOnlineProductWindowAppeared;
    }

    public boolean runProductTagReport()
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.RUN_PRODUCT_TAG_REPORT);
        boolean deleteOnlineProductWindowAppeared = switchToWindow(OnlineProductAssignmentReportPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return deleteOnlineProductWindowAppeared;
    }

    public String removeAssignments(String contentSet, String productName)
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.REMOVE_ASSIGNMENT);
        String alertText = getAlertText();
        acceptAlert();
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return alertText;
    }

    public boolean isCreateNewDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.CREATE_NEW));
    }

    public boolean isEditNamesAndAssignmentsDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.EDIT_NAMES_AND_ASSIGNMENTS));
    }

    public boolean isDeleteOnlineProductDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.DELETE_ONLINE_PRODUCT));
    }

    public boolean isRunProductTagReportDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.RUN_PRODUCT_TAG_REPORT));
    }

    public boolean isRemoveAssignmentDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.REMOVE_ASSIGNMENT));
    }

    public boolean isViewAssignmentStatusDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.VIEW_ASSIGNMENT_STATUS));
    }

    public boolean isUpdateAttributeValuesDisable()
    {
        return isElementDisabled(getElement(OnlineProductMaintenanceContextMenuElements.UPDATE_ATTRIBUTE_VALUES));
    }


    public void undelete()
    {
        openContextMenu(OnlineProductMaintenanceContextMenuElements.UNDELETE);
    }

}
