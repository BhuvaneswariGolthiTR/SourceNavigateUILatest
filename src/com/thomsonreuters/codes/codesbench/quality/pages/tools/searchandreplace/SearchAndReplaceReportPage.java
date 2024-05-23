package com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplaceReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SearchAndReplaceReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SearchAndReplaceReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SearchAndReplaceReportPageElements.class);
    }

    public boolean verifyNodeUuidMatches(String nodeUuid)
    {
        return isElementDisplayed(String.format(SearchAndReplaceReportPageElements.NODE_UUID_XPATH, nodeUuid));
    }

    public boolean verifyHighlightedPhraseAppearsBeforeChange(String phrase)
    {
        return isElementDisplayed(String.format(SearchAndReplaceReportPageElements.BEFORE_CHANGE_HIGHLIGHTED_TEXT_XPATH, phrase));
    }

    public boolean verifyHighlightedPhraseAppearsAfterChange(String phrase)
    {
        return isElementDisplayed(String.format(SearchAndReplaceReportPageElements.AFTER_CHANGE_HIGHLIGHTED_TEXT_XPATH, phrase));
    }

    public boolean clickCommitButton()
    {
        click(SearchAndReplaceReportPageElements.commitButton);
        boolean windowOpened = switchToWindow(SearchAndReplacePageElements.SAR_PAGE_TITLE_LOWERCASE);
        enterTheInnerFrame();
        return windowOpened;
    }
}
