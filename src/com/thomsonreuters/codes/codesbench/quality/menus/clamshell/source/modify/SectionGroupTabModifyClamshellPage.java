package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ModifyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractModifyClamshellPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupTabModifyClamshellPage extends AbstractModifyClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public SectionGroupTabModifyClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ModifyMenuElements.class);
	}

}
