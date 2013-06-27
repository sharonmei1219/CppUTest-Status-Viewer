package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.eclipse.cdt.testsrunner.model.ITestSuite;

public class TestCaseResultImp implements TestCaseResult {

	private String testCaseName;
	private String testSuiteName;
	private int testingTime = 0;
	private ITestItem.Status testingStatus = ITestItem.Status.Passed;;

	private AdditionalInfo additionalInfo = new AdditionalInfo();

	public TestCaseResultImp(String line, CppUTestOutputParser parser) {
		testCaseName = parser.extractTestCaseName(line);
		testSuiteName = parser.extractTestSuiteName(line);
		if(endOfTestCaseInfo(line, parser)){
			additionalInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
		if(parser.isTestCaseIgnored(line))
			testingStatus = ITestItem.Status.Skipped;
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
		if (parser.containsErrorInfo(line))
			testingStatus = ITestItem.Status.Failed;
		
		if (endOfTestCaseInfo(line, parser)){
			additionalInfo.done();
			testingTime = parser.extractTestingTime(line);
		}
		else
			additionalInfo.parseLine(line, parser);
	}

	private boolean endOfTestCaseInfo(String line, CppUTestOutputParser parser) {
		return parser.containsTestingTime(line);
	}

	@Override
	public boolean inTheSameSuiteWith(ITestSuite testSuite) {
		return testSuiteName.equals(testSuite.getName());
	}

	@Override
	public String testSuite() {
		return testSuiteName;
	}
}
