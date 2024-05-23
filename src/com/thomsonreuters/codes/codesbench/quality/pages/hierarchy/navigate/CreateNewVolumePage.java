package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CreateNewVolumePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

@Component
public class CreateNewVolumePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateNewVolumePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateNewVolumePageElements.class);
    }

    public void setVolumeNumber(String volumeNumber)
    {
        clearAndSendKeysToElement(CreateNewVolumePageElements.volumeNumber, volumeNumber);
    }

    public void setTitle(String volumeTitle)
    {
        clearAndSendKeysToElement(CreateNewVolumePageElements.volumeTitle, volumeTitle);
    }

    public void clickSubmit()
    {
        click(CreateNewVolumePageElements.submit);
    }

    public void clickCancel()
    {
        click(CreateNewVolumePageElements.cancel);
    }

    public String getVolumeNumberFromInputField()
    {
        return getVolumeNumberAndTitleFromInputFieldHelper(CreateNewVolumePageElements.volumeNumber);
    }

    public String getVolumeTitleFromInputField()
    {
        return getVolumeNumberAndTitleFromInputFieldHelper(CreateNewVolumePageElements.volumeTitle);
    }

    private String getVolumeNumberAndTitleFromInputFieldHelper(WebElement element)
    {
        element.click();
        element.sendKeys(Keys.END);
        element.sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME));
        element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
        try
        {
            return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        }
        catch(UnsupportedFlavorException | IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
