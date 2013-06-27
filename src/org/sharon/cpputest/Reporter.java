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
	private TestCaseFactory testCaseFactory;

	public Reporter(ITestModelUpdater unitTestDashBoard, InputStream testResultStreamFromConsole, TestCaseFactory factory) {
		this.testCaseFactory = factory;
		this.testResultStream = testResultStreamFromConsole;
		this.testDashBoard = unitTestDashBoard;
	}

	public void reportTestResult() {

		InputStreamReader streamReader = new InputStreamReader(testResultStream);
		BufferedReader reader = new BufferedReader(streamReader);
        String line;
        
        try {
			while ( ( line = reader.readLine() ) != null ) {
				TestCaseResult tcResult = testCaseFactory.createTestCase(line);
				while( (tcResult.needMoreInfo()) && (line = reader.readLine()) != null){
					tcResult.addMoreInfo(line);
				}
				tcResult.putTo(testDashBoard);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
