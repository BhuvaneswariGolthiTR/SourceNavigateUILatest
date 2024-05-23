package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ListOfC2012RenditionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ListOfC2012RenditionsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ListOfC2012RenditionsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ListOfC2012RenditionsPageElements.class);
    }

    public boolean isPageOpened()
    {
        boolean result;
        try
        {
            String title = getElementsText(ListOfC2012RenditionsPageElements.PAGE_TITLE);
            result = title.contains(ListOfC2012RenditionsPageElements.EXPECTED_TITLE);
        }
        catch (NoSuchElementException e)
        {
            result = false;
        }
        return result;
    }

    public String getExpectedWindowName()
    {
        return ListOfC2012RenditionsPageElements.EXPECTED_WINDOW_TITLE;
    }

    public void setLawTrackingToTableRowByNumber(int number)
    {
        rightClick(String.format(ListOfC2012RenditionsPageElements.LAW_TRACKING_RAW, number));
        click(ListOfC2012RenditionsPageElements.SET_LAW_TRACKING);
    }

    public String composeLawTrackingTextForLineByNumber(int lineNumber)
    {
        String firstElement = getElementsText(String.format(ListOfC2012RenditionsPageElements.TABLE_CELL, lineNumber, 2));
        String secondElement = getElementsText(String.format(ListOfC2012RenditionsPageElements.TABLE_CELL, lineNumber, 3));
        if(secondElement.equals(" "))
        {
            secondElement = "";
        }
        String thirdElement = getElementsText(String.format(ListOfC2012RenditionsPageElements.TABLE_CELL, lineNumber, 4));
        String fourthElement = getElementsText(String.format(ListOfC2012RenditionsPageElements.TABLE_CELL, lineNumber, 5));
        String fifthElement = getElementsText(String.format(ListOfC2012RenditionsPageElements.TABLE_CELL, lineNumber, 9));
        if(fifthElement.equals(" "))
        {
            fifthElement = "";
        }
        return String.format("%s %s (%s %s) %s", firstElement, secondElement, thirdElement, fourthElement, fifthElement);
    }
}
