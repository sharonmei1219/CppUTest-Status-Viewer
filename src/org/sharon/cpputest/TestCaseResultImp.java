package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {
	
	private String testCaseName;
	static int testCaseNumber;
	private boolean caseInfoComplete;
	private int testingTime;
	
	public TestCaseResultImp(String line) {
		testCaseName = extractTestCaseName(line);
		caseInfoComplete = isCaseInfoComplete(line);
		testingTime = extractTestingTime(line);
	}

	public void putTo(ITestModelUpdater dashBoard) {
		if (testCaseName.equals("")) return;
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(ITestItem.Status.Passed);
		dashBoard.setTestingTime(testingTime);
		dashBoard.exitTestCase();
	}

	@Override
	public boolean needMoreInfo() {
		return !caseInfoComplete;
	}

	@Override
	public void read(String line) {
		caseInfoComplete = isCaseInfoComplete(line);
	}
	
	private int extractTestingTime(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		if(line.matches(pattern)){
			return Integer.parseInt(line.replaceAll(pattern, "$3"));
		}
		return -1;
	}
	
	private String extractTestCaseName(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\)\\W)(.*)";
		
		if(line.matches(pattern) != true)
			return "";

		return line.replaceAll(pattern, "$5");

	}

	private boolean isCaseInfoComplete(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		return line.matches(pattern);
	}
}
