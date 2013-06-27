package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.eclipse.cdt.testsrunner.model.ITestSuite;

public interface TestCaseResult {
	public void putTo(ITestModelUpdater updater);
	public boolean needMoreInfo();
	public void parseAdditionalLine(String string, CppUTestOutputParser parser);
	public boolean inTheSameSuiteWith(ITestSuite currentTestSuite);
	public String testSuite();
}
