package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ocextract.PublicationFilesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract.PublicationFilesContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PublicationFilesTablePage extends TablePageAngular
{
    private final WebDriver driver;

    @Autowired
    public PublicationFilesTablePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
    }

    public void rightClickPubFileByName(String pubFileName)
    {
//        TableColumns fileNameColumn = TableColumns.FILE_NAME;
//        rightClickCellTextByTextAndColumn(pubFileName, fileNameColumn);
        rightClick(String.format("//div[@class='ag-center-cols-container']//div[@role='row']//div[@col-id='fileName' and contains(text(),'%s')]",pubFileName));
        waitForElement(PublicationFilesContextMenuElements.CONTEXT_MENU);
    }
}
