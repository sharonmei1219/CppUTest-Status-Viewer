package org.sharon.cpputest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class Reporter {

	private InputStream testResultStream;
	private ITestModelUpdater testDashBoard;

	public Reporter(ITestModelUpdater unitTestDashBoard, InputStream testResultStreamFromConsole) {
		this.testResultStream = testResultStreamFromConsole;
		this.testDashBoard = unitTestDashBoard;
	}

	public void reportTestResult() {

		InputStreamReader streamReader = new InputStreamReader(testResultStream);
		BufferedReader reader = new BufferedReader(streamReader);
        String line;
        
        try {
			while ( ( line = reader.readLine() ) != null ) {
				TestCaseResult testCaseResult = new TestCaseResultParser(line);
				testCaseResult.putTo(testDashBoard);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
