package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.COLUMN_SEARCH_FIELD;

@Component
public class GridHeaderPage extends BasePage
{
        WebDriver driver;

        @Autowired
        GridHeaderPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, GridHeaderElements.class);
        }

        public void openMenuForNodeHierarchyColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.NODE_HIERARCHY_COLUMN_MENU_XPATH);
        }

        public void openMenuForValueColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.VALUE_COLUMN_MENU_XPATH);
        }

        public void openMenuForStatusSetDateColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.STATUS_SET_DATE_COLUMN_MENU_XPATH);
        }

        public void openMenuForKeywordColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.KEYWORD_COLUMN_MENU_XPATH);
        }

        public void openMenuForReadyUserColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.READY_USER_COLUMN_MENU_XPATH);
        }

        public void openMenuForModifiedByColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.MODIFIED_BY_COLUMN_MENU_XPATH);
        }

        public void openMenuForVolColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.VOL_COLUMN_MENU_XPATH);
        }

        public void openMenuForStartDate()
        {
                clickOnInvisibleElement(GridHeaderElements.START_DATE_COLUMN_MENU_XPATH);
        }

        public void openMenuForEndDate()
        {
                clickOnInvisibleElement(GridHeaderElements.END_DATE_COLUMN_MENU_XPATH);
        }

        public void openMenuForModifiedDateColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.MODIFIED_DATE_COLUMN_MENU_XPATH);
        }

        public void openMenuForReadyDateColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.READY_DATE_COLUMN_MENU_XPATH);
        }

        public void openMenuForNodeUuidColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.NODE_UUID_COLUMN_MENU_XPATH);
        }

        public void openMenuForCodeColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.CODE_COLUMN_MENU_XPATH);
        }

        public void openMenuForFlagColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.FLAG_COLUMN_MENU_XPATH);
        }

        public void openMenuForStatusColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.STATUS_COLUMN_MENU_XPATH);
        }

        public String getPublishingStatusColumnHeaderText()
        {
                return getElementsText(GridHeaderElements.PUBLISHING_STATUS_COLUMN_XPATH);
        }

        public void openMenuForPublishingStatusColumn2()
        {
                clickOnInvisibleElement(GridHeaderElements.PUBLISHING_STATUS_COLUMN_MENU_XPATH_2);
        }

        public void openMenuForTargetValueColumn()
        {
                clickOnInvisibleElement(GridHeaderElements.TARGET_VALUE_COLUMN_MENU_XPATH);
        }

        public void filterForColumnAndSelect(String columnName)
        {
                click(GridHeaderElements.COLUMN_SEARCH_FIELD);
                sendKeys(columnName);
                new Actions(driver)
                        .pause(Duration.ofSeconds(1))
                        .sendKeys(Keys.ENTER)
                        .click(driver.findElement(By.xpath(GridHeaderElements.COLUMN_SEARCH_FIELD)))
                        .sendKeys(Keys.END)
                        .build()
                        .perform();
                for (int i = 0; i < columnName.length(); i++)
                {
                        sendKeys(Keys.BACK_SPACE);
                }
        }

}
