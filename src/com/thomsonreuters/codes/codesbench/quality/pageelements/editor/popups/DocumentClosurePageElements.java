package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DocumentClosurePageElements
{
	public static final String PAGE_TITLE = "Document Closure";
	public static final String DOCUMENT_NAME = "//p[@id='documentName']";
	
	@FindBy(how = How.ID, using = "b_discard")
	public static WebElement discardButton;
	
	@FindBy(how = How.ID, using = "rb_quickLoad")
	public static WebElement quickloadRadioButton;

	@FindBy(how = How.ID, using = "rb_canadaLoad")
	public static WebElement canadaRadioButton;

	@FindBy(how = How.ID, using = "rb_fullVols")
	public static WebElement fullVolsRadioButton;

	@FindBy(how = How.ID, using = "rb_fullVolsCompare")
	public static WebElement fullVolsCompareRadioButton;

	public static final String FULL_VOLS_COMPARE_RADIO_BUTTON = "//*[@id='rb_fullVolsCompare']";

	@FindBy(how = How.ID, using = "rb_fullVolsRecomp")
	public static WebElement fullVolsRecompRadioButton;

	public static final String FULL_VOLS_RECOMP_RADIO_BUTTON = "//*[@id='rb_fullVolsRecomp']";

	@FindBy(how = How.ID, using = "b_checkin")
	public static WebElement checkInButton;
	
	@FindBy(how = How.ID, using = "b_C2012LawTracking")
	public static WebElement c2012LawTrackingButton;

	@FindBy(how = How.ID, using = "effectiveDate")
	public static WebElement effectiveDate;

	@FindBy(how = How.ID, using = "f_trigger_c")
	public static WebElement assignEffectiveDateCalendarButton;

	@FindBy(how = How.XPATH, using = "//font[@color='#ff0000']")
	public static WebElement effectiveDatetodaysDate;

	public static final String EFFECTIVE_DATE = "//input[@name='effectiveDate']";
	public static final String QUICK_LOAD_RADIO = "//input[@id='rb_quickLoad']";
	public static final String CHECK_IN_BUTTON = "//button[@id='b_checkin']";
	public static final String BASELINE_NOTES_FIELD = "//input[@name='baselineNotes']";
	public static final String FULL_VOLS_RADIO_BUTTON = "//*[@id='rb_fullVols']";
	public static final String LAW_TRACKING_TEXT_AREA = "//input[@name='lawTracking']";
	public static final String WESTLAW_DATE_SUPPRESS_ON = "//input[@value='ON']";
	public static final String WESTLAW_DATE_SUPPRESS_OFF = "//input[@value='OFF']";
}
