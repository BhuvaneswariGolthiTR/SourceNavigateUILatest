package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ScriptMaintenanceGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ScriptMaintenanceGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ScriptMaintenanceGridPageElements.class);
    }

    public boolean scriptExists(String pubTag, String scriptName, String versionDescription, String status)
    {
        return doesElementExist(String.format(ScriptMaintenanceGridPageElements.GENERIC_SCRIPT_ROW, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
    }
    public void switchToScriptPage()
    {
        switchToWindow(ScriptMaintenancePageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }
    public String getScriptIdOfScript(String pubTag, String scriptName, String versionDescription, String status)
    {
        return getElementsText(String.format(ScriptMaintenanceGridPageElements.SCRIPT_ID_XPATH, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
    }

    public void clickHideDeletedCheckbox()
    {
        waitForPageLoaded();
        waitForElementGone(ScriptMaintenanceGridPageElements.PROGRESS_BAR);
        click(ScriptMaintenanceGridPageElements.HIDE_DELETED_CHECKBOX);
        waitForElementGone(ScriptMaintenanceGridPageElements.PROGRESS_BAR);
    }

    public List<String> getScriptIdListOfScript(String pubTag, String scriptName, String versionDescription, String status)
    {
        return getElementsTextList(String.format(ScriptMaintenanceGridPageElements.SCRIPT_ID_XPATH, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
    }

    public void selectScript(String pubTag, String scriptName, String versionDescription, String status)
    {
        click(String.format(ScriptMaintenanceGridPageElements.GENERIC_SCRIPT_ROW, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
        waitForElement(String.format(ScriptMaintenanceGridPageElements.GENERIC_SELECTED_SCRIPT_ROW, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
        waitForPageLoaded();
    }

    public void rightClickScript(String pubTag, String scriptName, String versionDescription, String status)
    {
        rightClick(String.format(ScriptMaintenanceGridPageElements.GENERIC_SCRIPT_ROW, pubTag, String.format("%s - %s", scriptName, versionDescription), status));
    }

    public String getContentSetFromTopLeftCorner()
    {
        return getElementsText(ScriptMaintenanceGridPageElements.CONTENT_SET_IN_TOP_LEFT_CORNER);
    }
}
