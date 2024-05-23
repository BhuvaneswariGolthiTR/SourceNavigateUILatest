package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GroupingPropertiesPageElements {

    @FindBy(how = How.XPATH, using = "//input[value='Cancel']")
    public static WebElement closeButton;

    @FindBy(how = How.ID, using = "pageForm:effectiveDate")
    public static WebElement sectionEffectiveDate;

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:assignedFor']/option[@selected='selected']")
    public static WebElement assignedUser;

    @FindBy(how = How.ID, using = "pageForm:assignedDate")
    public static WebElement assignedDate;

    @FindBy(how = How.ID, using = "pageForm:cpdDate")
    public static WebElement cpdDate;

    @FindBy(how = How.ID, using = "addCpdDate")
    public static WebElement addCpdDateButton;

    @FindBy(how = How.ID, using = "removeCpdDate")
    public static WebElement removeCpdDateButton;

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "cpdDateCalendar")
    public static WebElement cpdDateCalendar;

    public static final String PROPERTIES_CALENDAR_TODAY = "//td[contains(@class, 'today')]";
    public static final String SECTION_GROUP_PROP_CPD_CALENDAR = "//table[@id= 'cpdDateCalendar']";
    public static final String CPD_DATE_XPATH = "//input[@id='pageForm:cpdDate']";
}
