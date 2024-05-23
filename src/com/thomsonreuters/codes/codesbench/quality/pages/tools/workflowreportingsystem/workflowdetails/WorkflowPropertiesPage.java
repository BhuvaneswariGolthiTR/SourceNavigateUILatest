package com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements.Property;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements.PROPERTY_VALUE;

@Component
public class WorkflowPropertiesPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public WorkflowPropertiesPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }
    
        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, PropertiesElements.class);
        }

        public void clickWorkflowPropertiesButton()
        {
                logger.information("Click Workflow Properties button");
                click(PropertiesElements.WORKFLOW_PROPERTIES_BUTTON);
        }

        public String getPropertyValue(Property property)
        {
                return getElementsText(String.format(PROPERTY_VALUE, property.getName()));
        }

        public String getXmlExtractValue(String propertyName)
        {
            String workflowPropertyNameXpath = String.format(WorkflowSearchPageElements.PROPERTY_NAME_XPATH, propertyName);
            String workflowPropertyNameValueXpath = workflowPropertyNameXpath;
            workflowPropertyNameValueXpath += WorkflowSearchPageElements.PROPERTY_LOG_VALUE_XPATH;
            workflowPropertyNameValueXpath += WorkflowSearchPageElements.XML_EXTRACT_VALUE_XPATH;
            return getElementsText(workflowPropertyNameValueXpath);
        }
        
        public String getWorkflowPropertyValueByName(String propertyName)
    	{
    		String workflowPropertyNameXpath = String.format(WorkflowSearchPageElements.PROPERTY_NAME_XPATH, propertyName);
    		String workflowPropertyNameValueXpath = workflowPropertyNameXpath;
    		if(propertyName.equals("_log"))
    		{
    			workflowPropertyNameValueXpath += WorkflowSearchPageElements.PROPERTY_LOG_VALUE_XPATH;
    		}
    		else
    		{
    			workflowPropertyNameValueXpath += WorkflowSearchPageElements.PROPERTY_GENERIC_VALUE_XPATH;
    		}

    		return getElementsText(workflowPropertyNameValueXpath);
    	}

    	public String getHistoricalIdSet()
        {
            return getElementsText(WorkflowSearchPageElements.updatedHistoricalNodesAddedIdSetXpath).toLowerCase();
        }
}
