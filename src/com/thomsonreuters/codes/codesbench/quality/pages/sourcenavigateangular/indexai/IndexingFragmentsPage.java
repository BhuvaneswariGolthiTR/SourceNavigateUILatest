package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static java.lang.String.format;

@Component
public class IndexingFragmentsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexingFragmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexingFragmentsPageElements.class);
    }

    public void clickFragmentOnce(String fragmentName)
    {
        click(format(ANY_FRAGMENT_TEXT, fragmentName));
    }

    public String getSelectedFragmentText()
    {
        return getElementsText(SELECTED_FRAGMENT);
    }

    public void typeTextToLocateFragment(String text)
    {
        sendKeys(text);
        waitForElementExists(format(SELECTED_FRAGMENT_CONTAINS_TEXT, text));
    }

    public void makePause()
    {
        new Actions(driver).pause(Duration.ofSeconds(2)).build().perform();
    }

    public String getFragmentFontWeight(String xpath)
    {
        return driver
                .findElement(By.xpath(xpath))
                .getCssValue("font-weight");
    }

    public String getPreviousFragment()
    {
        List<WebElement> previousFragments = driver.findElements(By.xpath(FRAGMENTS_BEFORE_SELECTED));
        return previousFragments.get(previousFragments.size() - 1).getText();
    }

    public List<String> getListOfFragments(String xpath)
    {
        List<String> fragmentsList = new ArrayList<>();
        driver.findElements(By.xpath(xpath))
                .stream()
                .map(WebElement::getText)
                .forEachOrdered(fragmentsList::add);
        return fragmentsList;
    }

    public int getNumberOfFragments(String xpath)
    {
        return driver.findElements(By.xpath(xpath)).size();
    }

    public String getFragmentTextDecoration(String xpath)
    {
        return driver
                .findElement(By.xpath(xpath))
                .getCssValue("text-decoration-line");
    }

    public void doubleClickFragment(String xpath)
    {
        new Actions(driver)
                .doubleClick(driver.findElement(By.xpath(xpath)))
                .build()
                .perform();
    }

    public void pressEnterFragment(String xpath)
    {
        WebElement fragmentElement = driver.findElement(By.xpath(xpath));

        Actions actions = new Actions(driver);
        actions
                .moveToElement(fragmentElement)
                .click(fragmentElement);
        waitForElementExists(SELECTED_FRAGMENT);
        actions
                .sendKeys(Keys.ENTER)
                .build()
                .perform();
    }

    public void pressArrowDownKey()
    {
        new Actions(driver)
                .sendKeys(Keys.ARROW_DOWN)
                .pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }

    public String getRandomFragmentName()
    {
        return indexingFragmentsPage().getElementsText(format(FRAGMENT_NUMBER, getRandomFragmentNumber()));
    }

    public List<String> makeListOfRandomFragments(int totalNumberOfEntries)
    {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < totalNumberOfEntries; i++)
        {
            list.add(getRandomFragmentName());
        }
        return list;
    }

    public int getRandomFragmentNumber()
    {
        return new Random().nextInt(2125 - 1) + 1;
    }
}
