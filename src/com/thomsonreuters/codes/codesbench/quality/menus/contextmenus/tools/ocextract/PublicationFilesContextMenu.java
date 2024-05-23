package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ocextract.PublicationFilesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PublicationFilesContextMenu extends ContextMenu
{
    private final WebDriver driver;

    @Autowired
    public PublicationFilesContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PublicationFilesContextMenuElements.class);
    }


    public void clickPdfDownload()
    {
        //openContextMenu(PublicationFilesContextMenuElements.pdf, PublicationFilesContextMenuElements.pdfDownload);
        click(PublicationFilesContextMenuElements.pdf);
        click(PublicationFilesContextMenuElements.pdfDownload);
        /*
         If the browser is in Edge, it handles file downloads by actually downloading the file
         so we just check to see if we're using the Edge browser in IE Mode and handle the popup then
         */
        if(TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE))
        {
            AutoITUtils.clickSaveOnIEToolboxWithAutoIT();
        }
    }


    public void clickPdfUpload()
    {
        //openContextMenu(PublicationFilesContextMenuElements.pdf, PublicationFilesContextMenuElements.pdfUpload);
        click(PublicationFilesContextMenuElements.pdf);
        click(PublicationFilesContextMenuElements.pdfUpload);
    }
}