package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteRecordsPageElements 
{
	public static final String DELETE_RECORDS_MESSAGE_XPATH = "//div[contains(@class, 'ui-widget-header')]/span[text() = 'Delete Record(s)?']/../..//div[contains(@class, 'ui-widget-content')]//p[text() = 'Are you sure you want to delete record(s)?']";
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Ok')]")
	public static WebElement okButton;
}

