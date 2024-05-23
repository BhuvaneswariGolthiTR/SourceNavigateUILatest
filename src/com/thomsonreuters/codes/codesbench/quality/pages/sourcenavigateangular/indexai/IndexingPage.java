package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.PrintPreviewPageElements.PRINT_PREVIEW_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static java.lang.String.format;

@Component
public class IndexingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingPageElements.class);
    }

    public boolean isBoxDisplayed(String boxName)
    {
        return isElementDisplayed(format(BOX_NAME_XPATH, boxName));
    }

    public void clickPrintPreviewButton()
    {
        click(PRINT_PREVIEW_BUTTON);
        waitForElementExists(format(UI_TITLE_PATTERN, PRINT_PREVIEW_PAGE_TITLE));
    }

    public String getOutlineBorderColor(String xpath, String cssParameter)
    {
        String outlineCssValues = driver.findElement(By.xpath(xpath)).getCssValue(cssParameter);
        return Color.fromString(outlineCssValues).asHex();
    }

    //todo the rendition won't open in DS Editor because IE mode is required for the DS Editor
    public void openFullRenditionInEditor()
    {
        click(OPEN_IN_EDITOR_BUTTON);
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
//        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
//        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public void hoverElement(String xpath)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(xpath)))
                .pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }

    public void switchToNextFocusableElement()
    {
        sendKeys(Keys.TAB);
    }

    public void pressEnterToActivateButton()
    {
        sendKeys(Keys.ENTER);
    }
}
