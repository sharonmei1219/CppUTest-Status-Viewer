package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sharon.cpputest.CppUTestOutputParser;
import org.sharon.cpputest.NullTestCaseResult;
import org.sharon.cpputest.TestCaseFactory;
import org.sharon.cpputest.TestCaseFactoryImp;
import org.sharon.cpputest.TestCaseResult;
import org.sharon.cpputest.TestCaseResultImp;

public class TestFactory {
	CppUTestOutputParser parser = new CppUTestOutputParser();
	TestCaseFactory factory = new TestCaseFactoryImp(parser);

	@Test
	public void createNormalTestResult() {
		TestCaseResult tcResult = factory.createTestCase("TEST(TestSuite, TestCaseName) - 3 ms");
		assertTrue(tcResult instanceof TestCaseResultImp);
	}
	
	@Test
	public void createNullTestResult(){
		TestCaseResult tcResult = factory.createTestCase("");
		assertTrue(tcResult instanceof NullTestCaseResult);
	}
}
