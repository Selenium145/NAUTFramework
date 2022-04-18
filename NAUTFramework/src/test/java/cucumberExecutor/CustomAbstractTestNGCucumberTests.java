package cucumberExecutor;

import java.util.ArrayList;
import java.util.List;

import org.apiguardian.api.API;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.framework.utils.GlobalVariables;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

/**
 * Abstract TestNG Cucumber Test
 * <p>
 * Runs each cucumber scenario found in the features as separated test.
 *
 * @see TestNGCucumberRunner
 */
@API(status = API.Status.STABLE)
public abstract class CustomAbstractTestNGCucumberTests{

	private TestNGCucumberRunner testNGCucumberRunner;
		
	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@SuppressWarnings("unused")
	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		// the 'featureWrapper' parameter solely exists to display the feature
		// file in a test report
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}
	
	/**
     * Returns two dimensional array of {@link PickleWrapper}s with their
     * associated {@link FeatureWrapper}s.
     *
     * @return a two dimensional array of scenarios features.
     */
    @DataProvider
    public Object[][] scenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return filterTheFeature(testNGCucumberRunner.provideScenarios());
    }

	/*
	 * Method to return array list of feature file, which we want to execute
	 */

	private Object[][] filterTheFeature(Object[][] data) {
		
	    List<Object[]> customObj = new ArrayList<Object[]>();	    		
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				PickleWrapper pickleWrapper = (PickleWrapper)data[i][0];
				String uri = pickleWrapper.getPickle().getUri().toString();				
				System.out.println(uri.substring(uri.lastIndexOf('/')+1, uri.indexOf('.'))+" -> "
						+pickleWrapper.getPickle().getTags().toString());
				if (GlobalVariables.featureList.contains(uri.substring(uri.lastIndexOf('/')+1, uri.indexOf('.')))) {									
					customObj.add(data[i]);
				}
			}			
		}		
		Object[][] exeScenarios = new Object[customObj.size()][2];
		for(int i=0; i<customObj.size(); i++) {
			exeScenarios[i] = customObj.get(i); 
		}
		return exeScenarios;
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();
	}

}
