package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularViewManagerPageElements
{
    // *** View Names that are used for automated testing ***
    public static final String FIND_TEST_VIEW_DROPDOWN = "TestView--Find";
    public static final String INDEX_AI_TEST_VIEW_DROPDOWN = "TestView--IndexAIEntryCompletedBy";
    public static final String DEFAULT_VIEW_DROPDOWN = "Default view";
    public static final String DEFAULT_VIEW_BOTTOM = "default";
    public static final String VIEW_DROPDOWN_ITEM = "//strong[text()='%s']";
    public static final String ZERO_FILE_SIZE_DROPDOWN = "ZeroFileSize";
    @FindBy(how = How.XPATH, using = SOURCE_NAV_RENDITIONS + "//div[@class='view-manager']//input")
    public static WebElement viewDropdown;

    @FindBy(how = How.XPATH, using = SOURCE_NAV_RENDITIONS + "//button[text()='Apply View']")
    public static WebElement applyViewButton;
}
