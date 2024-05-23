package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions.DeleteAdminOpinionsPageElementsAngular;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeleteAdminOpinionsPageAngular extends AdminOpinionFormFragmentAngular
{
    private final WebDriver driver;

    @Autowired
    public DeleteAdminOpinionsPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteAdminOpinionsPageElementsAngular.class);
        super.init();
    }

    public boolean isPageOpened()
    {
        return doesElementExist(DeleteAdminOpinionsPageElementsAngular.PAGE_TAG);
    }

    public void clickDeleteOpinion()
    {
        click(DeleteAdminOpinionsPageElementsAngular.deleteOpinionButton);
    }

    public void clickCancel()
    {
        click(DeleteAdminOpinionsPageElementsAngular.cancelButton);
    }
}
