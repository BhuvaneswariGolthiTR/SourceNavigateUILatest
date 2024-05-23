package com.thomsonreuters.codes.codesbench.quality.pageelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SharedDeltaReportPageElements
{
	public static final String SHARED_DELTA_REPORT_PAGE_TITLE = "Shared Delta Report";
	public static final String NON_SELECTED_MATERIALS = "//select[@id='pageForm:nonSelectedMaterialIdList']//option[text()='%s']";
	public static final String LIST_OF_SELECTED_MATERIALS_OPTIONS = "//select[@id='pageForm:selectedMaterialIdList']/option";
	public static final String NO_DELTAS_ERROR_MESSAGE_SPAN = "//span[@class='error' and text()=\"Selected documents don't have deltas\"]";

	@FindBy(how = How.ID,using = "pageForm:sourceType")
	public static WebElement contentSet;

	@FindBy(how = How.ID,using = "pageForm:addOneVolsButton")
	public static WebElement addVolsButton;

	@FindBy(how = How.ID,using = "pageForm:allColumns")
	public static WebElement allColumnsCheckBox;

	@FindBy(how = How.ID,using = "pageForm:submit")
	public static WebElement submitButton;

	@FindBy(how = How.ID, using = "pageForm:reportName")
	public static WebElement reportNameField;
}
