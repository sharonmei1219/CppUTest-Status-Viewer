package org.sharon.cpputest;

import java.io.InputStream;

import org.eclipse.cdt.testsrunner.launcher.ITestsRunnerProvider;
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
	public void run(ITestModelUpdater dashBoard, InputStream testResultInStream)
			throws TestingException {
		TestCaseFactory factory = new TestCaseFactoryImp();
		Reporter reporter = new Reporter(dashBoard, testResultInStream, factory);
		reporter.reportTestResult();
	}
}
