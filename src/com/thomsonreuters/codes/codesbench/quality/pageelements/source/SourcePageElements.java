package com.thomsonreuters.codes.codesbench.quality.pageelements.source;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourcePageElements
{
	public static final String SOURCE_PAGE_TITLE = "Pending Rendition Navigate";
	
	public static final String FIND_DOCUMENT_BUTTON = "//*[@id='findDocumentButton']";
	
	public static final String DOCUMENT_UUID_INPUT = "//*[@id='pageForm:docId']";
	
	public static final String FIND_BY_UUID_BUTTON = "//*[@id='pageForm:docIdSearch']";
	
	public static final String FIRST_RENDITION = "//tbody[@class='yui-dt-data']/tr/td[3]";
	
	public static final String SELECTED_RENDITION = "//tr[contains(@class,'yui-dt-selected')]/td[3]";

	public static final String PLEASE_WAIT = "//*[@id='wait']";
	
	// CM
	public static final String EDIT_CM_SUBMENU = "//li/a[text()='Edit']";
	
	public static final String EDIT_RENDITION_WITH_DS_CM_ITEM = "//li/a[text()='Rendition Dynamic Scrolling']";
		
	@FindBy(how = How.XPATH, using = "div[@id='top_content']/div/span")
	public static List<WebElement> topContent;
	

}
