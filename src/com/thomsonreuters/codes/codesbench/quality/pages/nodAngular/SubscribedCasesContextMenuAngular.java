package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesContextMenuElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscribedCasesContextMenuAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SubscribedCasesContextMenuAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SubscribedCasesContextMenuElementsAngular.class);
    }

    public List<String> getContextMenuOptionsTexts()
    {
        List<WebElement> contextMenuOptions = headnotesPageAngular().getElements(SubscribedCasesContextMenuElementsAngular.CONTEXT_MENU_OPTION);
        return contextMenuOptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectOptionByText(String contextMenuOption)
    {
        click(String.format(SubscribedCasesContextMenuElementsAngular.CONTEXT_MENU_OPTION_WITH_TEXT, contextMenuOption));
    }

    public void selectExportToExcel()
    {
        selectOptionByText("Export to Excel");
    }

    public void selectExportToExcelSelectedRows()
    {
        selectOptionByText("Export to Excel selected rows");
    }

}
