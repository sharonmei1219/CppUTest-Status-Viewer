package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {

	private String testCaseName;
	private int testingTime = 0;
	private ITestItem.Status testingStatus = ITestItem.Status.Passed;;

	private AdditionalInfo extraInfo = new AdditionalInfo();
	private CppUTestOutputParser parser = new CppUTestOutputParser();

	public TestCaseResultImp(String line) {
		testCaseName = parser.extractTestCaseName(line);
		if(parser.containsTestingTime(line)){
			extraInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
	}

	public void putTo(ITestModelUpdater dashBoard) {
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(testingStatus);
		if (testingStatus == ITestItem.Status.Failed) {
			extraInfo.putTo(dashBoard);
		}
		dashBoard.setTestingTime(testingTime);
		dashBoard.exitTestCase();
	}

	@Override
	public boolean needMoreInfo() {
		return !extraInfo.isDone();
	}

	@Override
	public void addMoreInfo(String line) {
		testingStatus = ITestItem.Status.Failed;
		
		if (parser.containsTestingTime(line)){
			extraInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
		else
			extraInfo.add(line);
	}
}
