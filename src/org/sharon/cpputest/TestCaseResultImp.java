package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {
	
	private String testCaseName;
	static int testCaseNumber;
	private boolean testInfoComplete;
	private int testingTime;
	ITestItem.Status testingStatus;
	
	public TestCaseResultImp(String line) {
		testingStatus = ITestItem.Status.Passed;
		testCaseName = extractTestCaseName(line);
		testInfoComplete = isCaseInfoComplete(line);
		testingTime = extractTestingTime(line);
	}

	public void putTo(ITestModelUpdater dashBoard) {
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(testingStatus);
		dashBoard.setTestingTime(testingTime);
		dashBoard.exitTestCase();
	}

	@Override
	public boolean needMoreInfo() {
		return !testInfoComplete;
	}

	@Override
	public void read(String line) {
		testingStatus = ITestItem.Status.Failed;
		testInfoComplete = isCaseInfoComplete(line);
		testingTime = extractTestingTime(line);
	}
	
	private int extractTestingTime(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		if(line.matches(pattern)){
			return Integer.parseInt(line.replaceAll(pattern, "$3"));
		}
		return -1;
	}
	
	private String extractTestCaseName(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\))(.*)";
		return line.replaceAll(pattern, "$5");

	}

	private boolean isCaseInfoComplete(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		return line.matches(pattern);
	}
}
