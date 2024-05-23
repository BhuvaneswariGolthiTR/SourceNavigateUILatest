package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.CanadaCaseInformationFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.UsCaseInformationFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CanadaCaseInformationFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public CanadaCaseInformationFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CanadaCaseInformationFragmentElementsAngular.class);
    }

    public List<String> getCaseInformationWithoutReporter()
    {
        return getElements(UsCaseInformationFragmentElementsAngular.CASE_INFORMATION_COLUMN_VALUES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> sortSubscribedCasesCaseInformationToCanadianHeadnotesPageOrder(List<String> caseInformation)
    {
        List<Integer> caseInformationColumnsOrder = Arrays.asList(0, 2, 3, 4, 5, 6, 7, 11, 12);
        List<String> result = new ArrayList<>();
        for (int index : caseInformationColumnsOrder)
        {
            result.add(caseInformation.get(index));
        }
        if (result.get(6).contains("----"))
        {
            result.set(6, "");
        }
        return result;
    }

    public String getCaseInformationCompletedDate()
    {
        return getElementsText(CanadaCaseInformationFragmentElementsAngular.completedDate);
    }

    public String getCaseInformationSignOutBy()
    {
        return getElementsText(CanadaCaseInformationFragmentElementsAngular.signedOutBy);
    }

    public void waitUntilCCDBChangesToFollowing(String newCaseSerial)
    {
        waitUntilElementsTextIsTheFollowing(CanadaCaseInformationFragmentElementsAngular.CCDB, newCaseSerial);
    }
}
