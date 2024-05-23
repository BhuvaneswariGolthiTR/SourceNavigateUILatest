package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.SEE_MORE_HYPERLINK;


@Component
public class IndexingSessionLawBoxPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingSessionLawBoxPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingPageElements.class);
    }

    //todo the rendition won't open in DS Editor because IE mode is required for the DS Editor
    public void openFullRenditionInEditor()
    {
        isSeeMoreHyperlinkPresent();
        click(SEE_MORE_HYPERLINK);
        switchToWindow(EditorPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        waitForPageLoaded();
//        waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
//        waitForElement(EditorToolbarPageElements.CLOSE_DOC);
    }

    public boolean isSeeMoreHyperlinkPresent()
    {
        return doesElementExist(SEE_MORE_HYPERLINK);
    }
}
