package cucumberExecutor;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.drivers.mobile.MobileDriverManager;
import com.drivers.web.WebDriverManager;
import com.framework.utils.EWSEmailService;
import com.framework.utils.GlobalVariables;
import com.framework.utils.PropertyFileUtils;
import com.testdata.DBManager;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources" }, 
				 glue = {"pageObjects", "cucumberExecutor" }, 
				 dryRun = false, 
				 monochrome = false, 
				 plugin = { "pretty",
						    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",						    
						    "json:target/cucumber-reports/cucumber.json",						    
						    "timeline:target/ParallelTimeline-report"},						    
				 publish = false,
				 tags = "@UI or @API")

public class CucumberRunner extends CustomAbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
	
	@BeforeSuite
	public void initialSetUp() {
		/* Initiating configuration properties file */
		GlobalVariables.configProp = new PropertyFileUtils("config.properties");	

		/* Initiating TestData & Feature file execution list */
		DBManager.InitializeDB(GlobalVariables.configProp.getProperty("DBType"));		
	}
	
	@BeforeTest
	@Parameters("instance")
	public void setTestInstance(String instance) throws Exception {
		if(instance.equalsIgnoreCase("MOBILE")) {
			MobileDriverManager.configureDriver();
		}
		else if (instance.equalsIgnoreCase("API")) {			
		}
		else {
			GlobalVariables.currentBrowser.set(instance);
			WebDriverManager.configureDriver();
		}		
	}	

	@AfterSuite
	public void teardown() {
		// Open html test result - Temporary purpose
		try {			
			Desktop.getDesktop()
					.browse(new File(System.getProperty("user.dir") 
							+ GlobalVariables.configProp.getProperty("reportPath")).toURI());
			EWSEmailService.sendeMail();

		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}