package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.DetailReportPopupElementsAngular;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DetailReportPopupAngular extends ReportPopupAngular {
    private final WebDriver driver;

    @Autowired
    public DetailReportPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, DetailReportPopupElementsAngular.class);
    }

    
    public String getTitleByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "1"));
    }

    
    public String getCaseSerialByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "2"));
    }

    
    public String getWestlawByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "3"));
    }

    
    public String getCourtByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "4"));
    }

}
