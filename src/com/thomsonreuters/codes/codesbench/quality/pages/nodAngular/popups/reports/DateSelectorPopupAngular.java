package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.DateSelectorPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DateSelectorPopupAngular extends BasePage {
    private final WebDriver driver;

    @Autowired
    public DateSelectorPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, DateSelectorPopupElementsAngular.class);
    }

    
    public boolean isDateSelectorDisplayed() {
        return getElement(DateSelectorPopupElementsAngular.DATE_SELECTOR).isDisplayed();
    }

    
    public void selectYear(String year) {
        click(DateSelectorPopupElementsAngular.YEAR);
        click(DateSelectorPopupElementsAngular.YEAR_OPTION_ONE);
        click(DateSelectorPopupElementsAngular.YEAR);
        click(String.format(DateSelectorPopupElementsAngular.OPTION, year));
    }

    
    public void selectMonth(String month) {
        click(DateSelectorPopupElementsAngular.MONTH);
        click(String.format(DateSelectorPopupElementsAngular.OPTION, month));
    }

    
    public void selectDate(String date) {
        click(String.format(DateSelectorPopupElementsAngular.DATE, date));
    }

}


