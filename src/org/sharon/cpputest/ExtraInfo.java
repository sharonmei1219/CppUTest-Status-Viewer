package org.sharon.cpputest;

import java.util.ArrayList;
import org.eclipse.cdt.testsrunner.model.ITestMessage;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class ExtraInfo {

	private boolean testInfoComplete;
	private ArrayList<String> details = new ArrayList<String>();
	private String generalError;
	private String fileName;
	private int lineNumber;

	public void done() {
		testInfoComplete = true;
	}

	public boolean isDone() {
		return testInfoComplete;
	}

	public void putTo(ITestModelUpdater dashBoard) {
		putGeneralError(dashBoard);
		putDetailedInfo(dashBoard);
	}

	public void add(String line) {
		if (containsErrorInfo(line))
			getGeneralError(line);
		else
			getDetails(line);
	}

	private boolean containsErrorInfo(String line) {
		return line.matches("(.*)(:)([0-9]*)(: error:.*)");
	}

	private void putGeneralError(ITestModelUpdater dashBoard) {
		dashBoard.addTestMessage(fileName, lineNumber,
				ITestMessage.Level.Error, generalError);
	}

	private void putDetailedInfo(ITestModelUpdater dashBoard) {
		for (int i = 0; i < details.size(); i++)
			dashBoard.addTestMessage("", 0, ITestMessage.Level.Message,
					details.get(i));
	}

	private void getDetails(String line) {
		details.add(line);
	}

	private void getGeneralError(String line) {
		fileName = extractFileName(line);
		lineNumber = extractLineNumber(line);
		generalError = extractError(line);
	}

	private String extractError(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:)(.*)";
		return errorInfo.replaceAll(pattern, "$5");
	}

	private int extractLineNumber(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		return Integer.parseInt(errorInfo.replaceAll(pattern, "$3"));
	}

	private String extractFileName(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		return errorInfo.replaceAll(pattern, "$1");
	}
}
