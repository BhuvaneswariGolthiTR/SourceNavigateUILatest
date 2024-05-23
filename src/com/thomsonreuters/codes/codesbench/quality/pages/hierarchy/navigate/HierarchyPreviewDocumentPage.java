package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPreviewDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HierarchyPreviewDocumentPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public HierarchyPreviewDocumentPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyPreviewDocumentPageElements.class);
    }

    public boolean doesDocumentContainGivenText(String text)
    {
        return getSource().contains(text);
    }
}
