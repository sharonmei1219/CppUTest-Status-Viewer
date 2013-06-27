package org.sharon.cpputest;


public class TestCaseFactoryImp implements TestCaseFactory {

	@Override
	public TestCaseResult createTestCase(String line) {
		if (!cotainTestCaseName(line)){
			return new NullTestCaseResult();
		}
		return new TestCaseResultImp(line);
	}

	private boolean cotainTestCaseName(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\))(.*)";
		return line.matches(pattern);
	}
}
