package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLineagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLineagePageElements.*;

@Component
public class SourceNavigateAngularLineagePage extends BasePage {

    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularLineagePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularLineagePageElements.class);
    }

    public String getTotalNumberOfLineage() {
        return getElementsText(TOTAL_LINEAGE_NUMBERS).trim();
    }

    public void clickLineageTabClearFiltersButton() {
        click(CLEAR_FILTERS_LINEAGE_TAB);
    }
}
