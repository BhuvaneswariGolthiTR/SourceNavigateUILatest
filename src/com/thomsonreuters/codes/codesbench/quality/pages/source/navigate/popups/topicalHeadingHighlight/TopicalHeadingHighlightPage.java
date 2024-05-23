package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight.TopicalHeadingHighlightPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class TopicalHeadingHighlightPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public TopicalHeadingHighlightPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TopicalHeadingHighlightPageElements.class);
    }

    public boolean switchToTopicalHeadingHighlightWindow()
    {
        boolean windowAppears = switchToWindow(TopicalHeadingHighlightPageElements.TOPICAL_HEADING_HIGHLIGHT_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void goToIndexEntryTab()
    {
        click(TopicalHeadingHighlightPageElements.indexEntryTab);
        waitForPageLoaded();
    }

    public void goToTopicalHeadingTab()
    {
        click(TopicalHeadingHighlightPageElements.topicalHeadingTab);
        waitForPageLoaded();
    }

    public void movePhraseToSelectedPhraseList(String phrase)
    {
        String script = String.format("var index = document.querySelector(\"select[name='pageForm:list1'] option[value='%s']\")" +
                ".index; document.querySelector(\"select\").selectedIndex = index; listboxMoveacross();",phrase);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    public void movePhraseToTheRightAddedList(String phrase)
    {
        click(String.format("//select[@id='pageForm:list1']/option[text()='%s']",phrase));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].ondblclick()", getElement("//select[@id='pageForm:list1']"));
        waitForPageLoaded();
    }

    public boolean isPhraseInSelectedList(String phrase)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        List<WebElement> els = getElements(String.format(TopicalHeadingHighlightPageElements.SELECTED_PHRASES_LIST_OPTION, phrase));
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    public void addUserPhrase()
    {
        sendEnterToElement(TopicalHeadingHighlightPageElements.userAddedButton);
    }

    public void movePhraseUp(String phrase)
    {
        selectPhraseInSelectedPhraseList(phrase);
        sendEnterToElement(TopicalHeadingHighlightPageElements.upButton);
    }

    public void movePhraseDown(String phrase)
    {
        selectPhraseInSelectedPhraseList(phrase);
        sendEnterToElement(TopicalHeadingHighlightPageElements.downButton);
    }

    public int getSelectedPhraseIndex(String phrase)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        selectPhraseInSelectedPhraseList(phrase);
        List<WebElement> phrases = getElements(TopicalHeadingHighlightPageElements.SELECTED_PHRASES_LIST_ITEM);
        List<String> list = new ArrayList<>();

        for(int i=phrases.size() - 1; i > 0; i--)
        {
            list.add(phrases.get(i).getText());
        }
        return list.indexOf(phrase) + 1;
    }

    public void deletePhrase(String phrase)
    {
        selectPhraseInSelectedPhraseList(phrase);
        sendEnterToElement(TopicalHeadingHighlightPageElements.deletePhraseButton);
    }

    public void createIndexEntry(String phrase)
    {
        selectPhraseInSelectedPhraseList(phrase);
        sendEnterToElement(TopicalHeadingHighlightPageElements.createIndexEntryButton);
    }

    public boolean isPhraseInIndexEntryList(String phrase)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        List<WebElement> els = getElements(String.format(TopicalHeadingHighlightPageElements.INDEX_ENTRY_LIST_OPTION, phrase));
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    public void deleteIndexEntry(String phrase)
    {
        click(getElement(String.format(TopicalHeadingHighlightPageElements.INDEX_ENTRY_LIST_OPTION, phrase)));
        sendEnterToElement(TopicalHeadingHighlightPageElements.deleteIndexEntryButton);
    }

    public void clickOk()
    {
        sendEnterToElement(TopicalHeadingHighlightPageElements.okButton);
    }

    public void clickCancel()
    {
        sendEnterToElement(TopicalHeadingHighlightPageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
        waitForWindowGoneByTitle(TopicalHeadingHighlightPageElements.TOPICAL_HEADING_HIGHLIGHT_PAGE_TITLE);
    }

    public void clickCreateTopicalHeading(String expectedTopicalHeading)
    {
        sendEnterToElement(TopicalHeadingHighlightPageElements.createTopicalHeadingButton);
        if(expectedTopicalHeading.length() > 0)
        {
            waitForElement(String.format(TopicalHeadingHighlightPageElements.TOPICAL_HEADING_TEXT_XPATH, expectedTopicalHeading));
        }
        else
        {
            isElementDisplayed(String.format(TopicalHeadingHighlightPageElements.TOPICAL_HEADING_TEXT_XPATH, expectedTopicalHeading));
        }
    }

    private void selectPhraseInSelectedPhraseList(String phrase)
    {
        click(getElement(String.format(TopicalHeadingHighlightPageElements.SELECTED_PHRASES_LIST_OPTION, phrase)));
    }
}
