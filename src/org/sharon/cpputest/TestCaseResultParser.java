package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultParser implements TestCaseResult {
	private String testCaseName;
	static int testCaseNumber;

	public TestCaseResultParser(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\)\\W)(.*)";
		String namePositionInPattern = "$5";
		if(line.matches(pattern) != true)
			testCaseName = "";
		else
			testCaseName = line.replaceAll(pattern, namePositionInPattern);
	}

	public void putTo(ITestModelUpdater dashBoard) {
		if (testCaseName.equals("")) return;
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(ITestItem.Status.Passed);
		dashBoard.exitTestCase();
	}
}
