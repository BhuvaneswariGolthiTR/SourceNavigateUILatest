package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract.PublicationsContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationsTablePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PublicationsTablePage extends TablePageAngular
{
    private final WebDriver driver;

    @Autowired
    public PublicationsTablePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
        PageFactory.initElements(driver, PublicationsTablePageElements.class);
    }
    
    public void doubleClickFirstPublication()
    {
        doubleClickCellTextByRowAndColumn(PublicationsTablePageElements.FIRST_PUBLICATION_ROW_NUMBER, TableColumns.PUBLICATION_NAME);
        publicationFilesTablePage().waitForHiddenOverlay();
        waitForElement("//div[contains(@class,'main-toolbar')]//div[contains(@class,'left-part')]//span[text()='Files']");
    }
    
    public void rightClickFirstPublication()
    {
        rightClickCellTextByRowAndColumn(PublicationsTablePageElements.FIRST_PUBLICATION_ROW_NUMBER, TableColumns.PUBLICATION_NAME);
    }

    public String getFirstPublicationYear()
    {
        return getCellTextByRowAndColumn(PublicationsTablePageElements.FIRST_PUBLICATION_YEAR_ROW_NUMBER, TableColumns.YEAR).substring(0, 4);
    }

    public String getFirstPublicationName()
    {
        return getCellTextByRowAndColumn(PublicationsTablePageElements.FIRST_PUBLICATION_ROW_NUMBER, TableColumns.PUBLICATION_NAME);
    }
    
    public void selectFirstPublication()
    {
        clickCellTextByRowAndColumn(PublicationsTablePageElements.FIRST_PUBLICATION_ROW_NUMBER, TableColumns.PUBLICATION_NAME);
    }

}
