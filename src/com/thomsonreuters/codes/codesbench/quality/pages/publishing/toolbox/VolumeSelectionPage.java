package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.VolumeSelectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class VolumeSelectionPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public VolumeSelectionPage(WebDriver driver)
        {
            super(driver);
            this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
            PageFactory.initElements(driver, VolumeSelectionPageElements.class);
        }

        public void clickCheckBoxForVolume(String volume)
        {
            click(String.format(VolumeSelectionPageElements.CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_AVAILABLE_VOLUME, volume));
        }

        public void clickCheckBoxForVolumeSelectedVolumes(String volume)
        {
            click(String.format(VolumeSelectionPageElements.CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_SELECTED_VOLUME, volume));
        }

        public void clickAdd()
        {
            click(VolumeSelectionPageElements.ADD_BUTTON);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        }

        public void clickRemove()
        {
            click(VolumeSelectionPageElements.REMOVE_BUTTON);
        }

        public void clickConfirm()
        {
            click(VolumeSelectionPageElements.CONFIRM_BUTTON);
            waitForElementGone(VolumeSelectionPageElements.VOLUME_SELECTION_XPATH);
            waitForGridRefresh();
            gridPage().waitForGridLoaded();
        }

        public void clickCancel()
        {
            click(VolumeSelectionPageElements.CANCEL_BUTTON);
            waitForElementGone(VolumeSelectionPageElements.VOLUME_SELECTION_XPATH);
        }

        public int getNumberOfAvialableVolumes()
        {
            return  Integer.parseInt(getElementsText(VolumeSelectionPageElements.AVAILABLE_VOLUMES_COUNT));
        }

        public int getNumberOfSelectedVolumes()
        {
            return  Integer.parseInt(getElementsText(VolumeSelectionPageElements.SELECTED_VOLUMES_COUNT));
        }

        public boolean doVolumesAppearInSelectedVolumes(List<String> volumes)
        {
            List<String> volumesList = getElementsTextList(VolumeSelectionPageElements.SELECTED_VOLUMES_XPATH);
            return ListUtils.areListsEqualContains(volumesList, volumes);
        }
}
