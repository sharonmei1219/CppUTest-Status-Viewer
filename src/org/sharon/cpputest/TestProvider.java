package org.sharon.cpputest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.cdt.testsrunner.launcher.ITestsRunnerProvider;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.eclipse.cdt.testsrunner.model.TestingException;

public class TestProvider implements ITestsRunnerProvider {

	@Override
	public String[] getAdditionalLaunchParameters(String[][] testPaths)
			throws TestingException {
		final String [] result = {"-v"}; 
		return result;
	}

	@Override
	public void run(ITestModelUpdater modelUpdater, InputStream inputStream)
			throws TestingException {
		StatusReporter testStatusUpdater = new StatusReporter(inputStream, modelUpdater);
		testStatusUpdater.updateStatus();
	}
}
