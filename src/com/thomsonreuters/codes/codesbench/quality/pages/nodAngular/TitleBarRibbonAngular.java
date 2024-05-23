package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.TitleBarRibbonElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TitleBarRibbonAngular extends BasePage
{

    @Autowired
    public TitleBarRibbonAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TitleBarRibbonElementsAngular.class);
    }

    public enum NodUiRibbonOptions
    {
        HOME(TitleBarRibbonElementsAngular.HOME),
        SUBSCRIBED_CASES(TitleBarRibbonElementsAngular.SUBSCRIBED_CASES),
        CASES_PAGE(TitleBarRibbonElementsAngular.CASES_PAGE),
        COURTS(TitleBarRibbonElementsAngular.COURTS),
        ADMIN_OPINIONS(TitleBarRibbonElementsAngular.ADMIN_OPINIONS);

        private String xpath;

        NodUiRibbonOptions(String xpath)
        {
            this.xpath = xpath;
        }

        public String getXpath()
        {
            return xpath;
        }
    }

    public void clickRibbonOption(NodUiRibbonOptions option)
    {
        click(option.getXpath());
    }

    public void clickSubscribedCases()
    {
        clickRibbonOption(NodUiRibbonOptions.SUBSCRIBED_CASES);
    }

    public void clickHome()
    {
        clickRibbonOption(NodUiRibbonOptions.HOME);
    }

    public void clickAdministrativeOpinions()
    {
        clickRibbonOption(NodUiRibbonOptions.ADMIN_OPINIONS);
    }

}

