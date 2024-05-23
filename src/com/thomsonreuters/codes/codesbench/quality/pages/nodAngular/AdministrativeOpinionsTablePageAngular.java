package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.AdministrativeOpinionsTablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdministrativeOpinionsTablePageAngular extends TablePageAngular
{
    @Autowired
    public AdministrativeOpinionsTablePageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
        PageFactory.initElements(driver, AdministrativeOpinionsTablePageElementsAngular.class);
    }

    public void clickOpinionNumberByRow(int index)
    {
        click(String.format(AdministrativeOpinionsTablePageElementsAngular.OPTION_NUMBER_HREF_BY_ROW,index));
    }
}
