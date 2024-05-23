package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class SubscribedCasesPageAngular extends BasePage
{

    @Autowired
    public SubscribedCasesPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SubscribedCasesPageElementsAngular.class);
    }

    
    public void openNodSubscribedCasesPage(ContentSets contentSet)
    {
        String urlWithContentSet = String.format(urls().getNodPageUrl(), environmentTag,
                SubscribedCasesPageElementsAngular.URL_MODIFIER, contentSet.getCode());
        openPageWithUrl(urlWithContentSet, SubscribedCasesPageElementsAngular.PAGE_TITLE);
        waitForPageLoaded();
        cleanLocalStorage();
    }

    
    public boolean isPageOpened()
    {
        return doesElementExist(SubscribedCasesPageElementsAngular.PAGE_TAG);
    }

    public LinkedHashMap<String, Integer> getExpectedUSColumnOrder()
    {
        return new LinkedHashMap<String, Integer>()
        {{
            put("Loaded Date", 1);
            put("Notes", 2);
            put("Case serial", 3);
            put("Westlaw", 4);
            put("Reporter Cite", 5);
            put("Court", 6);
            put("R/U", 7);
            put("Reloaded", 8);
            put("HN", 9);
            put("AI Ignored HN", 10);
            put("Title", 11);
            put("Signed Out By", 12);
            put("Completed Date", 13);
            put("Completed By", 14);
            put("Cite Information", 15);
        }};
    }

    public List<String> getExpectedCanadaColumnOrder()
    {
        return Arrays.asList("Loaded Date","Notes","CCDB","Westlaw","Neutral Cite","Court","R/U","Reloaded","HN","AI Ignored HN","Title","Signed Out By","Completed Date","Completed By","Cite Information");
    }

    
    public void clickClearFilters()
    {SubscribedCasesPageElementsAngular.clearFilters.click();}

    
    public void clickClearSort()
    {SubscribedCasesPageElementsAngular.clearSort.click();}

    
    public void inputTextInSearchByCaseSerialField(String text)
    {
        SubscribedCasesPageElementsAngular.SearchByCaseSerial.sendKeys(text);
        subscribedCasesTablePage().waitForTableToReload();
    }

    public void scrollToRight(String offset)
    {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollLeft += %s",offset), getElement(SubscribedCasesPageElementsAngular.HORIZONTAL_SCROLL));
    }
}

