package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.BtsWebUiPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.ListPocketPartsTablePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ListPocketPartTablesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ListPocketPartTablesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ListPocketPartsTablePageElements.class);
	}
	
	public void clickGenerateTab()
	{
		click(ListPocketPartsTablePageElements.generateTab);
	}
	
	public boolean listAndGeneratePocketOptionIsDisplayed() 
	{
		return isElementDisplayed(BtsWebUiPageElements.TASKS_TABLES_GENERATE_POCKET_PART_TABLES_MENU_XPATH) && isElementDisplayed(BtsWebUiPageElements.TASKS_TABLES_LIST_POCKET_PART_TABLES_MENU_XPATH);
	}
	
	public String getPocketPartName(int index)
	{
		return getElement(String.format(ListPocketPartsTablePageElements.GENERIC_POCKET_PART_NAME_TABLE_ROW_XPATH, index)).getText();
	}
	
	public String getpocketPartLastModified(int index)
	{
		return getElement(String.format(ListPocketPartsTablePageElements.GENERIC_POCKET_PART_LAST_MODIFIED_ROW_XPATH, index)).getText();
	}
	
	public int getIndexOfPocketPart(String jurisdiction, String currentYearMinusOne)
	{
		Pattern pattern = Pattern.compile(jurisdiction + "P\\d{2,}Y" + currentYearMinusOne);
		List<WebElement> pocketPartIDElements = ListPocketPartsTablePageElements.pocketPartTablesIdElements;
		
		int index = 0;
		for (WebElement pocketPartIdElement : pocketPartIDElements) 
		{
			String pocketPartIDText = pocketPartIdElement.getText();
			Matcher matcher = pattern.matcher(pocketPartIDText);
			if(matcher.find())
			{
				index = pocketPartIDElements.indexOf(pocketPartIdElement) + 1; 
				return index;
			}
		}
		return -1;
	}

	public String getPocketPartID(int index) 
	{
		return getElement(String.format(ListPocketPartsTablePageElements.GENERIC_POCKET_PART_ID_TABLE_ROW_XPATH, index)).getText();
	}
}
