package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesBenchmarksFragmentElementsAngular
{
    public static final String HEADNOTE_BENCHMARKS = "//table[@id = 'headnoteBenchmarks']";

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[1]")
    public static WebElement classifications;

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[2]")
    public static WebElement headnotes;

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[3]")
    public static WebElement aiIgnoredHn;

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[4]")
    public static WebElement hnClassified;

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[5]")
    public static WebElement hnIgnored;

    @FindBy(how = How.XPATH, using = HEADNOTE_BENCHMARKS + "//tbody/tr/td[6]")
    public static WebElement hnNotReviewed;
}
