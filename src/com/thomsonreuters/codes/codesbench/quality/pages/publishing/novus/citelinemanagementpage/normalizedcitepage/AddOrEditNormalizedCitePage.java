package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddOrEditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddOrEditNormalizedCitePage extends CiteLineManagementPage
{
    private WebDriver driver;

    @Autowired
    public AddOrEditNormalizedCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditNormalizedCitePageElements.class);
    }

    public void setCitationPrefix(String setValue)
    {
        genericInput(setValue, AddOrEditNormalizedCitePageElements.citationPrefixInput);
    }

    public void setCondensedPrefix(String setValue)
    {
        genericInput(setValue, AddOrEditNormalizedCitePageElements.condensedPrefixInput);
    }

    public void setComments(String setValue)
    {
        genericInput(setValue, AddOrEditNormalizedCitePageElements.commentsInput);
    }

    public void clearCondensedPrefix(String valueToClear)
    {
        genericClear(valueToClear, AddOrEditNormalizedCitePageElements.condensedPrefixInput);
    }

    public void clearCitationPrefix(String valueToClear)
    {
        genericClear(valueToClear, AddOrEditNormalizedCitePageElements.citationPrefixInput);
    }

    public void setNewCiteFields(String citationPrefix, String condensedPrefix, String comments)
    {
        setCitationPrefix(citationPrefix);
        setCondensedPrefix(condensedPrefix);
        setComments(comments);
    }

    public boolean commentsHasTooManyCharactersError()
    {
        return isElementDisplayed(AddOrEditNormalizedCitePageElements.COMMENTS_LENGTH_ERROR);
    }
}
