package org.sharon.cpputest;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.cdt.testsrunner.model.ITestCase;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestMessage;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class TestCaseResultImp implements TestCaseResult {
	
	private String testCaseName;
	static int testCaseNumber;
	private boolean testInfoComplete;
	private int testingTime;
	ITestItem.Status testingStatus;
	ArrayList <String> errorMessage = new ArrayList <String>();
	
	public TestCaseResultImp(String line) {
		testingStatus = ITestItem.Status.Passed;
		testCaseName = extractTestCaseName(line);
		testInfoComplete = isCaseInfoComplete(line);
		testingTime = extractTestingTime(line);
	}

	public void putTo(ITestModelUpdater dashBoard) {
		dashBoard.enterTestCase(testCaseName);
		dashBoard.setTestStatus(testingStatus);
		if(testingStatus == ITestItem.Status.Failed){
			String errorInfo = errorMessage.get(0);
			String fileName = extractFileName(errorInfo);
			int lineNumber = extractLineNumber(errorInfo);
			dashBoard.addTestMessage(fileName, lineNumber, ITestMessage.Level.Error, errorMessage.get(0));
			for (int i = 1; i < errorMessage.size(); i++)
				dashBoard.addTestMessage("", 0, ITestMessage.Level.Message, errorMessage.get(i));
		}
		dashBoard.setTestingTime(testingTime);
		dashBoard.exitTestCase();
	}
	

	private int extractLineNumber(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		if(errorInfo.matches(pattern))
			return Integer.parseInt(errorInfo.replaceAll(pattern, "$3"));
		return 0;
	}

	private String extractFileName(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		if (errorInfo.matches(pattern))
			return errorInfo.replaceAll(pattern, "$1");
		return "";
	}

	@Override
	public boolean needMoreInfo() {
		return !testInfoComplete;
	}

	@Override
	public void read(String line) {
		testingStatus = ITestItem.Status.Failed;
		testInfoComplete = isCaseInfoComplete(line);
		
		if(!testInfoComplete)
			errorMessage.add(line);
		else
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
