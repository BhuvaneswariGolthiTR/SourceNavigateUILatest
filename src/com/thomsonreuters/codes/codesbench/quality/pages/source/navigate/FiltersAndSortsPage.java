package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import javax.annotation.PostConstruct;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.DateSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class FiltersAndSortsPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public FiltersAndSortsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FiltersAndSortsPageElements.class);
	}
	
    public void setFilterDeleted(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.deleteFlagFilter, setValue);
    }
    
    public void setFilterContentSet(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.contentSetFilter, setValue);
    }
    
    public void setFilterOrganisation(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.organisationFilter, setValue);
    }
    
    public void setFilterContentType(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.contentTypeFilter, setValue);
    }
    
    public void setFilterYear(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.yearFilter, setValue);
    }
    
    public void setFilterSession(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.sessionFilter, setValue);
    }
    
    public void setFilterDocNumber(String setValue) 
    {
    	sendTextToTextbox(FiltersAndSortsPageElements.docNumberFilter, setValue);
    }
    
    public void setFilterSectionNumber(String setValue) 
    {
    	sendTextToTextbox(FiltersAndSortsPageElements.sectionNumber, setValue);
    }
    
    public void setFilterDocType(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.docTypeFilter, setValue);
    }
    
    public void setFilterLockedContentSet(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.lockContentSetFilter, setValue);
    }
    
    public void setFilterRenditionStatus(String setValue) 
    {
    	sendTextToTextBoxAuto(FiltersAndSortsPageElements.renditionStatusFilter, setValue);
    }

    public void setFilterClassNumber(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.classNumberFilter, setValue);
    }

    public void setFilterLegislationType(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.legislationTypeFilter, setValue);
    }
    public void sortByDeleted()
    {
        click(FiltersAndSortsPageElements.lockContentSort);
        waitForGridRefresh();
    }

    public void setFilterCorrelationID(String setValue) 
    {
		sendTextToTextbox(FiltersAndSortsPageElements.correlationIdFilter, setValue);
    }
    
    public void setFilterRenditionTitle(String setValue) 
    {
    	sendTextToTextbox(FiltersAndSortsPageElements.renditionTitleFilter, setValue);
    }
    
    public void setFilterClassName(String setValue) 
    {
        sendTextToTextbox(FiltersAndSortsPageElements.classNameFilter, setValue);
    }
    
    public void clickRenditionLoadDateAndTimeCalendarButton()
    {
    	sendEnterToElement(FiltersAndSortsPageElements.renditionLoadDateFilterCalendarButton);
    	switchToWindow(DateSearchFilterPageElements.DATE_SEARCH_FILTER_PAGE_TITLE);
    	enterTheInnerFrame();
    }

    public void setFilterDeltaLevel(String setValue)
    {
        sendTextToTextBoxAuto(FiltersAndSortsPageElements.deltaLevelFilter,setValue);
    }

    public void setFilterAction(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.actionFilter,setValue);
    }

    public void setDeltaFilterSectionNumber(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.sectionNumberFilter, setValue);
    }

    public void setSiblingOrderFilter(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.siblingOrderFilter, setValue);
    }

    public void setOnlineProductTagFilter(String setValue)
    {
        sendTextToTextbox(FiltersAndSortsPageElements.onlineProductTagFilter, setValue);
    }

    public void resetOnlineProductTagFilter()
    {
        scrollTo(FiltersAndSortsPageElements.onlineProductTagFilterToggleButton);
        FiltersAndSortsPageElements.onlineProductTagFilterToggleButton.click();
        waitForElement(FiltersAndSortsPageElements.resetOnlineProductTagFilter);
        sendKeys(Keys.ENTER);
    }

    public void clickRenditionVersionDateAndTimeCalendarButton()
    {
        sendEnterToElement(FiltersAndSortsPageElements.renditionVersionDateFilterCalendarDateButton);
        switchToWindow(DateSearchFilterPageElements.DATE_SEARCH_FILTER_PAGE_TITLE);
        enterTheInnerFrame();
    }
    
    /**
     * setValue can be 'None', 'Multiple', or 'Duplicate'
     * @param setValue
     * @
     */
    public void setFilterMultipleDuplicate(String setValue)
    {
    	if(setValue != null && (setValue.equals("None") ||
    							setValue.equals("Multiple") ||
    							setValue.equals("Duplicate")))
    	{
    		sendTextToTextBoxAuto(FiltersAndSortsPageElements.multipleDuplicateFilter, setValue);
    	}
    }
    /**
     * setValue can be 'None', 'Error', 'Invalid Link', 'Updated Link', 'Warning'
     * @param setValue
     * @
     */
    public void setFilterValidation(String setValue) 
    {
    	if(setValue != null && (setValue.equals("None") ||
    							setValue.equals("Error") ||
    							setValue.equals("Invalid Link") ||
    							setValue.equals("Updated Link") ||
    							setValue.equals("Warning")))
    	{
    		sendTextToTextBoxAuto(FiltersAndSortsPageElements.validationFlagFilter, setValue);
    	}
    }

    public boolean validateFilterAttributeValue(String xpath, String attribute, String expectedValue)
    {
        return expectedValue.equals(getElementsAttribute(xpath, attribute));
    }
}
