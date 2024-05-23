package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ToolsGridPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ToolsGridPopupAngular extends BasePage
{
    @Autowired
    public ToolsGridPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ToolsGridPopupElementsAngular.class);
    }

    public String getNumberNonSelectedVolume()
    {
        return ToolsGridPopupElementsAngular.numberNonSelectedVolumes.getText();
    }

    public String getNumberSelectedVolume()
    {
        return ToolsGridPopupElementsAngular.numberSelectedVolumes.getText();
    }

    public void clickAdd()
    {
        ToolsGridPopupElementsAngular.add.click();
        waitForPageLoaded();
    }

    public void clickRemove()
    {
        ToolsGridPopupElementsAngular.remove.click();
        waitForPageLoaded();
    }

    public void setNonSelectedFilterList(String phrase)
    {
        ToolsGridPopupElementsAngular.nonSelectedFilterList.clear();
        ToolsGridPopupElementsAngular.nonSelectedFilterList.sendKeys(phrase);
    }

    public String getNonSelectedFilterList()
    {
        return ToolsGridPopupElementsAngular.nonSelectedFilterList.getText();
    }

    public String getSelectedFilterList()
    {
        return ToolsGridPopupElementsAngular.selectedFilterList.getText();
    }

    public void setSelectedFilterList(String phrase)
    {
        ToolsGridPopupElementsAngular.selectedFilterList.clear();
        ToolsGridPopupElementsAngular.selectedFilterList.sendKeys(phrase);
    }

    public void clearSelectedFilterList()
    {
        click(ToolsGridPopupElementsAngular.SELECTED_FILTER_LIST_X);
    }

    public void selectByNameFromNonSelectedList(String phrase)
    {
        if (!isElementSelected(String.format(ToolsGridPopupElementsAngular.NON_SELECTED_CHECKBOX_BY_VALUE, phrase))) {
            click(String.format(ToolsGridPopupElementsAngular.NON_SELECTED_CHECKBOX_BY_VALUE, phrase));
        }
    }

    public void selectByNameFromSelectedList(String phrase)
    {
        if (!isElementSelected(String.format(ToolsGridPopupElementsAngular.SELECTED_CHECKBOX_BY_VALUE, phrase))) {
            click(String.format(ToolsGridPopupElementsAngular.SELECTED_CHECKBOX_BY_VALUE, phrase));
        }
    }

    public void checkNonSelectedTitleCheckbox()
    {
        waitForPageLoaded();
        if (!ToolsGridPopupElementsAngular.nonSelectedTitleCheckbox.isSelected()) {
            ToolsGridPopupElementsAngular.nonSelectedTitleCheckbox.click();
        }
    }

    public void checkSelectedTitleCheckbox()
    {
        if (!ToolsGridPopupElementsAngular.selectedTitleCheckbox.isSelected()) {
            ToolsGridPopupElementsAngular.selectedTitleCheckbox.click();
        }
    }

    public boolean isAddButtonDisplayed()
    {return ToolsGridPopupElementsAngular.add.isDisplayed();}

    public boolean isRemoveButtonDisplayed()
    {return ToolsGridPopupElementsAngular.remove.isDisplayed();}

    public List<String> getSelectedVolumesList() {
        List<String> rowsValue = new ArrayList<>();
        getElements(ToolsGridPopupElementsAngular.SELECTED_ROWS).stream().map(WebElement::getText).forEach(rowsValue::add);
        return rowsValue;
    }

    public boolean isElementExistInSelectedListByPartOfName(String partName)
    {
        List<String> rowsValue = new ArrayList<>();
        getElements(ToolsGridPopupElementsAngular.SELECTED_ROWS).stream().map(WebElement::getText).forEach(rowsValue::add);
        return rowsValue.stream().anyMatch(t -> t.toLowerCase().contains(partName));
    }

    public boolean isElementExistInNonSelectedListByPartOfName(String partName)
    {
        List<String> rowsValue = new ArrayList<>();
        getElements(ToolsGridPopupElementsAngular.NON_SELECTED_ROWS).stream().map(WebElement::getText).forEach(rowsValue::add);
        return rowsValue.stream().anyMatch(t -> t.toLowerCase().contains(partName));
    }

    public boolean isOnlyVolumesContainingPatternExistInSelectedList(String partName)
    {
        List<String> rowsValue = new ArrayList<>();
        getElements(ToolsGridPopupElementsAngular.SELECTED_ROWS).stream().map(WebElement::getText).forEach(rowsValue::add);
        return rowsValue.stream().allMatch(t -> t.toLowerCase().contains(partName));
    }

}
