package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public interface TestCaseResult {
	public void putTo(ITestModelUpdater updater);
	public boolean needMoreInfo();
	public void parseAdditionalLine(String string, CppUTestOutputParser parser);
}
