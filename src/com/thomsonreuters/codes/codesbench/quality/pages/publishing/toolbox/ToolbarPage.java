package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ToolbarPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public ToolbarPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, ToolbarElements.class);
        }

        public void clickNext()
        {
                click(ToolbarElements.NEXT);
                waitForPageLoaded();
                gridPage().waitForGridLoaded();
        }

        @Deprecated
        public boolean isNextButtonDisplayed()
        {
                return isElementDisplayed(ToolbarElements.NEXT);
        }

        @Deprecated
        public boolean isPublishNowRadioButtonDisplayed()
        {
                return isElementDisplayed(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        }

        @Deprecated
        public boolean isPublishTonightRadioButtonDisplayed()
        {
                return isElementDisplayed(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);
        }

        public void clickSubmit()
        {
                click(ToolbarElements.SUBMIT);
        }

        public void clickBack()
        {
                click(ToolbarElements.BACK);
                waitForPageLoaded();
                gridPage().waitForGridLoaded();
        }

        public void clickRequeryAndReloadFromDatabase()
        {
                click(ToolbarElements.RELOAD);
                waitForPageLoaded();
                gridPage().waitForGridLoaded();
        }

        public void clickClearFiltersAndSorts()
        {
                click(ToolbarElements.CLEAR_FILTERS);
                waitForPageLoaded();
                gridPage().waitForGridLoaded();
        }

        public void clickLoadCompleteForPast30DaysButton()
        {
                click(ToolbarElements.LOAD_COMPLETE_FOR_PAST_30_DAYS);
                waitForPageLoaded();
                gridPage().waitForGridLoaded();
        }


        public boolean didProgressBarDisappear()
        {
                if(doesElementExist(ToolbarElements.PROGRESS_BAR))
                {
                        return waitForElementGone(ToolbarElements.PROGRESS_BAR);
                }
                else
                {
                        return true;
                }
        }

        public boolean isSubmitButtonDisabled()
        {
                return Boolean.valueOf(getElement(ToolbarElements.SUBMIT + "/..").getAttribute("disabled"));
        }

        public boolean verifyContentSetIsDisplayed(String contentSet)
        {
                waitForElement(String.format(MainHeaderElements.PUBLISHING_TOOLBOX_CONTENT_SET_XPATH, contentSet));
                return isElementDisplayed(String.format(MainHeaderElements.PUBLISHING_TOOLBOX_CONTENT_SET_XPATH, contentSet));
        }

        @Deprecated
        public boolean verifyVWarningAppears()
        {
                return isElementDisplayed(ToolbarElements.V_WARNING_XPATH);
        }

        @Deprecated
        public boolean verifyRWarningAppears()
        {
                return isElementDisplayed(ToolbarElements.R_WARNING_XPATH);
        }

        public void clickRefreshCurrentGrid()
        {
                click(ToolbarElements.REFRESH);
                gridPage().waitForGridLoaded();
        }

        public void checkHideFullVolsCheckbox()
        {
                checkCheckbox(ToolbarElements.HIDE_FULL_VOLS_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void checkHideFullVolsSarCheckbox()
        {
                checkCheckbox(ToolbarElements.HIDE_FULL_VOLS_SAR_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void checkHideComLawTrackingCheckbox()
        {
                checkCheckbox(ToolbarElements.HIDE_COM_LAW_TRACKING_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void checkHideFullVolsRecompCheckbox()
        {
                checkCheckbox(ToolbarElements.HIDE_FULL_VOLS_RECOMP_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void checkHideFullVolsCompareCheckbox()
        {
                checkCheckbox(ToolbarElements.HIDE_FULL_VOLS_COMPARE_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void uncheckHideFullVolsCheckbox()
        {
                uncheckCheckbox(ToolbarElements.HIDE_FULL_VOLS_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void uncheckHideFullVolsSarCheckbox()
        {
                uncheckCheckbox(ToolbarElements.HIDE_FULL_VOLS_SAR_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void uncheckHideComLawTrackingCheckbox()
        {
                uncheckCheckbox(ToolbarElements.HIDE_COM_LAW_TRACKING_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void uncheckHideFullVolsRecompCheckbox()
        {
                uncheckCheckbox(ToolbarElements.HIDE_FULL_VOLS_RECOMP_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void uncheckHideFullVolsCompareCheckbox()
        {
                uncheckCheckbox(ToolbarElements.HIDE_FULL_VOLS_COMPARE_CHECKBOX);
                gridPage().waitForGridLoaded();
        }

        public void clickPublishNowRadioButton()
        {
                click(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        }

        public void clickPublishTonightRadioButton()
        {
                click(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);
        }

        public void clickVolumeSelection()
        {
                click(ToolbarElements.VOLUME_SELECTION);
        }
}
