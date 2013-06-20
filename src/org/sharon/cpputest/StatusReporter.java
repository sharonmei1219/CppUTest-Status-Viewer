package org.sharon.cpputest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class StatusReporter {

	private InputStream inputStream;
	private ITestModelUpdater updater;

	public StatusReporter(InputStream inputStream, ITestModelUpdater updater) {
		this.inputStream = inputStream;
		this.updater = updater;
	}

	public void updateStatus() {

		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(streamReader);
        String line;
        try {
			while ( ( line = reader.readLine() ) != null ) {
				System.out.println(line);
				TestCaseResultParser testCaseStatus = new TestCaseResultParser(line);
				testCaseStatus.putTo(updater);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
