package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DispositionDerivationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;

@Component
public class DispositionDerivationPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DispositionDerivationPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DispositionDerivationPageElements.class);
    }

    public String getSelectedNodeValue()
    {
        return getElementsText(DispositionDerivationPageElements.selectedNodeTableValue);
    }

    public boolean isNodeValueDisplayed(String nodeValue)
    {
        return doesElementExist(String.format(DispositionDerivationPageElements.GIVEN_NODE_VALUE,nodeValue));
    }

    public void clickExpandButton()
    {
        click(DispositionDerivationPageElements.expandButton);
        waitForPageLoaded();
    }

    public void selectNodeByValue(String nodeValue)
    {
        click(String.format(DispositionDerivationPageElements.GIVEN_NODE_VALUE,nodeValue));
    }

    public void rightClickSelectNodeByNumber(String number)
    {
        rightClick(String.format(DispositionDerivationPageElements.ROW_OF_GRID_BY_NUMBER,number));
    }

    public void clickCommit()
    {
        sendEnterToElement(DispositionDerivationPageElements.commit);
        switchToWindow(SetLawTrackingPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void rightClickNodeByValue(String nodeValue)
    {
        click(String.format(DispositionDerivationPageElements.GIVEN_NODE_VALUE,nodeValue));
        rightClick(String.format(DispositionDerivationPageElements.GIVEN_NODE_VALUE,nodeValue));
    }

    public void rightClickSelectedNode()
    {
        waitForElement(DispositionDerivationPageElements.selectedNodeValue);
        click(DispositionDerivationPageElements.selectedNodeValue);
        rightClick(DispositionDerivationPageElements.selectedNodeValue);
    }

    public void rightClickSelectedNodeImage()
    {
        waitForElement(DispositionDerivationPageElements.selectedNodeImage);
        Actions action = new Actions(driver());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        // Percentage offset assumes 1920 x 1080 pixel resolution (or same proportions)
        action.moveToElement(DispositionDerivationPageElements.selectedNodeImage,(int)(-1 * width * 0.1), (int)(-1 * height * 0.08)).contextClick().build().perform();
    }

    public boolean areMultipleNodesShown()
    {
        return isElementDisplayed(DispositionDerivationPageElements.SECOND_ROW_OF_GRID);
    }

    public boolean isSelectedNodeStatusNotPublished()
    {
        return getElementsAttribute(
                DispositionDerivationPageElements.selectedNodePublishingStatus, "src").contains("Status_WIP_NOT_PUBLISHED");
    }

    public boolean isSelectedNodeSetToGivenPublishingStatus(String publishingStatus)
    {
        return getElementsAttribute(DispositionDerivationPageElements.selectedNodePublishingStatus, "src").contains(publishingStatus);
    }

    public void setStartDate(String date)
    {
        clearAndSendKeysToElement(DispositionDerivationPageElements.selectedNodeStartDateTextBox, date);
    }

    public String getEndDateOfSelectedNode()
    {
        return getElementsAttribute(DispositionDerivationPageElements.selectedNodeEndDateTextBox, "Value");
    }

}
