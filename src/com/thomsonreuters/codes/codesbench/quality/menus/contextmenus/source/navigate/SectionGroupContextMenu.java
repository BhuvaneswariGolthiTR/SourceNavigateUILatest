package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.SectionGroupContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.Menu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.CreatePreparationDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupContextMenu extends Menu {

    private WebDriver driver;

    @Autowired
    public SectionGroupContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupContextMenuElements.class);
    }

    public void editSectionGroup()
    {
        openContextMenu(RenditionContextMenuElements.EDIT, SectionGroupContextMenuElements.SECTION_GROUP);
    }

    public void moveGroupDown()
    {
        openContextMenu(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_DOWN);
    }

    public void moveGroupUp()
    {
        openContextMenu(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_UP);
    }

    public void remove()
    {
        openContextMenu(SectionGroupContextMenuElements.SECTION_GROUPING_REMOVE_GROUP);
    }

    public void rename()
    {
        openContextMenu(SectionGroupContextMenuElements.SECTION_GROUPING_RENAME_GROUP);
    }

    public void moveToGroupX(String group)
    {
        openContextMenu(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_TO_GROUP, String.format(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_TO_GROUP_X,group));
    }

    public boolean createPrepDocument()
    {
        openContextMenu(SectionGroupContextMenuElements.create, SectionGroupContextMenuElements.createPreparationDocument);
        AutoITUtils.verifyAlertTextContainsAndAccept(true,"PREP Rendition(s) have been previously created for this Rendition. Do you want to proceed with the creation of PREP document(s)?");
        boolean pageAppeared = switchToWindow(CreatePreparationDocumentPageElements.createPreparationDocumentTitle);
        enterTheInnerFrame();
        return pageAppeared;
    }
}
