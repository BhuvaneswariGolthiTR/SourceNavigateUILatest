package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.DetailReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.SummaryReportPopupElementsAngular;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SummaryReportPopupAngular extends ReportPopupAngular {

    private final WebDriver driver;

    @Autowired
    public SummaryReportPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, SummaryReportPopupElementsAngular.class);
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

    
    public String getRuByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "5"));
    }

    
    public String getReloadedByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "6"));
    }

    
    public String getNewByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "7"));
    }

    
    public String getUpdatedByRowIndex(String index) {
        return getElementsText(String.format(DetailReportPopupElementsAngular.CELL_BY_ROW_COLUMN_INDEX, index, "8"));
    }
}
