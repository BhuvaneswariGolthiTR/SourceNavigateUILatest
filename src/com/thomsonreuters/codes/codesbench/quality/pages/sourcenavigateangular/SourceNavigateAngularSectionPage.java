package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularRenditionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;

@Component
public class SourceNavigateAngularSectionPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularSectionPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularSectionPageElements.class);
    }

    public void rightClickSection(String xpath)
    {
        rightClick(xpath);
        breakOutOfFrame();
    }

    public String getTotalNumberOfSections() {
        return getElementsText(TOTAL_SECETION_NUMBERS).trim();
    }
    
    public void clickSectionTabClearFiltersButton()
    {
        click(CLEAR_FILTERS_SECTION_TAB);
    }
    public String sectionLockedByUsername()
    {
        return getElementsText(SourceNavigateAngularSectionPageElements.SECTION_LOCKED_BY).trim();
    }
    public boolean getSectionLevelInstructionsReadOnly()
    {
        return getElementsAttribute(SourceNavigateAngularSectionPageElements.sectionLevelinstructions, "readonly").equals("true");
    }

    public void selectTwoSections(String section1,String section2)
    {
        Actions action = new Actions(driver);
        action.click(getElement(section1));
        action.pause(Duration.ofSeconds(1));
        action.keyDown(Keys.SHIFT);
        action.pause(Duration.ofSeconds(1));
        action.click(getElement(section2));
        action.pause(Duration.ofSeconds(1));
        action.keyUp(Keys.SHIFT).build().perform();
    }
    public void addNotes(WebElement xpath, String text)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sendTextToTextbox(xpath, text);
    }

    public void replaceNotesText(WebElement xpath,String replaceWith)
    {
        click(xpath);
        editorTextPage().ctrlAUsingAction();
        editorTextPage().deleteUsingAction();
        editorTextPage().sendKeys(replaceWith);
    }
}
