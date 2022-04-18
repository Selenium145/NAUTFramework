package retryAnalyser;

import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.framework.utils.LoggerHelper;

public class RetryFailedTests implements IRetryAnalyzer{
	private Logger log;
	private int retryCount = 0;
	private int maxRetryCount =0;

	@Override
	public boolean retry(ITestResult result) {
		log = LoggerHelper.getLogger(result.getClass());
		//int maxRetryCount = Integer.parseInt(GlobalVariables.configProp.getProperty("retryCount"));
		if (retryCount < maxRetryCount) {
			log.debug("Retrying Method:" + result.getName() + "again and the count is" + (retryCount + 1));
			++retryCount;
			return true;
		} else {
			return false;
		}		
	}

}
