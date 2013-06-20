package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultParser {
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

	public void putTo(ITestModelUpdater updater) {
		if (testCaseName.equals("")) return;
		updater.enterTestCase(testCaseName);
		updater.setTestStatus(ITestItem.Status.Passed);
		updater.exitTestCase();
	}
}
