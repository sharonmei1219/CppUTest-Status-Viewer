package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {

	private String testCaseName;
	private int testingTime = 0;
	private ITestItem.Status testingStatus = ITestItem.Status.Passed;;

	private AdditionalInfo extraInfo = new AdditionalInfo();

	public TestCaseResultImp(String line) {
		testCaseName = extractTestCaseName(line);
		if(isCaseInfoComplete(line)){
			extraInfo.done();
			testingTime = extractTestingTime(line);
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
		if (isCaseInfoComplete(line))
			extraInfo.done();

		if (!extraInfo.isDone())
			extraInfo.add(line);
		else
			testingTime = extractTestingTime(line);
	}


	private int extractTestingTime(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		if (line.matches(pattern)) {
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
