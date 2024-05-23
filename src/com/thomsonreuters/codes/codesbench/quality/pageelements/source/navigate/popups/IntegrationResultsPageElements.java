package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IntegrationResultsPageElements
{
	public static final String INTEGRATION_RESULTS_PAGE_TITLE = "Integration Results";
	public static final String FIRST_SECTION_NUMBER="//div[contains(@class,'ag-cell ag-cel')][@col-id='sectionNumber']";
	public static final String SECOND_SECTION_NUMBER="(//div[@row-id='1']//div[@col-id='sectionNumber'])[2]";
	public static final String CLOSE_INTEGRATION_RESULT_PAGE="//button[@class='close-btn']";
	public static final String INTEGRATION_RESULT_PART_STATUS="(//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-normal-height ag-cell-value'][@col-id='intResTextMergeStatus'])[1]";
	public static final String PART_STATUS_SCROLL_VIEW="(//div[@class='ag-body-horizontal-scroll-viewport'])[7]";

	@FindBy(how = How.ID, using = "closeButton")
	public static WebElement closeButton;
}
