package org.sharon.cpputest;


public class TestCaseFactoryImp implements TestCaseFactory {

	@Override
	public TestCaseResult createTestCase(String line) {
		System.out.println("\"" + line + "\"");
		if (!cotainTestCaseName(line))
		{
			System.out.println("contains no name, will return null obj");
			return new NullTestCaseResult();
		}
		return new TestCaseResultImp(line);
	}

	private boolean cotainTestCaseName(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\))(.*)";
		return line.matches(pattern);
	}
}
