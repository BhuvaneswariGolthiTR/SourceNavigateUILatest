package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

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
public class UsCaseInformationFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public UsCaseInformationFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UsCaseInformationFragmentElementsAngular.class);
    }

    public List<String> getCaseInformationWithoutReporter()
    {
        List<String> fullCaseInformation = getElements(UsCaseInformationFragmentElementsAngular.CASE_INFORMATION_COLUMN_VALUES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
        //remove reporter number
        fullCaseInformation.remove(7);
        return fullCaseInformation;
    }

    public List<String> sortSubscribedCasesCaseInformationToUSHeadnotesPageOrder(List<String> caseInformation)
    {
        List<Integer> caseInformationColumnsOrder = Arrays.asList(0, 2, 3, 5, 6, 7, 4, 11, 12);
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
        return getElementsText(UsCaseInformationFragmentElementsAngular.completedDate);
    }

    public String getCaseInformationSignOutBy()
    {
        return getElementsText(UsCaseInformationFragmentElementsAngular.signedOutBy);
    }

    public String getCaseSerial()
    {
        return getElementsText(UsCaseInformationFragmentElementsAngular.caseSerial);
    }

    public void waitUntilCaseSerialChangesToFollowing(String newCaseSerial)
    {
        waitUntilElementsTextIsTheFollowing(UsCaseInformationFragmentElementsAngular.CASE_SERIAL, newCaseSerial);
    }
}
