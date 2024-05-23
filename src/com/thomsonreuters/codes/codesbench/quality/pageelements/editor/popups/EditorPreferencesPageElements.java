package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditorPreferencesPageElements 
{
	public static final String PAGE_TITLE = "Editor Preferences";

	@FindBy(how = How.XPATH, using = "//select[@id='defaultSourceTag']")
	public static WebElement defaultSourceTag;
	
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Automatic Source Tag Update')]/..//label[contains(text(),'Yes')]")
	public static WebElement automaticSourceTagUpdateYes;
	
	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Automatic Source Tag Update')]/..//label[contains(text(),'No')]")
	public static WebElement automaticSourceTagUpdateNo;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Always retain source document source tags')]/..//label[contains(text(),'Yes')]")
	public static WebElement alwaysRetainSourceDocumentSourceTagsYes;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Always retain source document source tags')]/..//label[contains(text(),'No')]")
	public static WebElement alwaysRetainSourceDocumentSourceTagsNo;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Automatic Credit Generation')]/..//label[contains(text(),'Yes')]")
	public static WebElement automaticCreditGenerationYes;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Automatic Credit Generation')]/..//label[contains(text(),'No')]")
	public static WebElement automaticCreditGenerationNo;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Yes')]//input[@name='showSubsectionLabelDesignators']")
	public static WebElement showSubsectionLabelDesignatorsYes;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'No')]//input[@name='showSubsectionLabelDesignators']")
	public static WebElement showSubsectionLabelDesignatorsNo;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Yes')]//input[@name='fullEnglishLabels']")
	public static WebElement fullEnglishLabelsYes;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'No')]//input[@name='fullEnglishLabels']")
	public static WebElement fullEnglishLabelsNo;

	public static final String DEFAULT_SOURCE_TAG_SELECTOR = "//select[@id='defaultSourceTag']";
	
}
