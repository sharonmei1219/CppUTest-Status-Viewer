package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.eclipse.cdt.testsrunner.model.ITestSuite;

public class NullTestCaseResult implements TestCaseResult {

	@Override
	public void putTo(ITestModelUpdater updater) {
	}

	@Override
	public boolean needMoreInfo() {
		return false;
	}

	@Override
	public void parseAdditionalLine(String string, CppUTestOutputParser parser) {
	}

	@Override
	public boolean inTheSameSuiteWith(ITestSuite currentTestSuite) {
		return true;
	}

	@Override
	public String testSuite() {
		return null;
	}

}
