package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ListLegislativeServiceTablesPageElements 
{
	public static final String LIST_LEGISLATIVE_SERVICE_TABLE_CANCEL_VIEW = "//input[@name='pageForm:cancelViewButton']"; 
	public static final String LEGISLATIVE_SERVICE_TABLE_PAGE_EDIT = "//span[@title='Edit']";
	public static final String LEGISLATIVE_SERVICE_TABLE_PAGE_DELETE ="//span[@title='Delete']";
	public static final String DOWNLOAD_BUTTON_GENERATE_LTS_LINK = "http://uat.magellan3.int.westgroup.com:9203/btsWeb/service/generateLTS/pdf/";
	public static final String GENERIC_TABLE_ROW = "//table[@id='tableman']//tr[td[text()='%s']]//li%s";
	
	@FindBy(how = How.XPATH, using = "//button//span[text()='Ok']")
	public static WebElement okButton;
	
	@FindBy(how = How.XPATH, using = "//a[text()='List']")
	public static WebElement listLegislativeServiceListTab;
	
	@FindBy(how = How.XPATH, using = "//a[@class='fg-button ui-button ui-state-default' and text()='?']")
	public static WebElement legislativeServiceTablePageButton;
	
	@FindBy(how = How.XPATH, using  = "//span[@title='Download']")
	public static WebElement legislativeServiceTableDownloadButton;
	
	@FindBy(how = How.XPATH, using = "//span[@title='Edit']")
	public static WebElement legislativeServiceTableEditButton;
	
	@FindBy(how = How.XPATH, using = "//span[@title='Delete']")
	public static WebElement legislativeServiceTableDeleteButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='tableman']//tbody//tr")
	public static List<WebElement> tableRows;
	
	@FindBy(how = How.XPATH, using = "//a[text()='List']")
	public static WebElement pocketPartsTableListTab;
	
	@FindBy(how = How.XPATH, using = "//textarea[@name='pageForm:editingarea']")
	public static WebElement legislativeTablesEditBox;
}
