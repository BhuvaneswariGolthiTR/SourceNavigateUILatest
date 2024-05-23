package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionGroupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionGroupPageElements.*;

@Component
public class SourceNavigateAngularSectionGroupPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularSectionGroupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularSectionGroupPageElements.class);
    }
    public String getTotalNumberOfSectionGroups() {
        return getElementsText(TOTAL_SECETION_GROUP_NUMBERS).trim();

    }
    public void clickSectionGroupTabClearFiltersButton() {
        click(CLEAR_FILTERS_SECTION_GROUP_TAB);
    }
}
