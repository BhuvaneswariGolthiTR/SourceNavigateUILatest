package com.thomsonreuters.codes.codesbench.quality.pageelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CourtsPageElements 
{
	//TODO
	//add xpaths for "Courts" page
	public static final String COURTS_PAGE_TITLE = "Courts";
	
	@FindBy(how = How.XPATH, using = "//div[@id='breadcrumbArea'][contains(., 'Courts')]")
	public static WebElement courtsBreadcrumbItem;

	@FindBy(how = How.XPATH, using = "//th[contains(@id, 'Court')]//a[text()='Court']")
	public static WebElement courtSorter;
}
