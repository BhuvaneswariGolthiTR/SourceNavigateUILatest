package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.InputAsChildPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InputAsChildPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InputAsChildPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InputAsChildPageElements.class);
    }

    public boolean clickCancel()
    {
        click(InputAsChildPageElements.cancelButton);
        waitForWindowGoneByTitle(InputAsChildPageElements.INPUT_AS_CHILD_PAGE_TITLE);
        return switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void selectFileToUpload(String fileName)
    {
        Actions builder = new Actions(driver);
        builder.moveToElement(InputAsChildPageElements.selectedFileInput).click(InputAsChildPageElements.selectedFileInput);
        builder.perform();
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileName);
    }

    public void clickOk()
    {
        click(InputAsChildPageElements.okButton);
        waitForWindowGoneByTitle(InputAsChildPageElements.INPUT_AS_CHILD_PAGE_TITLE);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }
}
