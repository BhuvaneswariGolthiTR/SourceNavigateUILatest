package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SubscribedCasesPageElements { 
	
	//Page Title
	public static final String SUBSCRIBED_CASES_PAGE_TITLE = "Subscribed Cases";
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Current view:')]")
	public static WebElement viewLabelXpath;
	
	@FindBy(how = How.ID, using = "targetForm:nodeUuid")
	public static WebElement targetNodeUUidTextbox;
	
	@FindBy(how = How.XPATH, using = "//a[text()='Subscribed Cases']")
	public static WebElement subscribedCasesButton;

	@FindBy(how = How.ID, using = "nodClassifyPageContent")
	public static WebElement pageContentXpath;

	@FindBy(how = How.ID, using = "wait") //put in base page (make one) base page elements
	public static WebElement progressBar;
	
    public static final String PROPERTIES_PAGE_TITLE = "Subscribed Case Properties";
    
    @FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-message']//div[contains(@class,'yui-dt-loading') and text()='Loading...']")
	public static WebElement loadingDiv;

    @FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-message' and @style='display: none;']")
	public static WebElement loadingTBody;
    
    //Generic XPath segments for various case fields 
    @FindBy(how = How.XPATH, using = "//td[contains(@headers,'caseSerialNumber')]")
	public static WebElement caseSerialNumberField;

    @FindBy(how = How.XPATH, using = "//td[contains(@headers, 'signedOutBy')]")
	public static WebElement caseSignedOutByField;

    @FindBy(how = How.XPATH, using = "//td[contains(@headers, 'signedOutBy')]")
	public static WebElement caseTitleField;

    public static final String HIGHLIGHTED_CASE_IN_A_TREE_WITH_TEXT_GIVEN = "//table[contains(@class,'highlight1')]//td[contains(text(),'%s')]";

    //There are two instances of the viewManagmentButton, but the first one is hidden.
	@FindBy(how = How.XPATH, using = "(//img[@id='viewManagementButton'])[2]")
	public static WebElement viewManagementButton;
    
    //Cases of interest
    @FindBy(how = How.XPATH, using = "//tr[contains(@id, 'yui-rec')][1]")
	public static WebElement firstSubscribedCaseOnPage;
    
    @FindBy(how = How.XPATH, using = "//tr[contains(@id, 'yui-rec')][2]")
	public static WebElement secondSubscribedCaseOnPage;

    @FindBy(how = How.XPATH, using = "//tr[td[contains(@class, 'RU')]//div[text()='R']]")
	public static WebElement firstReportedCaseOnPage;

    @FindBy(how = How.XPATH, using = "//tr[td[contains(@class, 'RU')]/div[text()='U']]")
	public static WebElement firstUnreportedCaseOnPage;

    public static final String CASE_IN_A_ROW_WITH_NUMBER_GIVEN = "//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@class,'caseSerialNumber')]//a";

    public static final String CASE_WITH_A_NUMBER_GIVEN = "//td[contains(@class,'caseSerialNumber')]//a[text()='%s']";
    
    @FindBy(how = How.XPATH, using = "//td[contains(@class, 'RU')]/div[text()='U']/../../td[contains(@class,'caseSerialNumber ')]//a")
	public static WebElement unreportedCaseSerialNumber;

    @FindBy(how = How.XPATH, using = "//a[text()='Add']/ancestor::tr/td[contains(@headers,'caseSerialNumber')]//a")
	public static WebElement caseWithoutNoteSerial;

    public static final String NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER = "//a[text()='%s']/ancestor::tr/td[contains(@headers,'caseNote')]//a/img";
    
    public static String ADD_NOTE_LINK_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER = "//a[text()='%s']/ancestor::tr/td[contains(@headers,'caseNote')]//a[text()='Add']";

    //Search fields
    @FindBy(how = How.XPATH, using = "//span[@class='yui-dt-label']/input[@id='caseSerialNumberFilter']")
   	public static WebElement serialNumberSearchField;

    @FindBy(how = How.XPATH, using = "//span[@class='yui-dt-label']/input[@id='caseSerialNumberFilter']")
   	public static WebElement signedOutBySearchField;
    
    //Page buttons
    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt0-paginator1']/a[@class='yui-pg-next']")
   	public static WebElement nextPageButton;

    @FindBy(how = How.XPATH, using = "//div[contains(@id, 'paginator1')]//a[text()='Clear All Filters']")
   	public static WebElement clearFiltersButton;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt0-paginator1']//em[text()='Refresh']")
   	public static WebElement refreshButton;
   
    //Properties Window
    @FindBy(how = How.XPATH, using = "//a[contains(@onclick, 'SerialNumber')]")
   	public static WebElement propertiesWindowSerialNumber;

    @FindBy(how = How.XPATH, using = "//input[@value='Cancel']")
   	public static WebElement propertiesWindowCancelNumber;
    
    @FindBy(how = How.XPATH, using = "//div//span[contains(text(), 'Current view: (none)')]")
   	public static WebElement currentView;
    
    //TODO
    @FindBy(how = How.ID, using = "RUFilter")
   	public static WebElement reportedUreportedFilter;

    public static final String CLICKABLED_SERIAL_NUMBER = "//tr//a[contains(., '%s')]";

   	public static final String CASE_WITH_NUMBER_SIGN_OUT_BY = "//td[contains(@class,'caseSerialNumber')]//a[text()='%s']/../../..//td[contains(@class, 'signedOutBy')]//div"; 

   	public static final String CASE_WITH_NUMBER ="//td[contains(@class,'caseSerialNumber')]//a[text()='%s']"; 
    
    //CM
    @FindBy(how = How.XPATH, using = "//div[@id='mycontextmenu']//a[text()='Properties']")
   	public static WebElement contextMenuProperties;

	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']//button[@class='calendarButton']")
	public static WebElement loadedDateCalendarButton;
}