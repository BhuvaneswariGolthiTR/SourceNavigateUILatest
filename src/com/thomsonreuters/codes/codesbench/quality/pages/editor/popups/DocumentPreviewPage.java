package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DocumentPreviewPage extends BasePage
 {
    private WebDriver driver;

    @Autowired
    public DocumentPreviewPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, DocumentPreviewPage.class);
    }

     public void closePreview()
     {
         switchToWindow(DocumentPreviewPageElements.PAGE_TITLE);
         closeCurrentWindowIgnoreDialogue();
     }

}