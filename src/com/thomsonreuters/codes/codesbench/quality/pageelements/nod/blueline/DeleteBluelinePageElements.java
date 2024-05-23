package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteBluelinePageElements 
{
	public static final String DELETE_BLUELINE_PAGE_TITLE = "Delete Blueline"; 
	
	@FindBy(how = How.ID, using = "pageForm:deleteButton")
	public static WebElement okButton;
}
