package org.sharon.cpputest;


public class TestCaseFactoryImp implements TestCaseFactory {
	CppUTestOutputParser parser;
	
	public TestCaseFactoryImp(CppUTestOutputParser parser){
		this.parser = parser;
	}

	@Override
	public TestCaseResult createTestCase(String line) {
		if (parser.cotainsTestCaseName(line)){
			return new TestCaseResultImp(line, parser);
		}
		return new NullTestCaseResult();
	}
}
