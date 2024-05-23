package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptForVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ScriptMaintenanceMetadataPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ScriptMaintenanceMetadataPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ScriptMaintenanceMetadataPageElements.class);
    }

    public List<String> getSelectedContentSets()
    {
        return getElementsTextList(ScriptMaintenanceMetadataPageElements.SELECTED_CONTENT_SETS);
    }

    public void setVersionDescription(String versionDescription)
    {
        clearAndSendKeysToElement(ScriptMaintenanceMetadataPageElements.versionDescription,versionDescription);
    }

    public void setScriptName(String scriptName)
    {
        clearAndSendKeysToElement(ScriptMaintenanceMetadataPageElements.scriptName,scriptName);
    }
}
