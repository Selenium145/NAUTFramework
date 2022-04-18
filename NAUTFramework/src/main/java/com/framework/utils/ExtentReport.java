package com.framework.utils;

import java.util.LinkedList;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;


public class ExtentReport implements Formatter,Reporter{
	
	static ExtentReports extent;
	ExtentTest extentTest;
	ExtentTest extentStep;
	LinkedList<Step> testSteps = new LinkedList<Step>();
	
	public static void initializeExtentReport() {		
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentSpark/ExtentHtmlReport.xml");
		extent.attachReporter(spark);		
	}
	
	public void createFeature(String feature) {
		extentTest = extent.createTest(feature);
		
	}
	
	public void createScenario(String scenario) {
		extentStep = extentTest.createNode(scenario);		
	}
	
	public void flushReport() {
		extent.flush();
	}
	
	public void passed(String description) {
		extentStep.pass(description);
	}
	
	public void failed(String description) {
		extentStep.fail(description);
	}
	
	public void info(String description) {
		extentStep.info(description);
	}
	
	public void warning(String description) {
		extentStep.warning(description);
	}
	
	public void skipped(String description) {
		extentStep.skip(description);
	}

	@Override
	public void before(Match match, Result result) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void result(Result result) {
		Step step = testSteps.poll();
		if(result.getStatus().equals("passed")) {
			passed(step.getKeyword()+" "+step.getName());
		}
		else if(result.getStatus().equals("failed")) {
			failed(step.getKeyword()+" "+step.getName());
		}
		else if(result.getStatus().equals("info")) {
			info(step.getKeyword()+" "+step.getName());
		}
		else if(result.getStatus().equals("warning")) {
			warning(step.getKeyword()+" "+step.getName());
		}
		else if(result.getStatus().equals("skip")) {
			skipped(step.getKeyword()+" "+step.getName());
		}
	}

	@Override
	public void after(Match match, Result result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void match(Match match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void embedding(String mimeType, byte[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uri(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void feature(Feature feature) {
		createFeature(feature.getName());
		
	}

	@Override
	public void scenarioOutline(ScenarioOutline scenarioOutline) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void examples(Examples examples) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startOfScenarioLifeCycle(Scenario scenario) {
		createScenario(scenario.getName());		
	}

	@Override
	public void background(Background background) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scenario(Scenario scenario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step(Step step) {
		testSteps.add(step);
		
	}

	@Override
	public void endOfScenarioLifeCycle(Scenario scenario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eof() {
		flushReport();		
	}
}
