package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertDeltaPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertDeltaPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertDeltaPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertDeltaPageElements.class);
    }

    public void setAction(String deltaAction)
    {
       // selectDropdownOption(InsertDeltaPageElements.ACTION_DROPDOWN, deltaAction);
        selectDropdownOptionUsingJavascript("pageForm:value0",deltaAction);
    }

    public void setLevel(String deltaLevel)
    {
       // selectDropdownOption(InsertDeltaPageElements.LEVEL_DROPDOWN, deltaLevel);
        selectDropdownOptionUsingJavascript("pageForm:value1",deltaLevel);
    }

    public boolean isTargetLocationCodeSelected()
    {
        return getElement(InsertDeltaPageElements.CODE_CHECKBOX).isSelected();
    }

    public void setTargetLocationCode(boolean checked)
    {
        if ((!isTargetLocationCodeSelected() && checked) || (isTargetLocationCodeSelected() && !checked))
        {
            click(InsertDeltaPageElements.CODE_CHECKBOX);
        }
    }

    public boolean isTargetLocationSectionSelected()
    {
        return getElement(InsertDeltaPageElements.SECTION_CHECKBOX).isSelected();
    }

    public void setTargetLocationSection(boolean checked)
    {
        if ((!isTargetLocationSectionSelected() && checked) || (isTargetLocationSectionSelected() && !checked))
        {
            click(InsertDeltaPageElements.SECTION_CHECKBOX);
        }
    }

    public boolean isTargetLocationSubsectionSelected()
    {
        return getElement(InsertDeltaPageElements.SUBSECTION_CHECKBOX).isSelected();
    }

    public void setTargetLocationSubsection(boolean checked)
    {
        if ((!isTargetLocationSubsectionSelected() && checked) || (isTargetLocationSubsectionSelected() && !checked))
        {
            click(InsertDeltaPageElements.SUBSECTION_CHECKBOX);
        }
    }

    public void clickSave()
    {
        sendEnterToElement(CommonPageElements.SAVE_BUTTON);
    }
}
