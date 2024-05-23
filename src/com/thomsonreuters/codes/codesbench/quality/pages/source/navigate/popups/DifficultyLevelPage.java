package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.DifficultyLevelPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DifficultyLevelPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DifficultyLevelPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DifficultyLevelPageElements.class);
    }

    public void selectDifficultyLevel(String level)
    {
        selectDropdownOption(DifficultyLevelPageElements.difficultyLevel, level);
    }

    public boolean switchToDifficultyLevelWindow()
    {
        boolean windowAppears = switchToWindow(DifficultyLevelPageElements.DIFFICULTY_LEVEL_PAGE_TITTLE);
        waitForPageLoaded();
        switchToInnerIFrameByIndex(0);
        return windowAppears;
    }

    public void clickSave()
    {
        click(DifficultyLevelPageElements.saveButton);
        waitForWindowGoneByTitle(DifficultyLevelPageElements.DIFFICULTY_LEVEL_PAGE_TITTLE);
        waitForGridRefresh();
    }
}
