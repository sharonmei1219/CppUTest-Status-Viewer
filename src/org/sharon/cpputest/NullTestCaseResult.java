package org.sharon.cpputest;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;

public class NullTestCaseResult implements TestCaseResult {

	@Override
	public void putTo(ITestModelUpdater updater) {
	}

	@Override
	public boolean needMoreInfo() {
		return false;
	}

	@Override
	public void read(String string) {
	}

}
