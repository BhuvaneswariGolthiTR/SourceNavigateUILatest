package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FindRecordPageElements 
{
	public static final String FIND_RECORD_PAGE_TITLE = "Find Record";
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Find Now')]")
	public static WebElement findNowButton;

	@FindBy(how = How.ID, using = "findDocNum")
	public static WebElement documentNumber;

	@FindBy(how = How.ID, using = "findDocType")
	public static WebElement documentType;

	@FindBy(how = How.ID, using = "findLegislativeYear")
	public static WebElement legislativeYear;

	@FindBy(how = How.LINK_TEXT, using = "Fields")
	public static WebElement fieldsTab;

	@FindBy(how = How.LINK_TEXT, using = "Bill Id")
	public static WebElement billIdTab;

}
