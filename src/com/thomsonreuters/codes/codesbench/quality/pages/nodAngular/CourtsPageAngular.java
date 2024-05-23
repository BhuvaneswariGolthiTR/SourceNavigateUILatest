package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CourtsPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class CourtsPageAngular extends BasePage {
    @Autowired
    public CourtsPageAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, CourtsPageElementsAngular.class);
    }

    
    public boolean isPageOpened() {
        return doesElementExist(CourtsPageElementsAngular.PAGE_TAG);
    }

    
    public void clickAddCourtRouting() {
        CourtsPageElementsAngular.addCourtRouting.click();
    }

    
    public void deleteCourtByNumber(String courtNumber)
    {
        rightClickOnCellByText(courtNumber);
        click(CourtsPageElementsAngular.DELETE_COURT_ROUTING);
    }

    
    public void rightClickOnCellByText(String text)
    {
        rightClick(String.format(CourtsPageElementsAngular.CELL_BY_TEXT,text));
    }

    public HashMap<String, Integer> getExpectedUSColumnOrder()
    {
        return new HashMap<String, Integer>() {{
            put("Court", 1);
            put("Display Name", 2);
            put("Long Name", 3);
            put("Court Line Style", 4);
        }};
    }

}
