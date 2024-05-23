package com.thomsonreuters.codes.codesbench.quality.pages.nod.reports;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.BrowseReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.NoTeamReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class BrowseReportsPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public BrowseReportsPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, BrowseReportsPageElements.class);
	}
	
	public boolean goToReportsNoTeamPage() 
	{
		return switchToWindow(NoTeamReportPageElements.NO_TEAM_REPORT_PAGE_TITLE);
	}
	
	public boolean switchToNoTeamReportPage()
	{
		waitForPageLoaded();
		return switchToWindow("No Team Report");
	}
	
	public boolean isBatchMergeReportsDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Batch Merge Reports"));
	}
	
	public boolean areAllBatchMergeReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Batch Merge Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
	}

	public boolean isBatchReorderReportsDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Batch Reorder Reports"));
	}
	
	public boolean areAllBatchReorderReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Batch Reorder Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
	}
	
	public boolean isUpdateReportsDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Update Reports"));
	}
	
	public boolean areAllUpdateReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Update Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
	}
	
	public boolean isXusscUpdateReportDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD XUSSC Update Reports"));
	}
	
	public boolean areAllXusscUpdateReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD XUSSC Update Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
    }
	
	public boolean isUnmergedReportsDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "Unmerged NOD Reports"));
	}
	
	public boolean areAllUnmergedReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "Unmerged NOD Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
	}
	
	public boolean isDataValidationReportsDisplayed()
	{
		return doesElementExist(String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Data Validation Reports"));
	}
	
	public boolean areDataValidationReportDatesLessThen2018()
	{
		String listOfDates = String.format(BrowseReportsPageElements.REPORTS_OF_GIVEN_TYPE, "NOD Data Validation Reports");
		List<String> dateList = browseReportsPage().getElementsDateList(listOfDates);
    	return compareDates(dateList);
	}
	
	public List<String> getElementsDateList(String xpath)
	{
		List<WebElement> elements = getElements(xpath);
		List<String> dateList = new ArrayList<>();
		for (WebElement element : elements)
		{
			dateList.add(element.getText().substring(0, element.getText().indexOf(' ')));
		}
		return dateList;
	}
	
	public boolean compareDates(List<String> dateList)
	{
		return dateList.stream().allMatch(date -> 
		{
			try 
			{
				return DateAndTimeUtils.compareDates(date, "01/01/2018");
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			return false;
		});
	}
}
