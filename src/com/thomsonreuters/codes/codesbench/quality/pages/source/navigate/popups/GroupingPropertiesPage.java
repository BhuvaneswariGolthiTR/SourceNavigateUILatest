package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.GroupingPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.RenditionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractPropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Locale;

@Component
public class GroupingPropertiesPage extends AbstractPropertiesPage
{
    private WebDriver driver;

    @Autowired
    public GroupingPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, GroupingPropertiesPageElements.class);
    }

    public void closeProperties()
    {
        super.clickCancel();
        waitForWindowGoneByTitle("Properties");
        switchToWindow("Navigate");
        waitForGridRefresh();
    }
    public boolean checkEffectiveDateIsToday()
    {
        return GroupingPropertiesPageElements.sectionEffectiveDate.getAttribute("value").equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
    }
    public boolean checkAssignedUser(String user)
    {
        return GroupingPropertiesPageElements.assignedUser.getText().equals(user);
    }

    public boolean checkAssignedDateIsToday()
    {
        return GroupingPropertiesPageElements.assignedDate.getAttribute("value").equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
    }

    public String getCPDDate()
    {
        return getElementsAttribute(GroupingPropertiesPageElements.cpdDate, "value");
    }

    public boolean removeCPDDateCancelAlert()
    {
        click(GroupingPropertiesPageElements.removeCpdDateButton);
        return AutoITUtils.verifyAlertTextAndCancel(true, "This date was provided by the system when the group was run through CPD. This Date should ordinarily only be removed if the running of CPD was a mistake. Do you want to proceed with deletion of the CPD Date?");
    }

    public boolean removeCPDDateAcceptAlert()
    {
        click(GroupingPropertiesPageElements.removeCpdDateButton);
        return AutoITUtils.verifyAlertTextContainsAndAccept(true, "This date was provided by the system when the group was run through CPD. This Date should ordinarily only be removed if the running of CPD was a mistake. Do you want to proceed with deletion of the CPD Date?");
    }

    public boolean addCPDDateCancelAlert()
    {
        click(GroupingPropertiesPageElements.addCpdDateButton);
        return AutoITUtils.verifyAlertTextAndCancel(true,"This date is usually provided by the system when a group is run through CPD. This Date should ordinarily only be added manually if the removal of a system-generated date was done by mistake. Do you want to proceed with adding of the CPD Date?");
    }

    public boolean addCPDDateAcceptAlert()
    {
        click(GroupingPropertiesPageElements.addCpdDateButton);
        return AutoITUtils.verifyAlertTextAndAccept(true,"This date is usually provided by the system when a group is run through CPD. This Date should ordinarily only be added manually if the removal of a system-generated date was done by mistake. Do you want to proceed with adding of the CPD Date?");
    }

    public void setCPDDateToTodaysDate()
    {
        Calendar mCalendar = Calendar.getInstance();
        String currentMonth = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String monthInCalendar = getElement(GroupingPropertiesPageElements.SECTION_GROUP_PROP_CPD_CALENDAR + "//a[@class='calnav']").getText().split("\\s")[0];
        while(!(currentMonth.equals(monthInCalendar)))
        {
            click(GroupingPropertiesPageElements.SECTION_GROUP_PROP_CPD_CALENDAR + "//a[@class='calnavright']");
            monthInCalendar = getElement(GroupingPropertiesPageElements.SECTION_GROUP_PROP_CPD_CALENDAR + "//a[@class='calnav']").getText().split("\\s")[0];
        }
        clickCalendarDay(Integer.parseInt(DateAndTimeUtils.getCurrentDayDD()));
    }

    public boolean clickCalendarDay(int day)
    {
        click(String.format(RenditionPropertiesPageElements.CALENDAR_DAY,day));
        return !isElementDisplayed(RenditionPropertiesPageElements.CALCONTAINER_BLOCK);
    }

    public void clickSaveButton()
    {
        click(GroupingPropertiesPageElements.saveButton);
    }
}
