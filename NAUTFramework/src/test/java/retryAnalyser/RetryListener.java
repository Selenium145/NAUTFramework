package retryAnalyser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;


public class RetryListener implements IAnnotationTransformer{
	
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		Class<? extends IRetryAnalyzer> retry = annotation.getRetryAnalyzerClass();		
		if (retry == null) {
			annotation.setRetryAnalyzer(RetryFailedTests.class);
		}
		annotation.setRetryAnalyzer(RetryFailedTests.class);
	}
	
	

}
