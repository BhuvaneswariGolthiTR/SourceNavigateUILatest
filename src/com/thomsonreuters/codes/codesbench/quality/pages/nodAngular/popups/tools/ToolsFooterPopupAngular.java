package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ToolsFooterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ToolsFooterPopupAngular extends BasePage
{
    @Autowired
    public ToolsFooterPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ToolsFooterPopupElementsAngular.class);
    }

    public void clickCancel()
    {
        ToolsFooterPopupElementsAngular.cancel.click();
    }

    public void createWorkflow()
    {
        ToolsFooterPopupElementsAngular.createWorkflow.click();
    }

    public boolean isCreateWorkflowButtonEnabled()
    {
        return ToolsFooterPopupElementsAngular.createWorkflow.isEnabled();
    }

}
