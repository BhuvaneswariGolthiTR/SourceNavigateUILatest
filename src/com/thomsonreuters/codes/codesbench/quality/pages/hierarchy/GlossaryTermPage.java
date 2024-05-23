package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.GlossaryTermPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GlossaryTermPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public GlossaryTermPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, GlossaryTermPageElements.class);
    }

    public String getTextOfPrimaryTerm()
    {
        return GlossaryTermPageElements.primaryTerm.getAttribute("value");
    }

    public String getTextOfDefinitionContent()
    {
        return GlossaryTermPageElements.definitionContent.getText();
    }

    public String getTextOfSelectedAlternateTerm()
    {
        return GlossaryTermPageElements.selectedTerm.getText();
    }

    public void clickCancel()
    {
        click(GlossaryTermPageElements.cancelButton);
        waitForWindowGoneByTitle(GlossaryTermPageElements.GLOSSARY_TERM_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
