package org.sharon.cpputest;

public class CppUTestOutputParser {

	private String TestCaseTitlePattern = "(.*)(TEST\\()(.*)(, )(.*)(\\))(.*)";
	private String TestingTimePattern = "(.*)( - )(.*)( ms)$";
	private String ErrorInfoPattern = "(.*)(:)([0-9]*)(: error:)(.*)";

	public String extractTestCaseName(String line) {
		return line.replaceAll(TestCaseTitlePattern, "$5");
	}
	
	public String extractTestSuiteName(String line) {
		return line.replaceAll(TestCaseTitlePattern, "$3");
	}

	boolean cotainsTestCaseName(String line) {
		return line.matches(TestCaseTitlePattern);
	}

	public boolean containsTestingTime(String line) {
		return line.matches(TestingTimePattern);
	}

	public int extractTestingTime(String line) {
		return Integer.parseInt(line.replaceAll(TestingTimePattern, "$3"));
	}

	public boolean containsErrorInfo(String line) {
		return line.matches(ErrorInfoPattern);
	}

	public String extractError(String errorInfo) {
		return errorInfo.replaceAll(ErrorInfoPattern, "$5");
	}

	public int extractLineNumber(String errorInfo) {
		return Integer.parseInt(errorInfo.replaceAll(ErrorInfoPattern, "$3"));
	}

	public String extractFileName(String errorInfo) {
		return errorInfo.replaceAll(ErrorInfoPattern, "$1");
	}

	public boolean isTestCaseIgnored(String string) {
		String pattern = "IGNORE_TEST.*";
		return string.matches(pattern);
	}


}
