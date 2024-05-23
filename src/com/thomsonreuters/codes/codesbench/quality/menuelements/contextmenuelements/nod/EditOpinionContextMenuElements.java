package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditOpinionContextMenuElements 
{
	private static final String EDIT_OPINION_TREE_CONTEXT_MENU = "//div[@id='mytreecontextmenu']";
	
	@FindBy(how = How.XPATH, using = EDIT_OPINION_TREE_CONTEXT_MENU + "//a[text()='Update Metadata']")
	public static WebElement updateMetadata;
	
	@FindBy(how = How.XPATH, using = EDIT_OPINION_TREE_CONTEXT_MENU + "//a[text()='View Content']")
	public static WebElement viewContent;
	
	@FindBy(how = How.XPATH, using = EDIT_OPINION_TREE_CONTEXT_MENU + "//a[text()='Edit Content']")
	public static WebElement editContent;
}
