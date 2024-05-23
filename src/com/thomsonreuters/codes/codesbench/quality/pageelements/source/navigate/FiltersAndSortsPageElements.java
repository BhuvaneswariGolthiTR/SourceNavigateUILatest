package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FiltersAndSortsPageElements
{
	public static final String FILTER_DROPDOWN_OPTION = "//div/ul/li[contains(@class, 'highlight') and text()='%s']";

	public static final String NOTES_WITH_SORT_ARROW = "//th[@id='yui-dt0-fixedth-notes' and contains(@class,'yui-dt-asc')]//a[@title='Click to sort descending']";
	
	@FindBy(how = How.XPATH, using = "//div[@id='validationFlagViewableAutoComplete']/input[@id='validationFlagViewableFilter']")
    public static WebElement validationFlagFilter;
	
	@FindBy(how = How.XPATH, using = "//div[@id='multipleDuplicateViewableAutoComplete']/input[@id='multipleDuplicateViewableFilter']")
    public static WebElement multipleDuplicateFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='deleteFlagViewableAutoComplete']/input[@id='deleteFlagViewableFilter' and @class='yui-ac-input']")
    public static WebElement deleteFlagFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='contentSetViewableAutoComplete']/input[@id='contentSetViewableFilter']")
    public static WebElement contentSetFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='contentSetViewableAutoComplete']/span[@id='contentSetViewableToggle']")
    public static WebElement contentSetFilterDropdown;
    
	@FindBy(how = How.XPATH, using = "//div[@id='yearAutoComplete']/input[@id='yearFilter']")
    public static WebElement yearFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='sessionAutoComplete']/input[@id='sessionFilter']")
    public static WebElement sessionFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='renditionStatusViewableAutoComplete']/input[@id='renditionStatusViewableFilter']")
    public static WebElement renditionStatusFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='legislationTypeAutoComplete']/input[@id='legislationTypeFilter']")
    public static WebElement legislationTypeFilter;

	@FindBy(how = How.XPATH, using = "//div[@id='internalEnactmentViewableAutoComplete']/input[@id='internalEnactmentViewableFilter']")
    public static WebElement internalEnactmentFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='contentTypeViewableAutoComplete']/input[@id='contentTypeViewableFilter']")
    public static WebElement contentTypeFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='classNameAutoComplete']/input[@id='classNameFilter']")
    public static WebElement classNameFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='classNumFilter']")
    public static WebElement classNumberFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='westIdFilter']")
    public static WebElement westIdFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='docTypeViewableAutoComplete']/input[@id='docTypeViewableFilter']")
    public static WebElement docTypeFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='docNumberFilter']")
    public static WebElement docNumberFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='renditionStageFilter']")
    public static WebElement renditionStageFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='trackingNumberFilter']")
    public static WebElement trackingNumberFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='lssTrackingNumberFilter']")
    public static WebElement lssTrackingNumberFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='effectiveDateFilter']")
    public static WebElement effectiveDateFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='effectiveDateProvisionAutoComplete']/input[@id='effectiveDateProvisionFilter']")
    public static WebElement effectiveDateProvisionFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='dateSuppressAutoComplete']/input[@id='dateSuppressFilter']")
    public static WebElement dateSuppressFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='addAsQCAutoComplete']/input[@id='addAsQCFilter']")
    public static WebElement addAsQcFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='queryDateFilter']")
    public static WebElement queryDateFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='addAsQDAutoComplete']/input[@id='addAsQDFilter']")
    public static WebElement addAsQdFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='sourceVersionDateFilter']")
    public static WebElement renditionVersionDateFilter;
    
	@FindBy(how = How.XPATH, using = "//span[input[@id = 'sourceVersionDateFilter']]/button[@title = 'Show Calendar']")
    public static WebElement renditionVersionDateFilterCalendarDateButton;
    
	@FindBy(how = How.XPATH, using = "//input[@id='sourceLoadDateFilter']")
    public static WebElement renditionLoadDateFilter;
    
	@FindBy(how = How.XPATH, using = "//span[input[@id = 'sourceLoadDateFilter']]/button[@title = 'Show Calendar']")
    public static WebElement renditionLoadDateFilterCalendarButton;
    
	@FindBy(how = How.XPATH, using = "//input[@id='slipLoadDateFilter']")
    public static WebElement slipLoadDateFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='slipLoadUserFilter']")
    public static WebElement slipLoadUserFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='westlawLoadAutoComplete']/input[@id='westlawLoadFilter']")
    public static WebElement westlawLoadFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='correlationIdFilter']")
    public static WebElement correlationIdFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='serialNumberFilter']")
    public static WebElement serialNumberFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='specialtyBillAutoComplete']/input[@id='specialtyBillFilter']")
    public static WebElement specialtyBillFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='difficultyLevelAutoComplete']/input[@id='difficultyLevelFilter']")
    public static WebElement difficultyLevelFilter;
    
	@FindBy(how = How.XPATH, using = "//div[@id='sourceStatus2AutoComplete']/input[@id='sourceStatus2Filter']")
    public static WebElement renditionTrackingStatusFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='renditionTitleFilter']")
    public static WebElement renditionTitleFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='organizationFilter']")
    public static WebElement organisationFilter;
    
	@FindBy(how = How.XPATH, using = "//input[@id='siblingOrderFilter']")
    public static WebElement sectionNumber;
    
	@FindBy(how = How.XPATH, using = "//input[@id='contentSetFilter']")
    public static WebElement lockContentSetFilter;

	@FindBy(how = How.ID, using = "yui-dt0-fixedth-lockViewable")
	public static WebElement lockContentSort;

	@FindBy(how = How.ID, using = "deltaLevelFilter")
	public static WebElement deltaLevelFilter;

	@FindBy(how = How.ID, using = "actionFilter")
	public static WebElement actionFilter;

	@FindBy(how = How.ID, using  = "sectionNumberFilter")
	public static WebElement sectionNumberFilter;

	@FindBy(how = How.ID, using = "siblingOrderFilter")
	public static WebElement siblingOrderFilter;

	@FindBy(how = How.ID, using = "containsOnlineProductTagFilter")
	public static WebElement onlineProductTagFilter;

	@FindBy(how = How.XPATH, using = "//span[@id='containsOnlineProductTagToggle']//button")
	public static WebElement onlineProductTagFilterToggleButton;

	@FindBy(how = How.XPATH, using = "//ul/li[text()='Y']/preceding-sibling::li")
	public static WebElement resetOnlineProductTagFilter;
}
