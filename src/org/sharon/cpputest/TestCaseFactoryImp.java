package org.sharon.cpputest;


public class TestCaseFactoryImp implements TestCaseFactory {
	CppUTestOutputParser parser = new CppUTestOutputParser();

	@Override
	public TestCaseResult createTestCase(String line) {
		if (parser.cotainsTestCaseName(line)){
			return new TestCaseResultImp(line);
		}
		return new NullTestCaseResult();
	}
}
