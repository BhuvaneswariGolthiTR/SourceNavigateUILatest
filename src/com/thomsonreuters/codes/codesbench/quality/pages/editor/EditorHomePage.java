package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.sourceRenditionChunkCheckbox;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.sourceRenditionEditButton;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.sourceRenditionUuidTextbox;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.targetNodeChunkCheckbox;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.targetNodeEditButton;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements.targetNodeUUidTextbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorHomePageElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditorHomePage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditorHomePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorHomePageElements.class);
	}

	public enum Applications
	{
		TARGET,
		SOURCE
	}

	public void goToEditorHomePage(String contentSetNumber)
	{
		openPageWithUrl(String.format(urls().dynamicScrollingHomePageUrl, environmentTag, contentSetNumber),
				EditorHomePageElements.PAGE_TITLE);
	}

	public void editDocumentWithDynamicScrolling(Applications application, String uuid)
	{
		switch (application)
		{
			case SOURCE:
				editSourceRenditionWithDynamicScrolling(uuid);
				break;
			case TARGET:
				editTargetNodeWithDynamicScrolling(uuid);
				break;
			default:
				throw new RuntimeException("Wrong argument for editDocumentWithDynamicScrolling method.");
		}
	}

	private void editTargetNodeWithDynamicScrolling(String uuid)
	{
		targetNodeUUidTextbox.sendKeys(uuid);
		targetNodeChunkCheckbox.click();
		targetNodeEditButton.click();
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE, true);
	}

	private void editSourceRenditionWithDynamicScrolling(String uuid)
	{
		sourceRenditionUuidTextbox.sendKeys(uuid);
		sourceRenditionChunkCheckbox.click();
		sourceRenditionEditButton.click();
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE, true);
	}
}
