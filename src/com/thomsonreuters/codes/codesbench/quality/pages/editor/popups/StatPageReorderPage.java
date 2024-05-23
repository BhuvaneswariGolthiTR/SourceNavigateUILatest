package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.StatPageReorderPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StatPageReorderPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public StatPageReorderPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, StatPageReorderPageElements.class);
    }

    public void switchToStatPageReorderWindow()
    {
        switchToWindow(StatPageReorderPageElements.PAGE_TITLE, true);
    }

    public void setFirstReorderedStatPageNumber(String reorderFromNumber)
    {
        clearAndSendKeysToElement(getElement(StatPageReorderPageElements.SET_STAT_PAGE_INPUT), reorderFromNumber);
    }

    public void clickStartReorderFromTopRadioButton()
    {
        click(StatPageReorderPageElements.TOP_RADIO_BUTTON);
    }

    public void clickStartReorderFromCurrentStatPageRadioButton()
    {
        click(StatPageReorderPageElements.CURRENT_RADIO_BUTTON);
    }

    public void clickStartReorderFromNextStatPageRadioButton()
    {
        click(StatPageReorderPageElements.NEXT_RADIO_BUTTON);
    }

    public void clickStartReorderButton()
    {
        click(StatPageReorderPageElements.START_REORDER_BUTTON);
        waitForWindowGoneByTitle(StatPageReorderPageElements.PAGE_TITLE);
    }

    public void clickCancelButton()
    {
        click(StatPageReorderPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(StatPageReorderPageElements.PAGE_TITLE);
    }
}
