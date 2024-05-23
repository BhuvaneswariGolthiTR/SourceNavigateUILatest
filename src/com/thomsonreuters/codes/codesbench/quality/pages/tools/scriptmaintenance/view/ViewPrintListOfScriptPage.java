package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewPrintListOfScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ViewPrintListOfScriptPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewPrintListOfScriptPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewPrintListOfScriptPageElements.class);
    }

    public List<String> getListOfScripts()
    {
        return getElementsTextList(ViewPrintListOfScriptPageElements.LIST_OF_SCRIPTS);
    }

    public List<String> getListOfScriptsIDs()
    {
        return getElementsTextList(ViewPrintListOfScriptPageElements.LIST_OF_SCRIPTS_IDS);
    }

    public boolean doesA999ScriptExistWithinAString(List<String> listOfScripts)
    {
        return listOfScripts.stream().noneMatch(item -> item.matches("1-\\d{4}-999"));
    }

}
