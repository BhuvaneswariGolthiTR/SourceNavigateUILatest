package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.AddAssignedScriptsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RemoveAssignedScriptsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RemoveAssignedScriptsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RemoveAssignedScriptsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RemoveAssignedScriptsPageElements.class);
    }

    public boolean isScriptWithGivenPubTagDisplayed(String pubTagValue)
    {
       return doesElementExist(String.format(RemoveAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE,pubTagValue));
    }

    public String getIndicatorByValue(String pubTagValue){
        String indicator = "There is NO such pub tag";
        if(doesElementExist(String.format(RemoveAssignedScriptsPageElements.INDICATOR_BY_GIVEN_PUBTAG_VALUE,pubTagValue))){
            indicator = getElementsText(String.format(RemoveAssignedScriptsPageElements.INDICATOR_BY_GIVEN_PUBTAG_VALUE,pubTagValue));
        }
        return indicator;
    }

    public void rightClickScriptWithGivenPubTagValue(String pubTagValue)
    {
        click(String.format(RemoveAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE,pubTagValue));
        rightClick(String.format(RemoveAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE,pubTagValue));
    }

    public List<String> getListOfAllScripts()
    {
        List<String> data = new ArrayList<String>();
        int scriptsNumber = countElements(RemoveAssignedScriptsPageElements.NUMBER_OF_ROWS_IN_SCRIPTS_GRID);
        for (int index = 1; index <= scriptsNumber; index++)
        {
            StringBuilder scriptsInfo = new StringBuilder();
            getElements(String.format(RemoveAssignedScriptsPageElements.CELL_VALUE_IN_GRID_ROW_OF_GIVEN_INDEX, index))
                    .stream().forEach(cell -> scriptsInfo.append(cell.getText().replace("Both", "Single"))); // replace being made for the case of comparing scripts of child and parent scripts
            data.add(scriptsInfo.toString());
        }
        Collections.sort(data);
        return data;
    }

    public void clickClose()
    {
        click(RemoveAssignedScriptsPageElements.closeButton);
        waitForWindowGoneByTitle(RemoveAssignedScriptsPageElements.REMOVE_ASSIGNED_SCRIPTS_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void switchToRemoveAssignedScriptsPage()
    {
        switchToWindow(RemoveAssignedScriptsPageElements.REMOVE_ASSIGNED_SCRIPTS_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public boolean isRemoveAssignedScriptsPageClosed()
    {
        return checkWindowIsClosed(RemoveAssignedScriptsPageElements.REMOVE_ASSIGNED_SCRIPTS_PAGE_TITLE);
    }
}
