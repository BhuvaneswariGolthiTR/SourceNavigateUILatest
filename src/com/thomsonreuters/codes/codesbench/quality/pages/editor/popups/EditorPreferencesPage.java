package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditorPreferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class EditorPreferencesPage  extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditorPreferencesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorPreferencesPageElements.class);
	}
	
	public void selectSessionDefaultTextSourceTagDropdownOption(String optionToSelect)
    {
		/*Select select = new Select(EditorPreferencesPageElements.defaultSourceTag);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
		    if (option.getText().equals(optionToSelect)) {
			option.click();
			break;
		    }
		}*/
		selectDropdownOptionUsingJavascript("defaultSourceTag",optionToSelect);
		waitForPageLoaded();
    }
	
	public void clickAutomaticSourceTagUpdateYesRadioButton()
	{
		click(EditorPreferencesPageElements.automaticSourceTagUpdateYes);
	}
	
	public void clickAutomaticSourceTagUpdateNoRadioButton()
	{
		click(EditorPreferencesPageElements.automaticSourceTagUpdateNo);
	}

	public void clickAlwaysRetainSourceDocumentSourceTagsYesRadioButton()
	{
		click(EditorPreferencesPageElements.alwaysRetainSourceDocumentSourceTagsYes);
	}

	public void clickAlwaysRetainSourceDocumentSourceTagsNoRadioButton()
	{
		click(EditorPreferencesPageElements.alwaysRetainSourceDocumentSourceTagsNo);
	}

	public void clickAutomaticCreditGenerationYesRadioButton()
	{
		click(EditorPreferencesPageElements.automaticCreditGenerationYes);
	}

	public void clickAutomaticCreditGenerationNoRadioButton()
	{
		click(EditorPreferencesPageElements.automaticCreditGenerationNo);
	}
	
	public void clickShowSubsectionLabelDesignatorsYesRadioButton()
	{
		click(EditorPreferencesPageElements.showSubsectionLabelDesignatorsYes);
	}
	
	public void clickShowSubsectionLabelDesignatorsNoRadioButton()
	{
		click(EditorPreferencesPageElements.showSubsectionLabelDesignatorsNo);
	}
	
	public void clickSaveButton()
	{
		click(CommonPageElements.SAVE_BUTTON);
	}
	
	public void clickFullEnglishLabelsYesRadioButton()
	{
		click(EditorPreferencesPageElements.fullEnglishLabelsYes);
	}
	
	public void clickFullEnglishLabelsNoRadioButton()
	{
		click(EditorPreferencesPageElements.fullEnglishLabelsNo);
	}
	
    public String grabDefaultSourceTag()
    {
		editorToolbarPage().clickConfigureEditorSessionPreferences();
		String defaultSourceTag = getElement(EditorPreferencesPageElements.DEFAULT_SOURCE_TAG_SELECTOR)
			.getAttribute("value");
		click(CommonPageElements.CANCEL_BUTTON);
		editorPage().switchToEditor();
		return defaultSourceTag;
    }
}