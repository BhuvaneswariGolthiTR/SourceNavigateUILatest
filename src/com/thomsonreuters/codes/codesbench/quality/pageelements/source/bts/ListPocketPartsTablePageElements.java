package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ListPocketPartsTablePageElements 
{
	public static final String POCKET_PARTS_TABLE_ID = "//table[@id='tableman']/tbody/tr[?]/td";
	public static final String POCKET_PARTS_TABLE_TIME = "//table[@id='tableman']/tbody/tr[?]/td[3]";
	public static final String GENERIC_POCKET_PART_ID_TABLE_ROW_XPATH = "//table[@id='tableman']/tbody/tr[%s]/td[1]";
	public static final String GENERIC_POCKET_PART_NAME_TABLE_ROW_XPATH = "//table[@id='tableman']/tbody/tr[%s]/td[2]";
	public static final String GENERIC_POCKET_PART_LAST_MODIFIED_ROW_XPATH = "//table[@id='tableman']/tbody/tr[%s]/td[3]";

	
	@FindBy(how = How.LINK_TEXT, using = "Generate") 
	public static WebElement generateTab;
	
	@FindBy(how = How.LINK_TEXT, using = "List")
	public static WebElement listTab;
	
	@FindBy(how = How.XPATH, using= "//table[@id='tableman']/tbody/tr/td[1]")
	public static List<WebElement> pocketPartTablesIdElements;
	
	
	
}
	