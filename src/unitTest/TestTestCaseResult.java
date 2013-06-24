package unitTest;

import org.jmock.*;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestMessage;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import static org.junit.Assert.*;

import org.junit.Test;
import org.sharon.cpputest.TestCaseFactory;
import org.sharon.cpputest.TestCaseFactoryImp;
import org.sharon.cpputest.TestCaseResult;
import org.sharon.cpputest.TestCaseResultImp;

public class TestTestCaseResult {
	Mockery context = new Mockery();
	String lineOfSuccCase = "TEST(TestSuite, TestSucc) - 3 ms";
	String beginningLineOfFailedCase = "TEST(TestSuite, TestFail)";
	final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);

	@Test
	public void testTestCaseResultReporting() {
		
		TestCaseResult testCaseResult = new TestCaseResultImp(lineOfSuccCase);
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("TestSucc");
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).setTestingTime(3);
				oneOf(updater).exitTestCase();
			}
		});
		
		testCaseResult.putTo(updater);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testTestCaseInfoComplete(){
		TestCaseResult testResult = new TestCaseResultImp(lineOfSuccCase);
		assertFalse(testResult.needMoreInfo());
	}
	
	@Test
	public void testTestCaseInfoImcomplete(){
		TestCaseResult testResult = new TestCaseResultImp(beginningLineOfFailedCase);
		assertTrue(testResult.needMoreInfo());
	}
	
	@Test
	public void testSupplyMoreInfoToCompleteTestCase(){
		TestCaseResult testResult = new TestCaseResultImp(beginningLineOfFailedCase);
		testResult.read("no implementation");
		assertTrue(testResult.needMoreInfo());
		testResult.read(" - 5 ms");
		assertFalse(testResult.needMoreInfo());
	}
	
	@Test
	public void testReportFailedTestCase(){
		
		TestCaseResult testCaseResult = new TestCaseResultImp(beginningLineOfFailedCase);
		testCaseResult.read("Error: not implemented");
		testCaseResult.read(" - 5 ms");
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("TestFail");
				oneOf(updater).setTestStatus(ITestItem.Status.Failed);
				oneOf(updater).setTestingTime(5);
				oneOf(updater).exitTestCase();
			}
		});
		
		testCaseResult.putTo(updater);
		context.assertIsSatisfied();
	}
	
	@Test
	public void funnyOne(){

//		TEST(TestTimerCommandParser, parseExpireTime) - 0 ms
		TestCaseFactory factory = new TestCaseFactoryImp();
		String line = "TEST(TestTimerCommandParser, parseAction)";
		TestCaseResult tcResult = factory.createTestCase(line);
		tcResult.read("test/testCmdLineParser.cpp:37: error: Failure in TEST(TestTimerCommandParser, parseAction)");
		tcResult.read("        expected <1>");
		tcResult.read("        but was  <0>");
		tcResult.read("        difference starts at position 0 at: <          0         >");
		tcResult.read("                                                       ^");
		tcResult.read("");
		tcResult.read(" - 0 ms");
		
		assertFalse(tcResult.needMoreInfo());
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("parseAction");
				oneOf(updater).setTestStatus(ITestItem.Status.Failed);
				oneOf(updater).setTestingTime(0);
				oneOf(updater).exitTestCase();
			}
		});
		
		tcResult.putTo(updater);
		context.assertIsSatisfied();
	}
}
