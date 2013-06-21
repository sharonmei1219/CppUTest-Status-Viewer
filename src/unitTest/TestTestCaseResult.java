package unitTest;

import org.jmock.*;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import static org.junit.Assert.*;

import org.junit.Test;
import org.sharon.cpputest.TestCaseResult;
import org.sharon.cpputest.TestCaseResultImp;

public class TestTestCaseResult {
	Mockery context = new Mockery();
	String lineOfSuccCase = "TEST(TestSolidBorderedRectangle, DrawSolidBorderedRectangle) - 3 ms";
	String lineOfFailedCase = "TEST(TestSolidBorderedRectangle, DrawSolidBorderedRectangle)";
	final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);

	@Test
	public void testTestCaseResultReporting() {
		
		TestCaseResult testResult = new TestCaseResultImp(lineOfSuccCase);
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("DrawSolidBorderedRectangle");
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).setTestingTime(3);
				oneOf(updater).exitTestCase();
			}
		});
		
		testResult.putTo(updater);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testTestCaseInfoComplete(){
		TestCaseResult testResult = new TestCaseResultImp(lineOfSuccCase);
		assertFalse(testResult.needMoreInfo());
	}
	
	@Test
	public void testTestCaseInfoImcomplete(){
		TestCaseResult testResult = new TestCaseResultImp(lineOfFailedCase);
		assertTrue(testResult.needMoreInfo());
	}
	
	@Test
	public void testSupplyMoreInfoToCompleteTestCase(){
		TestCaseResult testResult = new TestCaseResultImp(lineOfFailedCase);
		testResult.read("no implementation");
		assertTrue(testResult.needMoreInfo());
		testResult.read(" - 5 ms");
		assertFalse(testResult.needMoreInfo());
	}
}
