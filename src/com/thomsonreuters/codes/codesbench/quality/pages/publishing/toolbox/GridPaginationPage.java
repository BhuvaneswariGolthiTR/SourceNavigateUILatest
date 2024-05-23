package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridPaginationElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GridPaginationPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public GridPaginationPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, GridPaginationElements.class);
        }

        public int getTotalNumberOfSelectableRows()
        {
                String selectableRows = getElementsText(GridPaginationElements.selectableRows);
                String selectableRowsTrimmed = selectableRows.substring(17).trim();
                return Integer.valueOf(selectableRowsTrimmed);
        }
}
