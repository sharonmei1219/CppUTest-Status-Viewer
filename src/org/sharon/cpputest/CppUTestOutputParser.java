package org.sharon.cpputest;

public class CppUTestOutputParser {

	public String extractTestCaseName(String line) {
		String pattern = "(.*)(TEST\\()(.*)(, )(.*)(\\))(.*)";
		return line.replaceAll(pattern, "$5");
	}

	public int extractTestingTime(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		if (line.matches(pattern)) {
			return Integer.parseInt(line.replaceAll(pattern, "$3"));
		}
		return -1;
	}

	public boolean containsTestingTime(String line) {
		String pattern = "(.*)( - )(.*)( ms)$";
		return line.matches(pattern);
	}

	public boolean containsErrorInfo(String line) {
		return line.matches("(.*)(:)([0-9]*)(: error:.*)");
	}

	public String extractError(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:)(.*)";
		return errorInfo.replaceAll(pattern, "$5");
	}

	public int extractLineNumber(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		return Integer.parseInt(errorInfo.replaceAll(pattern, "$3"));
	}

	public String extractFileName(String errorInfo) {
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		return errorInfo.replaceAll(pattern, "$1");
	}

}
