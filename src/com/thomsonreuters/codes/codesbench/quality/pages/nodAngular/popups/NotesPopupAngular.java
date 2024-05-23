package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.NotesPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NotesPopupAngular extends BasePage
{

    @Autowired
    public NotesPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, NotesPopupElementsAngular.class);
    }

    public boolean isPopupShown()
    {
        return doesElementExist(NotesPopupElementsAngular.NOTES_POPUP);
    }

    public boolean waitForPopup()
    {
        return waitForElementExists(NotesPopupElementsAngular.NOTES_POPUP, 7000);
    }

    public boolean isPopupFullyOnScreen()
    {
        Dimension windowSize = driver().manage().window().getSize();
        driver().manage().window().getPosition();
        WebElement popup = getElement(NotesPopupElementsAngular.NOTES_POPUP);
        Point popupLocation = popup.getLocation();
        Dimension popupSize = popup.getSize();
        return ((popupSize.height + popupLocation.y) < (windowSize.height - 100));
    }

    public void typeIntoTextArea(String textToType)
    {
        click(NotesPopupElementsAngular.TEXT_AREA);
        sendKeysToElement(NotesPopupElementsAngular.TEXT_AREA, textToType);
    }

    public void clearTextArea()
    {
        clearAndSendTextToTextbox(NotesPopupElementsAngular.TEXT_AREA, "");
    }

    public void clickSaveButton()
    {
        click(NotesPopupElementsAngular.SAVE_BUTTON);
    }

    public String getNoteText()
    {
        return getElement(NotesPopupElementsAngular.TEXT_AREA).getAttribute("value");
    }
}
