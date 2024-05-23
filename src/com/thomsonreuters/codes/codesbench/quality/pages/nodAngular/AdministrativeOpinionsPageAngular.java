package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.AdministrativeOpinionsPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdministrativeOpinionsPageAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public AdministrativeOpinionsPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AdministrativeOpinionsPageElementsAngular.class);
    }

    public void openAdministrativeOpinionsPage(ContentSets contentSet)
    {
        String urlWithContentSet = String.format(urls().getNodPageUrl(), environmentTag,
                AdministrativeOpinionsPageElementsAngular.URL_MODIFIER, contentSet.getCode());
        openPageWithUrl(urlWithContentSet, AdministrativeOpinionsPageElementsAngular.PAGE_TITLE);
        waitForPageLoaded();
    }

    public boolean isPageOpened()
    {
        return doesElementExist(AdministrativeOpinionsPageElementsAngular.PAGE_TAG);
    }

    public boolean waitPageOpened()
    {
        return waitForElementExists(AdministrativeOpinionsPageElementsAngular.PAGE_TAG, 7000);
    }

    public void clickInsertOpinion()
    {
        click(AdministrativeOpinionsPageElementsAngular.insertOpinionButton);
    }

    public void clickOpinionNumber(String opinionNumber)
    {
        click(String.format(AdministrativeOpinionsPageElementsAngular.CLICKABLE_OPINION_NUMBER, opinionNumber));
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void rightClickOpinionNumber(String opinionNumber)
    {
        rightClick(String.format(AdministrativeOpinionsPageElementsAngular.CLICKABLE_OPINION_NUMBER, opinionNumber));
    }

    public boolean doesOpinionWithNumberExist(String opinionNumber)
    {
        return doesElementExist(String.format(AdministrativeOpinionsPageElementsAngular.CLICKABLE_OPINION_NUMBER,
                opinionNumber));
    }

    public void switchToAdministrativeOpinionsPage()
    {
        switchToWindow("NodClassifyUi");
    }

}
