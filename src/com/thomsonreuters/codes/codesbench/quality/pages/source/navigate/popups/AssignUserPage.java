package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.AssignUserPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AssignUserPage extends BasePage
{
    WebDriver driver;

    @Autowired
    public AssignUserPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AssignUserPageElements.class);
    }

    public boolean switchToAssignedUserPage()
    {
        boolean windowAppears = switchToWindow(AssignUserPageElements.ASSIGN_USER_PAGE_TITLE);
        switchToInnerIFrameByIndex(0);
        waitForPageLoaded();
        return windowAppears;
    }

    public void setAssignedUser(String user)
    {
        selectDropdownOption(getElement(AssignUserPageElements.ASSIGNED_USER_DROPDOWN), user);
    }

    public void setAssignedDate(String date)
    {
        sendTextToTextbox(AssignUserPageElements.assignedDateCalendar, date);
    }

    public String getAssignedUserDropdownText()
    {
        return getSelectedDropdownOptionText(AssignUserPageElements.ASSIGNED_USER_DROPDOWN);
    }

    public void clearAssignedUserDate()
    {
        clear(AssignUserPageElements.assignDateTextBox);
    }

    public void selectAssignedUserDropdown(String user)
    {
        selectDropdownOption(AssignUserPageElements.ASSIGNED_USER_DROPDOWN, user);
    }

    public void clickSave() {
        sendEnterToElement(AssignUserPageElements.saveButton);
        checkAlert();
        waitForWindowGoneByTitle(AssignUserPageElements.ASSIGN_USER_PAGE_TITLE);
        waitForPageLoaded();
        waitForGridRefresh();
    }
    public void selectUser(String user)
    {
        selectDropdownOption(AssignUserPageElements.assignToUserDropdown,user);
    }

    public void clickAssignUserCalender()
    {
        click(AssignUserPageElements.assignDateCalender);
    }

    public void submitAssignedUser()
    {
        click(AssignUserPageElements.saveButton);
    }
}
