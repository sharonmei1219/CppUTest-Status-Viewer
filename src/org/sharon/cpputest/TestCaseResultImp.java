package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {

	private String testCaseName;
	private int testingTime = 0;
	private ITestItem.Status testingStatus = ITestItem.Status.Passed;;

	private AdditionalInfo additionalInfo = new AdditionalInfo();
	private CppUTestOutputParser parser;

	public TestCaseResultImp(String line, CppUTestOutputParser parser) {
		this.parser = parser;
		testCaseName = parser.extractTestCaseName(line);
		if(parser.containsTestingTime(line)){
			additionalInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
	}

	public void putTo(ITestModelUpdater dashBoard) {
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(testingStatus);
		if (testingStatus == ITestItem.Status.Failed) {
			additionalInfo.putTo(dashBoard);
		}
		dashBoard.setTestingTime(testingTime);
		dashBoard.exitTestCase();
	}

	@Override
	public boolean needMoreInfo() {
		return !additionalInfo.isDone();
	}

	@Override
	public void parseAdditionalLine(String line, CppUTestOutputParser parser) {
		testingStatus = ITestItem.Status.Failed;
		
		if (parser.containsTestingTime(line)){
			additionalInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
		else
			additionalInfo.parseLine(line, parser);
	}
}
