package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.GenerateLegislativeServiceTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.ListLegislativeServiceTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ListLegislativeServiceTablesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ListLegislativeServiceTablesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ListLegislativeServiceTablesPageElements.class);
	}
	public int getLegislativeServiceTableRow(String legislativeServiceTableID)
	{
		int rowWeWant = 0;
		List<WebElement> tableRows = getElements("//table[@id='tableman']//tbody//tr");
		for (WebElement webElement : tableRows)
		{
			String text = webElement.getText();
			if(text.contains(legislativeServiceTableID))
			{
				rowWeWant = ListLegislativeServiceTablesPageElements.tableRows.indexOf(webElement);
				break;
			}
		}
			return rowWeWant + 1;
	}
	
	public boolean editLegislativeServicePamphlet(String legislativeServicePamphletID, String text) 
	{
		String editPamphlet = String.format(ListLegislativeServiceTablesPageElements.GENERIC_TABLE_ROW, legislativeServicePamphletID, ListLegislativeServiceTablesPageElements.LEGISLATIVE_SERVICE_TABLE_PAGE_EDIT);
		click(editPamphlet);
    	waitForPageLoaded();

		boolean textDoesntAppearBeforeEdit = !(ListLegislativeServiceTablesPageElements.legislativeServiceTableEditButton).getText().contains(text);
		waitForPageLoaded();
		click(ListLegislativeServiceTablesPageElements.legislativeTablesEditBox);
		sendKeysToElement(ListLegislativeServiceTablesPageElements.legislativeTablesEditBox, text);
		boolean textAppearsAfterEdit = (ListLegislativeServiceTablesPageElements.legislativeTablesEditBox.getText().contains(text));
		click(ListLegislativeServiceTablesPageElements.LIST_LEGISLATIVE_SERVICE_TABLE_CANCEL_VIEW);
    	waitForPageLoaded();

		return textDoesntAppearBeforeEdit && textAppearsAfterEdit;
    }
    
  	public boolean deleteLegislativeServicePamphlet(String legislativeServicePamphletID)
  	{
  		String deletePamphlet = String.format(ListLegislativeServiceTablesPageElements.GENERIC_TABLE_ROW, legislativeServicePamphletID, ListLegislativeServiceTablesPageElements.LEGISLATIVE_SERVICE_TABLE_PAGE_DELETE);
		click(deletePamphlet);
    	waitForPageLoaded();

		click(ListLegislativeServiceTablesPageElements.okButton);
		clickLastButton();

		return !isElementDisplayed(deletePamphlet);
  	}
  	
    public void clickLastButton()
    {
    	click(GenerateLegislativeServiceTablesPageElements.lastButton);
    	waitForGridRefresh();
    }
    
	public boolean downloadLegislativeServiceTable(String legislativeServiceTableID) 
	{
		int code = -1;
		try
		{
			URL url = new URL(ListLegislativeServiceTablesPageElements.DOWNLOAD_BUTTON_GENERATE_LTS_LINK + legislativeServiceTableID);
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.connect();
		    code = connection.getResponseCode();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return code == 200;
	}
}
