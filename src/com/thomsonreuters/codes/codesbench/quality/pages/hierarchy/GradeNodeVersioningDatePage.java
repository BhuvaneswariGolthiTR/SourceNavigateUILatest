package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.GradeNodeVersioningDatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GradeNodeVersioningDatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public GradeNodeVersioningDatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, GradeNodeVersioningDatePageElements.class);
    }

    public void clickCancelButton()
    {
        click(GradeNodeVersioningDatePageElements.cancelButton);
    }
}
