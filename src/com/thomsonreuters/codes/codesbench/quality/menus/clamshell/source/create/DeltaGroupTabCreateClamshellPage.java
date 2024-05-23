package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.create;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.CreateMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractCreateClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupTabCreateClamshellPage extends AbstractCreateClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaGroupTabCreateClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, CreateMenuElements.class);
	}

}
