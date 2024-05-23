package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RelatedRuleBooksPageElements;

@Component
public class RelatedRuleBooksPage extends BasePage
{
	private WebDriver driver;

	@Autowired
    public RelatedRuleBooksPage(WebDriver driver)
    {
	    super(driver);
	    this.driver = driver;
    }

    @PostConstruct
    public void init()
{
    PageFactory.initElements(driver, RelatedRuleBooksPageElements.class);
}

    public void clickOnGivenRulebook(String contentSet)
    {
	    try
        {
		    click(String.format(RelatedRuleBooksPageElements.OPEN_GIVEN_CONTENT_SET,contentSet));
        }
        catch (Exception e)
        {
            closeCurrentWindowIgnoreDialogue();
        }
    }

    public void switchToRelatedRuleBooksPage()
    {
        switchToWindow(RelatedRuleBooksPageElements.RELATED_RULE_BOOK_PAGE_TITLE);
        switchToInnerIFrameByIndex(0);
    }
}
