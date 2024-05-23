package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;

@Component
public class SourceNavigateAngularDeltaPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularDeltaPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularDeltaPageElements.class);
    }

    public void rightClickDelta(String xpath)
    {
        rightClick(xpath);
        breakOutOfFrame();
    }

    public String getTotalNumberOfDeltas() {
        return getElementsText(TOTAL_DELTA_NUMBERS).trim();

    }
    
    public void clickDeltaTabClearFiltersButton() {
        click(CLEAR_FILTERS_DELTA_TAB);
    }
    public boolean getDeltaLevelInstructionsReadOnly()
    {
        return getElementsAttribute(SourceNavigateAngularDeltaPageElements.deltaLevelinstructions, "readonly").equals("true");
    }
    public String deltaLockedByUsername()
    {
        return getElementsText(SourceNavigateAngularDeltaPageElements.DELTA_LOCKED_BY).trim();
    }

    public void selectTwoDeltas(String delta1,String delta2)
    {
        Actions action = new Actions(driver);
        action.click(getElement(delta1));
        action.pause(Duration.ofSeconds(1));
        action.keyDown(Keys.SHIFT);
        action.pause(Duration.ofSeconds(1));
        action.click(getElement(delta2));
        action.pause(Duration.ofSeconds(1));
        action.keyUp(Keys.SHIFT).build().perform();
    }
}
