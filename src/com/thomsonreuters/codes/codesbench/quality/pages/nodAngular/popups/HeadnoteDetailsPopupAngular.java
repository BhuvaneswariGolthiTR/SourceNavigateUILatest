package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.HeadnoteDetailsPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeadnoteDetailsPopupAngular extends BasePage
{
    @Autowired
    public HeadnoteDetailsPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HeadnoteDetailsPopupElementsAngular.class);
    }

    
    public boolean isPageOpened()
    {
        try
        {
            waitForVisibleElement(HeadnoteDetailsPopupElementsAngular.HEADER);
            waitForPageLoaded();
        } catch (Exception ignored)
        {
        }
        return (doesElementExist(HeadnoteDetailsPopupElementsAngular.PAGE_TAG));
    }

    
    public boolean isHeadnotesNumberShownAndExpected(int totalHeadnotes)
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.HEADNOTE) && getElementsText(
                HeadnoteDetailsPopupElementsAngular.HEADNOTE_VALUE).equals(String.format("1 of %s", totalHeadnotes));
    }

    
    public boolean isTopicAndKeyNumberShown()
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.TOPIC_AND_KEY_NUMBER) &&
                !getElementsText(HeadnoteDetailsPopupElementsAngular.TOPIC_AND_KEY_NUMBER_VALUE).equals("");
    }

    
    public boolean isContentSetShownAndExpected(ContentSets contentSet)
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.CONTENT_SET) &&
                getElementsText(HeadnoteDetailsPopupElementsAngular.CONTENT_SET_VALUE).equals(contentSet.getName());
    }

    
    public boolean isFullTextShown()
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.FULL_TEXT) &&
                isElementDisplayed(HeadnoteDetailsPopupElementsAngular.FULL_TEXT_VALUE);
    }

    
    public boolean isDigestsFieldShown()
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.DIGESTS) &&
                !getElementsText(HeadnoteDetailsPopupElementsAngular.DIGESTS_VALUE).equals("");
    }

    
    public boolean areNavigationArrowButtonsShown()
    {
        return isElementDisplayed(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_FIRST) &&
                isElementDisplayed(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_NEXT) &&
                isElementDisplayed(HeadnoteDetailsPopupElementsAngular.ARROW_TO_PREVIOUS) &&
                isElementDisplayed(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_LAST);
    }

    public List<String> getNavigationPageButtons()
    {
        List<WebElement> actualPageButtons = getElements(HeadnoteDetailsPopupElementsAngular.PAGE_NUMBERS);
        return actualPageButtons.stream().map(WebElement::getText).filter(element -> !element.equals("...")).
                map(element -> element.replace(" (current)", "")).collect(Collectors.toList());
    }

    public List<String> generateExpectedPageNumbersUsingTotalNumberOfHeadnotes(int totalNumberOfHeadnotes)
    {
        List<String> expectedPages = new ArrayList<>();
        for (int i = 1; i <= totalNumberOfHeadnotes; i++)
        {
            if (i < 6 || i == totalNumberOfHeadnotes)
            {
                if (i == 1)
                {
                    String toAdd = i + "\n(current)";
                    expectedPages.add(toAdd);
                }
                else
                {
                    expectedPages.add(Integer.toString(i));
                }
            }
        }
        return expectedPages;
    }

    public String getHeadnoteNumber()
    {
        return getElementsText(HeadnoteDetailsPopupElementsAngular.HEADNOTE_VALUE);
    }

    
    public void clickGoToNextHeadnote()
    {
        click(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_NEXT);
    }

    
    public void clickGoToPreviousHeadnote()
    {
        click(HeadnoteDetailsPopupElementsAngular.ARROW_TO_PREVIOUS);
    }

    
    public void clickGoToLastHeadnote()
    {
        click(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_LAST);
    }

    
    public void clickGoToFirstHeadnote()
    {
        click(HeadnoteDetailsPopupElementsAngular.ARROW_TO_THE_FIRST);
    }

    
    public void clickGoToHeadnoteByNumber(int number)
    {
        click(String.format(HeadnoteDetailsPopupElementsAngular.BLUELINE_BY_NUMBER, number));
    }

    
    public boolean isSaveButtonEnabled()
    {
        return getElement(HeadnoteDetailsPopupElementsAngular.SAVE_BUTTON).isEnabled();
    }

    public void clickSave()
    {
        sendEnterToElement(getElement(HeadnoteDetailsPopupElementsAngular.SAVE_BUTTON));
    }

    public void clickCancel()
    {
        sendEnterToElement(getElement(HeadnoteDetailsPopupElementsAngular.CANCEL_BUTTON));
    }

    public void sendTextToHeadnoteTextArea(String text)
    {
        waitForElement(HeadnoteDetailsPopupElementsAngular.TEXTAREA);
        clearAndSendKeysToElement(HeadnoteDetailsPopupElementsAngular.textArea, text);
    }

    public void ctrlVToHeadnoteTextArea()
    {
        waitForElement(HeadnoteDetailsPopupElementsAngular.TEXTAREA);
        click(HeadnoteDetailsPopupElementsAngular.textArea);
        clear(HeadnoteDetailsPopupElementsAngular.textArea);
        sendKeysToElement(HeadnoteDetailsPopupElementsAngular.TEXTAREA, Keys.chord(Keys.CONTROL, "v"));
    }

    public String getTextFromHeadnoteTextArea()
    {
        waitForElement(HeadnoteDetailsPopupElementsAngular.TEXTAREA);
        click(HeadnoteDetailsPopupElementsAngular.TEXTAREA);
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").build().perform();
        try
        {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        }
        catch(UnsupportedFlavorException | IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String getHeadnoteTopicAndKeyNumber()
    {
        StringBuilder result = new StringBuilder();
        waitForElement(HeadnoteDetailsPopupElementsAngular.ALL_INFORMATION);
        ArrayList<String> text = Arrays.stream(getElementsText(HeadnoteDetailsPopupElementsAngular.ALL_INFORMATION).split("\\n")).collect(Collectors.toCollection(ArrayList::new));
        int startIndex = text.indexOf("Topic & Key Number");
        int endIndex = text.indexOf("Content Set");
        for( int i=startIndex; i<endIndex; i++)
        {
            result.append(text.get(i)).append(" ");
        }
        return result.toString().replaceAll("\\s+$", "");
    }

}
