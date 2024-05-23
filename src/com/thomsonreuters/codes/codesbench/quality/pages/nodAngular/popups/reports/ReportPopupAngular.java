package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.DetailReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.ReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ReportPopupAngular extends BasePage {

    private final WebDriver driver;

    @Autowired
    public ReportPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, ReportPopupElementsAngular.class);
    }

    
    public void inputDate(String date) {
        ReportPopupElementsAngular.dateInputField.clear();
        ReportPopupElementsAngular.dateInputField.sendKeys(date);
    }

    
    public void clickSearch() {
        ReportPopupElementsAngular.searchBButton.click();
    }

    
    public String getInvalidMessageText() {
        if (getElement(DetailReportPopupElementsAngular.INVALID_MESSAGE).isDisplayed()) {
            return getElementsText(DetailReportPopupElementsAngular.INVALID_MESSAGE);
        }
        return "Message was NOT found";
    }

    
    public void clickDateSelector() {
        ReportPopupElementsAngular.dateSelectButton.click();
    }

}
