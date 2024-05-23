package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ViewMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ViewMetadataPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewMetadataPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewMetadataPageElements.class);
    }

    public boolean switchToViewMetadataPage()
    {
        boolean windowAppeared = switchToWindow(ViewMetadataPageElements.VIEW_METADATA_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean isNodeTypeEqualToGivenValue(String expectedNodeType)
    {
        return checkFieldValueIsExpectedOne(ViewMetadataPageElements.selectedNodeTypeOption,expectedNodeType);
    }

    public void clickCancel()
    {
        click(ViewMetadataPageElements.cancelButton);
    }
}
