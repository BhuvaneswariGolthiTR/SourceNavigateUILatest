package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptForVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptVersion998PageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.delete.DeleteScriptConfirmationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit.EditScriptRulesConfirmationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceClamshellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewDescriptionContentSetsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewPrintListOfScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewPrintScriptRulesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScriptMaintenanceClamshellPage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public ScriptMaintenanceClamshellPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ScriptMaintenanceClamshellPageElements.class);
    }

    private boolean genericGoTo(String clamshellPaneUnexpanded, String clamshellPaneOption, String clamshellPaneOptionPageXpath)
    {
        genericExpand(clamshellPaneUnexpanded);
        sendEnterToElement(clamshellPaneOption);
        waitForElement(clamshellPaneOptionPageXpath);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean pageExists = doesElementExist(clamshellPaneOptionPageXpath);
        waitForPageLoaded();
        return pageExists;
    }

    private void genericExpand(String clamshellPaneUnexpanded)
    {
        if(doesElementExist(clamshellPaneUnexpanded))
        {
            sendEnterToElement(clamshellPaneUnexpanded);
        }
    }

    private void genericExpand(String clamshellPaneUnexpanded, String clamshellPaneExpanded)
    {
        if(doesElementExist(clamshellPaneUnexpanded))
        {
            sendEnterToElement(clamshellPaneUnexpanded);
        }
        waitForElement(clamshellPaneExpanded);
    }

    public void expandEdit()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.EDIT_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.EDIT_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToEditScriptRules()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.EDIT_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.EDIT_SCRIPT_RULES_BUTTON, EditScriptRulesConfirmationPageElements.PAGE_XPATH);
    }

    public void expandView()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.VIEW_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.VIEW_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToViewDescriptionContentSets()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.VIEW_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.VIEW_DESCRIPTION_CONTENT_SETS_BUTTON, ViewDescriptionContentSetsPageElements.PAGE_XPATH);
    }

    public boolean goToViewPrintScriptRules()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.VIEW_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.VIEW_PRINT_SCRIPT_RULES_BUTTON, ViewPrintScriptRulesPageElements.PAGE_XPATH);
    }

    public boolean goToViewPrintListOfScript()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.VIEW_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.VIEW_PRINT_LIST_OF_SCRIPT_BUTTON, ViewPrintListOfScriptPageElements.PAGE_XPATH);
    }

    public void expandCreate()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.CREATE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.CREATE_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToCreateScript()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.CREATE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_BUTTON, CreateScriptPageElements.PAGE_XPATH);
    }

    public boolean goToCreateScriptVersion()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.CREATE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_BUTTON, CreateScriptVersionPageElements.PAGE_XPATH);
    }

    public boolean goToCreateScriptVersion998()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.CREATE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_998_BUTTON, CreateScriptVersion998PageElements.PAGE_XPATH);
    }

    public void expandCopy()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.COPY_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.COPY_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToCopyScript()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.COPY_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_BUTTON, CopyScriptPageElements.PAGE_XPATH);
    }

    public boolean goToCopyScriptForVersion()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.COPY_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_FOR_VERSION_BUTTON, CopyScriptForVersionPageElements.PAGE_XPATH);
    }

    public void expandDelete()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.DELETE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.DELETE_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToDeleteScript()
    {
        return genericGoTo(ScriptMaintenanceClamshellPageElements.DELETE_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.DELETE_SCRIPT_BUTTON, DeleteScriptConfirmationPageElements.PAGE_XPATH);
    }

    public void expandMisc()
    {
        genericExpand(ScriptMaintenanceClamshellPageElements.MISC_CLAMSHELL_MENU_PANE_UNEXPANDED, ScriptMaintenanceClamshellPageElements.MISC_CLAMSHELL_MENU_PANE_EXPANDED);
    }

    public boolean goToMiscChangeDescriptionContentSets()
    {
        return false;
    }
}
