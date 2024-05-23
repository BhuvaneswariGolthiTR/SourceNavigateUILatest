package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ClassifyDescendantsWorkflowPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClassifyDescendantsWorkflowPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ClassifyDescendantsWorkflowPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ClassifyDescendantsWorkflowPageElements.class);
    }

    public boolean totalNodesAffectedEqualToZero()
    {
        return ClassifyDescendantsWorkflowPageElements.totalNodeCount.getText().equals("0");
    }
}
