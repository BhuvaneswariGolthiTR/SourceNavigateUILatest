package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.MismatchedReportGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MismatchedReportGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public MismatchedReportGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, MismatchedReportGridPageElements.class);
    }

    public int getDocumentCount()
    {
        int count = 0;
        if(doesElementExist(MismatchedReportGridPageElements.RENDITIONS_XPATH))
        {
            count = getElements(MismatchedReportGridPageElements.RENDITIONS_XPATH).size();
        }
        return count;
    }

    public String getLoadDateTitle()
    {
        return MismatchedReportGridPageElements.loadDate.getAttribute("title");
    }
}
