package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.CalendarWidgetElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CalendarWidgetAngular extends BasePage
{
    
    @Autowired
    public CalendarWidgetAngular(WebDriver driver)
    {
        super(driver);
    }
    
    public void selectDateInCalendar(String stringDate)
    {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(stringDate, formatterDate);
        DateTimeFormatter formatterForYear = DateTimeFormatter.ofPattern("yyyy");
        String year = date.format(formatterForYear);
        String firstYear = getElementsText(CalendarWidgetElementsAngular.FIRST_YEAR);
        if (Integer.parseInt(year) < Integer.parseInt(firstYear))
        {
            selectDropdownOption(CalendarWidgetElementsAngular.YEAR, firstYear);
        }
        selectDropdownOption(CalendarWidgetElementsAngular.YEAR, year);
        DateTimeFormatter formatterForMonth = DateTimeFormatter.ofPattern("MMM");
        selectDropdownOption(CalendarWidgetElementsAngular.MONTH, date.format(formatterForMonth));
        DateTimeFormatter formatterForDay = DateTimeFormatter.ofPattern("d");
        String dayDate = date.format(formatterForDay);
        click(getElement(String.format(CalendarWidgetElementsAngular.DATE, dayDate)));
    }
}
