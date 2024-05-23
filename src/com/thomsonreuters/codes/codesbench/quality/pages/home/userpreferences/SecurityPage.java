package com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserSecuritySettingsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class SecurityPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SecurityPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UserSecuritySettingsPageElements.class);
    }

    //Note 6/30/2020: UI Changes?
    public boolean doesUserHaveSwatAccess()
    {
        String swatAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.swatAccess));
        return swatAccess.equals("V");
    }

    public boolean doesUserHaveParatechAccess()
    {
        String paratechAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.paratechAccess));
        return paratechAccess.equals("V");
    }

    public boolean doesUserHaveWestlawPublisherAccess()
    {
        String westlawPublisherAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.westlawPublisherAccess));
        return westlawPublisherAccess.equals("V");
    }

    public boolean doesUserHaveJetstreamEditorSwatUserAccess()
    {
        String jetstreamEditorSwatUserAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.jetstreamEditorSwatUserAccess));
        return jetstreamEditorSwatUserAccess.equals("V");
    }

    public boolean doesUserHaveLegalCT1UserAccess()
    {
        String legalCT1UserAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.legalCT1UserAccess));
        return legalCT1UserAccess.equals("V");
    }

    public boolean doesUserHaveRiskCT1UserAccess()
    {
        String riskCT1UserAccess = getElementsText(getElementsNextSibling(UserSecuritySettingsPageElements.riskCT1UserAccess));
        return riskCT1UserAccess.equals("V");
    }
}

