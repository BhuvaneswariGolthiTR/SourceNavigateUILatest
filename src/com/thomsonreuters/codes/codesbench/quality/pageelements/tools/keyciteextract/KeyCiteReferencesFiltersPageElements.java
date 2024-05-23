package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.keyciteextract;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class KeyCiteReferencesFiltersPageElements
{
	@FindBy(how = How.ID, using = "pageForm:pageForm:contentSetLegacyId")
	public static WebElement contentSetLegacyIdSelect;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:0")
	public static WebElement keyRulesCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:1")
	public static WebElement americanLawReportsAnnotationsCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:2")
	public static WebElement treatiesDocumentsCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:3")
	public static WebElement legalEncyclopediaDocumentsCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:4")
	public static WebElement otherSecondarySourcesCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:5")
	public static WebElement lawReviewCheckBox;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:docTypeSelection:6")
	public static WebElement ipFilingsCheckBox;

	@FindBy(how = How.ID, using = "pageForm:pageForm:defaultTitleListCheckBox")
	public static WebElement defaultAnalyticalTitlesCheckBox;
	
}
