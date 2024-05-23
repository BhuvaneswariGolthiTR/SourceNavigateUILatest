package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyPubNavigatePageElements 
{
	public static final String HIERARCHY_MENU_PUB_NAVIGATE_PAGE_TITLE = "Hierarchy Edit (PUB)";
	
	@FindBy(how = How.XPATH, using = "//div[@id='mytreecontextmenu']//a[@href='#navTreeContextMenupublishingWorkflows']")
	public static WebElement publishingWorkflowsXpath;
	
	@FindBy(how = How.XPATH, using = "//a[text()='Republish by Hierarchy']")
	public static WebElement republishByHierarcyXpath;

	public static final String POPNAME_WITH_STARTING_LETTER_XPATH = "//td[@class='ygtvcell ygtvhtml ygtvcontent' and contains(text(),'%s')]";

	public static final String POPNAME_WITH_STARTING_LETTER_EXPANDER_XPATH = "//td[@class='ygtvcell ygtvhtml ygtvcontent' and contains(text(),'%s')]/..//a[@class='ygtvspacer']";
}
