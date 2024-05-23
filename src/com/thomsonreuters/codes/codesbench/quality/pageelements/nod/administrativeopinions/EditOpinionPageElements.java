package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditOpinionPageElements 
{
	public static final String EDIT_OPINION_PAGE_TITLE = "Edit Opinion";

	@FindBy(how = How.ID, using = "pageForm:AdminOpinionNumber")
	public static WebElement opinionNumberField;

	@FindBy(how = How.ID, using = "pageForm:OpinionTypeMenu")
	public static WebElement opinionTypeDropdown;
	
	@FindBy(how = How.ID, using = "pageForm:opinionDateString")
	public static WebElement opinionDateField;

	@FindBy(how = How.ID, using = "pageForm:WlNumber")
	public static WebElement opinionWlNumberField;
	
	@FindBy(how = How.ID, using = "pageForm:updateOpinion")
	public static WebElement updateButton;

	@FindBy(how = How.ID, using = "pageForm:OpinionText")
	public static WebElement opinionTextField;

	@FindBy(how = How.ID, using = "pageForm:OpinionCitationText")
	public static WebElement citationField;

	@FindBy(how = How.XPATH, using = "//input[@value='Keyword Find']")
	public static WebElement keywordFindButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;

	public static String highlightedNodeInTreeWithGivenText = "//table[contains(@class,'highlight1')]//td[contains(text(),'%s')]";
}
