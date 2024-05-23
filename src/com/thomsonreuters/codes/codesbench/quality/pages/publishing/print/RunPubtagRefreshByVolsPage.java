package com.thomsonreuters.codes.codesbench.quality.pages.publishing.print;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.RunPubtagRefreshByVOLSPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RunPubtagRefreshByVolsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RunPubtagRefreshByVolsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RunPubtagRefreshByVOLSPageElements.class);
    }

    public void clickOnGivenVolValue(String volValue)
    {
        click(String.format(RunPubtagRefreshByVOLSPageElements.SELECT_VOL_BY_GIVEN_VALUE,volValue));
    }

    public void clickOnSingleArrowGoingRight()
    {
        click(RunPubtagRefreshByVOLSPageElements.singleArrowToRightButton);
    }

    public void clickPubtagRefresh()
    {
        click(RunPubtagRefreshByVOLSPageElements.pubtagRefreshButton);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void switchToRunPubtagRefreshByVolsPage()
    {
        switchToWindow(RunPubtagRefreshByVOLSPageElements.PUBTAG_REFRESH_PAGE_TITLE);
    }

    public List<String> getListOfAllVolumesUnderSelectedVolsTable()
    {
        return getElements(RunPubtagRefreshByVOLSPageElements.ALL_VOLUMES_IN_SELECTED_VOLS_TABLE).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
