package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.VolumeInformationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class VolumeInformationPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public VolumeInformationPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, VolumeInformationPageElements.class);
    }

    public void setVolumeNumberDropdown(String valueToSet)
    {
        selectDropdownOption(VolumeInformationPageElements.volumeNumberDropdown,valueToSet);
        waitForGridRefresh();
    }

    public String getSelectedVolumeNumber()
    {
        return VolumeInformationPageElements.selectedVolumeNumberDropdownOption.getText();
    }

    public boolean isVolumeTitleEqualTo(String expectedTitle)
    {
        String actualTitle = getElementsAttribute(VolumeInformationPageElements.volumeTitle, "value");
        return actualTitle.equals(expectedTitle);
    }

    public void switchToVolumeInformationPage()
    {
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        switchToWindow(VolumeInformationPageElements.VOLUME_INFORMATION_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void clickEditContentDynamicScrollingUnderBov()
    {
        click(VolumeInformationPageElements.editContentDynamicScrollingUnderBov);
        switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void clickEditContentDynamicScrollingUnderEov()
    {
        click(VolumeInformationPageElements.editContentDynamicScrollingUnderEov);
        switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void clickCloseButton()
    {
        click(CommonPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(VolumeInformationPageElements.VOLUME_INFORMATION_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickSubmit()
    {
        click(VolumeInformationPageElements.submitButton);
        waitForWindowGoneByTitle(VolumeInformationPageElements.VOLUME_INFORMATION_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
