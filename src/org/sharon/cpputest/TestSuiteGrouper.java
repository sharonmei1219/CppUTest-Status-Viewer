package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestSuiteGrouper {
	ITestModelUpdater dashBoard;
	String testSuiteName = "";

	public TestSuiteGrouper(ITestModelUpdater dashBoard) {
		this.dashBoard = dashBoard;
	}

	public void groupTcInTestSuite(TestCaseResult tcResult) {
		String testSuiteNameOfThisTestCase = tcResult.testSuite();
		if(testSuiteName.equals(testSuiteNameOfThisTestCase) || testSuiteNameOfThisTestCase == null)
				return;
		if(!testSuiteName.equals(""))
			dashBoard.exitTestSuite();
		testSuiteName = testSuiteNameOfThisTestCase;
		dashBoard.enterTestSuite(testSuiteNameOfThisTestCase);
	}

	public void close() {
		if(!testSuiteName.equals(""))
			dashBoard.exitTestSuite();
	}

}
