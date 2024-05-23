package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions.EditAdminOpinionsPageElementsAngular;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditAdminOpinionsPageAngular extends AdminOpinionFormFragmentAngular
{
    private final WebDriver driver;

    @Autowired
    public EditAdminOpinionsPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditAdminOpinionsPageElementsAngular.class);
        super.init();
    }

    public void superInit()
    {
        super.init();
    }

    public boolean isPageOpened()
    {
        return doesElementExist(EditAdminOpinionsPageElementsAngular.PAGE_TAG);
    }

    public boolean waitPageOpened()
    {
        return waitForElementExists(EditAdminOpinionsPageElementsAngular.PAGE_TAG, 7000);
    }

    public void clickUpdate()
    {
        click(EditAdminOpinionsPageElementsAngular.updateButton);
    }

    public void clickCancel()
    {
        click(EditAdminOpinionsPageElementsAngular.cancelButton);
    }




}
