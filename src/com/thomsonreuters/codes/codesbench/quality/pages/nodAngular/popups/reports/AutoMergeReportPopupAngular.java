package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.AutoMergeReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AutoMergeReportPopupAngular extends BasePage {
    private final WebDriver driver;

    @Autowired
    public AutoMergeReportPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, AutoMergeReportPopupElementsAngular.class);
    }

    
    public boolean isDownloadLatestReportClickable() {
        return AutoMergeReportPopupElementsAngular.downloadLatestReport.isEnabled();
    }

    
    public void clickDownloadLatestReport() {
        AutoMergeReportPopupElementsAngular.downloadLatestReport.sendKeys(Keys.ENTER);
        AutoITUtils.clickSaveOnIEPopupWithAutoIT();
    }

    
    public void clickSubscribe(){
        if(AutoMergeReportPopupElementsAngular.subscribeButton.isDisplayed()){
            AutoMergeReportPopupElementsAngular.subscribeButton.click();
        }
    }

    
    public boolean isSubscribeDisplayed(){
        return AutoMergeReportPopupElementsAngular.subscribeButton.isDisplayed();
    }

    
    public void clickUnsubscribe(){
        if(AutoMergeReportPopupElementsAngular.unsubscribeButton.isDisplayed()){
            AutoMergeReportPopupElementsAngular.unsubscribeButton.click();
        }
    }

    
    public boolean isUnsubscribeDisplayed(){
        return AutoMergeReportPopupElementsAngular.unsubscribeButton.isDisplayed();
    }

    
    public void close(){
        AutoMergeReportPopupElementsAngular.close.click();
    }

}
