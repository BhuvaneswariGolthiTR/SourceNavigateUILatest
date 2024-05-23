package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions.AdminOpinionsContextMenuElementsAngular;
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
public class AdminOpinionsContextMenuAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public AdminOpinionsContextMenuAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AdminOpinionsContextMenuElementsAngular.class);
    }

    public List<String> getContextMenuOptionsTexts()
    {
        List<WebElement> contextMenuOptions = headnotesPageAngular().getElements(AdminOpinionsContextMenuElementsAngular.CONTEXT_MENU_OPTION);
        return contextMenuOptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectOptionByText(String contextMenuOption)
    {
        click(String.format(AdminOpinionsContextMenuElementsAngular.CONTEXT_MENU_OPTION_WITH_TEXT, contextMenuOption));
    }

    public void selectDeleteOpinion()
    {
        click(AdminOpinionsContextMenuElementsAngular.deleteOpinion);
    }

}
