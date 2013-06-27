package org.sharon.cpputest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class Reporter {

	private TestCaseFactory testCaseFactory;
	private CppUTestOutputParser parser;

	public Reporter(TestCaseFactory factory, CppUTestOutputParser parser) {
		this.testCaseFactory = factory;
		this.parser = parser;
	}

	public void reportTestResult(ITestModelUpdater dashBoard, InputStream testResultOutputStream) {
		InputStreamReader streamReader = new InputStreamReader(testResultOutputStream);
		BufferedReader reader = new BufferedReader(streamReader);

        String line;
        
        try {
			while ( ( line = reader.readLine() ) != null ) {
				TestCaseResult tcResult = testCaseFactory.createTestCase(line);
				while( (tcResult.needMoreInfo()) && (line = reader.readLine()) != null){
					tcResult.parseAdditionalLine(line, parser);
				}
				tcResult.putTo(dashBoard);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
