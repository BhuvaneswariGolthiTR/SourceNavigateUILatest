package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesTreePageElements 
{
	@FindBy(how = How.XPATH, using = "//table[contains(@class,'highlight1')]")
	public static WebElement navTreeSelectedNodeXpath;

	public static final String HEADNOTE_TREE_NODE_WITH_VALUE_GIVEN = "//td[contains(text(),'%s')]";
}
