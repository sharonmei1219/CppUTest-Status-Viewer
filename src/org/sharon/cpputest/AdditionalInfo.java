package org.sharon.cpputest;

import java.util.ArrayList;
import org.eclipse.cdt.testsrunner.model.ITestMessage;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class AdditionalInfo {

	private String generalError;
	private String fileName;
	private int lineNumber;

	private ArrayList<String> details = new ArrayList<String>();
	private boolean testInfoComplete;

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

	public void parseLine(String line, CppUTestOutputParser parser) {
		if (parser.containsErrorInfo(line))
			getGeneralError(line, parser);
		else
			getDetails(line);
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

	private void getGeneralError(String line, CppUTestOutputParser parser) {
		fileName = parser.extractFileName(line);
		lineNumber = parser.extractLineNumber(line);
		generalError = parser.extractError(line);
	}
}