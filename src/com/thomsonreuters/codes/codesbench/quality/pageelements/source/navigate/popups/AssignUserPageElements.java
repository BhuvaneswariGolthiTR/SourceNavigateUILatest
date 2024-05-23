package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AssignUserPageElements
{
	public static final String ASSIGN_USER_PAGE_TITLE = "Assign User";
	public static final String ASSIGNED_USER_DROPDOWN = "//select[@id='pageForm:assignedToid' or @id='pageForm:assignedFor']";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:assignDate']")
	public static WebElement assignedDateCalendar;

	@FindBy(how = How.ID, using = "pageForm:assignedToid")
	public static WebElement assignToUserDropdown;

	@FindBy(how = How.ID, using = "showAssignDateCalender")
	public static WebElement assignDateCalender;

	@FindBy(how = How.ID, using = "pageForm:assignDate")
	public static WebElement assignDateTextBox;

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
}
