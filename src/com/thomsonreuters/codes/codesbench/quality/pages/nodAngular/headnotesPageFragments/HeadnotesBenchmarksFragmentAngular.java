package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HeadnotesBenchmarksFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class HeadnotesBenchmarksFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public HeadnotesBenchmarksFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HeadnotesBenchmarksFragmentElementsAngular.class);
    }

    public int getBenchmarksClassifications()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.classifications.getText());
    }

    public int getBenchmarksHeadnotes()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.headnotes.getText());
    }

    public int getBenchmarksAiIgnoredHN()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.aiIgnoredHn.getText());
    }

    public int getBenchmarksHNClassified()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.hnClassified.getText());
    }

    public int getBenchmarksHNIgnored()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.hnIgnored.getText());
    }

    public int getBenchmarksHNNotReviewed()
    {
        return Integer.parseInt(HeadnotesBenchmarksFragmentElementsAngular.hnNotReviewed.getText());
    }

    public List<Integer> getAllBenchmarks()
    {
        int benchmarkClassifications = headnotesBenchmarksFragmentAngular().getBenchmarksClassifications();
        int benchmarkHeadnotes = headnotesBenchmarksFragmentAngular().getBenchmarksHeadnotes();
        int benchmarkAiIgnoredHN = headnotesBenchmarksFragmentAngular().getBenchmarksAiIgnoredHN();
        int benchmarkHNClassified = headnotesBenchmarksFragmentAngular().getBenchmarksHNClassified();
        int benchmarkHNIgnored = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewed = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        return Arrays.asList(benchmarkClassifications, benchmarkHeadnotes, benchmarkAiIgnoredHN, benchmarkHNClassified, benchmarkHNIgnored, benchmarkHNNotReviewed);
    }

    //maybe keep it?
//    public List<Integer> countActualBenchmarks()
//    {
//        List<Integer> result = new ArrayList<>();
//        int classifications = getElements(HeadnotesClassificationFragmentElementsAngular.ALL_HEADNOTES_CLASSIFICATIONS).size();
//        int totalHeadnotes = getElements(HeadnotesClassificationFragmentElementsAngular.HEADNOTE_ROW).size();
//        int hnIgnored = getElements(HeadnotesClassificationFragmentElementsAngular.ALL_UNIGNORE_BUTTONS).size();
//        int hnClassified = getElements(HeadnotesClassificationFragmentElementsAngular.ALL_INNER_CLASSIFICATION_TABLES).size();
//        int hnNotReviewed = totalHeadnotes - hnIgnored - hnClassified;
//        result.add(classifications);
//        result.add(totalHeadnotes);
//        result.add(hnClassified);
//        result.add(hnIgnored);
//        result.add(hnNotReviewed);
//        return result;
//    }


}
