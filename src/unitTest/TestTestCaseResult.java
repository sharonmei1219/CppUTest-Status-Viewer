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
		final String errorInfo = "test/testTimer.cpp:40: error: Failure in TEST(TestTimer, testTimerExpired)";
		final String fileName = "test/testTimer.cpp";
		testCaseResult.read(errorInfo);
		testCaseResult.read(" - 5 ms");
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("TestFail");
				oneOf(updater).setTestStatus(ITestItem.Status.Failed);
				oneOf(updater).addTestMessage(fileName, 40, ITestMessage.Level.Error, " Failure in TEST(TestTimer, testTimerExpired)");
				oneOf(updater).setTestingTime(5);
				oneOf(updater).exitTestCase();
			}
		});
		
		testCaseResult.putTo(updater);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testRegularExpression(){
		
		String line = "test/testTimer.cpp:40: error: Failure in TEST(TestTimer, testTimerExpired)";
		String pattern = "(.*)(:)([0-9]*)(: error:.*)";
		String fileName = line.replaceAll(pattern, "$1");
		assertTrue(line.matches(pattern));
		assertEquals(fileName, "test/testTimer.cpp");
		assertEquals(Integer.parseInt(line.replaceAll(pattern, "$3")), 40);
		
		String splitedName[] = fileName.split("/");
		String neatFileName = splitedName[splitedName.length-1];
		assertEquals(neatFileName, "testTimer.cpp");
		
	}
}
