package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class PublicationsPage extends OCExtractBasePage
{
    private final WebDriver driver;

    @Autowired
    public PublicationsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PublicationsPageElements.class);
    }

    public List<String> getExpectedColumnOrder()
    {
        return Arrays.asList("Year", "Publication Name");
    }

    @Override
    public boolean isPageOpened()
    {
        if (super.isPageOpened())
        {
            waitForElement(String.format(OCExtractBasePageElements.TAB_PANEL_VISIBILITY_XPATH,
                    PublicationsPageElements.TAB_PANEL_NUMBER));
            return isElementDisplayed(String.format(OCExtractBasePageElements.TAB_PANEL_VISIBILITY_XPATH,
                    PublicationsPageElements.TAB_PANEL_NUMBER));
        }
        return super.isPageOpened();
    }
}
