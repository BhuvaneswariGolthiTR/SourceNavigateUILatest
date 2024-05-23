package com.thomsonreuters.codes.codesbench.quality.pageelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FindPageElements 
{
	public static final String FIND_PAGE_TITLE = "Find";

	@FindBy(how = How.ID, using = "pageForm:searchButton")
	public static WebElement findSearchButton;

	@FindBy(how = How.XPATH, using = "//tr[td[a]][1]//a")
	public static WebElement findSectionResult;
	
	@FindBy(how = How.ID, using = "pageForm:kw1")
	public static WebElement firstKeywordDropdown;

	@FindBy(how = How.ID, using = "pageForm:kw2")
	public static WebElement secondKeywordDropdown;

	@FindBy(how = How.ID, using = "pageForm:kw3")
	public static WebElement thirdKeywordDropdown;
	
	@FindBy(how = How.ID, using = "pageForm:v1")
	public static WebElement firstValueField;

	@FindBy(how = How.ID, using = "pageForm:v2")
	public static WebElement secondValueField;

	@FindBy(how = How.ID, using = "pageForm:v3")
	public static WebElement thirdValueField;
	
	@FindBy(how = How.ID, using = "//tbody//tbody//a")
	public static WebElement firstFindResult;
	
	public static final String GENERIC_FIND_RESULT_XPATH = "//tr[%s]//a";
	
	@FindBy(how = How.XPATH, using = "//a[contains(@onclick,'setNode')]")
	public static WebElement firstFindResultNodeUuid;
	
	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'yui-dt-')]/td[a or not(*)]")
	public static WebElement quickSearchTableContents;
}
