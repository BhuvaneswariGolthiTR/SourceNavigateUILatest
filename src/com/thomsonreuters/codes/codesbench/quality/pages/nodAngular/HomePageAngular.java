package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.HomePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HomePageAngular extends BasePage
{

    private final WebDriver driver;

    @Autowired
    public HomePageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HomePageElementsAngular.class);
    }

    public enum NodHomePageListOptions
    {
        SUBSCRIBED_CASES(HomePageElementsAngular.SUBSCRIBED_CASES),
        CASES(HomePageElementsAngular.CASES),
        COURTS(HomePageElementsAngular.COURTS),
        ADMINISTRATIVE_OPINIONS(HomePageElementsAngular.ADMINISTRATIVE_OPINIONS);
//        BROWSE_REPORTS(),
//        NO_TEAM(),
//        SUMMARY(),
//        DETAIL(),
//        INITIATE_NOD_BATCH_MERGE(),
//        INITIATE_NOD_UNMERGED_REPORT(),
//        INITIATE_NOD_UPDATE(),
//        IMPORT_LTC_NOVUS_LOAD(),
//        INITIATE_NOD_DATA_VALIDATION(),
//        INITIATE_XUSSC_UPDATE(),
//        INITIATE_NOD_BATCH_REORDER(),
//        IMPORT_COURT_TEAM_ROUTING();

        private String xpath;

        NodHomePageListOptions(String xpath)
        {
            this.xpath = xpath;
        }

        public String getXpath()
        {
            return xpath;
        }
    }

    
    public void openNodHomePage(ContentSets contentSet)
    {
        String urlWithContentSet = String.format(urls().getNodPageUrl(), environmentTag,
                HomePageElementsAngular.URL_MODIFIER, contentSet.getCode());
        openPageWithUrl(urlWithContentSet, SubscribedCasesPageElementsAngular.PAGE_TITLE);
        waitForPageLoaded();
        cleanLocalStorage();
    }

    
    public void switchHomePageAngular()
    {
        switchToWindow(HomePageElementsAngular.PAGE_TITLE);
    }

    
    public void clickAdminOpinions()
    {
        click(HomePageElementsAngular.ADMINISTRATIVE_OPINIONS);
        waitForPageLoaded();
    }

    
    public void clickCourts()
    {
        click(HomePageElementsAngular.COURTS);
        waitForPageLoaded();
    }

    
    public void clickImportCourtTeamRouting()
    {
        click(HomePageElementsAngular.IMPORT_COURT_TEAM_ROUTING);
        waitForPageLoaded();
    }

    
    public void clickSubscribedCases()
    {
        click(HomePageElementsAngular.SUBSCRIBED_CASES);
        waitForPageLoaded();
    }

    
    public void clickCases()
    {
        click(HomePageElementsAngular.CASES);
        waitForPageLoaded();
    }

    
    public void clickInitiateNODBatchMerge()
    {
        click(HomePageElementsAngular.INITIATE_NOD_BATCH_MERGE);
        waitForPageLoaded();
    }

    
    public void clickInitiateNODUnmergedReport()
    {
        click(HomePageElementsAngular.INITIATE_NOD_UNMERGED_REPORT);
        waitForPageLoaded();
    }

    
    public void clickInitiateNODUpdate()
    {
        click(HomePageElementsAngular.INITIATE_NOD_UPDATE);
        waitForPageLoaded();
    }

    
    public void clickInitiateNODDataValidation()
    {
        click(HomePageElementsAngular.INITIATE_NOD_DATA_VALIDATION);
        waitForPageLoaded();
    }

    
    public void clickInitiateXUSSCUpdate()
    {
        click(HomePageElementsAngular.INITIATE_XUSSC_UPDATE);
        waitForPageLoaded();
    }

    
    public void clickInitiateNODBatchReorder()
    {
        click(HomePageElementsAngular.INITIATE_NOD_BATCH_REORDER);
        waitForPageLoaded();
    }

    
    public void clickImportLtcNovusLoad()
    {
        click(HomePageElementsAngular.IMPORT_LTC_NOVUS_LOAD);
        waitForPageLoaded();
    }

    
    public void clickDetail()
    {
        click(HomePageElementsAngular.DETAIL);
        waitForPageLoaded();
    }

    public void clickNoTeam()
    {
        click(HomePageElementsAngular.NO_TEAM);
        waitForPageLoaded();
    }

    public void clickSummary()
    {
        click(HomePageElementsAngular.SUMMARY);
        waitForPageLoaded();
    }

    public void clickAutoMerge()
    {
        click(HomePageElementsAngular.AUTO_MERGE);
        waitForPageLoaded();
    }

    public boolean verifyPageIsOpened()
    {
        return doesElementExist(HomePageElementsAngular.PAGE_TAG);
    }

    
    public void clickHomePageMenuOption(NodHomePageListOptions option)
    {
        click(option.getXpath());
    }

    
    public String getTextFromMessageAndCloseIt()
    {
        String text = "There is NO message on the Home page";
        if (doesElementExist(HomePageElementsAngular.MESSAGE))
        {
            text = getElementsText(HomePageElementsAngular.MESSAGE);
            click(HomePageElementsAngular.MESSAGE_CLOSE_BUTTON);
        }
        return text;
    }
}

