package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ImportLtcNovusLoadFilePopupsElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ImportLtcNovusLoadFilePopupsAngular extends BasePage
{
    @Autowired
    public ImportLtcNovusLoadFilePopupsAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ImportLtcNovusLoadFilePopupsElementsAngular.class);
    }

    
    public void selectFileByName(String name)
    {
        importLtcNovusLoadFilePopupsAngular().getElement(String.format(ImportLtcNovusLoadFilePopupsElementsAngular.FILE_BY_NAME, name)).click();
    }

    
    public void clickLoadCanadaFiles()
    {
        ImportLtcNovusLoadFilePopupsElementsAngular.loadCanadaFiles.click();
    }

    
    public void clickClose()
    {
        ImportLtcNovusLoadFilePopupsElementsAngular.close.click();
    }

}
