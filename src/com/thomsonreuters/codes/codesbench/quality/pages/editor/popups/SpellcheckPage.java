package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SpellcheckPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class SpellcheckPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public SpellcheckPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, SpellcheckPageElements.class);
    }

	public String grabAllParatextsContent()
	{
		return driver.findElements(By.xpath("//paratext"))
				// because document.state never returns 'complete' in that iframe
				.stream().map(WebElement::getText).collect(Collectors.joining());
	}

	public void clickReplace()
	{
		click(SpellcheckPageElements.REPLACE_BUTTON);
		editorPage().acceptAlert();
	}

	public void clickCommitChangesAndClose()
	{
		click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
	}

	public void clickDiscardChangesAndClose() {click(SpellcheckPageElements.DISCARD_CHANGES_AND_CLOSE_BUTTON);}
}
