package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.BluelineAnalysisPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class BluelineAnalysisPopupAngular extends BasePage
{
    @Autowired
    public BluelineAnalysisPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, BluelineAnalysisPopupElementsAngular.class);
    }

    public boolean isPageOpened()
    {
        return (doesElementExist(BluelineAnalysisPopupElementsAngular.PAGE_TAG));
    }

    public String getHeaderText()
    {
        return bluelineAnalysisPopupAngular().getElement(BluelineAnalysisPopupElementsAngular.HEADER).getText();
    }

    public List<WebElement> getAllBluelines()
    {
        return headnotesPageAngular().getElements(BluelineAnalysisPopupElementsAngular.BLUELINE_ANALYSIS_ITEM);
    }

    public HashMap<String, String> getAllBluelinesAsNameToNumberHashMap()
    {
        List<WebElement> bluelineItems = bluelineAnalysisPopupAngular().getAllBluelines();
        HashMap<String, String> bluelineNamesAndNumbers = new HashMap<>();
        for (WebElement item : bluelineItems)
        {
            bluelineNamesAndNumbers.put(bluelineAnalysisPopupAngular().getElement(item, "./a").getText(),
                    bluelineAnalysisPopupAngular().getElement(item, "./span").getText());
        }
        return bluelineNamesAndNumbers;
    }

    public String selectRandomBlueline(HashMap<String, String> bluelineNamesAndNumbers)
    {
        Random random = new Random();
        List<String> bluelines = new ArrayList<>(bluelineNamesAndNumbers.keySet());
        return bluelines.get(random.nextInt(bluelines.size()));
    }

    public void clickOnBluelineWithName(String name)
    {
        bluelineAnalysisPopupAngular().click(String.format(BluelineAnalysisPopupElementsAngular.BLUELINE_ANALYSIS_ITEM_WITH_TEXT,
                name));
    }

}
