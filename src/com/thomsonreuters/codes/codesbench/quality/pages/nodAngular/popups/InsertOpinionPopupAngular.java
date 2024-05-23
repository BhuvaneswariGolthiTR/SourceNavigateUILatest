package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.InsertOpinionPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions.AdminOpinionFormFragmentAngular;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertOpinionPopupAngular extends AdminOpinionFormFragmentAngular
{
    @Autowired
    public InsertOpinionPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertOpinionPopupElementsAngular.class);
        super.init();
    }

    public boolean isPageOpened()
    {
        return (doesElementExist(InsertOpinionPopupElementsAngular.PAGE_TAG));
    }

    public boolean waitPageOpened()
    {
        return waitForElementExists(InsertOpinionPopupElementsAngular.PAGE_TAG, 7000);
    }

    public void clickInsert()
    {
        click(InsertOpinionPopupElementsAngular.insertButton);
    }

    public void clickCancel()
    {
        click(InsertOpinionPopupElementsAngular.cancelButton);
    }

    public void clickSaveAndEditClassify()
    {
        click(InsertOpinionPopupElementsAngular.saveAndEditClassifyButton);
    }

}
