package com.thomsonreuters.codes.codesbench.quality.pageelements.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ContentPreferencesPageElements
{
	public static final String CONTENT_PREFERENCES_PAGE_TITLE = "Content Preferences";
	public static final String NOD_UPLOAD_SETTINGS_DAILY_UPLOAD_TIME_XPATH = "//*[@id='pageForm:automatedLoadScheduleDaily']//option[text()='%s']";
	public static final String PUBLISH_APPROVERS_TABLE_USERNAME_XPATH = "//span[@id='pageForm:approvalTableContainer']//tbody//tr[td[contains(text(), '%s')]]";
	public static final String PUBLISH_APPROVERS_TABLE_REMOVE_USERNAME = "//span[@id='pageForm:approvalTableContainer']//tbody//tr//td//input[contains(@onclick, '%s')]";
	public static final String SAVE_CONTENT_SET_PREFERENCES = "//*[@id='pageForm:save']";
	public static final String WEEKLY_UPLOAD_RADIO_BUTTON_XPATH = "//label[contains(text(),'Weekly Upload')]/../input[contains(@id,'pageForm:automatedLoadScheduleType')]";
	public static final String NO_UPLOAD_RADIO_BUTTON_XPATH = "//label[contains(text(),'No Upload')]/../input[contains(@id,'pageForm:automatedLoadScheduleType')]";
	public static final String ENABLE_VOLUME_CHOOSING_CHECKBOX_XPATH = "//span[@id='pageForm:nodAutomationSettingsBlock']//input[@id='pageForm:enableVolumesChoosingCheckbox']";
	public static final String VOLUMES_SWITCH_TABLE_XPATH = "//div[@id='volumeSelector']//select[@id='pageForm:availableVolumes']";
	public static final String DISABLED_VOLUMES_SWITCH_TABLE_XPATH = "//div[@id='volumeSelector']//select[@id='pageForm:disabledVolumes']";
	public static final String ALL_VOLUMES_UNDER_VOLUMES_TABLE = "//div[@id='volumeSelector']//select[@id='pageForm:availableVolumes']//option";
	public static final String ALL_VOLUMES_UNDER_DISABLED_VOLUMES_TABLE = "//div[@id='volumeSelector']//select[@id='pageForm:disabledVolumes']//option";
	public static final String GIVEN_VOLUME_UNDER_VOLUMES_TABLE = "//div[@id='volumeSelector']//select[@id='pageForm:availableVolumes']//option[contains(text(),'%s')]";
	public static final String GIVEN_VOLUME_UNDER_DISABLED_VOLUMES_TABLE = "//div[@id='volumeSelector']//select[@id='pageForm:disabledVolumes']//option[contains(text(),'%s')]";
	public static final String SOURCE_HIDDEN_CONTENT_GROUP = "//select[@id='pageForm:sourceAvailableContentSets']/option[text()='%s']";
	public static final String SOURCE_SELECTED_CONTENT_GROUP = "//select[@id='pageForm:sourceSelectedContentSets']/option[text()='%s']";

	@FindBy(how = How.ID, using = "pageForm:addSourceContentSetButton")
	public static WebElement addSourceTargetContentSet;

	@FindBy(how = How.ID, using = "pageForm:removeSourceContentSetButton")
	public static WebElement removeSourceTargetContentSet;

	@FindBy(how = How.XPATH, using = "//a[@class='breadcrumblink'][text()='Home']")
	public static WebElement homeBreadcrumbItem;

	@FindBy(how = How.XPATH, using = "//legend[text()='NOD Source Tag Settings']")
	public static WebElement nodSourceTagSettings;

	public static final String NOD_SOURCE_TAG_SETTINGS = "//legend[text()='NOD Source Tag Settings']";

	@FindBy(how = How.XPATH, using = "//legend[text()='NOD Grid Display Settings']")
	public static WebElement nodGridDisplaySettings;

	@FindBy(how = How.XPATH, using = "//legend[text()='NOD Grid Display Settings']/../table//span[text()='Display Unreported Subscribed Cases:']")
	public static WebElement nodUnreportedFilter;

	public static final String NOD_UNREPORTED_FILTER = "//legend[text()='NOD Grid Display Settings']/../table//span[text()='Display Unreported Subscribed Cases:']";
	
	@FindBy(how = How.ID, using = "pageForm:sourceTagSelection")
	public static WebElement sourceTagSelection;

	@FindBy(how = How.ID, using = "pageForm:sourceTagSelection")
	public static WebElement sourceTagSelected;

	@FindBy(how = How.ID, using = "pageForm:selectedTextQuotedTextDate")
	public static WebElement processingInstructionDate;

	@FindBy(how = How.ID, using = "pageForm:selectedNodSourceTag")
	public static WebElement nodSourceTag;

	@FindBy(how = How.ID, using = "pageForm:automatedLoadScheduleType:1")
	public static WebElement dailyUploadRadioButton;

	@FindBy(how = How.XPATH, using = "//*[@id='pageForm:selectedNodSourceTag']//option[@selected='selected']")
	public static WebElement nodSourceTagSelected;

	@FindBy(how = How.XPATH, using = "//*[@id='pageForm:automatedLoadScheduleDaily']//option[@selected='selected']")
	public static WebElement nodUploadSettingsDailyUploadTime;

	@FindBy(how = How.XPATH, using = "//*[@id='pageForm:automatedLoadScheduleWeeklyTime']//option[@selected='selected']")
	public static WebElement nodUploadSettingsWeeklyUploadTime;

	@FindBy(how = How.ID, using = "pageForm:approvalIdToAdd")
	public static WebElement publishApproversUserIdTextBox;

	@FindBy(how = How.XPATH, using = "//input[@value='Add Approver']")
	public static WebElement addApproverButton;

	@FindBy(how = How.ID, using = "pageForm:sendDataViaAjax")
	public static WebElement saveButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "pageForm:addVolumeButton")
	public static WebElement rightArrowButton;

	@FindBy(how = How.ID, using = "pageForm:removeVolumeButton")
	public static WebElement leftArrowButton;
 }
