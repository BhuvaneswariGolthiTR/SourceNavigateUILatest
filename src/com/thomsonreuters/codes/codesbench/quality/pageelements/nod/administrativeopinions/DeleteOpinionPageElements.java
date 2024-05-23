package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteOpinionPageElements 
{
	public static final String DELETE_OPINION_PAGE_TITLE = "Delete Opinion";
	
	@FindBy(how = How.ID, using = "pageForm:delete")
	public static WebElement deleteButton;
	
	public static String opinionWithGivenValuesInGridXpath = "//table[@id='pageForm:AdminOpinion']//tbody["
			+ "tr/td/span[@id='pageForm:OpinionTypeMenu' and contains(text(),'%s')] and " 
			+ "tr/td/span[@id='pageForm:AdminOpinionNumber' and contains(text(),'%s')] and " 
			+ "tr/td/span[@id='pageForm:opinionDateString' and contains(text(),'%s')] and "  
			+ "tr/td/span[@id='pageForm:EditorName' and contains(text(),'%s')] and " 
			+ "tr/td/span[@id='pageForm:OpinionText' and contains(text(),'%s')] and " 
			+ "tr/td/span[@id='pageForm:OpinionCitationText' and contains(text(),'%s')] and " 
			+ "tr/td/span[@id='pageForm:WlNumber' and contains(text(),'%s')]]";
}
