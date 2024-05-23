package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.UpdateMetadataPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UpdateMetadataPopupAngular extends BasePage
{
    @Autowired
    public UpdateMetadataPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateMetadataPopupElementsAngular.class);
    }

    public boolean isPageOpened()
    {
        boolean isPopupOpened = doesElementExist(UpdateMetadataPopupElementsAngular.PAGE_TAG);
        enterTheInnerFrame();
        boolean isContentShown = doesElementExist(UpdateMetadataPopupElementsAngular.HEADER);
        return isPopupOpened && isContentShown;
    }


}
