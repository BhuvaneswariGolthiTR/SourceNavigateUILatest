package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertOpinionPageElements 
{
	public static final String INSERT_OPINION_PAGE_TITLE = "Insert Opinion";

	@FindBy(how = How.ID, using = "pageForm:AdminOpinionNumber")
	public static WebElement opinionNumberField;

	public static String opinionTypeDropdown = "//select[@id='pageForm:OpinionTypeMenu']//option[@value='%s']";

	@FindBy(how = How.ID, using = "pageForm:opinionDateString")
	public static WebElement opinionDateField;

	@FindBy(how = How.ID, using = "pageForm:OpinionCitationText")
	public static WebElement opinionCitationField;

	@FindBy(how = How.ID, using = "pageForm:WlNumber")
	public static WebElement opinionWlNumberField;

	@FindBy(how = How.ID, using = "pageForm:insertAndEditOpinion")
	public static WebElement saveAndEditClassifyButton;

	@FindBy(how = How.ID, using = "pageForm:insertOpinion")
	public static WebElement insertButton;
	
	@FindBy(how = How.ID, using = "pageForm:OpinionText")
	public static WebElement opinionTextField;
}
