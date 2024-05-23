package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class XmlExtractPageElements
{
	public static final String XML_EXTRACT_PAGE_TITLE = "Xml Extract";
	public static final String FILE_OUTPUT_RADIO_BUTTON = "//table[@class='formInput']/tbody/tr/td/input[@type='radio' and @value='%s']";

	@FindBy(how = How.ID, using = "pageForm:fileName")
	public static WebElement fileNameInput;

	@FindBy(how = How.ID, using = "pageForm:removeAddedMaterial")
	public static WebElement removeAddedMaterialCheckbox;

	@FindBy(how = How.ID, using = "pageForm:removeDeletedMaterial")
	public static WebElement removeDeletedMaterialCheckbox;

	@FindBy(how = How.ID, using = "pageForm:submitButton")
	public static WebElement okButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
}
