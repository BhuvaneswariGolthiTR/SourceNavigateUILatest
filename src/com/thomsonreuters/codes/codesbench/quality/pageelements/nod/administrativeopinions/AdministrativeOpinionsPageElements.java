package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdministrativeOpinionsPageElements 
{
	public static final String ADMINISTRATIVE_OPINIONS_PAGE_TITLE = "Administrative Opinions";
	
	@FindBy(how = How.ID, using = "buttonCommandForm:insertOpinion")
	public static WebElement insertOpinionButton;

	public static String opinionWithGivenValuesInGridXpath = "//div[@id='gridContainer']//tr["
			+ "td[contains(@class,'Type')]//div[text()='%s'] and " 
			+ "td[contains(@class,'OpinionNumber')]//a[text()='%s'] and " 
			+ "td[contains(@class,'OpinionDate')]//div[text()='%s'] and "  
			+ "td[contains(@class,'Editor')]//div[text()='%s'] and " 
			+ "td[contains(@class,'Text')]//div[text()='%s'] and " 
			+ "td[contains(@class,'OpinionCitation')]//div[text()='%s'] and " 
			+ "td[contains(@class,'WLNumber')]//div[text()='%s']]";
	
	public static String clickableOpinionNumber = "//tr//td[contains(@class,'OpinionNumber')]//a[text()='%s']";

	public static String opinionTypeCell = "//tr[td[contains(@class,'OpinionNumber')]//a[text()='%s']]//td[contains(@class,'Type')]";
}
