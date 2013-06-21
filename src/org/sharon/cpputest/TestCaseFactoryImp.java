package org.sharon.cpputest;


public class TestCaseFactoryImp implements TestCaseFactory {

	@Override
	public TestCaseResult createTestCase(String line) {
		return new TestCaseResultImp(line);
	}
}
