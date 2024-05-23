package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.TableHeaders;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class CasesPageAngular extends BasePage
{

    @Autowired
    public CasesPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CasesPageElementsAngular.class);
    }

    
    public void openNodCasesPage(ContentSets contentSet)
    {
        String urlWithContentSet = String.format(urls().getNodPageUrl(), environmentTag,
                CasesPageElementsAngular.URL_MODIFIER, contentSet.getCode());
        openPageWithUrl(urlWithContentSet, CasesPageElementsAngular.PAGE_TITLE);
        waitForPageLoaded();
        cleanLocalStorage();
    }

    public boolean isPageOpened()
    {
        return doesElementExist(CasesPageElementsAngular.PAGE_TAG);
    }

    public HashMap<String, Integer> getExpectedColumnOrder()
    {
        return new HashMap<String, Integer>()
        {{
            put("Loaded Date", 1);
            put("Case serial", 2);
            put("Westlaw", 3);
            put("Reporter Cite", 4);
            put("Court", 5);
            put("R/U", 6);
            put("Reloaded", 7);
            put("Headnotes", 8);
            put("Title", 9);
            put("Subscriptions", 10);
        }};
    }

    public List<String> getActualColumnOrder(TableHeaders headers)
    {
        return headers.getAllTableHeadersNames();
    }

    public String getContentSetTeamMessage()
    {
        String wholeMessage = getElementsText(CasesPageElementsAngular.CONTENT_SET_TEAM_MESSAGE);
        return wholeMessage.replace("Currently viewing cases for: ", "");
    }

    public void clickGoButton()
    {
        click(CasesPageElementsAngular.GO_BUTTON);
    }

    public void selectContentSetTeam(String contentSetName)
    {
        sendTextToTextbox(CasesPageElementsAngular.CONTENT_SET_TEAM_INPUT, contentSetName);
        sendEnterToElement(CasesPageElementsAngular.CONTENT_SET_TEAM_INPUT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        clickGoButton();
    }
}

