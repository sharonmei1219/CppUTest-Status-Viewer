package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sharon.cpputest.CppUTestOutputParser;

public class TestCppUTestOutputParser {
	CppUTestOutputParser parser = new CppUTestOutputParser();

	@Test
	public void testParseIgnore() {
		assertTrue(parser.isTestCaseIgnored("IGNORE_TEST(TestTimer, testTimerExpired) - 0 ms"));
		
	}
	
	@Test
	public void testParseTestSuiteName(){
		assertEquals(parser.extractTestSuiteName("TEST(TestTimer, testTimerExpired) - 0 ms"), "TestTimer");
	}

}
