package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.validate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ValidateMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractValidateClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LineageTabValidateClamshellPage  extends AbstractValidateClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public LineageTabValidateClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ValidateMenuElements.class);
	}
}

