package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyInputAsSiblingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HierarchyInputAsSiblingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public HierarchyInputAsSiblingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyInputAsSiblingPageElements.class);
    }

    
    public void selectFileToUpload(String fileName)
    {
        WebElement elementToClick = HierarchyInputAsSiblingPageElements.selectedFileInput;
        Actions builder = new Actions(driver);
        builder.moveToElement(elementToClick).click(elementToClick);
        builder.perform();
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileName);
    }

    
    public void clickSave()
    {
        click(HierarchyInputAsSiblingPageElements.saveButton);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }
}