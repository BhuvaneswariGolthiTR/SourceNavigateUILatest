package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesContextMenuElements 
{
	private static final String HEADNOTES_TREE_CONTEXT_MENU = "//div[@id='mytreecontextmenu']";

	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Insert Blueline']")
	public static WebElement insertBlueline;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Update Metadata']")
	public static WebElement updateMetadata;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='View Content']")
	public static WebElement viewContent;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='View Content Raw Xml']")
	public static WebElement viewContentRawXml;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Edit Content']")
	public static WebElement editContent;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Edit Content Raw Xml']")
	public static WebElement editContentRawXml;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Find in Hierarchy']")
	public static WebElement findInHierarchy;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='All Blueline Analysis View']")
	public static WebElement allBluelineAnalysisView;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Blueline Analysis View']")
	public static WebElement bluelineAnalysisView;
	
	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Edit Blueline']")
	public static WebElement editBlueline;

	@FindBy(how = How.XPATH, using = HEADNOTES_TREE_CONTEXT_MENU + "//a[text()='Delete Blueline']")
	public static WebElement deleteBlueline;
}
