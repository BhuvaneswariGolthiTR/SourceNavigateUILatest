package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ImportCourtTeamRoutingPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ImportCourtTeamRoutingPopupAngular extends BasePage
{
    @Autowired
    public ImportCourtTeamRoutingPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ImportCourtTeamRoutingPopupElementsAngular.class);
    }

    public String getCurrentUserName()
    {
        return ImportCourtTeamRoutingPopupElementsAngular.currentUser.getText();
    }

    public String getMessage()
    {
        return getElementsText(ImportCourtTeamRoutingPopupElementsAngular.MESSAGE);
    }

}
