package com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserContentSetsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ContentSetsPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public ContentSetsPage(WebDriver driver)
	{
		super(driver);
	    this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
	    PageFactory.initElements(driver, UserContentSetsPageElements.class);
	}

	public boolean doOnlyCarswellContentSetsAppear()
    {
    	List<WebElement> contentSetsOnLeft = getElements(UserContentSetsPageElements.HIERARCHY_HIDDEN_CONTENT_GROUP_XPATH);
    	List<WebElement> contentSetsOnRight = getElements(UserContentSetsPageElements.HIERARCHY_CURRENT_CONTENT_GROUP_XPATH);
    	List<String> contentSets = new ArrayList<String>();
    	
    	contentSetsOnLeft.stream().forEach(contentSet -> contentSets.add(contentSet.getText()));
    	contentSetsOnRight.stream().forEach(contentSet -> contentSets.add(contentSet.getText()));
    	
    	return contentSets.stream().allMatch(contentSet -> contentSet.contains("Canada"));
    }
	
	public void setDefaultTargetContentSet(String contentSet)
	{
		selectDropdownOption(UserContentSetsPageElements.DEFUALT_TARGET_CONTENT_SET_XPATH, contentSet);
		sendEnterToElement(UserContentSetsPageElements.saveButton);
		AutoITUtils.verifyAlertTextAndAccept(true, "Your preferences have been saved");
		waitForGridRefresh();
	}
    
    public boolean doesHierarchyContentSetsNotContainGivenContentSetList(List<String> contentSetList)
    {
    	List<WebElement> contentOnLeft = getElements(UserContentSetsPageElements.HIERARCHY_HIDDEN_CONTENT_GROUP_XPATH);
    	List<WebElement> contentOnRight = getElements(UserContentSetsPageElements.HIERARCHY_TARGET_CONTENT_GROUP);
    	List<String> contentSets = new ArrayList<String>();
    	List<String> contentSetsUnderHiddenTable = getElements(UserContentSetsPageElements.HIERARCHY_HIDDEN_CONTENT_GROUP_XPATH).stream().map(WebElement::getText).collect(Collectors.toList());
		List<String> contentSetsUnderCurrentTable = getElements(UserContentSetsPageElements.HIERARCHY_TARGET_CONTENT_GROUP).stream().map(WebElement::getText).collect(Collectors.toList());

		contentSets.addAll(contentSetsUnderHiddenTable);
		contentSets.addAll(contentSetsUnderCurrentTable);

    	for (String contentSet : contentSetList)
    	{
			if(contentSets.contains(contentSet))
			{
				return false;
			}
		}
    	return true;
    }

	public void addSourceContentSetByName(String contentSet)
	{
		if(doesElementExist(UserContentSetsPageElements.SOURCE_SELECTED_CONTENT_GROUP + "[text()='" + contentSet + "']"))
		{
			return;
		}
		click(UserContentSetsPageElements.SOURCE_HIDDEN_CONTENT_GROUP + "[text()='" + contentSet + "']");
		clickAddSourceTargetContentSet();
	}

	public void removeSourceContentSetByName(String contentSet)
	{
		click(UserContentSetsPageElements.SOURCE_SELECTED_CONTENT_GROUP + "[text()='" + contentSet + "']");
		clickRemoveSourceTargetContentSet();
	}

	public void clickAddSourceTargetContentSet()
	{
		UserContentSetsPageElements.addSourceCurrentContentSet.click();
	}

	public void clickRemoveSourceTargetContentSet()
	{
		UserContentSetsPageElements.removeSourceCurrentContentSet.click();
	}

	public void saveContentSettings()
	{
		click(UserContentSetsPageElements.saveButton);
		AutoITUtils.verifyAlertTextAndAccept(true, "Your preferences have been saved");
		enterTheInnerFrame();
	}

}
