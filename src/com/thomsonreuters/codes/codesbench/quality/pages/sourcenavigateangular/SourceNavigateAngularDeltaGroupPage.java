package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaGroupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaGroupPageElements.*;

@Component
public class SourceNavigateAngularDeltaGroupPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularDeltaGroupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularDeltaGroupPageElements.class);
    }
    public String getTotalNumberOfDeltaGroups() {
        return getElementsText(TOTAL_DELTA_GROUP_NUMBERS).trim();

    }
    public void clickDeltaGroupTabClearFiltersButton() {
        click(CLEAR_FILTERS_DELTA_GROUP_TAB);
    }

}
