package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdministrativeOpinionsPageElementsAngular
{
	public static final String PAGE_TITLE = "Administrative Opinions";
	public static final String HEADING = "Administrative Opinions";
	public static final String URL_MODIFIER = "adminOpinions";
	public static final String PAGE_TAG = "//body/app-root/app-administrative-opinions";
	public static final String CLICKABLE_OPINION_NUMBER = "//div[@col-id = 'opinionName']/a[text() = '%s']";

	@FindBy(how = How.XPATH, using = "//div[@class = 'insert-button']/button")
	public static WebElement insertOpinionButton;

}
