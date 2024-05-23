package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertImagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigateAddPDFMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertImagePage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public InsertImagePage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, InsertImagePageElements.class);
        }

        public void insertImageThroughUI(String fileName)
        {
                switchToWindow(InsertImagePageElements.PAGE_TITLE);
                enterTheInnerFrame();
                doubleClick(SourceNavigateAddPDFMetadataPageElements.SELECTED_FILE_TEXT_BOX);
                AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileName);
                click(SourceNavigateAddPDFMetadataPageElements.SUBMIT_BUTTON);
                acceptAlert();
                editorPage().switchToEditor();
        }
}
