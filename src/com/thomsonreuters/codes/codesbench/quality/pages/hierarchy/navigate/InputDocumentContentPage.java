package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.InputDocumentContentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InputDocumentContentPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InputDocumentContentPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InputDocumentContentPageElements.class);
    }

    public void findAndSelectFile(String fileName)
    {
        Actions builder = new Actions(driver());
        builder.moveToElement(InputDocumentContentPageElements.selectedFileTextBox).click(InputDocumentContentPageElements.selectedFileTextBox);
        builder.perform();
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileName);
    }

    public void clickOk()
    {
        click(InputDocumentContentPageElements.okButton);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickCancel()
    {
        click(InputDocumentContentPageElements.cancelButton);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public boolean isWindowClosed()
    {
        return checkWindowIsClosed(InputDocumentContentPageElements.INPUT_DOCUMENT_CONTENT_PAGE_TITLE);
    }
}
